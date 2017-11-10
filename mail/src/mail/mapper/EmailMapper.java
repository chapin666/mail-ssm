package mail.mapper;

import java.util.List;
import java.util.Map;

import mail.bean.Config;
import mail.bean.Email;
import mail.bean.EmailDrafts;
import mail.bean.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import adm.bean.Notice;

public interface EmailMapper {
			
    @Select("select * from email_recv where id=#{id}")
	public Email getEmail(String id);
    
    @Select("select * from email_send where id=#{id}")
    public Email getDraft(int id);
    
	public void editEmail(Email email);
	
	public void editEmails(Email email);

	public void addEmail(Email email);

	public void addDraftsEmail(Email emailDrafts);

	public void editDraftsEmail(EmailDrafts emailDrafts);
	
	public void deleteEmail(Email email);

	public Email getEmails(Email email);
	
    public Email getDraftsOrSend(Email email);

    public String getEmailDraftsId();
    //@Select("SELECT * from emaildata_recv a,email_recv b where a.mailid = b.mailid and b.tomail like '%${username}%' and (b.state = #{phone} or b.state = #{stat}) order by #{orby} '+#{sortorder}+' limit #{start},#{end}")
	//@Select("select * from email_recv where tomail like '%${username}%' and (state = #{phone} or state = #{stat}) order by #{orby} desc limit #{start},#{end}")
	public List<Email> reciveEmail(User user);

	@Select("select count(1) from email_recv where tomail like '%${username}%' and (state = #{phone} or state = #{stat})")
	public int getReciveSize(User user);

	@Select("select a.*,b.subject as title,b.file,b.filename,b.data as content from email_send a, emaildata_send b where a.frommail like '%${username}%' and a.state in(1) and a.mailid=b.mailid order by a.time desc limit #{start},#{end}")
	public List<Email> fromEmail(User user);

	@Select("select count(1) from email_send a, emaildata_send b where a.frommail like '%${username}%' and a.state in(1) and a.mailid=b.mailid")
	public int getFromSize(User user);

	@Select("select * from email_send where frommail like '%${username}%' and state in(0) order by time desc limit #{start},#{end}")
	public List<Email> saveEmail(User user);

	@Select("select count(1) from email_send where frommail like '%${username}%' and state in(0)")
	public int getSaveSize(User user);

	@Select("select a.id as id,a.frommail as frommail,a.tomail as tomail,a.copy as copyto,a.bcc as bcc,a.title as title,a.content as content,a.attachFile as filename,a.attachPath as file,a.time as time,a.ismain as ismain,a.see as see from email_drafts a where frommail like '%${username}%' order by time desc limit #{start},#{end}")
	public List<Email> draftsEmail(User user);

	@Select("select a.id id,a.frommail frommail,a.tomail tomail,a.copy copyto,a.bcc bcc,a.title title,a.content content,a.attachFile filename,a.attachPath file,a.time time,a.mailfile mailfile,a.ismain ismain,a.see see from email_drafts a where id = #{id}")
	public Email getDraftsEmail(int id);

	@Select("select count(1) from email_drafts where frommail like '%${username}%'")
	public int getDraftsSize(User user);

	public void deleteDrafts(String[] ids);

	@Select("select *,1 as boxtype from email_recv where tomail like '%${username}%' and state in(3) union select b.id,b.mailid,b.frommail,b.tomail,b.time,b.ismain,b.state,2 from email_send b where b.frommail like '%${username}%' and b.state in(3) order by time desc limit #{start},#{end}")
	public List<Email> delEmail(User user);

	@Select("select count(1)+(select count(1) from email_send where frommail like '%${username}%'  and state in(3)) from email_recv where tomail like '%${username}%'  and state in(3)")
	public int getDelSize(User user);
	
	/**清空所有已删除邮件*/
	//public int clearDel(User user);
	/**查询所有已删除邮件*/
	@Select("select * from email_recv where tomail like '%${username}%' and state in(3) union select b.id,b.mailid,b.frommail,-1,b.time,b.ismain,b.state from email_send b where b.frommail like '%${username}%' and b.state in(3)")
	public List<Email> delEmail1(User user);
	
	public List<Notice> getNews(User user);

	public int getNewsSize(User user);

	@Select("select * from email_notice where tomail like '%${username}%' and (state=1 or state = 0) order by time desc limit #{start},#{end}")
	public List<Notice> getAdmNews(User user);

	@Select("select count(1) from email_notice where tomail like '%${username}%' and (state=1 or state = 0)")
	public int getAdmNewsSize(User user);
	
	/**单位公告未读邮件*/
	@Select("select count(1) from email_notice where tomail like '%${username}%' and state = 0")
	public int getAdmNewsSize1(User user);

	public List<Email> getMainMail(User user);

	public int getMainMailSize(User user);
	
	/**新标未读邮件*/
    public int getMainMailSize1(User user);


	public Integer getNextEmail(User user);
	
	public Integer getPreEmail(User user);

	public void delNews(String id);

	public void delAllrecv();
	public void delAllsend();
	public void delAlldatarecv();
	public void delAlldatasend();
	
	@Select("select * from configinfo where name = #{name}")
	public Config config(String name);

	public void editREmail(Email email);

	public void editDEmail(Email email);

	public void addNotice(Notice notice);

	public void editNotice(Notice notice);

	@Select("select * from email_notice where id = #{id}")
	public Notice getNotice(String id);

	public Email getDEmail(Email email);
	
	/**修改举报信息*/
	public void updateReport(Email email);	
	/**插入用户黑名单*/
	public void InsertSpamList(Email email);
	/**删除用户黑名单*/
	@Delete("delete from spamuser where user = #{tomail} and reportuser = #{frommail}")
	public void delSpamList(Email email);
	@Select("select * from spamuser where user = #{tomail} limit #{offset},#{rows}")
	public List<Map<String,String>> getSpamListByUser(Map<String, Object> map);
	@Select("SELECT COUNT(*) from spamuser where user = #{tomail}")
	public int getSpamListCount(Map<String, Object> map);
	
	/**插入用户黑名单 域名*/
	public void InsertSpamListDomain(Email email);
	/**删除用户黑名单 域名*/
	@Delete("delete from spamuser_domain where user = #{tomail} and reportuser = #{frommail}")
	public void delSpamListDomain(Email email);
	@Select("select * from spamuser_domain where user = #{tomail}  limit #{offset},#{rows}")
	public List<Map<String,String>> getSpamListDomainByUser(Map<String, Object> map);
	@Select("SELECT COUNT(*) from spamuser_domain where user = #{tomail}")
	public int getSpamListDomainCount(Map<String, Object> map);
	
	/**用户地址白名单 */
	@Delete("delete from spamuser_white where user = #{tomail} and reportuser = #{frommail}")
	public void delWhite(Email email);
	@Insert("insert into spamuser_white(user,reportuser) value(#{tomail},#{frommail})")
	public void addWhite(Email email);
	@Select("select * from spamuser_white where user = #{tomail}  limit #{offset},#{rows}")
	public List<Map<String,String>> getWhites(Map<String, Object> map);
	@Select("SELECT COUNT(*) from spamuser_white where user = #{tomail}")
	public int getWhiteCount(Map<String, Object> map);
	
	/**用户域名白名单 */
	@Delete("delete from spamuser_white_domain where user = #{tomail} and reportuser = #{frommail}")
	public void delWhiteDomain(Email email);
	@Insert("insert into spamuser_white_domain(user,reportuser) value(#{tomail},#{frommail})")
	public void addWhiteDomain(Email email);
	@Select("select * from spamuser_white_domain where user = #{tomail}  limit #{offset},#{rows}")
	public List<Map<String,String>> getWhitesDomain(Map<String, Object> map);
	@Select("SELECT COUNT(*) from spamuser_white_domain where user = #{tomail}")
	public int getWhiteDomainCount(Map<String, Object> map);
	
	
	
	
	/** 获取垃圾箱所有垃圾记录 分页 */
	@Select("select * from email_recv where state = 4 and tomail like '%${username}%' limit #{start},#{end}")
	public List<Email> getRubMail(User user);
	
	/** 获取垃圾箱所有邮件*/
	@Select("select * from email_recv where state = 4  and tomail like '%${username}%'")
	public List<Email> getRubMail1(User user);
	
	/** 获取垃圾箱所有垃圾总记录 */
	@Select("SELECT COUNT(*) from email_recv where state = 4 and tomail like '%${username}%' ")
	public int getRubMailCount(User user);
	
	/**这不是垃圾*/
	public void noRubMail(String[] ids);

	
	// 查看发送的邮件状态
	public List<Email> SelectRecallMail(int id);
	
	// 撤回邮件
	public void Recall(int id);
	
	/**查询最后一条单位公告信息*/
	@Select("SELECT * from email_notice ORDER BY time DESC LIMIT 0,1")
	public List<Notice> getLastNewsInfo();
	
	/**收件箱未读邮件*/
	@Select("select count(1) from email_recv where tomail like '%${username}%' and state = #{stat}")
	public int getReciveSize1(User user);

}
