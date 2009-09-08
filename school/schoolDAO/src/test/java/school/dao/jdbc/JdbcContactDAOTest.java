package school.dao.jdbc;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.BoBeanEqualsFactory;
import school.bo.Contact;
import school.dao.ContactDAO;
import school.dao.DAOTestCase;

public class JdbcContactDAOTest extends DAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(JdbcContactDAOTest.class);
	
	private Connection connection;

	private ContactDAO dao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		DataSource ds = new OracleDataSource();
		((OracleDataSource)ds).setURL(System.getProperty("jdbc.url"));
		((OracleDataSource)ds).setUser(System.getProperty("jdbc.username"));
		((OracleDataSource)ds).setPassword(System.getProperty("jdbc.password"));
		connection = ds.getConnection();

		dao = new JdbcContactDAO();
		((JdbcContactDAO) dao).setConnection(connection);
		
		connection.setAutoCommit(false);

	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
		
		if(connection != null) {
			try {
				connection.setAutoCommit(true);	
				connection.commit();
			} catch(SQLException sqle) {
				connection.rollback();
			} finally {
				connection.close();
			}
		}
		
	}
	
	@Test
	public void testCreateContact() throws Exception {
		
		Contact contact = new Contact("Test", "User");
		dao.createContact(contact);
		
		assertTrue("id not set", contact.getId() > 0);
		
		Contact dbContact = dao.readContact(contact.getId());
		assertNotNull("contact is null", dbContact);
		assertTrue("contact not same", 
			BoBeanEqualsFactory.equals(contact, dbContact));
		
	}
	
	@Test
	public void testReadContact() throws Exception {
		
		long id = 1;
		Contact contact = dao.readContact(id);
		assertNotNull(contact);
	}
	
	@Test
	public void testUpdateContact() throws Exception {
		
		Contact contact = new Contact("Test", "User");
		dao.createContact(contact);
		
		String newFirstName = "New First Name";
		contact.setFirstName(newFirstName);
		dao.updateContact(contact);
		
		Contact dbContact = dao.readContact(contact.getId());
		assertNotNull("contact is null", dbContact);
		assertEquals("wrong contact first name", newFirstName, dbContact.getFirstName());
		assertTrue("contact not same", 
			BoBeanEqualsFactory.equals(contact, dbContact));
	}
	
	@Test
	public void testDeleteContact() throws Exception {
		
		long id = 1;
		
		Contact dbContact = dao.readContact(id);
		assertNotNull("dbContact is null", dbContact);
		
		dao.deleteContact(dbContact.getId());
		
		Contact deletedDbContact = dao.readContact(id);
		assertNull("dbContact not deleted", deletedDbContact);
		
	}
	
}
