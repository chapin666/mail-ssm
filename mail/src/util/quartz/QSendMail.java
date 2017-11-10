package util.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mail.bean.Email;
import mail.bean.User;
import mail.service.EmailService;
import mail.service.UserService;

import org.apache.http.protocol.HttpService;
import org.apache.struts2.ServletActionContext;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import util.LoggerUtil;

public class QSendMail extends StatefulMethodInvokingJob {
	private static int i = 0;
	private static int sends = 0;
	private static int sendt = 0;
	private static int sendf = 0;
	private int j = 0; /*说明每次执行都是new了一个新的执行类，具有线程安全性*/

	@Override
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		j++;/*说明每次执行都是new了一个新的执行类，具有线程安全性*/
		i++;
//		System.out.println("j====>" + j);/*说明每次执行都是new了一个新的执行类，具有线程安全性*/
		Object id = context.getJobDetail().getJobDataMap().get("eid");
		String JobId = context.getJobDetail().getJobDataMap().get("JobId").toString();
		String JobGroup = context.getJobDetail().getJobDataMap().get("JobGroup").toString();
		UserService userService = (UserService) context.getJobDetail().getJobDataMap().get("userService");
		EmailService emailService = (EmailService) context.getJobDetail().getJobDataMap().get("emailService");
		User user = (User) context.getJobDetail().getJobDataMap().get("user");
		String fileurl = context.getJobDetail().getJobDataMap().get("fileurl").toString();
		
		try {
			sendMailTimeQuartz(id,emailService,userService,user,fileurl);
			QuartzManager.disableSchedule(JobId,JobGroup);
		} catch (Exception e) {
			e.printStackTrace();
			QuartzManager.disableSchedule(JobId,JobGroup);
		}
	}


	//定时发送邮件方法
	public void sendMailTimeQuartz(Object id ,EmailService emailService, UserService userService, User user, String fileurl) throws Exception
	{
		Email email = new Email();
		email = emailService.getDraftsEmail(Integer.parseInt(id.toString()));
		if(email!=null&&!email.equals("")){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(date);
			email.setTime(time);
			email.setReceipt(1);
			email.setSee(2);
//			List<String> recivelist = new ArrayList<String>();
//			List<String> copytolist = new ArrayList<String>();
//			List<String> bcclist = new ArrayList<String>();
//			List<String> alllist = new ArrayList<String>();
//			String types = "";
//			if(email.getToname()!=null&&!email.getToname().equals("")){
//				String[] enames = email.getToname().split(";");
//				if(enames!=null&&enames.length>0){
//					for(int i=0;i<enames.length;i++){
//						recivelist.add(enames[i].replace("<","&lt;").replace(">","&gt;"));
//						alllist.add(enames[i].replace("<","&lt;").replace(">","&gt;"));
//						if(enames[i].indexOf("<")<0){
//							types+="<"+enames[i]+">2;";
//							email.setToname(email.getToname().replace(enames[i], "<"+enames[i]+">"));
//						}else{
//							types+=enames[i]+"2;";
//						}
//					}
//				}
//			}
//			if(email.getCopyto()!=null&&!email.getCopyto().equals("")){
//				String[] cnames = email.getCopyto().split(";");
//				if(cnames!=null&&cnames.length>0){
//					for(int i=0;i<cnames.length;i++){
//						copytolist.add(cnames[i].replace("<","&lt;").replace(">","&gt;"));
//						alllist.add(cnames[i].replace("<","&lt;").replace(">","&gt;"));
//						if(cnames[i].indexOf("<")<0){
//							types+="<"+cnames[i]+">2;";
//							email.setCopyto(email.getCopyto().replace(cnames[i], "<"+cnames[i]+">"));
//						}else{
//							types+=cnames[i]+"2;";
//						}
//					}
//				}
//			}
//			if(email.getBcc()!=null&&!email.getBcc().equals("")){
//				String[] bnames = email.getBcc().split(";");
//				if(bnames!=null&&bnames.length>0){
//					for(int i=0;i<bnames.length;i++){
//						bcclist.add(bnames[i].replace("<","&lt;").replace(">","&gt;"));
//						alllist.add(bnames[i].replace("<","&lt;").replace(">","&gt;"));
//						if(bnames[i].indexOf("<")<0){
//							types+="<"+bnames[i]+">2;";
//							email.setBcc(email.getBcc().replace(bnames[i], "<"+bnames[i]+">"));
//						}else{
//							types+=bnames[i]+"2;";
//						}
//					}
//				}
//			}
//			email.setType(types);
			
			emailService.sendDSEmail(email, user, fileurl);
			emailService.deleteDrafts(new String[]{id.toString()});
			
//			String emails = "";
//			for(String addr : alllist){
//				emails+=addr.replaceAll("<","&lt;").replaceAll(">","&gt;");
//				
//			}
			LoggerUtil.addDSLog(user, logger, "发信", "", "", "","",email.getTitle(),"");
			LoggerUtil.addDSLog(user, logger, "收信", "", "", "","",email.getTitle(),"");
			System.out.println("定时邮件已发送！");
		}else{
			System.out.println("定时邮件已被删除，无法发送！");
		}
	}
	
	public static String sendMailNum()
	{
		int cha = sendt - sends - sendf;
		String num = sends + "," + sendf + "," + sendt + "," + cha;
		return num;
	}
}
