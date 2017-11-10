package adm.mapper;

import java.util.List;

import adm.bean.spamlist;

public interface SpamlistMapper {

	//查询分类白黑名单  带分页
	public List<spamlist> selectspamlist(spamlist sp);
	
	//增加名单
	public void Addspamlist(spamlist sl);
	//删除名单
	public void deletespamlist(int id);
	
	//所有记录数
	public int spamlistCount(int tag);
	
	
	//删除所有
	public void deleteAll(int tag);
	
}
