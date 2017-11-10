package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Group;
import mail.bean.User;
import mail.mapper.GroupMapper;
import mail.service.GroupService;

@Component("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupMapper groupMapper ;

	public Group checkNameExist(Group group) throws RuntimeException{
		
		return groupMapper.checkNameExist(group) ;
	}

	public Group getGroup(int id) {
		
		return groupMapper.getGroup(id) ;
	}
	
	public List<Group> getGroupList(int id) {
		
		return groupMapper.getGroupList(id) ;
	}

	public List<Group> getGroupLists(User user) {
		
		return groupMapper.getGroupLists(user) ;
	}

	public void editGroup(Group group) {

		groupMapper.editGroup(group) ;
	}

	public void addGroup(Group group) {

		groupMapper.addGroup(group) ;
	}

	public void deleteGroup(int id) {
		
		groupMapper.deleteGroup(id) ;
	}

	public void delAll() {

		groupMapper.delAll() ;
	}

	public int getGroupSize() {

		return groupMapper.getGroupSize() ;
	}
}
