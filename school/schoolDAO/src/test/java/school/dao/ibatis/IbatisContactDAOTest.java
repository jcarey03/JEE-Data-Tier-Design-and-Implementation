package school.dao.ibatis;
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

import com.ibatis.sqlmap.client.SqlMapSession;

public class IbatisContactDAOTest extends IbatisDAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(IbatisContactDAOTest.class);

	private SqlMapSession session;
	
	private ContactDAO contactDao;
	
	private StudentDAO studentDao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		contactDao = new IbatisContactDAO();
		((IbatisContactDAO) contactDao).setSqlMapClient(sqlMapClient);
		
		studentDao = new IbatisStudentDAO();
		((IbatisStudentDAO) studentDao).setSqlMapClient(sqlMapClient);
		
		session = sqlMapClient.openSession();
		session.startTransaction();

	}
	
	@After
	public void tearDown() throws Exception {
		
		if(session != null) {
			try {
				session.commitTransaction();
			} finally {
				try {
					session.endTransaction();
				} finally {
					session.close();
				}
			}
		}
		
		super.tearDown();
	}
	
	@Test
	public void testCreateContact() throws Exception {
		
		Contact contact = new Contact("Test", "User");
		contactDao.createContact(contact);
		
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
		
		String newFirstName = "New First Name";
		contact.setFirstName(newFirstName);
		contactDao.updateContact(contact);
		
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
		
		Contact deletedDbContact = contactDao.readContact(id);
		assertNull("dbContact not deleted", deletedDbContact);
		
		// test for cascading delete
		Student deletedDbStudent = studentDao.readStudent(id);
		assertNull("dbStudent not deleted", deletedDbStudent);
		
	}
	
}
