package util.quartz;

import java.io.File;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

public class QDeleteExcel extends StatefulMethodInvokingJob {
	private static int i = 0;
	private int j = 0; /*说明每次执行都是new了一个新的执行类，具有线程安全性*/
	
	@Override
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		j++;/*说明每次执行都是new了一个新的执行类，具有线程安全性*/
		i++;
		System.out.println("j====>" + j);/*说明每次执行都是new了一个新的执行类，具有线程安全性*/
		
		System.out.println(context.getJobDetail().getJobDataMap().get("path"));/*拿到传入的数据*/
		
		delAllFile(context.getJobDetail().getJobDataMap().get("path").toString());
		
		if(i == 1){
			System.out.println("删除Excel文件成功.....");
			QuartzManager.disableSchedule("deleteExcel","deleteExcel_group");
		}
	}
	

	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
	 	File file = new File(path);
		if (!file.exists()) {
			return flag;
	 	}
		if (!file.isDirectory()) {
	    	return flag;
	  	}
	 	String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
	        	temp = new File(path + tempList[i]);
	     	} else {
	     		temp = new File(path + File.separator + tempList[i]);
	     	}
	      	if (temp.isFile()) {
	      		temp.delete();
	      	}
	     	if (temp.isDirectory()) {
	     		delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	     		flag = true;
	     	}
	 	}
		return flag;
	}
}
