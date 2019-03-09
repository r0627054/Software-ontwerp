package ui.model.view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui.model.viewmodes.TableRowsViewMode;
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
public class View extends CanvasWindow implements PropertyChangeListener{
	
	private PropertyChangeSupport support;
	
	/**
	 * Variable storing the current (view mode)
	 */
	private ViewMode currentMode;
	
	/**
	 * Variable storing all the different viewModes. With a String as key.
	 */
	//private Map<String, ViewMode> viewModes = new HashMap<String, ViewMode>();
	
	//UUID of the table can have multiple (currently max 2 viewmodes: TableRows and TableDesign) viewmode 
	private Map<UUID, List<ViewMode>> viewModes = new HashMap<UUID, List<ViewMode>>();
	private ViewMode tablesViewMode;
	
	
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
		tablesViewMode = new TablesViewMode("TablesViewMode", map);
		tablesViewMode.addPropertyChangeListener(this);
		//addViewMode(tablesViewMode);
		changeModeTo(null, ViewModeType.TABLESVIEWMODE);
		
		
		/*TableRowsViewMode tableRowsViewMode = new TableRowsViewMode("TableRowsViewMode");
		tableRowsViewMode.addPropertyChangeListener(this);
		addViewMode(tableRowsViewMode);*/
		//changeModeTo("TablesViewMode");
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

	public void repaintTest() {
		this.repaint();
	}
	
	/**
	 * Returns the current mode of the view.
	 */
	public ViewMode getCurrentViewMode() {
		return currentMode;
	}
	
	public ViewMode getViewMode(UUID id, ViewModeType viewModeType) {
		if(id == null && viewModeType.equals(ViewModeType.TABLESVIEWMODE)) {
			return tablesViewMode;
		}else if(id == null || viewModeType == null) {
			throw new IllegalArgumentException("Cannot get ViewMode for null string");
		} else {
			List<ViewMode> listOfViewModes = this.getAllViewModes().get(id);
			if(listOfViewModes != null) {
				for (ViewMode viewMode : this.getAllViewModes().get(id)) {
					if(viewModeType.equals(viewMode.getViewModeType())) {
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
	
	public void changeToTablesViewMode() {
		this.setCurrentMode(this.getTablesViewMode());
	}
	
	public ViewMode getTablesViewMode() {
		return this.tablesViewMode;
	}
	
	public void addViewMode(UUID id, ViewMode viewMode) {
		if(this.getAllViewModes().containsKey(id)) {
			
			List<ViewMode> viewModesOfId = this.getAllViewModes().get(id);
			boolean containsViewModeOfType =false;
			for (ViewMode v : viewModesOfId) {
				if(v.getViewModeType().equals(viewMode.getViewModeType())) {
					containsViewModeOfType = true;
				}
			}
			if(!containsViewModeOfType) {
				viewModesOfId.add(viewMode);
				this.getAllViewModes().replace(id, viewModesOfId);
			}
		}else {
			List<ViewMode> list = new ArrayList<>();
			list.add(viewMode);
			this.viewModes.put(id, list);
		}
	}
	

	private Map<UUID, List<ViewMode>> getAllViewModes() {
		return viewModes;
	}

	private void setCurrentMode(ViewMode currentMode) {
		this.currentMode = currentMode;
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
		if(keyCode == KeyEvent.VK_ESCAPE) {
			ViewModeType currentType = this.getCurrentViewModeType();
			if(ViewModeType.TABLEDESIGNVIEWMODE.equals(currentType) || ViewModeType.TABLEROWSVIEWMODE.equals(currentType))  {
				this.changeToTablesViewMode();
				this.repaint();
			}
		}
		currentMode.keyPressed(id, keyCode, keyChar);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.support.firePropertyChange(evt);
		this.repaint();
		
	}

	public void throwErrorOnCurrentViewMode(UUID id) {
		getCurrentViewMode().throwError(id);
	}
	
	public void openTableRowsViewMode(UUID tableId, String tableName, Map<Map<UUID, String>, Map<UUID, Object>> table) {
		ViewMode tableRowsViewMode = this.getViewMode(tableId, ViewModeType.TABLEROWSVIEWMODE);
		if(tableRowsViewMode == null) {
			//the tableRowsViewMode does not exist
			tableRowsViewMode = createTableRowsViewMode(tableName, table);
			this.addViewMode(tableId, tableRowsViewMode);
		}
		this.setCurrentMode(tableRowsViewMode);
	}
	
	public ViewMode createTableRowsViewMode(String tableName, Map<Map<UUID, String>, Map<UUID, Object>> table) {
		if(tableName == null || table == null) {
			throw new IllegalArgumentException("Cannot create TableRowsViewMode with tableName or table equals null.");
		}
		return new TableRowsViewMode(tableName, table);
	}

}
