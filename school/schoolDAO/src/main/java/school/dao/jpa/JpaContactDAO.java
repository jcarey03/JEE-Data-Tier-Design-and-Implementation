package school.dao.jpa;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Contact;
import school.dao.ContactDAO;
import school.dao.DataAccessException;

public class JpaContactDAO implements ContactDAO {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(JpaContactDAO.class);
	
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void createContact(Contact contact) {
		getEntityManager().persist(contact);
	}

	public void deleteContact(Long id) throws DataAccessException {
		
		// need to get contact into persistence context
		Contact contact = readContact(id);
		if(contact == null) {
			throw new DataAccessException("Invalid id: " + id);
		}
		getEntityManager().remove(contact);
	}

	public Contact readContact(Long id) {
		return (Contact) getEntityManager().find(Contact.class, id);
	}

	public void updateContact(Contact contact) {
		getEntityManager().merge(contact);
	}
	
}
