package adm.mapper;

import java.util.List;

import adm.bean.KeyWordRule;

public interface KeyWordRuleMapper {
	/**查询所有*/
	public List<KeyWordRule> selectKeyWordRule(KeyWordRule kwr);
	/** 统计记录条数  */	
	public int keyWordRuleCount();
	/**根据关键字查询*/
	public List<KeyWordRule> mailKeyMore(KeyWordRule kwr);
	/** 根据条件统计记录条数  */	
	public int kwrCount(KeyWordRule kwr);
	/**删除关键字规则*/	
	public void delKeyWordRule(String[] ids);	
	/**关键字规则状态*/		
	public void updateState(KeyWordRule kwr);
	/**添加关键字规则*/
	public void addKeyWordRule(KeyWordRule kwr);
}
