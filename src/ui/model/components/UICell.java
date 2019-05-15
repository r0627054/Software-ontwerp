package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;

/**
 * A Cell is an EditableComponent which on his turn contains a component.
 * It makes use of the observer pattern. It listens to events of the component in the cell.
 * And fires propertyChange events to his listeners.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class UICell extends EditableComponent implements PropertyChangeListener {

	/**
	 * The variable storing the component inside the cell.
	 */
	private Component component;

	/**
	 * The variable storing the defaultHeight of the Cell.
	 */
	private final static int defaultHeight = 50;

	/**
	 * The variable storing the defaultWidth of the Cell.
	 */
	private final static int defaultWidth = 100;

	/**
	 * The variable storing whether or not a redBackground will be shown.
	 */
	private boolean redBackground = false;

	/**
	 * 
	 * Initialise this new cell with all the given variables.
	 * 
	 * @param x
	 *        | The x-coordinate of the cell.
	 * @param y
	 *        | The y-coordinate of the cell.
	 * @param cellComponent
	 *        | The component inside the cell.
	 * @param id
	 *        | The id of the cell.
	 * @effect All the variables are set and the components coordinates are changed.
	 *        | super(x, y, defaultWidth, defaultHeight, false, id)
	 *        |	this.setComponent(cellComponent)
	 *        |	setComponentCoordinates(x, y, defaultWidth, defaultHeight)
	 */
	public UICell(Component cellComponent, UUID id) {
		super(0, 0, defaultWidth, defaultHeight, false, id);
		this.setComponent(cellComponent);
		setComponentCoordinates(0, 0, defaultWidth, defaultHeight);
	}
	
	public UICell(Component cellComponent, UUID id, int width, int height) {
		super(0, 0, width, height, false, id);
		this.setComponent(cellComponent);
		setComponentCoordinates(0, 0, width, height);
	}

	/**
	 * Initialise this new cell with all the given variables and a cellType equals null.
	 * @param x
	 *        | The x-coordinate of the cell.
	 * @param y
	 *        | The y-coordinate of the cell.
	 * @param value
	 *        | The Component of the cell.
	 * @param id
	 *        | The id of the cell.
	 * @effect All the variables are set, and the cellType equals null.
	 *        | this(x, y, value, id, null)
	 */
	public UICell(Object value, UUID id, ChangeEventType submitAction, ChangeEventType doubleClickAction,
			ChangeEventType deleteAction) {
		this(value, id, value.getClass(), submitAction, doubleClickAction, deleteAction);
	}

	/**
	 * 
	 * Initialise this new cell with all the given variables.
	 * 
	 * @param x
	 *        | The x-coordinate of the cell.
	 * @param y
	 *        | The y-coordinate of the cell.
	 * @param value
	 *        | The Component of the cell.
	 * @param id
	 *        | The id of the cell
	 * @param cellType
	 *        | The type class of the component.
	 * @effect All the variables are set and a new component is created.
	 *        | super(x, y, defaultWidth, defaultHeight, false, id)
	 *        |	createComponent(value, id, cellType)
	 *        
	 */
	public UICell(Object value, UUID id, Class<?> cellType, ChangeEventType submitAction,
			ChangeEventType doubleClickAction, ChangeEventType deleteAction) {
		super(0, 0, defaultWidth, defaultHeight, false, id);
		createComponent(value, id, cellType, submitAction, doubleClickAction, deleteAction);
	}

	/**
	 * Sets the component coordinates.
	 * We are talking about the component inside the cell. 
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @effect The coordinates of the component are set. 
	 *        | getComponent().setX(x)
	 *        | setX(x);
	 *        |	getComponent().setY(y);
	 *        |	getComponent().setWidth(width);
	 *	      | getComponent().setHeight(height);
	 */
	private void setComponentCoordinates(int x, int y, int width, int height) {
		Component c = getComponent();
		c.setX(x);
		c.setY(y);
		c.setWidth(width);
		c.setHeight(height);
	}

	/**
	 * Sets the component to the same values as the cell coordinates.
	 * @effect The component coordinates are updated.
	 *         | setComponentCoordinates(getX(), getY(), getWidth(), getHeight())
	 */
	private void refreshComponentCoordinates() {
		setComponentCoordinates(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Creates a new component and sets it to the component of the cell.
	 * 
	 * @param value
	 *        | The value of the component.
	 * @param id
	 *        | The id of the component.
	 * @param cellType
	 *        | The type of the component.
	 * @param deleteAction 
	 * @param doubleClickAction 
	 * @param submitAction 
	 */
	private void createComponent(Object value, UUID id, Class<?> cellType, ChangeEventType submitAction,
			ChangeEventType doubleClickAction, ChangeEventType deleteAction) {
		Component component;

		if (cellType == Boolean.class) {
			if (value == null || String.valueOf(value).isEmpty()) {
				component = new ToggleTextField(100, 100, "", id, submitAction);
			} else {
				if (value instanceof String) {
					value = Boolean.parseBoolean((String) value);
				}
				component = new CheckBox((boolean) value, id, submitAction);
			}
		} else if (value != null) {
			component = new EditableTextField(value.toString(), id, submitAction, doubleClickAction, deleteAction);
		} else {
			component = new EditableTextField("", id, submitAction, doubleClickAction, deleteAction);
		}
		this.setComponent(component);
	}

	/**
	 * Refreshed the component coordinates and paints the component inside the cell.
	 * @param g 
	 * 		  | This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		refreshComponentCoordinates();

		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		

		if (this.isRedBackground()) {
			g.setColor(Color.RED);
			g.fillRect(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);
		}

		getComponent().paint((Graphics2D) g.create());
	}

	/**
	 * Delegates the mouseClick event to the component of the cell.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The component is called with mouseClick event.
	 *        | getComponent().mouseClicked(id, x, y, clickCount)
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (!isError() || this.getComponent() instanceof ToggleTextField || this.getComponent() instanceof CheckBox) {
			getComponent().mouseClicked(id, x, y, clickCount);
		}
	}

	/**
	 * Delegates the outsideClick event to the component of the cell.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The component is called with outside click event.
	 *        | getComponent().outsideClick(id, x, y, clickCount)
	 */
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		getComponent().outsideClick(id, x, y, clickCount);
	}

	/**
	 * Delegates the keyPressed event to the component of the cell.
	 * 
	 * @param id
	 *        | The id of the key pressed event. 
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 * @effect The component is called with the keyPressed Event.
	 *        | getComponent().keyPressed(id, keyCode, keyChar)
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		getComponent().keyPressed(id, keyCode, keyChar);
	}

	/**
	 * Returns the component variable
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * Sets the component variable.
	 * 
	 * @param component
	 *        | The component inside the cell.
	 * @throws IllegalArgumentException if the component equals null.
	 *        | component == null
	 * @effect The component is set and the cell is added as propertyChangeListener for the component.
	 *        | this.component = component;
	 *        |	component.addPropertyChangeListener(this);
	 */
	public void setComponent(Component component) {
		if (component == null) {
			throw new IllegalArgumentException("Cannot add a null component to a cell.");
		}
		this.component = component;
		component.addPropertyChangeListener(this);
	}

	/**
	 * Fires the propertyChange if the action type is not equal to null and the eventType does not equal repaint.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getSupport().firePropertyChange(evt);
	}

	/**
	 * Sets the error of the component inside the cell. If this component is an editableComponent. 
	 * @param error 
	 *        | Whether or not the component has an error.
	 */
	public void setError(boolean error) {
		super.setError(error);
		if (getComponent() instanceof EditableComponent) {
			EditableComponent editable = (EditableComponent) getComponent();
			editable.setError(error);
		}
	}

	/**
	 * Sets the error with a new Value for the component.
	 * If the component is a ToggleTextField.
	 * 
	 * @param b
	 *        | Whether or not the component has an error.
	 * @param newValue
	 *        | The newValue of the component
	 */
	public void setErrorWithNewValue(boolean b, Object newValue) {
		if (getComponent() instanceof ToggleTextField) {
			ToggleTextField toggleTextField = (ToggleTextField) getComponent();
			toggleTextField.setError(b);
			toggleTextField.setText(newValue.toString());
		} // TODO Add checkbox
	}

	/**
	 * Returns true if the component is an editableTextField and it is selected.
	 * Otherwise it returns false.
	 */
	public boolean hasSelectedEditableTextField() {
		if (this.getComponent() instanceof EditableTextField) {
			EditableTextField editableTextField = (EditableTextField) getComponent();
			if (editableTextField.isSelected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the redBackground variable to the new value.
	 * @param b
	 *        | Whether or not the background of the cell should be red.
	 * @post redBackGroundValue equals b
	 *        | new.getBackground() == b
	 */
	public void setRedBackground(boolean b) {
		if ((redBackground && !b) || (!redBackground && b)) {
			this.propertyChanged();
		}
		this.redBackground = b;
	}

	/**
	 * Returns whether the background should be red.
	 */
	private boolean isRedBackground() {
		return redBackground;
	}

	/**
	 * Sets the x-coordinate of the cell.
	 * If the component inside the cell not equals null, the x is also updated.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 */
	@Override
	protected void setX(int x) {
		super.setX(x);

		if (this.getComponent() != null) {
			this.getComponent().setX(x);
		}
	}

	/**
	 * Sets the y-coordinate of the cell.
	 * If the component inside the cell not equals null, the y is also updated.
	 * 
	 * @param y
	 *        The y-coordinate of the component.
	 */
	@Override
	protected void setY(int y) {
		super.setY(y);

		if (this.getComponent() != null) {
			this.getComponent().setY(y);
		}
	}

	/**
	 * Checks whether the component inside the cell is an editableComponent, when this is the case an error is thrown for that component.
	 * @param id
	 *        | The id of which element an error is thrown.
	 */
	@Override
	public void throwError(UUID id) {
		if (this.getComponent() instanceof EditableComponent) {
			EditableComponent component = (EditableComponent) getComponent();
			if (component.getId().equals(id)) {
				component.setError(true);
			}
		}
	}

}
