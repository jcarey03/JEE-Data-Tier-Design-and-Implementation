package school.dao.hibernate;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.BoBeanEqualsFactory;
import school.bo.Student;
import school.dao.StudentDAO;

public class HibernateStudentDAOTest extends HibernateDAOTestCase {

	@SuppressWarnings("unused")
	private Logger logger = 
		LoggerFactory.getLogger(HibernateStudentDAOTest.class);
	
	private Transaction tx;

	private StudentDAO dao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();

		dao = new HibernateStudentDAO();
		((HibernateStudentDAO) dao).setSession(session);
		
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
	public void testCreateStudent() throws Exception {
		
		Student student = new Student("Test", "User", "Math");
		dao.createStudent(student);
	
		session.flush();
		session.clear();
		
		assertTrue("id not set", student.getId() > 0);
		
		Student dbStudent = dao.readStudent(student.getId());
		assertNotNull("student is null", dbStudent);
		assertTrue("student not same", 
			BoBeanEqualsFactory.equals(student, dbStudent));
		
	}
	
	@Test
	public void testReadStudent() throws Exception {
		
		long id = 1;
		Student student = dao.readStudent(id);
		assertNotNull(student);
	}
	
	@Test
	public void testUpdateStudent() throws Exception {
		
		Student student = new Student("Test", "User", "Math");
		dao.createStudent(student);
	
		session.flush();
		session.clear();
		
		String newFirstName = "New First Name";
		student.setFirstName(newFirstName);		// inherited property
		
		String newMajor = "Really Cool Major";
		student.setMajor(newMajor);				// student property
		
		dao.updateStudent(student);
		
		session.flush();
		session.clear();
		
		Student dbStudent = dao.readStudent(student.getId());
		assertNotNull("student is null", dbStudent);
		assertEquals("wrong contact first name", newFirstName, dbStudent.getFirstName());
		assertEquals("wrong student major", newMajor, dbStudent.getMajor());
		assertTrue("student not same", 
			BoBeanEqualsFactory.equals(student, dbStudent));
	}
	
	@Test
	public void testDeleteStudent() throws Exception {
		
		long id = 1;
		
		Student dbStudent = dao.readStudent(id);
		assertNotNull("dbStudent is null", dbStudent);
		
		dao.deleteStudent(dbStudent.getId());
		
		session.flush();
		session.clear();
		
		Student deletedDbStudent = dao.readStudent(id);
		
//		/*
//		 * Use below try-catch if using session.load()
//		 */
//		try {
//			Hibernate.initialize(deletedDbStudent);
//		} catch(ObjectNotFoundException onfe) {
//			logger.debug(onfe.toString());
//			return;
//		}
//		fail("deletedDbStudent not deleted");
		
		assertNull("deletedDbStudent not null", deletedDbStudent);
		
	}
	
}
