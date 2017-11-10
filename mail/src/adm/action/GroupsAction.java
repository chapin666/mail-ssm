package adm.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mail.bean.Units;
import mail.bean.User;
import mail.service.UnitsService;
import mail.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import adm.bean.Groups;
import adm.bean.Member;
import adm.bean.Unit;
import adm.bean.UnitUser;
import adm.bean.Users;
import adm.service.GroupsService;
import util.LogUtil;
import util.Page;

@Component("groupsAction")
@Scope("prototype")
public class GroupsAction {
	private static final Log logger = LogFactory.getLog(GroupsAction.class);
	@Autowired
	private GroupsService groupsService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UnitsService unitsService ;
	
	private Groups groups ;
		
	//检测邮件群组名称是否存在
	public String checkNameExist() throws Exception{
		
		String name = ServletActionContext.getRequest().getParameter("name").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		groups = groupsService.checkNameExist(name);
		
		if(groups!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}
	
	public String addGroups(){
		String pid = ServletActionContext.getRequest().getParameter("pid");
		ServletActionContext.getRequest().setAttribute("pid",pid);
		return "success";
	}
	
	public String doAddGroups(){
		groupsService.addGroups(groups);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "添加邮件群组", "", "", "","",groups.getName(),"");
		groups = groupsService.checkNameExist(groups.getName());
		HttpServletRequest request = ServletActionContext.getRequest();
		String groupuser = request.getParameter("groupuser");
		String groupunit = request.getParameter("groupunit");
		if(null != groupuser && !groupuser.equals("")){
			groupsService.addGroupsUser(groups.getId(), groupuser.split(","));
		}
		if(null != groupunit && !groupunit.equals("")){
			groupsService.addGroupsUnit(groups.getId(), groupunit.split(","));
		}
		return "success";
	}

	public String doUpdateGroups(){
		groupsService.deleteGnss(groups.getId());
		groupsService.deleteGuss(groups.getId());
		groupsService.editGroups(groups);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "修改邮件群组", "", "", "","",groups.getName(),"");
		groups = groupsService.checkNameExist(groups.getName());
		HttpServletRequest request = ServletActionContext.getRequest();
		String groupuser = request.getParameter("groupuser");
		String groupunit = request.getParameter("groupunit");
		if(null != groupuser && !groupuser.equals("")){
			groupsService.addGroupsUser(groups.getId(), groupuser.split(","));
		}
		if(null != groupunit && !groupunit.equals("")){
			groupsService.addGroupsUnit(groups.getId(), groupunit.split(","));
		}
		return "success";
	}
	
	//把联系人或部门移除出邮件群组
	public String ycGroups()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String[] unitsids = request.getParameterValues("unitsids");
		String[] usersids = request.getParameterValues("usersids");
		groupsService.deleteGroupsUnit(Integer.parseInt(id),unitsids,usersids);
		return "success";
	}
	
	//删除邮件群组
	public String deleteGroups(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		groupsService.deleteGroups(Integer.parseInt(id));
		return "success";
	}
	
	public String editGroups(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		groups = groupsService.getGroups(iid);
		ServletActionContext.getRequest().setAttribute("groups", groups);
		return "success" ;
	}
	
	public String doEditGroups(){
		
		groupsService.editGroups(groups);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "编辑邮件群组", "", "", "","",groups.getName(),"");
		return "success";
	}
	
	public String list()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		if(null == name){
			request.setAttribute("searchname", "搜索邮件群组");
		}else if("搜索邮件群组".equals(name)){
			request.setAttribute("searchname", "搜索邮件群组");
			name = null;
		}else{
			request.setAttribute("searchname", name);
		}
		String id = request.getParameter("id");
		String number = request.getParameter("number");
		if(null == number || "".equals(number)){
			number = "0";
		}
		List<Groups> list = groupsService.listGroups(name);
		request.setAttribute("list", list);
		request.setAttribute("number", number);
		groups = new Groups();
		List<UnitUser> list2 = new ArrayList<UnitUser>();
		if(list.size() > 0){
			if(null != id && !"".equals(id)){
				groups = groupsService.getGroups(Integer.parseInt(id));
			}else{
				groups = list.get(0);
			}
			int totalsize = groupsService.totalUnitUser(groups);
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
				groups.setStart(pagesize*pagenow-pagesize);
				groups.setEnd(pagesize);
				list2 = groupsService.listUnitUser(groups);
				if(list2 != null){
					Page page = new Page();
					page.setPagenow(pagenow);
					page.setPagesize(pagesize);
					page.setTotalsize(totalsize);
					request.setAttribute("pagebar",page.pagebar3());
					request.setAttribute("pagenow",pagenow);
					request.setAttribute("pagesize",pagesize);
					request.setAttribute("totalsize",totalsize);
					request.setAttribute("pagenum",pagenum);
				}
			}
		}
		request.setAttribute("list2", list2);
		return "success";
	}
	
	//进入添加邮件群组页面
	public String toAddGroup()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String number = request.getParameter("number");
		if(null == number || "".equals(number)){
			number = "0";
		}
		List<Groups> list = groupsService.listGroups("");
		request.setAttribute("list", list);
		request.setAttribute("number", number);
		
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
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		
		return "success";
	}

	//进入编辑邮件群组页面
	public String toUpdateGroup()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String number = request.getParameter("number");
		String id = request.getParameter("id");
		if(null == number || "".equals(number)){
			number = "0";
		}
		List<Groups> list = groupsService.listGroups("");
		request.setAttribute("list", list);
		request.setAttribute("number", number);
		
		groups = groupsService.getGroups(Integer.parseInt(id));
		List<Users> listGuss = groupsService.listGuss(Integer.parseInt(id));
		List<Unit> listGnss = groupsService.listGnss(Integer.parseInt(id));
		request.setAttribute("listGuss", listGuss);
		request.setAttribute("listGnss", listGnss);
		
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
		List<User> userlist = new ArrayList<User>();
		userlist = userService.getAllUser(1);
		request.setAttribute("userlist",userlist);
		
		return "success";
	}
	
	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}
}