package mail.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mail.bean.Contact;
import mail.mapper.ContactMapper;
import mail.service.ContactService;

@Component("contactService")
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactMapper contactMapper ;

	public Contact checkAddrExist(Contact contact) throws RuntimeException{
		
		return contactMapper.checkAddrExist(contact) ;
	}

	public Contact getContact(int id) {
		
		return contactMapper.getContact(id) ;
	}
	
	public int getSize(Contact contact){
		
		return contactMapper.getSize(contact);
	}
	
	public List<Contact> getContactList(Contact contact) {
		
		return contactMapper.getContactList(contact) ;
	}

	public void editContact(Contact contact) {

		contactMapper.editContact(contact) ;
	}

	public void addContact(Contact contact) {

		contactMapper.addContact(contact) ;
	}
	
	public void deleteContact(String[] ids){
		
		contactMapper.deleteContact(ids);
	}

	public List<Contact> getContactListByUgid(String ugid) {

		return contactMapper.getContactListByUgid(ugid);
	}
	
	public List<Contact> getContactListByUserid(int userid) {

		return contactMapper.getContactListByUserid(userid);
	}

	public void delAll() {

		contactMapper.delAll();		
	}
}
