package mail.service;

import java.util.List;

import mail.bean.Gns;

public interface GnsService {

	public Gns checkNameExist(Gns gns) throws RuntimeException;
		
	public Gns getGns(int id);

	public List<String> getGnsList(int gid);
	
	public void editGns(Gns gns);

	public void addGns(Gns gns);

	public void deleteGns(int id);
	
}