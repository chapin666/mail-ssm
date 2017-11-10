package util.quartz;

import java.util.ArrayList;
import org.quartz.JobDataMap;

public class QuartzManagerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CustomJob job = new CustomJob();
		job.setJobId("job1");
		job.setJobGroup("job1_group");
		job.setJobName("第一个测试定时器");
		job.setMemos("我是第一个测试定时器的描述");
		job.setCronExpression("0/5 * * * * ?");//每五秒执行一次
		job.setStateFulljobExecuteClass(QuartzJobOne.class);
		
		JobDataMap paramsMap = new JobDataMap();
		ArrayList<String> paramList = new ArrayList<String>();
		paramList.add("one");
		paramList.add("two");
		paramList.add("three");
		
		paramsMap.put("p1","p1");
		paramsMap.put("p2",paramList);
		QuartzManager.enableCronSchedule(job, paramsMap, true);
	}
}
