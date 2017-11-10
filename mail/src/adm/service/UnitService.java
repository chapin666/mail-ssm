package adm.service;

import java.util.List;

import mail.bean.Config;

import adm.bean.Unit;

public interface UnitService {

	public Unit checkNameExist(String name) throws RuntimeException;
		
	public Unit getUnit(int id);
	
	public void deleteUnit(int id);

	public void editUnit(Unit unit);

	public void addUnit(Unit unit);

	public List<Unit> getUnitList(Unit unit);

	public List<String> getIdByPids(String uids);

	public int getUnitSize();
	
	/**查询所有被禁用的用户*/
	public int getState();
	
	public void editState(String[] ids,int state);

	public void deleteUser(String[] ids);
	
	public void deleteUserAllData(String[] ids);

	public void editUnitId(String[] ids,int unid);

	public List<Unit> getUnitByJson(String pid);
	
	public void addDoMain(Config config);
	
	/**修改域名*/
	public void updateDoMain(Config config);
	
	/**添加域名*/
	public void insertDoMain(Config config);
	
	/**删除域名*/
	public void delHost(int id);
	
	public List<Config> getDoMain(String name);	
	
	/**根据name查询域名返回一个list集合*/
	public List<Config> getDoMainName();
	/**注册信息查询*/
	public List<Config> regeditInfo();
	/**编辑注册信息*/
	public void editRegeditInfo(String authorcode);
}