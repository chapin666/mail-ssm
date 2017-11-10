package mail.mapper;

import java.util.List;
import mail.bean.Contact;

public interface ContactMapper {
	
	public Contact checkAddrExist(Contact contact) throws RuntimeException;
		
	public Contact getContact(int id);
	
	public int getSize(Contact contact);
	
	public List<Contact> getContactList(Contact contact);
	
	public void editContact(Contact contact);

	public void addContact(Contact contact);
	
	public void deleteContact(String[] ids);

	public List<Contact> getContactListByUgid(String ugid);
	
	public List<Contact> getContactListByUserid(int userid);

	public void delAll();
	
}
