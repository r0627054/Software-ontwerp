package ui.model.components;

import java.awt.Graphics2D;

/**
 * A component is an abstract class which has a given X-coordinate, Y-coordinate, width,
 * height and whether the component is hidden or not.
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public abstract class Component {

	/**
	 * Variable storing the x coordinate of the component.
	 */
	private int x;

	/**
	 * Variable storing the y coordinate of the component.
	 */
	private int y;

	/**
	 * Variable storing the width of the component.
	 */
	private int width;

	/**
	 * Variable storing the height coordinate of the component.
	 */
	private int height;

	/**
	 * Variable storing whether the component is hidden or not.
	 */
	private boolean hidden;
	
	/**
	 * Variable storing the parent container.
	 */
	private Container container;

	/**
	 * Initialise this new component with all the given variables.
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
	 * @effect All the variables are set, using the setters.
	 *         |setX(x)
	 *         |setY(y)
	 *         |setHeight(height)
	 *         |setWidth(width)
	 *         |setHidden(hidden)
	 */
	public Component(int x, int y, int width, int height, boolean hidden) {
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setHidden(hidden);
	}

	/**
	 * Initialise the given component with the given width, height and hidden.
	 * The x- and y-coordinate are by default 0.
	 * 
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param hidden
	 *        The variable showing whether or not the component is hidden.
	 * @effect The variables are set, by making use of the other constructor.
	 *        | this(0,0,width,height,hidden)
	 * 
	 */
	public Component(int width, int height, boolean hidden) {
		this(0, 0, width, height, hidden);
	}

	/**
	 * Initialise the given component with the given width and height.
	 * The x- and y-coordinate are by default 0 and the component is not hidden.
	 * 
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @effect The variables are set, by making use of the other constructor.
	 *        | this(width,height,true)      
	 */
	public Component(int width, int height) {
		this(width, height, true);
	}

	/**
	 * Returns the x-coordinate of the component.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate of the component.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @throws IllegalArgumentException
	 *         The x coordinate is negative.
	 *         | x < 0
	 * @post The x-coordinate is set with the given value.
	 *       | new.getX() == x
	 */
	protected void setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("The x-coordinate of the component cannot be negative.");
		}
		this.x = x;
	}

	/**
	 * Returns the offset x, this is the x-coordinate plus the width.
	 * 
	 * @return The x coordinate plus the width.
	 *         | this.getX() + this.getWidth()
	 */
	public int getOffsetX() {
		return this.getX() + this.getWidth();
	}

	/**
	 * Returns the offset y, this is the y-coordinate plus the height.
	 * 
	 * @return The y coordinate plus the height.
	 *         |this.getY() + this.getHeight()
	 */
	public int getOffsetY() {
		return this.getY() + this.getHeight();
	}

	/**
	 * Returns the y-coordinate of the component.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate of the component.
	 * 
	 * @param y
	 *        The y-coordinate of the component.
	 * @throws IllegalArgumentException
	 *         The y coordinate is negative.
	 *         | y < 0
	 * @post The y-coordinate is set with the given value.
	 *       | new.getY() == y
	 */
	protected void setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("The y-coordinate of the component cannot be negative.");
		}
		this.y = y;
	}

	/**
	 * Returns the width of the component.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the component.
	 * 
	 * @param width
	 *        The width of the component.
	 * @throws IllegalArgumentException
	 *         The width is negative.
	 *         | width < 0
	 * @post The width is set with the given value.
	 *       | new.getWidth() == width
	 */
	private void setWidth(int width) {
		if (width < 0) {
			throw new IllegalArgumentException("The width of the component cannot be negative.");
		}
		this.width = width;
	}

	/**
	 * Returns the height of the component.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the component.
	 * 
	 * @param height
	 *        The height of the component.
	 * @throws IllegalArgumentException
	 *         The height is negative.
	 *         | height < 0
	 * @post The height is set with the given value.
	 *       | new.getHeight() == height
	 */
	private void setHeight(int height) {
		if (height < 0) {
			throw new IllegalArgumentException("The height of the component cannot be negative.");
		}
		this.height = height;
	}

	/**
	 * Returns whether the component is hidden or not.
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden variable of the component.
	 * 
	 * @param hidden
	 *        The variable showing whether or not the component is hidden.
	 * @post The hidden variable is set with the given value.
	 *       | new.isHidden() == hidden
	 */
	private void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	

	/**
	 * Containing all visualisation and paint methods of the component.
	 * 
	 * @param g 
	 * 			This object offers the methods that allow you to paint on the canvas.
	 */
	public abstract void paint(Graphics2D g);
	
	public boolean isWithinComponent(int x, int y) {
		return x >= getX() && x <= getOffsetX() && y >= getY() && y <= getOffsetY();
	}
	

	public abstract void mouseClicked(int id, int x, int y, int clickCount);

	public abstract void keyPressed(int id, int keyCode, char keyChar);

	protected void propertyChanged() {	
		//Dit faalt omdat je nog geen container meegeeft via de constructors van Components
		//Zie Container klasse commentaar
		
		//container.repaintContainer();
	}

}
