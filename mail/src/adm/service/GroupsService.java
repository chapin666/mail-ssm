package adm.service;

import java.util.List;

import adm.bean.Groups;
import adm.bean.Guss;
import adm.bean.Unit;
import adm.bean.UnitUser;
import adm.bean.Users;

public interface GroupsService {

	public Groups checkNameExist(String name) throws RuntimeException;
		
	public Groups getGroups(int id);

	public List<Users> listGuss(int gid);

	public List<Unit> listGnss(int gid);

	public Guss getGUId(Guss guss);
	
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
	
	public void addGroupsUser(int gid, String[] ids);
	public void addGroupsUnit(int gid, String[] ids);
//	public void ycGroupsUser(int gid, String[] ids);
	public void deleteGroupsUnit(int gid, String[] ids1, String[] ids2);
	
}