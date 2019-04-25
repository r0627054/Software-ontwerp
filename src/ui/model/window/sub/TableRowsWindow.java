package ui.model.window.sub;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.RowsTable;
import ui.model.components.UICell;

public class TableRowsWindow extends TableWindow {

	/**
	 * Variable holding the title String that comes before the table name.
	 */
	public static final String TITLE_STRING = "Table rows of table: ";

	public TableRowsWindow(UUID tableId, String tableName, Map<List<Object>, LinkedHashMap<UUID, Object>> table) {
		super(tableId, TITLE_STRING + tableName);
		createTable(table);
	}

	private void createTable(Map<List<Object>, LinkedHashMap<UUID, Object>> tableInformation) {
		setContainer(new Container(getX(), getY(), getWidth(), getHeight()));

		RowsTable rowsTable = new RowsTable(CONTENT_OFFSET_X + getX(), CONTENT_OFFSET_Y + getY(), getId());
		List<UICell> cellList = rowsTable.createTable(tableInformation);

		for (UICell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(rowsTable);
		rowsTable.addPropertyChangeListener(this);

		getContainer().addComponent(rowsTable);
		this.resetAllListeners();
	}

	public void updateRowsTable(Map<List<Object>, LinkedHashMap<UUID, Object>> tableInformation) {
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();
		this.createTable(tableInformation);
		this.setPaused(false);
	}

	public void pauseSubWindow(int columnIndex, UUID columnId) {
		UICell errorCell = this.getRowsTable().getCell(columnIndex, columnId);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);

	}

	public void resumeSubWindow() {
		this.setPaused(false);
		this.getRowsTable().resetError();
		this.resetAllListeners();
	}

	private RowsTable getRowsTable() {
		for (Component container : getContainer().getComponentsList()) {
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

	@Override
	public void ctrlEntrPressed() {
		if (!isPaused()) {
			getSupport().firePropertyChange(
					new PropertyChangeEvent(this.getId(), ChangeEventType.CREATE_TABLEDESIGNWINDOW, null, null));
		}
	}

	@Override
	public void updateContent(Object... tableData) {
		super.updateContent(tableData);
		this.updateRowsTable((Map<List<Object>, LinkedHashMap<UUID, Object>>) tableData[2]);
		this.setTableName(TITLE_STRING + (String) tableData[0]);
	}

	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
		for (Component c : getContainer().getComponentsList()) {
			c.throwError(id);
		}
	}
}
