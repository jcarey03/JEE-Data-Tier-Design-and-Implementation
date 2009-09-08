package school.dao;

import school.bo.Student;

public interface StudentDAO {

	void createStudent(Student student);
	
	Student readStudent(Long id);
	
	void updateStudent(Student student);
	
	void deleteStudent(Long id);
	
}
