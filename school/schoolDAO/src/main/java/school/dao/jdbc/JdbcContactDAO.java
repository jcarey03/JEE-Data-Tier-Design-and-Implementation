package school.dao.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import school.bo.Contact;
import school.dao.ContactDAO;
import school.dao.DataAccessException;

public class JdbcContactDAO implements ContactDAO {

	private static final Logger logger = 
		LoggerFactory.getLogger(JdbcContactDAO.class);
	
	private Connection connection;
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void createContact(Contact contact) throws DataAccessException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			/*
			 * Oracle requires you to specify the identity column.  You cannot 
			 * use the Statement.RETURN_GENERATED_KEYS flag.
			 */
			pstmt = connection.prepareStatement(INSERT_SQL, new String[] {"ID"});
			
			pstmt.setString(1, contact.getFirstName());
			pstmt.setString(2, contact.getLastName());
			pstmt.setString(3, contact.getMiddleInitial());
			
			pstmt.executeUpdate();
			
			/* set the primary key value on the contact instance */
			rs = pstmt.getGeneratedKeys();
			if(rs != null && rs.next()) {

				long id = rs.getLong(1);
				
				Method setIdMethod =
					contact.getClass().
					getDeclaredMethod("setId", Long.TYPE);
				setIdMethod.setAccessible(true);
				setIdMethod.invoke(contact, id);
				
			}

		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		} catch(NoSuchMethodException nsme) {
			throw new DataAccessException(nsme);
		} catch(InvocationTargetException ite) {
			throw new DataAccessException(ite);
		} catch(IllegalAccessException iae) {
			throw new DataAccessException(iae);
		} finally {
			
			if(pstmt != null) {
				try { 
					pstmt.close(); 
				} catch (SQLException sqle) {
					logger.warn(sqle.toString());
				}
			}
			
		}
		
	}

	public void deleteContact(Long id) throws DataAccessException {
		
		PreparedStatement pstmt = null;
		try {
			
			pstmt = connection.prepareStatement(DELETE_SQL);
			pstmt.setLong(1, id);
			
			// delete record and check that it was successful
			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated != 1) {
				throw new DataAccessException("contact not deleted: " + id);
			}
			
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		} finally {
			
			if(pstmt != null) {
				try { 
					pstmt.close(); 
				} catch (SQLException sqle) {
					logger.warn(sqle.toString());
				}
			}
			
		}
	}

	public Contact readContact(Long id) throws DataAccessException {
		
		Contact contact = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			pstmt = connection.prepareStatement(READ_SQL);
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			// go to the first record
			if(rs.next()) {
				
				contact = new Contact(id);
				contact.setFirstName(rs.getString("FIRST_NAME"));
				contact.setLastName(rs.getString("LAST_NAME"));
				contact.setMiddleInitial(rs.getString("MIDDLE_INITIAL"));
				
			}
			
			return contact;
			
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		} finally {
			
			if(pstmt != null) {
				try { 
					pstmt.close(); 
				} catch (SQLException sqle) {
					logger.warn(sqle.toString());
				}
			}
			
		}
		
	}

	public void updateContact(Contact contact) throws DataAccessException {

		PreparedStatement pstmt = null;
		try {
			
			pstmt = connection.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, contact.getFirstName());
			pstmt.setString(2, contact.getLastName());
			pstmt.setString(3, contact.getMiddleInitial());
			pstmt.setLong(4, contact.getId());
			
			// update record and check that it was successful
			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated != 1) {
				throw new DataAccessException(
					"contact not updated: " + contact.getId());
			}
			
		} catch(SQLException sqle) {
			throw new DataAccessException(sqle);
		} finally {
			
			if(pstmt != null) {
				try { 
					pstmt.close(); 
				} catch (SQLException sqle) {
					logger.warn(sqle.toString());
				}
			}
			
		}
			
	}

	private static final String INSERT_SQL = 
		"insert into CONTACT " +
		"(" +
		"ID," +
		"FIRST_NAME," +
		"LAST_NAME," +
		"MIDDLE_INITIAL" +
		")" +
		"values " +
		"(" +
		"CONTACT_SEQ.nextval, ?, ?, ?" +
		")";
	
	private static final String READ_SQL =
		"select " +
		"FIRST_NAME," +
		"LAST_NAME," +
		"MIDDLE_INITIAL " +
		"from CONTACT " +
		"where ID = ?";
	
	private static final String UPDATE_SQL =
		"update CONTACT set " +
		"FIRST_NAME = ?," +
		"LAST_NAME = ?," +
		"MIDDLE_INITIAL = ? " +
		"where ID = ?";
	
	private static final String DELETE_SQL =
		"delete from CONTACT where ID = ?";
}
