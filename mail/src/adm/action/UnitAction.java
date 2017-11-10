package adm.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mail.bean.Config;
import mail.service.ContactService;
import mail.service.EmailService;
import mail.service.GroupService;
import mail.service.TagService;
import mail.service.UGroupService;
import mail.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import util.LogUtil;
import util.Page;
import util.Upload;
import adm.bean.Groups;
import adm.bean.Guss;
import adm.bean.Unit;
import adm.bean.Users;
import adm.service.GroupsService;
import adm.service.UnitService;
import adm.service.UsersService;

@Component("unitAction")
@Scope("prototype")
public class UnitAction {
	private static final Log logger = LogFactory.getLog(UnitAction.class);
	@Autowired
	private UnitService unitService ;
	@Autowired
	private UsersService usersService ;
	@Autowired
	private GroupsService groupsService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private GroupService groupService ;
	@Autowired
	private UGroupService ugroupService ;
	@Autowired
	private TagService tagService ;
	@Autowired
	private ContactService contactService ;
	@Autowired
	private EmailService emailService ;
	
	private Unit unit ;
	private File qtlogo;
	private File htlogo;
	private List<Unit> unitjson;
	
	//后台检测单位部门名称是否存在
	public String checkNameExist() throws Exception{
		
		String name = ServletActionContext.getRequest().getParameter("name").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		unit = unitService.checkNameExist(name);
		
		if(unit!=null){
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}

	public String toUnit(){
		return "success";
	}
	
	public String addUnit(){
		String pid = ServletActionContext.getRequest().getParameter("pid");
		ServletActionContext.getRequest().setAttribute("pid",pid);
		return "success";
	}
	
	public String doAddUnit(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String unitid = request.getParameter("unitid");
		String unitname = request.getParameter("unitname");
		unit = new Unit();
		unit.setPid((null == unitid || unitid.equals("") ? 1 : Integer.parseInt(unitid)));
		unit.setName(unitname);
		unitService.addUnit(unit);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "添加部门", "", "", "","",unit.getName(),"");
		return "success";
	}
	
	public String editUnit(){
		
		String id = ServletActionContext.getRequest().getParameter("id");
		int iid = 0 ;
		if(id!=null&&!id.equals("")){
			iid = Integer.parseInt(id);
		}
		unit = unitService.getUnit(iid);
		ServletActionContext.getRequest().setAttribute("unit", unit);
		return "success" ;
	}
	
	public String doEditUnit(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String unitid = request.getParameter("unitid");
		String unitname = request.getParameter("unitname");
		unit = new Unit();
		unit.setId((null == unitid || unitid.equals("") ? 1 : Integer.parseInt(unitid)));
		unit.setName(unitname);
		unitService.editUnit(unit);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "编辑部门", "", "", "","",unit.getName(),"");
		return "success";
	}
	
	//获取单位部门列表
	public String getUnitList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Unit> unitlist = new ArrayList<Unit>();
		unitlist = unitService.getUnitList(unit);
		request.setAttribute("unit", unit);	
		request.setAttribute("unitlist", unitlist);
		return "success";
	}
	
	//根据部门获取邮箱用户
	public String getUserByUnit(){		
		List<Unit> unitlist = new ArrayList<Unit>();
		Unit unit = new Unit();
		unitlist = unitService.getUnitList(unit);
		if(unitlist!=null&&unitlist.size()>0){
			for(Unit u:unitlist){
				if(u.getId()==1){
					u.setPid(-1);
				}
			}
		}
		

		ServletActionContext.getRequest().setAttribute("unitlist",unitlist);
		int stopState = 0;//禁用用户
		stopState = unitService.getState();
		ServletActionContext.getRequest().setAttribute("stopState",stopState);
		
		List<Groups> listgroups = groupsService.listGroups("");
		ServletActionContext.getRequest().setAttribute("listgroups",listgroups);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String id1 = request.getParameter("id");
		int id=1;
		if(id1 == null || id1.equals("")){
			id1 = "1";
		}
		id = Integer.parseInt(id1);
		unit = unitService.getUnit(id);
		ServletActionContext.getRequest().setAttribute("unit", unit);
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
		Users users = new Users();
		users.setName(uids);
		String searchname = request.getParameter("searchname");
		if(null == searchname){
			request.setAttribute("searchname", "搜索成员");
		}else if("搜索成员".equals(searchname)){
			request.setAttribute("searchname", "搜索成员");
			searchname = null;
		}else{
			request.setAttribute("searchname", searchname);
		}
		users.setUsername(searchname);
		List<Users> userlist = new ArrayList<Users>();
		int totalsize = 0;
		
		String disable = request.getParameter("disable");
		if(disable != null){
			totalsize = stopState;
		}else{
			totalsize = usersService.getSize(users);
		}
		
		
		
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
			users.setStart(pagesize*pagenow-pagesize);
			users.setEnd(pagesize);
			if(disable != null){
				userlist = usersService.getUserByStateUnit(users);
				request.setAttribute("disable",2);
			}else {
				userlist = usersService.getUserByUnit(users);
				request.setAttribute("disable",0);
			}			
			
			if(userlist != null){
				request.setAttribute("userlist", userlist);
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
		uiid = unitService.getIdByPids(uids);
		if(uiid!=null&&uiid.size()>0){
			for(String uiids:uiid){
				uidss.add(uiids);
			}
			unitids(uidss,uiid);
		}
		return uidss ;
	}
	
	public String editState()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String userunid = request.getParameter("unid");
		String[] ids = request.getParameterValues("unitids");
		unitService.editState(ids, Integer.parseInt(userunid));
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "修改人员状态", "", "", "","","修改人员状态","");
		
		return "success";
	}
	private int pagenow;
	
	public int getPagenow() {
		return pagenow;
	}

	public void setPagenow(int pagenow) {
		this.pagenow = pagenow;
	}

	public String deleteUsers()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("unitids");
		
		// 删除用户邮件信息信息
		unitService.deleteUserAllData(ids);
		// 删除用户
		unitService.deleteUser(ids);
		
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "删除人员", "", "", "","","删除人员","");
		return "success";
	}

	public String editUnitId()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String userunid = request.getParameter("unid");
		String[] ids = request.getParameterValues("unitids");
		unitService.editUnitId(ids, Integer.parseInt(userunid));
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "修改人员部门", "", "", "","","修改人员部门","");
		return "success";
	}

	public String editGroups()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String userunid = request.getParameter("unid");
		String[] ids = request.getParameterValues("unitids");
		Guss guss = new Guss();
		guss.setGid(Integer.parseInt(userunid));
		String result = "";
		for(int i=0; i<ids.length; i++){
			guss.setUserid(Integer.parseInt(ids[i]));
			Guss g = groupsService.getGUId(guss);
			if(null == g){
				if("".equals(result)){
					result = ids[i];
				}else{
					result += "," +ids[i];
				}
			}
		}
		if(!"".equals(result)){
			groupsService.addGroupsUser(Integer.parseInt(userunid), result.split(","));
			LogUtil.addLog(ServletActionContext.getRequest(), logger, "人员添加到群组", "", "", "","","人员添加到群组","");
		}
		return "success";
	}
	
	public String deleteUnit()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		unitService.deleteUnit(Integer.parseInt(id));
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "删除部门", "", "", "","","删除部门","");
		return "success";
	}
	
	public String toUpdateHost()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Config> configList = new ArrayList<Config>();
		int num1 = unitService.getUnitSize();
		int num2 = userService.getUserSize();
		int num3 = groupService.getGroupSize();
		request.setAttribute("num1",num1);
		request.setAttribute("num2",num2);
		request.setAttribute("num3",num3);
		configList = unitService.getDoMainName();
		request.setAttribute("configList",configList);
		return "updateHost";
	}

	//修改域名
	public String updateHost()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Config> configList = new ArrayList<Config>();
		String host = request.getParameter("host");
		String domainid = request.getParameter("domainid");
		Config config = new Config();
		String edithost = request.getParameter("edithost");//判断 0：添加,1：修改
		try {			
			configList = unitService.getDoMain("domain");
			if(configList!=null){
				config.setValue(host);
				if(edithost.equals("1")){
					config.setId(Integer.parseInt(domainid));
					unitService.updateDoMain(config);//修改域名
				}else{					
					unitService.insertDoMain(config);//插入域名
				}
				
			}
//			else{
//				config = new Config();
//				config.setName("domain");
//				config.setValue(host);
//				unitService.addDoMain(config);
//			}
			request.getSession().setAttribute("host",host);
//			ugroupService.delAll();
//			contactService.delAll();
//			emailService.delAll();
//			tagService.delAll();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		toUpdateHost();
		return "updateHost";
	}

	
	//删除域名
	public String delHost(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String domainid = request.getParameter("domainid");
		int id = Integer.parseInt(domainid);
		unitService.delHost(id);
		toUpdateHost();
		return "success";
	}
	
	
	//修改前台logo
	public String updateQTLogo()
	{
		String path = this.getClass().getResource("/").getPath();
		if(path!=null&&!path.equals("")){
			path = path.replace("/WEB-INF/classes/","");
		}
		path +="/mail/images/gray/logo.png";
		String src = ServletActionContext.getRequest().getParameter("src");
		if(src!=null&&!src.equals("")){
			File file = new File(src);
			Upload.UploadFile(path,file);
		}else{
			if(qtlogo != null && !qtlogo.equals("")){
				File ff = new File(path);
				if(!ff.exists()){
					ff.mkdirs();
				}
				Upload.UploadFile(path,qtlogo);
			}
		}
		return "success";
	}

	//修改后台logo
	public String updateHTLogo()
	{
		String path = this.getClass().getResource("/").getPath();
		if(path!=null&&!path.equals("")){
			path = path.replace("/WEB-INF/classes/","");
		}
		path +="/adm/images/index/logo.png";
		String src = ServletActionContext.getRequest().getParameter("src");
		if(src!=null&&!src.equals("")){
			File file = new File(src);
			Upload.UploadFile(path,file);
		}else{
			if(htlogo != null && !htlogo.equals("")){
				File ff = new File(path);
				if(!ff.exists()){
					ff.mkdirs();
				}
				Upload.UploadFile(path,htlogo);
			}
		}
		return "success";
	}
	//根据父级id获取子部门
	public String getUnitByJson(){
		
		String pid = ServletActionContext.getRequest().getParameter("pid");
		unitjson = unitService.getUnitByJson(pid);
		return "success" ;
	}

	//根据部门id获取部门父id
	public String getUnitPidById() throws IOException{
		
		String id = ServletActionContext.getRequest().getParameter("id");
		unit = unitService.getUnit(Integer.parseInt(id));
		if(unit!=null){
			ServletActionContext.getResponse().getWriter().print(unit.getPid());
		}else{
			ServletActionContext.getResponse().getWriter().print(-1);
		}
		return null ;
	}
	
	/**注册信息*/	
	public String regeditInfo(){
		String authorcode = "";//授权信息
		String maxuser = "";//最大用户数
		String softenddate = "";//到期
		String machinecode = "";//机器码
		String softversion = "";//版本号
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Config> list = new ArrayList<Config>();
		list = unitService.regeditInfo();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getName().equals("authorcode")){
				authorcode = list.get(i).getValue();
			}
			if(list.get(i).getName().equals("maxuser")){
				maxuser = list.get(i).getValue();				
			}
			if(list.get(i).getName().equals("softenddate")){
				softenddate = list.get(i).getValue();
			}
			if(list.get(i).getName().equals("machinecode")){
				machinecode = list.get(i).getValue();
			}
			if(list.get(i).getName().equals("softversion")){
				softversion = list.get(i).getValue();
			}
		}
		request.setAttribute("authorcode", authorcode);
		request.setAttribute("maxuser", maxuser);
		request.setAttribute("softenddate", softenddate);
		request.setAttribute("machinecode", machinecode);
		request.setAttribute("softversion", softversion);		
		return "success";
	}
	
	
	/**编辑注册信息*/
	public String editRegeditInfo(){		
		regeditInfo();		
		return "success";
	}
	
	/**修改注册信息*/
	public String updateRegeditInfo(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String authorcode = request.getParameter("authorcode");
		unitService.editRegeditInfo(authorcode);
		regeditInfo();
		return "success";
	}
	//检查域名是否存在
	public String checkHostNameExist() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String host = request.getParameter("host");
		List<Config> configList = new ArrayList<Config>();
		configList = unitService.regeditInfo();
		for (int i = 0; i < configList.size(); i++) {
			if(configList.get(i).getName().equals("domain") && configList.get(i).getValue().equals(host)){
				ServletActionContext.getResponse().getWriter().print(1);
			}
		}
		return null;
	}
	
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public File getQtlogo() {
		return qtlogo;
	}

	public void setQtlogo(File qtlogo) {
		this.qtlogo = qtlogo;
	}

	public File getHtlogo() {
		return htlogo;
	}

	public void setHtlogo(File htlogo) {
		this.htlogo = htlogo;
	}

	public List<Unit> getUnitjson() {
		return unitjson;
	}

	public void setUnitjson(List<Unit> unitjson) {
		this.unitjson = unitjson;
	}
}