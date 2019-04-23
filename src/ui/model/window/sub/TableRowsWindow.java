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

/**
 * The TableRowsWindow is a TableWindow specifically used for editiong the table information.
 *  The TableRowsWindow allows edits of the values of the table.
 * 
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class TableRowsWindow extends TableWindow {

	/**
	 * Variable holding the title String that comes before the table name.
	 */
	public static final String TITLE_STRING_PREFIX = "Table rows of table: ";

	/**
	 * Initialises a new TableRowsWindow with the given information.
	 * @param tableId    The id of the table.
	 * @param tableName  The name of the table.
	 * @param table      The information of the table.
	 * @effect The TableRowsWindow is created with the given information.
	 *         | super(tableId, TITLE_STRING_PREFIX + tableName);
	 *         | createTable(table);
	 */
	public TableRowsWindow(UUID tableId, String tableName, Map<List<Object>, LinkedHashMap<UUID, Object>> table) {
		super(tableId, TITLE_STRING_PREFIX + tableName);
		createTable(table);
	}

	/**
	 * Creates a (Rows) Table with all the given information.
	 *  All the listeners are registered.
	 * @param tableInformation All the information in the table.
	 * @effect The table is created and all the listeners are stored.
	 */
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
		this.addComponent(getContainer());
		this.resetAllListeners();
	}

	/**
	 * Deletes the previous rows table and updates the the Window by creating a new RowsTable.
	 * 
	 * @param tableInformation The information needed to create a rowsTable
	 * @effect The rows table is updated with the new information by deleting the
	 *           previous information and creating a new table.
	 */
	public void updateRowsTable(Map<List<Object>, LinkedHashMap<UUID, Object>> tableInformation) {
		this.removeComponent(getContainer());
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();
		this.createTable(tableInformation);
		this.setPaused(false);
	}

	/**
	 * Pauses the current subWindow information, the only cell which can be edited is the one
	 * with the given columnIndex and given columnId.
	 * Everything in the titlebar can still be clicked.
	 *
	 * @param columnIndex | the index of the column where the cell is situated.
	 * @param columnId    | the columnId of the column where the cell is situated.
	 * @effect All the keyListeners and clickListeners different from this one cell (and title bar)
	 *         are removed.
	 */
	public void pauseSubWindow(int columnIndex, UUID columnId) {
		UICell errorCell = this.getRowsTable().getCell(columnIndex, columnId);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);

	}

	/**
	 * Resumes the current SubWindow information, adds all the key and click-listeners such that
	 * the user can edit again.
	 *
	 * @param columnIndex | the index of the column where the cell is situated.
	 * @param columnId    | the columnId of the column where the cell is situated.
	 * @effect All the keyListeners and clickListeners different from this one cell
	 *         are added again.
	 */
	public void resumeSubWindow() {
		this.setPaused(false);
		this.getRowsTable().resetError();
		this.resetAllListeners();
	}

	/**
	 * Returns the RowsTable stored in the container of the SubWindow.
	 * @return The rowsTable inside the TableRowsWindow.
	 */
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

	/**
	 * Handles the crtl enter behaviour.
	 * Fires the CREATE_TABLEDESIGNWINDOW propertyChangeEvent
	 */
	@Override
	public void ctrlEntrPressed() {
		if (!isPaused()) {
			getSupport().firePropertyChange(
					new PropertyChangeEvent(this.getId(), ChangeEventType.CREATE_TABLEDESIGNWINDOW, null, null));
		}
	}

	/**
	 * Updates the content of the SubWindow with the given tableData.
	 * @param tableData the table data needed for the update
	 * @effect The table data is updated.
	 */
	@Override
	public void updateContent(Object... tableData) {
		super.updateContent(tableData);
		this.updateRowsTable((Map<List<Object>, LinkedHashMap<UUID, Object>>) tableData[2]);
		this.setTableName(TITLE_STRING_PREFIX + (String) tableData[0]);
	}

	/**
	 * Handles the throw error of a component with the given ID.
	 * @param id
	 *        | The id of which element an error is thrown.
	 * @param newValue    The new value for the component.
	 * @param columnIndex The index of the component which contains the error.
	 * @effect Gives the error to all the components inside the RowsTable
	 *         | for (Component c : getComponents()) {
	 *         |	c.throwError(id);
	 */
	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
		for (Component c : getComponents()) {
			c.throwError(id);
		}
	}
}
