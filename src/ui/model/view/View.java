package ui.model.view;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import controller.observer.PropertyChangeSupport;
import ui.model.window.CanvasWindow;
import ui.model.window.sub.FormWindow;
import ui.model.window.sub.SubWindow;
import ui.model.window.sub.TableDesignWindow;
import ui.model.window.sub.TableRowsWindow;
import ui.model.window.sub.TablesWindow;

/**
 * 
 * A view is a subclass of CanvasWindow. This is a frame which contains all the
 * different subWindows and gives the information to the correct subWindows.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class View extends CanvasWindow implements PropertyChangeListener {

	/**
	 * The PropertyChangeSupport where the listeners of this class are registered
	 * and where this class calls his propertyChange function to.
	 */
	private PropertyChangeSupport support;

	/**
	 * Variable map storing all the subWindows.
	 */
	private List<SubWindow> subWindows = new ArrayList<>();

	/**
	 * Variables to determine if the user pressed control.
	 */
	private boolean ctrlPressed = false;

	/**
	 * Initialise this new view component with the given title.
	 *  The propertyChangesupport variable is set.
	 * 
	 * @param title The title of the CanvasWindow.
	 * @effectT The title of the CanvasWindow is set and the propertyChangeSupport variable is set.
	 *         | super(title)
	 *         | setSupport(new PropertyChangeSupport());
	 */
	public View(String title) {
		super(title);
		setSupport(new PropertyChangeSupport());
	}

	/**
	 * Adds a propertyChangeListener to the PropertyChangeSupport.
	 * 
	 * @param pcl  The propertyChangeListener.
	 * @effect Adds a propertyChangeListener to the PropertyChangeSupport. 
	 *        |  getSupport().addPropertyChangeListener(pcl);
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		getSupport().addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a propertyChangeListener from the PropertyChangeSupport.
	 *
	 * @param pcl  The propertyChangeListener.
	 * @effect Removes a propertyChangeListener from the PropertyChangeSupport.
	 *       |  getSupport().removePropertyChangeListener(pcl);
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		getSupport().removePropertyChangeListener(pcl);
	}

	/**
	 * Paints all the different subWindows.
	 * @post The different SubWindows are painted.
	 *       | for (SubWindow sw : getSubWindows())  
	 *       |	sw.paint(g, sw.equals(getCurrentSubWindow()));
	 */
	@Override
	public void paint(Graphics g) {
		for (SubWindow sw : getSubWindows()) {
			sw.paint(g, sw.equals(getCurrentSubWindow()));
		}
	}

	/**
	 * Returns the current subWindow.
	 *  The current subWindow is returned. If no subWindow is available null is returned.
	 *  @return Null when there is no subWindow in the list of subWindows otherwise it returns the last subWindow in the list.
	 *         | getSubWindows() != null && !getSubWindows().isEmpty() ? getSubWindows().get(getSubWindows().size() - 1)	: null
	 */
	public SubWindow getCurrentSubWindow() {
		return getSubWindows() != null && !getSubWindows().isEmpty() ? getSubWindows().get(getSubWindows().size() - 1)
				: null;
	}

	/**
	 * Returns the subWindows with the given Id.
	 * @param id The id of the subWindow.
	 * @return The list containing the subWindows with the given id.
	 *         | for (SubWindow subWindow : this.getSubWindows()) 
	 *         |	if ((subWindow.getId() != null && subWindow.getId().equals(id))
	 *		   |	|| (id == null && subWindow.getId() == null)) 
	 *		   |    	result.add(subWindow);
	 */
	public List<SubWindow> getSubWindows(UUID id) {
		List<SubWindow> result = new ArrayList<>();
		for (SubWindow subWindow : this.getSubWindows()) {
			if ((subWindow.getId() != null && subWindow.getId().equals(id))
					|| (id == null && subWindow.getId() == null)) {
				result.add(subWindow);
			}
		}
		return result;
	}

	/**
	 * Adds a subWindow to the list of subWindows.
	 * 
	 * @param subWindow The subWindow which will be added to the list of subWindows.
	 * @throws IllegalArgumentException The subWindow which has to be added equals null.
	 *                                  | subWindow == null
	 * @effect The subWindow is added to the list of subWindows.
	 *         | this.getSubWindows().add(subWindow);
	 */
	public void addSubwindow(SubWindow subWindow) {
		if (subWindow == null) {
			throw new IllegalArgumentException("Subwindow cannot be null");
		}
		this.getSubWindows().add(subWindow);
	}

	/**
	 * Returns the list of subWindows
	 * @return The list of subWindows.
	 *         | return subWindows.
	 */
	private List<SubWindow> getSubWindows() {
		return subWindows;
	}

	/**
	 * Sets the currentSubwindow. The window, which is already in the list, will be the last in the list.
	 * The last subWindow in the list is always, the current subWindow. And everything is repainted.
	 * @param currentWindow The subWindow, which is in the list, which will be the last one in the list.
	 * @throws IllegalArgumentException when the currentWindow equals null or when the currentWindow is not in the list of windows.
	 *         | currentWindow == null || !this.getSubWindows().contains(currentWindow)
	 * @post The subWindow is set to be the last one in the list.
	 *         | this.removeSubWindow(currentWindow);
	 *         | this.getSubWindows().add(currentWindow);
	 *         | this.repaint();
	 */
	private void setCurrentSubWindow(SubWindow currentWindow) {
		if (currentWindow == null || !this.getSubWindows().contains(currentWindow)) {
			throw new IllegalArgumentException(
					"Current window cannot be null nor can be set if it is not in the list.");
		}
		this.removeSubWindow(currentWindow);
		this.getSubWindows().add(currentWindow);
		this.repaint();
	}

	/**
	 * If there are subWindows in the list, the last window is removed and the canvas is repainted.
	 * @post The last subWindow is removed and the canvas is repainted.
	 *       | if(this.getCurrentSubWindow() != null)
	 *       |  	this.removeSubWindow(this.getCurrentSubWindow());
	 *       |		this.repaint();
	 * 
	 */
	public void closeCurrentSubWindow() {
		if (this.getCurrentSubWindow() != null) {
			this.removeSubWindow(this.getCurrentSubWindow());
			this.repaint();
		}
	}

	/**
	 * Closes all the SubWindows which contain information of the table with the given tableID.
	 * @param tableID The UUID of the table.
	 * @throws IllegalArgumentException When the tableID equals null.
	 *                                 | tableID == null
	 * @effect All the subWindows are removed from the list of subWindows.
	 *         | for (SubWindow subWindow : copySubWindows) {
	 *         |	if(subWindow!= null && subWindow.getId() != null && subWindow.getId() == tableID) {
	 *         |    	this.removeSubWindow(subWindow);
	 * @effect The view is repainted.
	 *         | this.repaint();   
	 */
	public void closeAllSubWindowsOfTable(UUID tableID) {
		if (tableID == null) {
			throw new IllegalArgumentException("TableID cannot equal null for subWindow deletion");
		}
		List<SubWindow> copySubWindows = new ArrayList<>(this.getSubWindows());
		for (SubWindow subWindow : copySubWindows) {
			if (subWindow != null && subWindow.getId() != null && subWindow.getId() == tableID) {
				this.removeSubWindow(subWindow);
			}
		}
		this.repaint();
	}

	/**
	 * Removes the subWindow out of the list of subWindows.
	 * @param subWindow The subWindow which will be removed.
	 * @effect The subWindow is removed out of the list of subWindows.
	 *         | this.getSubWindows().remove(subWindow);
	 */
	public void removeSubWindow(SubWindow subWindow) {
		this.getSubWindows().remove(subWindow);
	}

	/**
	 * When the mouse is clicked in another subWindow, that window is set as currentSubwindow and the click is given to it.
	 * 
	 * @param id         | MouseEvent click id
	 * @param x          | x-coordinate clicked
	 * @param y          | y-coordinate clicked
	 * @param clickCount | amount of times clicked in a short interval
	 */
	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {

		boolean isFound = false;

		for (int i = this.getNbrOfSubWindows() - 1; i >= 0 && !isFound; i--) {
			SubWindow subWindow = this.getSubWindows().get(i);

			if (subWindow != null) {

				if (subWindow.isWithinComponent(x, y, 0)) {
					if (!subWindow.equals(getCurrentSubWindow()) && id == MouseEvent.MOUSE_PRESSED) {
						this.setCurrentSubWindow(subWindow);
					}
					isFound = true;
				}
				subWindow.mouseClicked(id, x, y, clickCount);
			}
		}
	}

	/**
	 * Gives the raised key event details to the current subWindow.
	 *  Checks whether the ctrl-enter or ctrl-t is pressed. The key press is given to the current subwindow.
	 *   
	 * @param id      | MouseEvent key id
	 * @param keyCode | MouseEvent Keycode
	 * @param keyChar | Character typed
	 */
	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id == KeyEvent.KEY_PRESSED) {
			if (this.getCurrentSubWindow() != null) {
				this.getCurrentSubWindow().keyPressed(id, keyCode, keyChar);
			}
			if (keyCode == KeyEvent.VK_CONTROL) {
				this.setCtrlPressed(true);
			} else if (keyCode == 84 && this.isCtrlPressed()) {
				this.propertyChange(new PropertyChangeEvent(null, ChangeEventType.CREATE_TABLESSUBWINDOW, null, null));
				this.setCtrlPressed(false);
			} else {
				setCtrlPressed(false);
			}
		}
	}

	/**
	 * Receives a propertyChangeEvent and fires a new event to its listeners.
	 * Overrides from the PropertyChangeListener interface.
	 * 
	 * @param evt The received event from a subWindow.
	 * 
	 * @post fires the event to its listeners and repaints the canvas.
	 *       | getSupport().firePropertyChange(evt)
	 *       | this.repaint()
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getSupport().firePropertyChange(evt);
		this.repaint();
	}

	/**
	 * Throws an error to a component in the current subWindow.
	 * @param id          The id of component that needs to receive the error.
	 * @param columnIndex The index of the column where the error should be thrown.
	 * @param newValue    The newValue which need to be set in the UI.
	 * 
	 * @effect The error is thrown on the current subWindow.
	 *         | getCurrentSubWindow().throwError(id, columnIndex, newValue)
	 */
	public void throwErrorOnCurrentSubWindow(UUID id, int columnIndex, Object newValue) {
		getCurrentSubWindow().throwError(id, columnIndex, newValue);
	}

	/**
	 * Creates a new tableRows window with the given data. That subWindow is added to the list of subWindows.
	 * 
	 * @param tableId      The tableId of the table that should be shown.
	 * @param tableName    The table name of the table that should be shown.
	 * @param table        A map containing all the information of to show the
	 *                     table.
	 * @param isComputed 
	 * @param columnTypes  A map containing a class for each column, to determine
	 *                     if the value is null | What the column type should be.
	 * @throws IllegalArgumentException when the id equals null or when the table equals null.
	 *                     | id == null || table == null
	 * @effect The tableRowsWindow is created and is added to the list of subWindows.
	 *         | this.addCreatedTable(new TableRowsWindow(id, tableName, table, columnTypes));
	 */
	public void createTableRowsWindow(UUID id, String tableName, Map<List<Object>, List<Object[]>> table,
			boolean isComputed) {
		if (id == null || table == null) {
			throw new IllegalArgumentException("Cannot create TableRowsWindow with id or table equals null.");
		}
		this.addCreatedTable(new TableRowsWindow(id, tableName, table, isComputed));
	}

	/**
	 * Creates a new tableDesign window with the given data. That subWindow is added to the list of subWindows.
	 * 
	 * @param id                    The id of the table.
	 * @param tableName             The name of the table.
	 * @param columnCharacteristics The characteristics of the columns of the table.
	 * @throws IllegalArgumentException When the id equals null or when the tableName equals null or is empty or when the columnCharacteristics equal null.
	 *                              | id == null || tableName == null || tableName.isEmpty() || columnCharacteristics == null
	 * @effect The tableDesignWindow is created and is added to the list of subWindows.
	 *         | this.addCreatedTable(new TableDesignWindow(id, tableName, columnCharacteristics));
	 */
	public void createTableDesignWindow(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		if (id == null || tableName == null || tableName.isEmpty() || columnCharacteristics == null) {
			throw new IllegalArgumentException(
					"Cannot create TableRowsWindow with columnCharacteristics, id or name equals null/empty.");
		}
		this.addCreatedTable(new TableDesignWindow(id, tableName, columnCharacteristics));
	}

	/**
	 * Creates a Table window with the given data. The subWindow is added to the list of subWindows.
	 * 
	 * @param data  The data containing all the information needed to create a TablesWindow. (The table UUID and tableName)
	 * @effect  The tablesWindow is created and is added to the list of subWindows.
	 *           | this.addCreatedTable(new TablesWindow(data));
	 * @throws IllegalArgumentException when the data equals null.
	 *           | data == null
	 */
	public void createTablesWindow(Map<UUID, List<String>> data) {
		if (data == null) {
			throw new IllegalArgumentException("Cannot create a tables window when the data equals null");
		}
		this.addCreatedTable(new TablesWindow(data));
	}

	/**
	 * Adds this class to the propertyChangeListeners of the subWindow and sets the subWindow as currentWindow.
	 * @param subWindow The subWindow which needs to be added.
	 * @effect This class is added to the propertyChange listeners of the subWindow. 
	 *         The subWindow is added to the list of subWindows.
	 *         The window is set as currentSubWindow.
	 *         | subWindow.addPropertyChangeListener(this);
	 *         | this.addSubwindow(subWindow);
	 *         | this.setCurrentSubWindow(subWindow);
	 */
	private void addCreatedTable(SubWindow subWindow) {
		if (subWindow == null) {
			throw new IllegalArgumentException("Cannot add an subWindow which is null");
		}
		subWindow.addPropertyChangeListener(this);
		this.addSubwindow(subWindow);
		this.setCurrentSubWindow(subWindow);
	}

	/**
	 * Updates all the tablesSubWindows with the given data.
	 * @param tablesListData The data needed to update a tablesSubWindow.
	 * @effect All the tableSubwindows are updated.
	 *         | for (SubWindow sw : this.getSubWindows(null)) 
	 *         |	sw.updateContent(tablesListData);
	 */
	public void updateTablesSubWindows(Map<UUID, List<String>> tablesListData) {
		for (SubWindow sw : this.getSubWindows(null)) {
			sw.updateContent(tablesListData);
		}
	}

	/**
	 * Updates all the tableRows and design subWindows associated with the given tableId.
	 * @param id            The id of the table.
	 * @param designData    The data used for the design.
	 * @param tableRowsData The data used in the rows.
	 * @param isComputedTable 
	 * @param rowsClassData The rows class data.
	 * @effect The information is updated for the different tables with the given tableId.
	 *         |for (SubWindow sw : this.getSubWindows(id)) 
	 *        		sw.updateContent(designData, tableRowsData, rowsClassData);
	 */
	public void updateTableRowsAndDesignSubWindows(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> designData, Map<List<Object>, List<Object[]>> tableRowsData,
			boolean isComputedTable) {
		for (SubWindow sw : this.getSubWindows(id)) {
			sw.updateContent(tableName, designData, tableRowsData, isComputedTable);
		}
	}

	/**
	 * Pauses the current subWindow for any changes except for the given cell at the given id.
	 * @param indexOfCell The index of the cell.
	 * @param id          The id on which the cell is located.
	 * @effect The current subWindow is paused using the given parameters.
	 *        | this.getCurrentSubWindow().pauseSubWindow(indexOfCell, idd);
	 */
	public void pauseCurrentSubWindow(int indexOfCell, UUID id) {
		this.getCurrentSubWindow().pauseSubWindow(indexOfCell, id);
	}

	/**
	 * Returns the id of the current subWindow.
	 * @return The id of the current subWindow is returned.
	 *         | this.getCurrentSubWindow().getId()
	 */
	public UUID getCurrentTableSubWindowTableId() {
		return this.getCurrentSubWindow().getId();
	}

	/**
	 * Resumes the current subWindow.
	 * @effect The currentSubWindow is resumed.
	 *         | this.getCurrentSubWindow().resumeSubWindow();
	 */
	public void resume() {
		this.getCurrentSubWindow().resumeSubWindow();
	}

	/**
	 * Returns the ctrlPressed variable.
	 * @return True when ctrl was pressed; otherwise false.
	 */
	private boolean isCtrlPressed() {
		return ctrlPressed;
	}

	/**
	 * Sets the ctrlPressed variable
	 * @param ctrlPressed True when the ctrl-key is pressed otherwise false.
	 * @post The ctrlPressed variable is set. 
	 *       | new.isCtrlPressed = ctrlPressed
	 */
	private void setCtrlPressed(boolean ctrlPressed) {
		this.ctrlPressed = ctrlPressed;
	}

	/**
	 * Gets the PropertyChangeSupport
	 * 
	 * @return support The PropertyChangeSupport variable.
	 */
	private PropertyChangeSupport getSupport() {
		return support;
	}

	/**
	 * Sets the PropertyChangeSupport.
	 * 
	 * @param support                    The PropertyChangeSupport variable
	 * @throws IllegalArgumentException  The support argument equals null
	 *                                   | support == null
	 * @post                             The support given is set    
	 *                                   |new.getSupport() = support
	 */
	private void setSupport(PropertyChangeSupport support) {
		if (support == null) {
			throw new IllegalArgumentException("Cannot set a null support.");
		}
		this.support = support;
	}

	/**
	 * Returns the number of subWindows.
	 * @return The number of subWindows.
	 *         | this.getSubWindows().size()
	 */
	private int getNbrOfSubWindows() {
		return this.getSubWindows().size();
	}

	/**
	 * Handles the incoming click simulation with the given parameters.
	 * @param x The x-coordinate of the click.
	 * @param y The y-coordinate of the click.
	 * @param clickCount The amount of clicks which will be simulated.
	 * @effect The click (mouseEvent) is handled with the given parameters
	 *          | this.handleMouseEvent(MouseEvent.MOUSE_PRESSED, x, y, clickCount);
	 */
	public void simulateClickClicked(int x, int y, int clickCount) {
		this.handleMouseEvent(MouseEvent.MOUSE_PRESSED, x, y, clickCount);
	}

	/**
	 * Handles the incoming click release simulation with the given parameters.
	 * @param x The x-coordinate of the click.
	 * @param y The y-coordinate of the click.
	 * @effect The click (mouseEvent) is handled with the given parameters
	 *          | this.handleMouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 1);
	 */
	public void simulateClickRelease(int x, int y) {
		this.handleMouseEvent(MouseEvent.MOUSE_RELEASED, x, y, 1);
	}

	/**
	 * Handles the incoming click drag simulation with the given parameters.
	 * @param x The x-coordinate of the click.
	 * @param y The y-coordinate of the click.
	 * @effect The click (mouseEvent) is handled with the given parameters
	 *          | this.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, x, y, 1);
	 */
	public void simulateClickDrag(int x, int y) {
		this.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, x, y, 1);
	}

	/**
	 * Simulates a key press with the given character.
	 * @param keyChar The key (character) which will be simulated.
	 * @effect The click (mouseEvent) is handled with the given parameters
	 *          | this.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_UNDEFINED, keyChar)
	 */
	public void simulateKeyPress(char keyChar) {
		this.handleKeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_UNDEFINED, keyChar);
	}

	/**
	 * Simulates a key press with the given key code.
	 * @param keyChar The key (code) which will be simulated.
	 * @effect The click (mouseEvent) is handled with the given parameters
	 *          | this.handleKeyEvent(KeyEvent.KEY_PRESSED, keyCode, ' ');
	 */
	public void simulateKeyPress(int keyCode) {
		this.handleKeyEvent(KeyEvent.KEY_PRESSED, keyCode, ' ');
	}

	public void createFormSubWindow(UUID tableId, String tableName, Map<List<Object>, List<Object[]>> tableData,
			boolean isComputed) {
		if (tableId == null || tableName == null || tableName.isEmpty() || tableData == null) {
			throw new IllegalArgumentException(
					"Cannot create FormWindow with tableData, id or name equals null/empty.");
		}
		this.addCreatedTable(new FormWindow(tableId, tableName, tableData, isComputed));
	}

	public void closeAllDesignWindows(UUID tableId) {
		boolean repaint = false;
		for (SubWindow sw : getSubWindows()) {
			if (sw.getId().equals(tableId) && sw instanceof TableDesignWindow) {
				this.getSubWindows().remove(sw);
				repaint = true;
			}
		}

		if (repaint) {
			getSupport().firePropertyChange(new PropertyChangeEvent(ChangeEventType.REPAINT));
		}
	}

}
