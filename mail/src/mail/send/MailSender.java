package mail.send;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import mail.bean.Email;
import mail.bean.Files;
import mail.bean.User;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts2.ServletActionContext;

import util.Cache;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class MailSender{
	
	public static class SendThread extends Thread {
		HtmlEmail emaila;
		SendThread(HtmlEmail emaila)
		{
			this.emaila=emaila;
		}
		
		public void run(){
			try {
				emaila.send();
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
	}
	
    // private static final Log log = LogFactory.getLog(MailSender.class);
	
    public static String sendMail(Email mailInfo,int state) throws Exception {
        try {
          User user = (User)ServletActionContext.getRequest().getSession().getAttribute("user");
          String domain = (String)ServletActionContext.getRequest().getSession().getAttribute("host");

          HtmlEmail emaila = new HtmlEmail();
          emaila.setHostName("127.0.0.1");
		  emaila.setAuthenticator(new DefaultAuthenticator(user.getUsername()+"@"+domain,new String(Base64.decode(user.getPass()))));
		  emaila.setAuthentication(user.getUsername()+"@"+domain,new String(Base64.decode(user.getPass())));
		  emaila.setCharset("UTF-8");
		  emaila.setFrom(mailInfo.getFrommail(),mailInfo.getFromname());
		 
		  // 收件人
		  String tos = mailInfo.getToname();
		  if (tos != null && !"".equals(tos)) {
			  String to[] = tos.replace(",", ";").split(";");
			  for (int i = 0; i < to.length; i++) {
				  if(to[i].indexOf("<") > 0){
					  String[] tmp = to[i].split("<");
					  emaila.addTo(tmp[1].replace(">", ""), tmp[0]);
				  } else {
					  emaila.addTo(to[i]);
				  }
			  }
		  }else
		  {
			  emaila.addTo(mailInfo.getFrommail());
		  }

		  // 抄送
		  String ccs = mailInfo.getCopyto();
		  if (ccs != null && !"".equals(ccs)) {
			  String cc[] = ccs.replace(",", ";").split(";");
			  for (int j = 0; j < cc.length; j++) {
				  if(cc[j].indexOf("<") > 0){
					  String[] tmp = cc[j].split("<");
					  emaila.addCc(tmp[1].replace(">", ""), tmp[0]);
				  } else {
					  emaila.addCc(cc[j]);
				  }
			  }
		  }

		  // 密送
		  String bccs = mailInfo.getBcc();
		  if (bccs != null && !"".equals(bccs)) {
			  String bcc[] = bccs.replace(",", ";").split(";");
			  for (int j = 0; j < bcc.length; j++) {
				  if(bcc[j].indexOf("<") > 0){
					  String[] tmp = bcc[j].split("<");
					  emaila.addBcc(tmp[1].replace(">", ""), tmp[0]);
				  } else {
					  emaila.addBcc(bcc[j]);
				  }
			  }
		  }
		  emaila.setSubject(mailInfo.getTitle());
		  // 添加正文，正文不能为空，否则发送失败
		  String strContent=mailInfo.getContent();
		  if(strContent.length() < 1) strContent=" ";
		  emaila.setHtmlMsg(strContent);
		  
		  String AttachFile=mailInfo.getFile();
		  if (AttachFile != null && !"".equals(AttachFile)) 
		  {
			List<Files> filelist = new ArrayList<Files>();
			String[] files = AttachFile.split("\\|");
			if (files != null && files.length > 0) {
				for (String file : files) {
					Files ff = new Files();
					String[] filess = file.split("\\*");
					if (filess != null && filess.length > 0
							&& filess[0] != null && !filess[0].equals("")
							&& filess[1] != null && !filess[1].equals("")) {
						ff.setFile(filess[1]);
						ff.setFilename(filess[0]);
						filelist.add(ff);
					}
				}
			}
			for(Files file:filelist)
			{
				EmailAttachment emailattachment = new EmailAttachment();
				emailattachment.setName("=?gb18030?B?"+ Base64.encode(file.getFilename().getBytes("gb18030")) + "?=");
				emailattachment.setPath(ServletActionContext.getRequest().getRealPath("/").replace("\\", "/") +file.getFile());	
				emaila.attach(emailattachment);
			}
		  }
		  
			// 将邮件保存为文件
			if (state == 0) {
				String dir = Cache.cache.get("filedir"); // 目录以"/"结束
				
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String timeDir = sdf.format(date);
				String timeFile ="draft-"+Integer.toString(date.hashCode());
				String dir2="MaileFile/"+ timeDir + "/";
				
				// 创建目录
				File Fdir = new File(dir+dir2);
				if (Fdir.exists() == false){
					Fdir.mkdirs();
				}

				// 保存数据
				emaila.buildMimeMessage();
				MimeMessage mimeMessage = emaila.getMimeMessage();
				OutputStream fos = new FileOutputStream(dir+dir2+timeFile);
				mimeMessage.writeTo(fos);
				fos.close();

				return dir2+ timeFile;
				
			} else if (state == 1) {
				// 发送邮件
				SendThread thread1=new SendThread(emaila);
				thread1.start();
			}

        } catch (Exception e) {
        	System.out.println(e.toString());
        }
        
        return "";
    }
    
    public static void sendDSMail(Email mailInfo, User user, String fileurl) throws Exception {
        try {
        	
          HtmlEmail emaila = new HtmlEmail(); 
          emaila.setHostName("127.0.0.1");
//		  emaila.setHostName("smtp."+ServletActionContext.getRequest().getSession().getAttribute("host"));
		  emaila.setAuthenticator(new DefaultAuthenticator(user.getUsername(),new String(Base64.decode(user.getPass()))));
		  emaila.setAuthentication(user.getUsername(),new String(Base64.decode(user.getPass())));
		  emaila.setCharset("UTF-8");
		  emaila.setFrom(mailInfo.getFrommail().substring(1, mailInfo.getFrommail().length() - 1).replace("<", "").replace(">", ""),mailInfo.getFromname());
		  // 收件人
		  String tos = mailInfo.getTomail();
		  if (tos != null && !"".equals(tos)) {
			  String to[] = tos.replace(",", ";").split(";");
			  for (int i = 0; i < to.length; i++) {
				  if(to[i].indexOf("<") > 0){
					  String[] tmp = to[i].split("<");
					  emaila.addTo(tmp[1].replace(">", ""), tmp[0]);
				  } else {
					  emaila.addTo(to[i]);
				  }
			  }
		  }

		  // 抄送
		  String ccs = mailInfo.getCopyto();
		  if (ccs != null && !"".equals(ccs)) {
			  String cc[] = ccs.replace(",", ";").split(";");
			  for (int j = 0; j < cc.length; j++) {
				  if(cc[j].indexOf("<") > 0){
					  String[] tmp = cc[j].split("<");
					  emaila.addCc(tmp[1].replace(">", ""), tmp[0]);
				  } else {
					  emaila.addCc(cc[j]);
				  }
			  }
		  }

		  // 密送
		  String bccs = mailInfo.getBcc();
		  if (bccs != null && !"".equals(bccs)) {
			  String bcc[] = bccs.replace(",", ";").split(";");
			  for (int j = 0; j < bcc.length; j++) {
				  if(bcc[j].indexOf("<") > 0){
					  String[] tmp = bcc[j].split("<");
					  emaila.addBcc(tmp[1].replace(">", ""), tmp[0]);
				  } else {
					  emaila.addBcc(bcc[j]);
				  }
			  }
		  }
		  emaila.setSubject(mailInfo.getTitle());
		  // 添加正文，正文不能为空，否则发送失败
		  String strContent=mailInfo.getContent();
		  if(strContent.length() < 1) strContent=" ";
		  emaila.setHtmlMsg(strContent);
		  
		  String AttachFile=mailInfo.getFile();
		  if (AttachFile != null && !"".equals(AttachFile)) 
		  {
			List<Files> filelist = new ArrayList<Files>();
			String[] files = AttachFile.split("\\|");
			if (files != null && files.length > 0) {
				for (String file : files) {
					Files ff = new Files();
					String[] filess = file.split("\\*");
					if (filess != null && filess.length > 0
							&& filess[0] != null && !filess[0].equals("")
							&& filess[1] != null && !filess[1].equals("")) {
						ff.setFile(filess[1]);
						ff.setFilename(filess[0]);
						filelist.add(ff);
					}
				}
			}
			for(Files file:filelist)
			{
				EmailAttachment emailattachment = new EmailAttachment();
				emailattachment.setName("=?gb18030?B?"+ Base64.encode(file.getFilename().getBytes("gb18030")) + "?=");
				emailattachment.setPath(fileurl +file.getFile());
				emaila.attach(emailattachment);
			}
		
		  }
		  emaila.send();
        } catch (Exception e) {
        	System.out.println(e.toString());
        }
    }
}
