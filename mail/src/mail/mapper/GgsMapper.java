package mail.mapper;

import java.util.List;
import mail.bean.Ggs;

public interface GgsMapper {
	
	public Ggs checkNameExist(Ggs ggs) throws RuntimeException;
		
	public Ggs getGgs(int id);
	
	public List<String> getGgsList(int gid);
	
	public void editGgs(Ggs ggs);

	public void addGgs(Ggs ggs);

	public void deleteGgs(int id);
	
}
