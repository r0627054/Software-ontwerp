package ui.model.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import controller.observer.PropertyChangeSupport;

/**
 * The actual implementation of the uiFacadeInterface.
 * This handles all the actions defined in the uiFacadeInterface.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class UIFacade implements UIFacadeInterface, PropertyChangeListener {
	
	/**
	 * The view, which handles changing view modes and the CanvasWindow.
	 */
	private View view;
	
	/**
	 * The PropertyChangeSupport where the listeners of this class are registered
	 * and where this class calls his propertyChange function to.
	 */
	private PropertyChangeSupport support;

	/**
	 * Initialises a new UIFacade.
	 * This constructor is only called once.
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
	 * Startup method to initialise the first table overview.
	 * The map of keys (tableId's) and Strings (table names) will be
	 * shown to the user on startup. By default this should be empty,
	 * unless a table is loaded from memory or initialised in the domain.
	 * 
	 * @param map
	 * 		  The Map of tableIds and tableNames
	 */
//	@Override
//	public void startup(Map<UUID, String> map) {
//		getView().startup(map);
//	}

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

//	/**
//	 * Gets the ViewModeType of the current ViewMode from the view.
//	 * 
//	 * @return the type of the current viewmode of view.
//	 * 			| getView().getCurrentViewModeType();
//	 */
//	@Override
//	public ViewModeType getCurrentViewModeType() {
//		return this.getView().getCurrentViewModeType();
//	}

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
	 * Opens the tableRowsViewMode
	 * To open this mode, we need data to fetch the already existing view mode
	 * and if this does not exist, we pass data to create a new tableRowsViewMode
	 * 
	 * @param tableId
	 * 		   | The tableId of the table that should be shown.
	 * @param tableName
	 *         | The table name of the table that should be shown.
	 * @param table
	 *         | A map containing all the information of to show the table.
	 * @param columnTypes
	 * 		   | A map containing a class for each column, to determine if the value is null
	 *         | What the column type should be.
	 */
	@Override
	public void createTableRowsSubWindow(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		this.getView().createTableRowsWindow(tableId, tableName, table, columnTypes);
	}

	public void updateTablesSubWindows(Map<UUID, String> tablesListData) {
		this.getView().updateTablesSubWindows(tablesListData);
	}
	
	public void updateTableRowsAndDesignSubWindows(UUID id, Map<UUID, LinkedHashMap<String, Object>> designData,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableRowsData, Map<UUID, Class<?>> rowsClassData) {
		this.getView().updateTableRowsAndDesignSubWindows(id, designData, tableRowsData, rowsClassData);
	}

//	/**
//	 * Updates the tablesViewMode
//	 * Whenever a domain element is updated, the view needs to be updated as well.
//	 * 
//	 * @param tableId
//	 * 		   | The tableId of the table that should be shown.
//	 * @param tableName
//	 *         | The table name of the table that should be shown.
//	 * @param table
//	 *         | A map containing all the information of to show the table.
//	 * @param columnTypes
//	 * 		   | A map containing a class for each column, to determine if the value is null
//	 *         | What the column type should be.
//	 */
//	@Override
//	public void updateTablesViewMode(Map<UUID, String> map) {
//		this.getView().updateTablesViewMode(map);
//	}

	/**
	 * Opens the tableDesignViewMode
	 * To open this mode, we need data to fetch the already existing view mode
	 * and if this does not exist, we pass data to create a new tableDesignViewMode
	 * 
	 * @param id
	 * 		   | The tableId of the table that should be shown.
	 * @param tableName
	 *         | The table name of the table that should be shown.
	 * @param columnCharacteristics
	 *         | A map containing all the information of to show the table.
	 */
	@Override
	public void createTableDesignSubWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.getView().createTableDesignWindow(id, tableName, columnCharacteristics);
	}

	@Override
	public void createTablesSubWindow(Map<UUID, String> data) {
		this.getView().createTablesWindow(data);
	}
	
//	/**
//	 * Updates the tableDesignViewMode
//	 * Whenever a domain element is updated, the view needs to be updated as well.
//	 * 
//	 * @param tableId
//	 * 		   | The tableId of the table that should be shown.
//	 * @param tableName
//	 *         | The table name of the table that should be shown.
//	 * @param columnCharacteristics
//	 *         | A map containing all the information of to show the table.
//	 */
//	@Override
//	public void updateTableDesignViewMode(UUID id, String tableNameOfId,
//			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
//		this.getView().updateTableDesignViewMode(id, tableNameOfId, columnCharacteristics);
//	}

	/**
	 * Pauses the application.
	 * Only one 'error' cell should be editable of a certain column with index
	 * 
	 * @param index
	 *        | index of the cell of a column
	 * @param id
	 *        | columnId of the column
	 */
	@Override
	public void pauseApplication(int i, UUID id) {
		this.getView().pauseApplication(i, id);

	}

	/**
	 * Resumes the application.
	 * To make sure we don't add the error twice as actionListeners to 
	 * clicks and keys, give the cell data to the view.
	 * 
	 * @param index
	 *        | index of the cell of a column
	 * @param id
	 *        | columnId of the column
	 */
	@Override
	public void resume(int columnIndex, UUID columnId) {
		this.getView().resume(columnIndex, columnId);
	}

	/**
	 * Gets the Id of the currently opened TableViewMode.
	 * If the view currently is in TablesViewMode, this should not return an id.
	 * 
	 * @return UUID of the current table
	 *        | getView.getCurrentTableId();
	 */
	@Override
	public UUID getCurrentTableId() {
		return this.getView().getCurrentTableSubWindowTableId();
	}

//	@Override
//	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
//		this.getView().setErrorDesignTableCell(columnIndex, columnId, newValue);
//	}

//	/**
//	 * Sets a specific error in a cell in the DesignTable.
//	 * 
//	 * @param columnIndex
//	 *        | index of the cell of a column
//	 * @param columnId
//	 *        | columnId of the column
//	 * @param newValue
//	 *        | the new value of this cell
//	 */
//	public void emulateClickClicked(int x, int y, int clickCount) {
//		this.getView().emulateClickClicked(x, y, clickCount);
//	}
//
//	/**
//	 * Simulates a key press for testing purposes.
//	 * 
//	 * @param keyChar
//	 *        | The character pressed.
//	 */
//	public void emulateKeyPress(char keyChar) {
//		this.getView().emulateKeyPress(keyChar);
//	}
//
//	/**
//	 * Simulates a key press for testing purposes.
//	 * 
//	 * @param keyCode
//	 *        | The special code of the key pressed.
//	 */
//	public void emulateKeyPress(int keyCode) {
//		this.getView().emulateKeyPress(keyCode);
//	}
//
//	/**
//	 * Resets all the view modes and listeners for testing purposes. 
//	 */
//	public void resetViewModes() {
//		getSupport().removePropertyChangeListener(this.getView());
//		this.getView().resetViewModes();
//		this.getView().addPropertyChangeListener(this);
//	}

	/**
	 * Gets the View.
	 * 
	 * @return view
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
	 * @throws IllegalArgumentException
	 *         | the support argument is null
	 * @post The support given is set
	 *       | this.support = support
	 */
	private void setSupport(PropertyChangeSupport support) {
		if (support == null) {
			throw new IllegalArgumentException("Cannot set a null support.");
		}
		this.support = support;
	}


	@Override
	public void closeCurrentSubWindow() {
		this.getView().closeCurrentSubWindow();		
	}

}
