package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ui.model.components.Component;
import ui.model.components.TitleBar;

public abstract class TableWindow extends SubWindow {

	private UUID id;
	private String tableName;


	public TableWindow(UUID id, String tableName) {
		super(tableName);
		this.setId(id);
		this.setTableName(tableName);
	}

	public abstract void pauseViewMode(int columnIndex, UUID columnId);

	public abstract void resumeViewMode(int columnIndex, UUID columnId);

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
