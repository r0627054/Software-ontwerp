package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;

/**
 * An EditableComponent is a specific component, which can be edited.
 * It has an id and it can have an error.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public abstract class EditableComponent extends Component {
	
	/**
	 * The variable storing the size of the rectangle.
	 */
	private final static int ERROR_RECT_SIZE = 2;

	/**
	 * The variable storing the id.
	 */
	private final UUID id;
	
	/**
	 * The variable storing whether or not is an error.
	 */
	private boolean error;

	/**
	 * Initialise the EditableComponent with the given information.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param hidden
	 *        The variable showing whether or not the component is hidden.
	 * @param id
	 *        The id of the component.
	 * @effect the variables are set
	 *        | super(x, y, width, height, hidden);
	 *        |	this.id = id;
	 */
	public EditableComponent(int x, int y, int width, int height, boolean hidden, UUID id) {
		super(x, y, width, height, hidden);
		this.id = id;
	}
		
	/**
	 * Initialise the EditableComponent with the given information.
	 * 
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param id
	 *        The id of the component.
	 * @effect the variables are set
	 *        | super(width, height);
	 *        |	this.id = id;
	 */
	public EditableComponent(int width, int height, UUID id) {
		super(width, height);
		this.id = id;
	}

	/**
	 * Returns the id of the EditableComponent.
	 */
	protected UUID getId() {
		return id;
	}

	/**
	 * Returns whether or not the EditableComponent contains an error.
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Sets the error variable of the EditableComponent.
	 * @param error
	 *        | The error variable displaying whether or not the EditableComponent has an error.
	 * @post The error is equal to the error parameter.
	 *        | new.isError == error 
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * Draws the error of the editable Component on the canvas.
	 * @param g 
	 * 		This object offers the methods that allow you to paint on the canvas.
	 */
	protected void displayError(Graphics2D g) {
		g.setStroke(new BasicStroke(ERROR_RECT_SIZE, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.setColor(Color.RED);

		// -1 & +/- Error_rect_size zodat rode kader niet overlapped met zwarte kader
		// van TextField
		g.drawRect(getX() + ERROR_RECT_SIZE, getY() + ERROR_RECT_SIZE, getWidth() - ERROR_RECT_SIZE - 1,
				getHeight() - ERROR_RECT_SIZE - 1);
	}

}
