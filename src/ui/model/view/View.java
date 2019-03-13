package ui.model.view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import ui.model.viewmodes.TableDesignViewMode;
import ui.model.viewmodes.TableRowsViewMode;
import ui.model.viewmodes.TableViewMode;
import ui.model.viewmodes.TablesViewMode;
import ui.model.viewmodes.ViewMode;
import ui.model.viewmodes.ViewModeType;
import ui.model.window.CanvasWindow;

/**
 * 
 * A view is a subclass of CanvasWindow.
 * This is a frame which contains all the different viewModes. 
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public class View extends CanvasWindow implements PropertyChangeListener {

	private PropertyChangeSupport support;

	/**
	 * Variable storing the current (view mode)
	 */
	private ViewMode currentMode;

	/**
	 * Variable storing all the different viewModes. With a String as key.
	 */

	// UUID of the table can have multiple (currently max 2 viewmodes: TableRows and
	// TableDesign) viewmode
	private Map<UUID, List<TableViewMode>> viewModes = new HashMap<>();
	private TablesViewMode tablesViewMode;

	private boolean ctrlPressed = false;
	private boolean entrPressed = false;

	/**
	 * Initialise this new view component with the given title.
	 * 
	 * @param title
	 *        The title of the view.
	 */
	public View(String title) {
		super(title);
		support = new PropertyChangeSupport(this);
	}

	public void startup(Map<UUID, String> map) {
		tablesViewMode = new TablesViewMode(map);
		tablesViewMode.addPropertyChangeListener(this);
		// addViewMode(tablesViewMode);
		changeModeTo(null, ViewModeType.TABLESVIEWMODE);

		/*
		 * TableRowsViewMode tableRowsViewMode = new
		 * TableRowsViewMode("TableRowsViewMode");
		 * tableRowsViewMode.addPropertyChangeListener(this);
		 * addViewMode(tableRowsViewMode);
		 */
		// changeModeTo("TablesViewMode");
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
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
	public ViewMode getCurrentViewMode() {
		return currentMode;
	}

	public ViewMode getViewMode(UUID id, ViewModeType viewModeType) {
		if (id == null && viewModeType.equals(ViewModeType.TABLESVIEWMODE)) {
			return tablesViewMode;
		} else if (id == null || viewModeType == null) {
			throw new IllegalArgumentException("Cannot get ViewMode for null string");
		} else {
			List<TableViewMode> listOfViewModes = this.getAllTableViewModes().get(id);
			if (listOfViewModes != null) {
				for (ViewMode viewMode : this.getAllTableViewModes().get(id)) {
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
		ViewMode newCurrentMode = this.getViewMode(id, viewModeType);
		if (newCurrentMode == null) {
			throw new IllegalArgumentException("No mode found for key");
		} else
			this.setCurrentMode(newCurrentMode);
	}

	public TablesViewMode getTablesViewMode() {
		return this.tablesViewMode;
	}

	public void addTableViewMode(UUID id, TableViewMode viewMode) {
		if (this.getAllTableViewModes().containsKey(id)) {

			List<TableViewMode> viewModesOfId = this.getAllTableViewModes().get(id);
			boolean containsViewModeOfType = false;
			for (TableViewMode v : viewModesOfId) {
				if (v.getViewModeType().equals(viewMode.getViewModeType())) {
					containsViewModeOfType = true;
				}
			}
			if (!containsViewModeOfType) {
				viewModesOfId.add(viewMode);
				this.getAllTableViewModes().replace(id, viewModesOfId);
			}
		} else {
			List<TableViewMode> list = new ArrayList<>();
			list.add(viewMode);
			this.viewModes.put(id, list);
		}
	}

	private Map<UUID, List<TableViewMode>> getAllTableViewModes() {
		return viewModes;
	}

	private void setCurrentMode(ViewMode currentMode) {
		this.currentMode = currentMode;
		this.repaint();
	}

	public ViewModeType getCurrentViewModeType() {
		return this.getCurrentViewMode().getViewModeType();
	}

	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		getCurrentViewMode().mouseClicked(id, x, y, clickCount);
	}

	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id == KeyEvent.KEY_PRESSED) {
			if (!(this.getCurrentViewMode() instanceof TableDesignViewMode)) {
				checkEscapeKeyPress(id, keyCode);
				checkCtrlEnterKeyPress(id, keyCode);
			} else {
				TableDesignViewMode currentViewMode = (TableDesignViewMode) getCurrentViewMode();
				if (!currentViewMode.isPaused() && !currentViewMode.hasASelectedCell()) {
					checkEscapeKeyPress(id, keyCode);
					checkCtrlEnterKeyPress(id, keyCode);
				}
			}
			getCurrentViewMode().keyPressed(id, keyCode, keyChar);
		}
	}

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

			ViewMode currentViewMode = this.getCurrentViewMode();

			if (tableDVM.equals(currentViewMode.getViewModeType())) {
				TableViewMode currentTableViewMode = (TableViewMode) currentViewMode;
				PropertyChangeEvent evt = new PropertyChangeEvent(currentTableViewMode.getId(),
						ChangeEventType.SWITCH_VIEWMODE.getEventString(), tableDVM, tableRVM);

				this.support.firePropertyChange(evt);

			} else if (tableRVM.equals(currentViewMode.getViewModeType())) {
				TableViewMode currentTableViewMode = (TableViewMode) currentViewMode;

				PropertyChangeEvent evt = new PropertyChangeEvent(currentTableViewMode.getId(),
						ChangeEventType.SWITCH_VIEWMODE.getEventString(), tableRVM, tableDVM);
				this.support.firePropertyChange(evt);
			}
		}
	}

	private void checkEscapeKeyPress(int id, int keyCode) {
		if (keyCode == KeyEvent.VK_ESCAPE) {
			ViewModeType currentType = this.getCurrentViewModeType();
			if (ViewModeType.TABLEDESIGNVIEWMODE.equals(currentType)
					|| ViewModeType.TABLEROWSVIEWMODE.equals(currentType)) {
				this.openTablesViewMode();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.support.firePropertyChange(evt);
		this.repaint();
	}

	public void throwErrorOnCurrentViewMode(UUID id) {
		getCurrentViewMode().throwError(id);
	}

	public void openTablesViewMode() {
		this.setCurrentMode(this.getTablesViewMode());
	}

	public void openTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		if (tableId == null || table == null || tableName == null) {
			throw new IllegalArgumentException("Cannot open TableRowsViewMode with id/table/name equals null.");
		}

		TableRowsViewMode tableRowsViewMode = (TableRowsViewMode) this.getViewMode(tableId,
				ViewModeType.TABLEROWSVIEWMODE);

		if (tableRowsViewMode == null) {
			tableRowsViewMode = createTableRowsViewMode(tableId, tableName, table, columnTypes);
			this.addTableViewMode(tableId, tableRowsViewMode);
		} else {
			tableRowsViewMode.updateRowsTable(table, columnTypes);
		}

		this.setCurrentMode(tableRowsViewMode);
	}

	public TableRowsViewMode createTableRowsViewMode(UUID id, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		if (id == null || table == null) {
			throw new IllegalArgumentException("Cannot create TableRowsViewMode with id or table equals null.");
		}
		TableRowsViewMode newTableRowsViewMode = new TableRowsViewMode(id, tableName, table, columnTypes);
		newTableRowsViewMode.addPropertyChangeListener(this);
		return newTableRowsViewMode;
	}

	public void openTableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot open TableRowsViewMode with columnCharacteristics or id equals null.");
		}

		TableDesignViewMode tableDesignViewMode = (TableDesignViewMode) this.getViewMode(id,
				ViewModeType.TABLEDESIGNVIEWMODE);
		if (tableDesignViewMode == null) {
			tableDesignViewMode = createTableDesignViewMode(id, tableName, columnCharacteristics);
			this.addTableViewMode(id, tableDesignViewMode);
		} else {
			tableDesignViewMode.updateDesignTable(columnCharacteristics);
		}
		this.setCurrentMode(tableDesignViewMode);
	}

	private TableDesignViewMode createTableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot create TableRowsViewMode with columnCharacteristics or id equals null.");
		}
		TableDesignViewMode newTableDesignViewMode = new TableDesignViewMode(id, tableName, columnCharacteristics);
		newTableDesignViewMode.addPropertyChangeListener(this);

		return newTableDesignViewMode;
	}

	public void updateTablesViewMode(Map<UUID, String> map) {
		getTablesViewMode().updateTables(map);
	}

	public void updateTableDesignViewMode(UUID tableId, String tableNameOfId,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		TableDesignViewMode tableDesignViewMode = (TableDesignViewMode) getViewMode(tableId,
				ViewModeType.TABLEDESIGNVIEWMODE);
		tableDesignViewMode.updateDesignTable(columnCharacteristics);
	}

	public void updateTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		TableRowsViewMode tableRowsViewMode = (TableRowsViewMode) getViewMode(tableId, ViewModeType.TABLEROWSVIEWMODE);
		tableRowsViewMode.updateRowsTable(table, columnTypes);
	}

	public void pauseApplication(int indexOfCell, UUID columnId) {
		ViewMode currentMode = getCurrentViewMode();
		if (currentMode instanceof TableViewMode) {
			TableViewMode currentViewMode = (TableViewMode) currentMode;
			currentViewMode.pauseViewMode(indexOfCell, columnId);
		}
	}

	public UUID getCurrentTableViewModeTableId() {
		ViewMode current = getCurrentViewMode();

		if (current instanceof TableViewMode) {
			TableViewMode currentTableViewMode = (TableViewMode) current;
			return currentTableViewMode.getId();
		}
		return null;
	}

	public void unpause(int columnIndex, UUID columnId) {
		ViewMode current = this.getCurrentViewMode();

		if (current instanceof TableViewMode) {
			TableViewMode currentTableViewMode = (TableViewMode) current;
			currentTableViewMode.unpauseViewMode(columnIndex, columnId);
		}
	}

	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
		ViewMode current = this.getCurrentViewMode();

		if (current instanceof TableDesignViewMode) {
			TableDesignViewMode currentDesignViewMode = (TableDesignViewMode) current;
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
}
