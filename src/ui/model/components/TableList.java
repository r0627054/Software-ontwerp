package ui.model.components;

import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeListener;

/**
 * TableList is a subClass of a VerticalComponentList.
 *  It contains the list of table names.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class TableList extends VerticalComponentList {

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
		super(x, y, width, height);
	}

	/**
	 * Creates the full table with all the data and components.
	 * an automatically positions the children.
	 * 
	 * @param map
	 *        | Map containing all the information used to create the tableList.
	 *        | The UUID of the table and the name of the table.
	 * @param pcl
	 *        | The propertyChangeListener used in the TableList (Observer)
	 */
	public void createTableList(Map<UUID, String> map, PropertyChangeListener pcl) {
		for (Map.Entry<UUID, String> entry : map.entrySet()) {
			TextField textField = new EditableTextField(0, 0, 200, 40, entry.getValue(), entry.getKey());
			textField.addPropertyChangeListener(pcl);
			this.addComponent(textField);
		}
		this.positionChildren();
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
	 * @effect The keyPressed event is passed to all the components of the TableList.
	 *        | for (Component c : getComponentsList()) {
	 *        |  	c.keyPressed(id, keyCode, keyChar);
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		for (Component c : getComponentsList()) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	/**
	 * Checks whether a component inside the list has a component with an error.
	 * @return True if there exists a component in the list with an error.
	 */
	private boolean hasCurrentError() {
		boolean hasCurrentlyError = false;
		for (Component c : getComponentsList()) {
			EditableTextField textField = (EditableTextField) c;
			if (textField.isError()) {
				hasCurrentlyError = true;
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
	 * @effect If non of the components has an error, the super class should handle the mouseClicked event.
	 *        | if (!hasCurrentError()) 
		      | 	super.mouseClicked(id, x, y, clickCount)
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (!hasCurrentError()) {
			super.mouseClicked(id, x, y, clickCount);
		}
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
			if (id == MouseEvent.MOUSE_CLICKED) {
				if (clickCount == 2 && y > this.getOffsetY()) {
					propertyChanged(null, ChangeEventType.CREATE_TABLE, null, null);
				}

				for (Component c : getComponentsList()) {
					EditableTextField textField = (EditableTextField) c;
					c.outsideClick(id, x, y, clickCount);

					if (y > c.getY() && y < c.getOffsetY() && x < c.getX()) {
						textField.setSelectedForDelete(true);
					} else {
						textField.setSelectedForDelete(false);
					}
				}
			}
		}
	}

}
