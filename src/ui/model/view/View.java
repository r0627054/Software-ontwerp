package ui.model.view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import controller.observer.PropertyChangeSupport;
import ui.model.viewmodes.TableDesignWindow;
import ui.model.viewmodes.TableRowsWindow;
import ui.model.viewmodes.TableWindow;
import ui.model.viewmodes.TablesWindow;
import ui.model.viewmodes.SubWindow;
import ui.model.viewmodes.ViewModeType;
import ui.model.window.CanvasWindow;

/**
 * 
 * A view is a subclass of CanvasWindow.
 * This is a frame which contains all the different viewModes and contains
 * the logic to change these viewModes. 
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class View extends CanvasWindow implements PropertyChangeListener {

	/**
	 * The PropertyChangeSupport where the listeners of this class are registered
	 * and where this class calls his propertyChange function to.
	 */
	private PropertyChangeSupport support;

	/**
	 * Variable storing the current (view mode)
	 */
	private SubWindow currentMode;

	/**
	 * Variable map storing the DesignViewMode and TableRowsViewMode for a certain tableId.
	 */
	private Map<UUID, List<TableWindow>> viewModes = new HashMap<>();

	/**
	 * Variable holding the single instance of TablesViewMode
	 */
	private TablesWindow tablesViewMode;

	/**
	 * Variables to determine if the user pressed control
	 */
	private boolean ctrlPressed = false;
	
	/**
	 * Variables to determine if the user pressed entr
	 */
	private boolean entrPressed = false;

	/**
	 * Initialise this new view component with the given title.
	 * 
	 * @param title
	 *        The title of the view.
	 */
	public View(String title) {
		super(title);
		setSupport(new PropertyChangeSupport());
	}

	/**
	 * Startup method to initialise the tablesViewMode.
	 * The view starts listening to this window for events and
	 * the current viewMode is set to tablesViewMode.
	 * 
	 * @param map
	 * 		  The Map of tableIds and tableNames used to create the tablesViewMode
	 */
	public void startup(Map<UUID, String> map) {
		tablesViewMode = new TablesWindow(map);
		getTablesViewMode().addPropertyChangeListener(this);
		changeModeTo(null, ViewModeType.TABLESVIEWMODE);
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
	 * Paints the current mode.
	 */
	@Override
	public void paint(Graphics g) {
		this.currentMode.paint(g);
	}

	/**
	 * Returns the current mode of the view.
	 */
	public SubWindow getCurrentViewMode() {
		return currentMode;
	}

	/**
	 * Gets the ViewMode for a certain tableId and a certain ViewModeType.
	 * The table id should be empty if the TablesViewMode is requested, otherwise
	 * the table id is mandatory.
	 *  
	 * @throws IllegalArgumentException
	 *         | the tableId is null and viewModeType is null
	 * @param id
	 *         | The table id of the requested view mode
	 * @param viewModeType
	 *         | The viewModeType enum to indicate which view mode is requested
	 * @return ViewMode
	 *         | The requested ViewMode
	 */
	public SubWindow getViewMode(UUID id, ViewModeType viewModeType) {
		if (id == null && viewModeType.equals(ViewModeType.TABLESVIEWMODE)) {
			return tablesViewMode;
		} else if (id == null || viewModeType == null) {
			throw new IllegalArgumentException("Cannot get ViewMode for null string");
		} else {
			List<TableWindow> listOfViewModes = this.getAllTableViewModes().get(id);
			if (listOfViewModes != null) {
				for (SubWindow viewMode : this.getAllTableViewModes().get(id)) {
					if (viewModeType.equals(viewMode.getViewModeType())) {
						return viewMode;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Changes the current mode with the mode associated with the given key.
	 * 
	 * @param key
	 * @throws IllegalArgumentException
	 *         if the newCurrentMode is null
	 *         | viewModes.get(key) == null
	 * @post The current mode is switched with the mode associated with the given key.
	 *         | new.getCurrentMode == viewModes.get(key) 
	 */
	public void changeModeTo(UUID id, ViewModeType viewModeType) {
		SubWindow newCurrentMode = this.getViewMode(id, viewModeType);
		if (newCurrentMode == null) {
			throw new IllegalArgumentException("No mode found for key");
		} else
			this.setCurrentMode(newCurrentMode);
	}

	/**
	 * Gets the Tables overview.
	 * 
	 * @return TablesViewMode
	 *         | Only instance of TablesViewMode.
	 */
	public TablesWindow getTablesViewMode() {
		return this.tablesViewMode;
	}

	/**
	 * Adds a TableViewMode (TableRowViewMode or TableDesignViewMode) to the list of view modes.
	 * If the corresponding view mode of the table table id exists, the new view mode is added to the same list.
	 * Otherwise, a new value in the view modes map is created.
	 * 
	 * @param id
	 *       | tableId of the new TableViewMode
	 * @param viewMode
	 *       | the new TableViewMode
	 */
	public void addTableViewMode(UUID id, TableWindow viewMode) {
		if (this.getAllTableViewModes().containsKey(id)) {

			List<TableWindow> viewModesOfId = this.getAllTableViewModes().get(id);
			boolean containsViewModeOfType = false;
			for (TableWindow v : viewModesOfId) {
				if (v.getViewModeType().equals(viewMode.getViewModeType())) {
					containsViewModeOfType = true;
				}
			}
			if (!containsViewModeOfType) {
				viewModesOfId.add(viewMode);
				this.getAllTableViewModes().replace(id, viewModesOfId);
			}
		} else {
			List<TableWindow> list = new ArrayList<>();
			list.add(viewMode);
			this.viewModes.put(id, list);
		}
	}

	/**
	 * Gets the map of tableIds with their TableViewMode(s).
	 * 
	 * @return viewModes
	 *         | The Map containing a list of TableViewModes for a certain tableId.
	 */
	private Map<UUID, List<TableWindow>> getAllTableViewModes() {
		return viewModes;
	}

	/**
	 * Changes the current view mode to the given current view mode and repaints the window.
	 * 
	 * @param currentMode
	 *        | the new set view mode
	 * @post The currentMode is set
	 *        | this.currentMode = currentMode
	 * @post Window is repainted
	 *        | repaint()
	 */
	private void setCurrentMode(SubWindow currentMode) {
		this.currentMode = currentMode;
		this.repaint();
	}

	/**
	 * Gets the current ViewModeType
	 * 
	 * @return ViewModeType
	 *         | the current type of the current view mode.
	 */
	public ViewModeType getCurrentViewModeType() {
		return this.getCurrentViewMode().getViewModeType();
	}

	/**
	 * Gives the raised mouse event details to the current shown view mode.
	 * 
	 * @param id
	 *       | MouseEvent click id
	 * @param x
	 *       | x-coordinate clicked
	 * @param y
	 *       | y-coordinate clicked
	 * @param clickCount
	 *       | amount of times clicked in a short interval
	 */
	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		getCurrentViewMode().mouseClicked(id, x, y, clickCount);
	}

	/**
	 * Gives the raised key event details to the current shown view mode.
	 * @param id
	 *        | MouseEvent key id 
	 * @param keyCode
	 *        | MouseEvent Keycode (e.g.: KeyEvent.VK_ESCAPE)
	 * @param keyChar
	 *        | Character typed
	 */
	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id == KeyEvent.KEY_PRESSED) {
			if (this.getCurrentViewMode() instanceof TableRowsWindow) {
				TableRowsWindow currentViewMode = (TableRowsWindow) getCurrentViewMode();
				if (!currentViewMode.isPaused()) {
					checkEscapeKeyPress(id, keyCode);
					checkCtrlEnterKeyPress(id, keyCode);
				}
			} else if (this.getCurrentViewMode() instanceof TableDesignWindow) {
				TableDesignWindow currentViewMode = (TableDesignWindow) getCurrentViewMode();
				if (!currentViewMode.isPaused() && !currentViewMode.hasASelectedCell()) {
					checkEscapeKeyPress(id, keyCode);
					checkCtrlEnterKeyPress(id, keyCode);
				}
			}
			getCurrentViewMode().keyPressed(id, keyCode, keyChar);
		}
	}

	/**
	 * Check if the control and enter keys were pressed after each other.
	 * If not, reset the boolean values.
	 * 
	 * @param id
	 *       | KeyEvent key id
	 * @param keyCode
	 *       | Button pressed
	 */
	private void checkCtrlEnterKeyPress(int id, int keyCode) {
		ViewModeType tableDVM = ViewModeType.TABLEDESIGNVIEWMODE;
		ViewModeType tableRVM = ViewModeType.TABLEROWSVIEWMODE;

		if (keyCode == KeyEvent.VK_CONTROL) {
			this.setCtrlPressed(true);
		} else if (keyCode == KeyEvent.VK_ENTER) {
			this.setEntrPressed(true);
		} else {
			setCtrlPressed(false);
			setEntrPressed(false);
		}

		if (isCtrlPressed() && isEntrPressed()) {
			setCtrlPressed(false);
			setEntrPressed(false);

			SubWindow currentViewMode = this.getCurrentViewMode();

			if (tableDVM.equals(currentViewMode.getViewModeType())) {
				TableWindow currentTableViewMode = (TableWindow) currentViewMode;
				PropertyChangeEvent evt = new PropertyChangeEvent(currentTableViewMode.getId(),
						ChangeEventType.SWITCH_VIEWMODE, tableDVM, tableRVM);

				this.support.firePropertyChange(evt);

			} else if (tableRVM.equals(currentViewMode.getViewModeType())) {
				TableWindow currentTableViewMode = (TableWindow) currentViewMode;

				PropertyChangeEvent evt = new PropertyChangeEvent(currentTableViewMode.getId(),
						ChangeEventType.SWITCH_VIEWMODE, tableRVM, tableDVM);
				this.support.firePropertyChange(evt);
			}
		}
	}

	/**
	 * Check if the escape key was pressed.
	 * This should change view modes in the TableRowsViewMode and TableDesignViewMode.
	 * @param id
	 *       | KeyEvent key id
	 * @param keyCode
	 *       | Button pressed
	 */
	private void checkEscapeKeyPress(int id, int keyCode) {
		if (keyCode == KeyEvent.VK_ESCAPE) {
			ViewModeType currentType = this.getCurrentViewModeType();
			if (ViewModeType.TABLEDESIGNVIEWMODE.equals(currentType)
					|| ViewModeType.TABLEROWSVIEWMODE.equals(currentType)) {
				this.openTablesViewMode();
			}
		}
	}

	/**
	 * Receives a propertyChangeEvent and fires a new event to its listeners.
	 * Overrides from the PropertyChangeListener interface.
	 * 
	 * @param evt
	 *       | the received event from a ViewMode
	 *       
	 * @post fires the event to its listeners
	 *       | getSupport().firePropertyChange(evt); 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getSupport().firePropertyChange(evt);
		this.repaint();
	}

	/**
	 * Throws an error to a component on the current view mode.
	 * 
	 * @param id
	 *        | The id of component that needs to receive the error.
	 */
	public void throwErrorOnCurrentViewMode(UUID id) {
		getCurrentViewMode().throwError(id);
	}

	/**
	 * Opens the tables view mode
	 * Changes the current mode to the tables view mode
	 * 
	 * @post changes current mode to tablesViewMode
	 *       | this.setCurrentMode(this.getTablesViewMode())
	 */
	public void openTablesViewMode() {
		this.setCurrentMode(this.getTablesViewMode());
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
	public void openTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		if (tableId == null || table == null || tableName == null) {
			throw new IllegalArgumentException("Cannot open TableRowsViewMode with id/table/name equals null.");
		}

		TableRowsWindow tableRowsViewMode = (TableRowsWindow) this.getViewMode(tableId,
				ViewModeType.TABLEROWSVIEWMODE);

		if (tableRowsViewMode == null) {
			tableRowsViewMode = createTableRowsViewMode(tableId, tableName, table, columnTypes);
			this.addTableViewMode(tableId, tableRowsViewMode);
		} else {
			tableRowsViewMode.updateRowsTable(table, columnTypes);
		}

		this.setCurrentMode(tableRowsViewMode);
	}

	public TableRowsWindow createTableRowsViewMode(UUID id, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		if (id == null || table == null) {
			throw new IllegalArgumentException("Cannot create TableRowsViewMode with id or table equals null.");
		}
		TableRowsWindow newTableRowsViewMode = new TableRowsWindow(id, tableName, table, columnTypes);
		newTableRowsViewMode.addPropertyChangeListener(this);
		return newTableRowsViewMode;
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
	public void openTableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot open TableRowsViewMode with columnCharacteristics or id equals null.");
		}

		TableDesignWindow tableDesignViewMode = (TableDesignWindow) this.getViewMode(id,
				ViewModeType.TABLEDESIGNVIEWMODE);
		if (tableDesignViewMode == null) {
			tableDesignViewMode = createTableDesignViewMode(id, tableName, columnCharacteristics);
			this.addTableViewMode(id, tableDesignViewMode);
		} else {
			tableDesignViewMode.updateDesignTable(columnCharacteristics);
		}
		this.setCurrentMode(tableDesignViewMode);
	}

	private TableDesignWindow createTableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot create TableRowsViewMode with columnCharacteristics or id equals null.");
		}
		TableDesignWindow newTableDesignViewMode = new TableDesignWindow(id, tableName, columnCharacteristics);
		newTableDesignViewMode.addPropertyChangeListener(this);

		return newTableDesignViewMode;
	}

	/**
	 * Updates the tablesViewMode
	 * Whenever a domain element is updated, the view needs to be updated as well.
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
	public void updateTablesViewMode(Map<UUID, String> map) {
		getTablesViewMode().updateTables(map);
	}

	/**
	 * Updates the tableDesignViewMode
	 * Whenever a domain element is updated, the view needs to be updated as well.
	 * 
	 * @param tableId
	 * 		   | The tableId of the table that should be shown.
	 * @param tableName
	 *         | The table name of the table that should be shown.
	 * @param columnCharacteristics
	 *         | A map containing all the information of to show the table.
	 */
	public void updateTableDesignViewMode(UUID tableId, String tableNameOfId,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		TableDesignWindow tableDesignViewMode = (TableDesignWindow) getViewMode(tableId,
				ViewModeType.TABLEDESIGNVIEWMODE);
		tableDesignViewMode.updateDesignTable(columnCharacteristics);
	}

	/**
	 * Updates the tableRowsViewMode
	 * Whenever a domain element is updated, the view needs to be updated as well.
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
	public void updateTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		TableRowsWindow tableRowsViewMode = (TableRowsWindow) getViewMode(tableId, ViewModeType.TABLEROWSVIEWMODE);
		tableRowsViewMode.updateRowsTable(table, columnTypes);
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
	public void pauseApplication(int indexOfCell, UUID columnId) {
		SubWindow currentMode = getCurrentViewMode();
		if (currentMode instanceof TableWindow) {
			TableWindow currentViewMode = (TableWindow) currentMode;
			currentViewMode.pauseViewMode(indexOfCell, columnId);
		}
	}

	public UUID getCurrentTableViewModeTableId() {
		SubWindow current = getCurrentViewMode();

		if (current instanceof TableWindow) {
			TableWindow currentTableViewMode = (TableWindow) current;
			return currentTableViewMode.getId();
		}
		return null;
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
	public void resume(int columnIndex, UUID columnId) {
		SubWindow current = this.getCurrentViewMode();

		if (current instanceof TableWindow) {
			TableWindow currentTableViewMode = (TableWindow) current;
			currentTableViewMode.resumeViewMode(columnIndex, columnId);
		}
	}
	
	/**
	 * Sets a specific error in a cell in the DesignTable.
	 * 
	 * @param columnIndex
	 *        | index of the cell of a column
	 * @param columnId
	 *        | columnId of the column
	 * @param newValue
	 *        | the new value of this cell
	 */
	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
		SubWindow current = this.getCurrentViewMode();

		if (current instanceof TableDesignWindow) {
			TableDesignWindow currentDesignViewMode = (TableDesignWindow) current;
			currentDesignViewMode.setErrorDesignTableCell(columnIndex, columnId, newValue);
		}
	}

	private boolean isCtrlPressed() {
		return ctrlPressed;
	}

	private void setCtrlPressed(boolean ctrlPressed) {
		this.ctrlPressed = ctrlPressed;
	}

	private boolean isEntrPressed() {
		return entrPressed;
	}

	private void setEntrPressed(boolean entrPressed) {
		this.entrPressed = entrPressed;
	}

	public void emulateClickClicked(int x, int y, int clickCount) {
		this.handleMouseEvent(MouseEvent.MOUSE_CLICKED, x, y, clickCount);
	}

	public void emulateKeyPress(char keyChar) {
		this.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_UNDEFINED, keyChar);
	}

	public void emulateKeyPress(int keyCode) {
		this.handleKeyEvent(KeyEvent.KEY_PRESSED, keyCode, ' ');
	}

	public void resetViewModes() {
		support = new PropertyChangeSupport();
		this.setTablesViewMode(new TablesWindow(new HashMap<UUID, String>()));
		this.getTablesViewMode().addPropertyChangeListener(this);

		this.setCurrentMode(getTablesViewMode());
		this.changeModeTo(null, ViewModeType.TABLESVIEWMODE);
		this.setViewModesMap(new HashMap<UUID, List<TableWindow>>());
	}

	private void setViewModesMap(Map<UUID, List<TableWindow>> map) {
		if (map == null) {
			throw new IllegalArgumentException("Cannot set View ViewModes map to null.");
		}
		this.viewModes = map;
	}

	private void setTablesViewMode(TablesWindow newTablesViewMode) {
		if (newTablesViewMode == null) {
			throw new IllegalArgumentException("Cannot set a null TablesViewMode");
		}
		this.tablesViewMode = newTablesViewMode;
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

}
