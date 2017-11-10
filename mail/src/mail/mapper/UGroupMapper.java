package mail.mapper;

import java.util.List;
import mail.bean.UGroup;

public interface UGroupMapper {
	
	public UGroup checkNameExist(UGroup ugroup) throws RuntimeException;
		
	public UGroup getUGroup(int id);
	
	public List<UGroup> getUGroupList(int userid);
	
	public void editUGroup(UGroup ugroup);

	public void addUGroup(UGroup ugroup);

	public void deleteUGroup(int id);

	public void delAll();
	
}
