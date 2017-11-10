package adm.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.Analy;

import adm.bean.KeyWordRule;
import adm.bean.SendFaileLog;
import adm.bean.spamlist;
import adm.bean.traitlib;
import adm.mapper.KeyWordRuleMapper;
import adm.mapper.SendFaileLogMapper;
import adm.mapper.SpamlistMapper;
import adm.mapper.traitlibMapper;
import adm.service.MailSpamService;

@Component("mailSpamService")
public class MailSpamServiceImpl implements MailSpamService{

	@Autowired
	private SpamlistMapper spamlistMapper;//投递日子失败分析
	@Autowired
	private traitlibMapper traitlibmapper;
	@Autowired
	private SendFaileLogMapper sendFaileLogMapper;
	@Autowired
	private KeyWordRuleMapper keyWordRuleMapper;//关键字规则
	
	
	
	public List<spamlist> selectspamlist(spamlist sp)
	{
		return spamlistMapper.selectspamlist(sp);
	}
	
	public void Addspamlist(spamlist sl)
	{
		spamlistMapper.Addspamlist(sl);
	}
	
	public void deletespamlist(int id)
	{
		spamlistMapper.deletespamlist(id);
	}
	
	public int spamlistCount(int tag)
	{
		return spamlistMapper.spamlistCount(tag);
	}
	
	public void deleteAll(int tag)
	{
		spamlistMapper.deleteAll(tag);
	}
	
	public List<traitlib> selectTraitlibs(traitlib t)
	{
		return traitlibmapper.selectTraitlibs(t);
	}
	
	/**删除特征库记录*/
	public void delTraitlibs(traitlib t)
	{
		traitlibmapper.delTraitlibs(t);
	}
	
	
	public int traitlibCount()
	{
		return traitlibmapper.traitlibCount();
	}
	
	public List<SendFaileLog> selectSendFaileLogs(SendFaileLog log)
	{
		List<SendFaileLog> listLog=sendFaileLogMapper.selectSendFaileLogs(log);
		
		List<SendFaileLog> listLog2=new ArrayList<SendFaileLog>();
		for(SendFaileLog log2:listLog )
		{
			String title = log2.getMailtitle().trim();
	        log2.setMailtitle(Analy.encodeSubjectMail(title));
	        listLog2.add(log2);
		}
		
		return listLog2;
	}
	
	public int SendFaileLogsCount()
	{
		return sendFaileLogMapper.SendFaileLogsCount();
	}
	
	/** 查询关键字规则所有记录  */
	public List<KeyWordRule> selectKeyWordRule(KeyWordRule kwr) {
		return keyWordRuleMapper.selectKeyWordRule(kwr);
	}
	/**统计所有关键字规则记录*/
	public int KeyWordRuleCount() {
		return keyWordRuleMapper.keyWordRuleCount();
	}

	public List<KeyWordRule> mailKeyMore(KeyWordRule kwr) {		
		return keyWordRuleMapper.mailKeyMore(kwr);
	}

	public int kwrCount(KeyWordRule kwr) {
		return keyWordRuleMapper.kwrCount(kwr);
	}

	public void delKeyWordRule(String[] ids) {
		keyWordRuleMapper.delKeyWordRule(ids);
	}
	//关键字状态	
	public void updateState(KeyWordRule kwr) {
		keyWordRuleMapper.updateState(kwr);
	}
	/**添加关键字规则*/
	public void addKeyWordRule(KeyWordRule kwr) {
		keyWordRuleMapper.addKeyWordRule(kwr);
	}
	
	public List<SendFaileLog> selectTimeLog(SendFaileLog log) {		
		return sendFaileLogMapper.selectTimeLog(log);
	}

	public int logCount(SendFaileLog log) {
		return sendFaileLogMapper.logCount(log);
	}
}
