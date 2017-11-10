package adm.mapper;

import java.util.List;

import adm.bean.SendFaileLog;

public interface SendFaileLogMapper {

	public List<SendFaileLog> selectSendFaileLogs(SendFaileLog log);	
	
	public int SendFaileLogsCount();
	
	/**根据条件查询*/
	public List<SendFaileLog> selectTimeLog(SendFaileLog log);
	/** 根据条件统计记录条数  */	
	public int logCount(SendFaileLog log);
}
