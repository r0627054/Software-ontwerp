package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import controller.handlers.ChangeEventType;

/**
 * The button class is special type of (subclass) of textField.
 *   It has is special behaviour when it is clicked.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Button extends TextField {

	/**
	 * The stroke width variable of the checkBox cross.
	 */
	private final static int STROKE_WIDTH_BORDER = 2;

	/**
	 * The variable storing whether or not the textfield is clicked.
	 */
	private boolean clicked;
	
	/**
	 * The variable storing the changeEventType of the button.
	 */
	private ChangeEventType action;

	/**
	 * Initialise a new button with the given x-coordinate, y-coordinate, height, width, text and the action.
	 * 
	 * @param x      The x-coordinate of the button.
	 * @param y      The y-coordinate of the button.
	 * @param width  The width of the button.
	 * @param height The height of the button.
	 * @param text   The text shown in the button.
	 * @param action The action show in the button.
	 * @effect All the given variables are set within the button.
	 *         | super(x, y, width, height, text);
	 *         | setClicked(false);
	 *         | setAction(action);
	 * 
	 */
	public Button(int x, int y, int width, int height, String text, ChangeEventType action) {
		super(x, y, width, height, text);
		setClicked(false);
		setAction(action);
	}

	
	/**
	 * The button is pained depending if it is clicked or not.
	 * @param g 
	 * 		This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		if (isClicked()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(226, 226, 226));
		}

		g.fillRect(getX(), getY(), getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(STROKE_WIDTH_BORDER, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		super.paint(g);
	}

	/**
	 * If the mouse is pressed down the button is clicked if the mouse is released the mouse is not clicked anymore.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect When the mouse is pressed the clicked variable is set to true. When the mouse is released the clicked variable is set to false.
	 *        |if (id == MouseEvent.MOUSE_PRESSED) 
	 *        |  	this.setClicked(true);
	 *        | if (id == MouseEvent.MOUSE_RELEASED && isClicked()) 
	 *        |		this.setClicked(false);
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_PRESSED) {
			this.setClicked(true);
		} else if (id == MouseEvent.MOUSE_RELEASED && isClicked()) {
			this.setClicked(false);
			this.propertyChanged(getId(), getAction(), null, null);
		}
	}

	/**
	 * When there is a click outside the variable clicked is set to false.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The clicked variable is set to false.
	 *        | this.setClicked(false);
	 */
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		this.setClicked(false);
	}

	/**
	 * Returns whether or not the button is clicked.
	 */
	private boolean isClicked() {
		return clicked;
	}

	/**
	 * Sets whether or not the button is clicked.
	 * When the clicked variable is different from its previous it will launch a propertyChanged.
	 * @param clicked true when the button is clicked; otherwise false.
	 * @post The clicked variable is set.
	 *       | new.isClicked() == clicked
	 * @effect the propertyChanged event is raised when the click variable is different from its previous value.
	 *       | if ((this.clicked && !clicked) || (!this.clicked && clicked)) 
	 *       |	this.propertyChanged();
	 */
	private void setClicked(boolean clicked) {
		this.clicked = clicked;
		if ((this.clicked && !clicked) || (!this.clicked && clicked)) {
			this.propertyChanged();
		}

	}

	/**
	 * Returns the action (ChangeEventType of the button)
	 */
	private ChangeEventType getAction() {
		return action;
	}

	/**
	 * Sets the action of the button.
	 * @param action The changeEventType (action) when raised when the button is pressed.
	 * @throws IllegalArgumentException when the action equals null.
	 *        | action == null
	 */
	private void setAction(ChangeEventType action) {
		if(action == null) {
			throw new IllegalArgumentException("The action of a button cannot be equal to null.");
		}
		this.action = action;
	}

}
