package mail.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import util.Analy;
import util.Utf7Coder;

import adm.bean.Logger;

import mail.bean.Email;
import mail.bean.Group;
import mail.bean.Sign;
import mail.bean.Tag;
import mail.bean.Units;
import mail.bean.User;
import mail.mapper.UserMapper;
import mail.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper ;
	
	public User checkUserNameExist(User user) throws RuntimeException{
		
		return userMapper.checkUserNameExist(user) ;
	}

	public User getUser(int id) {
		
		return userMapper.getUser(id) ;
	}

	public void editUser(User user) {

		userMapper.editUser(user) ;
	}

	public void editPass(User user) {

		userMapper.editPass(user) ;
	}

	public List<User> getUsernameByUnits(Units uids) {

		return userMapper.getUsernameByUnits(uids);
	}
	
	public int getUsernameCountByUnits(String uids) {

		return userMapper.getUsernameCountByUnits(uids);
	}
	
	public List<User> getUsernameByGroup(Group group) {

		return userMapper.getUsernameByGroup(group);
	}
	
	public int getUsernameCountByGroup(Group group) {

		return userMapper.getUsernameCountByGroup(group);
	}
	
	public List<User> getAllUser(int state) {

		return userMapper.getAllUser(state);
	}
	
	public List<User> getUsernameByGroupids(String gids) {

		return userMapper.getUsernameByGroupids(gids);
	}

	public List<User> getUserByKey(String key) {
		return userMapper.getUserByKey(key);
	}
	
	public List<User> getUserByKey2(String key) {
		return userMapper.getUserByKey2(key);
	}
	
	public List<String> getUnids(Group group) {

		return userMapper.getUnids(group);
	}

	public void delAll() {

		userMapper.delAll();
	}

	public int getUserSize() {

		return userMapper.getUserSize();
	}

	public void addUser(User user) {

		userMapper.addUser(user);
	}
	/**根据类型查询最后一次登录的日子*/
	public List<Logger> getTypeLoginlog(Logger logger) {
		return userMapper.getTypeLoginlog(logger);
	}
	/**查询所有标签*/
	public List<Tag> setmail(User user) {
		return userMapper.setmail(user);
	}	
	/**根据标记名统计收件箱所以未读标记邮件*/
	public int getnoreadtag(User user) {
		return userMapper.getnoreadtag(user);
	}
	/**根据标记名统计所以被标记邮件*/
	public int getsumtag(User user) {
		return userMapper.getsumtag(user);
	}
	/**查询所有标签邮件记录*/
	public int getTagMailCount(User user) {		
		return userMapper.getTagMailCount(user);
	}	
	
	/**查询所有收件箱标记邮件*/
	public List<Email> getTagMailRecv(User user){			
		List<Email> listMail = new ArrayList<Email>();
    	listMail = userMapper.getTagMailRecv(user);		
    	return encodeSubjectMail(listMail);			
	}
	/**查询所有发件箱标记邮件*/
	public List<Email> getTagMailSend(User user){		
		List<Email> listMail = new ArrayList<Email>();
    	listMail = userMapper.getTagMailSend(user);    	
		return encodeSubjectMail(listMail);		
		
	}
	/**查询所有草稿箱标记邮件*/
	public List<Email> getTagMailDrafts(User user){		
		List<Email> listMail = new ArrayList<Email>();
    	listMail = userMapper.getTagMailDrafts(user);    	
    	return encodeTitleMail(listMail);
	}	
	
	/**根据名字查询所有收件箱标记邮件*/
	public List<Email> getTagNameMailRecv(User user){
		List<Email> listMail = new ArrayList<Email>();
    	listMail = userMapper.getTagNameMailRecv(user);		
    	return encodeSubjectMail(listMail);
		
	}
	/**根据名字查询所有发件箱标记邮件*/
	public List<Email> getTagNameMailSend(User user){		
		List<Email> listMail = new ArrayList<Email>();
    	listMail = userMapper.getTagNameMailSend(user);		
    	return encodeSubjectMail(listMail);
	}
	/**根据名字查询所有草稿箱标记邮件*/
	public List<Email> getTagNameMailDrafts(User user){		
		List<Email> listMail = new ArrayList<Email>();
    	listMail = userMapper.getTagNameMailDrafts(user);		
    	return encodeTitleMail(listMail);		
	}
	
	/**根据mailid删除收件箱标记邮件记录*/
	public void delTagNameMailRecv(String mailid){
		userMapper.delTagNameMailRecv(mailid);
	}
	/**根据mailid删除发件箱标记邮件记录*/
	public void delTagNameMailSend(String mailid){
		userMapper.delTagNameMailSend(mailid);
	}
	/**根据id删除草稿箱标记邮件记录*/
	public void delTagNameMailDrafts(int id){
		userMapper.delTagNameMailDrafts(id);
	}
	
	
	/**查询所有签名*/
	public List<Sign> getSignList(int id){
		return userMapper.getSignList(id);
	}
	
	/**检查签名的名字是否重复*/
	public Sign checkSignNameExist(Sign sign){
		return userMapper.checkSignNameExist(sign);
	}
	
	/**添加签名*/
	public int addSign(Sign sign){
		return userMapper.addSign(sign);
	}
	
	/**编辑签名*/
	public int editSign(Sign sign){
		return userMapper.editSign(sign);
	}
	
	/**删除加签名*/
	public int delSign(int id){
		return userMapper.delSign(id);
	}
	
	/**清空所有默认名称*/
	public void clearDefaultSign(){
		userMapper.clearDefaultSign();
	}
	
	/**设置默认名*/
	public int modifyDefaultSign(Sign sign){
		userMapper.clearDefaultSign();
		return userMapper.modifyDefaultSign(sign);
	}
	
	/**根据默认名查询*/
	public List<Sign> getDefaultSignList(){
		return userMapper.getDefaultSignList();
	}
	
	
	/**邮件subject编码*/
	public List<Email> encodeSubjectMail(List<Email> listMail){
		List<Email> listMail2 = new ArrayList<Email>();	
		Analy analy = new Analy();
		for(Email data : listMail){
			String subject = data.getSubject().trim();//去掉字符串左右的空格
			subject = analy.encodeSubjectMail(subject);//subject转码
			subject = subject.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'");
			data.setSubject(subject);
			listMail2.add(data);
		}
		return listMail2;
	}
	
	/**邮件title编码*/
	public List<Email> encodeTitleMail(List<Email> listMail){
		List<Email> listMail2 = new ArrayList<Email>();	
		Analy analy = new Analy();
		for(Email data : listMail){
			String subject = data.getTitle().trim();//去掉字符串左右的空格
			subject = analy.encodeSubjectMail(subject);//title转码
			subject = subject.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "'");
			data.setSubject(subject);
			listMail2.add(data);
		}
		return listMail2;
	}
}
