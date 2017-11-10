package adm.service;

import java.util.List;

import adm.bean.KeyWordRule;
import adm.bean.SendFaileLog;
import adm.bean.spamlist;
import adm.bean.traitlib;

public interface MailSpamService {

	public List<spamlist> selectspamlist(spamlist sp);
	
	public void Addspamlist(spamlist sl);
	
	public void deletespamlist(int id);
	
	public int spamlistCount(int tag);
	
	public void deleteAll(int tag);
	
	public List<traitlib> selectTraitlibs(traitlib t);
	
	public int traitlibCount();
	
	public List<SendFaileLog> selectSendFaileLogs(SendFaileLog log);
	
	public int SendFaileLogsCount();
	
	/**查询关键字规则所有记录*/
	public List<KeyWordRule> selectKeyWordRule(KeyWordRule kwr);
	/**统计所有关键字规则记录*/
	public int KeyWordRuleCount();
	/**根据关键字查询*/
	public List<KeyWordRule> mailKeyMore(KeyWordRule kwr);	
	/**根据条件统计所有关键字规则记录*/
	public int kwrCount(KeyWordRule kwr);	
	/**删除*/	
	public void delKeyWordRule(String[] ids);	
	/**关键字状态*/	
	public void updateState(KeyWordRule kwr);
	/**添加关键字规则*/
	public void addKeyWordRule(KeyWordRule kwr);
	
	/**根据条件查询*/
	public List<SendFaileLog> selectTimeLog(SendFaileLog log);
	/** 根据条件统计记录条数  */	
	public int logCount(SendFaileLog log);
	
	/**删除特征库记录*/
	public void delTraitlibs(traitlib t);	
}
