package mail.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import mail.bean.Contact;
import mail.bean.UGroup;
import mail.bean.User;
import mail.service.CongService;
import mail.service.ContactService;
import mail.service.UGroupService;
import util.LoggerUtil;

@Component("uGroupAction")
@Scope("prototype")
public class UGroupAction {
	private static final Log logger = LogFactory.getLog(UGroupAction.class);
	@Autowired
	private UGroupService uGroupService ;
	@Autowired
	private CongService congService ;
	@Autowired
	private ContactService contactService ;
	
	private UGroup ugroup ;
	
	//判断用户联系人分组名称是否存在
	public String checkNameExist() throws Exception{
		String name = ServletActionContext.getRequest().getParameter("name");
		String userid = ServletActionContext.getRequest().getParameter("userid");
		int userids = 0;
		if(userid!=null&&!userid.equals("")){
			userids = Integer.parseInt(userid);
		}
		ugroup = new UGroup();
		ugroup.setName(name);
		ugroup.setUserid(userids);
		ugroup = uGroupService.checkNameExist(ugroup);
		
		if(ugroup!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}
	
	public String addUGroup(){
		
		return "success";
	}
	//添加联系人分组
	public String doAddUGroup(){
		uGroupService.addUGroup(ugroup);
		ugroup = uGroupService.checkNameExist(ugroup);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "添加联系人分组", "", "", "","",ugroup.getName(),"");
		return "success";
	}
	
	public String editUGroup(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		ugroup = uGroupService.getUGroup(iid);
		ServletActionContext.getRequest().setAttribute("ugroup", ugroup);
		List<String> cidlist = new ArrayList<String>();
		cidlist = congService.getCidList(id);
		User user= (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		List<Contact> contactlist = new ArrayList<Contact>();
		contactlist = contactService.getContactListByUserid(user.getId());
		List<Contact> contactlist1 = new ArrayList<Contact>();
		List<Contact> contactlist2 = new ArrayList<Contact>();
		if(contactlist!=null&&contactlist.size()>0){
			for(Contact con : contactlist){
				if(cidlist!=null&&cidlist.size()>0){
					boolean flag = false ;
					for(String cid : cidlist){
						if(cid.equals(con.getId()+"")){
							flag = true ;
						}
					}
					if(flag){
						contactlist2.add(con);
					}else{
						contactlist1.add(con);
					}
				}else{
					contactlist1.add(con);
				}
			}
		}
		ServletActionContext.getRequest().setAttribute("contactlist1", contactlist1);
		ServletActionContext.getRequest().setAttribute("contactlist2", contactlist2);
		return "success" ;
	}
	
	public String doEditUGroup(){
		
		uGroupService.editUGroup(ugroup);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "编辑联系人分组", "", "", "","",ugroup.getName(),"");
		return "success";
	}
	
	public String getUGroupList(){
		String uid = ServletActionContext.getRequest().getParameter("userid");
		int userid = 0 ;
		if(uid!=null&&!uid.equals("")){
			userid = Integer.parseInt(uid);
		}
		List<UGroup> ugrouplist = new ArrayList<UGroup>();
		ugrouplist=uGroupService.getUGroupList(userid);
		ServletActionContext.getRequest().setAttribute("ugrouplist", ugrouplist);
		return "success";
	}

	public String deleteUGroup() throws IOException{
		String id1 = ServletActionContext.getRequest().getParameter("id");
		int id = 0 ;
		if(id1!=null&&!id1.equals("")){
			id = Integer.parseInt(id1);
		}
		uGroupService.deleteUGroup(id);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "删除联系人分组", "", "", "","","删除联系人分组","");
		ServletActionContext.getResponse().getWriter().print(1);
		return null;
	}
	
	public UGroup getUgroup() {
		return ugroup;
	}

	public void setUgroup(UGroup ugroup) {
		this.ugroup = ugroup;
	}
	
}