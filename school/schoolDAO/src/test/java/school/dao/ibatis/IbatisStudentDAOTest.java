package school.dao.ibatis;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.BoBeanEqualsFactory;
import school.bo.Student;
import school.dao.StudentDAO;

import com.ibatis.sqlmap.client.SqlMapSession;

public class IbatisStudentDAOTest extends IbatisDAOTestCase {

	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(IbatisStudentDAOTest.class);

	private SqlMapSession session;
	
	private StudentDAO studentDao;
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();

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
	public void testCreateStudent() throws Exception {
		
		Student student = new Student("Test", "User", "English");
		studentDao.createStudent(student);
		
		assertTrue("id not set", student.getId() > 0);
		
		Student dbStudent = studentDao.readStudent(student.getId());
		assertNotNull("student is null", dbStudent);
		assertTrue("student not same", 
			BoBeanEqualsFactory.equals(student, dbStudent));
		
	}
	
	@Test
	public void testReadStudent() throws Exception {
		
		long id = 1;
		Student student = studentDao.readStudent(id);
		assertNotNull(student);
	}
	
	@Test
	public void testUpdateStudent() throws Exception {
		
		Student student = new Student("Test", "User", "Cool Major");
		studentDao.createStudent(student);
		
		String newFirstName = "New First Name";
		student.setFirstName(newFirstName);		// inherited property
		
		String newMajor = "Really Cool Major";
		student.setMajor(newMajor);				// student property
		
		studentDao.updateStudent(student);
		
		Student dbStudent = studentDao.readStudent(student.getId());
		assertNotNull("student is null", dbStudent);
		assertEquals("wrong contact first name", newFirstName, dbStudent.getFirstName());
		assertEquals("wrong student major", newMajor, dbStudent.getMajor());
		assertTrue("student not same", 
			BoBeanEqualsFactory.equals(student, dbStudent));
	}
	
	@Test
	public void testDeleteStudent() throws Exception {
		
		long id = 1;
		
		Student dbStudent = studentDao.readStudent(id);
		assertNotNull("dbStudent is null", dbStudent);
		
		studentDao.deleteStudent(dbStudent.getId());
		
		Student deletedDbStudent = studentDao.readStudent(id);
		assertNull("dbStudent not deleted", deletedDbStudent);
		
	}
	
}
