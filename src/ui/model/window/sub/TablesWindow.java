package ui.model.window.sub;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;
import ui.model.components.UICell;

/**
 * A TablesWindow is specific SubWindow.
 *  It contains a container which stores all the names of the tables.
 *
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class TablesWindow extends SubWindow {

	/**
	 * Variable holding the title String.
	 */
	public static final String TITLE_STRING_PREFIX = "TABLES LIST";

	/**
	 * Initialises a new TablesWindow with the given information.
	 *
	 * @param map
	 *        | Containing all the UUID's and names of the tables.
	 * @effect the full Tables list is created and all the information is set.
	 *        | super(null, TITLE_STRING_PREFIX);
	 *        |	this.createTableList(map);
	 */
	public TablesWindow(Map<UUID, String> map) {
		super(null, TITLE_STRING_PREFIX);
		this.createTableList(map);
	}
	

	/**
	 * Creates a tableList out of the given information.
	 * @param map
	 *        | the map containing all the information for the list.
	 * @effect A new Table is created.
	 */
	private void createTableList(Map<UUID, String> map) {
		setContainer(new Container(getX(), getY(), getWidth(), getHeight()));

		TableList tableList = new TableList(CONTENT_OFFSET_X + getX(), CONTENT_OFFSET_Y + getY(), getWidth(),
				getHeight());
		List<UICell> cellList = tableList.createTableList(map);

		for (UICell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(tableList);
		tableList.addPropertyChangeListener(this);

		getContainer().addComponent(tableList);
		this.resetAllListeners();
	}

	/**
	 * Updates the old table and initialises it with all the new information.
	 * @param map
	 *        | the map containing all the information for the list.
	 * @effect the current table is updated with the new information.
	 */
	public void updateTables(Map<UUID, String> map) {
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();
		this.createTableList(map);
		this.setPaused(false);
	}

	/**
	 * Updates the content of the SubWindow with the given data.
	 * @param data the table data needed for the update
	 * @effect The table is updated.
	 *         | updateTables((Map<UUID, String>) data[0])
	 */
	@Override
	public void updateContent(Object... data) {
		this.removeContentClickAndKeyListeners();
		this.clearStoredListeners();
		updateTables((Map<UUID, String>) data[0]);
		this.setPaused(false);
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
	@Override
	public void pauseSubWindow(int columnIndex, UUID id) {
		UICell errorCell = this.getTableList().getCell(id);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);
	}

	/**
	 * The tablesWindow does nothing for resuming.
	 */
	@Override
	public void resumeSubWindow() {
	}

	/**
	 * The error is thrown to all the components in the tablesWindow.
	 * @param id
	 *        | The id of which element an error is thrown.
	 * @param newValue    The new value for the component.
	 * @param columnIndex The index of the component which contains the error.
	 * @effect All the components are called and the error is given.
	 *        | for (Component c : getComponents())
	 *        |		c.throwError(id);
	 */
	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
		for (Component c : getContainer().getComponentsList()) {
			c.throwError(id);
		}
	}

	/**
	 * Returns the rowsTable of the TablesWindow.
	 * @return the rowsTable (TableList) of the tablesWindow.
	 */
	private TableList getTableList() {
		for (Component c : getContainer().getComponentsList()) {
			if (c instanceof TableList) {
				return (TableList) c;
			}
		}
		return null;
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		super.keyPressed(id, keyCode, keyChar);
		
		if (keyCode == KeyEvent.VK_CONTROL) {
			this.setCtrlPressed(true);
		} else if (keyCode == 70 && this.isCtrlPressed()) {
			this.ctrlFPressed();
			this.setCtrlPressed(false);
		} else {
			setCtrlPressed(false);
		}
	}

	private void ctrlFPressed() {
		UICell selected = getTableList().getSelectedCell();
		if (selected != null) {
			EditableTextField etf = (EditableTextField) selected.getComponent();
			getSupport().firePropertyChange(new PropertyChangeEvent(selected.getId(),
					ChangeEventType.CREATE_FORMDESIGNSUBWINDOW, etf.getText(), null));
		}
	}

}
