package ui.model.viewmodes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui.model.components.UICell;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.RowsTable;
import ui.model.components.TextField;

public class TableRowsViewMode extends TableViewMode {
	private Container container;

	public TableRowsViewMode(UUID tableId, String tableName, Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table,
			Map<UUID, Class<?>> columnTypes) {
		super(tableId, tableName);
		this.setType(ViewModeType.TABLEROWSVIEWMODE);
		createTable(table, columnTypes);
	}

	private void createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation,
			Map<UUID, Class<?>> columnTypes) {
		container = new Container(0, 0, 600, 600);
		this.addComponent(new TextField(50, 5, 200, 25, "Table rows of: " + getTableName(), getId()));

		RowsTable rowsTable = new RowsTable(50, 50, getId());
		List<UICell> cellList = rowsTable.createTable(tableInformation, columnTypes);
		
		this.clearStoredListeners();
		for (UICell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}
		
		this.addStoredListener(rowsTable);
		rowsTable.addPropertyChangeListener(this);
		
		getContainer().addComponent(rowsTable);
		this.addComponent(getContainer());
		this.addAllListeners();
	}

	public void updateRowsTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation,
			Map<UUID, Class<?>> columnTypes) {
		this.removeAllComponents();
		this.removeAllClickAndKeyListeners();
		this.createTable(tableInformation, columnTypes);
	}

	public void pauseViewMode(int columnIndex, UUID columnId) {
		this.setPaused(true);
		UICell errorCell = this.getRowsTable().getCell(columnIndex, columnId);
		this.removeAllClickListenersButOne(errorCell);
		this.removeAllKeyListenersButOne(errorCell);
		errorCell.setError(true);
	}
	
	public void resumeViewMode(int columnIndex, UUID columnId) {
		this.setPaused(false);
		UICell errorCell = this.getRowsTable().getCell(columnIndex, columnId);
		this.addAllListeners();
		errorCell.setError(false);
	}
	
	private RowsTable getRowsTable() {
		for (Component container : getComponents()) {
			if (container instanceof Container) {
				Container containerCasted = (Container) container;
				for (Component c : containerCasted.getComponentsList()) {
					if (c instanceof RowsTable) {
						return (RowsTable) c;
					}
				}
			}
		}
		return null;
	}

	private Container getContainer() {
		return container;
	}

}
