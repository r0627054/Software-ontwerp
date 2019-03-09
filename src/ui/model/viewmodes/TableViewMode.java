package ui.model.viewmodes;

import java.util.UUID;

public abstract class TableViewMode extends ViewMode {

	private UUID id;

	public TableViewMode(String name, UUID id) {
		super(name);
		this.setId(id);
	}

	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot create a TableViewMode with a null ID");
		}
		this.id = id;
	}

}
