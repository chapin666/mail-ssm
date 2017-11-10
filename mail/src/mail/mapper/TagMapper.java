package mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import mail.bean.Tag;

public interface TagMapper {
	
	public Tag checkNameExist(Tag tag) throws RuntimeException;
		
	public Tag getTag(int id);
	
	public List<Tag> getTagList(int userid);
	
	public void editTag(Tag tag);

	public void addTag(Tag tag);

	public void deleteTag(int id);

	public void delAll();
	
}
