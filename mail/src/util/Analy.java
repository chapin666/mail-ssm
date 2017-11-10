package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.QuotedPrintableCodec;

import mail.bean.Email;


import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

class PairFile {

	public String filename = "";
	public String path = "";

	PairFile(String name, String path) {
		filename = name;
		this.path = path;
	}
}

public class Analy {

	private static String dir = Cache.cache.get("filedir"); //目录以"/"结束
	
	private String filename = "";
	private String messageID = ""; // 邮件ID
	private String subject = ""; // 邮件标题
	private String from = ""; // 发件人帐号和名称
	private String to = ""; // 邮件收件人
	private String cc = ""; // 邮件收件人
	private String bcc = ""; // 邮件收件人
	private String date = ""; // 日期
	private boolean hasAttachment = false; // 是否包含附件
	private String contentHtml = ""; // html格式邮件正文
	private String contentText = ""; // text格式邮件正文

    MimeMessage mimeMessage;
    
	// 装载附件
	private Vector<PairFile> vecAttach = new Vector<PairFile>();

	public static void main(String[] args) throws Exception {
		Email email = new Email();
		new Analy().parse(email);
	}

	/**
	 * 外部调用接口
	 * @param email
	 * @param data
	 * @throws Exception 
	 */
	public static void parse(Email email, String filePath) throws Exception{
		Analy analy = new Analy();
		analy.filename = dir + filePath;
		analy.parse(email);
	}
	
	public void parse(Email email) throws Exception {
		
		// 创建目录
		File Fdir = new File(dir+"temp");
		if (Fdir.exists() == false){
			Fdir.mkdirs();
		}
		
	    try {
	        InputStream fis = new FileInputStream(filename);
	        Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
	        mimeMessage = new MimeMessage(mailSession,fis);
	        // 发件人
	        from=getFrom();
	        // 收件人
	        to=getMailAddress("TO");
	        // 抄送
	        cc=getMailAddress("CC");
	        // 密送
	        bcc=getMailAddress("BCC");
	        
	        // 主题
	        subject=getSubject();
	        // 时间
	        date=getSentDate();
	        // 邮件ID
	        messageID=mimeMessage.getMessageID();
	        // 正文
	        getMailContent(mimeMessage);
	        
	        // 附件判断
	        hasAttachment=isContainAttach(mimeMessage);
	        if(hasAttachment)
	        {
	        	saveAttachMent(mimeMessage);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		email.setFrommail(from);
		email.setToname(to);
		email.setCopyto(cc);
		email.setBcc(bcc);
		email.setTitle(subject);
		email.setTime(date);
		if(contentHtml.compareTo("") == 0)
		{
			// 将换行符转换为标签模式
			contentText=contentText.replace("\r\n", "<br>");
			email.setContent(contentText);
		}else
		{
			email.setContent(contentHtml);
		}
		
		// 将附件信息拼成字符串
		String TotailFile="";
		for(PairFile file:vecAttach)
		{
			TotailFile+=file.filename+"*";
			TotailFile+=file.path+"|";
		}
		email.setFile(TotailFile);
	}
	
	/**
	 * 获得发件人的地址和姓名   
	 */    
	public String getFrom() throws Exception {     
	    InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom(); 
	    if(address == null || address.length < 1){
	    	return "";
	    }
	    String from = address[0].getAddress();     
	    if (from == null)     
	        from = "";     
	    String personal = address[0].getPersonal();     
	    if (personal == null)     
	        personal = "";     
	    String fromaddr = personal + "<" + from + ">";
	    
	    return fromaddr;     
	}

	/**   
	 * 获得邮件发送日期   
	 */    
	public String getSentDate() throws Exception {     
	    Date sentdate = mimeMessage.getSentDate();     
	    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日(E) HH:mm");
	    if(sentdate==null)
	    	return "";

	    return format.format(sentdate);     
	}
	
	/**
	 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址   
	 */    
	public String getMailAddress(String type) throws Exception {     
	    String mailaddr = "";     
	    String addtype = type.toUpperCase();     
	    InternetAddress[] address = null;     
	    if (addtype.equals("TO") || addtype.equals("CC")|| addtype.equals("BCC")) {     
	        if (addtype.equals("TO")) {     
	            address = (InternetAddress[]) mimeMessage.getRecipients(javax.mail.Message.RecipientType.TO);
	        } else if (addtype.equals("CC")) {     
	            address = (InternetAddress[]) mimeMessage.getRecipients(javax.mail.Message.RecipientType.CC);     
	        } else {     
	            address = (InternetAddress[]) mimeMessage.getRecipients(javax.mail.Message.RecipientType.BCC);     
	        }     
	        if (address != null) {     
	            for (int i = 0; i < address.length; i++) {     
	                String email = address[i].getAddress();     
	                if (email == null)     
	                    email = "";     
	                else {     
	                    email = MimeUtility.decodeText(email);     
	                }     
	                String personal = address[i].getPersonal();     
	                if (personal == null)     
	                    personal = "";     
	                else {     
	                    personal = MimeUtility.decodeText(personal);     
	                }     
	                String compositeto = personal + "<" + email + ">";     
	                mailaddr += ";" + compositeto;     
	            }     
	            mailaddr = mailaddr.substring(1);     
	        }     
	    }
	    
	    return mailaddr;     
	}
	
	/**   
	 * 获得邮件主题   
	 */    
	public String getSubject() throws MessagingException {     
	    String subject = "";
	    boolean bMyselfDecode=false;
	    try {
	    	subject=mimeMessage.getSubject();
	    	
	    	if(subject == null){
	    		return subject;
	    	}
	    	
	    	// UTF-7编码，自己进行解码
	    	int index1=subject.indexOf("=?");
			int index2=subject.indexOf("?=");
			if(index1!=-1 )
			{
				if(index2!=-1 && index1<index2)
				{
					subject=subject.substring(index1+2,index2);
				}else
				{
					subject=subject.replace("=?", "").replace("?=", "");
				}
				
				String[] tmp2 = subject.split("\\?");
				if(tmp2.length >= 3)
				{
					if("unicode-1-1-utf-7".equalsIgnoreCase(tmp2[0]))
					{
						subject= Utf7Coder.decode(tmp2[2]);
						bMyselfDecode=true;
					}
				}
			}
			
			if(!bMyselfDecode)
			{
				// javamail进行解码
		        subject = MimeUtility.decodeText(subject);
			}
			
	        if (subject == null)     
	            subject = "";
	        
	    } catch (Exception e)
	    {
	    }     
	    return subject;     
	}
	
	 /**   
	 * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析   
	 */
	public void getMailContent(Part part) throws Exception {     
	    String contenttype = part.getContentType();
	    int nameindex = contenttype.indexOf("name");
	    boolean conname = false; 
	    if (nameindex != -1)
	        conname = true;
	    if (part.isMimeType("text/plain") && !conname) {
	    	try{
	    	contentText+=(String)part.getContent();
	    	}catch (Exception e) {
			}
	    } else if (part.isMimeType("text/html") && !conname) {     
	    	contentHtml+=(String)part.getContent();
	    } else if (part.isMimeType("multipart/*")) {     
	        Multipart multipart = (Multipart) part.getContent();
	        int counts = multipart.getCount();     
	        for (int i = 0; i < counts; i++) {     
	            getMailContent(multipart.getBodyPart(i));     
	        }     
	    } else if (part.isMimeType("message/rfc822")) {
	        getMailContent((Part) part.getContent());     
	    } else {}
	}
	
	/**
	 * 判断此邮件是否包含附件   
	 */    
	public boolean isContainAttach(Part part) throws Exception {     
	    boolean attachflag = false;
	    if (part.isMimeType("multipart/*")) {
	        Multipart mp = (Multipart) part.getContent();
	        for (int i = 0; i < mp.getCount(); i++) {
	            BodyPart mpart = mp.getBodyPart(i);
	            String disposition = mpart.getDisposition();     
	            if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))     
	                attachflag = true;     
	            else if (mpart.isMimeType("multipart/*")) {     
	                attachflag = isContainAttach((Part) mpart);     
	            } else {     
	                String contype = mpart.getContentType();     
	                if (contype.toLowerCase().indexOf("application") != -1)     
	                    attachflag = true;     
	                if (contype.toLowerCase().indexOf("name") != -1)     
	                    attachflag = true;     
	            }     
	        }     
	    } else if (part.isMimeType("message/rfc822")) {     
	        attachflag = isContainAttach((Part) part.getContent());     
	    }     
	    return attachflag;     
	}
	
	/**     
	 * 【保存附件】     
	 */     
	public void saveAttachMent(Part part) throws Exception {     
	    String fileName = "";     
	    if (part.isMimeType("multipart/*")) {     
	        Multipart mp = (Multipart) part.getContent();     
	        for (int i = 0; i < mp.getCount(); i++) {     
	            BodyPart mpart = mp.getBodyPart(i);     
	            String disposition = mpart.getDisposition();     
	            if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
	                fileName = mpart.getFileName();  
	                saveFile(fileName, mpart.getInputStream());     
	            } else if (mpart.isMimeType("multipart/*")) {     
	                saveAttachMent(mpart);     
	            } else {     
	                fileName = mpart.getFileName();     
	                saveFile(fileName, mpart.getInputStream());     
   
	            }     
	        }     
	    } else if (part.isMimeType("message/rfc822")) {     
	        saveAttachMent((Part) part.getContent());     
	    }     
	}
	
	/**
	 * 【真正的保存附件到指定目录里】
	 */
	private void saveFile(String fileName, InputStream in) throws Exception {
		if(fileName==null) 
			return;
	
		// 附件文件名解码
        fileName = MimeUtility.decodeText(fileName);

	    String saveAttachFile=dir+"temp/tmp-"+System.currentTimeMillis()+fileName;

	    BufferedOutputStream bos = null;
	    BufferedInputStream bis = null;
	    try {
	    	File storefile = new File(saveAttachFile);
	    	
	        bos = new BufferedOutputStream(new FileOutputStream(storefile));    
	        bis = new BufferedInputStream(in);    
	        byte[] byteBuff = new byte[1024];
	        while ( bis.read(byteBuff)!= -1) {    
	            bos.write(byteBuff);    
	            bos.flush();    
	        }
	    } catch (Exception exception) {    
	        System.out.println("文件保存失败!");
	    } finally {    
	        bos.close();    
	        bis.close();    
	    }

	    // 装载附件信息
	    PairFile file = new PairFile(fileName, saveAttachFile);
	    vecAttach.add(file);
	}

	/**转码  邮件subject编码*/
	public static String encodeSubjectMail(String subject) {
		if(subject == null)
			return "";
		try {
			int index1 = subject.indexOf("=?");
			int index2 = subject.lastIndexOf("?=");
			if (index1 != -1) {
				if (index2 != -1 && index1 < index2) {
					subject = subject.substring(index1 + 2, index2);
				} else {
					subject = subject.replace("=?", "").replace("?=", "");
				}

				String[] tmp2 = subject.split("\\?");
				if (tmp2.length >= 3) {
					if ("unicode-1-1-utf-7".equalsIgnoreCase(tmp2[0])) {
						subject = Utf7Coder.decode(tmp2[2]);
					} else {
						if ("B".equalsIgnoreCase(tmp2[1])) {
							subject = new String(Base64.decodeBase64(tmp2[2]),
									tmp2[0]);
						} else if ("Q".equalsIgnoreCase(tmp2[1])) {
							subject = new QuotedPrintableCodec().decode(tmp2[2], tmp2[0]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}
}