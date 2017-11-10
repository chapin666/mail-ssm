package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.UGroup;
import mail.mapper.UGroupMapper;
import mail.service.UGroupService;

@Component("uGroupService")
public class UGroupServiceImpl implements UGroupService {

	@Autowired
	private UGroupMapper uGroupMapper ;

	public UGroup checkNameExist(UGroup ugroup) throws RuntimeException{
		
		return uGroupMapper.checkNameExist(ugroup) ;
	}

	public UGroup getUGroup(int id) {
		
		return uGroupMapper.getUGroup(id) ;
	}
	
	public List<UGroup> getUGroupList(int userid) {
		
		return uGroupMapper.getUGroupList(userid) ;
	}

	public void editUGroup(UGroup ugroup) {

		uGroupMapper.editUGroup(ugroup) ;
	}

	public void addUGroup(UGroup ugroup) {

		uGroupMapper.addUGroup(ugroup) ;
	}

	public void deleteUGroup(int id) {
		
		uGroupMapper.deleteUGroup(id) ;
	}

	public void delAll() {

		uGroupMapper.delAll() ;
	}
}
