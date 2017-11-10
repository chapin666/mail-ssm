package mail.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import adm.bean.Notice;

import mail.bean.Email;
import mail.bean.EmailDrafts;
import mail.bean.MailData;
import mail.bean.User;

public interface EmailService {
		
    public MailData getRecvMail(String mailId);

    public MailData getSendMailByMailId(String mailId);

	public Email getEmail(String id) throws Exception;
	
    public Email getDrafts(int id);

    public String getEmailDraftsId();

	public void editEmail(Email email);
	
	public void editEmails(Email email);

	public void deleteEmail(Email email);
	
	public void sendEmail(Email email,int state) throws Exception;

	public void sendDSEmail(Email email, User user, String fileurl) throws Exception;
	
	public void addEmail(Email email);

	public void addDraftsEmail(Email emailDrafts);

	public void editDraftsEmail(EmailDrafts emailDrafts);
	
	public Email getEmails(Email email);
	
    public Email getDraftsOrSend(Email email);

	public void addEmails(Email email, List<String> alllist, List<String> copytolist, List<String> bcclist);

	public List<Email> reciveEmail(User user);

	public int getReciveSize(User user);
	
	/**收件箱未读邮件*/
	public int getReciveSize1(User user);

	public void deleteEmails(String[] states, String[] types, String[] ids, String[] files, String[] frommails, String username);

	public void editEmails(String[] ids, String[] frommails, String username, int type);

	public void editEmails(String[] ids);

	public int getFromSize(User user);

	public List<Email> fromEmail(User user);

	public void deleteFEmails(String[] states, String[] types, String[] ids, String[] files, String[] frommails, String username, String tag, int flag);

	// 已删除里面改变邮件状态
	public void editFEmails(String[] ids,String[] tomails,int tag);
	
	public void editFEmails(String[] ids,int tag);
	
	// 彻底删除
	public void delFEmails(String[] ids,String[] tomails,int tag);

    public void deleteEmailSend(String[] states, String[] types, String[] ids, String[] files, String[] frommails, String username, String tag, int flag);
    
    public void editEmailSend(String[] ids, String[] frommails, String username, int state, String[] types, int tag);
    
	public int getSaveSize(User user);

	public List<Email> saveEmail(User user);

	public int getDraftsSize(User user);

	public List<Email> draftsEmail(User user);
	public Email getDraftsEmail(int id);
	public void deleteDrafts(String[] ids);

	public int getDelSize(User user);	
	 /**清空所有已删除邮件*/
    //public void clearDel(User user);
	/**查询所有已删除邮件*/
    public List<Email> delEmail1(User user);
    
	public List<Email> delEmail(User user);

	public int getNewsSize(User user);

	public List<Notice> getNews(User user);

	public int getAdmNewsSize(User user);
	
	 /**单位公告未读邮件*/
	public int getAdmNewsSize1(User user);

	public List<Notice> getAdmNews(User user);

	public int getMainMailSize(User user);
	
	 /**新标未读邮件*/
    public int getMainMailSize1(User user);

	public List<Email> getMainMail(User user);

	public Integer getNextEmail(User user);
	
	public Integer getPreEmail(User user);

	public void delNews(String id);

	public void delAll();

    public MailData getSendMailById(int id);
    
    public void updateDraftData(Email mail);

	public void deleteSendEmail(String[] mailids);

	public void editSendEmail(String[] mailids, int state);

	public void editREmail(Email email);

	public void editDEmail(Email email);

	public Email getAllEmail(int iid) throws Exception;

	public void deleteDEmails(String[] ids, String[] tomails);
	
	public String parseSubject(MailData data);

	public void addNotice(Notice notice);

	public void editNotice(Notice notice);

	public Notice getNotice(String id);

	public void editReciveMail(String[] ids);

	public void editSendMail(String[] ids);

	public void deleteDraftsMail(String[] ids);

	public void deleteReciveMail(String[] ids);

	public void deleteSendMail(String[] ids);

	public void deleteDelMail(String[] ids,String[] mailids);
	
	public void updateReport(Email email);
	
	/**删除用户黑名单*/
	public void delReport(Email email);
	public void addSpamUser(Email email);
	public List<Map<String,String>> getSpamListByUser(Map<String, Object> map);
	public int getSpamListCount(Map<String, Object> map);
	
	public void delReportDomain(Email email);
	public void addSpamUserDomain(Email email);
	public List<Map<String,String>> getSpamListDomainByUser(Map<String, Object> map);
	public int getSpamListDomainCount(Map<String, Object> map);
	
	/**用户白名单*/
	public void delWhite(Email email);
	public void addWhite(Email email);
	public List<Map<String,String>> getWhites(Map<String, Object> map);
	public int getWhiteCount(Map<String, Object> map);
	/**用户域名白名单*/
	public void delWhiteDomain(Email email);
	public void addWhiteDomain(Email email);
	public List<Map<String,String>> getWhitesDomain(Map<String, Object> map);
	public int getWhiteDomainCount(Map<String, Object> map);
	
	
	/** 获取垃圾箱所有垃圾记录  分页*/
	public List<Email> getRubMail(User user);
	
	/** 获取垃圾箱所有邮件*/
	public List<Email> getRubMail1(User user);
	
	/** 获取垃圾箱所有垃圾总记录 */
	public int getRubMailCount(User user);
	
	/**这不是垃圾*/
	public void noRubMail (String[] ids);

	
	public List<Email> SelectRecallMail(int id);
	
	public void Recall(int id);
	
	/**修改邮件状态*/	
	public void updateMeilState (String[] ids,int state);
	
	/**查询最后一条单位公告信息*/
	public List<Notice> getLastNewsInfo();
}