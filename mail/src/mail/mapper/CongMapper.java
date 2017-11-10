package mail.mapper;

import java.util.List;
import mail.bean.Cong;

public interface CongMapper {
			
	public Cong getCong(int id);
	
	public List<Cong> getCongList(int ugid);
	
	public void addCong(Cong cong);

	public void deleteCong(Cong cong);

	public List<String> getCidList(String ugid);

	public List<Cong> getCongListByCids(String cids);
	
}
