package ui.model.viewmodes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.Table;

public class TableRowsViewMode extends TableViewMode {
	private Container container;

	public TableRowsViewMode(UUID id, String tableName, Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table) {
		super(id, tableName);
		this.setType(ViewModeType.TABLEROWSVIEWMODE);
		createTable(table);
	}

	private void createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation) {
		container = new Container(0, 0, 600, 600);
		Table table = new Table(50, 30, 200, 200, tableInformation);
		this.addClickListener(table);
		getContainer().addComponent(table);
		this.addComponent(container);
	}

	public void updateTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation) {
		this.removeComponent(this.getContainer());
		this.removeAllClickAndKeyListeners();
		this.createTable(tableInformation);
	}

	private Container getContainer() {
		return container;
	}

}
