package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;
/**
 * A ColumnHeader is a subclass of a TextField. It has a more specific paint method than a regular textField.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class ColumnHeader extends TextField {

	/**
	 * Initialise a new ColumnHeader with the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param text
	 *        | The text displayed in the header.
	 * @param id
	 *        | The id of the header.
	 * @effect All the variables are set.
	 *        | super(x, y, width, height, false, text,id)
	 */
	public ColumnHeader(int x, int y, int width, int height, String text, UUID id) {
		super(x, y, width, height, false, text,id);
	}
	
	/**
	 * Initialise a new ColumnHeader with a text and an Id.
	 * The x,y-coordinates and with, height are predifined.
	 * 
	 * 
	 * @param text
	 *        | The text displayed in the header.
	 * @param id
	 *        | The id of the header.
	 * @effect The variables are set with default values and a text and id.
	 *        | super(30, 50, 100, 50, text,id);
	 */
	public ColumnHeader(String text, UUID id) {
		super(30, 50, 100, 50, text,id);
		//TODO replace with default values
	}
	
	/**
	 * The Specific header design is drawn when this method is called.
	 * @param g 
	 * 			This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.PINK);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		super.paint((Graphics2D) g.create());
	}

}
