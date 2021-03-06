package school.dao.hibernate;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.BoBeanEqualsFactory;
import school.bo.Contact;
import school.bo.Student;
import school.dao.ContactDAO;
import school.dao.StudentDAO;

public class HibernateContactDAOTest extends HibernateDAOTestCase {
	
	private static final Logger logger = 
		LoggerFactory.getLogger(HibernateContactDAOTest.class);
	
	private static final Long INVALID_CONTACT_ID = 1000L;
	
	private Transaction tx;

	private ContactDAO contactDao;
	
	private StudentDAO studentDao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();

		contactDao = new HibernateContactDAO();
		((HibernateContactDAO) contactDao).setSession(session);
		
		studentDao = new HibernateStudentDAO();
		((HibernateStudentDAO) studentDao).setSession(session);
		
		tx = session.beginTransaction();

	}
	
	@After
	public void tearDown() throws Exception {
		
		if(tx != null) {
			if(tx.wasRolledBack()) {
				tx.rollback();
			} else {
				tx.commit();
			}
		}
		
		super.tearDown();
		
	}
	
	@Test
	public void testCreateContact() throws Exception {
		
		Contact contact = new Contact("Test", "User");
		contactDao.createContact(contact);
	
		session.flush();
		session.clear();
		
		assertTrue("id not set", contact.getId() > 0);
		
		Contact dbContact = contactDao.readContact(contact.getId());
		assertNotNull("contact is null", dbContact);
		assertTrue("contact not same", 
			BoBeanEqualsFactory.equals(contact, dbContact));
		
	}
	
	@Test
	public void testReadContact() throws Exception {
		
		long id = 1;
		Contact contact = contactDao.readContact(id);
		assertNotNull(contact);
	}
	
	@Test
	public void testUpdateContact() throws Exception {
		
		Contact contact = new Contact("Test", "User");
		contactDao.createContact(contact);
	
		session.flush();
		session.clear();
		
		String newFirstName = "New First Name";
		contact.setFirstName(newFirstName);
		contactDao.updateContact(contact);
		
		session.flush();
		session.clear();
		
		Contact dbContact = contactDao.readContact(contact.getId());
		assertNotNull("contact is null", dbContact);
		assertEquals("wrong contact first name", newFirstName, dbContact.getFirstName());
		assertTrue("contact not same", 
			BoBeanEqualsFactory.equals(contact, dbContact));
	}
	
	@Test
	public void testDeleteContact() throws Exception {
		
		long id = 1;
		
		Contact dbContact = contactDao.readContact(id);
		assertNotNull("dbContact is null", dbContact);
		
		contactDao.deleteContact(dbContact.getId());
		
		session.flush();
		session.clear();
		
		Contact deletedDbContact = contactDao.readContact(id);
		
		assertNull("deletedDbContact not null", deletedDbContact);
		
		// test for cascading delete
		Student deletedDbStudent = studentDao.readStudent(id);
		assertNull("dbStudent not deleted", deletedDbStudent);
		
	}
	
	@Test
	public void testInvalidReadContact() throws Exception {
		
		Contact invalidDbContact = 
			((HibernateContactDAO) contactDao).readContact(INVALID_CONTACT_ID);
		
		// should be null because session.get(...) returns null if not found
		assertNull("invalidDbContact not null", invalidDbContact);
		
	}
	
	@Test
	public void testInvalidReadContactByLoad() throws Exception {

		Contact invalidDbContact = 
			((HibernateContactDAO) contactDao).readContactByLoad(INVALID_CONTACT_ID);
		
		// should not be null because it is a proxy, but it will throw error
		// when hydrated
		assertNotNull("invalidDbContact is null", invalidDbContact);
		
		try {
			Hibernate.initialize(invalidDbContact);
		} catch(ObjectNotFoundException onfe) {
			logger.debug(onfe.toString());
			return;
		}
		fail("invalidDbContact exists");
		
	}
	
}
