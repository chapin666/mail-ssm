package mail.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import adm.bean.Logger;

import mail.bean.Email;
import mail.bean.Group;
import mail.bean.Sign;
import mail.bean.Tag;
import mail.bean.Units;
import mail.bean.User;

public interface UserService {

	public User checkUserNameExist(User user) throws RuntimeException;
		
	public User getUser(int id);

	public void editUser(User user);

	public void editPass(User user);

	public List<User> getUsernameByUnits(Units uids);
	
	public int getUsernameCountByUnits(String uids);

	public List<User> getUsernameByGroup(Group group);
	
	public int getUsernameCountByGroup(Group group);
	
	public List<User> getAllUser(int state);

	public List<User> getUsernameByGroupids(String gids);

	public List<String> getUnids(Group group);

	public List<User> getUserByKey(String key);
	
	public List<User> getUserByKey2(String key);

	public void delAll();

	public int getUserSize();

	public void addUser(User user);
	
	/**根据类型查询最后一次登录的日子*/
	public List<Logger> getTypeLoginlog(Logger logger);
	
	/**查询所有标签*/
	public List<Tag> setmail(User user);	
	/**统计收件箱所以未读标记邮件*/
	public int getnoreadtag(User user);
	/**根据标记名统计所以被标记邮件*/
	public int getsumtag(User user);
	/**查询所有标签邮件记录*/
	public int getTagMailCount(User user);
	/**查询所有收件箱标记邮件*/
	public List<Email> getTagMailRecv(User user);
	/**查询所有发件箱标记邮件*/
	public List<Email> getTagMailSend(User user);
	/**查询所有草稿箱标记邮件*/
	public List<Email> getTagMailDrafts(User user);
	
	/**根据名字查询所有收件箱标记邮件*/
	public List<Email> getTagNameMailRecv(User name);
	/**根据名字查询所有发件箱标记邮件*/
	public List<Email> getTagNameMailSend(User name);
	/**根据名字查询所有草稿箱标记邮件*/
	public List<Email> getTagNameMailDrafts(User name);
	
	/**根据mailid删除收件箱标记邮件记录*/
	public void delTagNameMailRecv(String mailid);
	/**根据mailid删除发件箱标记邮件记录*/
	public void delTagNameMailSend(String mailid);
	/**根据id删除草稿箱标记邮件记录*/
	public void delTagNameMailDrafts(int id);
	
	/**查询所有签名*/
	public List<Sign> getSignList(int id);
	
	/**检查签名的名字是否重复*/
	public Sign checkSignNameExist(Sign sign);
	
	/**清空所有默认名称*/
	public void clearDefaultSign();
	
	/**添加签名*/
	public int addSign(Sign sign);
	
	/**编辑签名*/
	public int editSign(Sign sign);
	
	/**删除加签名*/
	public int delSign(int id);
	
	/**设置默认名*/
	public int modifyDefaultSign(Sign sign);
	/**根据默认名查询*/
	public List<Sign> getDefaultSignList();
}