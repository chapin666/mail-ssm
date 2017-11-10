package mail.action;


import java.io.BufferedReader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.User;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("outerAction")
@Scope("prototype")
public class OuterAction {

	@Autowired
	private UserAction action;
	
	//跳转前台主页
	public String index() throws Exception{

		ServletContext context = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.addHeader("Access-Control-Allow-Origin", "*" );	// open your api to any client 
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept" ); 
		response.addHeader("Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader("Access-Control-Max-Age", "1000");		// tim
		
		/*String name = request.getParameter("username");
		String pwd = request.getParameter("password");
		String host = request.getParameter("domainhost");*/
		
		BufferedReader reader = request.getReader();
		String []array = new String[3];
		String s = null;
		int i = 0;
		while ((s = reader.readLine()) != null) {
		    array[i] = s.trim();
		    i++;
		}
		
		User user = new User();
		user.setUsername(array[0]);
		user.setPass(array[1]);
		user.setDomainname(array[2]);
	
		
		if (("").equals(user.getUsername().trim())) {
			//用户名为空
			context.setAttribute("error", "用户名为空");
			return "error";
		}
		if (("").equals(user.getPass().trim())) {
			//密码为空
			context.setAttribute("error", "密码为空");
			return "error";
		}
		
		boolean flag = action.UsernameExist(user);
		if (!flag) {
			////用户名不存在
			context.setAttribute("error", "用户名或密码错误");
			return "error";
		}
		
		String result = action.checkPassInteface(user);
		if ("lock".equals(result)) {
			//账号冻结
			context.setAttribute("error", "账户被冻结");
			return "error";
		} else if ("miss".equals(result)) {
			//密码错误
			context.setAttribute("error", "用户名或密码错误");
			return "error";
		} else {
			context.setAttribute("error", "success");
			return "error";
		}

	}
	
	public String home() throws Exception {
		return "success";
	}
	
}
