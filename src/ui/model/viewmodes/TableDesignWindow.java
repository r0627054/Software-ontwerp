package ui.model.viewmodes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui.model.components.UICell;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.TextField;

/**
 * The tableDesignViewMode is a TableViewMode specifically used for designing the table.
 *  The tableDesignViewMode can allow edits of the table and can handles pauses.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class TableDesignWindow extends TableWindow {

	/**
	 * Variable storing the container.
	 */
	private Container container;

	/**
	 * Initialises a new TableDesignViewMode with the given information.
	 * 
	 * @param id
	 *        | the id of the table.
	 * @param tableName
	 *        | the name of the table.
	 * @param columnCharacteristics
	 *        | the characteristics of a table (it contains all the information needed to edit and show the design.
	 * @effect the viewMode is created with the given information and a designTable is created and added to the viewMode.
	 */
	public TableDesignWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, "Designing table: " + tableName);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);

		this.createDesignTable(columnCharacteristics);
	}

	/**
	 * Creates a DesignTable with all the given information.
	 * ( all the listeners are registered )
	 * @param columnCharacteristics
	 *        | the information needed to create a designTable.
	 */
	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		container = new Container(getX(), getY(), getWidth(), getHeight());

		DesignTable table = new DesignTable(CONTENT_OFFSET_X + getX(), CONTENT_OFFSET_Y + getY(), getWidth(), getHeight(),
				getTableName(), this.getId());
		List<UICell> cellList = table.createTable(columnCharacteristics);

		for (UICell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(table);
		table.addPropertyChangeListener(this);

		getContainer().addComponent(table);
		this.addComponent(getContainer());
		this.resetAllListeners();
	}

	/**
	 * Deletes the previous design table and updates the viewMode by creating a new DesignTable.
	 * @param columnCharacteristics
	 *        | the information needed to create a designTable.
	 */
	public void updateDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.removeComponent(getContainer());
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();

		this.createDesignTable(columnCharacteristics);
		this.setPaused(false);
	}

	/**
	 * Returns the container variable of the TablesViewMode.
	 */
	private Container getContainer() {
		return container;
	}

	/**
	 * Returns the DesignTable stored in the container of the ViewMode.
	 */
	private DesignTable getDesignTable() {
		for (Component container : getComponents()) {
			if (container instanceof Container) {
				Container containerCasted = (Container) container;
				for (Component c : containerCasted.getComponentsList()) {
					if (c instanceof DesignTable) {
						return (DesignTable) c;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Pauses the current viewMode, the only cell which can be edited is the one with the given
	 * columnIndex and given columnId.
	 * 
	 * @param columnIndex
	 *        | the index of the column where the cell is situated.
	 * @param columnId
	 *        | the columnId of the column where the cell is situated.
	 * @effect All the keyListeners and clickListeners different from this one cell are removed. 
	 */
	public void pauseViewMode(int columnIndex, UUID columnId) {
		this.setPaused(true);
		UICell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.removeAllContentListenersButOne(errorCell);
		errorCell.setError(true);
	}

	/**
	 * Resumes the current viewMode, adds all the key and click-listeners such that the user can edit again.
	 * 
	 * @param columnIndex
	 *        | the index of the column where the cell is situated.
	 * @param columnId
	 *        | the columnId of the column where the cell is situated.
	 * @effect All the keyListeners and clickListeners different from this one cell are added again. 
	 */
	public void resumeViewMode(int columnIndex, UUID columnId) {
		this.setPaused(false);
		UICell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.resetAllListeners();
		errorCell.setError(false);
	}

	/**
	 * Sets an error to the cell inside the design table and updates the value to the new value.
	 * 
	 * @param columnIndex
	 *        | the index of the column where the cell is situated.
	 * @param columnId
	 *        | the columnId of the column where the cell is situated.
	 * @param newValue
	 *        | the new Value of the cell inside the DesignViewMode.
	 * @effect the cell is updated to the newValue and the cell is in error state.
	 */
	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
		UICell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		errorCell.setErrorWithNewValue(true, newValue);
	}

	/**
	 * Returns whether there is a cell in the viewMode that is selected.
	 * @return true if there is a cell selected in the viewMode; otherwise false.
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

}
