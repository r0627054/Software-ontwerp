package ui.model.window.sub;

import java.util.UUID;

import ui.model.components.TitleBar;

public abstract class TableWindow extends SubWindow {

	private String tableName;

	public TableWindow(UUID id, String tableName) {
		super(id, tableName);
		this.setTableName(tableName);
	}

	public String getTableName() {
		return tableName;
	}

	protected void setTableName(String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException("Cannot create a TableViewMode with a null tableName");
		}
		this.tableName = tableName;
		this.getTitleBar().updateTitle(tableName);
	}

	@Override
	public void updateContent(Object... tableData) {
//		this.setTableName();
		//TODO
	}
}
