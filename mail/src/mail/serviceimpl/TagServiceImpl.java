package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Tag;
import mail.mapper.TagMapper;
import mail.service.TagService;

@Component("tagService")
public class TagServiceImpl implements TagService {

	@Autowired
	private TagMapper tagMapper ;

	public Tag checkNameExist(Tag tag) throws RuntimeException{
		
		return tagMapper.checkNameExist(tag) ;
	}

	public Tag getTag(int id) {
		
		return tagMapper.getTag(id) ;
	}
	
	public List<Tag> getTagList(int userid) {
		
		return tagMapper.getTagList(userid) ;
	}

	public void editTag(Tag tag) {

		tagMapper.editTag(tag) ;
	}

	public void addTag(Tag tag) {

		tagMapper.addTag(tag) ;
	}

	public void deleteTag(int id) {
		
		tagMapper.deleteTag(id) ;
	}

	public void delAll() {

		tagMapper.delAll() ;
	}	
}
