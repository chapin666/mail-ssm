package mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import adm.bean.Logger;

import mail.bean.Email;
import mail.bean.Group;
import mail.bean.Sign;
import mail.bean.Tag;
import mail.bean.Units;
import mail.bean.User;

public interface UserMapper {
	
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
	@Select("select * from tags where userid=#{unid} limit #{start},#{end}")
	public List<Tag> setmail(User user);	
	/**统计收件箱所以未读标记邮件记录*/
	@Select("SELECT COUNT(*) from tags t,etag e, email_recv r WHERE t.id = e.tid and e.eid = r.id and t.`name` = #{name} and r.state = '0' and t.userid=#{unid}")
	public int getnoreadtag(User user);
	/**根据标记名统计所以被标记邮件记录*/
	@Select("SELECT COUNT(*)+" +
			"(SELECT COUNT(*) from tags t,etag e, email_send r WHERE t.id = e.tid and e.eid = r.id and t.`name` = #{name} and t.userid=#{unid}  and e.type=2)+" +
			"(SELECT COUNT(*) from tags t,etag e, email_drafts r WHERE t.id = e.tid and e.eid = r.id and t.`name` = #{name} and t.userid=#{unid}  and e.type=3) " +
			"from tags t,etag e, email_recv r WHERE t.id = e.tid and e.eid = r.id and t.`name` = #{name} and t.userid=#{unid}  and e.type=1")
	public int getsumtag(User user);
	/**查询所有标签邮件记录*/
	@Select("SELECT COUNT(*)+(SELECT COUNT(*) FROM tags t,etag e,email_send s WHERE t.id = e.tid AND e.eid = s.id and s.frommail like '%${username}%' and e.type=2)+" +
			"(SELECT COUNT(*) from tags t,etag e, email_drafts d WHERE t.id = e.tid and e.eid = d.id and d.frommail like '%${username}%' and e.type=3) " +
			"FROM tags t,etag e,email_recv r WHERE	t.id = e.tid AND e.eid = r.id and r.tomail like '%${username}%' and e.type=1;")
	public int getTagMailCount(User user);
	/**查询所有收件箱标记邮件*/
	@Select("SELECT r.id,r.mailid,r.frommail,rd.`subject`,r.time,r.state,e.type FROM tags t,etag e,email_recv r,emaildata_recv rd WHERE	t.id = e.tid AND e.eid = r.id AND r.mailid = rd.mailid and r.tomail like '%${username}%' and e.type=1;")
	public List<Email> getTagMailRecv(User user);
	/**查询所有发件箱标记邮件*/
	@Select("SELECT s.id,s.mailid,s.frommail,sd.`subject`,s.time,s.state,e.type FROM tags t,etag e,email_send s,emaildata_send sd WHERE	t.id = e.tid AND e.eid = s.id AND s.mailid = sd.mailid and s.frommail like '%${username}%' and e.type=2;")
	public List<Email> getTagMailSend(User user);
	/**查询所有草稿箱标记邮件*/
	@Select("SELECT d.id,d.frommail,d.see,d.title,d.time,e.type FROM tags t,etag e,	email_drafts d WHERE t.id = e.tid AND e.eid = d.id and d.frommail like '%${username}%' and e.type=3;")
	public List<Email> getTagMailDrafts(User user);
	
	/**根据名字查询所有收件箱标记邮件*/
	@Select("SELECT r.id,r.mailid,r.frommail,rd.`subject`,r.time,r.state,e.type FROM tags t,etag e,email_recv r,emaildata_recv rd WHERE	t.id = e.tid AND e.eid = r.id AND r.mailid = rd.mailid and t.`name` = #{name} and r.tomail like '%${username}%' and e.type=1;")
	public List<Email> getTagNameMailRecv(User name);
	/**根据名字查询所有发件箱标记邮件*/
	@Select("SELECT s.id,s.mailid,s.frommail,sd.`subject`,s.time,s.state,e.type FROM tags t,etag e,email_send s,emaildata_send sd WHERE	t.id = e.tid AND e.eid = s.id AND s.mailid = sd.mailid and t.`name` = #{name} and s.frommail like '%${username}%' and e.type=2;")
	public List<Email> getTagNameMailSend(User name);
	/**根据名字查询所有草稿箱标记邮件*/
	@Select("SELECT d.id,d.frommail,d.see,d.title,d.time,e.type FROM tags t,etag e,	email_drafts d WHERE t.id = e.tid AND e.eid = d.id and t.`name` = #{name} and d.frommail like '%${username}%' and e.type=3;")
	public List<Email> getTagNameMailDrafts(User name);
	
	/**根据mailid删除收件箱标记邮件记录*/
	@Delete("delete from email_recv where mailid = #{mailid}")
	public void delTagNameMailRecv(String mailid);
	/**根据mailid删除发件箱标记邮件记录*/
	@Delete("delete from email_send where mailid = #{mailid}")
	public void delTagNameMailSend(String mailid);
	/**根据id删除草稿箱标记邮件记录*/
	@Delete("delete from email_drafts where id = #{id}")
	public void delTagNameMailDrafts(int id);
	
	/**查询所有签名*/
	@Select("select * from sign where userid = #{id}")
	public List<Sign> getSignList(int id);
	
	/**检查签名的名字是否重复*/
	@Select("select * from sign where userid = #{userid} and name = #{name}")
	public Sign checkSignNameExist(Sign sign);
	
	/**添加签名*/
	@Insert("insert into sign(userid,name,content) values(#{userid},#{name},#{content})")
	public int addSign(Sign sign);
	
	/**编辑签名*/
	@Update("update sign set name = #{name} ,content = #{content} where id = #{id}")
	public int editSign(Sign sign);
	
	/**删除加签名*/
	@Delete("delete from sign where id = #{id}")
	public int delSign(int id);
	
	/**清空所有默认名称*/
	@Update("UPDATE sign set defaultsign=''")
	public void clearDefaultSign();
	/**设置默认签名*/
	@Update("update sign set defaultsign = #{defaultsign} where id = #{id}")
	public int modifyDefaultSign(Sign sign);
	
	/**根据默认名查询*/
	@Select("select * from sign where defaultsign!='' or defaultsign!= NULL")
	public List<Sign> getDefaultSignList();
}
