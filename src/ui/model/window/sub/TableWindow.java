package ui.model.window.sub;

import java.util.UUID;

/**
 * A TableWindow is super class for all the windows containg a table.
 *  The tableWindow stores the table name of that window.
 *  Itself is a sub class of SubWindow.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public abstract class TableWindow extends SubWindow {

	/**
	 * Variable storing the name of the table.
	 */
	private String tableName;

	/**
	 * Initialises a new TableWindow with the given tableName.
	 * @param id        The id of the table.
	 * @param tableName The name of the table.
	 * @effect The tableWindow is created with the id and name.
	 *         | super(id, tableName);
	 *         | this.setTableName(tableName);
	 */
	public TableWindow(UUID id, String tableName) {
		super(id, tableName);
		this.setTableName(tableName);
	}

	/**
	 * Returns the name of the table.
	 * @return The name of the table.
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets the name of the table.
	 * @throws IllegalArgumentException When the tableName equals null.
	 *                                  | tableName == null
	 * @param tableName The name of the table.
	 * @post The name variable equals the name parameter.
	 *       | new.getTableName() == tableName
	 * @effect The titleBar of the window is updated with the new name.
	 *       |  this.getTitleBar().updateTitle(tableName)
	 */
	protected void setTableName(String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException("Cannot create a TableViewMode with a null tableName");
		}
		this.tableName = tableName;
		this.getTitleBar().updateTitle(tableName);
	}

	/**
	 * The updateContent should do nothing for the tableWindow.
	 */
	@Override
	public void updateContent(Object... tableData) {
	}
}
