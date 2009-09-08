package school.dao.hibernate;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Contact;
import school.dao.ContactDAO;

public class HibernateContactDAO implements ContactDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(HibernateContactDAO.class);
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}
	
	public void createContact(Contact contact) {
		getSession().save(contact);
	}

	public void deleteContact(Long id) {
		Contact contact = readContact(id);
		if(contact == null) {
			throw new RuntimeException("Invalid id: " + id);
		}
		getSession().delete(contact);
	}

	public Contact readContact(Long id) {
		return (Contact) getSession().get(Contact.class, id);
	}

	public void updateContact(Contact contact) {
		getSession().update(contact);
	}

}
