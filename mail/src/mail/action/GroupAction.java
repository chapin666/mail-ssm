package mail.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.JsonUtil;
import mail.bean.Group;
import mail.bean.Units;
import mail.bean.User;
import mail.service.GroupService;
import mail.service.UnitsService;
import mail.service.UserService;

@Component("groupAction")
@Scope("prototype")
public class GroupAction {
	@Autowired
	private GroupService groupService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private UnitsService unitsService ;
	
	private Group group ;
	//用户获取邮件群组
	public String getGroupList(){
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
		int userid = 0 ;
		if(user!=null){
			userid = user.getId();
		}
		List<Group> grouplist = new ArrayList<Group>();
		grouplist=groupService.getGroupList(userid);
		ServletActionContext.getRequest().setAttribute("grouplist", grouplist);
		return "success";
	}
	//通过邮件群组查询群组邮箱用户
	public String getUserByGroup(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id1 = request.getParameter("id");
		Group group = new Group();
		int id=0;
		if(id1!=null&&!id1.equals("")){
			id = Integer.parseInt(id1);
		}
		group = groupService.getGroup(id);
		request.setAttribute("gname", group.getName());
		List<User> userlist = new ArrayList<User>();
		List<String> unidslist = new ArrayList<String>();
		unidslist = userService.getUnids(group);
		final List<String> uidss = new ArrayList<String>();
		List<String> uidsss = new ArrayList<String>();
		String uids = "" ;
		if(unidslist!=null&&unidslist.size()>0){
			for(String unid : unidslist){
				uidss.add(unid);
				unitids(uidss,uidsss);
				if(uidss!=null&&uidss.size()>0){
					for(String uid:uidss){
						uids+="'"+uid+"',";
					}
					uidss.clear();
				}
			}
			if(uids!=null&&!uids.equals("")){
				uids = uids.substring(0,uids.length()-1);
			}
		}
		if(uids==null|| uids.equals(""))
		{
			uids="''";
		}
		
		group.setName(uids);
		
		int totalsize = userService.getUsernameCountByGroup(group);
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow2");
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

			group.setStart(pagesize*pagenow-pagesize);
			group.setEnd(pagesize);
			
			userlist = userService.getUsernameByGroup(group);
			String usernames = "";
			if(userlist!=null&&userlist.size()>0){
				for(User ul : userlist){
					usernames+=ul.getName()+"_"+ul.getUsername()+",";
				}
				usernames = usernames.substring(0,usernames.length()-1);
			}
			request.setAttribute("usernames", usernames);
			request.setAttribute("id", id);
			
			request.setAttribute("pagenow1",pagenow);
			request.setAttribute("pagesize",pagesize);
			request.setAttribute("totalsize",totalsize);
			request.setAttribute("pagenum",pagenum);
		}
		
		return "success" ;
	}
	//通过邮件群组无刷新查询群组邮箱用户
	public String getUserByGroups() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String id1 = request.getParameter("id");
		Group group = new Group();
		int id=0;
		if(id1!=null&&!id1.equals("")){
			id = Integer.parseInt(id1);
		}
		group.setId(id);
		List<User> userlist = new ArrayList<User>();
		List<String> unidslist = new ArrayList<String>();
		unidslist = userService.getUnids(group);
		final List<String> uidss = new ArrayList<String>();
		List<String> uidsss = new ArrayList<String>();
		String uids = "" ;
		if(unidslist!=null&&unidslist.size()>0){
			for(String unid : unidslist){
				uidss.add(unid);
				unitids(uidss,uidsss);
				if(uidss!=null&&uidss.size()>0){
					for(String uid:uidss){
						uids+="'"+uid+"',";
					}
					uidss.clear();
				}
			}
			if(uids!=null&&!uids.equals("")){
				uids = uids.substring(0,uids.length()-1);
			}
		}
		// 假如名称为空，添加‘’，防止sql语句出错
		if(uids == null || uids.equals(""))
		{
			uids="''";
		}
		group.setName(uids);
		userlist = userService.getUsernameByGroup(group);
		JSONObject json = null ;
		if(userlist!=null&&userlist.size()>0){
			json = JsonUtil.generate(userlist);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(json);
		return null ;
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
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}