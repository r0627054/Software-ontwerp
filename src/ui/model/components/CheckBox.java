package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.UUID;

import controller.handlers.ChangeEventType;

/**
 * The CheckBox is an EditableComponent.
 * It draws a checkBox which can be checked or unchecked.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CheckBox extends EditableComponent {
	
	/**
	 * The size variable of the checkBox square.
	 */
	private final static int SIZE = 18;
	
	/**
	 * The stroke width variable of the checkBox square itself.
	 */
	private final static int STROKE_WIDTH_BOX = 3;
	
	/**
	 * The stroke width variable of the checkBox cross.
	 */
	private final static int STROKE_WIDTH_CROSS = 2;

	private final ChangeEventType action;
	
	/**
	 * The variable storing whether the box is checked or not.
	 */
	private boolean checked;

	/**
	 * Initialise this new checkBox with the given variables.
	 * 
	 * The x and y coordinates are by default equal to 0.
	 * 
	 * @param checked
	 *        The variable storing whether the box is checked or not.
	 * @param id
	 *        The id of the checkBox.
	 * @effect All the variables are set and the x and y coordinates are equal to 0.
	 *         | this(0, 0, checked, id)
	 */
	public CheckBox(boolean checked, UUID id, ChangeEventType action) {
		this(0, 0, checked, id, action);
	}

	/**
	 * Initialise this new checkBox with the given variables.
	 * 
	 * The width and the height are by default the size of box itself.
	 *  
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param checked
	 *        The variable storing whether the box is checked or not.
	 * @param id
	 *        The id of the checkBox.
	 * @effect All the variables are set and the width and height are by default the size of the box itself.
	 *        | this(x, y, SIZE, SIZE, checked, id)
	 */
	public CheckBox(int x, int y, boolean checked, UUID id, ChangeEventType action) {
		this(x, y, SIZE, SIZE, checked, id, action);
	}

	/**
	 * Initialise this new checkBox with the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param checked
	 *        The variable storing whether the box is checked or not.
	 * @param id
	 *        The id of the checkBox.
	 * @effect all the variables are set
	 *       | super(x, y, width, height, false, id);
	 *       | setChecked(checked)
	 */
	public CheckBox(int x, int y, int width, int height, boolean checked, UUID id, ChangeEventType action) {
		super(x, y, width, height, false, id);
		setChecked(checked);
		this.action = action;
	}

	/**
	 * Sets the check variable to the new value.
	 * It fires a propertyChangedEvent, because the value is changed.
	 * 
	 * @param checked
	 *        | Whether or not the variable is checked or not.
	 * @post The check variable equals the checked parameter.
	 *        | new.getChecked() == checked
	 */
	private void setChecked(boolean checked) {
		this.checked = checked;
		if(getAction() != null) {
			propertyChanged(this.getId(), getAction(), !this.isChecked(), this.isChecked());
		}
//		propertyChanged(this.getId(), ChangeEventType.TABLE_CHANGE_NAME, !this.isChecked(), this.isChecked());
	}

	/**
	 * Returns whether or not the checkBox is checked or not.
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * Draws the checkBox and if the checkBox is checked a cross is shown.
	 * @param g 
	 * 		  | This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		int x1 = getX() + (getWidth() - SIZE) / 2;
		int y1 = getY() + (getHeight() - SIZE) / 2;
		g.setColor(Color.BLACK);

		if (this.isError()) {
			this.displayError((Graphics2D) g.create());
		}

		if (getWidth() != SIZE) {
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}

		g.setStroke(new BasicStroke(STROKE_WIDTH_BOX, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.drawRect(x1, y1, SIZE, SIZE);

		if (this.checked) {
			g.setStroke(new BasicStroke(STROKE_WIDTH_CROSS, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
			g.drawLine(x1, y1, x1 + SIZE, y1 + SIZE);
			g.drawLine(x1, y1 + SIZE, x1 + SIZE, y1);
		}
	}

	/**
	 * If the mouse clicked inside the component,
	 *  the CheckedVariable is set to the opposite value. 
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The CheckedVariable is set to the opposite value when the mouse is pressed within the checkbox.
	 *        | if (id == MouseEvent.MOUSE_PRESSED && isWithinComponent(x, y))
	 *        | 	 this.setChecked(!isChecked())
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_PRESSED && isWithinComponent(x, y)) {
			this.setChecked(!isChecked());
		}
	}

	/**
	 *  Does nothing inside a CheckBoxs.
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
	public void keyPressed(int id, int keyCode, char keyChar) {}

	/**
	 * Does nothing when there is an outside click.
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
	public void outsideClick(int id, int x, int y, int clickCount) {}

	private ChangeEventType getAction() {
		return action;
	}
	

}
