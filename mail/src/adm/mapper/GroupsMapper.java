package adm.mapper;

import java.util.List;

import adm.bean.Groups;
import adm.bean.Guss;
import adm.bean.Unit;
import adm.bean.UnitUser;
import adm.bean.Users;

public interface GroupsMapper {
	
	public Groups checkNameExist(String name) throws RuntimeException;
		
	public Groups getGroups(int id);
	
	public Guss getGUId(Guss guss);
	
	public List<Users> listGuss(int gid);

	public List<Unit> listGnss(int gid);
	
	public void deleteGroups(int id);

	public void deleteGuss(int gid);

	public void deleteGnss(int gid);

	public void deleteAllGuss(String[] ids);

	public void deleteAllGnss(String[] ids);
	
	public void editGroups(Groups groups);

	public void addGroups(Groups groups);
	
	public List<Groups> listGroups(String name);
	
	public List<UnitUser> listUnitUser(Groups groups);
	public int totalUnitUser(Groups groups);
	
}
