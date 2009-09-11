package school.dao;

import school.bo.Contact;

/**
 * A Data Access Object for {@link Contact}s that supports basic CRUD operations.
 */
public interface ContactDAO {

	void createContact(Contact contact) throws DataAccessException;
	
	Contact readContact(Long id) throws DataAccessException;
	
	void updateContact(Contact contact) throws DataAccessException;
	
	void deleteContact(Long id) throws DataAccessException;
	
}
