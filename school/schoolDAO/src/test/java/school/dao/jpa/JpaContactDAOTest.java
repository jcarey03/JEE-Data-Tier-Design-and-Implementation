package school.dao.jpa;
import javax.persistence.EntityTransaction;

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

public class JpaContactDAOTest extends JpaDAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(JpaContactDAOTest.class);
	
	private EntityTransaction tx;

	private ContactDAO contactDao;
	
	private StudentDAO studentDao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		contactDao = new JpaContactDAO();
		((JpaContactDAO) contactDao).setEntityManager(em);
		
		studentDao = new JpaStudentDAO();
		((JpaStudentDAO) studentDao).setEntityManager(em);
		
		tx = em.getTransaction();
		tx.begin();
	}
	
	@After
	public void tearDown() throws Exception {
		
		if(tx != null) {
			if(tx.getRollbackOnly()) {
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
	
		em.flush();
		em.clear();
		
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
	
		em.flush();
		em.clear();
		
		String newFirstName = "New First Name";
		contact.setFirstName(newFirstName);
		contactDao.updateContact(contact);
		
		em.flush();
		em.clear();
		
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
		
		em.flush();
		em.clear();
		
		Contact deletedDbContact = contactDao.readContact(id);
		
//		/*
//		 * Use the below try-catch if using entityManager.getReference()
//		 */
//		try {
//			deletedDbContact.getFirstName();
//		} catch(EntityNotFoundException enfe) {
//			logger.debug(enfe.toString());
//			return;
//		}
//		fail("dbContact not deleted");
		
		assertNull("dbContact not deleted", deletedDbContact);
		
		// test for cascading delete
		Student deletedDbStudent = studentDao.readStudent(id);
		assertNull("dbStudent not deleted", deletedDbStudent);
	}
	
}
