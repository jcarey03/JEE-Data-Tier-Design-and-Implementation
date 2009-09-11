package school.dao.jpa;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Student;
import school.dao.DataAccessException;
import school.dao.StudentDAO;

public class JpaStudentDAO implements StudentDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(JpaStudentDAO.class);
	
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void createStudent(Student student) {
		getEntityManager().persist(student);
	}

	public void deleteStudent(Long id) throws DataAccessException {
		
		// need to get student into persistence context
		Student student = readStudent(id);
		if(student == null) {
			throw new DataAccessException("Invalid id: " + id);
		}
		getEntityManager().remove(student);
	}

	public Student readStudent(Long id) {
		return (Student) getEntityManager().find(Student.class, id);
	}

	public void updateStudent(Student student) {
		getEntityManager().merge(student);
	}

}
