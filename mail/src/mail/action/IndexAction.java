package mail.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mail.bean.Config;
import mail.bean.Tag;
import mail.bean.User;
import mail.service.EmailService;
import mail.service.TagService;

import mail.service.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adm.bean.Logger;
import adm.bean.Notice;
import adm.service.UnitService;
import util.Format;
import util.LoggerUtil;
import util.Rand;
import util.Upload;

@Component("indexAction")
public class IndexAction{
	private static final Log logger = LogFactory.getLog(IndexAction.class);
	
	@Autowired
	private EmailService emailService ;
	@Autowired
	private UnitService unitService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private TagService tagService ;

	//跳转前台主页
	public String index(){
		
		return "success" ;
	}
	
	//跳转前台登录页面
	public String login() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Config> configList = new ArrayList<Config>();
		configList = unitService.getDoMain("domain");
		request.setAttribute("configList",configList);
		return "success" ;
	}

	public String top(){
		
		return "success" ;
	}

	public String left(){
		int getMailnum = 0;			//收件箱未读邮件
		int saveMailnum = 0;		//草稿箱邮件
		int delMailnum = 0;			//已删除邮件
		int getRubMailnum = 0;		//垃圾邮件
		int getMainMailnum = 0;		//新标未读邮件
		int newsnum = 0; 			//单位公告邮件
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		user1.setName("<admin@"+hosts+">");
		user1.setStat("0");
		getMailnum = emailService.getReciveSize(user1);//获得收件箱未读邮件		
		saveMailnum = emailService.getDraftsSize(user1);//草稿箱未读邮件
		delMailnum = emailService.getDelSize(user1);//已删除邮件		
		user1.setState(4);
		getRubMailnum = emailService.getRubMailCount(user1);//垃圾邮件
		getMainMailnum = emailService.getAdmNewsSize1(user1);//新标未读邮件
		newsnum = emailService.getAdmNewsSize1(user1);//单位公告邮件
		
		//查询所有标签
		
		List<Tag> taglist = new ArrayList<Tag>();
		taglist = tagService.getTagList(user.getId());//查询所有标签		
		
		request.setAttribute("taglist",taglist);
		request.setAttribute("getMainMailnum",getMainMailnum);
		request.setAttribute("newsnum",newsnum);
		request.setAttribute("getRubMailnum",getRubMailnum);
		request.setAttribute("delMailnum",delMailnum);
		request.setAttribute("saveMailnum",saveMailnum);
		request.setAttribute("getMailnum", getMailnum);
		return "success" ;
	}
	//前台邮箱主页
	public String main(){
		int num1 = 0 ;				//单位公告总记录
		int num2 = 0 ;				//未读邮件总记录
		int num3 = 0 ;				//星标邮件总记录
		int num4 = 0 ;				//收件箱总记录
		
		String username = null;     //用户名
		String ip = null;           //登录IP
		String time = null;         //登录时间
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		User user= (User)request.getSession().getAttribute("user");
		Object hosts = request.getSession().getAttribute("host");
		User user1 = new User();
		user1.setUsername("<"+user.getUsername()+"@"+hosts+">");
		user1.setName("<admin@"+hosts+">");
		user1.setPhone("0");	
		num1 = emailService.getAdmNewsSize(user1);
		
		num2 = emailService.getReciveSize(user1);	
		
		user.setPhone("1,0");
		num3= emailService.getMainMailSize(user1);
		
		user1.setPhone("1");
		user1.setStat("0");
		num4 = emailService.getReciveSize(user1);
		
		//System.out.println("用户名："+user.getName());
		
		
		username = user.getUsername();//获取账号
		Logger logger = new Logger();
		logger.setUsername(username);	
		logger.setOdata((String)hosts);
		
		List<Logger> listlogger = new ArrayList<Logger>();
		listlogger = userService.getTypeLoginlog(logger);//查出最后一次登录日志记录
		if(listlogger.size() > 0){//判断记录是否存在
			username = listlogger.get(0).getUsername();//获取list中的值username
			ip = listlogger.get(0).getIps();
			time = listlogger.get(0).getTime();
		}else {
			request.setAttribute("message","第一次登录");
		}

		request.setAttribute("username",username);
		request.setAttribute("ip",ip);
		request.setAttribute("time",time);
		
		
		request.setAttribute("num1",num1);
		request.setAttribute("num2",num2);
		request.setAttribute("num3",num3);
		request.setAttribute("num4",num4);
		return "success" ;
	}

	public String Security(){
		
		return "success" ;
	}
	
	//判断上传文件大小
	public String UploadImgs() throws IOException {
		
		String src = ServletActionContext.getRequest().getParameter("src");
		if(src!=null&&!src.equals("")){
			File file = new File(src);
			if(file.exists()){
				ServletActionContext.getResponse().getWriter().print(file.length()/1024);
			}else{
				ServletActionContext.getResponse().getWriter().print(0);
			}
		}
		return null ;
	}

	// 上传附件
	public String UploadAttach() throws IOException {

		String src = ServletActionContext.getRequest().getParameter("src");
		String userid = ServletActionContext.getRequest()
				.getParameter("userid");
		String filename = "";
		String path = this.getClass().getResource("/").getPath();
		if (path != null && !path.equals("")) {
			path = path.replace("/WEB-INF/classes/", "");
		}
		path += "/upload/" + userid + "/";

		if (src != null && !src.equals("")) {
			File file = new File(path);
			if (file.exists()) {
				filename = Format.IdFormat(new Date())
						+ Rand.rondom4()
						+ src.substring(src.lastIndexOf("\\") + 1, src.length());
				path += filename;
				File files = new File(src);
				Upload.UploadFile(path, files);
				ServletActionContext.getResponse()
						.setCharacterEncoding("uft-8");
				ServletActionContext.getResponse().setContentType(
						"text/html;charset=utf-8");
				ServletActionContext.getResponse().getWriter().print(path);
			} else {
				file.mkdirs();
				filename = Format.IdFormat(new Date())
						+ Rand.rondom4()
						+ src.substring(src.lastIndexOf("\\") + 1, src.length());
				path += filename;
				File files = new File(src);
				Upload.UploadFile(path, files);
				ServletActionContext.getResponse()
						.setCharacterEncoding("uft-8");
				ServletActionContext.getResponse().setContentType(
						"text/html;charset=utf-8");
				ServletActionContext.getResponse().getWriter().print(path);
			}
			ServletActionContext.getRequest().getSession()
					.setAttribute("mailattachpath", path);
		}
		return null;
	}

	//前台安全退出
	public String exitSystem(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		LoggerUtil.addLog(ServletActionContext.getRequest(), logger, "安全退出", "", "", "","",user.getUsername(),"");
		session.removeAttribute("user");
		return "success" ;
	}
}
