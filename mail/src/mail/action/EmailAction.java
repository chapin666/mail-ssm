package mail.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mail.bean.Cong;
import mail.bean.Contact;
import mail.bean.ETag;
import mail.bean.Email;
import mail.bean.EmailDrafts;
import mail.bean.Files;
import mail.bean.Group;
import mail.bean.MailData;
import mail.bean.Sign;
import mail.bean.Tag;
import mail.bean.UGroup;
import mail.bean.Units;
import mail.bean.User;
import mail.service.CongService;
import mail.service.ContactService;
import mail.service.ETagService;
import mail.service.EmailService;
import mail.service.GroupService;
import mail.service.TagService;
import mail.service.UGroupService;
import mail.service.UnitsService;
import mail.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.Analy;
import util.LogUtil;
import util.LoggerUtil;
import util.Page;
import util.quartz.CustomJob;
import util.quartz.QSendMail;
import util.quartz.QuartzManager;
import adm.bean.Member;
import adm.bean.Notice;

@Component("emailAction")
@Scope("prototype")
public class EmailAction {
	private static final Log logger = LogFactory.getLog(EmailAction.class);
	@Autowired
	private EmailService emailService ;
	@Autowired
	private UGroupService uGroupService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UnitsService unitsService ;
	@Autowired
	private ContactService contactService ;
	@Autowired
	private GroupService groupService ;
	@Autowired
	private CongService congService ;
	@Autowired
	private TagService tagService ;
	@Autowired
	private ETagService eTagService ;
	
	private Email email ;
	
	private Notice notice ;
	
	private File file;
	
	private String fileFileName;
	
	
	private int count;       	//总记录
	private int pageNumber; 	//总页
	private int pageNow;    	//当前页
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	
	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	


	//进入写信页面
	public String Write(){
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
		
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
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
		request.setAttribute("grouplist",grouplist);
		
		//查询所有签名
		List<Sign> signlist = new ArrayList<Sign>();		
		signlist = userService.getSignList(user.getId());//查询所有签名
		String signContent = "";
		String sdefault = "";
		for(int i=0; i<signlist.size();i++){			
			if(signlist.get(i).getDefaultsign()!=null && signlist.get(i).getDefaultsign()!=""&&signlist.get(i).getDefaultsign().length()>0){				
				signContent = signlist.get(i).getContent();	
				sdefault = signlist.get(i).getDefaultsign();
			}
		}
		request.setAttribute("sdefault",sdefault);
		request.setAttribute("signContent",signContent);
		
		List<UGroup> ugrouplist = new ArrayList<UGroup>();
		if(user!=null){
			ugrouplist = uGroupService.getUGroupList(user.getId());
			request.setAttribute("ugrouplist",ugrouplist);
		}
		String cids = request.getParameter("cids");
		Object hosts = request.getSession().getAttribute("host");
		String host = "";
		String showstr = "";
		String eaddr = "";
		if(hosts!=null&&!hosts.equals("")){
			host = hosts.toString();
		}

		if(cids!=null&&!cids.equals("")){
			String [] cidss = cids.split(",");
			if(cidss!=null&&cidss.length>0){
				for(int i=0;i<cidss.length;i++){
					String [] cidsss = cidss[i].split("_");
					if(cidsss!=null&&cidsss.length>0){
						if(cidsss[1].indexOf("@")<0){
							showstr+=cidsss[0]+"<"+cidsss[1]+"@"+host+">;";
							eaddr+=cidsss[1]+",";
						}else{
							showstr+=cidsss[0]+"<"+cidsss[1]+">;";
							eaddr+=cidsss[1]+",";
						}
					}
				}
			}
		}
		request.setAttribute("showstr", showstr);
		request.setAttribute("eaddr", eaddr);
		
		List<Contact> contactList = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setUserid(user.getId());
		// 手动设置查询范围
		contact.setStart(0);
		contact.setEnd(1000);
		
		contactList = contactService.getContactList(contact);
		request.setAttribute("contactList", contactList);
		
		String cidss = "";
		for(Contact contacts:contactList){
			cidss+=contacts.getId()+",";
		}
		if(!cidss.equals("")){
			cidss = cidss.substring(0,cidss.length()-1);
			List<Cong> conglist = new ArrayList<Cong>();
			conglist = congService.getCongListByCids(cidss);
			request.setAttribute("conglist", conglist);
		}
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		return "success";
	}

	//点击邮件查看详情
	public String MailInfo() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String tagmail = request.getParameter("tagmail");
		User user= (User)request.getSession().getAttribute("user");
		String ftype = request.getParameter("ftype");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		String id = ServletActionContext.getRequest().getParameter("id");
        String mailid = ServletActionContext.getRequest().getParameter("mailid");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		if("0".equals(ftype)){
		    email = emailService.getDraftsEmail(iid);
		    email.setToname(email.getTomail());
		    EmailDrafts emailDrafts = new EmailDrafts();
		    emailDrafts.setId(iid);
		    emailDrafts.setSee(1);
		    emailService.editDraftsEmail(emailDrafts);
		} else if("2".equals(ftype)){
            email = emailService.getDrafts(iid);
        } else if("4".equals(ftype)){
            email = emailService.getAllEmail(iid);
        } else if("6".equals(ftype)){
            email = emailService.getAllEmail(iid);
        } else if("10".equals(ftype)){
        	notice = emailService.getNotice(id);
        	if(notice!=null){
        		email = new Email();
        		email.setFrommail(notice.getAuthor());
        		email.setTitle(notice.getTitle());
        		email.setContent(notice.getContent());
        		email.setTime(notice.getTime());
        		email.setTomail(notice.getTomail());
        		email.setState(notice.getState());
        	}
        } else {
		    email = emailService.getEmail(id);
		    email.setState(1); // 已读
		    emailService.editREmail(email);
		}
		Object host = ServletActionContext.getRequest().getSession().getAttribute("host");
		String username = "&lt;"+user.getUsername()+"@"+host+"&gt;";
		String adminname = "&lt;admin@"+host+"&gt;";
		ServletActionContext.getRequest().setAttribute("username", username);
		if(email!=null&&email.getContent()!=null){
			ServletActionContext.getRequest().setAttribute("content1", email.getContent().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'"));
		}
		if(email!=null&&email.getTitle()!=null){
			ServletActionContext.getRequest().setAttribute("title1", email.getTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'"));
		}
		if(email!=null&&email.getFrommail()!=null){
			email.setFrommail(email.getFrommail().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'"));
		}
		if(email!=null&&email.getCopyto()!=null){
			email.setCopyto(email.getCopyto().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'"));
		}
		if(email!=null&&email.getBcc()!=null){
			email.setBcc(email.getBcc().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'"));
		}
		if(email!=null&&email.getToname()!=null){
			email.setToname(email.getToname().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'"));
		}
		if(email.getFile()!=null&&!email.getFile().equals("")){
			List<Files> filelist = new ArrayList<Files>();
			String [] files = email.getFile().split("\\|");
			if(files!=null&&files.length>0){
				for(String file : files){
					Files ff = new Files();
					String [] filess = file.split("\\*");
					if(filess!=null&&filess.length>0&&filess[0]!=null&&!filess[0].equals("")&&filess[1]!=null&&!filess[1].equals("")){
						ff.setFile(filess[1]);
						ff.setFilename(filess[0]);
						filelist.add(ff);
					}
				}
				ServletActionContext.getRequest().setAttribute("filelist",filelist);
			}
		}
		if(email.getContent() != null)
		{
			email.setContent(email.getContent().replace("href=\"", "target=\"_blank\" href=\""));
		}
		
		ServletActionContext.getRequest().setAttribute("email", email);
		ServletActionContext.getRequest().setAttribute("adminname", adminname);
		ServletActionContext.getRequest().setAttribute("ftype", ftype);
		if(tagmail != null){
			String tagmailid = request.getParameter("tagmailid");//标签邮件mailid
			String tagtypes = request.getParameter("tagtypes");//1：收件箱标签邮件 ，2：发件箱标签邮件，3：草稿箱标签邮件
			String tagname = request.getParameter("tagname");//标签名
			String draftsid = request.getParameter("draftsid");//草稿箱id
			request.setAttribute("mailid", tagmailid);
			request.setAttribute("types", tagtypes);
			request.setAttribute("tagname", tagname);
			request.setAttribute("draftsid",draftsid);
			return "tagmail";
		}else{
			return "success";
		}		
	}

	//后台进入单位公告查看详情
	public String NewsInfo() throws Exception{
		String id = ServletActionContext.getRequest().getParameter("id");
		notice = emailService.getNotice(id);
		ServletActionContext.getRequest().setAttribute("notice", notice);
		return "success";
	}
	
	//后台进入发布公告的页面
	public String SendNews(){
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
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
		Member member= (Member)request.getSession().getAttribute("member");
		if(user==null&&member!=null){
			user = new User();
			user.setId(-1);
		}
		
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
		request.setAttribute("grouplist",grouplist);		
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		return "success";
	}

	//进入添加分级管理员页面
	public String SendSubAdmin(){
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
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
		Member member= (Member)request.getSession().getAttribute("member");
		if(user==null&&member!=null){
			user = new User();
			user.setId(-1);
		}
		
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
		request.setAttribute("grouplist",grouplist);		
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		return "success";
	}
	
	//单位部门递归查询
	public List<String> unitids(List<String> uidss,List<String> uidsss){
		String uids = "" ;
		List<String> uiid = new ArrayList<String>();
		if(uidsss!=null&&uidsss.size()>0){
			for(String uid:uidsss){
				uids+=uid+",";
			}
		}else{
			for(String uid:uidss){
				uids+=uid+",";
			}
		}
		uids = uids.substring(0,uids.length()-1);
		uiid = unitsService.getIdByPids(uids);
		if(uiid!=null&&uiid.size()>0){
			for(String uiids:uiid){
				uidss.add(uiids);
			}
			unitids(uidss,uiid);
		}
		return uidss ;
	}
	
	//发送邮件以及保存邮件
	public String SendMail() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		session.setAttribute("progressBar",0);      //定义指定上传进度的Session变量
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
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
		
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
		request.setAttribute("grouplist",grouplist);
		List<UGroup> ugrouplist = new ArrayList<UGroup>();
		if(user!=null){
			ugrouplist = uGroupService.getUGroupList(user.getId());
			request.setAttribute("ugrouplist",ugrouplist);
		}
		String cids = request.getParameter("cids");
		Object host = request.getSession().getAttribute("host");
		String showstr = "";
		String eaddr = "";

		if(cids!=null&&!cids.equals("")){
			String [] cidss = cids.split(",");
			if(cidss!=null&&cidss.length>0){
				for(int i=0;i<cidss.length;i++){
					String [] cidsss = cidss[i].split("_");
					if(cidsss!=null&&cidsss.length>0){
						showstr+=cidsss[0]+"<"+cidsss[1]+"@"+host+">;";
						eaddr+=cidsss[1]+",";
					}
				}
			}
		}
		request.setAttribute("showstr", showstr);
		request.setAttribute("eaddr", eaddr);
		
		List<Contact> contactList = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setUserid(user.getId());
		contactList = contactService.getContactList(contact);
		request.setAttribute("contactList", contactList);
		
		String cidss = "";
		for(Contact contacts:contactList){
			cidss+=contacts.getId()+",";
		}
		if(!cidss.equals("")){
			cidss = cidss.substring(0,cidss.length()-1);
			List<Cong> conglist = new ArrayList<Cong>();
			conglist = congService.getCongListByCids(cidss);
			request.setAttribute("conglist", conglist);
		}
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);

		String path = this.getClass().getResource("/").getPath();
		if(path!=null&&!path.equals("")){
			path = path.replace("/WEB-INF/classes/","");
		}
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		email.setTime(time);

		email.setReceipt(1);
		email.setSee(2);
		
		List<String> recivelist = new ArrayList<String>();
		List<String> copytolist = new ArrayList<String>();
		List<String> bcclist = new ArrayList<String>();
		String types = "";

		if(email.getToname()!=null&&!email.getToname().equals("")){
			String[] enames = email.getToname().split(";");
			if(enames!=null&&enames.length>0){
				for(int i=0;i<enames.length;i++){
					String address=enames[i];
					
					if(address.indexOf("@")<0){
						address=address+"@"+host;
					}
					if(address.indexOf("<")<0){
						address="<"+address+">";
					}
					email.setToname(email.getToname().replace(enames[i], address));
					
					types+=address;
					recivelist.add(address.replace("<","&lt;").replace(">","&gt;"));
				}
			}
		}
		if(email.getCopyto()!=null&&!email.getCopyto().equals("")){
			String[] cnames = email.getCopyto().split(";");
			if(cnames!=null&&cnames.length>0){
				for(int i=0;i<cnames.length;i++){
					String address=cnames[i];
					
					if(address.indexOf("@")<0){
						address=address+"@"+host;
					}
					if(address.indexOf("<")<0){
						address="<"+address+">";
					}
					email.setCopyto(email.getCopyto().replace(cnames[i], address));
					
					types+=address;
					copytolist.add(address.replace("<","&lt;").replace(">","&gt;"));
				}
			}
		}
		if(email.getBcc()!=null&&!email.getBcc().equals("")){
			String[] bnames = email.getBcc().split(";");
			if(bnames!=null&&bnames.length>0){
				for(int i=0;i<bnames.length;i++){
					String address=bnames[i];
					
					if(address.indexOf("@")<0){
						address=address+"@"+host;
					}
					if(address.indexOf("<")<0){
						address="<"+address+">";
					}
					email.setBcc(email.getBcc().replace(bnames[i], address));
					
					types+=address;
					bcclist.add(address.replace("<","&lt;").replace(">","&gt;"));
				}
			}
		}
		
		email.setType(types);
		request.setAttribute("recivelist",recivelist);
		request.setAttribute("copytolist",copytolist);
		request.setAttribute("bcclist",bcclist);
		request.setAttribute("email",email);
		
		if(email.getId()==0){
			// state：0 保存邮件，state：发送邮件
			emailService.sendEmail(email,email.getState());
		}else{
			emailService.editEmail(email);
		}
		
		if(email.getState() == 0)
			return "to3";
		
		return "to1";
	}
	
	//发布公告+保存草稿
	public String SendAdmMail() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		notice.setTime(time);
		
		if(notice.getId()==0){
			emailService.addNotice(notice);
		}else{
			emailService.editNotice(notice);
		}
		request.setAttribute("notice",notice);
		if(notice.getState()==2){
			LogUtil.addLog(ServletActionContext.getRequest(), logger, "公告保存草稿", "", "", "","",notice.getTitle(),"");
			return "to3";
		}else{
			LogUtil.addLog(ServletActionContext.getRequest(), logger, "发信", notice.getTitle(), notice.getAuthor(), notice.getTomail().replace("<","&lt;").replace(">","&gt;"),"单位公告"," 发布公告","");
			LogUtil.addLog(ServletActionContext.getRequest(), logger, "收信", notice.getTitle(), notice.getAuthor(), notice.getTomail().replace("<","&lt;").replace(">","&gt;"),"单位公告"," 接收公告","");
			return "to1";
		}
	}

	/**进入收件箱 */
	public String getMail(){
		HttpServletRequest request = ServletActionContext.getRequest();	
		
		String orby = request.getParameter("orby");//按关键字排序	
		String sortorder = request.getParameter("sortorder");//排序方式
		
//		//分页排序
//		String orbypage = request.getParameter("orbypage");//按关键字排序 分页查询
//		String sortorderpage = request.getParameter("sortorderpage");//排序方式 分页查询
//		if(orbypage !=null && sortorderpage != null){
//			orby = orbypage;
//			sortorder = sortorderpage;
//		}	
		
		
		User user= (User)request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		user1.setName("<admin@"+hosts+">");
		
		String issee = request.getParameter("state");
		if(issee!=null&&!issee.equals("")){
		    issee = "0";
		    user1.setPhone("0");
		    user1.setStat("0");
		}else{
		    issee = "0 , 1";
		    user1.setPhone("1");
		    user1.setStat("0");
		}
		request.setAttribute("issee",issee);
		
		List<Email> emaillist = new ArrayList<Email>();
		
		int totalsize = emailService.getReciveSize(user1);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;			
			
			if(currentpage != null){
				pagenow = Integer.parseInt(currentpage);
			}else{
				pagenow = 1;
			}			
			
			if(pagenow == 0){
				pagenow = 1;
			}
			int pagenum = 0 ;//总页
			if(totalsize%pagesize==0){
				pagenum = totalsize/pagesize;
			}else{
				pagenum = totalsize/pagesize+1;
			}
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			if(sortorder == null){//默认按desc降序
				sortorder = "desc";
				user1.setSortorder(sortorder);
			}else{
				user1.setSortorder(sortorder);
			}
			
			if(orby == null){//orby==null按默认 time关键字 排序 
				orby = "time";
				user1.setOrby(orby);
			}else{
				user1.setOrby(orby);
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			emaillist = emailService.reciveEmail(user1);
			if(emaillist!=null&&emaillist.size()>0){
				for(Email email : emaillist){
					if(email.getFrommail()!=null&&!email.getFrommail().equals("")){
					    if(email.getFromname() == null || "".equals(email.getFromname()))
					    {
					    	if(email.getFrommail().indexOf("@") != -1)
					    	{
					    		 email.setFromname(email.getFrommail().substring(1, email.getFrommail().indexOf("@")));
					    	}
					    }

                        email.setFrommail(email.getFrommail().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
					if(email.getSubject()!=null&&!email.getSubject().equals("")){
						email.setSubject(email.getSubject().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
				}
				request.setAttribute("orby",orby);
				request.setAttribute("sortorder",sortorder);
				request.setAttribute("emaillist",emaillist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	//收件箱编辑操作（如移动到已删除 发件箱 草稿箱等）
	public String EditReciveMail(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		Object host = request.getSession().getAttribute("host");
		String edittype = request.getParameter("edittype");
		String [] ids = request.getParameterValues("id");
		String [] states = request.getParameterValues("states");
		String [] types = request.getParameterValues("types");
		String [] files = request.getParameterValues("files");
		String [] frommails = request.getParameterValues("frommails");
		
		String tid = request.getParameter("tid");
		
		if(edittype.equals("5")){
			emailService.deleteFEmails(states,types,ids,files,frommails,"<"+user.getUsername()+"@"+host+">","@"+host+">",2);
		}else if(tid.equals("-3")){
			int state = 1;			
			emailService.updateMeilState(ids,state);
		}else if(tid.equals("-4")){
			int state = 0;
			emailService.updateMeilState(ids, state);
		}else{
			int type = Integer.parseInt(edittype);
			emailService.editFEmails(ids,type);
		}
		return "success";
	}
	//进入星标邮件
	public String getMainMail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		String issee = request.getParameter("issee");
		if(issee!=null&&!issee.equals("")){
			user1.setPhone(issee);
		}else{
			user1.setPhone("1,0");
		}
		request.setAttribute("issee",issee);
		List<Email> emaillist = new ArrayList<Email>();
		
		int totalsize = emailService.getMainMailSize(user1);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;
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
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			emaillist = emailService.getMainMail(user1);
			if(emaillist!=null&&emaillist.size()>0){
				for(Email email : emaillist){
					if(email.getFrommail()!=null&&!email.getFrommail().equals("")){
						
						MailData data=new MailData();
						if(email.getBoxtype().equals("1"))
						{
							data = emailService.getRecvMail(email.getMailid());//编码转换
						}else if(email.getBoxtype().equals("2")){
							data = emailService.getSendMailByMailId(email.getMailid());//编码转换
						}
						
						if(data !=null){
							email.setTitle(data.getSubject());	
							email.setFile(data.getFile());
	                        email.setFilename(data.getFilename());
						}
						
						if(email.getTitle()!=null&&!email.getTitle().equals("")){
							email.setTitle(email.getTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
						}
						if(email.getFrommail() != null){
						 	if(email.getFrommail().indexOf("@") != -1)
					    	{
						 		email.setFrommail(email.getFrommail().substring(1, email.getFrommail().indexOf("@")));
					    	}
						}
					}
				}
				request.setAttribute("emaillist",emaillist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	// 进入发件箱
	public String fromMail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		List<Email> emaillist = new ArrayList<Email>();
		
		int totalsize = emailService.getFromSize(user1);//总记录
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;			
			
			
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
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			emaillist = emailService.fromEmail(user1);
			if(emaillist!=null&&emaillist.size()>0){
				for(Email email : emaillist){
					if(email.getToname()!=null&&!email.getToname().equals("")){
						email.setToname(email.getToname().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
					if(email.getCopyto()!=null&&!email.getCopyto().equals("")){
						email.setCopyto(email.getCopyto().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
					if(email.getBcc()!=null&&!email.getBcc().equals("")){
						email.setBcc(email.getBcc().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
					if(email.getTomail() != null){
						if(email.getTomail().indexOf("@") != -1)
						{
							email.setTomail(email.getTomail().substring(1, email.getTomail().indexOf("@")));
						}
					}
					if(email.getTitle()!=null&&!email.getTitle().equals("")){
						email.setTitle(email.getTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
					MailData data = new MailData();
					data.setSubject(email.getTitle());
					String title = emailService.parseSubject(data);
					if(title!=null){
						email.setTitle(title);
					}
				}
				request.setAttribute("emaillist",emaillist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	//发件箱编辑操作（如移动到已删除 收件箱 草稿箱等）
	public String EditFromMail(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String edittype = request.getParameter("edittype");
		String [] ids = request.getParameterValues("id");
		
		if(edittype.equals("2")){
//			emailService.deleteFEmails(states,types,ids,files,frommails,"<"+user.getUsername()+"@"+host+">","@"+host+">",1);
			emailService.deleteSendEmail(ids);
		}else{
			int state = 1 ;
			if(edittype.equals("1")){
				state=3;
			}else if(edittype.equals("4")){
				state=2;
			}else if(edittype.equals("5")){
				state=0;
			}else if(edittype.equals("6")){
				state=5;
			}
//			emailService.editFEmails(ids,frommails,"<"+user.getUsername()+"@"+host+">",state,types,1);
			emailService.editSendEmail(ids,state);
		}
		return "success";
	}
	//进入草稿箱
	public String saveMail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		List<Email> emaillist = new ArrayList<Email>();
		
		int totalsize = emailService.getDraftsSize(user1);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;
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
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			emaillist = emailService.draftsEmail(user1);
			if(emaillist!=null&&emaillist.size()>0){
				for(Email email : emaillist){
					if(email.getTitle()!=null&&!email.getTitle().equals("")){
						email.setTitle(Analy.encodeSubjectMail(email.getTitle()));
						email.setTitle(email.getTitle().replace("<", "&lt;").replace(">", "&gt;"));
					}
					
					// 切割发件人，存在别名时只获取名称，否则只获取帐号
					String strToMail=email.getTomail();
					String strTomail2=strToMail;
					if(strToMail!=null&&!strToMail.equals("")){
						if(strToMail.indexOf("<")!=-1)
						{
							strTomail2=strToMail.substring(0, strToMail.indexOf("<"));
						}
						if((strTomail2==null || strTomail2.equals("")) &&strToMail.indexOf("@") != -1)
						{
							strTomail2=strToMail.substring(1, strToMail.indexOf("@"));
						}
						email.setTomail(strTomail2.replace("<", "&lt;").replace(">", "&gt;"));
					}
				}
				request.setAttribute("emaillist",emaillist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	//草稿箱编辑操作（如移动到已删除 发件箱 收件箱等）
	public String EditSaveMail(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		Object host = request.getSession().getAttribute("host");
		String edittype = request.getParameter("edittype");
		String [] ids = request.getParameterValues("id");
		String [] types = request.getParameterValues("types");
		String [] frommails = request.getParameterValues("frommails");
		
		if(edittype.equals("2")){
			//emailService.deleteEmailSend(states,types,ids,files,frommails,"<"+user.getUsername()+"@"+host+">","@"+host+">",3);
			emailService.deleteDrafts(ids);
		}else{
			int state = 3 ;
			if(edittype.equals("1")){
				state=4;
			}else if(edittype.equals("4")){
				state=2;
			}else if(edittype.equals("5")){
				state=1;
			}else if(edittype.equals("6")){
				state=5;
			}
			emailService.editEmailSend(ids,frommails,"<"+user.getUsername()+"@"+host+">",state,types,3);
		}
		return "success";
	}

	//进入已删除邮件
	public String delMail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		List<Email> emaillist = new ArrayList<Email>();
		
		int totalsize = emailService.getDelSize(user1);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;
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
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			emaillist = emailService.delEmail(user1);
			if(emaillist!=null&&emaillist.size()>0){
				for(Email email : emaillist){
					if(email.getFrommail()!=null&&!email.getFrommail().equals("")){
						if(email.getFrommail().indexOf("@") != -1)
						{
							email.setFrommail(email.getFrommail().substring(1, email.getFrommail().indexOf("@")));
						}
						email.setFrommail(email.getFrommail().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
					
					MailData data = emailService.getRecvMail(email.getMailid());//编码转换
					if(data !=null){
						email.setTitle(data.getSubject());						
					}
					
					if(email.getTitle()!=null&&!email.getTitle().equals("")){
						email.setTitle(email.getTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
				}
				request.setAttribute("emaillist",emaillist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	
	//清空所以以删除邮件
	public String clearDel(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Object hosts = request.getSession().getAttribute("host");
		User user= (User)request.getSession().getAttribute("user");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		
		List<Email> emaillist = new ArrayList<Email>();		
		emaillist = emailService.delEmail1(user1);//获取所有已删邮件
		
		String  ids[] = new String[emaillist.size()];//获取所有已删邮件 id
		String  tomail[] = new String[emaillist.size()];//获取所有已删邮件 tomail
		
		for(int i=0; i<emaillist.size(); i++){
			ids[i] = emaillist.get(i).getId()+"";
			tomail[i] = emaillist.get(i).getTomail();
		}
		
		emailService.deleteDEmails(ids,tomail);//删除所有已删邮件			
		//emailService.clearDel(user1);	
		return "success";
	}
	
	//清空垃圾箱所有邮件
	public String clearRub(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");//获取登录名
		
		Object hosts = request.getSession().getAttribute("host");
		//所有垃圾记录
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		user1.setName("<admin@"+hosts+">");
		user1.setState(4);
		List<Email>  emaillist = new ArrayList<Email>();		
		emaillist = emailService.getRubMail1(user1);//获取所有垃圾箱所有邮件
		
		
		String  ids[] = new String[emaillist.size()];//获取所有垃圾箱 id
		String  tomails[] = new String[emaillist.size()];//获取所有垃圾箱tomail
		
		for(int i=0; i<emaillist.size(); i++){
			ids[i] = emaillist.get(i).getId()+"";
			tomails[i] = emaillist.get(i).getTomail();
		}		
		emailService.deleteDEmails(ids,tomails);//删除所有垃圾箱邮件
		
		return "success";
	}
	
	//已删除邮件编辑操作（如移动到收件箱 发件箱 草稿箱等）
	public String EditDelMail(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		Object host = request.getSession().getAttribute("host");
		String edittype = request.getParameter("edittype");
		String [] ids = request.getParameterValues("id");
		String [] types = request.getParameterValues("types");
		String [] tomails = request.getParameterValues("tomails");
		String [] boxtype = request.getParameterValues("boxtype");
		
		int state=0;
		try {
			state = Integer.parseInt(edittype);
		} catch (NumberFormatException e) {
			state=-1;
		}

		// 彻底删除邮件
		if(state == 5)
		{
			emailService.delFEmails(ids,boxtype,state);
		}else if(state != -1){	
			// 改变邮件状态
			emailService.editFEmails(ids,boxtype,state);
		}
		

		return "success";
	}
	
	//单位公告编辑操作（如删除公告）
	public String EditNewsMail(){
		
		HttpServletRequest request = ServletActionContext.getRequest();

		String [] ids = request.getParameterValues("id");

		emailService.editFEmails(ids,6);
		return "success";
	}

	//前台进入单位公告
	public String News(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");		
		List<Notice> noticelist = new ArrayList<Notice>();
		
		int totalsize = emailService.getAdmNewsSize(user1);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;
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
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			noticelist = emailService.getAdmNews(user1);
			if(noticelist!=null&&noticelist.size()>0){
				for(Notice notice : noticelist){
					if(notice.getTomail()!=null&&!notice.getTomail().equals("")){
						notice.setTomail(notice.getTomail().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
				}
				request.setAttribute("noticelist",noticelist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	//后台进入单位公告栏目
	public String getNews(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user1 = new User();
		List<Notice> newslist = new ArrayList<Notice>();
		
		int totalsize = emailService.getNewsSize(user1);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 12 ;
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
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			newslist = emailService.getNews(user1);
			if(newslist!=null&&newslist.size()>0){
				request.setAttribute("emaillist",newslist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";
	}
	
	public String editEmail() throws Exception{
		
		String id = ServletActionContext.getRequest().getParameter("id");
		email = emailService.getEmail(id);
		ServletActionContext.getRequest().setAttribute("email", email);
		return "success" ;
	}
	//星际邮件编辑操作（如取消星际邮件）
	public String EditMainMail() throws IOException{
		
		String id = ServletActionContext.getRequest().getParameter("id");
		String ismain = ServletActionContext.getRequest().getParameter("ismain");
		//String flag = ServletActionContext.getRequest().getParameter("flag");
		//String mailid = ServletActionContext.getRequest().getParameter("mailid");
		// 1-收件箱 	2-发件箱  3-草稿箱
		String boxtype = ServletActionContext.getRequest().getParameter("boxtype");
		//String draftsmail = ServletActionContext.getRequest().getParameter("draftsmail");
		
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		int iim = 0 ;
		if(ismain!=null&&!ismain.equals("")){
			iim = Integer.parseInt(ismain);
		}
		Email email = new Email();
		email.setId(iid);
		email.setIsmain(iim);
		email.setType("1");
		
		try {
			if(boxtype.equals("1")){
				// 收件箱的星标邮件操作(email_recv)
				emailService.editREmail(email);
			}else if(boxtype.equals("2")){
				// 发件箱的星标邮件操作(email_send)
				emailService.editEmail(email);
			}else if(boxtype.equals("3")){
				// 草稿箱的星标邮件操作(目前版本，草稿箱不支持星标操作)
				emailService.editDEmail(email);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
//		if(null != draftsmail){
//			EmailDrafts emailDrafts = new EmailDrafts();
//			emailDrafts.setId(iid);
//			emailDrafts.setIsmain(iim);
//			emailService.editDraftsEmail(emailDrafts);
//		}else{
//			if(flag==null){
//				if(mailid!=null){
//					try {
//						if(mailid.equals("-1")){
//							// 草稿箱的星标邮件操作
//							emailService.editDEmail(email);
//						}else{
//							if(boxtype != null)
//							{
//								if(boxtype.equals("1")){
//									// 收件箱的星标邮件操作(email_recv)
//									emailService.editREmail(email);
//								}else{
//									// 发件箱的星标邮件操作(email_send)
//									emailService.editEmail(email);
//								}
//							}
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}else{
//					emailService.editEmail(email);
//				}
//			}else{
//				emailService.editREmail(email);
//			}
//		}
		ServletActionContext.getResponse().getWriter().print(1);
		return null;
	}

	// 举报邮件
	public String ReportMail(){
		HttpServletRequest request = ServletActionContext.getRequest();		
		User user= (User)request.getSession().getAttribute("user");

		String tomail = user.getUsername();
		String frommail = request.getParameter("email.frommail");
		int id = Integer.parseInt(request.getParameter("email.id"));

		String checkbox1[] = request.getParameterValues("checkbox1");
		String checkbox2[] = request.getParameterValues("checkbox2");

		tomail = "<"+tomail+"@"+request.getSession().getAttribute("host")+">";
		
		int i = frommail.indexOf('<');
		int j = frommail.indexOf('>');
		
		if(i>0 && j>0){
			frommail = frommail.substring(i,j+1);
		}
		
		Email email = new Email();
		email.setTomail(tomail);
		email.setFrommail(frommail);
		email.setId(id);
		
		// 加入黑名单
		if(checkbox1 != null){
			email.setbInsertSpamList(1);
		}
		//将历史来信移到垃圾箱
		if(checkbox2 != null){
			email.setCheckboxs(1);
		}
		
		// 执行sql语句
		emailService.updateReport(email);
		
		return "success";
	}
	
	// 撤回邮件
	public String RecallMail() throws Exception
	{
		String id = ServletActionContext.getRequest().getParameter("emailid");
		String tomail = ServletActionContext.getRequest().getParameter("tomail");
		
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		
		String strHost= (String)ServletActionContext.getRequest().getSession().getAttribute("host");
		
		// 判断这封邮件的状态
		List<Email> maillist=new ArrayList<Email>();
		maillist=emailService.SelectRecallMail(iid);
		// 查看邮件是否都是发送到本服务器的
	
		String mail[]=tomail.split(";");
		for(String str:mail)
		{
			int index1=str.indexOf("@");
			int index2=str.indexOf(">");
			if(index1!= -1 && index2!=-1)
			{
				String host=str.substring(index1+1,index2);
				if(strHost.equals(host) == false)
				{
					// 撤销的邮件收件人有不是本服务器的
					ServletActionContext.getResponse().getWriter().print(3);
					return null;
				}
			}
		}
	
		
		if(maillist.size() ==0)
		{
			// 邮件已经不存在
			ServletActionContext.getResponse().getWriter().print(1);
		}else if(maillist.get(0).getState() !=0 )
		{
			// 邮件已经被查看
			ServletActionContext.getResponse().getWriter().print(2);
		}else
		{
			// 撤回这封邮件
			emailService.Recall(iid);
			
			ServletActionContext.getResponse().getWriter().print(0);
		}
		
		return null;
	}
	
	//邮件详情页编辑操作（如移动到已删除 收件箱 发件箱 草稿箱等）
	public String EditInfoMail(){		
		HttpServletRequest request = ServletActionContext.getRequest();		
		User user= (User)request.getSession().getAttribute("user");
		Object host = request.getSession().getAttribute("host");
		String edittype = request.getParameter("edittype");
		String [] ids = request.getParameterValues("id");
		String [] mailids = request.getParameterValues("mailids");
		String ftype = request.getParameter("ftype");
		String eid = request.getParameter("eid");
		int tag = 0 ;
		
		if(ftype.equals("1")){	//收件箱
			tag = 1;
		}else if(ftype.equals("2")){	//已发送
			tag =2;
		}else if(ftype.equals("0")){	//草稿箱
			tag=0;
		}
		int iid = 0 ;
		if(eid!=null&&!eid.equals("")){
			iid = Integer.parseInt(eid);
		}
		User user1 = new User();
		user1.setId(iid);
		user1.setUsername("<"+user.getUsername()+"@"+host+">");
		user1.setEnd(tag);
		Integer emailid = null;
		emailService.getNextEmail(user1);
		if(emailid==null){
			emailid = emailService.getPreEmail(user1);
		}
		if(edittype.equals("2")){
			if(ftype.equals("0")){
				emailService.deleteDraftsMail(ids);
			}else if(ftype.equals("1")){
				emailService.deleteReciveMail(ids);
			}else if(ftype.equals("2")){
				emailService.deleteSendMail(ids);
			}else if(ftype.equals("4")){
				emailService.deleteDelMail(ids,mailids);
			}
		}else if(edittype.equals("-3")){
			int state = 1;			
			emailService.updateMeilState(ids,state);
		}else if(edittype.equals("-4")){
			int state = 0;
			emailService.updateMeilState(ids, state);
		}else{
			if(ftype.equals("1")){
				emailService.editReciveMail(ids);
			}else if(ftype.equals("2")){
				emailService.editSendMail(ids);
			}
		}
		
		ServletActionContext.getRequest().setAttribute("eid",emailid);
		ServletActionContext.getRequest().setAttribute("ftype",ftype);
		if(emailid==null){
			if(ftype.equals("1")){
				return "list1";
			}else if(ftype.equals("2")){
				return "list2";
			}else if(ftype.equals("0")){
				return "list3";
			}else if(ftype.equals("4")){
				return "list4";
			}
		}
		if(edittype.equals("-3") || edittype.equals("-4")){
			return "list1";//跳转到 getMail.html
		}
		return "success";
	}

	//附件下载
	public String download() throws IOException{
		HttpServletResponse response= ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String filename = request.getParameter("filenames");
		String file = request.getParameter("files");
		try{
			response.reset();
			response.setContentType("application/octet-stream");
	        response.setContentType("application/OCTET-STREAM;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String( filename.getBytes("gb2312"), "ISO8859-1" ) );
			File files = new File(file);
			if(files.exists()){
				FileInputStream fis = new FileInputStream(files);
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) != -1) {
				     out.write(buffer, 0, len);
				     out.flush();
				}
			    out.close();
			    fis.close();
			}
		}catch(java.lang.IllegalStateException e){}
		return null;
	}
	
	//转发、回复邮件
	public String ReWrite() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
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
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
		
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
		request.setAttribute("grouplist",grouplist);
		List<UGroup> ugrouplist = new ArrayList<UGroup>();
		if(user!=null){
			ugrouplist = uGroupService.getUGroupList(user.getId());
			request.setAttribute("ugrouplist",ugrouplist);
		}
		String cids = request.getParameter("cids");
		Object host = request.getSession().getAttribute("host");
		String showstr = "";
		String eaddr = "";

		if(cids!=null&&!cids.equals("")){
			String [] cidss = cids.split(",");
			if(cidss!=null&&cidss.length>0){
				for(int i=0;i<cidss.length;i++){
					String [] cidsss = cidss[i].split("_");
					if(cidsss!=null&&cidsss.length>0){
						showstr+=cidsss[0]+"<"+cidsss[1]+"@"+host+">;";
						eaddr+=cidsss[1]+",";
					}
				}
			}
		}
		request.setAttribute("showstr", showstr);
		request.setAttribute("eaddr", eaddr);
		
		List<Contact> contactList = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setUserid(user.getId());
		// 手动设置查询范围
		contact.setStart(0);
		contact.setEnd(1000);
		
		contactList = contactService.getContactList(contact);
		request.setAttribute("contactList", contactList);
		
		String cidss = "";
		for(Contact contacts:contactList){
			cidss+=contacts.getId()+",";
		}
		if(!cidss.equals("")){
			cidss = cidss.substring(0,cidss.length()-1);
			List<Cong> conglist = new ArrayList<Cong>();
			conglist = congService.getCongListByCids(cidss);
			request.setAttribute("conglist", conglist);
		}
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		if(type.equals("1")){
			// 给回复的内容添加源邮件信息
			String content="<br><br><br>------------------ 原始邮件 ------------------<br>";
			content+="发件人:"+email.getFrommail().replace("<", "&lt; ").replace(">", "&gt; ")+"<br>"
			+"发送时间: "+email.getTime()+"<br>"
			+"收件人:"+email.getToname().replace("<", "&lt; ").replace(">", "&gt; ")+"<br>"
			+"主题："+email.getTitle()+"<br>";
			
			if(email.getFrommail()!=null&&!email.getFrommail().equals("")){
				email.setToname(email.getFromname()+email.getFrommail());
			}else{
				email.setToname("");
			}
			email.setId(0);
			email.setFile("");
			email.setFilename("");
			email.setTitle("回复："+email.getTitle());
			email.setContent(content+email.getContent()+"<br><br><br>");
			email.setBcc("");
			email.setCopyto("");
		}else if(type.equals("2")){
			email.setId(0);
			email.setToname("");
			email.setCopyto("");
			email.setBcc("");
		}
		
		String strFile="";
		
		if(email.getFile()!=null&&!email.getFile().equals("")){
			List<Files> filelist = new ArrayList<Files>();
			String [] files = email.getFile().split("\\|");
			if(files!=null&&files.length>0){
				for(String file : files){
					Files ff = new Files();
					String [] filess = file.split("\\*");
					if(filess!=null&&filess.length>0&&filess[0]!=null&&!filess[0].equals("")&&filess[1]!=null&&!filess[1].equals("")){
						
						// 文件拷贝
						String path=ServletActionContext.getRequest().getRealPath("/").replace("\\", "/");
						
						String targetFileName  ="upload/"+ UploadAction.uuidFileName(filess[0]);//生成随机新文件名
						path+=targetFileName;

						try {
							FileInputStream in = new FileInputStream(filess[1]);
							FileOutputStream out = new FileOutputStream(path);
							byte[] buffer = new byte[2097152];
							while (true) {
								int ins = in.read(buffer);
								if (ins == -1) {
									in.close();
									out.flush();
									out.close();
								} else
									out.write(buffer, 0, ins);
							}
						} catch (Exception e) {}
						
						strFile+=filess[0]+"*"+targetFileName+"|";					
						ff.setFile(targetFileName);
						ff.setFilename(filess[0]);
						filelist.add(ff);
						
					}
				}
				ServletActionContext.getRequest().setAttribute("filelist",filelist);
				ServletActionContext.getRequest().setAttribute("filelistsize",filelist.size());
			}
		}else {
			ServletActionContext.getRequest().setAttribute("filelistsize","0");
		}
		email.setFile(strFile);
		
		request.setAttribute("email",email);
		return "success";
	}
	//草稿箱 ‘编辑’ 邮件
	public String ReEditMail() throws Exception{
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
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
		Member member= (Member)request.getSession().getAttribute("member");
		if(user==null&&member!=null){
			user = new User();
			user.setId(-1);
		}
		
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
		request.setAttribute("grouplist",grouplist);		
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		String eid = request.getParameter("eid");
		notice = emailService.getNotice(eid);
		request.setAttribute("notice",notice);
		return "success";
	}
	
	//定时发送邮件
	public String dsSendMail() throws Exception {
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
		request.setAttribute("unitlist",unitlist);
		
		User user= (User)request.getSession().getAttribute("user");
		
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
		request.setAttribute("grouplist",grouplist);
		List<UGroup> ugrouplist = new ArrayList<UGroup>();
		if(user!=null){
			ugrouplist = uGroupService.getUGroupList(user.getId());
			request.setAttribute("ugrouplist",ugrouplist);
		}
		String cids = request.getParameter("cids");
		Object host = request.getSession().getAttribute("host");
		String showstr = "";
		String eaddr = "";

		if(cids!=null&&!cids.equals("")){
			String [] cidss = cids.split(",");
			if(cidss!=null&&cidss.length>0){
				for(int i=0;i<cidss.length;i++){
					String [] cidsss = cidss[i].split("_");
					if(cidsss!=null&&cidsss.length>0){
						showstr+=cidsss[0]+"<"+cidsss[1]+"@"+host+">;";
						eaddr+=cidsss[1]+",";
					}
				}
			}
		}
		request.setAttribute("showstr", showstr);
		request.setAttribute("eaddr", eaddr);
		
		List<Contact> contactList = new ArrayList<Contact>();
		Contact contact = new Contact();
		contact.setUserid(user.getId());
		contactList = contactService.getContactList(contact);
		request.setAttribute("contactList", contactList);
		
		String cidss = "";
		for(Contact contacts:contactList){
			cidss+=contacts.getId()+",";
		}
		if(!cidss.equals("")){
			cidss = cidss.substring(0,cidss.length()-1);
			List<Cong> conglist = new ArrayList<Cong>();
			conglist = congService.getCongListByCids(cidss);
			request.setAttribute("conglist", conglist);
		}
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		
		EmailDrafts emailDrafts = new EmailDrafts();
		
		emailDrafts.setAttachFile("");
		emailDrafts.setAttachPath(email.getFile());
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time1 = sdf.format(date);
		email.setTime(time1);
		email.setType("0");
		emailDrafts.setTitle(email.getTitle());
		emailDrafts.setContent(email.getContent());
		emailDrafts.setTime(time1);
		
		List<String> recivelist = new ArrayList<String>();
		List<String> copytolist = new ArrayList<String>();
		List<String> bcclist = new ArrayList<String>();
		List<String> alllist = new ArrayList<String>();
		String types = "";
		if(email.getToname()!=null&&!email.getToname().equals("")){
			String[] enames = email.getToname().split(";");
			if(enames!=null&&enames.length>0){
				for(int i=0;i<enames.length;i++){
					recivelist.add(enames[i].replace("<","&lt;").replace(">","&gt;"));
					alllist.add(enames[i].replace("<","&lt;").replace(">","&gt;"));
					if(enames[i].indexOf("<")<0){
						types+="<"+enames[i]+">2;";
						email.setToname(email.getToname().replace(enames[i], "&lt;"+enames[i]+"&gt;").replace("<","&lt;").replace(">","&gt;"));
					}else{
						types+=enames[i]+"2;";
					}
				}
			}
		}
		if(email.getCopyto()!=null&&!email.getCopyto().equals("")){
			String[] cnames = email.getCopyto().split(";");
			if(cnames!=null&&cnames.length>0){
				for(int i=0;i<cnames.length;i++){
					copytolist.add(cnames[i].replace("<","&lt;").replace(">","&gt;"));
					alllist.add(cnames[i].replace("<","&lt;").replace(">","&gt;"));
					if(cnames[i].indexOf("<")<0){
						types+="<"+cnames[i]+">2;";
						email.setCopyto(email.getCopyto().replace(cnames[i], "&lt;"+cnames[i]+"&gt;").replace("<","&lt;").replace(">","&gt;"));
					}else{
						types+=cnames[i]+"2;";
					}
				}
			}
		}
		if(email.getBcc()!=null&&!email.getBcc().equals("")){
			String[] bnames = email.getBcc().split(";");
			if(bnames!=null&&bnames.length>0){
				for(int i=0;i<bnames.length;i++){
					bcclist.add(bnames[i].replace("<","&lt;").replace(">","&gt;"));
					alllist.add(bnames[i].replace("<","&lt;").replace(">","&gt;"));
					if(bnames[i].indexOf("<")<0){
						types+="<"+bnames[i]+">2;";
						email.setBcc(email.getBcc().replace(bnames[i], "&lt;"+bnames[i]+"&gt;").replace("<","&lt;").replace(">","&gt;"));
					}else{
						types+=bnames[i]+"2;";
					}
				}
			}
		}
		email.setType(types);
		request.setAttribute("email",email);
		request.setAttribute("recivelist",recivelist);
		request.setAttribute("copytolist",copytolist);
		request.setAttribute("bcclist",bcclist);
		
		emailDrafts.setFrommail(email.getFrommail());
		emailDrafts.setTomail(email.getToname());
		emailDrafts.setBcc(email.getBcc());
		emailDrafts.setCopy(email.getCopyto());
		
		//emailService.addDraftsEmail(emailDrafts);
		String id = emailService.getEmailDraftsId();
		email.setId(Integer.parseInt(id));
		
		request.setAttribute("email",email);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件保存到草稿箱", "", "", "","",email.getTitle(),"");
		
		String time = request.getParameter("mailtimes");
		String dstime = request.getParameter("dstime");
		request.setAttribute("dstime",dstime);
		
		CustomJob job = new CustomJob();
		job.setJobId("sendMail"+time1);
		job.setJobGroup("sendMail_group"+time1);
		job.setJobName("定时发送邮件");
		job.setMemos("定时发送邮件");
		job.setCronExpression(time); 
		job.setStateFulljobExecuteClass(QSendMail.class);
		JobDataMap paramsMap = new JobDataMap();
		paramsMap.put("userService",userService);
		paramsMap.put("emailService",emailService);
		paramsMap.put("user",request.getSession().getAttribute("user"));
		paramsMap.put("fileurl",request.getRealPath("/").replace("\\", "/"));
		paramsMap.put("eid",email.getId());
		paramsMap.put("JobId","sendMail"+time1);
		paramsMap.put("JobGroup","sendMail_group"+time1);
		QuartzManager.enableCronSchedule(job, paramsMap, true);
		
		return "success";
	}
	
	//编辑邮件
	public String doEditEmail(){
		
		emailService.editEmail(email);
		return "success";
	}
	
	public String delNews(){
		String id = ServletActionContext.getRequest().getParameter("id");
		emailService.delNews(id);
		return "success";
	}
	


	/** 获取垃圾箱所有垃圾记录 */
	public String getRubMail(){		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");//获取登录名
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());//查询所有标签
		List<ETag> etaglist = new ArrayList<ETag>();
		etaglist = eTagService.getETagListByUser(user.getId());
		if(etaglist!=null&&etaglist.size()>0){
			for(ETag et : etaglist){
				for(Tag ta : taglist){
					if(ta.getId()==et.getTid()){
						et.setName(ta.getName());
					}
				}
			}
		}
		request.setAttribute("taglist", taglist);
		request.setAttribute("etaglist", etaglist);
		Object hosts = request.getSession().getAttribute("host");
		//所有垃圾记录
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		user1.setName("<admin@"+hosts+">");
		user1.setState(4);
		
		List<Email> emaillist = new ArrayList<Email>();
		
		int totalsize = emailService.getRubMailCount(user1);//获取总记录
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 15 ;
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
			if(pagenow>pagenum){
				pagenow = pagenum;
			}
			user1.setStart(pagesize*pagenow-pagesize);
			user1.setEnd(pagesize);
			emaillist = emailService.getRubMail(user1);
			if(emaillist!=null&&emaillist.size()>0){
				for(Email email : emaillist){
					
					if(email.getFrommail()!=null&&!email.getFrommail().equals("")){
						if(email.getFrommail().indexOf("@") != -1)
						{
							email.setFrommail(email.getFrommail().substring(1, email.getFrommail().indexOf("@")));
						}
						
						email.setFrommail(email.getFrommail().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
						
						MailData data = emailService.getRecvMail(email.getMailid());
						if(data !=null)
							email.setTitle(data.getSubject());
						
					}
					if(email.getTitle()!=null&&!email.getTitle().equals("")){
						email.setTitle(email.getTitle().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
					}
				}
				request.setAttribute("emaillist",emaillist);
				Page page = new Page();
				page.setPagenow(pagenow);
				page.setPagesize(pagesize);
				page.setTotalsize(totalsize);
				request.setAttribute("pagebar",page.pagebar3());
				request.setAttribute("pagenow",pagenow);
				request.setAttribute("pagenum",pagenum);
			}
		}
		request.setAttribute("totalsize",totalsize);
		return "success";		
	}
	
	/**垃圾箱邮件删除*/
	public String editRubMail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		Object host = request.getSession().getAttribute("host");
		String edittype = request.getParameter("edittype");
		String [] ids = request.getParameterValues("id");
		String [] types = request.getParameterValues("types");
		String [] tomails = request.getParameterValues("tomails");
		String tid = request.getParameter("tid");
		if(edittype.equals("2")){
			emailService.deleteDEmails(ids,tomails);
		}else if(tid.equals("-3")){
			int state = 1;			
			emailService.updateMeilState(ids,state);
		}else if(tid.equals("-4")){
			int state = 0;
			emailService.updateMeilState(ids, state);
		}else{
			int state = 4 ;
			if(edittype.equals("4")){
				state=2;
			}else if(edittype.equals("5")){
				state=1;
			}else if(edittype.equals("6")){
				state=3;
			}else if(edittype.equals("7")){
				state=5;
			}
			emailService.editFEmails(ids,state);
		}
		getRubMail();
		return "success";		
	}
	
	//这不是垃圾
	public String noRubMail(){
		
		Email email = new Email();
		HttpServletRequest request = ServletActionContext.getRequest();	
		User user= (User)request.getSession().getAttribute("user");
		String tomail = user.getUsername();//获取收件人
		tomail = "<"+tomail+"@"+request.getSession().getAttribute("host")+">";
		
		String ids[] = request.getParameterValues("id");//获取所以不是垃圾邮件Id		
		for(int i=0; i<ids.length; i++){			
			try {
				email = emailService.getEmail(ids[i]);
				String frommail = email.getFrommail();//获取发件人
				int n = frommail.indexOf('<');
				int j = frommail.indexOf('>');				
				if(n>0 && j>0){
					frommail = frommail.substring(n,j+1);
				}
				int id = Integer.parseInt(ids[i]);
				
				Email email1 = new Email();
				email1.setTomail(tomail);
				email1.setFrommail(frommail);
				email1.setId(id);
				emailService.delReport(email1);//删除用户黑名单			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		emailService.noRubMail(ids);//修改邮件状态
		getRubMail();
		return "success";
	}
	
	public String TrimMail(String mail){
		String strMail="";
		// 对输入不规范的格式进行自动修正
		if(mail!=null&& !mail.equals("")){
			String[] enames = mail.split(";");
			if(enames!=null&&enames.length>0){
				for(int i=0;i<enames.length;i++){
					if(enames[i].indexOf("<")<0){
						strMail+="<"+enames[i]+">;";
					}else{
						strMail+=enames[i]+";";
					}
				}
			}
		}
		
		return strMail;
	}
	
}