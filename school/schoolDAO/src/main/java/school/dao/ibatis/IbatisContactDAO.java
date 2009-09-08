package school.dao.ibatis;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Contact;
import school.dao.ContactDAO;

import com.ibatis.sqlmap.client.SqlMapClient;

public class IbatisContactDAO implements ContactDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(IbatisContactDAO.class);
	
	private SqlMapClient sqlMapClient;
	
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public void createContact(Contact contact) {
		try {
			sqlMapClient.insert("createContact", contact);
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	public void deleteContact(Long id) {
		try {
			sqlMapClient.delete("deleteContact", id);
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	public Contact readContact(Long id) {
		try {
			return (Contact) sqlMapClient.queryForObject("readContact", id);
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	public void updateContact(Contact contact) {
		try {
			sqlMapClient.update("updateContact", contact);
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

}
