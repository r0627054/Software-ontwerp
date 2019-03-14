package domain.model;

import java.util.UUID;

/**
 * An ObjectIdentifier is an abstract class which creates a unique id's.
 * This unique id is generated using the java UUID class.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public abstract class ObjectIdentifier {

	/**
	 * Variable storing the Unique id.
	 */
	private final UUID id;
	
	/**
	 * Initialise a new ObjectIdentifier which generates and sets a unique id.
	 */
	public ObjectIdentifier() {
		id = UUID.randomUUID();
	}

	/**
	 * Returns the id of the ObjectIdentifier.
	 * @return the UUID
	 */
	public UUID getId() {
		return id;
	}	
}
