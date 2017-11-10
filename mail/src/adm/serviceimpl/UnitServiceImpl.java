package adm.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mail.bean.Config;

import org.apache.struts2.ServletActionContext;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import adm.bean.Unit;
import adm.mapper.UnitMapper;
import adm.service.UnitService;

@Component("unitService")
public class UnitServiceImpl extends SqlSessionDaoSupport implements UnitService {

	@Autowired
	private UnitMapper unitMapper ;

	public Unit checkNameExist(String name) throws RuntimeException{
		
		return unitMapper.checkNameExist(name) ;
	}

	public Unit getUnit(int id) {
		
		return unitMapper.getUnit(id) ;
	}

	public void deleteUnit(int id){
		
		unitMapper.deleteUnit(id);
	}
	
	public void editUnit(Unit unit) {

		unitMapper.editUnit(unit) ;
	}
	
	public void addUnit(Unit unit) {

		unitMapper.addUnit(unit) ;
	}

	public List<Unit> getUnitList(Unit unit) {

		return unitMapper.getUnitList(unit);
	}

	public List<String> getIdByPids(String uids) {

		return unitMapper.getIdbyPids(uids);
	}

	public int getUnitSize() {

		return unitMapper.getUnitSize();
	}

	public void editState(String[] ids, int state) {
		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		try {
			if(null !=ids && ids.length>0){
				String sql="update user set state=? where id=?" ;
				pstmt=conn.prepareStatement(sql);
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,state);
					pstmt.setObject(2,ids[i]);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**查询所有被禁用的用户*/
	public int getState(){
		return unitMapper.getState();
	}
	
	
	public void deleteUser(String[] ids) {
		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		try {
			if(null !=ids && ids.length>0){
				String sql="delete from user where id=?" ;
				pstmt=conn.prepareStatement(sql);
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,ids[i]);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUserAllData(String[] ids) {
		Connection conn = getSqlSession().getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		try {
			if (null != ids && ids.length > 0) {
				// 删除收件箱
				String sql1 = "delete from email_recv where tomail=?";
				// 删除发件箱
				String sql2 = "delete from email_send where frommail=?";
				// 删除草稿箱
				String sql3 = "delete from email_drafts where frommail=?";
				
				pstmt1 = conn.prepareStatement(sql1);
				pstmt2 = conn.prepareStatement(sql2);
				pstmt3 = conn.prepareStatement(sql3);
				for (int i = 0; i < ids.length; i++) {
					// 获取用户名
					String username = unitMapper.getUserNameById(ids[i]);

					HttpServletRequest request = ServletActionContext
							.getRequest();
					HttpSession session = request.getSession();
					String domain = (String) session.getAttribute("host");

					pstmt1.setObject(1, "<" + username + "@" + domain + ">");
					pstmt1.addBatch();
					pstmt2.setObject(1, "<" + username + "@" + domain + ">");
					pstmt2.addBatch();
					pstmt3.setObject(1, "<" + username + "@" + domain + ">");
					pstmt3.addBatch();
				}
			}
			pstmt1.executeBatch();
			pstmt2.executeBatch();
			pstmt3.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public List<Unit> getUnitByJson(String pid) {

		return unitMapper.getUnitByJson(pid);
	}

	public void editUnitId(String[] ids, int unid) {
		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		try {
			if(null !=ids && ids.length>0){
				String sql="update user set unid=? where id=?" ;
				pstmt=conn.prepareStatement(sql);
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,unid);
					pstmt.setObject(2,ids[i]);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addDoMain(Config config) {

		unitMapper.addDoMain(config);
	}
	/**修改域名*/
	public void updateDoMain(Config config) {
		unitMapper.updateDoMain(config);
	}
	/**添加域名*/
	public void insertDoMain(Config config) {
		unitMapper.insertDoMain(config);
	}
	
	/**删除域名*/
	public void delHost(int id){
		unitMapper.delHost(id);
	}
	
	public List<Config> getDoMain(String name) {		
		return unitMapper.getDoMain(name);
	}
//	public Config getDoMain(String name) {
//		Config config = unitMapper.getDoMain(name);
//		return config;
//	}
	
	/**根据name查询域名返回一个list集合*/
	public List<Config> getDoMainName() {		
		return unitMapper.getDoMainName();
	}
	
	/**注册信息查询*/
	public List<Config> regeditInfo(){		
		return unitMapper.regeditInfo();
	}
	
	/**编辑注册信息*/
	public void editRegeditInfo(String authorcode){
		int n = 0;//标记是否有"authorcode"字段 0：表示没有 
		List<Config> configList = new ArrayList<Config>();
		configList = unitMapper.regeditInfo();//查询注册信息表的所有记录
		
		for (int i = 0; i < configList.size(); i++) {
			if((configList.get(i).getName()).equals("authorcode")){
				n++;
			}
		}
		if(n>0){
			unitMapper.editRegeditInfo(authorcode);//修改注册信息
		}else {
			unitMapper.addRegeditInfo(authorcode);//添加注册信息
		}
	}
}
