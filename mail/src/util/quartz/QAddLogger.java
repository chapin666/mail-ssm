package util.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import adm.bean.Logger;
import adm.service.LoggerService;
import adm.serviceimpl.LoggerServiceImpl;
import util.Para;

public class QAddLogger extends StatefulMethodInvokingJob {
	
	@Autowired
	private LoggerServiceImpl loggerService=new LoggerServiceImpl();
	
	@Override
	
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		
		Para para = new Para();
		String temp="";
		try {
			temp = para.getPara("log_addr","file.properties","",false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(temp+"action.log");
		System.out.println("调用导入日志......");
	}
	
	public void add(String filename)
	{
		File file = null;
		BufferedReader reader = null;
		try {
			file = new File(filename);
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			int temp_index = 0;
			
			while ((tempString = reader.readLine()) != null) {
				temp_index = tempString.indexOf("|") + 1;
				if (temp_index > 0) {
					tempString = tempString.substring(temp_index);
					String[] tempArray = tempString.split("#");
					Logger wLog = new Logger();
					wLog.setTypes(tempArray[0]);
					try {
						wLog.setTitle(tempArray[1]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setTitle("");
					}
					try {
						wLog.setFmail(tempArray[2]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setFmail("");
					}
					try {
						wLog.setTmail(tempArray[3]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setTmail("");
					}
					try {
						wLog.setTime(tempArray[4]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setTime("");
					}
					try {
						wLog.setState(tempArray[5]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setState("");
					}
					try {
						wLog.setUserid(tempArray[6]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setUserid("");
					}
					try {
						wLog.setUsername(tempArray[7]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setUsername("");
					}
					try {
						wLog.setOdata(tempArray[8]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setOdata("");
					}
					try {
						wLog.setIps(tempArray[9]);
					} catch (ArrayIndexOutOfBoundsException e) {
						wLog.setIps("");
					}
					try {
						loggerService.addLogger(wLog);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			reader.close();
			FileWriter fw1 = new FileWriter(file, false);
			fw1.write("");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {}
			}
		}
	}

}
