package util.quartz;

import org.quartz.JobDataMap;
import util.Para;

public class QDeleteExcelStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CustomJob job = new CustomJob();
		job.setJobId("deleteExcel");
		job.setJobGroup("deleteExcel_group");
		job.setJobName("删除Excel文件");
		job.setMemos("删除Excel文件");
		job.setCronExpression("0 0 3 * * ?");//每天凌晨3点执行一次
		job.setStateFulljobExecuteClass(QDeleteExcel.class);
		
		Para para = new Para();
		String path = para.getPara("adm_addr","file.properties","/page/crp/excel/",false);
		
		JobDataMap paramsMap = new JobDataMap();
		paramsMap.put("path",path);
		
		QuartzManager.enableCronSchedule(job, paramsMap, true);
		
	}

}
