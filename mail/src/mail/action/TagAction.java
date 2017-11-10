package mail.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import mail.bean.ETag;
import mail.bean.Tag;
import mail.bean.User;
import mail.service.ETagService;
import mail.service.TagService;
import util.LoggerUtil;

@Component("tagAction")
@Scope("prototype")
public class TagAction {
	private static final Log logger = LogFactory.getLog(TagAction.class);
	@Autowired
	private TagService tagService ;
	@Autowired
	private ETagService eTagService ;
	
	private Tag tag ;
	//判断用户标签名称是否存在
	public String checkNameExist() throws Exception{
		String name = ServletActionContext.getRequest().getParameter("name");
		String userid = ServletActionContext.getRequest().getParameter("userid");
		int userids = 0;
		if(userid!=null&&!userid.equals("")){
			userids = Integer.parseInt(userid);
		}
		tag = new Tag();
		tag.setName(name);
		tag.setUserid(userids);
		tag = tagService.checkNameExist(tag);
		
		if(tag!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}
	
	public String addTag(){
		
		return "success";
	}
	//添加标签
	public String doAddTag(){
		tagService.addTag(tag);
		tag = tagService.checkNameExist(tag);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "添加邮件标签", "", "", "","",tag.getName(),"");
		return "success";
	}
	//添加标签
	public String doAddTag6(){
		String eid = ServletActionContext.getRequest().getParameter("eid");
		String ftype = ServletActionContext.getRequest().getParameter("ftype");
		tagService.addTag(tag);
		tag = tagService.checkNameExist(tag);
		ServletActionContext.getRequest().setAttribute("eid",eid);
		ServletActionContext.getRequest().setAttribute("ftype",ftype);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "添加邮件标签", "", "", "","",tag.getName(),"");
		return "success";
	}
	
//	public String editTag(){
//		
//		String id = ServletActionContext.getRequest().getParameter("id");
//		int iid = 0 ;
//		if(id!=null&&!id.equals("")){
//			iid = Integer.parseInt(id);
//		}
//		tag = tagService.getTag(iid);
//		ServletActionContext.getRequest().setAttribute("tag", tag);
//		List<String> eidlist = new ArrayList<String>();
//		eidlist = eTagService.getEidList(id);
//		return "success" ;
//	}
	
	public String doEditTag(){
		
		tagService.editTag(tag);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "编辑联系人分组", "", "", "","",tag.getName(),"");
		return "success";
	}
	
	public String getTagList(){
		String uid = ServletActionContext.getRequest().getParameter("userid");
		int userid = 0 ;
		if(uid!=null&&!uid.equals("")){
			userid = Integer.parseInt(uid);
		}
		List<Tag> taglist = new ArrayList<Tag>();
		taglist=tagService.getTagList(userid);
		ServletActionContext.getRequest().setAttribute("taglist", taglist);
		return "success";
	}

	public String deleteTag() throws IOException{
		String id1 = ServletActionContext.getRequest().getParameter("id");
		int id = 0 ;
		if(id1!=null&&!id1.equals("")){
			id = Integer.parseInt(id1);
		}
		tagService.deleteTag(id);
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "删除联系人分组", "", "", "","","删除联系人分组","");
		ServletActionContext.getResponse().getWriter().print(1);
		return null;
	}

	//邮件绑定标签
	public String doAddETag(){
		String tid = ServletActionContext.getRequest().getParameter("tid");// 标签ID
		String[] ids = ServletActionContext.getRequest().getParameterValues("id");// 邮件ID
		String pagenow = ServletActionContext.getRequest().getParameter("pagenow");
		String issee = ServletActionContext.getRequest().getParameter("issee");
		String orbypage = ServletActionContext.getRequest().getParameter("orbypage");
		String sortorderpage = ServletActionContext.getRequest().getParameter("sortorderpage");
		//User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		//String [] frommails = ServletActionContext.getRequest().getParameterValues("frommails");
		
		String type = ServletActionContext.getRequest().getParameter("ttype");// 邮件所属邮箱
		if(tid!=null&&!tid.equals("")){
			List<ETag> eidlist = new ArrayList<ETag>();
			eidlist = eTagService.getEidList(tid);
			eTagService.addBatETag(tid,ids,eidlist,type);
		}
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件绑定标签", "", "", "","","邮件绑定标签","");
		ServletActionContext.getRequest().setAttribute("pagenow",pagenow);
		ServletActionContext.getRequest().setAttribute("issee",issee);
		ServletActionContext.getRequest().setAttribute("orbypage",orbypage);
		ServletActionContext.getRequest().setAttribute("sortorderpage",sortorderpage);
		return "success";
	}
	
	/**垃圾箱邮件绑定标签*/
	public String rubmaildoAddETag(){
		String tid = ServletActionContext.getRequest().getParameter("tid");// 标签ID
		String[] ids = ServletActionContext.getRequest().getParameterValues("id");// 邮件ID
		String pagenow = ServletActionContext.getRequest().getParameter("pagenow");//当前页
		
		//User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		//String [] frommails = ServletActionContext.getRequest().getParameterValues("frommails");
		
		String type = "1";// 邮件所属邮箱
		if(tid!=null&&!tid.equals("")){
			List<ETag> eidlist = new ArrayList<ETag>();
			eidlist = eTagService.getEidList(tid);
			eTagService.rubmaildoAddETag(tid,ids,eidlist,type);
		}
		//LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件绑定标签", "", "", "","","邮件绑定标签","");
		ServletActionContext.getRequest().setAttribute("pagenow",pagenow);
		return "success";
	}
	
	/**删除箱邮件绑定标签*/
	public String deldoAddETag(){
		String tid = ServletActionContext.getRequest().getParameter("tid");
		String[] ids = ServletActionContext.getRequest().getParameterValues("id");
		String pagenow = ServletActionContext.getRequest().getParameter("pagenow");//当前页
		//User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		//String [] frommails = ServletActionContext.getRequest().getParameterValues("frommails");		
		//String type = ServletActionContext.getRequest().getParameter("ttype");
		
		String[] boxtype = ServletActionContext.getRequest().getParameterValues("boxtype");//判断在那个想 1：收件箱 2：发件箱
		
		if(tid!=null&&!tid.equals("")){
			List<ETag> eidlist = new ArrayList<ETag>();
			eidlist = eTagService.getEidList(tid);
			eTagService.deladdBatETag(tid,ids,eidlist,boxtype);
		}
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件绑定标签", "", "", "","","邮件绑定标签","");
		ServletActionContext.getRequest().setAttribute("pagenow",pagenow);
		return "success";
	}
	
	public String doAddETag6(){
		String tid = ServletActionContext.getRequest().getParameter("tid");
		String eid = ServletActionContext.getRequest().getParameter("eid");
		String ftype = ServletActionContext.getRequest().getParameter("ftype");
		String[] ids = ServletActionContext.getRequest().getParameterValues("id");
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		String [] frommails = ServletActionContext.getRequest().getParameterValues("frommails");
		
		if(tid!=null&&!tid.equals("")){
			List<ETag> eidlist = new ArrayList<ETag>();
			eidlist = eTagService.getEidList(tid);
			eTagService.addBatETag(tid,ids,eidlist,ftype);
		}
		ServletActionContext.getRequest().setAttribute("eid",eid);
		ServletActionContext.getRequest().setAttribute("ftype",ftype);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件绑定标签", "", "", "","","邮件绑定标签","");
		return "success";
	}
	
	public String deleteETag(){
		
		String id = ServletActionContext.getRequest().getParameter("tid");
		String pagenow = ServletActionContext.getRequest().getParameter("pagenow");//当前页
		String issee = ServletActionContext.getRequest().getParameter("issee");
		String orbypage = ServletActionContext.getRequest().getParameter("orbypage");
		String sortorderpage = ServletActionContext.getRequest().getParameter("sortorderpage");
		
		if(id!=null&&!id.equals("")){
			eTagService.deleteETag(id);
		}
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件解绑标签", "", "", "","","邮件解绑标签","");
		ServletActionContext.getRequest().setAttribute("pagenow",pagenow);
		ServletActionContext.getRequest().setAttribute("issee",issee);
		ServletActionContext.getRequest().setAttribute("orbypage",orbypage);
		ServletActionContext.getRequest().setAttribute("sortorderpage",sortorderpage);
		return "success";
	}
	
	public String deleteETag6(){
		
		String id = ServletActionContext.getRequest().getParameter("tid");
		String eid = ServletActionContext.getRequest().getParameter("eid");
		String ftype = ServletActionContext.getRequest().getParameter("ftype");

		if(id!=null&&!id.equals("")){
			eTagService.deleteETag(id);
		}
		ServletActionContext.getRequest().setAttribute("eid",eid);
		ServletActionContext.getRequest().setAttribute("ftype",ftype);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "邮件解绑标签", "", "", "","","邮件解绑标签","");
		return "success";
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	/*//查询所有标签
	public String getTags(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//System.out.println("查询所有标签。。。。。。。。。。。。。");	
		List<Tag> getTags = new ArrayList<Tag>();
		getTags = tagService.getTags(tag);
		request.setAttribute("getTags", getTags);
		return "success";
	}*/
}