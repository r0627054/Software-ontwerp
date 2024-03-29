package domain.model;

/**
 *  A custom RuntimeException class made for Exceptions in the domain.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 * 
 */
public class DomainException extends RuntimeException {

	/**
	 * The serial version unique ID variable.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new DomainException without parameters
	 * 
	 */
	public DomainException() {
	}
	
	/**
	 * Initialise the exception with the given message and Throwable.
	 * 
	 * @param message
	 *        The error message of the exception.
	 * @param exception
	 *        The Throwable of the exception.
	 * @effect This new DomainException is initialised as new 
	 *         RuntimeException with the given message and exception.
	 *         | super(message,exception)
	 */
	public DomainException(String message, Throwable exception) {
		super(message,exception);
	}
	
	/**
	 * Initialise the exception with the given message. 
	 * 
	 * @param message
	 *        The error message of the exception.
	 * @effect This new DomainException is initialised as new 
	 *         RuntimeException with the given message.
	 *         | super(message)
	 */
	public DomainException(String message) {
		super(message);
	}
	
	/**
	 * Initialise the exception with the given Throwable. 
	 * 
	 * @param exception
	 *        The Throwable of the exception.
	 * @effect This new DomainException is initialised as new 
	 *         RuntimeException with the given exception.
	 *         | super(exception)
	 */
	public DomainException(Throwable exception) {
		super(exception);
	}
	
	
}
