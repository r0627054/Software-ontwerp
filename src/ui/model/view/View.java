package ui.model.view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
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

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
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
		currentMode.mouseClicked(id, x, y, clickCount);
	}

	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		checkEscapeKeyPress(id, keyCode);
		checkCtrlEnterKeyPress(id, keyCode);
		currentMode.keyPressed(id, keyCode, keyChar);
	}

	private void checkCtrlEnterKeyPress(int id, int keyCode) {

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

			if (ViewModeType.TABLEDESIGNVIEWMODE.equals(currentViewMode.getViewModeType())) {
				TableViewMode currentTableViewMode = (TableViewMode) currentViewMode;
				PropertyChangeEvent evt = new PropertyChangeEvent(currentTableViewMode.getId(),
						ChangeEventType.SWITCH_VIEWMODE.getEventString(), ViewModeType.TABLEDESIGNVIEWMODE,
						ViewModeType.TABLEROWSVIEWMODE);

				this.support.firePropertyChange(evt);

			} else if (ViewModeType.TABLEROWSVIEWMODE.equals(currentViewMode.getViewModeType())) {
				TableViewMode currentTableViewMode = (TableViewMode) currentViewMode;

				PropertyChangeEvent evt = new PropertyChangeEvent(currentTableViewMode.getId(),
						ChangeEventType.SWITCH_VIEWMODE.getEventString(), ViewModeType.TABLEROWSVIEWMODE,
						ViewModeType.TABLEDESIGNVIEWMODE);
				this.support.firePropertyChange(evt);
			}
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

	private void checkEscapeKeyPress(int id, int keyCode) {
		if (id == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_ESCAPE) {
			// if escape pressed the application will go the tablesViewMode if it currently
			// at tableDesign or tableRowsViewMode
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

	public void openTableRowsViewMode(UUID tableId, String tableName, Map<Map<UUID, String>, Map<UUID, Object>> table) {
		if (tableId == null || table == null) {
			throw new IllegalArgumentException("Cannot open TableRowsViewMode with id or table equals null.");
		}
		
		TableViewMode tableRowsViewMode = (TableViewMode) this.getViewMode(tableId, ViewModeType.TABLEROWSVIEWMODE);
		
		if (tableRowsViewMode == null) {
			tableRowsViewMode = createTableRowsViewMode(tableId, tableName, table);
			this.addTableViewMode(tableId, tableRowsViewMode);
		}
		
		this.setCurrentMode(tableRowsViewMode);
	}

	public TableViewMode createTableRowsViewMode(UUID id, String tableName, Map<Map<UUID, String>, Map<UUID, Object>> table) {
		if (id == null || table == null) {
			throw new IllegalArgumentException("Cannot create TableRowsViewMode with id or table equals null.");
		}
		TableViewMode newTableRowsViewMode = new TableRowsViewMode(id, tableName, table);
		newTableRowsViewMode.addPropertyChangeListener(this);
		return newTableRowsViewMode;
	}

	public void openTableDesignViewMode(UUID id, String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot open TableRowsViewMode with columnCharacteristics or id equals null.");
		}

		TableViewMode tableDesignViewMode = (TableViewMode) this.getViewMode(id, ViewModeType.TABLEDESIGNVIEWMODE);
		if (tableDesignViewMode == null) {
			tableDesignViewMode = createTableDesignViewMode(id, tableName, columnCharacteristics);
			this.addTableViewMode(id, tableDesignViewMode);
		}
		this.setCurrentMode(tableDesignViewMode);
	}

	private TableViewMode createTableDesignViewMode(UUID id, String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot create TableRowsViewMode with columnCharacteristics or id equals null.");
		}
		TableViewMode newTableDesignViewMode = new TableDesignViewMode(id, tableName, columnCharacteristics);
		newTableDesignViewMode.addPropertyChangeListener(this);
		
		return newTableDesignViewMode;
	}

	public void updateTablesViewMode(Map<UUID, String> map) {
		getTablesViewMode().updateTables(map);
	}

	public void updateTableDesignViewMode(UUID id, String tableNameOfId,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		TableDesignViewMode tableDesignViewMode = (TableDesignViewMode) getViewMode(id, ViewModeType.TABLEDESIGNVIEWMODE);
		tableDesignViewMode.updateDesignTable(columnCharacteristics);
	}
}
