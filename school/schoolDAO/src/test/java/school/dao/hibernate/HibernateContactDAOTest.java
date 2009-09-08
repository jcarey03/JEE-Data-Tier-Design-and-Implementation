package school.dao.hibernate;
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

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(HibernateContactDAOTest.class);
	
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
		
//		/*
//		 * Use below try-catch if using session.load()
//		 */
//		try {
//			Hibernate.initialize(deletedDbContact);
//		} catch(ObjectNotFoundException onfe) {
//			logger.debug(onfe.toString());
//			return;
//		}
//		fail("deletedDbContact not deleted");
		
		assertNull("deletedDbContact not null", deletedDbContact);
		
		// test for cascading delete
		Student deletedDbStudent = studentDao.readStudent(id);
		assertNull("dbStudent not deleted", deletedDbStudent);
		
	}
	
}
