package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Ggs;
import mail.mapper.GgsMapper;
import mail.service.GgsService;

@Component("ggsService")
public class GgsServiceImpl implements GgsService {

	@Autowired
	private GgsMapper ggsMapper ;

	public Ggs checkNameExist(Ggs ggs) throws RuntimeException{
		
		return ggsMapper.checkNameExist(ggs) ;
	}

	public Ggs getGgs(int id) {
		
		return ggsMapper.getGgs(id) ;
	}
	
	public List<String> getGgsList(int gid) {
		
		return ggsMapper.getGgsList(gid) ;
	}

	public void editGgs(Ggs ggs) {

		ggsMapper.editGgs(ggs) ;
	}

	public void addGgs(Ggs ggs) {

		ggsMapper.addGgs(ggs) ;
	}

	public void deleteGgs(int id) {
		
		ggsMapper.deleteGgs(id) ;
	}
}
