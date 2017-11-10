package mail.mapper;

import java.util.List;
import mail.bean.Gns;

public interface GnsMapper {
	
	public Gns checkNameExist(Gns gns) throws RuntimeException;
		
	public Gns getGns(int id);
	
	public List<String> getGnsList(int gid);
	
	public void editGns(Gns gns);

	public void addGns(Gns gns);

	public void deleteGns(int id);
	
}
