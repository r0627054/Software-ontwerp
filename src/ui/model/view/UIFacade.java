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
 * @version 2.0
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
			Map<List<Object>, LinkedHashMap<UUID, Object>> table) {
		this.getView().createTableRowsWindow(tableId, tableName, table);
	}

	public void updateTablesSubWindows(Map<UUID, String> tablesListData) {
		this.getView().updateTablesSubWindows(tablesListData);
	}
	
	public void updateTableRowsAndDesignSubWindows(UUID id, String tableName, Map<UUID, LinkedHashMap<String, Object>> designData,
			Map<List<Object>, LinkedHashMap<UUID, Object>> tableRowsData) {
		this.getView().updateTableRowsAndDesignSubWindows(id, tableName, designData, tableRowsData);
	}

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
	public void pauseCurrentSubWindow(int i, UUID id) {
		this.getView().pauseCurrentSubWindow(i, id);

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
	public void resume() {
		this.getView().resume();
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
