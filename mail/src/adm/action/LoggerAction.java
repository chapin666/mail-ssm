package adm.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mail.bean.Email;
import mail.bean.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.xslt.ArrayAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import adm.bean.Logger;
import adm.bean.SendFaileLog;
import adm.service.LoggerService;
import util.LogUtil;
import util.Page;
import util.Para;

@Component("loggerAction")
@Scope("prototype")
public class LoggerAction {
	private static final Log log = LogFactory.getLog(LoggerAction.class);
	@Autowired
	private LoggerService loggerService;
	private Logger logger;
	private List<?> logList;
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public List<?> getLogList() {
		return logList;
	}
	public void setLogList(List<?> logList) {
		this.logList = logList;
	}
	
	//事务列表
	public String list1()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String ftmail = request.getParameter("ftmail");    //发件人
		String title = request.getParameter("title");      //主题
		String types = request.getParameter("types");      //类型
		String times = request.getParameter("times");      //开始时间
		String timex = request.getParameter("timex");      //结束时间
		
		
		String currentpage = request.getParameter("pagenow");
		int pagenow = 0 ;
		int pagesize = 15 ;
		int pagenum = 0;
		int totalsize = 0;
		
		List<Email> logList = new ArrayList<Email>();
		
//		logger = new Logger();
//		logger.setFtmail(ftmail);
//		logger.setTitle(title);
//		logger.setTypes(types);
//		logger.setTimes(times);
//		logger.setTimex(timex);
//		
//		logger.setSearchType("1");
		
		
		Email email = new Email();
		email.setFrommail(ftmail);
		email.setTitle(title);
		//email.setType(types);
		email.setTimes(times);
		email.setTimex(timex);
		
		if(types.equals("2")){
			totalsize = loggerService.emailRecvCount(email);//统计收信
		}else if(types.equals("3")){
			totalsize = loggerService.emailSendCount(email);//统计发信
		}else{
			totalsize = loggerService.emailAllCount(email);
		}
		
		if(totalsize>0){			
			if(currentpage != null){
				pagenow = Integer.parseInt(currentpage);
			}else{
				pagenow = 1;
			}
			if(pagenow == 0){
				pagenow = 1;
			}
			pagenum = 0 ;
			if(totalsize%pagesize==0){
				pagenum = totalsize/pagesize;
			}else{
				pagenum = totalsize/pagesize+1;
			}
			email.setStart(pagesize*pagenow-pagesize);
			email.setEnd(pagesize);
			if(types.equals("2")){				
				logList = loggerService.getEmailRecv(email);//查询收信
				request.setAttribute("searchValue", 2);
			}else if(types.equals("3")){				
				logList = loggerService.getEmailSend(email);//查询发信
				request.setAttribute("searchValue", 3);
			}else{	
				logList = loggerService.getEmailAll(email);				
				request.setAttribute("searchValue", 1);//全部
			}
		}
		
		if(logList != null){
			Page page = new Page();
			page.setPagenow(pagenow);
			page.setPagesize(pagesize);
			page.setTotalsize(totalsize);
			request.setAttribute("pagebar",page.pagebar3());
			request.setAttribute("pagenow",pagenow);
			request.setAttribute("pagesize",pagesize);
			request.setAttribute("totalsize",totalsize);
			request.setAttribute("pagenum",pagenum);
			request.setAttribute("times",times);
			request.setAttribute("timex",timex);
			request.setAttribute("logList",logList);
		}
		request.setAttribute("ftmail",ftmail);
		request.setAttribute("title",title);
		return "list1";
	}

	//事务列表
	public String list2()
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		String username = request.getParameter("username");
		String time = request.getParameter("time");
		String day = request.getParameter("day");
		logger = new Logger();
		logger.setUsername(username);
		logger.setTime(time);
		logger.setDay(day);
		logger.setSearchType("2");
		int totalsize = loggerService.getLoggerUserTotal(logger);
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 13 ;
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
			if(logger == null){
				logger = new Logger();
			}
			logger.setStart(pagesize*pagenow-pagesize);
			logger.setEnd(pagesize);
			logList = loggerService.getLoggerUserList(logger);
			
			if(logList != null){
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
		ServletActionContext.getRequest().setAttribute("searchValue", 1);
		return "list2";
	}

	//事务列表
	public String list21()
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		String username = request.getParameter("username");
		logger = new Logger();
		logger.setUsername(username);
		logger.setSearchType("2");
		int totalsize = loggerService.getLoggerTotal(logger);
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 13 ;
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
			if(logger == null){
				logger = new Logger();
			}
			logger.setStart(pagesize*pagenow-pagesize);
			logger.setEnd(pagesize);
			logList = loggerService.getLoggerList(logger);
			
			if(logList != null){
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
		ServletActionContext.getRequest().setAttribute("searchValue", 1);
		return "list21";
	}

	//事务列表
	public String list22()
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		logger = new Logger();
		logger.setSearchType("4");
		int totalsize = loggerService.getLoggerTotal(logger);
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 16 ;
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
			if(logger == null){
				logger = new Logger();
			}
			logger.setStart(pagesize*pagenow-pagesize);
			logger.setEnd(pagesize);
			logList = loggerService.getLoggerList(logger);
			
			if(logList != null){
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
		ServletActionContext.getRequest().setAttribute("searchValue", 1);
		return "list22";
	}

	//事务列表
	public String list3()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		logger = new Logger();
		logger.setSearchType("3");
		
		int totalsize = loggerService.getLoggerTotal(logger);
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 18 ;
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
			if(logger == null){
				logger = new Logger();
			}
			logger.setStart(pagesize*pagenow-pagesize);
			logger.setEnd(pagesize);
			logList = loggerService.getLoggerList(logger);
			
			if(logList != null){
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
		ServletActionContext.getRequest().setAttribute("searchValue", 1);
		return "list3";
	}
	
	public String dsAdd() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		Para para = new Para();
		String temp = para.getPara("log_addr","file.properties","",false);
		add(temp+"action.log");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print("2");
		
		return "list1";
	}
	
	public String toList1()
	{
		return "list1";
	}
	
	public String toList2()
	{
		return "list2";
	}

	public String toList21()
	{
		return "list21";
	}
	
	public void add(String filename)
	{
		File file = null;
		BufferedReader reader = null;
		try {
			file = new File(filename);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			int temp_index = 0;
			
			while ((tempString = reader.readLine()) != null) {
				temp_index = tempString.indexOf("|") + 1;
				if (temp_index > 0) {
					tempString = tempString.substring(temp_index);
					String[] tempArray = tempString.split("#");
					Logger wLog = new Logger();
					wLog.setTypes(tempArray[0]);
					try {
						wLog.setTitle(tempArray[1]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setTitle("");
					}
					try {
						wLog.setFmail(tempArray[2]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setFmail("");
					}
					try {
						wLog.setTmail(tempArray[3]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setTmail("");
					}
					try {
						wLog.setTime(tempArray[4]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setTime("");
					}
					try {
						wLog.setState(tempArray[5]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setState("");
					}
					try {
						wLog.setUserid(tempArray[6]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setUserid("");
					}
					try {
						wLog.setUsername(tempArray[7]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setUsername("");
					}
					try {
						wLog.setOdata(tempArray[8]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setOdata("");
					}
					try {
						wLog.setIps(tempArray[9]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setIps("");
					}
					try {
						loggerService.addLogger(wLog);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			reader.close();
			FileWriter fw1 = new FileWriter(file, false);
			fw1.write("");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {}
			}
		}
	}

	public String find() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		logger = loggerService.findLogger(id);
		return "find";
	}

	public String delete() 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("id");
		loggerService.deleteLogger(ids);
		LogUtil.addLog(ServletActionContext.getRequest(), log, "日志系统", "", "", "", "", "删除日志信息", "");
		return "toList";
	}
	//发件统计
	public String list4(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String startTime = request.getParameter("startTime");
		SendFaileLog sendFaileLog = new SendFaileLog();
		List<String> frommailList = new ArrayList<String>();//保存frommail
		List<String> domainList = new ArrayList<String>();//保存域名
		List<SendFaileLog> sendCountList = new ArrayList<SendFaileLog>();
		sendFaileLog.setStartTime(startTime);//设置起始时间
		int totalsize = loggerService.sendCount(sendFaileLog);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 14 ;
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
			sendFaileLog.setStart(pagesize*pagenow-pagesize);
			sendFaileLog.setEnd(pagesize);
			
			sendCountList = loggerService.sendCountPage(sendFaileLog);//分页发件统计
			
			if(sendCountList!=null){
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
		for (int i = 0; i < sendCountList.size(); i++) {
			String frommail = sendCountList.get(i).getFrommail();
			String domain = sendCountList.get(i).getFrommail();
			int k = frommail.indexOf('<')+1;
			int j = frommail.indexOf('@');
			int len = frommail.length();
			if(k>0 && j>0){
				frommail = frommail.substring(k,j);//账号
			}
			domain = domain.substring(j+1,len-1);
			int m = domain.indexOf('>');
			if(m>0){
				domain = domain.substring(j,m);//域名
			}
			frommailList.add(frommail);
			domainList.add(domain);
		}
		
		request.setAttribute("sendCountList", sendCountList);
		request.setAttribute("frommailList",frommailList);	
		request.setAttribute("domainList",domainList);
		request.setAttribute("startTime",startTime);		
		return "list4";
	}
	
	//发件查询 根据frommail发件名
	public String getSendName(){
		HttpServletRequest request = ServletActionContext.getRequest();
		SendFaileLog sendFaileLog = new SendFaileLog();
		List<String> frommailList = new ArrayList<String>();//保存frommail
		List<String> domainList = new ArrayList<String>();//保存域名
		List<SendFaileLog> sendNameList = new ArrayList<SendFaileLog>();		
		String frommail = request.getParameter("frommail");
		int totalsize = loggerService.getSendNameCount(frommail);
		
		if(totalsize>0){
			String currentpage = request.getParameter("pagenow");
			int pagenow = 0 ;
			int pagesize = 14 ;
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
			sendFaileLog.setStart(pagesize*pagenow-pagesize);
			sendFaileLog.setEnd(pagesize);
			sendFaileLog.setFrommail(frommail);
			sendNameList = loggerService.getSendName(sendFaileLog);//分页发件统计
			if(sendNameList!=null){
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
		for (int i = 0; i < sendNameList.size(); i++) {
			String fm = sendNameList.get(i).getFrommail();
			String dm = sendNameList.get(i).getFrommail();
			int k = fm.indexOf('<')+1;
			int j = fm.indexOf('@');
			int len = fm.length();
			if(k>0 && j>0){
				fm = fm.substring(k,j);//账号
			}
			dm = dm.substring(j+1,len-1);
			int m = dm.indexOf('>');
			if(m>0){
				dm = dm.substring(j,m);//域名
			}
			frommailList.add(fm);
			domainList.add(dm);
		}
		
		request.setAttribute("sendNameList", sendNameList);
		request.setAttribute("frommailList",frommailList);	
		request.setAttribute("domainList",domainList);
		request.setAttribute("frommail",frommail);
		return "getSendName";
	}
}
