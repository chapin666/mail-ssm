package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Gns;
import mail.mapper.GnsMapper;
import mail.service.GnsService;

@Component("gnsService")
public class GnsServiceImpl implements GnsService {

	@Autowired
	private GnsMapper gnsMapper ;

	public Gns checkNameExist(Gns gns) throws RuntimeException{
		
		return gnsMapper.checkNameExist(gns) ;
	}

	public Gns getGns(int id) {
		
		return gnsMapper.getGns(id) ;
	}
	
	public List<String> getGnsList(int gid) {
		
		return gnsMapper.getGnsList(gid) ;
	}

	public void editGns(Gns gns) {

		gnsMapper.editGns(gns) ;
	}

	public void addGns(Gns gns) {

		gnsMapper.addGns(gns) ;
	}

	public void deleteGns(int id) {
		
		gnsMapper.deleteGns(id) ;
	}
}
