package ui.model.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import controller.observer.PropertyChangeSupport;

/**
 * The actual implementation of the uiFacadeInterface.
 * This handles all the actions defined in the uiFacadeInterface.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class UIFacade implements UIFacadeInterface, PropertyChangeListener {

	/**
	 * The variable storing the view.
	 */
	private View view;

	/**
	 * The PropertyChangeSupport where the listeners of this class are registered
	 * and where this class calls his propertyChange function to.
	 */
	private PropertyChangeSupport support;

	/**
	 * Initialises a new UIFacade.
	 */
	public UIFacade() {
		this.view = new View("Tablr");
		setSupport(new PropertyChangeSupport());
		this.getView().addPropertyChangeListener(this);
	}

	/**
	 * Shows the application window of the view.
	 */
	public void show() {
		this.getView().show();
	}

	/**
	 * Adds a propertyChangeListener to the PropertyChangeSupport.
	 * 
	 * @param pcl
	 *        | The propertyChangeListener.
	 * @effect Adds a propertyChangeListener to the PropertyChangeSupport.
	 *        | support.addPropertyChangeListener(pcl);
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		getSupport().addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a propertyChangeListener from the PropertyChangeSupport.
	 *
	 * @param pcl
	 *        | The propertyChangeListener.
	 * @effect Removes a propertyChangeListener from the PropertyChangeSupport.
	 *        | support.addPropertyChangeListener(pcl);
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		getSupport().removePropertyChangeListener(pcl);
	}

	/**
	 * Receives a propertyChangeEvent and fires a new event to its listeners.
	 * Overrides from the PropertyChangeListener interface.
	 * 
	 * @param evt
	 *       | the received event from View
	 *       
	 * @post fires the event to its listeners
	 *       | getSupport().firePropertyChange(evt); 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getSupport().firePropertyChange(evt);
	}

	/**
	 * Throws an error to a component.
	 * 
	 * @param id
	 *        | The id of component that needs to receive the error.
	 */
	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
		this.getView().throwErrorOnCurrentSubWindow(id, columnIndex, newValue);
	}

	/**
	 * Creates a new tableRows window with the given data. That subWindow is added to the list of subWindows.
	 * 
	 * @param tableId      The tableId of the table that should be shown.
	 * @param tableName    The table name of the table that should be shown.
	 * @param table        A map containing all the information of to show the
	 *                     table.
	 * @param columnTypes  A map containing a class for each column, to determine
	 *                     if the value is null | What the column type should be.
	 * @effect The view will create a tableRowsWindow with the given information.
	 *                     | this.getView().createTableRowsWindow(tableId, tableName, table, columnTypes)
	 */
	@Override
	public void createTableRowsSubWindow(UUID tableId, String tableName,
			Map<List<Object>, List<Object[]>> table, boolean isComputed) {
		this.getView().createTableRowsWindow(tableId, tableName, table, isComputed);
	}

	/**
	 * Updates all the tablesSubWindows with the given data.
	 * @param tablesListData The data needed to update a tablesSubWindow.
	 * @effect The view will update the tablesSubwindows. 
	 *         | this.getView().updateTablesSubWindows(tablesListData)
	 *          
	 */
	@Override
	public void updateTablesSubWindows(Map<UUID, List<String>> tablesListData) {
		this.getView().updateTablesSubWindows(tablesListData);
	}

	/**
	 * Updates all the tableRows and design subWindows associated with the given tableId.
	 * @param id            The id of the table.
	 * @param designData    The data used for the design.
	 * @param tableRowsData The data used in the rows.
	 * @param rowsClassData The rows class data.
	 * @effect The view will update the TableRowsAndDesignSubWindow.
	 *         | this.getView().updateTableRowsAndDesignSubWindows(id, designData, tableRowsData, rowsClassData)
	 */
	public void updateTableRowsAndDesignSubWindows(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> designData,
			Map<List<Object>, List<Object[]>> tableRowsData, boolean isComputedTable) {
		this.getView().updateTableRowsAndDesignSubWindows(id, tableName, designData, tableRowsData, isComputedTable);
	}

	/**
	 * Creates a new tableDesign window with the given data. That subWindow is added to the list of subWindows.
	 * 
	 * @param id                    The id of the table.
	 * @param tableName             The name of the table.
	 * @param columnCharacteristics The characteristics of the columns of the table.
	 * @effect The view will update the tableDesignSubWindow.
	 *         | this.getView().createTableDesignWindow(id, tableName, columnCharacteristics)
	 */
	@Override
	public void createTableDesignSubWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.getView().createTableDesignWindow(id, tableName, columnCharacteristics);
	}

	/**
	 * Creates a Table window with the given data. The subWindow is added to the list of subWindows.
	 * 
	 * @param data  The data containing all the information needed to create a TablesWindow. (The table UUID and tableName)
	 * @effect the view will create a TablesSubWindow.
	 *         | this.getView().createTablesWindow(data)
	 */
	@Override
	public void createTablesSubWindow(Map<UUID, List<String>> data) {
		this.getView().createTablesWindow(data);
	}

	/**
	 * Pauses the subWindow. Only one 'error' cell should be editable of a certain
	 * column with index in the specific subWindow.
	 * 
	 * @param index | index of the cell of a column
	 * @param id    | columnId of the column
	 * @effect The view will pause the specific subWindow.
	 *              | this.getView().pauseApplication(i, id);
	 */
	@Override
	public void pauseCurrentSubWindow(int i, UUID id) {
		this.getView().pauseCurrentSubWindow(i, id);
	}

	/**
	 * Resumes the subWindow. To make sure we don't add the error twice as
	 * actionListeners to clicks and keys, give the cell data to the view.
	 */
	@Override
	public void resume() {
		this.getView().resume();
	}

	/**
	 * Gets the Id of the currently selected subWindow.
	 * @return The UUID of the table.
	 */
	@Override
	public UUID getCurrentTableId() {
		return this.getView().getCurrentTableSubWindowTableId();
	}

	/**
	 * Gets the View.
	 * 
	 * @return view The view class.
	 */
	public View getView() {
		return this.view;
	}

	/**
	 * Gets the PropertyChangeSupport
	 * 
	 * @return support
	 */
	private PropertyChangeSupport getSupport() {
		return support;
	}

	/**
	 * Sets the PropertyChangeSupport
	 * 
	 * @param support
	 *        | the PropertyChangeSupport
	 * @throws IllegalArgumentException the support argument is null
	 *        | support == null
	 * @post The support given is set
	 *        | new.getSupport() = support
	 */
	private void setSupport(PropertyChangeSupport support) {
		if (support == null) {
			throw new IllegalArgumentException("Cannot set a null support.");
		}
		this.support = support;
	}

	/**
	 * Closes the currentSubWindow.
	 * @effect the view closes the currentSubWindow.
	 *         | this.getView().closeCurrentSubWindow()
	 */
	@Override
	public void closeCurrentSubWindow() {
		this.getView().closeCurrentSubWindow();
	}

	/**
	 * Closes all the SubWindows which contain information of the table with the given tableID.
	 * @param tableID The UUID of the table.
	 * @effect The view closes all the subWindows of containing information of the table with the given tableID
	 *         | this.getView().closeAllSubWindowsOfTable(tableID)
	 */
	public void closeAllSubWindowsOfTable(UUID tableID) {
		this.getView().closeAllSubWindowsOfTable(tableID);
	}

	/**
	 * Simulates a click to the view.
	 * @param x The x-coordinate of the click.
	 * @param y The y-coordinate of the click.
	 * @param clickCount The amount of clicks which will be simulated.
	 * @effect The click is further simulated in the view.
	 *         | getView().simulateClickClicked(x, y, clickCount)
	 */
	public void simulateClick(int x, int y, int clickCount) {
		getView().simulateClickClicked(x, y, clickCount);
	}

	/**
	 * Simulates a click release to the view.
	 * @param x The x-coordinate of the click.
	 * @param y The y-coordinate of the click.
	 * @effect The click is further simulated in the view.
	 *         | getView().simulateClickRelease(x, y)
	 */
	public void simulateClickRelease(int x, int y) {
		getView().simulateClickRelease(x, y);
	}

	/**
	 * Simulates a click drag to the view.
	 * @param x The x-coordinate of the click.
	 * @param y The y-coordinate of the click.
	 * @effect The click is further simulated in the view.
	 *         | getView().simulateClickDrag(x, y)
	 */
	public void simulateClickDrag(int x, int y) {
		getView().simulateClickDrag(x, y);
	}

	/**
	 * Simulates a key press with the given character.
	 * @param c The key (character) which will be simulated.
	 * @effect The key press is further simulated in the view.
	 *          | getView().simulateKeyPress(c)
	 */
	public void simulateKeyPress(char c) {
		getView().simulateKeyPress(c);
	}

	/**
	 * Simulates a keyCode with the given keyCode.
	 * @param keyCode The keyCode which will be simulated
	 * @effect The key press is further simulated in the view.
	 *          | getView().simulateKeyPress(keyCode)
	 */
	public void simulateKeyPress(int keyCode) {
		getView().simulateKeyPress(keyCode);
	}

	/**
	 * Creates a formSubwindow out of the given information.
	 * 
	 * @param tableId       The id of the table.
	 * @param tableNameOfId The name of the table.
	 * @param tableData     The data inside the table.
	 * @param isComputed     Whether or not the table is computed.
	 * @effect the view creates a from subWindow out of the given data.
	 *          | getView().createFormSubWindow(tableId, tableNameOfId, tableData, isComputed);
	 */
	@Override
	public void createFormSubWindow(UUID tableId, String tableNameOfId,
			Map<List<Object>, List<Object[]>> tableData, boolean isComputed) {
		getView().createFormSubWindow(tableId, tableNameOfId, tableData, isComputed);
	}

	/**
	 * Closes all the design window using the table with the given tableId.
	 * @param tableId The tableId of which all the design windows.
	 * @effect the view closes all the design window with the given id.
	 *         | getView().closeAllDesignWindows(tableId)
	 */
	@Override
	public void closeAllDesignWindows(UUID tableId) {
		getView().closeAllDesignWindows(tableId);		
	}

}
