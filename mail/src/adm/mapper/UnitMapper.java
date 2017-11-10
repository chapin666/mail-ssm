package adm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import mail.bean.Config;
import adm.bean.Unit;

public interface UnitMapper {
	
	public Unit checkNameExist(String name) throws RuntimeException;
		
	public Unit getUnit(int id);
	
	public void deleteUnit(int id);
	
	public void editUnit(Unit unit);

	public void addUnit(Unit unit);

	public List<Unit> getUnitList(Unit unit);

	public List<String> getIdbyPids(String uids);

	public int getUnitSize();
	
	/**查询所有被禁用的用户*/
	@Select("select count(*) from user where state = 2")
	public int getState();

	public List<Unit> getUnitByJson(String pid);
	
	public String getUserNameById(String id);
	
	public void addDoMain(Config config);
	
	/**修改域名*/
	public void updateDoMain(Config config);
	
	/**添加域名*/
	@Insert("Insert into configinfo(name,value) values('domain',#{value})")
	public void insertDoMain(Config config);
	
	/**删除域名*/
	@Delete("DELETE FROM configinfo where id=#{id}")
	public void delHost(int id);
	
	public List<Config> getDoMain(String name);
	
	/**根据name查询域名返回一个list集合*/
	@Select("select * from configinfo where name = 'domain'")
	public List<Config> getDoMainName();
	/**注册信息查询*/
	@Select("select * from configinfo")
	public List<Config> regeditInfo();
	/**编辑注册信息*/
	@Update("Update configinfo set value = #{authorcode} where name = 'authorcode'")
	public void editRegeditInfo(String authorcode);
	
	/**新增注册信息*/
	@Insert("Insert into configinfo(name,value) values('authorcode',#{authorcode})")
	public void addRegeditInfo(String authorcode);
}
