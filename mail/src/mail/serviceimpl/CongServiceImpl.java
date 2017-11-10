package mail.serviceimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Cong;
import mail.mapper.CongMapper;
import mail.service.CongService;

@Component("congService")
public class CongServiceImpl extends SqlSessionDaoSupport implements CongService {

	@Autowired
	private CongMapper congMapper ;

	public Cong getCong(int id) {
		
		return congMapper.getCong(id) ;
	}
	
	public List<Cong> getCongList(int ugid) {
		
		return congMapper.getCongList(ugid) ;
	}

	public void addCong(Cong cong) {

		congMapper.addCong(cong) ;
	}

	public void deleteCong(Cong cong) {
		
		congMapper.deleteCong(cong) ;
	}

	public List<String> getCidList(String ugid) {

		return congMapper.getCidList(ugid);
	}

	public List<Cong> getCongListByCids(String cids) {

		return congMapper.getCongListByCids(cids);
	}

	public void addBatCong(String ugid, String[] ids, List<String> cidlist) {

		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		
		try {
			if(ids.length>0){
				String sql="insert into cong (ugid,cid) values (?,?)" ;
				pstmt=conn.prepareStatement(sql);
		
				for(int i=0;i<ids.length;i++){
					boolean flag = true ;
					if(cidlist!=null&&cidlist.size()>0){
						for(String cid : cidlist){
							if(cid.equals(ids[i])){
								flag = false ;
							}
						}
						if(flag){
							pstmt.setObject(1,ugid);
							pstmt.setObject(2,ids[i]);
							pstmt.addBatch();
						}
					}else{
						pstmt.setObject(1,ugid);
						pstmt.setObject(2,ids[i]);
						pstmt.addBatch();
					}
		        }
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addBatCong2(int cid,String[] ids) {

		Connection conn = getSqlSession().getConnection() ;
		PreparedStatement pstmt = null ;
		
		try {
			if(ids.length>0){
				String sql="insert into cong (ugid,cid) values (?,?)" ;
				pstmt=conn.prepareStatement(sql);
		
				for(int i=0;i<ids.length;i++){
					pstmt.setObject(1,ids[i]);
					pstmt.setObject(2,cid);
					pstmt.addBatch();
		        }
				pstmt.executeBatch();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
