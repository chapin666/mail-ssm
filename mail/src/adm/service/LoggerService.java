package adm.service;

import java.util.List;

import mail.bean.Email;
import mail.bean.User;

import adm.bean.Logger;
import adm.bean.SendFaileLog;

public interface LoggerService {

	public void addLogger(Logger logger);
	
	public void deleteLogger(String[] ids);

	public List<Logger> getLoggerList(Logger logger) throws RuntimeException;

	public int getLoggerTotal(Logger logger);
	
	/**全部统计*/
	public int emailAllCount(Email email);
	
	/**全部查询*/
	public List<Email> getEmailAll(Email email);
	
	/**收信统计*/
	public int emailRecvCount(Email email);
	
	/**收信查询*/
	public List<Email> getEmailRecv(Email email);
	
	/**发信统计*/
	public int emailSendCount(Email email);
	
	/**发信查询*/
	public List<Email> getEmailSend(Email email);
	
	public Logger findLogger(String id);

	public List<Logger> getLoggerUserList(Logger logger) throws RuntimeException;

	public int getLoggerUserTotal(Logger logger);
	/**发件统计*/
	public int sendCount(SendFaileLog sendFaileLog);
	/**发件统计分页*/
	public List<SendFaileLog> sendCountPage(SendFaileLog sendFaileLog);
	/**根据发件名统计*/
	public int getSendNameCount(String frommail);
	/**根据发件名统计*/
	public List<SendFaileLog> getSendName(SendFaileLog sendFaileLog);
}
