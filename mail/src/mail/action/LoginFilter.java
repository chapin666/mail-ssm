package mail.action;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpSession;
import mail.bean.User;

public class LoginFilter  implements Filter {

	public void doFilter(final ServletRequest req,final ServletResponse res,FilterChain chain)
		throws IOException,ServletException {

		HttpServletRequest hreq = (HttpServletRequest)req;
		HttpServletResponse hres = (HttpServletResponse)res;
		HttpSession session = hreq.getSession();
		String url = hreq.getRequestURI();
		//前台判断登录过滤
		try{
				User user = (User) session.getAttribute("user");
			 
				if(user!=null){
					chain.doFilter(req,res);
				}else if(url.endsWith("login.html")){
					chain.doFilter(req,res);
				}else if(url.endsWith("checkUsernameExist.html")){
					chain.doFilter(req,res);
				}else if(url.endsWith("checkPass.html")){
					chain.doFilter(req,res);
				}else if(url.endsWith("checkPassInteface.html")){
					chain.doFilter(req,res);
				}else if(url.endsWith("Security.html")){
					chain.doFilter(req,res);
				}else if(url.endsWith("MailAuth.html")){
					chain.doFilter(req,res);
				}else if(url.endsWith("Auth.html")){
					chain.doFilter(req,res);
				} else if (url.endsWith("outer.html")) {
					chain.doFilter(req,res);
				} else if (url.endsWith("home.html")) {
					chain.doFilter(req,res);
				} else if (url.endsWith("index.html")) {
					chain.doFilter(req,res);
				} else if(url.endsWith("checkUserInterface.html")) {
					chain.doFilter(req,res);
				}else{
					hres.sendRedirect("/login.html");
				}
			}catch(Exception e){        
				e.printStackTrace();
			}
		}

	public void destroy() {}
	
	public void init(FilterConfig arg0) throws ServletException {}

}
