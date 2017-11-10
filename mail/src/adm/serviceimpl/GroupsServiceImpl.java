package adm.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adm.bean.Groups;
import adm.bean.Guss;
import adm.bean.Unit;
import adm.bean.UnitUser;
import adm.bean.Users;
import adm.mapper.GroupsMapper;
import adm.service.GroupsService;

@Component("groupsService")
public class GroupsServiceImpl extends SqlSessionDaoSupport implements GroupsService {

	@Autowired
	private GroupsMapper groupsMapper ;

	public Groups checkNameExist(String name) throws RuntimeException{
		
		return groupsMapper.checkNameExist(name) ;
	}

	public Groups getGroups(int id) {
		
		return groupsMapper.getGroups(id) ;
	}

	public void deleteGroups(int id){
		
		groupsMapper.deleteGroups(id);
	}
	
	public void editGroups(Groups groups) {

		groupsMapper.editGroups(groups) ;
	}
	
	public void addGroups(Groups groups) {

		groupsMapper.addGroups(groups) ;
	}

	public List<Groups> listGroups(String name) {

		return groupsMapper.listGroups(name);
	}

	public List<UnitUser> listUnitUser(Groups groups) {
		
		return groupsMapper.listUnitUser(groups);
	}

	public int totalUnitUser(Groups groups) {
		
		return groupsMapper.totalUnitUser(groups);
	}

	public void addGroupsUnit(int gid, String[] ids) 
	{
		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		try {
			if(ids.length>0){
				String sql="insert into gns (gid,unid) values (?,?)" ;
				pstmt=conn.prepareStatement(sql);
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,gid);
					pstmt.setObject(2,ids[i]);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addGroupsUser(int gid, String[] ids) 
	{
		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		try {
			if(ids.length>0){
				String sql="insert into gus (gid,userid) values (?,?)" ;
				pstmt=conn.prepareStatement(sql);
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,gid);
					pstmt.setObject(2,ids[i]);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Unit> listGnss(int gid) {
		return groupsMapper.listGnss(gid);
	}

	public List<Users> listGuss(int gid) {
		return groupsMapper.listGuss(gid);
	}

	public void deleteGnss(int gid) {
		groupsMapper.deleteGnss(gid);
	}

	public void deleteGuss(int gid) {
		groupsMapper.deleteGuss(gid);
	}

	public void deleteAllGnss(String[] ids) {
		groupsMapper.deleteAllGnss(ids);
	}

	public void deleteAllGuss(String[] ids) {
		groupsMapper.deleteAllGuss(ids);
	}

//	public void ycGroupsUser(int gid, String[] ids) {
//		Connection conn2 = getSqlSession().getConnection() ;
//		PreparedStatement pstmt2 = null ;
//		try {
//			if(ids.length>0){
//				String sql="delete from gus where gid=? and userid=?" ;
//				pstmt2=conn2.prepareStatement(sql);
//				for(int i=0;i<ids.length;i++){
//					pstmt2.setObject(1,gid);
//					pstmt2.setObject(2,ids[i]);
//					pstmt2.addBatch();
//				}
//			}
//			pstmt2.executeBatch();
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public void deleteGroupsUnit(int gid, String[] ids1, String[] ids2) {
		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		try {
			if(ids1!=null&&ids1.length > 0){
				String sql1="delete from gns where gid=? and unid=?" ;
				pstmt=conn.prepareStatement(sql1);
				for(int i=0;i<ids1.length;i++){  
					pstmt.setObject(1,gid);
					pstmt.setObject(2,ids1[i]);
					pstmt.addBatch();
		        }
				pstmt.executeBatch();
			}
			if(ids2!=null&&ids2.length>0){
				String sql="delete from gus where gid=? and userid=?" ;
				pstmt=conn.prepareStatement(sql);
				for(int i=0;i<ids2.length;i++){
					pstmt.setObject(1,gid);
					pstmt.setObject(2,ids2[i]);
					pstmt.addBatch();
				}
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Guss getGUId(Guss guss) {
		return groupsMapper.getGUId(guss);
	}
}
