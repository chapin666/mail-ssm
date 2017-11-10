package mail.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.ETag;
import mail.mapper.ETagMapper;
import mail.service.ETagService;

@Component("eTagService")
public class ETagServiceImpl extends SqlSessionDaoSupport implements ETagService {

	@Autowired
	private ETagMapper eTagMapper ;

	public ETag getETag(int id) {
		
		return eTagMapper.getETag(id) ;
	}
	
	public List<ETag> getETagList(int ugid) {
		
		return eTagMapper.getETagList(ugid) ;
	}

	public void addETag(ETag etag) {

		eTagMapper.addETag(etag) ;
	}

	public void deleteETag(String id) {
		
		eTagMapper.deleteETag(id) ;
	}

	public List<ETag> getEidList(String tid) {

		return eTagMapper.getEidList(tid);
	}

	public List<ETag> getETagListByEids(String eids) {

		return eTagMapper.getETagListByEids(eids);
	}

//	public void addBatETag(String tid, String[] ids, List<ETag> eidlist, String[] frommails, String username) {
//
//		Connection conn = getSqlSession().getConnection() ;
//		PreparedStatement pstmt = null ;
//		
//		try {
//			if(ids.length>0){
//				String sql="insert into etag (eid,tid,type) values (?,?,?)" ;
//				pstmt=conn.prepareStatement(sql);
//		
//				for(int i=0;i<ids.length;i++){
//					boolean flag = true ;
//					if(eidlist!=null&&eidlist.size()>0){
//						if(username.equals(frommails[i])){
//							for(ETag eid : eidlist){
//								if(eid.getType()==2){
//									if(eid.getEid()==Integer.parseInt(ids[i])){
//										flag = false ;
//									}
//								}
//							}
//						}else{
//							for(ETag eid : eidlist){
//								if(eid.getType()==1){
//									if(eid.getEid()==Integer.parseInt(ids[i])){
//										flag = false ;
//									}
//								}
//							}
//						}
//						if(flag){
//							pstmt.setObject(1,ids[i]);
//							pstmt.setObject(2,tid);
//							if(username.equals(frommails[i])){
//								pstmt.setObject(3,2);
//							}else{
//								pstmt.setObject(3,1);
//							}
//							pstmt.addBatch();
//						}
//					}else{
//						pstmt.setObject(1,ids[i]);
//						pstmt.setObject(2,tid);
//						if(username.equals(frommails[i])){
//							pstmt.setObject(3,2);
//						}else{
//							pstmt.setObject(3,1);
//						}
//						pstmt.addBatch();
//					}
//		        }
//				pstmt.executeBatch();
//			}
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	public void addBatETag(String tid, String[] ids, List<ETag> eidlist,String type) {

		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		
		try {
			if(ids.length>0){
				String sql="insert into etag (eid,tid,type) values (?,?,?)" ;
				pstmt=conn.prepareStatement(sql);
		
				for(int i=0;i<ids.length;i++){
					boolean flag = true ;
					if(eidlist!=null&&eidlist.size()>0){
						if(type.equals("1")){
							for(ETag eid : eidlist){
								if(eid.getType()==1){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}else if(type.equals("2")){
							for(ETag eid : eidlist){
								if(eid.getType()==2){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}else if(type.equals("3")){
							for(ETag eid : eidlist){
								if(eid.getType()==3){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}
						if(flag){
							pstmt.setObject(1,ids[i]);
							pstmt.setObject(2,tid);
							pstmt.setObject(3,type);
							
							pstmt.addBatch();
						}
					}else{
						pstmt.setObject(1,ids[i]);
						pstmt.setObject(2,tid);
						pstmt.setObject(3,type);
						
						pstmt.addBatch();
					}
		        }
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**垃圾箱邮件绑定标签*/
	public void rubmaildoAddETag(String tid, String[] ids, List<ETag> eidlist,String type){
		SqlSessionTemplate st = (SqlSessionTemplate) getSqlSession();
		Connection conn = SqlSessionUtils.getSqlSession(
                st.getSqlSessionFactory(), st.getExecutorType(),
                st.getPersistenceExceptionTranslator()).getConnection();
		PreparedStatement pstmt = null ;
		
		try {
			if(ids.length>0){
				String sql="insert into etag (eid,tid,type) values (?,?,?)" ;
				pstmt=conn.prepareStatement(sql);
		
				for(int i=0;i<ids.length;i++){
					boolean flag = true ;
					if(eidlist!=null&&eidlist.size()>0){
						if(type.equals("1")){
							for(ETag eid : eidlist){
								if(eid.getType()==1){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}
						if(flag){
							pstmt.setObject(1,ids[i]);
							pstmt.setObject(2,tid);
							pstmt.setObject(3,type);
							
							pstmt.addBatch();
						}
					}else{
						pstmt.setObject(1,ids[i]);
						pstmt.setObject(2,tid);
						pstmt.setObject(3,type);
						
						pstmt.addBatch();
					}
		        }
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**删除箱邮件绑定标签*/
	public void deladdBatETag(String tid, String[] ids, List<ETag> eidlist,String[] type){
		//Connection conn = getSqlSession().getConnection();
		SqlSessionTemplate st = (SqlSessionTemplate) getSqlSession();
		Connection conn = SqlSessionUtils.getSqlSession(
                st.getSqlSessionFactory(), st.getExecutorType(),
                st.getPersistenceExceptionTranslator()).getConnection();
		PreparedStatement pstmt = null ;
		
		try {
			if(ids.length>0){
				String sql="insert into etag (eid,tid,type) values (?,?,?)" ;
				pstmt=conn.prepareStatement(sql);
		
				for(int i=0;i<ids.length;i++){
					boolean flag = true ;
					if(eidlist!=null&&eidlist.size()>0&&type!=null){
						if(type[i].equals("1")){
							for(ETag eid : eidlist){
								if(eid.getType()==1){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}else if(type[i].equals("2")){
							for(ETag eid : eidlist){
								if(eid.getType()==2){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}else if(type[i].equals("3")){
							for(ETag eid : eidlist){
								if(eid.getType()==3){
									if(eid.getEid()==Integer.parseInt(ids[i])){
										flag = false ;
									}
								}
							}
						}
						if(flag){
							pstmt.setObject(1,ids[i]);
							pstmt.setObject(2,tid);
							pstmt.setObject(3,type[i]);
							
							pstmt.addBatch();
						}
					}else{
						pstmt.setObject(1,ids[i]);
						pstmt.setObject(2,tid);
						pstmt.setObject(3,type[i]);
						
						pstmt.addBatch();
					}
		        }
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	public void addBatETag2(int eid,String[] ids) {

		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		
		try {
			if(ids.length>0){
				String sql="insert into etag (ugid,cid) values (?,?)" ;
				pstmt=conn.prepareStatement(sql);
		
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,ids[i]);
					pstmt.setObject(2,eid);
					pstmt.addBatch();
		        }
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<ETag> getETagListByUser(int userid) {

		return eTagMapper.getETagListByUser(userid);
	}
}
