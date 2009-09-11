package school.dao.jpa;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.BoBeanEqualsFactory;
import school.bo.Student;
import school.dao.StudentDAO;

public class JpaStudentDAOTest extends JpaDAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(JpaStudentDAOTest.class);
	
	private EntityTransaction tx;

	private StudentDAO dao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();

		dao = new JpaStudentDAO();
		((JpaStudentDAO) dao).setEntityManager(em);
		
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
	public void testCreateStudent() throws Exception {
		
		Student student = new Student("Test", "User", "Math");
		dao.createStudent(student);
	
		em.flush();
		em.clear();
		
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
	
		em.flush();
		em.clear();
		
		String newFirstName = "New First Name";
		student.setFirstName(newFirstName);		// inherited property
		
		String newMajor = "Really Cool Major";
		student.setMajor(newMajor);				// student property
		
		dao.updateStudent(student);
		
		em.flush();
		em.clear();
		
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
		
		em.flush();
		em.clear();
		
		Student deletedDbStudent = dao.readStudent(id);
		
//		/*
//		 * Use the below try-catch if using entityManager.getReference()
//		 */
//		try {
//			deletedDbStudent.getFirstName();
//		} catch(EntityNotFoundException enfe) {
//			logger.debug(enfe.toString());
//			return;
//		}
//		fail("dbContact not deleted");
		
		assertNull("dbStudent not deleted", deletedDbStudent);
		
	}
	
}
