package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Units;
import mail.mapper.UnitsMapper;
import mail.service.UnitsService;

@Component("unitsService")
public class UnitsServiceImpl implements UnitsService {

	@Autowired
	private UnitsMapper unitsMapper ;

	public Units checkNameExist(String name) throws RuntimeException{
		
		return unitsMapper.checkNameExist(name) ;
	}

	public Units getUnits(int id) {
		
		return unitsMapper.getUnits(id) ;
	}

	public void deleteUnits(int id){
		
		unitsMapper.deleteUnits(id);
	}
	
	public void editUnits(Units unit) {

		unitsMapper.editUnits(unit) ;
	}
	
	public void addUnits(Units unit) {

		unitsMapper.addUnits(unit) ;
	}

	public List<Units> getUnitsList(Units unit) {

		return unitsMapper.getUnitsList(unit);
	}

	public List<String> getIdByPids(String uids) {

		return unitsMapper.getIdbyPids(uids);
	}
}
