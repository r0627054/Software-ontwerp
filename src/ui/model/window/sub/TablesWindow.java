package ui.model.window.sub;

import java.util.Map;
import java.util.UUID;

import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.HorizontalComponentList;
import ui.model.components.RowsTable;
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
	 * Initialises a new TablesViewMode with the given information.
	 * 
	 * @param map
	 *        | Containing all the UUID's and names of the tables.
	 * @effect the full Tables list is created and all the information is set.
	 */
	public TablesWindow(Map<UUID, String> map) {
		super(null, TITLE_STRING_PREFIX);
		this.createTableList(map);
	}

	/**
	 * Creates a tableList out of the given information.
	 * @param map
	 *        | the map containing all the information of a viewMode.
	 */
	private void createTableList(Map<UUID, String> map) {
		this.setContainer(new Container(getX(), getY(), getWidth(), getHeight()));
		this.addComponent(getContainer());

		TableList tableList = new TableList(CONTENT_OFFSET_X + getX(), CONTENT_OFFSET_Y + getY(), getWidth(),
				getHeight());
		tableList.createTableList(map, this);
		this.addClickListener(tableList);
		this.addKeyListener(tableList);
		tableList.addPropertyChangeListener(this);

		getContainer().addComponent(tableList);
	}

	/**
	 * Updates the old table and initialises it with all the new information.
	 * @param map
	 *        | the map containing all the information of a viewMode.
	 */
	public void updateTables(Map<UUID, String> map) {
		this.removeContentClickAndKeyListeners();
		this.removeComponent(getContainer());
		this.createTableList(map);
		this.setPaused(false);
	}

	@Override
	public void ctrlEntrPressed() {
	}

	@Override
	public void updateContent(Object... data) {
		updateTables((Map<UUID, String>) data[0]);
	}

	@Override
	public void pauseSubWindow(int columnIndex, UUID id) {
		UICell errorCell = this.getTableList().getCell(id);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);
	}

	@Override
	public void resumeSubWindow() {
		// TODO Auto-generated method stub
		// DO NOTHING
	}

	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
		for (Component c : getComponents()) {
			c.throwError(id);
		}
	}

	private TableList getTableList() {
		for (Component c : getContainer().getComponentsList()) {
			if (c instanceof TableList) {
				return (TableList) c;
			}
		}
		return null;
	}

}
