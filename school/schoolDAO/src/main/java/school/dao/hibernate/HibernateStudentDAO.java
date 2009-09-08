package school.dao.hibernate;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Student;
import school.dao.StudentDAO;

public class HibernateStudentDAO implements StudentDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(HibernateStudentDAO.class);
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}

	public void createStudent(Student student) {
		getSession().save(student);
	}

	public void deleteStudent(Long id) {
		Student student = readStudent(id);
		if(student == null) {
			throw new RuntimeException("Invalid id: " + id);
		}
		getSession().delete(student);
	}

	public Student readStudent(Long id) {
		return (Student) getSession().get(Student.class, id);
	}

	public void updateStudent(Student student) {
		getSession().update(student);
	}

}
