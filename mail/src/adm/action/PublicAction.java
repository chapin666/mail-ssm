package adm.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mail.bean.Config;
import mail.service.GroupService;
import mail.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import adm.bean.Member;
import adm.bean.Unit;
import adm.service.UnitService;
import util.LogUtil;

@Component("publicAction")
public class PublicAction{
	private static final Log logger = LogFactory.getLog(PublicAction.class);
	
	@Autowired
	private UnitService unitService ;
	@Autowired
	private UserService userService ;
	@Autowired
	private GroupService groupService ;
	
	//进入后台主页
	public String index() throws Exception{
//		MailSet ms = new MailSet();
//		ms.getmail();
		//Config config = unitService.getDoMain("domain");
		//if(config!=null){
			//ServletActionContext.getRequest().getSession().setAttribute("host",config.getValue());
		//}
		return "success" ;
	}
	
	public String top() throws Exception{

		return "success" ;
	}
	
	//后台主页主题页面
	public String main() throws Exception{
		int num1 = unitService.getUnitSize();
		int num2 = userService.getUserSize();
		int num3 = groupService.getGroupSize();
		ServletActionContext.getRequest().setAttribute("num1",num1);
		ServletActionContext.getRequest().setAttribute("num2",num2);
		ServletActionContext.getRequest().setAttribute("num3",num3);
		return "success" ;
	}
	
	public String login(){
		
		return "success" ;
	}

	public String member(){
		
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
		return "success" ;
	}

	public String Security(){
		
		return "success" ;
	}
	
	public String UploadImg() throws IOException {
		
		String src = ServletActionContext.getRequest().getParameter("src");
		if(src!=null&&!src.equals("")){
			File file = new File(src);
			if(file.exists()){
				ServletActionContext.getResponse().getWriter().print(file.length()/1024);
			}
		}
		return null ;
	}
	
	public String exitSystem(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("member");
		LogUtil.addLog(ServletActionContext.getRequest(), logger, "安全退出", "", "", "","",member.getUsername(),"");
		session.removeAttribute("member");
		return "success" ;
	}
}
