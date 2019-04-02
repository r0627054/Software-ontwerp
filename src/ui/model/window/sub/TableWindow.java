package ui.model.window.sub;

import java.util.UUID;

public abstract class TableWindow extends SubWindow {

	
	private String tableName;

	public TableWindow(UUID id, String tableName) {
		super(id,tableName);
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
}
