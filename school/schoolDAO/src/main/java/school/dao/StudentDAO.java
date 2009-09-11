package school.dao;

import school.bo.Student;

/**
 * A Data Access Object for {@link Student}s that supports basic CRUD operations.
 */
public interface StudentDAO {

	void createStudent(Student student) throws DataAccessException;
	
	Student readStudent(Long id) throws DataAccessException;
	
	void updateStudent(Student student) throws DataAccessException;
	
	void deleteStudent(Long id) throws DataAccessException;
	
}
