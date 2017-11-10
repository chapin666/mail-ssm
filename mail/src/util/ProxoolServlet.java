package util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.logicalcobwebs.proxool.ProxoolFacade;

public class ProxoolServlet extends HttpServlet {    
    /**
	 * 
	 */
	private static final long serialVersionUID = 45285813753731246L;
	
	public void init() throws ServletException {    
    }    
  
    public void destroy() {    
        ProxoolFacade.shutdown();    
    }    
  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws    
        ServletException, IOException {    
        doGet(request,response);    
    }    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws    
        ServletException, IOException {    
           
    }    
  
}   
