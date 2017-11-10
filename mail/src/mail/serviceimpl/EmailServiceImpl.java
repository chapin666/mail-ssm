package mail.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mail.bean.Email;
import mail.bean.EmailDrafts;
import mail.bean.MailData;
import mail.bean.User;
import mail.mapper.EmailMapper;
import mail.mapper.MailDataMapper;
import mail.send.MailSender;
import mail.service.EmailService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adm.bean.Notice;

import util.Analy;
import util.Files;
import util.Utf7Coder;

@Component("emailService")
public class EmailServiceImpl extends SqlSessionDaoSupport implements EmailService {

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private MailDataMapper emaildataMapper;

    public String parseSubject(MailData data){
    	String baseStr = null;
    	if(data.getSubject()!=null){
	        baseStr = data.getSubject().trim();
	        baseStr=Analy.encodeSubjectMail(baseStr);
    	}
        return baseStr;
    }
    
    public MailData getRecvMail(String mailId) {
        MailData data = emaildataMapper.getRecvData(mailId);
        if(data!=null){
        	String subject = data.getSubject().trim();
			data.setSubject(Analy.encodeSubjectMail(subject));
        }
        return data;
    }
    
    public MailData getSendMailByMailId(String mailId) {
        MailData data = emaildataMapper.getSendDataByMailId(mailId);
        if(data!=null){
        	String subject = data.getSubject().trim();
			data.setSubject(Analy.encodeSubjectMail(subject));
        }
        return data;
    }
    
    public MailData getSendMailById(int mailId) {
        MailData data = emaildataMapper.getSendDataById(mailId);
        if(data!=null){
        	data.setSubject(parseSubject(data));
        }
        return data;
    }
    
    public void updateDraftData(Email mail) {
        emaildataMapper.updateDraftData(mail);
    }

    public Email getEmail(String id) throws Exception {
        Email email = emailMapper.getEmail(id);
        if (email != null) {
        	MailData data = this.getRecvMail(email.getMailid());
            if(data!=null){//记录大于1条，取第一条记录
            	Analy.parse(email, data.getData());
            }
        }
        return email;
    }

    // 根据ID取草稿内容
    public Email getDrafts(int id) {
        Email mail = emailMapper.getDraft(id);
        if(mail!=null){
	        MailData data = emaildataMapper.getSendDataByMailId(mail.getMailid()+"");
	        mail.setTitle(data.getSubject());
	        mail.setFile(data.getFile());
	        mail.setFilename(data.getFilename());
	        try {
				Analy.parse(mail, data.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return mail;
    }

    // 根据用户信息取邮件内容
    public Email getDraftsOrSend(Email email) {
        Email rtnMail = emailMapper.getDraftsOrSend(email);
        if(rtnMail != null){
            email.setId(rtnMail.getId());
            MailData data = this.getSendMailById(rtnMail.getId());
            if(data != null){
                email.setContent(data.getData());
                email.setTitle(data.getSubject());
                email.setFile(data.getFile());
                email.setFilename(data.getFilename());
            }
        }
        return email;
    }

    public void editEmail(Email email) {
        emailMapper.editEmail(email);
        if(email!=null&&!email.getType().equals("1")){
        	emaildataMapper.updateDraftData(email);
        }
    }

    public void editREmail(Email email) {
        emailMapper.editREmail(email);
    }
    
    public void editEmails(Email email) {
        emailMapper.editEmails(email);
    }

    public void deleteEmail(Email email) {
        emailMapper.deleteEmail(email);
    }

    // 发送邮件，由SMTP服务器存数据库
    public void sendEmail(Email email,int state) throws Exception {
    	
    	String FilePath=MailSender.sendMail(email,state);
 
        // 保存邮件时
        if(state == 0)
        {
        	// 保存记录到数据库
            email.setMailfile(FilePath);
            email.setTomail(email.getToname());
            emailMapper.addDraftsEmail(email);
        }
    }

    // 发送邮件，由SMTP服务器存数据库
    public void sendDSEmail(Email email, User user, String fileurl) throws Exception {
        MailSender.sendDSMail(email, user, fileurl);
    }

    // 存草稿，直接存数据库，发送时要删除
    public void addEmail(Email email) {
        emailMapper.addEmail(email);
        email = this.getDraftsOrSend(email);
        emaildataMapper.saveDraftData(email);
    }

    // 存草稿
    public void addDraftsEmail(Email emailDrafts) {
        emailMapper.addDraftsEmail(emailDrafts);
    	//MailSender.sendMail(email);
    }

    // 存草稿
    public void editDraftsEmail(EmailDrafts emailDrafts) {
        emailMapper.editDraftsEmail(emailDrafts);
    }

    public Email getEmails(Email email) {
        return emailMapper.getEmails(email);
    }

    public void addEmails(Email email, List<String> recivelist, List<String> copytolist, List<String> bcclist) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (recivelist.size() > 0) {
                String sql = "insert into email (fromname,frommail,toname,tomail,realsend,copyto,bcc,title,content,file,filename,receipt,see,type,state,time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < recivelist.size(); i++) {
                    pstmt.setObject(1, email.getFromname());
                    pstmt.setObject(2, email.getFrommail());
                    pstmt.setObject(3, recivelist.get(i).replace("&lt;", "<").replace("&gt;", ">"));
                    pstmt.setObject(4, email.getTomail());
                    pstmt.setObject(5, email.getRealsend());
                    pstmt.setObject(6, "");
                    pstmt.setObject(7, "");
                    pstmt.setObject(8, email.getTitle());
                    pstmt.setObject(9, email.getContent());
                    pstmt.setObject(10, email.getFile());
                    pstmt.setObject(11, email.getFilename());
                    pstmt.setObject(12, email.getReceipt());
                    pstmt.setObject(13, email.getSee());
                    pstmt.setObject(14, email.getType());
                    pstmt.setObject(15, email.getState());
                    pstmt.setObject(16, email.getTime());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            if (copytolist.size() > 0) {
                String sql = "insert into email (fromname,frommail,toname,tomail,realsend,copyto,bcc,title,content,file,filename,receipt,see,type,state,time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < copytolist.size(); i++) {
                    pstmt.setObject(1, email.getFromname());
                    pstmt.setObject(2, email.getFrommail());
                    pstmt.setObject(3, "");
                    pstmt.setObject(4, email.getTomail());
                    pstmt.setObject(5, email.getRealsend());
                    pstmt.setObject(6, copytolist.get(i).replace("&lt;", "<").replace("&gt;", ">"));
                    pstmt.setObject(7, "");
                    pstmt.setObject(8, email.getTitle());
                    pstmt.setObject(9, email.getContent());
                    pstmt.setObject(10, email.getFile());
                    pstmt.setObject(11, email.getFilename());
                    pstmt.setObject(12, email.getReceipt());
                    pstmt.setObject(13, email.getSee());
                    pstmt.setObject(14, email.getType());
                    pstmt.setObject(15, email.getState());
                    pstmt.setObject(16, email.getTime());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            if (bcclist.size() > 0) {
                String sql = "insert into email (fromname,frommail,toname,tomail,realsend,copyto,bcc,title,content,file,filename,receipt,see,type,state,time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < bcclist.size(); i++) {
                    pstmt.setObject(1, email.getFromname());
                    pstmt.setObject(2, email.getFrommail());
                    pstmt.setObject(3, "");
                    pstmt.setObject(4, email.getTomail());
                    pstmt.setObject(5, email.getRealsend());
                    pstmt.setObject(6, "");
                    pstmt.setObject(7, bcclist.get(i).replace("&lt;", "<").replace("&gt;", ">"));
                    pstmt.setObject(8, email.getTitle());
                    pstmt.setObject(9, email.getContent());
                    pstmt.setObject(10, email.getFile());
                    pstmt.setObject(11, email.getFilename());
                    pstmt.setObject(12, email.getReceipt());
                    pstmt.setObject(13, email.getSee());
                    pstmt.setObject(14, email.getType());
                    pstmt.setObject(15, email.getState());
                    pstmt.setObject(16, email.getTime());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Email> reciveEmail(User user) {

    	List<Email> listMail = new ArrayList<Email>();
    	listMail = emailMapper.reciveEmail(user);
		
		List<Email> listMail2 = new ArrayList<Email>();
		
		for(Email data : listMail){
			String subject = data.getSubject().trim();
			data.setSubject(Analy.encodeSubjectMail(subject));
			listMail2.add(data);
		}
    	return listMail2;
    }

    public int getReciveSize(User user) {

        return emailMapper.getReciveSize(user);
    }

    public List<Email> getMainMail(User user) {

        return emailMapper.getMainMail(user);
    }

    public int getMainMailSize(User user) {

        return emailMapper.getMainMailSize(user);
    }

    /**新标未读邮件*/
    public int getMainMailSize1(User user) {

        return emailMapper.getMainMailSize1(user);
    }
    
    public List<Email> fromEmail(User user) {

        return emailMapper.fromEmail(user);
    }

    public int getFromSize(User user) {

        return emailMapper.getFromSize(user);
    }

    public List<Email> saveEmail(User user) {

        return emailMapper.saveEmail(user);
    }

    public int getSaveSize(User user) {

        return emailMapper.getSaveSize(user);
    }

    public List<Email> delEmail(User user) {

        return emailMapper.delEmail(user);
    }

    public int getDelSize(User user) {

        return emailMapper.getDelSize(user);
    }
    

    public List<Notice> getNews(User user) {

        return emailMapper.getNews(user);
    }

    public int getNewsSize(User user) {

        return emailMapper.getNewsSize(user);
    }

    public List<Notice> getAdmNews(User user) {

        return emailMapper.getAdmNews(user);
    }

    public int getAdmNewsSize(User user) {

        return emailMapper.getAdmNewsSize(user);
    }
    
    /**单位公告未读邮件*/
	public int getAdmNewsSize1(User user){
		return emailMapper.getAdmNewsSize1(user);
	}

    public Integer getNextEmail(User user) {

        return emailMapper.getNextEmail(user);
    }

    public Integer getPreEmail(User user) {

        return emailMapper.getPreEmail(user);
    }

    public void deleteEmails(String[] states, String[] types, String[] ids, String[] files, String[] frommails,
            String username) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
                String sql = "delete from email where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    if (!username.equals(frommails[i])) {
                        if (states[i].equals("6")) {
                            pstmt.setObject(1, ids[i]);
                            pstmt.addBatch();
                            Files.del(files[i]);
                        }
                    }
                }
                pstmt.executeBatch();
            }
            if (ids.length > 0) {
                String sql = "update email set type=6 where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    if (!username.equals(frommails[i])) {
                        if (!states[i].equals("6")) {
                            pstmt.setObject(1, ids[i]);
                            pstmt.addBatch();
                        }
                    }
                }
                pstmt.executeBatch();
            }
            if (ids.length > 0) {
                String sql = "delete from email where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    if (username.equals(frommails[i])) {
                        if (types[i].equals("6")) {
                            pstmt.setObject(1, ids[i]);
                            pstmt.addBatch();
                            Files.del(files[i]);
                        }
                    }
                }
                pstmt.executeBatch();
            }
            if (ids.length > 0) {
                String sql = "update email set state=6 where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    if (username.equals(frommails[i])) {
                        if (!types[i].equals("6")) {
                            pstmt.setObject(1, ids[i]);
                            pstmt.addBatch();
                        }
                    }
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmailSend(String[] states, String[] types, String[] ids, String[] files, String[] frommails,
            String username, String tag, int flag) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
                String sql = "delete from email_send where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            if (ids.length > 0) {
                String sql = "delete from emaildata_send where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteFEmails(String[] states, String[] types, String[] ids, String[] files, String[] frommails,
            String username, String tag, int flag) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
            	 String sql = "delete email_recv,emaildata_recv from email_recv,emaildata_recv where email_recv.id=? and email_recv.mailid=emaildata_recv.mailid";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editEmails(String[] ids, String[] frommails, String username, int type) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
                String sql = "update email set type=" + type + " where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    if (!username.equals(frommails[i])) {
                        pstmt.setObject(1, ids[i]);
                        pstmt.addBatch();
                    }
                }
                pstmt.executeBatch();
            }
            if (ids.length > 0) {
                String sql = "update email set state=" + type + " where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    if (username.equals(frommails[i])) {
                        pstmt.setObject(1, ids[i]);
                        pstmt.addBatch();
                    }
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delFEmails(String[] ids,String[] boxtype,int state) {
    	
    	// 这样获取的连接是关闭着的
        // Connection conn1 = getSqlSession().getConnection();
        //Connection conn2 = getSqlSession().getConnection();
    	
    	SqlSessionTemplate st = (SqlSessionTemplate) getSqlSession();

    	Connection conn1 = SqlSessionUtils.getSqlSession(
    	                 st.getSqlSessionFactory(), st.getExecutorType(),
    	                 st.getPersistenceExceptionTranslator()).getConnection();
    	Connection conn2 = SqlSessionUtils.getSqlSession(
                st.getSqlSessionFactory(), st.getExecutorType(),
                st.getPersistenceExceptionTranslator()).getConnection();

        PreparedStatement pstmt1 = null,pstmt2=null;
        
        try {
            if (ids.length > 0) {
            	String sql1="",sql2="";
            	sql1 = "delete email_recv,emaildata_recv from email_recv,emaildata_recv where email_recv.id=? and email_recv.mailid=emaildata_recv.mailid";
            	sql2 = "delete email_send,emaildata_send from email_send,emaildata_send where email_send.id=? and email_send.mailid=emaildata_send.mailid";
            	
                pstmt1 = conn1.prepareStatement(sql1);
                pstmt2 = conn2.prepareStatement(sql2);
                for (int i = 0; i < ids.length; i++) {
                	if(boxtype.length > i)
                	{
                		if(boxtype[i].equals("1")){
                			pstmt1.setObject(1, ids[i]);
                            pstmt1.addBatch();
                		}else if(boxtype[i].equals("2")){
                			pstmt2.setObject(1, ids[i]);
                            pstmt2.addBatch();
                		} 
                	}
                }
                pstmt1.executeBatch();
                pstmt2.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editFEmails(String[] ids,String[] boxtype,int state) {

        Connection conn1 = getSqlSession().getConnection();
        Connection conn2 = getSqlSession().getConnection();
        PreparedStatement pstmt1 = null,pstmt2=null;
        
        try {
            if (ids.length > 0) {
            	String sql1="",sql2="";
            	sql1 = "update email_recv set state=" + state + " where id=?";
            	sql2 = "update email_send set state=" + state + " where id=?";

                pstmt1 = conn1.prepareStatement(sql1);
                pstmt2 = conn2.prepareStatement(sql2);
                
                for (int i = 0; i < ids.length; i++) {
                 	if(boxtype.length > i)
                	{
                		if(boxtype[i].equals("1")){
                			pstmt1.setObject(1, ids[i]);
                            pstmt1.addBatch();
                		}else{
                			pstmt2.setObject(1, ids[i]);
                            pstmt2.addBatch();
                		} 
                	}
                }
                pstmt1.executeBatch();
                pstmt2.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void editFEmails(String[] ids,int state) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;
        
        try {
            if (ids.length > 0) {
            	String sql="";
            	sql = "update email_recv set state=" + state + " where id=?";


                pstmt = conn.prepareStatement(sql);
                
                for (int i = 0; i < ids.length; i++) {
                	pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void editEmailSend(String[] ids, String[] frommails, String username, int state, String[] types, int tag) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
                String sql = "update email_send set state=" + state + " where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editEmails(String[] ids) {

        Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
                String sql = "update email set see=1 where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delNews(String id) {
        emailMapper.delNews(id);
    }

    public void delAll() {
        emailMapper.delAllrecv();
        emailMapper.delAllsend();
        emailMapper.delAlldatarecv();
        emailMapper.delAlldatasend();
    }

	public void deleteSendEmail(String[] ids) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
            	String sql = "delete email_send,emaildata_send from email_send,emaildata_send where email_send.id=? and email_send.mailid=emaildata_send.mailid";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void editSendEmail(String[] mailids, int state) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (mailids.length > 0) {
                String sql = "update email_send set state=? where id=?";
                pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < mailids.length; i++) {
                    pstmt.setObject(1, state);
                    pstmt.setObject(2, mailids[i]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public String getEmailDraftsId() {
		return emailMapper.getEmailDraftsId();
	}

	public int getDraftsSize(User user) {
		return emailMapper.getDraftsSize(user);
	}

	public List<Email> draftsEmail(User user) {
		return emailMapper.draftsEmail(user);
	}

	public void deleteDrafts(String[] ids) {
		emailMapper.deleteDrafts(ids);
	}
	public Email getDraftsEmail(int id){
		 Email email = emailMapper.getDraftsEmail(id);
	        if (email != null) {
	            try {
					Analy.parse(email, email.getMailfile());
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        
		return email;
	}

	public void editDEmail(Email email) {
		
		emailMapper.editDEmail(email);
	}

	public Email getAllEmail(int id) throws Exception {
        Email email = emailMapper.getEmail(id+"");
        if (email != null) {
            email.setFrommail(email.getFrommail().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
            MailData data = this.getRecvMail(email.getMailid());
            email.setTitle(data.getSubject());
            email.setFile(data.getFile());
            email.setFilename(data.getFilename());
            Analy.parse(email, data.getData());
        }else{
        	email = emailMapper.getDraft(id);
        	if (email != null) {
                email.setFrommail(email.getFrommail().replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
                MailData data = this.getSendMailByMailId(email.getMailid());
                email.setTitle(data.getSubject());
                email.setFile(data.getFile());
                email.setFilename(data.getFilename());
                Analy.parse(email, data.getData());
            }
        }
        return email;
    }

	public void deleteDEmails(String[] ids, String[] tomails) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
	            String sql = "delete from email_send where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
	            	if(tomails[i].equals("-1")){
	                    pstmt.setObject(1, ids[i]);
	                    pstmt.addBatch();
	                }
	            }
	            pstmt.executeBatch();
            }
            if (ids.length > 0) {
	            String sql = "delete from email_recv where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
	            	if(!tomails[i].equals("-1")){
	                    pstmt.setObject(1, ids[i]);
	                    pstmt.addBatch();
	                }
	            }
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//查询所有已删除邮件
	public List<Email> delEmail1(User user){
		return emailMapper.delEmail1(user);
	}
	
//	/**清空所有已删除邮件*/    
//	public void clearDel(User user) {
//		List<Email> emaillist = new ArrayList<Email>();
//		emaillist = emailMapper.delEmail1(user);//查找所以已删邮件
//		Connection conn = null;
//        PreparedStatement pstmt = null;          
//        try {
//        	conn = getSqlSession().getConnection();//连接池
//        	
//            if (emaillist.size() > 0) {
//	            String sql = "delete from email_recv where id=?";	           
//	            pstmt = conn.prepareStatement(sql);	 
//	            
//	            for (int i = 0; i < emaillist.size(); i++) {	            	
//	            	if((emaillist.get(i).getTomail()).equals("-1")){
//	                    pstmt.setObject(1, emaillist.get(i).getId());
//	                    pstmt.addBatch();
//	                }
//	            }
//	            pstmt.executeBatch();
//            }
//            if (emaillist.size() > 0) {
//	            String sql = "delete from email_send where id=?";
//	           
//	            pstmt = conn.prepareStatement(sql);
//	            
//	            for (int i = 0; i < emaillist.size(); i++) {
//	            	if(!((emaillist.get(i).getTomail()).equals("-1"))){
//	                    pstmt.setObject(1, emaillist.get(i).getId());
//	                    pstmt.addBatch();
//	                }
//	            }
//	            pstmt.executeBatch();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//}  
	
	public void addNotice(Notice notice) {

		emailMapper.addNotice(notice);
	}

	public void editNotice(Notice notice) {

		emailMapper.editNotice(notice);
	}

	public Notice getNotice(String id) {

		return emailMapper.getNotice(id);
	}

	public void editReciveMail(String[] ids) {
		
		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
	            String sql = "update email_recv set state=3 where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
	            }
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void editSendMail(String[] ids) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
	            String sql = "update email_send set state=3 where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
	            }
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void deleteDraftsMail(String[] ids) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
	            String sql = "delete from email_drafts where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
	            }
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void deleteReciveMail(String[] ids) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
	            String sql = "delete from email_recv where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
	            }
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void deleteSendMail(String[] ids) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
	            String sql = "delete from email_send where id=?";
	            pstmt = conn.prepareStatement(sql);
	            
	            for (int i = 0; i < ids.length; i++) {
                    pstmt.setObject(1, ids[i]);
                    pstmt.addBatch();
	            }
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void deleteDelMail(String[] ids,String[] mailids) {

		Connection conn = getSqlSession().getConnection();
        PreparedStatement pstmt = null;

        try {
            if (ids.length > 0) {
            	String sql = "delete from email_recv where id=?";
            	Email email = new Email();
            	pstmt = conn.prepareStatement(sql);
            	Email email1 = new Email();
            	
            	for (int j = 0; j < ids.length; j++) {
            		email1.setId(Integer.parseInt(ids[j]));
            		email1.setMailid(mailids[j]);
            		email = emailMapper.getDEmail(email1);
            		if(email!=null){
            			pstmt.setObject(1, ids[j]);
                        pstmt.addBatch();
            		}
            	}
	            pstmt.executeBatch();
            }  
            if (ids.length > 0) {
            	String sql = "delete from email_send where id=?";
            	Email email = new Email();
            	pstmt = conn.prepareStatement(sql);
            	Email email1 = new Email();
            	
            	for (int j = 0; j < ids.length; j++) {
            		email1.setId(Integer.parseInt(ids[j]));
            		email1.setMailid(mailids[j]);
            		email = emailMapper.getDEmail(email1);
            		if(email==null){
            			pstmt.setObject(1, ids[j]);
                        pstmt.addBatch();
            		}
            	}
	            pstmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void updateReport(Email email) {
		emailMapper.updateReport(email);
		if(email.getbInsertSpamList() == 1)
		{
			emailMapper.InsertSpamList(email);
		}
	}
	
	/**删除用户黑名单*/
	public void delReport(Email email) {
		emailMapper.delSpamList(email);
	}
	public void addSpamUser(Email email){
		emailMapper.InsertSpamList(email);
	}
	
	/** 获取垃圾箱所有垃圾 记录  分页*/
	public List<Email> getRubMail(User user) {
		return emailMapper.getRubMail(user);
	}
	/** 获取垃圾箱所有邮件*/
	public List<Email> getRubMail1(User user) {
		return emailMapper.getRubMail1(user);
	}
	
	/** 获取垃圾箱所有垃圾总记录 */
	public int getRubMailCount(User user) {
		return emailMapper.getRubMailCount(user);
	}

	public void noRubMail(String[] ids) {
		emailMapper.noRubMail(ids);
	}

	
	public List<Email> SelectRecallMail(int id){
		return emailMapper.SelectRecallMail(id);
	}
	
	public void Recall(int id){
		emailMapper.Recall(id);
	}
	
	
	/**修改邮件状态*/	
	public void updateMeilState (String[] ids,int state){
		//emailMapper.updateMeilState(ids);
		Connection conn = getSqlSession().getConnection();//创建连接
		PreparedStatement pstmt = null;//预编译
		
			try {
				if(ids.length>0){
					String sql = "update email_recv set state = " + state + " where id = ?";
					pstmt = conn.prepareStatement(sql);
					for (int i = 0; i < ids.length; i++) {
						pstmt.setObject(1,ids[i]);
						pstmt.addBatch();
					}
					pstmt.executeBatch();//执行修改
				} 
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	/**查询最后一条单位公告信息*/
	public List<Notice> getLastNewsInfo(){		
		return emailMapper.getLastNewsInfo();
	}
	
	/**收件箱未读邮件*/
	public int getReciveSize1(User user){
		return emailMapper.getReciveSize1(user);
	}

	public List<Map<String, String>> getSpamListByUser(Map<String, Object> map) {
		
		return emailMapper.getSpamListByUser(map);
	}

	
	public void addSpamUserDomain(Email email) {
		emailMapper.InsertSpamListDomain(email);
	}

	public void delReportDomain(Email email) {
		emailMapper.delSpamListDomain(email);	
	}

	public List<Map<String, String>> getSpamListDomainByUser(
			Map<String, Object> map) {
		return emailMapper.getSpamListDomainByUser(map);
	}

	public int getSpamListCount(Map<String, Object> map) {
		return emailMapper.getSpamListCount(map);
	}

	public int getSpamListDomainCount(Map<String, Object> map) {
		return emailMapper.getSpamListDomainCount(map);
	}

	public void addWhite(Email email) {
		emailMapper.addWhite(email);
	}

	public void delWhite(Email email) {
		emailMapper.delWhite(email);
	}

	public int getWhiteCount(Map<String, Object> map) {
		return emailMapper.getWhiteCount(map);
	}

	public List<Map<String, String>> getWhites(Map<String, Object> map) {
		return emailMapper.getWhites(map);
	}

	public void addWhiteDomain(Email email) {
		emailMapper.addWhiteDomain(email);
	}

	public void delWhiteDomain(Email email) {
		emailMapper.delWhiteDomain(email);
	}

	public int getWhiteDomainCount(Map<String, Object> map) {
		return emailMapper.getWhiteDomainCount(map);
	}

	public List<Map<String, String>> getWhitesDomain(Map<String, Object> map) {
		return emailMapper.getWhitesDomain(map);
	}
}

	