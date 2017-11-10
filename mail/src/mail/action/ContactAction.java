package mail.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import mail.bean.Cong;
import mail.bean.Contact;
import mail.bean.Group;
import mail.bean.UGroup;
import mail.bean.Units;
import mail.bean.User;
import mail.service.CongService;
import mail.service.ContactService;
import mail.service.GroupService;
import mail.service.UGroupService;
import mail.service.UnitsService;
import mail.service.UserService;
import util.LoggerUtil;
import util.Page;

@Component("contactAction")
@Scope("prototype")
public class ContactAction {
	private static final Log logger = LogFactory.getLog(ContactAction.class);
	@Autowired
	private ContactService contactService ;
	@Autowired
	private UGroupService uGroupService ;
	@Autowired
	private CongService congService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UnitsService unitsService ;
	@Autowired
	private GroupService groupService ;
	
	private Contact contact ;
	
	private String cgid ;
	
	private String cname;
		
	//判断用户名是否存在于邮箱服务器
	public String checkUserExist() throws Exception{
//		String addr = ServletActionContext.getRequest().getParameter("addr");
//		User user = new User();
//		user = userService.checkUserNameExist(addr);
//		
//		if(user!=null){
//			ServletActionContext.getResponse().getWriter().print(1);
//		}
		return null;
	}
	
	//判断联系人邮箱地址是否存在
	public String checkAddrExist() throws Exception{
		String addr = ServletActionContext.getRequest().getParameter("addr");
		User user= (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		contact = new Contact();
		contact.setAddr(addr);
		contact.setUserid(user.getId());
		contact = contactService.checkAddrExist(contact);
		
		if(contact!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}
	
	public String addContact(){
		User user= (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user!=null){
			List<UGroup> ugrouplist = uGroupService.getUGroupList(user.getId());
			ServletActionContext.getRequest().setAttribute("ugrouplist",ugrouplist);
		}
		return "success";
	}

	//添加联系人操作
	public String doAddContact(){
		contactService.addContact(contact);
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("ugid");
		contact = contactService.checkAddrExist(contact);
		int cid = contact.getId();
		if(ids!=null&&ids.length>0){
			congService.addBatCong2(cid,ids);
		}
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "添加联系人", "", "", "","",contact.getAddr(),"");
		return "success";
	}
	
	public String editContact(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		contact = contactService.getContact(iid);
		ServletActionContext.getRequest().setAttribute("contact", contact);
		return "success" ;
	}
	
	//编辑联系人操作
	public String doEditContact(){
		
		contactService.editContact(contact);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "编辑联系人", "", "", "","",contact.getAddr(),"");
		return "success";
	}
	
	//进入联系人页面
	public String getContactList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Units> unitlist = new ArrayList<Units>();
		Units units = new Units();
		unitlist = unitsService.getUnitsList(units);
		
		if(unitlist!=null&&unitlist.size()>0){
			for(Units u:unitlist){
				if(u.getId()==1){
					u.setPid(0);
				}
			}
		}
		ServletActionContext.getRequest().setAttribute("unitlist",unitlist);
		
		User user= (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		
		List<Group> grouplist = new ArrayList<Group>();
		List<Group> grouplist1 = new ArrayList<Group>();
		grouplist = groupService.getGroupList(-1);
		grouplist1 = groupService.getGroupLists(user);
		if(grouplist!=null&&grouplist.size()>0){
			int j = grouplist.size();
			for(int i=0;i<j;i++){
				boolean flag = false;
				if(grouplist.get(i).getState()==2){
					if(grouplist1!=null&&grouplist1.size()>0){
						for(Group ggg : grouplist1){
							if(grouplist.get(i).getId()==ggg.getId()){
								flag = true;
							}
						}
					}
					if(!flag){
						grouplist.remove(grouplist.get(i));
						i--;
						j--;
					}
				}
			}
		}
		ServletActionContext.getRequest().setAttribute("grouplist",grouplist);
		
		List<UGroup> ugrouplist = new ArrayList<UGroup>();
		if(user!=null){
			ugrouplist = uGroupService.getUGroupList(user.getId());
			ServletActionContext.getRequest().setAttribute("ugrouplist",ugrouplist);
		}
		
		String id = request.getParameter("id");
		String ugid = request.getParameter("ugid");
		String unitid = request.getParameter("unitid");
		String name = request.getParameter("name");

		String uname = request.getParameter("uname");
		String gname = request.getParameter("gname");
		String usernames = request.getParameter("usernames");
		if(uname!=null&&!uname.equals("")){
			List<Contact> contactList = new ArrayList<Contact>();
			if(usernames!=null&&!usernames.equals("")){
				String [] usernamess = usernames.split(",");
				if(usernamess!=null&&usernamess.length>0){
					for(int i=0;i<usernamess.length;i++){
						Contact contact = new Contact();
						String [] usernamesss = usernamess[i].split("_");
						if(usernamesss!=null&&usernamesss.length>0){
							if(!usernamesss[1].equals(user.getUsername())){
								contact.setName(usernamesss[0]);
								contact.setAddr(usernamesss[1]);
								contactList.add(contact);
							}
						}
					}
				}
			}
			request.setAttribute("uname",uname);
			request.setAttribute("id",id);
			request.setAttribute("unitid",unitid);
			request.setAttribute("usernames",usernames);
			request.setAttribute("contactList", contactList);
			
			Object pagenow=request.getParameter("pagenow1");
			Object pagesize=request.getParameter("pagesize");
			Object totalsize=request.getParameter("totalsize");
			if(pagenow != null && pagesize != null && totalsize != null&&pagenow.toString() != ""&&pagesize.toString() != ""&&totalsize.toString() != "")
			{
				Page page = new Page();
				page.setPagenow(Integer.parseInt(pagenow.toString()));
				page.setPagesize(Integer.parseInt(pagesize.toString()));
				page.setTotalsize(Integer.parseInt(totalsize.toString()));

				request.setAttribute("pagebar",page.pagebar1());
				request.setAttribute("pagenow1",pagenow);
				request.setAttribute("pagesize",pagesize);
				request.setAttribute("totalsize",totalsize);
				request.setAttribute("pagenum",request.getParameter("pagenum"));
			}
			
		}else if(gname!=null&&!gname.equals("")){
			List<Contact> contactList = new ArrayList<Contact>();
			if(usernames!=null&&!usernames.equals("")){
				String [] usernamess = usernames.split(",");
				if(usernamess!=null&&usernamess.length>0){
					for(int i=0;i<usernamess.length;i++){
						Contact contact = new Contact();
						String [] usernamesss = usernamess[i].split("_");
						if(usernamesss!=null&&usernamesss.length>0){
							if(!user.getUsername().equals(usernamesss[1])){
								contact.setName(usernamesss[0]);
								contact.setAddr(usernamesss[1]);
								contactList.add(contact);
							}
						}
					}
				}
			}
			request.setAttribute("id",id);
			request.setAttribute("gname",gname);
			request.setAttribute("usernames",usernames);
			request.setAttribute("contactList", contactList);
			
			Object pagenow=request.getParameter("pagenow1");
			Object pagesize=request.getParameter("pagesize");
			Object totalsize=request.getParameter("totalsize");
			if(pagenow != null && pagesize != null && totalsize != null&&pagenow.toString() != ""&&pagesize.toString() != ""&&totalsize.toString() != "")
			{
				Page page = new Page();
				page.setPagenow(Integer.parseInt(pagenow.toString()));
				page.setPagesize(Integer.parseInt(pagesize.toString()));
				page.setTotalsize(Integer.parseInt(totalsize.toString()));

				request.setAttribute("pagebar",page.pagebar2());
				request.setAttribute("pagenow1",pagenow);
				request.setAttribute("pagesize",pagesize);
				request.setAttribute("totalsize",totalsize);
				request.setAttribute("pagenum",request.getParameter("pagenum"));
			}
			
		}else{
			if(name!=null&&!name.equals("")){
				name = name.replaceAll("　","");
			}
			request.setAttribute("ugid",ugid);
			request.setAttribute("name",name);
			List<Contact> contactList = new ArrayList<Contact>();
			if(contact == null){
				contact = new Contact();
			}
			
			contact.setUserid(user.getId());
			
			if(ugid!=null&&!ugid.equals("")){
				List<String> cidlist = new ArrayList<String>();
				cidlist = congService.getCidList(ugid);
				String cids = "";
				if(cidlist!=null&&cidlist.size()>0){
					for(String cid : cidlist){
						cids+=cid+",";
					}
					cids = cids.substring(0,cids.length()-1);
				}
				if(cids!=null&&!cids.equals("")){
					contact.setIds(cids);
				}else{
					contact.setIds(0+"");
				}
			}
			int totalsize = contactService.getSize(contact);
			if(totalsize>0){
				String currentpage = request.getParameter("pagenow");
				int pagenow = 0 ;
				int pagesize = 13 ;
				if(currentpage != null){
					pagenow = Integer.parseInt(currentpage);
				}else{
					pagenow = 1;
				}
				if(pagenow == 0){
					pagenow = 1;
				}
				int pagenum = 0 ;
				if(totalsize%pagesize==0){
					pagenum = totalsize/pagesize;
				}else{
					pagenum = totalsize/pagesize+1;
				}
				contact.setStart(pagesize*pagenow-pagesize);
				contact.setEnd(pagesize);
				
				contactList = contactService.getContactList(contact);
				request.setAttribute("contact", contact);
				
				if(contactList != null&&contactList.size()>0){
					String cids = "";
					for(Contact contact:contactList){
						cids+=contact.getId()+",";
					}
					for(Contact contact:contactList){
						if(user.getUsername().equals(contact.getAddr())){
							contactList.remove(contact);
							break;
						}
					}
					cids = cids.substring(0,cids.length()-1);
					List<Cong> conglist = new ArrayList<Cong>();
					conglist = congService.getCongListByCids(cids);
					if(conglist!=null&&conglist.size()>0){
						for(Contact contact:contactList){
							String gnames = "";
							for(Cong cong : conglist){
								if(contact.getId()==cong.getCid()){
									if(ugrouplist!=null&&ugrouplist.size()>0){
										for(UGroup ugroup:ugrouplist){
											if(ugroup.getId()==cong.getUgid()){
												gnames+=ugroup.getName()+";<a class='delg' onclick='deleteugroup1("+ugroup.getId()+","+contact.getId()+")'>×</a>";
											}
										}
									}
								}
							}
							contact.setGnames(gnames);
						}
					}
					request.setAttribute("contactList", contactList);
					Page page = new Page();
					page.setPagenow(pagenow);
					page.setPagesize(pagesize);
					page.setTotalsize(totalsize);
					request.setAttribute("pagebar",page.pagebar());
					request.setAttribute("pagenow",pagenow);
					request.setAttribute("pagesize",pagesize);
					request.setAttribute("totalsize",totalsize);
					request.setAttribute("pagenum",pagenum);
				}
			}else{
				request.setAttribute("contactList", contactList);
			}
		}
		return "success";
	}
	
	//删除联系人
	public String deleteContact(){		
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("cid");
		contactService.deleteContact(ids);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "删除联系人", "", "", "","","删除联系人","");
		return "success";
	}

	//联系人添加到联系人分组
	public String doAddCong(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String ugid = request.getParameter("cgid");
		String name = request.getParameter("cname");
		name = name.replaceAll("　","");
		request.setAttribute("cgid", ugid);
		request.setAttribute("cname", name);
		String[] ids = request.getParameterValues("cid"); 
		List<String> cidlist = new ArrayList<String>();
		
		if(ugid!=null&&!ugid.equals("")){
			cidlist = congService.getCidList(ugid);
		}
		congService.addBatCong(ugid,ids,cidlist);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "添加联系人到分组", "", "", "","","添加联系人到分组","");
		return "success";
	}
	
	//联系人解除联系人分组
	public String deleteCong() throws IOException{
		String cid1 = ServletActionContext.getRequest().getParameter("cid");
		int cid = 0 ;
		if(cid1!=null&&!cid1.equals("")){
			cid = Integer.parseInt(cid1);
		}
		String ugid1 = ServletActionContext.getRequest().getParameter("ugid");
		int ugid = 0 ;
		if(ugid1!=null&&!ugid1.equals("")){
			ugid = Integer.parseInt(ugid1);
		}
		Cong cong = new Cong();
		cong.setCid(cid);
		cong.setUgid(ugid);
		congService.deleteCong(cong);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "联系人解除分组", "", "", "","","联系人解除分组","");
		ServletActionContext.getResponse().getWriter().print(1);
		return null;
	}
	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getCgid() {
		return cgid;
	}

	public void setCgid(String cgid) {
		this.cgid = cgid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
}