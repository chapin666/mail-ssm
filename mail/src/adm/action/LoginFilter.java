package adm.action;

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
import adm.bean.Member;

public class LoginFilter  implements Filter {

	public void doFilter(final ServletRequest req,final ServletResponse res,FilterChain chain)
		throws IOException,ServletException {
		
		HttpServletRequest hreq = (HttpServletRequest)req;
		HttpServletResponse hres = (HttpServletResponse)res;
		HttpSession session = hreq.getSession();
		String url = hreq.getRequestURI();
		//后台管理员登录验证
		try{
				Member member = (Member) session.getAttribute("member");
			 
				if(member!=null){
					chain.doFilter(req,res);
				}else if(url.endsWith("adm/login.mail")){
					chain.doFilter(req,res);
				}else if(url.endsWith("member/checkUserNameExist.mail")){
					chain.doFilter(req,res);
				}else if(url.endsWith("member/checkPass.mail")){
					chain.doFilter(req,res);
				}else if(url.endsWith("adm/Security.mail")){
					chain.doFilter(req,res);
				}else{
					hres.sendRedirect("/adm/login.mail");
				}
			}catch(Exception e){      
				e.printStackTrace();
			}
		}

	public void destroy() {}
	
	public void init(FilterConfig arg0) throws ServletException {}

}