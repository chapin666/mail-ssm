package mail.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.Config;
import mail.bean.Email;
import mail.bean.Sign;
import mail.bean.Tag;
import mail.bean.User;
import mail.service.EmailService;
import mail.service.TagService;
import mail.service.UserService;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import util.IpData;
import util.JsonUtil;
import util.LogUtil;
import util.LoggerUtil;
import util.MD5;
import util.Page;

import util.SessionManager;
import adm.bean.Unit;
import adm.service.UnitService;
import Tool.MD5Crypt;

@Component("userAction")
@Scope("prototype")
public class UserAction {
	private static final Log logger = LogFactory.getLog(UserAction.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UnitService unitService;
	@Autowired
	private TagService tagService;
	@Autowired
	private EmailService emailService;

	private User user;
	private Tag tag;
	private List<Tag> tagList;

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	// 检测邮箱用户名称是否存在
	public String checkUsernameExist() throws Exception {
		User user1 = new User();
		List<Config> configList = new ArrayList<Config>();
		String host = ServletActionContext.getRequest().getParameter(
				"domainhost");// 域名
		

		int domain = 1;
		configList = unitService.getDoMain("domain");

		if (configList != null) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getValue().equals(host)) {
					ServletActionContext.getRequest().getSession()
							.setAttribute("host", configList.get(i).getValue());// 设置域名
					domain = configList.get(i).getId();
				}
			}
		}

		String username = ServletActionContext.getRequest()
				.getParameter("username")
				.replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		user1.setDomain(domain);
		user1.setUsername(username);
		user = userService.checkUserNameExist(user1);

		if (user != null) {
			ServletActionContext.getResponse().getWriter().print(1);
		}
		
		return null;
	}
	
	public boolean UsernameExist(User user) throws Exception {
		User user1 = new User();
		List<Config> configList = new ArrayList<Config>();
		String host = user.getDomainname();// 域名
		

		int domain = 1;
		configList = unitService.getDoMain("domain");

		if (configList != null) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getValue().equals(host)) {
					ServletActionContext.getRequest().getSession()
							.setAttribute("host", configList.get(i).getValue());// 设置域名
					domain = configList.get(i).getId();
				}
			}
		}

		String username = user.getUsername();
		user1.setDomain(domain);
		user1.setUsername(username);
		user = userService.checkUserNameExist(user1);

		if (user != null) {
			return true;
		}
		
		return false;
	}

	// 检测邮箱用户数量是否超过规定个数
	public String checkUserSize() throws Exception {

		int num = userService.getUserSize();
		if (num >= 2000) {
			ServletActionContext.getResponse().getWriter().print(1);
		}
		return null;
	}

	// 检测用户密码是否正确
	public String checkPass() throws Exception {
		String domainhost = ServletActionContext.getRequest().getParameter(
				"domainhost");// 获取域名
		int domain = 0;
		String username = ServletActionContext.getRequest()
				.getParameter("username")
				.replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		String password = ServletActionContext.getRequest()
				.getParameter("password")
				.replaceAll("\\,|\\'|\\(|\\)|\\s*", "");

		List<Config> configList = new ArrayList<Config>();
		// 设置域名
		configList = unitService.getDoMain("domain");
		for (int i = 0; i < configList.size(); i++) {
			if (configList.get(i).getValue().equals(domainhost)) {
				domain = configList.get(i).getId();
			}
		}
		User user1 = new User();
		user1.setDomain(domain);
		user1.setUsername(username);
		user = userService.checkUserNameExist(user1);
		String password1 = user.getPass();
		String password2 = user.getSecondpass();

		password1 = password1 != null ? password1.trim() : "";
		password2 = password2 != null ? password2.trim() : "";

		boolean result = false;
		// 第一密码为123 从第二密码进行判断
		if (password1.equals("MTIz")) {
			String salt = "";
			password2 = new String(Base64.decode(password2));
			int index1 = password2.lastIndexOf('}');
			int index2 = password2.lastIndexOf('$');
			if (index1 != -1 && index2 != -1 && index2 > index1) {
				salt = password2.substring(index1 + 1, index2 + 1);
			}
			String cryptPass = "{CRYPT}" + MD5Crypt.crypt(password, salt);
			if (cryptPass.equals(password2)) {
				result = true;
			}
		} else {
			if (Base64.encode(password.getBytes()).equals(password1)) {
				result = true;
			}
		}

		if (result) {
			if (user.getState() == 2) {
				ServletActionContext.getResponse().getWriter().print(2);
			} else {
				ServletActionContext.getResponse().getWriter().print(1);
				ServletActionContext.getRequest().getSession()
						.setAttribute("user", user);
				SessionManager.addUser(user);
				LoggerUtil.addLog(ServletActionContext.getRequest(), logger,
						"用户登录", "", "", "", "", domainhost,
						IpData.getIp(ServletActionContext.getRequest()));
			}
		} else {
			ServletActionContext.getResponse().getWriter().print(3);
		}

		return null;
	}

	// 检测用户密码是否正确
	public String checkPassInteface(User u) throws Exception {
		String host = u.getDomainname();// 域名
		
		int domain = 1;

		String username = u.getUsername();
		String password = u.getPass();
				
		List<Config> configList = new ArrayList<Config>();
		configList = unitService.getDoMain("domain");

		if (configList != null) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getValue().equals(host)) {
					ServletActionContext.getRequest().getSession()
							.setAttribute("host", configList.get(i).getValue());// 设置域名
					domain = configList.get(i).getId();
				}
			}
		}
		
		User user1 = new User();
		user1.setDomain(domain);
		user1.setUsername(username);

		User user = userService.checkUserNameExist(user1);
		String password1 = user.getPass();
		byte[] base64String = Base64.decode(password1);
		MD5 md5 = new MD5();
		password1 = md5.getMD5ofStr(new String(base64String));
		password = md5.getMD5ofStr(password);
		
		if (password.equals(password1)) {
			if (user.getState() == 2) {
				//用户被冻结
				return "lock";
				//ServletActionContext.getResponse().getWriter().print(2);
			} else {
				//ServletActionContext.getResponse().getWriter().print(1);
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				SessionManager.addUser(user);
				LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "登录", "", "", "", "", user.getName(),
						IpData.getIp(ServletActionContext.getRequest()));
				return "success";
			}
		} else {
			//密码错误
			return "miss";
			//ServletActionContext.getResponse().getWriter().print(3);
		}
	}
	
	////////////////////////////////////////////////////////////////////
	// 检测用户密码是否正确
	public String checkUserInterface() throws Exception {
		String host = ServletActionContext.getRequest().getParameter(
		"domainhost");// 获取域名
		
		int domain = 1;

		String username = ServletActionContext.getRequest().getParameter("username").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		String password = ServletActionContext.getRequest().getParameter("password").replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
				
		List<Config> configList = new ArrayList<Config>();
		configList = unitService.getDoMain("domain");

		if (configList != null) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getValue().equals(host)) {
					ServletActionContext.getRequest().getSession()
							.setAttribute("host", configList.get(i).getValue());// 设置域名
					domain = configList.get(i).getId();
				}
			}
		}
		
		User user1 = new User();
		user1.setDomain(domain);
		user1.setUsername(username);

		User user = userService.checkUserNameExist(user1);
		String password1 = user.getPass();
		byte[] base64String = Base64.decode(password1);
		MD5 md5 = new MD5();
		password1 = md5.getMD5ofStr(new String(base64String));
		password = md5.getMD5ofStr(password);
		
		if (password.equals(password1)) {
			if (user.getState() == 2) {
				//用户被冻结
				ServletActionContext.getResponse().getWriter().print(2);
			} else {
				ServletActionContext.getResponse().getWriter().print(1);
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				SessionManager.addUser(user);
				LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "登录", "", "", "", "", user.getName(),
						IpData.getIp(ServletActionContext.getRequest()));
			}
		} else {
			//密码错误
			ServletActionContext.getResponse().getWriter().print(3);
		}
		return null;
	}
	//////////////////////////////////////////////////////////
	
	
	
	public String checkPassword() throws Exception {

		String domainhost = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("host");// 获取域名
		int domain = 0;
		user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		String username = user.getUsername();
		String password = ServletActionContext.getRequest()
				.getParameter("password")
				.replaceAll("\\,|\\'|\\(|\\)|\\s*", "");

		List<Config> configList = new ArrayList<Config>();
		// 设置域名
		configList = unitService.getDoMain("domain");
		for (int i = 0; i < configList.size(); i++) {
			if (configList.get(i).getValue().equals(domainhost)) {
				domain = configList.get(i).getId();
			}
		}
		User user1 = new User();
		user1.setDomain(domain);
		user1.setUsername(username);
		user = userService.checkUserNameExist(user1);
		String password1 = user.getPass();
		String password2 = user.getSecondpass();

		password1 = password1 != null ? password1.trim() : "";
		password2 = password2 != null ? password2.trim() : "";

		boolean result = false;
		// 第一密码为123 从第二密码进行判断
		if (password1.equals("MTIz")) {
			String salt = "";
			password2 = new String(Base64.decode(password2));
			int index1 = password2.lastIndexOf('}');
			int index2 = password2.lastIndexOf('$');
			if (index1 != -1 && index2 != -1 && index2 > index1) {
				salt = password2.substring(index1 + 1, index2 + 1);
			}
			String cryptPass = "{CRYPT}" + MD5Crypt.crypt(password, salt);
			if (cryptPass.equals(password2)) {
				result = true;
			}
		} else {
			if (Base64.encode(password.getBytes()).equals(password1)) {
				result = true;
			}
		}

		if (result) {
			ServletActionContext.getResponse().getWriter().print(1);
		} else {
			ServletActionContext.getResponse().getWriter().print(3);
		}

		return null;
	}

	public String editUser() {

		String id = ServletActionContext.getRequest().getParameter("id");
		int iid = 0;
		if (id != null && !id.equals("")) {
			iid = Integer.parseInt(id);
		}
		user = userService.getUser(iid);
		ServletActionContext.getRequest().setAttribute("user", user);
		return "success";
	}

	public String toEditPass() {
		return "success";
	}

	public void editPass() {
		String password = ServletActionContext.getRequest()
				.getParameter("password")
				.replaceAll("\\,|\\'|\\(|\\)|\\s*", "");
		user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		password = Base64.encode(password.getBytes());// base64加密

		/*
		 * md5 加密 MD5 md = new MD5(); password = md.getMD5ofStr(password);
		 * System.out.println("password:"+password);
		 */

		user.setPass(password);
		userService.editPass(user);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "用户密码修改",
				"", "", "", "", user.getName(), "");
		try {
			ServletActionContext.getResponse().getWriter().print(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String doEditUser() {

		userService.editUser(user);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "用户信息修改",
				"", "", "", "", user.getName(), "");
		return "success";
	}

	public String doEditPass() {
		user.setPass(it.sauronsoftware.base64.Base64.decode(user.getPass()));
		userService.editPass(user);
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "用户修改密码",
				"", "", "", "", user.getName(), "");
		return "success";
	}

	// 根据关键字无刷新查找用户
	public String getUserByKey() throws IOException {

		String host = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("host");

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String key = request.getParameter("key");

		List<User> kuserlist = new ArrayList<User>();
		List<User> kuserlist1 = new ArrayList<User>();
		List<User> kuserlist2 = new ArrayList<User>();
		// 从单位用户列表获取用户
		kuserlist1 = userService.getUserByKey(key);
		// 从联系人获取用户数据
		kuserlist2 = userService.getUserByKey2(key);

		// 合并查询到来的数据
		for (User user : kuserlist1) {
			user.setUsername(user.getUsername() + "@" + host);
			kuserlist.add(user);
		}
		for (User user : kuserlist2) {
			kuserlist.add(user);
		}

		JSONObject json = null;
		if (kuserlist != null && kuserlist.size() > 0) {
			json = JsonUtil.generate(kuserlist);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(json);
		return null;
	}

	// 进入后台添加用户页面
	public String addAdmUser() {

		List<Config> configList = new ArrayList<Config>();
		configList = unitService.getDoMain("domain");

		List<Unit> unitlist = new ArrayList<Unit>();
		Unit unit = new Unit();
		unitlist = unitService.getUnitList(unit);

		if (unitlist != null && unitlist.size() > 0) {
			for (Unit u : unitlist) {
				if (u.getId() == 1) {
					u.setPid(-1);
				}
			}
		}
		ServletActionContext.getRequest()
				.setAttribute("configList", configList);
		ServletActionContext.getRequest().setAttribute("unitlist", unitlist);
		return "success";
	}

	// 后台添加用户
	public String doAddAdmUser() {
		// Blowfish crypt = new Blowfish();
		// user.setPass(crypt.encryptString(user.getPass()));
		HttpServletRequest request = ServletActionContext.getRequest();
		String domain = request.getParameter("domain");

		List<Config> configList = new ArrayList<Config>();
		configList = unitService.getDoMain("domain");// 获取所以域名
		for (int i = 0; i < configList.size(); i++) {
			if (configList.get(i).getValue().equals(domain)) {
				user.setDomain(configList.get(i).getId());
				String pa = user.getPass();
				user.setPass(Base64.encode(pa.getBytes()));
				userService.addUser(user);
			}
		}

		LogUtil.addLog(ServletActionContext.getRequest(), logger, "用户信息添加", "",
				"", "", "", user.getName(), "");
		return "success";
	}

	// 进入后台编辑用户页面
	public String editAdmUser() {
		List<Unit> unitlist = new ArrayList<Unit>();
		Unit unit = new Unit();
		unitlist = unitService.getUnitList(unit);

		if (unitlist != null && unitlist.size() > 0) {
			for (Unit u : unitlist) {
				if (u.getId() == 1) {
					u.setPid(-1);
				}
			}
		}
		// Blowfish crypt = new Blowfish();
		String id = ServletActionContext.getRequest().getParameter("id");
		user = userService.getUser(Integer.parseInt(id));

		List<Config> configList = new ArrayList<Config>();
		String domain = "";
		configList = unitService.getDoMain("domain");
		if (configList != null) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getId() == user.getDomain()) {
					domain = configList.get(i).getValue();
					break;
				}
			}
		}
		user.setDomainname(domain);
		user.setPass("notedit");
		ServletActionContext.getRequest().setAttribute("unitlist", unitlist);
		ServletActionContext.getRequest().setAttribute("user", user);
		return "success";
	}

	// 后台编辑用户
	public String doEditAdmUser() {
		String pa = user.getPass();
		if (pa.equals("notedit")) {
			pa = "";
		}
		user.setPass(Base64.encode(pa.getBytes()));
		userService.editUser(user);
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "用户信息修改", "",
				"", "", "", user.getName(), "");
		return "success";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// 邮箱设置
	public String setmail() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String signstate = request.getParameter("signstate");// 选项卡状态

		int totalsize = 0;// 标签总记录
		User user = (User) request.getSession().getAttribute("user");
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());// 查询所有标签
		totalsize = taglist.size();

		// 查询所有签名
		List<Sign> signlist = new ArrayList<Sign>();
		signlist = userService.getSignList(user.getId());// 查询所有签名
		int signid = 0;
		String sName = "";// 签名
		String signContent = "";
		for (int i = 0; i < signlist.size(); i++) {
			if (signlist.get(i).getDefaultsign() != null
					&& signlist.get(i).getDefaultsign() != ""
					&& signlist.get(i).getDefaultsign().length() > 0) {
				signid = signlist.get(i).getId();
				signContent = signlist.get(i).getContent();
				sName = signlist.get(i).getName();
			}
		}

		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<" + user.getUsername() + "@" + hosts + ">");

		tag = new Tag();
		int onreadtag = 0;// 收件箱未读标记邮件
		int sumtag = 0; // 所以标记邮件

		if (totalsize > 0) {
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0;
			int pagesize = 12;
			if (currentpage != null) {
				pagenow = Integer.parseInt(currentpage);
			} else {
				pagenow = 1;
			}
			if (pagenow == 0) {
				pagenow = 1;
			}
			int pagenum = 0;
			if (totalsize % pagesize == 0) {
				pagenum = totalsize / pagesize;
			} else {
				pagenum = totalsize / pagesize + 1;
			}
			if (pagenow > pagenum) {
				pagenow = pagenum;
			}
			user.setStart(pagesize * pagenow - pagesize);
			user.setEnd(pagesize);
			user.setUnid(user.getId());
			tagList = userService.setmail(user);// 查询所有的标签

			List<Integer> listonreadtag = new ArrayList<Integer>();// 创建收件箱所有未读标记邮件集合
			for (int i = 0; i < tagList.size(); i++) {
				String name = tagList.get(i).getName();
				user1.setName(name);
				user1.setUnid(user.getId());
				onreadtag = userService.getnoreadtag(user1);// 收件箱所有未读标记邮件，根据标记名查找
				listonreadtag.add(onreadtag);// 添加到list中
			}
			List<Integer> listsumtag = new ArrayList<Integer>();// 创建所以标记邮件集合
			// 根据标记名查找所以被标记的邮件
			for (int i = 0; i < tagList.size(); i++) {
				String name = tagList.get(i).getName();
				user1.setName(name);
				user1.setUnid(user.getId());
				sumtag = userService.getsumtag(user1);// 所以标记邮件
				listsumtag.add(sumtag);
			}
			request.setAttribute("listonreadtag", listonreadtag);// 收件箱所有未读标记邮件
			request.setAttribute("listsumtag", listsumtag);// 所以标记邮件
			Page page = new Page();
			page.setPagenow(pagenow);
			page.setPagesize(pagesize);
			page.setTotalsize(totalsize);
			request.setAttribute("pagebar", page.pagebar3());
			request.setAttribute("pagenow", pagenow);
			request.setAttribute("pagenum", pagenum);

		}

		request.setAttribute("signstate", signstate);
		request.setAttribute("sName", sName);
		request.setAttribute("signContent", signContent);
		request.setAttribute("signid", signid);

		request.setAttribute("signlist", signlist);
		request.setAttribute("taglist", taglist);
		return "success";
	}

	// 邮箱设置 添加标签、编辑标签
	public String addTag() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stag = request.getParameter("edittag");// 标识：1为编辑 0为添加
		String sid = request.getParameter("id");
		int edittag = 0;
		int id = 0;
		if (!(stag.equals("null"))) {
			edittag = Integer.parseInt(stag);
		}
		if (!(sid.equals("null"))) {
			id = Integer.parseInt(sid);
		}
		if (edittag == 1) {
			tag.setId(id);
			tagService.editTag(tag);// 编辑标签
			LoggerUtil.addLog(ServletActionContext.getRequest(), logger,
					"修改邮件标签", "", "", "", "", tag.getName(), "");
		} else {
			tagService.addTag(tag);
			tag = tagService.checkNameExist(tag);// 添加标签
			LoggerUtil.addLog(ServletActionContext.getRequest(), logger,
					"添加邮件标签", "", "", "", "", tag.getName(), "");
		}
		setmail();
		return "success";
	}

	// 删除标签
	public String deleteTag() throws IOException {
		String id1 = ServletActionContext.getRequest().getParameter("id");
		int id = 0;
		if (id1 != null && !id1.equals("")) {
			id = Integer.parseInt(id1);
		}
		tagService.deleteTag(id);
		setmail();
		return "success";
	}

	// 查询所有标签邮件
	public String gettagmail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tagname = request.getParameter("tagname");
		List<Email> getTagMailRecvList = new ArrayList<Email>();// 所有收件箱标记邮件list
		List<Email> getTagMailSendList = new ArrayList<Email>();// 所有发件箱标记邮件list
		List<Email> getTagMailDraftsList = new ArrayList<Email>();// 所有草稿箱标记邮件list
		List<String> recvFrommailList = new ArrayList<String>();
		List<String> sendFrommailList = new ArrayList<String>();
		List<String> draftsFrommailList = new ArrayList<String>();
		int totalsize = 0;
		User user = (User) request.getSession().getAttribute("user");
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<" + user.getUsername() + "@" + hosts + ">");
		user1.setName("<admin@" + hosts + ">");
		totalsize = userService.getTagMailCount(user1);

		getTagMailRecvList = userService.getTagMailRecv(user1); // 查询所有收件箱标记邮件

		getTagMailSendList = userService.getTagMailSend(user1);// 查询所有发件箱标记邮件

		getTagMailDraftsList = userService.getTagMailDrafts(user1);// 查询所有草稿箱标记邮件
		// 收件箱标记邮件标题（subject）集合
		for (int i = 0; i < getTagMailRecvList.size(); i++) {
			if (getTagMailRecvList.get(i).getFrommail().indexOf('@') != -1) {
				recvFrommailList.add(getTagMailRecvList
						.get(i)
						.getFrommail()
						.substring(
								1,
								getTagMailRecvList.get(i).getFrommail()
										.indexOf("@")));
			} else {
				recvFrommailList.add(null);
			}
		}

		// 发件箱标记邮件标题（subject）集合
		for (int i = 0; i < getTagMailSendList.size(); i++) {
			if (getTagMailSendList.get(i).getFrommail().indexOf('@') != -1) {
				sendFrommailList.add(getTagMailSendList
						.get(i)
						.getFrommail()
						.substring(
								1,
								getTagMailSendList.get(i).getFrommail()
										.indexOf("@")));
			} else {
				sendFrommailList.add(null);
			}
		}

		// 草稿箱标记邮件标题（title）集合
		for (int i = 0; i < getTagMailDraftsList.size(); i++) {
			if (getTagMailDraftsList.get(i).getFrommail().indexOf('@') != -1) {
				draftsFrommailList.add(getTagMailDraftsList
						.get(i)
						.getFrommail()
						.substring(
								1,
								getTagMailDraftsList.get(i).getFrommail()
										.indexOf("@")));
			} else {
				draftsFrommailList.add(null);
			}
		}

		request.setAttribute("tagname", tagname);
		request.setAttribute("totalsize", totalsize);
		request.setAttribute("getTagMailRecvList", getTagMailRecvList);
		request.setAttribute("getTagMailSendList", getTagMailSendList);
		request.setAttribute("getTagMailDraftsList", getTagMailDraftsList);
		request.setAttribute("recvFrommailList", recvFrommailList);
		request.setAttribute("sendFrommailList", sendFrommailList);
		request.setAttribute("draftsFrommailList", draftsFrommailList);
		return "success";
	}

	// 根据名字查询所有标签邮件
	public String getTagNameMail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tagname = request.getParameter("tagname");// 标签名字
		List<Email> getTagMailRecvList = new ArrayList<Email>();// 根据名字所有收件箱标记邮件list
		List<Email> getTagMailSendList = new ArrayList<Email>();// 根据名字所有发件箱标记邮件list
		List<Email> getTagMailDraftsList = new ArrayList<Email>();// 根据名字所有草稿箱标记邮件list
		List<String> recvFrommailList = new ArrayList<String>();
		List<String> sendFrommailList = new ArrayList<String>();
		List<String> draftsFrommailList = new ArrayList<String>();
		int totalsize = 0;

		User user = (User) request.getSession().getAttribute("user");
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<" + user.getUsername() + "@" + hosts + ">");
		user1.setName("<admin@" + hosts + ">");

		totalsize = userService.getTagMailCount(user1);

		if (!tagname.equals("null")) {

			user1.setName(tagname);

			getTagMailRecvList = userService.getTagNameMailRecv(user1);// 根据名字查询所有收件箱标记邮件

			getTagMailSendList = userService.getTagNameMailSend(user1);// 根据名字查询所有发件箱标记邮件

			getTagMailDraftsList = userService.getTagNameMailDrafts(user1);// 根据名字查询所有草稿箱标记邮件

			// 收件箱标记邮件标题（subject）集合
			for (int i = 0; i < getTagMailRecvList.size(); i++) {
				if (getTagMailRecvList.get(i).getFrommail().indexOf('@') != -1) {
					recvFrommailList.add(getTagMailRecvList
							.get(i)
							.getFrommail()
							.substring(
									1,
									getTagMailRecvList.get(i).getFrommail()
											.indexOf("@")));
				} else {
					recvFrommailList.add(null);
				}
			}

			// 发件箱标记邮件标题（subject）集合
			for (int i = 0; i < getTagMailSendList.size(); i++) {
				if (getTagMailSendList.get(i).getFrommail().indexOf('@') != -1) {
					sendFrommailList.add(getTagMailSendList
							.get(i)
							.getFrommail()
							.substring(
									1,
									getTagMailSendList.get(i).getFrommail()
											.indexOf("@")));
				} else {
					sendFrommailList.add(null);
				}
			}

			// 草稿箱标记邮件标题（title）集合
			for (int i = 0; i < getTagMailDraftsList.size(); i++) {
				if (getTagMailDraftsList.get(i).getFrommail().indexOf('@') != -1) {
					draftsFrommailList.add(getTagMailDraftsList
							.get(i)
							.getFrommail()
							.substring(
									1,
									getTagMailDraftsList.get(i).getFrommail()
											.indexOf("@")));
				} else {
					draftsFrommailList.add(null);
				}
			}
			request.setAttribute("tagname", tagname);
			request.setAttribute("totalsize", totalsize);
			request.setAttribute("getTagMailRecvList", getTagMailRecvList);
			request.setAttribute("getTagMailSendList", getTagMailSendList);
			request.setAttribute("getTagMailDraftsList", getTagMailDraftsList);
			request.setAttribute("recvFrommailList", recvFrommailList);
			request.setAttribute("sendFrommailList", sendFrommailList);
			request.setAttribute("draftsFrommailList", draftsFrommailList);
		}

		return "success";
	}

	// 根据条件删除标记邮件
	public String delTagNameMail() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("types");// 类型：1：收件箱 2：发件箱 3：草稿箱
		String mailid = request.getParameter("mailid");
		String id = request.getParameter("id");
		String tagname = request.getParameter("tagname");
		if (type.equals("1")) {
			userService.delTagNameMailRecv(mailid);// 删除收件箱标记邮件
		}
		if (type.equals("2")) {
			userService.delTagNameMailSend(mailid);// 删除发件箱标记邮件
		}
		if (type.equals("3")) {
			userService.delTagNameMailDrafts(Integer.parseInt(id));// 删除草稿箱标记邮件
		}
		gettagmail();
		request.setAttribute("tagname", tagname);
		return "success";
	}

	/** 检查签名的名字是否重复 */
	// public String checkSignNameExist() throws Exception{
	// HttpServletRequest request = ServletActionContext.getRequest();
	// User user= (User)request.getSession().getAttribute("user");
	// String signName = request.getParameter("signName");
	//
	// return null;
	// }

	/**
	 * 添加、修改签名
	 * 
	 * @throws IOException
	 */
	public String modifySign() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		User user = (User) request.getSession().getAttribute("user");
		String state = request.getParameter("state");// 0:添加；1：编辑
		String signName = request.getParameter("signName");// 签名名称
		String content = request.getParameter("content");// 签名内容
		String id = request.getParameter("id");// 签名id
		Sign sign = new Sign();
		sign.setUserid(user.getId());
		sign.setName(signName);
		sign.setContent(content);

		JSONObject json = null;

		if (state.equals("0")) {

			/** 检查签名的名字是否重复 */
			Sign sign2 = new Sign();
			sign2 = userService.checkSignNameExist(sign);
			if (sign2 != null) {
				response.getWriter().print(json);
				return null;
			} else {
				userService.addSign(sign);// 添加签名
			}
		}
		if (state.equals("1")) {
			if (id != null) {
				sign.setId(Integer.parseInt(id));// 修改签名
			}
			userService.editSign(sign);
		}

		List<Sign> signlist = new ArrayList<Sign>();
		signlist = userService.getSignList(user.getId());// 查询所有签名

		if (signlist != null && signlist.size() > 0) {
			json = JsonUtil.generate(signlist);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(json);

		return null;
	}

	/** 删除签名 */
	public String delSign() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		if (id != null) {
			userService.delSign(Integer.parseInt(id));
		}
		ServletActionContext.getRequest().setAttribute("signstate", 1);// 选项卡状态
		return "success";
	}

	/** 设置默认名 */
	public String modifyDefaultSign() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		String defaultsign = request.getParameter("defaultsign");
		Sign sign = new Sign();
		if (id != null) {
			sign.setId(Integer.parseInt(id));
		}
		sign.setDefaultsign(defaultsign);

		userService.modifyDefaultSign(sign);
		List<Sign> signList = new ArrayList<Sign>();
		signList = userService.getDefaultSignList();
		if (signList != null) {
			String content = signList.get(0).getContent();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(content);
		}
		return null;
	}

	/** 清空所有默认名称 */
	public String clearDefaultSign() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String state = request.getParameter("state");
		if (state.equals("-1")) {
			userService.clearDefaultSign();
		}
		return null;
	}

	/**
	 * 邮箱设置-反垃圾-黑名单
	 */
	public String spam_blacklist() {
		return "success";
	}

	/**
	 * 邮箱设置-反垃圾-邮件地址黑名单
	 */
	public String spam_blacklist_list() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String method = req.getParameter("m");
		User user = (User) req.getSession().getAttribute("user");
		Object hosts = req.getSession().getAttribute("host");
		String tomail = "<" + user.getUsername() + "@" + hosts + ">";
		if ("add".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.addSpamUser(e);
		} else if ("del".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.delReport(e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tomail", tomail);
		int count = emailService.getSpamListCount(map);
		int pageNumber = 0, pageNow = 0, offset = 0, rows = 10;

		if (count % rows > 0)
			pageNumber = count / rows + 1;
		else
			pageNumber = count / rows;
		String i = ServletActionContext.getRequest().getParameter("i");
		if (i != null) {
			pageNow = Integer.parseInt(i);
			if (pageNow > 0)
				pageNow = Integer.parseInt(i);
			else
				pageNow = 1;
		} else {
			pageNow = 1;
		}
		if (pageNow * rows > count) {
			if ((pageNumber - 1) * rows > 0)
				offset = (pageNumber - 1) * rows;
			pageNow = pageNumber;
		} else {
			offset = pageNow * rows - rows;
		}
		map.put("offset", offset);
		map.put("rows", rows);
		List<Map<String, String>> l = emailService.getSpamListByUser(map);
		req.setAttribute("blacklist", l);
		req.setAttribute("count", count);
		req.setAttribute("pageNumber", pageNumber);
		req.setAttribute("pageNow", pageNow);
		return "success";
	}

	/**
	 * 邮箱设置-反垃圾-邮件域名黑名单
	 */
	public String spam_blacklist_domain_list() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String method = req.getParameter("m");
		User user = (User) req.getSession().getAttribute("user");
		Object hosts = req.getSession().getAttribute("host");
		String tomail = "<" + user.getUsername() + "@" + hosts + ">";
		if ("add".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.addSpamUserDomain(e);
		} else if ("del".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.delReportDomain(e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tomail", tomail);
		int count = emailService.getSpamListDomainCount(map);
		int pageNumber = 0, pageNow = 0, offset = 0, rows = 10;

		if (count % rows > 0)
			pageNumber = count / rows + 1;
		else
			pageNumber = count / rows;
		String i = ServletActionContext.getRequest().getParameter("i");
		if (i != null) {
			pageNow = Integer.parseInt(i);
			if (pageNow > 0)
				pageNow = Integer.parseInt(i);
			else
				pageNow = 1;
		} else {
			pageNow = 1;
		}
		if (pageNow * rows > count) {
			if ((pageNumber - 1) * rows > 0)
				offset = (pageNumber - 1) * rows;
			pageNow = pageNumber;
		} else {
			offset = pageNow * rows - rows;
		}
		map.put("offset", offset);
		map.put("rows", rows);
		List<Map<String, String>> l = emailService.getSpamListDomainByUser(map);
		req.setAttribute("blacklist", l);
		req.setAttribute("count", count);
		req.setAttribute("pageNumber", pageNumber);
		req.setAttribute("pageNow", pageNow);
		return "success";
	}

	/**
	 * 邮箱设置-反垃圾-邮件地址白名单
	 */
	public String spam_whitelist_list() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String method = req.getParameter("m");
		User user = (User) req.getSession().getAttribute("user");
		Object hosts = req.getSession().getAttribute("host");
		String tomail = "<" + user.getUsername() + "@" + hosts + ">";
		if ("add".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.addWhite(e);
		} else if ("del".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.delWhite(e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tomail", tomail);
		int count = emailService.getWhiteCount(map);
		int pageNumber = 0, pageNow = 0, offset = 0, rows = 2;

		if (count % rows > 0)
			pageNumber = count / rows + 1;
		else
			pageNumber = count / rows;
		String i = ServletActionContext.getRequest().getParameter("i");
		if (i != null) {
			pageNow = Integer.parseInt(i);
			if (pageNow > 0)
				pageNow = Integer.parseInt(i);
			else
				pageNow = 1;
		} else {
			pageNow = 1;
		}
		if (pageNow * rows > count) {
			if ((pageNumber - 1) * rows > 0)
				offset = (pageNumber - 1) * rows;
			pageNow = pageNumber;
		} else {
			offset = pageNow * rows - rows;
		}
		map.put("offset", offset);
		map.put("rows", rows);
		List<Map<String, String>> l = emailService.getWhites(map);
		req.setAttribute("blacklist", l);
		req.setAttribute("count", count);
		req.setAttribute("pageNumber", pageNumber);
		req.setAttribute("pageNow", pageNow);
		return "success";
	}

	/**
	 * 邮箱设置-反垃圾-邮件域名白名单
	 */
	public String spam_whitelist_domain_list() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String method = req.getParameter("m");
		User user = (User) req.getSession().getAttribute("user");
		Object hosts = req.getSession().getAttribute("host");
		String tomail = "<" + user.getUsername() + "@" + hosts + ">";
		if ("add".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.addWhiteDomain(e);
		} else if ("del".equals(method)) {
			String addr = req.getParameter("txtbl");
			Email e = new Email();
			e.setTomail(tomail);
			e.setFrommail(addr);
			emailService.delWhiteDomain(e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tomail", tomail);
		int count = emailService.getWhiteDomainCount(map);
		int pageNumber = 0, pageNow = 0, offset = 0, rows = 2;

		if (count % rows > 0)
			pageNumber = count / rows + 1;
		else
			pageNumber = count / rows;
		String i = ServletActionContext.getRequest().getParameter("i");
		if (i != null) {
			pageNow = Integer.parseInt(i);
			if (pageNow > 0)
				pageNow = Integer.parseInt(i);
			else
				pageNow = 1;
		} else {
			pageNow = 1;
		}
		if (pageNow * rows > count) {
			if ((pageNumber - 1) * rows > 0)
				offset = (pageNumber - 1) * rows;
			pageNow = pageNumber;
		} else {
			offset = pageNow * rows - rows;
		}
		map.put("offset", offset);
		map.put("rows", rows);
		List<Map<String, String>> l = emailService.getWhitesDomain(map);
		req.setAttribute("blacklist", l);
		req.setAttribute("count", count);
		req.setAttribute("pageNumber", pageNumber);
		req.setAttribute("pageNow", pageNow);
		return "success";
	}
}