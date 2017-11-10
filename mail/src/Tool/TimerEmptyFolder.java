package Tool;

import java.util.ArrayList;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;


public class TimerEmptyFolder  extends StatefulMethodInvokingJob {
	
	@Override
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		@SuppressWarnings("unchecked")
		ArrayList<String> paramList=(ArrayList<String>)context.getJobDetail().getJobDataMap().get("folder");/*拿到传入的数据*/
		
		for(String folder:paramList){
			ClearDir.DeleteFolder(folder);
		}
	}
}