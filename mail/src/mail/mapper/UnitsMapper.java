package mail.mapper;

import java.util.List;
import mail.bean.Units;

public interface UnitsMapper {
	
	public Units checkNameExist(String name) throws RuntimeException;
		
	public Units getUnits(int id);
	
	public void deleteUnits(int id);
	
	public void editUnits(Units unit);

	public void addUnits(Units unit);

	public List<Units> getUnitsList(Units unit);

	public List<String> getIdbyPids(String uids);
	
}
