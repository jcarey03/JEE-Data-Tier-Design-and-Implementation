package school.dao.ibatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import school.bo.Contact;
import school.dao.ContactDAO;

/**
 * This class is similar to {@link IbatisContactDao IbatisContactDao}, but uses
 * Spring's <code>SqlMapClientTemplate</code> class.  The template has several
 * convenience methods and translates SQL exceptions into unchecked Spring
 * exceptions.
 */
public class SpringIbatisContactDAO implements ContactDAO {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
		LoggerFactory.getLogger(SpringIbatisContactDAO.class);
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public void createContact(Contact contact) {
		sqlMapClientTemplate.insert("createContact", contact);
	}

	public void deleteContact(Long id) {
		sqlMapClientTemplate.delete("deleteContact", id);
	}

	public Contact readContact(Long id) {
		return (Contact) sqlMapClientTemplate.queryForObject("readContact", id);
	}

	public void updateContact(Contact contact) {
		sqlMapClientTemplate.update("updateContact", contact);
	}

}
