package ui.model.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.observer.PropertyChangeListener;

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

	/**
	 * Throws an error to a component.
	 * 
	 * @param id | The id of component that needs to receive the error.
	 */
	public void throwError(UUID id, int columnIndex, Object newValue);

	/**
	 * Creates a new tableRows window with the given data. That subWindow is added to the list of subWindows.
	 * 
	 * @param tableId      The tableId of the table that should be shown.
	 * @param tableName    The table name of the table that should be shown.
	 * @param table        A map containing all the information of to show the
	 *                     table.
	 * @param isComputedTable Is this table a computedTable 
	 * @param columnTypes  A map containing a class for each column, to determine
	 *                     if the value is null | What the column type should be.
	 */
	public void createTableRowsSubWindow(UUID tableId, String tableName,
			Map<List<Object>, List<Object[]>> table, boolean isComputedTable);

	/**
	 * Updates all the tablesSubWindows with the given data.
	 * @param map The data needed to update a tablesSubWindow.
	 */
	public void updateTablesSubWindows(Map<UUID, List<String>> map);

	/**
	 * Updates all the tableRows and design subWindows associated with the given tableId.
	 * @param id            The id of the table.
	 * @param designData    The data used for the design.
	 * @param tableRowsData The data used in the rows.
	 * @param isComputedTable Is this table a computed table
	 * @param rowsClassData The rows class data.
	 */
	public void updateTableRowsAndDesignSubWindows(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> designData, Map<List<Object>, List<Object[]>> tableRowsData,
			boolean isComputedTable);

	/**
	 * Creates a new tableDesign window with the given data. That subWindow is added to the list of subWindows.
	 * 
	 * @param id                    The id of the table.
	 * @param tableName             The name of the table.
	 * @param columnCharacteristics The characteristics of the columns of the table.
	 *
	 */
	public void createTableDesignSubWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics);

	/**
	 * Creates a Table window with the given data. The subWindow is added to the list of subWindows.
	 * 
	 * @param map  The data containing all the information needed to create a TablesWindow. (The table UUID and tableName)
	 */
	public void createTablesSubWindow(Map<UUID, List<String>> map);

	/**
	 * Pauses the subWindow. Only one 'error' cell should be editable of a certain
	 * column with index in the specific subWindow.
	 * 
	 * @param index | index of the cell of a column
	 * @param id    | columnId of the column
	 */
	public void pauseCurrentSubWindow(int index, UUID id);

	/**
	 * Resumes the subWindow. To make sure we don't add the error twice as
	 * actionListeners to clicks and keys, give the cell data to the view.
	 */
	public void resume();

	/**
	 * Gets the Id of the currently selected subWindow.
	 * @return The UUID of the table.
	 */
	public UUID getCurrentTableId();

	/**
	 * Closes the currentSubWindow.
	 */
	public void closeCurrentSubWindow();

	/**
	 * Closes all the SubWindows which contain information of the table with the given tableID.
	 * @param tableID The UUID of the table.
	 */
	public void closeAllSubWindowsOfTable(UUID tableID);

	public void createFormSubWindow(UUID tableId, String tableNameOfId,
			Map<List<Object>, List<Object[]>> tableData, boolean isComputed);

	public void closeAllDesignWindows(UUID tableId);

}
