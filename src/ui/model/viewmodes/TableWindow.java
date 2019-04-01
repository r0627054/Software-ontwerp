package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ui.model.components.Component;
import ui.model.components.TitleBar;

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
