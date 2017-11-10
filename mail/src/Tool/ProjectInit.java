package Tool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.JobDataMap;

import util.Cache;
import util.quartz.CustomJob;
import util.quartz.QAddLogger;
import util.quartz.QuartzManager;

public class ProjectInit  extends HttpServlet {
    /**
	 * 项目初次启动，进行初始化
	 */
    private static final long serialVersionUID = 1L;

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    public void init() throws ServletException {
    	
    	// 读取配置文件
    	ReadXmlFile();
    	
    	// 启动定时器定时清除冗余文件
    	StartEmptyFolderTimer();
    	
    	// 定时导入日志文件
    	//ImportLoger();
    }
    
    private void ImportLoger(){
    	
		CustomJob job = new CustomJob();
		job.setJobId("ImportLoger");
		job.setJobGroup("ImportLoger_group");
		job.setJobName("ImportLoger");
		job.setMemos("定时导入日志文件");
		job.setCronExpression("5 * * * * ?");
		job.setStateFulljobExecuteClass(QAddLogger.class);
		
		QuartzManager.enableCronSchedule(job, null, true);
    }
    
    
    private void StartEmptyFolderTimer(){
    	
    	String dir = Cache.cache.get("filedir"); //目录以"/"结束
    	
		CustomJob job = new CustomJob();
		job.setJobId("EmptyFolder");
		job.setJobGroup("jEmptyFolder_group");
		job.setJobName("EmptyFolder");
		job.setMemos("定时清除冗余文件");
		job.setCronExpression("0 0 23 * * ?");//每天晚上11点
		job.setStateFulljobExecuteClass(TimerEmptyFolder.class);
		
		JobDataMap paramsMap = new JobDataMap();
		ArrayList<String> paramList = new ArrayList<String>();
		paramList.add(dir+"temp/");
		paramList.add(dir+"mail/upload/");
		paramsMap.put("folder",paramList);
		
		QuartzManager.enableCronSchedule(job, paramsMap, true);
    }
    
    private void ReadXmlFile(){
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
                     Cache.cache.put("filedir", set.elementText("filedir"));
                 }
             }
         } catch (DocumentException e) {
             e.printStackTrace();
         }
    }
}