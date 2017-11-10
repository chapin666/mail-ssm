package mail.service;

import java.util.List;

import mail.bean.Gus;

public interface GusService {

	public Gus checkNameExist(Gus gus) throws RuntimeException;
		
	public Gus getGus(int id);

	public List<String> getGusList(int gid);
	
	public void editGus(Gus gus);

	public void addGus(Gus gus);

	public void deleteGus(int id);
	
}