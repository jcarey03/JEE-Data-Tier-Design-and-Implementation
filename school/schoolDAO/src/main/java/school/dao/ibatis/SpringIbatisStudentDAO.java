package school.dao.ibatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import school.bo.Student;
import school.dao.StudentDAO;

/**
 * This class is similar to {@link IbatisStudentDao IbatisStudentDao}, but uses
 * Spring's <code>SqlMapClientTemplate</code> class.  The template has several
 * convenience methods and translates SQL exceptions into unchecked Spring
 * exceptions.
 */
public class SpringIbatisStudentDAO implements StudentDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(SpringIbatisStudentDAO.class);
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public void createStudent(Student student) {
		sqlMapClientTemplate.insert("createContact", student);
		sqlMapClientTemplate.insert("createStudent", student);
	}

	public void deleteStudent(Long id) {
		sqlMapClientTemplate.delete("deleteStudent", id);
	}

	public Student readStudent(Long id) {
		return (Student) sqlMapClientTemplate.queryForObject("readStudent", id);
	}

	public void updateStudent(Student student) {
		sqlMapClientTemplate.update("updateContact", student);
		sqlMapClientTemplate.update("updateStudent", student);
	}

}
