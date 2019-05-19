package domain.model.sql;

/**
 * 
 *  A custom RuntimeException class made for Exceptions in the sql classes.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class SqlException extends RuntimeException{

	
	/**
	 * The serial version unique ID variable.
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * Creates new SqlException without parameters
	 * 
	 */
	public SqlException() {
	}
	
	/**
	 * Initialise the exception with the given message and Throwable.
	 * 
	 * @param message
	 *        The error message of the exception.
	 * @param exception
	 *        The Throwable of the exception.
	 * @effect This new SqlException is initialised as new 
	 *         RuntimeException with the given message and exception.
	 *         | super(message,exception)
	 */
	public SqlException(String message, Throwable exception) {
		super(message,exception);
	}
	
	/**
	 * Initialise the exception with the given message. 
	 * 
	 * @param message
	 *        The error message of the exception.
	 * @effect This new SqlException is initialised as new 
	 *         RuntimeException with the given message.
	 *         | super(message)
	 */
	public SqlException(String message) {
		super(message);
	}
	
	/**
	 * Initialise the exception with the given Throwable. 
	 * 
	 * @param exception
	 *        The Throwable of the exception.
	 * @effect This new SqlException is initialised as new 
	 *         RuntimeException with the given exception.
	 *         | super(exception)
	 */
	public SqlException(Throwable exception) {
		super(exception);
	}
	
	
}
