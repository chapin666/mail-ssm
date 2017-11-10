package adm.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import adm.bean.KeyWordRule;
import adm.bean.SendFaileLog;
import adm.bean.spamlist;
import adm.bean.traitlib;
import adm.service.MailSpamService;

@Component("EmailspamAction")
@Scope("prototype")
public class EmailspamAction {

	@Autowired
	private MailSpamService mailSpamService;

	private List<spamlist> spamlists;
	private List<traitlib> traitlibs;
	private List<SendFaileLog> SendFaileLogs;

	private List<KeyWordRule> keyWordRule;

	private int count;
	private int pageNumber;
	private int pageNow;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public List<spamlist> getSpamlists() {
		return spamlists;
	}

	public void setSpamlists(List<spamlist> spamlists) {
		this.spamlists = spamlists;
	}

	public List<traitlib> getTraitlibs() {
		return traitlibs;
	}

	public void setTraitlibs(List<traitlib> traitlibs) {
		this.traitlibs = traitlibs;
	}

	public List<SendFaileLog> getSendFaileLogs() {
		return SendFaileLogs;
	}

	public void setSendFaileLogs(List<SendFaileLog> sendFaileLogs) {
		SendFaileLogs = sendFaileLogs;
	}

	public List<KeyWordRule> getKeyWordRule() {
		return keyWordRule;
	}

	public void setKeyWordRule(List<KeyWordRule> keyWordRule) {
		this.keyWordRule = keyWordRule;
	}

	public String tzk() {
		return "success";
	}

	public String mailspamlog() {
		return "success";
	}

	public String mailspamglobal() {
		return "success";
	}

	public String mailfilter1() {
		return "success";
	}

	public String mailfilter2() {
		return "success";
	}

	// 添加黑名单
	public String Addblacklist() {
		spamlist sl = new spamlist();
		String address = ServletActionContext.getRequest().getParameter(
				"address");
		sl.setAddress(address);
		sl.setState(1);
		sl.setTag(1);
		mailSpamService.Addspamlist(sl);
		return "success";
	}

	// 添加白名单
	public String Addwhitelist() {
		spamlist sl = new spamlist();
		String address = ServletActionContext.getRequest().getParameter(
				"address");
		sl.setAddress(address);
		sl.setState(1);
		sl.setTag(0);
		mailSpamService.Addspamlist(sl);
		return "success";
	}

	// 查看黑名单
	public String spamlistblack() {

		spamlist sl = new spamlist();
		count = mailSpamService.spamlistCount(1);
		if (count % 10 > 0)
			pageNumber = count / 10 + 1;
		else
			pageNumber = count / 10;
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
		sl.setTag(1);
		if (pageNow * 10 > count) {

			// sl.setEnd(count);
			if ((pageNumber - 1) * 10 > 0)
				sl.setStart((pageNumber - 1) * 10);
			else
				sl.setStart(0);
			pageNow = pageNumber;
		} else {
			// sl.setEnd(pageNow * 10);
			sl.setStart(pageNow * 10 - 10);
		}
		sl.setEnd(10);
		spamlists = mailSpamService.selectspamlist(sl);
		return "success";
	}

	// 查看白名单

	public String spamlistwhite() {
		spamlist sl = new spamlist();
		count = mailSpamService.spamlistCount(0);
		if (count % 10 > 0)
			pageNumber = count / 10 + 1;
		else
			pageNumber = count / 10;
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
		sl.setTag(0);
		if (pageNow * 10 > count) {
			// sl.setEnd(count);
			if ((pageNumber - 1) * 10 > 0)
				sl.setStart((pageNumber - 1) * 10);
			else
				sl.setStart(0);
			pageNow = pageNumber;

		} else {
			// sl.setEnd(pageNow * 10);
			sl.setStart(pageNow * 10 - 10);
		}
		sl.setEnd(10);
		spamlists = mailSpamService.selectspamlist(sl);
		return "success";
	}

	// 删除名单（白黑）
	public String deletespamlist() {
		int id = Integer.parseInt(ServletActionContext.getRequest()
				.getParameter("id"));
		mailSpamService.deletespamlist(id);
		return "success";
	}

	public String deletes() {
		String[] ids = ServletActionContext.getRequest().getParameterValues("ids");
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				mailSpamService.deletespamlist(Integer.parseInt(ids[i]));
			}
		}
		return "success";
	}

	// 删除所有 清空(黑白名单)
	public String deleteAll() {
		int tag = Integer.parseInt(ServletActionContext.getRequest().getParameter("tag"));
		mailSpamService.deleteAll(tag);
		return "success";
	}

	// 查看更新特征库

	public String selectTraitlibs() {

		traitlib t = new traitlib();
		count = mailSpamService.traitlibCount();
		if (count % 10 > 0)
			pageNumber = count / 10 + 1;
		else
			pageNumber = count / 10;
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
		if (pageNow * 10 > count) {
			// t.setEnd(count);
			if ((pageNumber - 1) * 10 > 0)
				t.setStart((pageNumber - 1) * 10);
			else
				t.setStart(0);
			pageNow = pageNumber;

		} else {
			// t.setEnd(pageNow * 10);
			t.setStart(pageNow * 10 - 10);
		}
		t.setEnd(10);
		traitlibs = mailSpamService.selectTraitlibs(t);
		return "success";
	}
	
	//删除特征库记录
	public String delTraitlibs(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		
		traitlib t = new traitlib();
		t.setId(Integer.parseInt(id));
		mailSpamService.delTraitlibs(t);
		selectTraitlibs();
		return "success";
	}
	

	// 查询投递日志失败信息
	public String selectSendFaileLogs() {
		SendFaileLog log = new SendFaileLog();
		count = mailSpamService.SendFaileLogsCount();
		if (count % 10 > 0)
			pageNumber = count / 10 + 1;
		else
			pageNumber = count / 10;
		
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
		if (pageNow * 10 > count) {
			if ((pageNumber - 1) * 10 > 0)
				log.setStart((pageNumber - 1) * 10);//最后一页
			else
				log.setStart(0);//第一页
			pageNow = pageNumber;

		} else {
			log.setStart(pageNow * 10 - 10);//当前页
		}
		log.setEnd(10);//每页10条
		SendFaileLogs = mailSpamService.selectSendFaileLogs(log);
		return "success";

	}

	// 关键字规则
	public String selectKeyWordRule() {

		KeyWordRule kwr = new KeyWordRule();
		count = mailSpamService.KeyWordRuleCount();//总记录
		//总页
		if (count % 10 > 0) {
			pageNumber = count / 10 + 1; 
		}

		else {
			pageNumber = count / 10;      
		}

		String i = ServletActionContext.getRequest().getParameter("i");//接受从页面传过来的页码
		if (i != null) {
			pageNow = Integer.parseInt(i);//当前页
			if (pageNow > 0) {
				pageNow = Integer.parseInt(i);
			} else {
				pageNow = 1;
			}
		} else {
			pageNow = 1;
		}
		if (pageNow * 10 > count) {
			if (pageNumber > 1) {
				kwr.setStart((pageNumber - 1) * 10);
			}
			else {
				kwr.setStart(0);
			}
			pageNow = pageNumber;
		} else {
			kwr.setStart(pageNow * 10 - 10);
		}
		kwr.setEnd(10);
		keyWordRule = mailSpamService.selectKeyWordRule(kwr);
		return "success";
	}
	//根据关键字规则查询
	public String mailKeyMore(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//接收页面参数
		String name = request.getParameter("name");
		String lastTime = request.getParameter("lastTime");
		KeyWordRule kwr = new KeyWordRule();
		
		kwr.setName(name);
		kwr.setLasttime(lastTime);
		
		count = mailSpamService.kwrCount(kwr);//总记录
		//总页
		if (count % 10 > 0) {
			pageNumber = count / 10 + 1; 
		}

		else {
			pageNumber = count / 10;      
		}

		String i = ServletActionContext.getRequest().getParameter("i");//接受从页面传过来的页码
		if (i != null) {
			pageNow = Integer.parseInt(i);//当前页
			if (pageNow > 0) {
				pageNow = Integer.parseInt(i);
			} else {
				pageNow = 1;
			}
		} else {
			pageNow = 1;
		}
		if (pageNow * 10 > count) {
			if (pageNumber > 1) {
				kwr.setStart((pageNumber - 1) * 10);
			}
			else {
				kwr.setStart(0);
			}
			pageNow = pageNumber;
		} else {
			kwr.setStart(pageNow * 10 - 10);
		}
		kwr.setEnd(10);
		
		
		keyWordRule = mailSpamService.mailKeyMore(kwr);
		return "success";
	}
	
	//删除关键字记录
	public String delKeyWordRule(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("keyWordRuleids");
		mailSpamService.delKeyWordRule(ids);		
		return "success";
	}
	
	//修改关键字规则状态
	public String updateState(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("keyWordRuleids");		
		int state = Integer.parseInt(request.getParameter("state"));
		KeyWordRule kwr = new KeyWordRule();
		for(int i=0; i<ids.length; i++){
			kwr.setId(Integer.parseInt(ids[i]));
			kwr.setState(state);
			mailSpamService.updateState(kwr);
		}
		return "success";
	}
	
	//添加关键字规则
	public String addKeyWordRule(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		int grade = Integer.parseInt(request.getParameter("grade"));
		String addresser = request.getParameter("addresser");
		String addressee = request.getParameter("addressee");
		String title = request.getParameter("title");
		String mainbody = request.getParameter("mainbody");
		
		KeyWordRule kwr = new KeyWordRule();
		
		kwr.setName(name);
		kwr.setGrade(grade);
		kwr.setAddresser(addresser);
		kwr.setAddressee(addressee);
		kwr.setTitle(title);
		kwr.setMainbody(mainbody);		
		mailSpamService.addKeyWordRule(kwr);
		
		return "success";
	}	
	
	//投递日志失败分析查询
	public String selectTimeLog(){		
		HttpServletRequest request = ServletActionContext.getRequest();
		//接收页面参数
		String startTime = request.getParameter("startTime")+" 00:00:00";
		String endTime = request.getParameter("endTime")+" 59:59:59";
		SendFaileLog log = new SendFaileLog();
		log.setStartTime(startTime);
		log.setEndTime(endTime);		
		count = mailSpamService.logCount(log);//总记录
		//总页
		if (count % 10 > 0) {
			pageNumber = count / 10 + 1; 
		}

		else {
			pageNumber = count / 10;      
		}

		String i = ServletActionContext.getRequest().getParameter("i");//接受从页面传过来的页码
		if (i != null) {
			pageNow = Integer.parseInt(i);//当前页
			if (pageNow > 0) {
				pageNow = Integer.parseInt(i);
			} else {
				pageNow = 1;
			}
		} else {
			pageNow = 1;
		}
		if (pageNow * 10 > count) {
			if (pageNumber > 1) {
				log.setStart((pageNumber - 1) * 10);
			}
			else {
				log.setStart(0);
			}
			pageNow = pageNumber;
		} else {
			log.setStart(pageNow * 10 - 10);
		}
		log.setEnd(10);			
		SendFaileLogs = mailSpamService.selectTimeLog(log);
		return "success";
	}
}
