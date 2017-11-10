package adm.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import mail.bean.Email;
import mail.bean.User;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.Analy;

import adm.bean.Logger;
import adm.bean.SendFaileLog;
import adm.mapper.LoggerMapper;
import adm.service.LoggerService;

@Component("loggerService")
public class LoggerServiceImpl implements LoggerService {
	
	@Autowired
	private LoggerMapper loggerMapper;
	
	public void addLogger(Logger logger) {
		loggerMapper.addLogger(logger);
	}

	public void deleteLogger(String[] ids) {
		loggerMapper.deleteLogger(ids);
	}

	public List<Logger> getLoggerList(Logger logger) throws RuntimeException {
		return loggerMapper.getLoggerList(logger);
	}

	public int getLoggerTotal(Logger logger) {
		return loggerMapper.getLoggerTotal(logger);
	}
	
	/**全部统计*/
	public int emailAllCount(Email email){
		return loggerMapper.emailAllCount(email);
	}
	
	/**全部查询*/
	public List<Email> getEmailAll(Email email){
		List<Email> listMail = new ArrayList<Email>();
    	listMail = loggerMapper.getEmailAll(email);		
    	return encodeSubjectMail(listMail);
	}
	
	/**收信统计*/
	public int emailRecvCount(Email email) {
		return loggerMapper.emailRecvCount(email);
	}
	
	/**收信查询*/
	public List<Email> getEmailRecv(Email email){
		List<Email> listMail = new ArrayList<Email>();
    	listMail = loggerMapper.getEmailRecv(email);		
    	return encodeSubjectMail(listMail);
	}
	
	/**发信统计*/
	public int emailSendCount(Email email) {
		return loggerMapper.emailSendCount(email);
	}
	
	/**发信查询*/
	public List<Email> getEmailSend(Email email){
		List<Email> listMail = new ArrayList<Email>();
		listMail = loggerMapper.getEmailSend(email);
		return encodeSubjectMail(listMail);
	}

	public Logger findLogger(String id) {
		return loggerMapper.findLogger(id);
	}

	public List<Logger> getLoggerUserList(Logger logger) throws RuntimeException {
		return this.loggerMapper.getLoggerUserList(logger);
	}

	public int getLoggerUserTotal(Logger logger) {
		return this.loggerMapper.getLoggerUserTotal(logger);
	}
	/**发件统计*/
	public int sendCount(SendFaileLog sendFaileLog) {
		return loggerMapper.sendCount(sendFaileLog);
	}	
	/**发件统计分页*/
	public List<SendFaileLog> sendCountPage(SendFaileLog sendFaileLog) {
		return loggerMapper.sendCountPage(sendFaileLog);
	}	
	/**根据发件名统计*/
	public int getSendNameCount(String frommail){
		return loggerMapper.getSendNameCount(frommail);
	}
	/**根据发件名统计*/
	public List<SendFaileLog> getSendName(SendFaileLog sendFaileLog){
		return loggerMapper.getSendName(sendFaileLog);
	}
	
	/**邮件subject编码*/
	public List<Email> encodeSubjectMail(List<Email> listMail){
		List<Email> listMail2 = new ArrayList<Email>();	
		Analy analy = new Analy();
		for(Email data : listMail){
			String subject = data.getSubject().trim();//去掉字符串左右的空格
			subject = analy.encodeSubjectMail(subject);//subject转码
			subject = subject.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'");
			data.setSubject(subject);
			String frommail = data.getFrommail();
			String tomail = data.getTomail();
			int i = frommail.indexOf('<');
			int j = frommail.indexOf('@');
			if(i>=0&&j>0){
				data.setFromname(frommail.substring(i+1,j));				
			}
			int n = tomail.indexOf('<');
			int m = tomail.indexOf('@');
			if(n>=0&&m>0){
				data.setToname(tomail.substring(n+1,m));
			}		
			listMail2.add(data);
		}
		return listMail2;
	}
}
