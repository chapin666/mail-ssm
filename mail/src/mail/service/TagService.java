package mail.service;

import java.util.List;

import mail.bean.Tag;

public interface TagService {

	public Tag checkNameExist(Tag tag) throws RuntimeException;
		
	public Tag getTag(int id);

	public List<Tag> getTagList(int userid);
	
	public void editTag(Tag tag);

	public void addTag(Tag tag);

	public void deleteTag(int id);

	public void delAll();	
}