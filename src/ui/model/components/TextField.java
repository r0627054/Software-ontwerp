package ui.model.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.UUID;

import controller.handlers.ChangeEventType;

/**
 * A textField is a subclass of an EditableComponent.
 *  It handles the drawing and styling of the text.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class TextField extends EditableComponent {

	/**
	 * The variable storing the font used to paint the component.
	 */
	public Font font;

	/**
	 * The variable storing the margin used in the textField.
	 */
	public static final int MARGIN = 3;

	/**
	 * The variable storing the text used in the textField.
	 */
	private String text;

	/**
	 * Initialise this new textField with all the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the textField.
	 * @param y
	 *        The y-coordinate of the textField.
	 * @param width
	 *        The width of the textField.
	 * @param height
	 *        The height of the textField.
	 * @param text
	 *        The text stored in the textField.
	 * @param id
	 *        The id of the textField.
	 * @effect All the variables are of the TextField.
	 *       | this(x, y, width, height, true, text, id)
	 */
	public TextField(int x, int y, int width, int height, String text, UUID id) {
		this(x, y, width, height, true, text, id);
	}

	/**
	 * Initialise this new TextField with all the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the textField.
	 * @param y
	 *        The y-coordinate of the textField.
	 * @param width
	 *        The width of the textField.
	 * @param height
	 *        The height of the textField.
	 * @param hidden
	 *        Whether the textField is hidden or not.
	 * @param text
	 *        The text stored in the textField.
	 * @param id
	 *        The id of the textField.
	 * @effect All the variables are set and by default a new Font is created.
	 *        | super(x, y, width, height, hidden, id);
	 *        |	this.setText(text);
	 *        | this.setFont(new Font("NewTimesRoman", Font.PLAIN, 12));
	 */
	public TextField(int x, int y, int width, int height, boolean hidden, String text, UUID id) {
		super(x, y, width, height, hidden, id);
		this.setText(text);
		this.setFont(new Font("NewTimesRoman", Font.PLAIN, 12));
	}

	/**
	 * Initialise the textField with all the given variables and a new UUID.
	 * 
	 * @param x
	 *        The x-coordinate of the textField.
	 * @param y
	 *        The y-coordinate of the textField.
	 * @param width
	 *        The width of the textField.
	 * @param height
	 *        The height of the textField.
	 * @param text
	 *        The text stored in the textField.
	 * @effect All the variables are set and by default a UUID is generated.
	 *        | this(x, y, width, height, text, UUID.randomUUID())
	 */
	public TextField(int x, int y, int width, int height, String text) {
		this(x, y, width, height, text, UUID.randomUUID());
	}

	/**
	 * The font is set for the textField.
	 * 
	 * @param font
	 *        | The new font of the textField.
	 * @throws IllegalArgumentException when the font equals null
	 *        | font == null
	 * @post The new font is set to the given font.
	 *        | this.getFont() == font
	 */
	private void setFont(Font font) {
		if (font == null)
			throw new IllegalArgumentException("Font cannot be null inside a TextField."); // TODO hier kom je nooit
		this.font = font;
	}

	/**
	 * Returns the font of the TextField.
	 */
	public Font getFont() {
		return this.font;
	}

	/**
	 * The text is set for the textField.
	 * 
	 * @param text
	 *        | The new text of the textField.
	 * @throws IllegalArgumentException when the text equals null
	 *        | text == null
	 * @post The new text is set to the given text.
	 *        | this.getText() == text
	 */
	public void setText(String text) {
		if (text == null)
			throw new IllegalArgumentException("Text of TextField cannot be empty.");

		this.text = text;
		propertyChanged();
	}

	/**
	 * Returns the text of the textField.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Draws the textField with the correct font and position and a rectangle is drawn around it.
	 * 
	 * @param g 
	 * 		This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {

		Rectangle current = g.getClip().getBounds();
		Rectangle temp = new Rectangle(getX(), getY(), getWidth(), getHeight());

		if (temp.getWidth() + temp.getX() > current.getWidth() + current.getX()) {
			System.out.println(current.getWidth());
			int newWidth = (int) (current.getWidth() - this.getX() + current.getX());
			temp.setSize(newWidth, (int) temp.getHeight());
		}
		if (temp.getHeight() + temp.getY() > current.getHeight() + current.getY()) {
			int newHeight = (int) (current.getHeight() - this.getY() + current.getY());
			temp.setSize((int) temp.getWidth(), newHeight);
		}

		g.setColor(Color.BLACK);
		g.setFont(this.getFont());

		g.setClip(temp);
		drawString(g);
		g.setClip(current);

		if (this.isError()) {
			displayError((Graphics2D) g.create());
		}
	}

	/**
	 * Draws the actual string with the correct margin.
	 */
	protected void drawString(Graphics2D g) {
		g.drawString(getText(), getX() + MARGIN, getOffsetY() - MARGIN);
	}

	/**
	 * Handles the key pressed Event.
	 *  The keyPressed Event of a TextField will not anything.
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
	}

	/**
	 * Handles the mouse Click.
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
		propertyChanged(this.getId(), null, null, null);
	}

}
