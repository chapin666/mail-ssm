package adm.mapper;

import java.util.List;
import adm.bean.Users;

public interface UsersMapper {
	
	public Users checkUsersNameExist(String username) throws RuntimeException;
		
	public Users getUsers(int id);
	
	public void editUsers(Users users);

	public void editPass(Users users);
	
	public int getSize(Users users);
	
	public List<Users> getUserByUnit(Users users);
	
	/**所有禁用用户*/
	public List<Users> getUserByStateUnit(Users users);
}

