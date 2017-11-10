package adm.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import adm.bean.Users;
import adm.mapper.UsersMapper;
import adm.service.UsersService;

@Component("usersService")
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersMapper usersMapper ;

	public Users checkUsersNameExist(String username) throws RuntimeException{
		
		return usersMapper.checkUsersNameExist(username) ;
	}

	public Users getUsers(int id) {
		
		return usersMapper.getUsers(id) ;
	}

	public void editUsers(Users users) {

		usersMapper.editUsers(users) ;
	}

	public void editPass(Users users) {

		usersMapper.editPass(users) ;
	}
	
	public int getSize(Users users){
		
		return usersMapper.getSize(users);
	}
	
	public List<Users> getUserByUnit(Users users){
		
		return usersMapper.getUserByUnit(users);
	}
	
	/**所有禁用用户*/
	public List<Users> getUserByStateUnit(Users users){
		
		return usersMapper.getUserByStateUnit(users);
	}	
}
