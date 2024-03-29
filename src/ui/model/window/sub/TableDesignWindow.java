package ui.model.window.sub;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.UICell;

/**
 * The tableDesignWindow is a TableWindow specifically used for designing
 * the table. The tableDesignWindow allows edits of the design of the table.
 *
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class TableDesignWindow extends TableWindow {

	/**
	 * Variable holding the title String that comes before the table name.
	 */
	public static final String TITLE_STRING_PREFIX = "Designing table: ";

	/**
	 * Initialises a new TableDesignWindow with the given information.
	 *
	 * @param id                    | the id of the table.
	 * @param tableName             | the name of the table.
	 * @param columnCharacteristics | the characteristics of a table (it contains
	 *                              all the information needed to edit and show the
	 *                              design.
	 * @effect the TableDesignWindow is created with the given information and a designTable
	 *         is created.
	 */
	public TableDesignWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, TITLE_STRING_PREFIX + tableName);
		this.createDesignTable(columnCharacteristics);
	}

	/**
	 * Creates a DesignTable with all the given information. ( all the listeners are
	 * registered )
	 *
	 * @param columnCharacteristics | the information needed to create a
	 *                              designTable.
	 * @effect The table is created and all the listeners are stored.
	 */
	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		setContainer(new Container(getX(), getY(), getWidth(), getHeight()));

		DesignTable table = new DesignTable(CONTENT_OFFSET_X + getX(), CONTENT_OFFSET_Y + getY(), getWidth(),
				getHeight(), getTableName(), this.getId());
		List<UICell> cellList = table.createTable(columnCharacteristics);

		for (UICell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(table);
		table.addPropertyChangeListener(this);

		getContainer().addComponent(table);
		this.resetAllListeners();
	}

	/**
	 * Deletes the previous design table and updates the TableDesignWindow by creating a new
	 * DesignTable.
	 *
	 * @param columnCharacteristics | the information needed to create a
	 *                              designTable.
	 */
	public void updateDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();
		this.createDesignTable(columnCharacteristics);
		this.setPaused(false);
	}

	/**
	 * Returns the DesignTable stored in the container of the SubWindow.
	 * @return The designTable inside the designTable.
	 */
	private DesignTable getDesignTable() {
		for (Component c : getContainer().getComponentsList()) {
			if (c instanceof DesignTable) {
				return (DesignTable) c;
			}
		}
		return null;
	}

	/**
	 * Pauses the current subwindow information, the only cell which can be edited is the one
	 * with the given columnIndex and given columnId.
	 * Everything in the titlebar can still be clicked.
	 *
	 * @param columnIndex | the index of the column where the cell is situated.
	 * @param columnId    | the columnId of the column where the cell is situated.
	 * @effect All the keyListeners and clickListeners different from this one cell (and title bar)
	 *         are removed.
	 */
	public void pauseSubWindow(int columnIndex, UUID columnId) {
		this.setPaused(true);
		UICell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.removeAllContentListenersButOne(errorCell);
		errorCell.setError(true);
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
		this.getDesignTable().resetError();
		this.resetAllListeners();
	}

	/**
	 * Sets an error to the cell inside the design table and updates the value to
	 * the new value.
	 *
	 * @param columnIndex | the index of the column where the cell is situated.
	 * @param columnId    | the columnId of the column where the cell is situated.
	 * @param newValue    | the new Value of the cell inside the subWindow.
	 * @effect the cell is updated to the newValue and the cell is in error state.
	 */
	public void setErrorDesignTableCell(UUID columnId, int columnIndex, Object newValue) {
		UICell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		errorCell.setErrorWithNewValue(true, newValue);
	}

	/**
	 * Returns whether there is a cell in the table that is selected.
	 * @return true if there is a cell selected in the table; otherwise false.
	 */
	public boolean hasASelectedCell() {
		for (Component comp : getStoredListeners()) {
			if (comp instanceof UICell) {
				UICell compCell = (UICell) comp;
				if (compCell.hasSelectedEditableTextField()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Handles the crtl enter behaviour.
	 * Fires the create_tableRowsWindow propertyChangeEvent
	 */
	private void ctrlEntrPressed() {
		if (!isPaused() && !hasASelectedCell()) {
			getSupport().firePropertyChange(
					new PropertyChangeEvent(this.getId(), ChangeEventType.CREATE_TABLEROWSWINDOW, null, null));
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		super.keyPressed(id, keyCode, keyChar);

		if (keyCode == KeyEvent.VK_CONTROL) {
			this.setCtrlPressed(true);
		} else if (keyCode == KeyEvent.VK_ENTER && this.isCtrlPressed()) {
			this.ctrlEntrPressed();
			this.setCtrlPressed(false);
		} else {
			setCtrlPressed(false);
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
		this.updateDesignTable((Map<UUID, LinkedHashMap<String, Object>>) tableData[1]);
		this.setTableName(TITLE_STRING_PREFIX + (String) tableData[0]);
	}

	/**
	 * Handles the throw error of a component with the given ID.
	 * @param id
	 *        | The id of which element an error is thrown.
	 * @param newValue    The new value for the component.
	 * @param columnIndex The index of the component which contains the error.
	 * @effect Calls the setErrorDesignTable function which will handle the error.
	 *         |this.setErrorDesignTableCell(id, columnIndex, newValue)
	 */
	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
		this.setErrorDesignTableCell(id, columnIndex, newValue);
	}

}
