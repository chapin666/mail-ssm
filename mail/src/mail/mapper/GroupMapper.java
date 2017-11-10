package mail.mapper;

import java.util.List;
import mail.bean.Group;
import mail.bean.User;

public interface GroupMapper {
	
	public Group checkNameExist(Group group) throws RuntimeException;
		
	public Group getGroup(int id);
	
	public List<Group> getGroupList(int id);
	
	public List<Group> getGroupLists(User user);
	
	public void editGroup(Group group);

	public void addGroup(Group group);

	public void deleteGroup(int id);

	public void delAll();

	public int getGroupSize();
	
}
