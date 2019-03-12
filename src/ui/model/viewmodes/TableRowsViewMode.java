package ui.model.viewmodes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.RowsTable;
import ui.model.components.TextField;

public class TableRowsViewMode extends TableViewMode {
	private Container container;

	public TableRowsViewMode(UUID tableId, String tableName, Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table) {
		super(tableId, tableName);
		this.setType(ViewModeType.TABLEROWSVIEWMODE);
		createTable(table);
	}

	private void createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation) {
		container = new Container(0, 0, 600, 600);
		this.addComponent(new TextField(50, 5, 200, 25, "Table rows of: " + getTableName(), getId()));

		RowsTable table = new RowsTable(50, 50, 200, 200, getId());
		table.createTable(tableInformation);

		table.addPropertyChangeListener(this);
		this.addClickListener(table);
		getContainer().addComponent(table);
		this.addComponent(container);
	}

	public void updateRowsTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation) {
		this.removeAllComponents();
		this.removeAllClickAndKeyListeners();
		this.createTable(tableInformation);
		this.setPaused(false);
	}

	private Container getContainer() {
		return container;
	}

}
