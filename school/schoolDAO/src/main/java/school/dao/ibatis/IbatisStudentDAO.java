package school.dao.ibatis;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Student;
import school.dao.DataAccessException;
import school.dao.StudentDAO;

import com.ibatis.sqlmap.client.SqlMapClient;

public class IbatisStudentDAO implements StudentDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(IbatisStudentDAO.class);
	
	private SqlMapClient sqlMapClient;
	
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public void createStudent(Student student) throws DataAccessException {
		try {
			sqlMapClient.insert("createContact", student);
			sqlMapClient.insert("createStudent", student);
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}

	public void deleteStudent(Long id) throws DataAccessException {
		try {
			sqlMapClient.delete("deleteStudent", id);
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}

	public Student readStudent(Long id) throws DataAccessException {
		try {
			return (Student) sqlMapClient.queryForObject("readStudent", id);
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}

	public void updateStudent(Student student) throws DataAccessException {
		try {
			sqlMapClient.update("updateContact", student);
			sqlMapClient.update("updateStudent", student);
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		}
	}

}
