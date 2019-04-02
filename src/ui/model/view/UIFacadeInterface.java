package ui.model.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import controller.observer.PropertyChangeListener;
import ui.model.window.sub.SubWindow;

/**
 * An interface of the uiFacade. This interface defines all the functionalities
 * that should be handled the user interface.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public interface UIFacadeInterface {

	/**
	 * Shows the application window of the view.
	 */
	public void show();

//	/**
//	 * Startup method to initialise the first table overview. The map of keys
//	 * (tableId's) and Strings (table names) will be shown to the user on startup.
//	 * By default this should be empty, unless a table is loaded from memory or
//	 * initialised in the domain.
//	 * 
//	 * @param map The Map of tableIds and tableNames
//	 */
//	public void startup(Map<UUID, String> map);

	/**
	 * Adds a propertyChangeListener to the PropertyChangeSupport.
	 * 
	 * @param pcl | The propertyChangeListener.
	 * @effect Adds a propertyChangeListener to the PropertyChangeSupport. |
	 *         support.addPropertyChangeListener(pcl);
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl);

	/**
	 * Removes a propertyChangeListener from the PropertyChangeSupport.
	 *
	 * @param pcl | The propertyChangeListener.
	 * @effect Removes a propertyChangeListener from the PropertyChangeSupport. |
	 *         support.addPropertyChangeListener(pcl);
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl);

//	/**
//	 * Gets the ViewModeType of the current ViewMode from the view.
//	 * 
//	 * @return the type of the current viewmode of view. |
//	 *         getView().getCurrentViewModeType();
//	 */
//	public ViewModeType getCurrentViewModeType();

	/**
	 * Throws an error to a component.
	 * 
	 * @param id | The id of component that needs to receive the error.
	 */
	public void throwError(UUID id, int columnIndex, Object newValue);

	/**
	 * Opens the tableRowsViewMode To open this mode, we need data to fetch the
	 * already existing view mode and if this does not exist, we pass data to create
	 * a new tableRowsViewMode
	 * 
	 * @param tableId     | The tableId of the table that should be shown.
	 * @param tableName   | The table name of the table that should be shown.
	 * @param table       | A map containing all the information of to show the
	 *                    table.
	 * @param columnTypes | A map containing a class for each column, to determine
	 *                    if the value is null | What the column type should be.
	 */
	public void createTableRowsSubWindow(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes);

	public void updateTablesSubWindows(Map<UUID, String> tablesListData);
	
	public void updateTableRowsAndDesignSubWindows(UUID id, Map<UUID, LinkedHashMap<String, Object>> designData,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableRowsData, Map<UUID, Class<?>> rowsClassData) ;

//	/**
//	 * Updates the tablesViewMode Whenever a domain element is updated, the view
//	 * needs to be updated as well.
//	 * 
//	 * @param tableId     | The tableId of the table that should be shown.
//	 * @param tableName   | The table name of the table that should be shown.
//	 * @param table       | A map containing all the information of to show the
//	 *                    table.
//	 * @param columnTypes | A map containing a class for each column, to determine
//	 *                    if the value is null | What the column type should be.
//	 */
//	public void updateTablesViewMode(Map<UUID, String> map);

	/**
	 * Opens the tableDesignViewMode To open this mode, we need data to fetch the
	 * already existing view mode and if this does not exist, we pass data to create
	 * a new tableDesignViewMode
	 * 
	 * @param id                    | The tableId of the table that should be shown.
	 * @param tableName             | The table name of the table that should be
	 *                              shown.
	 * @param columnCharacteristics | A map containing all the information of to
	 *                              show the table.
	 */
	public void createTableDesignSubWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics);

	public void createTablesSubWindow(Map<UUID, String> data);
	
	
	
//	/**
//	 * Updates the tableDesignViewMode Whenever a domain element is updated, the
//	 * view needs to be updated as well.
//	 * 
//	 * @param tableId               | The tableId of the table that should be shown.
//	 * @param tableName             | The table name of the table that should be
//	 *                              shown.
//	 * @param columnCharacteristics | A map containing all the information of to
//	 *                              show the table.
//	 */
//	public void updateTableDesignViewMode(UUID id, String tableNameOfId,
//			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics);

	/**
	 * Pauses the application. Only one 'error' cell should be editable of a certain
	 * column with index
	 * 
	 * @param index | index of the cell of a column
	 * @param id    | columnId of the column
	 */
	public void pauseApplication(int index, UUID id);

	/**
	 * Resumes the application. To make sure we don't add the error twice as
	 * actionListeners to clicks and keys, give the cell data to the view.
	 * 
	 * @param index | index of the cell of a column
	 * @param id    | columnId of the column
	 */
	public void resume(int index, UUID columnId);

	/**
	 * Gets the Id of the currently opened TableViewMode. If the view currently is
	 * in TablesViewMode, this should not return an id.
	 * 
	 * @return UUID of the current table | getView.getCurrentTableId();
	 */
	public UUID getCurrentTableId();
//
//	/**
//	 * Sets a specific error in a cell in the DesignTable.
//	 * 
//	 * @param columnIndex | index of the cell of a column
//	 * @param columnId    | columnId of the column
//	 * @param newValue    | the new value of this cell
//	 */
//	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue);

	public void closeCurrentSubWindow();

}
