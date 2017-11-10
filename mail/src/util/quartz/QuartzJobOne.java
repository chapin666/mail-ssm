package util.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;
 
public class QuartzJobOne extends StatefulMethodInvokingJob {
	private static int i = 0;
	private int j = 0; /*说明每次执行都是new了一个新的执行类，具有线程安全性*/
	
	@Override
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		j++;/*说明每次执行都是new了一个新的执行类，具有线程安全性*/
		i++;
		System.out.println("j====>" + j);/*说明每次执行都是new了一个新的执行类，具有线程安全性*/
		System.out.println("这是我得第" + i + "次执行");
		System.out.println("my name is QuartzJobOne");
		System.out.println(context.getJobDetail().getJobDataMap().get("p2"));/*拿到传入的数据*/
		if(i == 3){
			System.out.println("我只执行三次.....");
			QuartzManager.disableSchedule("job1","job1_group");
		}
	}
}