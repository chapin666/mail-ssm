package mail.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.Page;
import mail.bean.Units;
import mail.bean.User;
import mail.service.UnitsService;
import mail.service.UserService;

@Component("unitsAction")
@Scope("prototype")
public class UnitsAction {
	@Autowired
	private UnitsService unitsService ;
	@Autowired
	private UserService userService ;
	
	private Units units ;
	
	public String checkNameExist() throws Exception{
		
		String name = ServletActionContext.getRequest().getParameter("name").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		units = unitsService.checkNameExist(name);
		
		if(units!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}
	
	public String addUnits(){
		String pid = ServletActionContext.getRequest().getParameter("pid");
		ServletActionContext.getRequest().setAttribute("pid",pid);
		return "success";
	}
	
	public String doAddUnits(){
		
		unitsService.addUnits(units);
		return "success";
	}
	
	public String editUnits(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		units = unitsService.getUnits(iid);
		ServletActionContext.getRequest().setAttribute("units", units);
		return "success" ;
	}
	
	public String doEditUnits(){
		
		unitsService.editUnits(units);
		return "success";
	}
	
	public String getUnitsList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Units> unitlist = new ArrayList<Units>();
		unitlist = unitsService.getUnitsList(units);
		request.setAttribute("units", units);	
		request.setAttribute("unitlist", unitlist);
		return "success";
	}
	//获取单位部门下的所有用户
	public String getUserByUnits(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id1 = request.getParameter("id");
		Units units = new Units();
		int id=0;
		if(id1!=null&&!id1.equals("")){
			id = Integer.parseInt(id1);
		}
		units = unitsService.getUnits(id);
		request.setAttribute("uname", units.getName());
		request.setAttribute("unitid", units.getPid());
		request.setAttribute("id",id);
		final List<String> uidss = new ArrayList<String>();
		List<String> uidsss = new ArrayList<String>();
		String uids = "" ;
		uidss.add(id1);
		unitids(uidss,uidsss);
		if(uidss!=null&&uidss.size()>0){
			for(String uid:uidss){
				uids+="'"+uid+"',";
			}
			uids = uids.substring(0,uids.length()-1);
		}
		List<User> userlist = new ArrayList<User>();
		
		int totalsize = userService.getUsernameCountByUnits(uids);
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow1");
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

			Units uid=new Units();
			uid.setName(uids);
			uid.setStart(pagesize*pagenow-pagesize);
			uid.setEnd(pagesize);
			
			userlist = userService.getUsernameByUnits(uid);
			
			String usernames = "";
			if(userlist!=null&&userlist.size()>0){
				for(User ul : userlist){
					usernames+=ul.getName()+"_"+ul.getUsername()+",";
				}
				usernames = usernames.substring(0,usernames.length()-1);
			}
			request.setAttribute("usernames", usernames);
			
			request.setAttribute("pagenow1",pagenow);
			request.setAttribute("pagesize",pagesize);
			request.setAttribute("totalsize",totalsize);
			request.setAttribute("pagenum",pagenum);
		}
		
		return "success" ;
	}
	
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
}