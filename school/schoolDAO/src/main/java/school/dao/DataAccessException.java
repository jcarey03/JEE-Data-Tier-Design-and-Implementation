package school.dao;

/**
 * Generic data access exception for representing exceptional cases when 
 * interfacing with the database.  This exception may also be used to wrap 
 * "checked" exceptions that expose the underlying persistence framework or
 * database.
 */
@SuppressWarnings("serial")
public class DataAccessException extends RuntimeException {

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

}
