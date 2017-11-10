package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Gus;
import mail.mapper.GusMapper;
import mail.service.GusService;

@Component("gusService")
public class GusServiceImpl implements GusService {

	@Autowired
	private GusMapper gusMapper ;

	public Gus checkNameExist(Gus gus) throws RuntimeException{
		
		return gusMapper.checkNameExist(gus) ;
	}

	public Gus getGus(int id) {
		
		return gusMapper.getGus(id) ;
	}
	
	public List<String> getGusList(int gid) {
		
		return gusMapper.getGusList(gid) ;
	}

	public void editGus(Gus gus) {

		gusMapper.editGus(gus) ;
	}

	public void addGus(Gus gus) {

		gusMapper.addGus(gus) ;
	}

	public void deleteGus(int id) {
		
		gusMapper.deleteGus(id) ;
	}
}
