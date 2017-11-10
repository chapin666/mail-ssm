package util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class InitLogj4jXmlConfig extends HttpServlet {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    public void init() throws ServletException {
    	
    	// 读取配置文件
        String prefix = getServletContext().getRealPath("/");
        String file = getInitParameter("log4jxmlconfig");
        org.apache.log4j.xml.DOMConfigurator.configureAndWatch(prefix + file);
        
        File mailInfo = new File(this.getClass().getResource("/mail.xml").getPath());
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(mailInfo);
            if(document!=null){
                Element root = document.getRootElement();
                Element set ;
                for (Iterator<?> i = root.elementIterator("mailset"); i.hasNext();) {
                    set = (Element) i.next();
                    Cache.cache.put("pop3", set.elementText("pop3"));
                    Cache.cache.put("smtp", set.elementText("smtp"));
                    Cache.cache.put("auth", set.elementText("auth"));
                    Cache.cache.put("filedir", set.elementText("filedir"));
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}