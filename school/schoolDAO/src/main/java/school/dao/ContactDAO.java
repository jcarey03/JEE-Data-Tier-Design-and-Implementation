package school.dao;

import school.bo.Contact;

public interface ContactDAO {

	void createContact(Contact contact);
	
	Contact readContact(Long id);
	
	void updateContact(Contact contact);
	
	void deleteContact(Long id);
	
}
