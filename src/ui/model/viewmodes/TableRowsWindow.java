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

public class TableRowsWindow extends TableWindow {
	private Container container;

	public TableRowsWindow(UUID tableId, String tableName, Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table,
			Map<UUID, Class<?>> columnTypes) {
		super(tableId, "Table rows of table: " + tableName);
		this.setType(ViewModeType.TABLEROWSVIEWMODE);
		createTable(table, columnTypes);
	}

	private void createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation,
			Map<UUID, Class<?>> columnTypes) {
		container = new Container(getX(), getY(), getWidth(), getHeight());

		RowsTable rowsTable = new RowsTable(CONTENT_OFFSET_X + getX(), CONTENT_OFFSET_Y + getY(), getId());
		List<UICell> cellList = rowsTable.createTable(tableInformation, columnTypes);

		for (UICell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(rowsTable);
		rowsTable.addPropertyChangeListener(this);

		getContainer().addComponent(rowsTable);
		this.addComponent(getContainer());
		this.resetAllListeners();
	}

	public void updateRowsTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableInformation,
			Map<UUID, Class<?>> columnTypes) {
		this.removeComponent(getContainer());
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();
		this.createTable(tableInformation, columnTypes);
	}

	public void pauseViewMode(int columnIndex, UUID columnId) {
		UICell errorCell = this.getRowsTable().getCell(columnIndex, columnId);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);

	}

	public void resumeViewMode(int columnIndex, UUID columnId) {
		this.setPaused(false);
		UICell errorCell = this.getRowsTable().getCell(columnIndex, columnId);
		this.resetAllListeners();
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
