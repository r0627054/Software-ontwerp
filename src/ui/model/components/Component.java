package ui.model.components;

import java.awt.Graphics2D;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import controller.observer.PropertyChangeSupport;

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
	 * Variable storing the PropertyChangeSupport.
	 */
	private PropertyChangeSupport support;

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
	 *         |this.setSupport(new PropertyChangeSupport(this));
	 */
	public Component(int x, int y, int width, int height, boolean hidden) {
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setHidden(hidden);
		this.setSupport(new PropertyChangeSupport());
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
		this(width, height, false);
	}

	/**
	 * Adds a propertyChangeListener to the PropertyChangeSupport.
	 * @param pcl
	 *        | The propertyChangeListener.
	 * @effect Adds a propertyChangeListener to the PropertyChangeSupport.
	 *        | support.addPropertyChangeListener(pcl);
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a propertyChangeListener from the PropertyChangeSupport.
	 * @param pcl
	 *        | The propertyChangeListener.
	 * @effect Removes a propertyChangeListener from the PropertyChangeSupport.
	 *        | support.addPropertyChangeListener(pcl);
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
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
	protected void setWidth(int width) {
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
	protected void setHeight(int height) {
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
	 *       | The variable showing whether or not the component is hidden.
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
	 * 		This object offers the methods that allow you to paint on the canvas.
	 */
	public abstract void paint(Graphics2D g);

	/**
	 * Checks whether the x and y coordinates are within the component.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @return True if the x and y coordinates are within the component; otherwise false.
	 *        | return x > getX() && x < getOffsetX() && y > getY() && y < getOffsetY();
	 */
	public boolean isWithinComponent(int x, int y) {
		return x > getX() && x < getOffsetX() && y > getY() && y < getOffsetY();
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
	public abstract void mouseClicked(int id, int x, int y, int clickCount);

	/**
	 * Handles the key pressed Event.
	 * 
	 * @param id
	 *        | The id of the key pressed event. 
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 */
	public abstract void keyPressed(int id, int keyCode, char keyChar);

	/**
	 * Handles the outside mouse Click.
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
	public void outsideClick(int id, int x, int y, int clickCount) {
	}

	/**
	 * This method fires a propertyChange Event.
	 * Because it used observer pattern a the observer will handle the firePropertyChange.
	 * 
	 * @param source
	 *        | the source Object of the property.
	 * @param propertyName
	 *        | The name of the property. We use ChangeEventTypes.
	 * @param oldValue
	 *        | The old value of the property.
	 * @param newValue
	 *        | The new value of the property.
	 */
	protected void propertyChanged(UUID source, ChangeEventType action, Object oldValue, Object newValue) {
		getSupport().firePropertyChange(new PropertyChangeEvent(source, action, oldValue, newValue));
	}

	/**
	 * This method fires a propertyChange Event.
	 * Because it used observer pattern a the observer will handle the firePropertyChange.
	 */
	protected void propertyChanged() {
		getSupport().firePropertyChange(new PropertyChangeEvent(ChangeEventType.REPAINT));
	}

	/**
	 * Handles the throw error of a component with the given ID. 
	 * @param id
	 *        | The id of which element an error is thrown.
	 */
	public void throwError(UUID id) {}

	/**
	 * Returns the PropertyChangeSupport.
	 */
	protected PropertyChangeSupport getSupport() {
		return support;
	}

	/**
	 * Sets the PropertyChangeSupport.
	 * 
	 * @param support
	 *        | the propertyChangeSupport variable, used in the observer design pattern.
	 * @post the support variable equals the given support.
	 *        |new.getSupport() == support.
	 */
	private void setSupport(PropertyChangeSupport support) {
		this.support = support;
	}

	/**
	 * Sets the x-coordinate depending of the change of x. How far it is moved from the previous y location.
	 * @param x the x-coordinate movement.
	 * @effect The x-coordinate is set relative to the previous x-coordinate value.
	 *         | this.setX(this.getX() + x)
	 */
	public void changeX(int x) {
		this.setX(this.getX() + x);
	}

	/**
	 * Sets the y-coordinate depending of the change of y. How far it is moved from the previous y location.
	 * @param y the y-coordinate movement.
	 * @effect The y-coordinate is set relative to the previous y-coordinate value.
	 *         | this.setY(this.getY() + y)
	 */
	public void changeY(int y) {
		this.setY(this.getY() + y);
	}

}
