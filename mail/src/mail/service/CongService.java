package mail.service;

import java.util.List;
import mail.bean.Cong;

public interface CongService {
		
	public Cong getCong(int id);

	public List<Cong> getCongList(int ugid);
	
	public void addCong(Cong cong);

	public void deleteCong(Cong cong);

	public List<String> getCidList(String ugid);

	public List<Cong> getCongListByCids(String cids);

	public void addBatCong(String ugid, String[] ids, List<String> cidlist);

	public void addBatCong2(int cid, String[] ids);
	
}