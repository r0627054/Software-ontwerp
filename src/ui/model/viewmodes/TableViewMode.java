package ui.model.viewmodes;

import java.util.UUID;

public abstract class TableViewMode extends ViewMode {

	private UUID id;
	private String tableName;

	public TableViewMode(UUID id, String tableName) {
		super();
		this.setId(id);
		this.setTableName(tableName);
	}

	public String getTableName() {
		return tableName;
	}

	private void setTableName(String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException("Cannot create a TableViewMode with a null tableName");
		}
		this.tableName = tableName;
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
