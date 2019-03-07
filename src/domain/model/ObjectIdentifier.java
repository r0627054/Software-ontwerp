package domain.model;

import java.util.UUID;

public abstract class ObjectIdentifier {

	private final UUID id;
	
	public ObjectIdentifier() {
		id = UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}	
}
