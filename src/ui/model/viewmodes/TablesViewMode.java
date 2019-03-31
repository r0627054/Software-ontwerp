package ui.model.viewmodes;

import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.TableList;
import ui.model.components.TextField;

/**
 * A TablesViewMode is specific ViewMode.
 *  It contains a container which stores all the names of tables.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class TablesViewMode extends ViewMode {

	/**
	 * Variable storing the container.
	 */
	private Container container;

	/**
	 * Initialises a new TablesViewMode with the given information.
	 * 
	 * @param map
	 *        | Containing all the UUID's and names of the tables.
	 * @effect the full Tables list is created and all the information is set.
	 */
	public TablesViewMode(Map<UUID, String> map) {
		super();
		this.setType(ViewModeType.TABLESVIEWMODE);
		this.createTableList(map);
		this.addComponent(new TextField(50, 5, 200, 25, "TABLES LIST"));
	}

	/**
	 * Creates a tableList out of the given information.
	 * @param map
	 *        | the map containing all the information of a viewMode.
	 */
	private void createTableList(Map<UUID, String> map) {
		container = new Container(getX(), getY(), getWidth(), getHeight());
		this.addComponent(getContainer());

		TableList tableList = new TableList(50, 50, 600, 600);
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
		this.removeAllClickAndKeyListeners();
		this.removeComponent(getContainer());
		this.createTableList(map);
	}

	/**
	 * Returns the container variable of the TablesViewMode.
	 */
	private Container getContainer() {
		return container;
	}

}
