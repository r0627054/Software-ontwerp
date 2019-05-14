package ui.model.components;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;

/**
 * TableList is a subClass of a VerticalComponentList.
 *  It contains the list of table names.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class TableList extends HorizontalComponentList {

	public static final int TABLE_NAMES_WIDTH = 100;
	public static final int QUERY_WIDTH = 700;
	public static final int ROW_HEIGHT = 30;

	/**
	 * Initialise this new TableList with all the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @effect All the variables are set using the super class.
	 *         | super(x, y, width, height)
	 */
	public TableList(int x, int y, int width, int height) {
		super(x, y);
	}

	/**
	 * Creates the full table with all the data and components.
	 * an automatically positions the children.
	 * 
	 * @param data
	 *        | Map containing all the information used to create the tableList.
	 *        | The UUID of the table and the name of the table.
	 * @param pcl
	 *        | The propertyChangeListener used in the TableList (Observer)
	 */
	public List<UICell> createTableList(Map<UUID, List<String>> data) {
		List<UICell> listOfAllCells = new ArrayList<>();
		ChangeEventType submitAction = ChangeEventType.TABLE_CHANGE_NAME;
		ChangeEventType deleteAction = ChangeEventType.DELETE_TABLE;
		ChangeEventType doubleClickAction = ChangeEventType.OPEN_TABLESUBWINDOW;
		ChangeEventType queryAction = ChangeEventType.CREATE_COMPUTED_TABLE;

		VerticalComponentList tableList = new VerticalComponentList(getX(), getY(), TABLE_NAMES_WIDTH, getHeight());
		VerticalComponentList queryList = new VerticalComponentList(getX(), getY(), QUERY_WIDTH, getHeight());

		for (Map.Entry<UUID, List<String>> entry : data.entrySet()) {
			TextField textField = new EditableTextField(0, 0, TABLE_NAMES_WIDTH, ROW_HEIGHT, entry.getValue().get(0),
					entry.getKey(), submitAction, doubleClickAction, deleteAction);
			UICell textCell = new UICell(textField, entry.getKey(), TABLE_NAMES_WIDTH, ROW_HEIGHT);

			String queryString = entry.getValue().size() > 1 ? entry.getValue().get(1) : "";
			TextField queryField = new EditableTextField(0, 0, QUERY_WIDTH, ROW_HEIGHT, queryString, entry.getKey(),
					queryAction, null, null);
			UICell queryCell = new UICell(queryField, entry.getKey(), QUERY_WIDTH, ROW_HEIGHT);

			queryList.addComponent(queryCell);
			tableList.addComponent(textCell);
			listOfAllCells.add(textCell);
			listOfAllCells.add(queryCell);
		}
		tableList.positionChildren();
		queryList.positionChildren();

		this.addComponent(tableList);
		this.addComponent(queryList);
		return listOfAllCells;
	}

	/**
	 * Checks whether a component inside the list has a component with an error.
	 * @return True if there exists a component in the list with an error.
	 */
	private boolean hasCurrentError() {
		boolean hasCurrentlyError = false;
		for (Component c : getComponentsList()) {
			for (Component comp : ((VerticalComponentList) c).getComponentsList()) {
				UICell cell = (UICell) comp;
				if (cell.isError()) {
					hasCurrentlyError = true;
				}
			}
		}
		return hasCurrentlyError;
	}

	/**
	 * Handles the mouseClickedEvent.
	 * If non of the components has an error, the super class should handle the mouseClicked event.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// DO NOTHING
	}

	/**
	 * 
	 * Delegates the the keyPressed event to all the components of the TableList.
	 *  
	 * @param id
	 *        | The id of the key pressed event. 
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// Do nothing
	}

	/**
	 * If there is no component with an error,
	 * the TableList checks which event should be raised.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 */
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (!hasCurrentError()) {
			if (id == MouseEvent.MOUSE_PRESSED) {

				if (clickCount == 2 && y > (this.getY() + getMaxHeightFromChildren())) {
					propertyChanged(null, ChangeEventType.CREATE_TABLE, null, null);
				}
				VerticalComponentList namesList = (VerticalComponentList) getComponentsList().get(0);
				for (Component comp : namesList.getComponentsList()) {
					if (!comp.isWithinComponent(x, y)) {
						UICell cell = (UICell) comp;
						cell.outsideClick(id, x, y, clickCount);
						EditableTextField textField = (EditableTextField) cell.getComponent();
						if (y > comp.getY() && y < comp.getOffsetY() && x < comp.getX()) {
							textField.setSelectedForDelete(true);
						} else {
							textField.setSelectedForDelete(false);
						}
					}
				}
			}
		}
	}

	/**
	 * Returns the cell with given tableId.
	 * 
	 * @param tableId
	 *        | The id of the column.
	 * @param columnIndex 
	 * @return the cell at the given index in the given column.
	 */
	public UICell getCell(UUID tableId, int columnIndex) {
		VerticalComponentList list = (VerticalComponentList) this.getComponentsList().get(columnIndex);
		for (Component c : list.getComponentsList()) {
			if (((UICell) c).getId() == tableId) {
				return ((UICell) c);
			}
		}
		return null;
	}

	public UICell getSelectedCell() {
		for (Component c : getComponentsList()) {
			for (Component comp : ((VerticalComponentList) c).getComponentsList()) {
				if (comp instanceof UICell) {
					if (((UICell) comp).getComponent() instanceof EditableTextField) {
						EditableTextField etf = (EditableTextField) ((UICell) comp).getComponent();
						if (etf.isSelected()) {
							return (UICell) comp;
						}
					}
				}
			}
		}
		return null;
	}

}
