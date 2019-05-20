package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A container itself is a component and contains a list of Components.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Container extends Component {

	/**
	 * The variable storing the components.
	 */
	private List<Component> components = new ArrayList<>();

	/**
	 * Initialise a new Container with the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param listItems
	 *        The list of components.
	 * @effect All variables are set. 
	 *        |super(x, y, width, height, false)
	 *     	  |this.setListItems(listItems)
	 */
	public Container(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, false);
		this.setListItems(listItems);
	}

	/**
	 * Initialise a new Container with the given variables.
	 * The list of components is initiated with an empty ArrayList<>()
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @effect All the variables are set.
	 *        | this(x, y, width, height, new ArrayList<Component>())
	 */
	public Container(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	/**
	 * Sets the list of items.
	 * 
	 * @param listItems
	 *        | the new list of items the container will have.
	 * @post the list of components is equal to the listItems parameter.
	 *        | this.components = listItems
	 * @throws IllegalArgumentException if listItems equals null
	 *        | listItems == null
	 */
	private void setListItems(List<Component> listItems) {
		if (listItems == null) {
			throw new IllegalArgumentException("ListItems can not be null");
		}
		this.components = listItems;

	}

	/**
	 * Returns the list of components.
	 */
	public List<Component> getComponentsList() {
		return this.components;
	}

	/**
	 * Calculates the maximum height of the children.
	 * @return The maximum height of the children.
	 *         | getComponentsList().stream().mapToInt(c -> c.getHeight()).max().orElse(0)
	 */
	public int getMaxHeightFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getHeight()).max().orElse(0);
	}

	/**
	 * Calculates the maximum width of the children.
	 * @return The maximum width of the children.
	 *         | getComponentsList().stream().mapToInt(c -> c.getWidth()).max().orElse(0)
	 */
	public int getMaxWidthFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getWidth()).max().orElse(0);
	}

	/**
	 * Calculates the sum of the widths of the children.
	 * @return The sum of the widths of the children.
	 *         | getComponentsList().stream().mapToInt(c -> c.getWidth()).sum()
	 */
	public int getSumWidthFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getWidth()).sum();
	}

	/**
	 * Calculates the sum of the heights of the children.
	 * @return The sum of the heights of the children.
	 *         | getComponentsList().stream().mapToInt(c -> c.getHeight()).sum()
	 */
	public int getSumHeightFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getHeight()).sum();
	}

	/**
	 * Draws the container and draws all the components of the container.
	 * @param g 
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		for (Component c : getComponentsList()) {
			c.paint((Graphics2D) g.create());
		}
	}

	/**
	 * Delegates the mouseClick event to the components of the container.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The components are called an told whether there was a click inside or outside.
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		for (Component c : getComponentsList()) {
			if (c.isWithinComponent(x, y)) {
				c.mouseClicked(id, x, y, clickCount);
			} else {
				c.outsideClick(id, x, y, clickCount);
			}
		}
	}

	/**
	 * Delegates the keyPressed event to the components of the container.
	 * 
	 * @param id
	 *        | The id of the key pressed event. 
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 * @effect The components are called an told whether there was a keyPressed.
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		for (Component c : getComponentsList()) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	/**
	 * Adds a component to the list of components.
	 * 
	 * @param c
	 *        | the component which will be added to the container.
	 * @throws IllegalArgumentException if the component equals null.
	 *        | c == null 
	 */
	public void addComponent(Component c) {
		if (c == null) {
			throw new IllegalArgumentException("Null component cannot be added to a container");
		}
		this.components.add(c);
	}

	/**
	 * The throwError method is called on the components of the container.
	 * @param id
	 *        | The id of which element an error is thrown.
	 */
	@Override
	public void throwError(UUID id) {
		super.throwError(id);
		for (Component c : getComponentsList()) {
			c.throwError(id);
		}
	}

	/**
	 * Returns the offset x, this is the x-coordinate plus sum of the width of the children.
	 * 
	 * @return The x coordinate plus the sum of the width of the children (components).
	 *         | this.getX() + this.getSumWidthFromChildren()
	 */
	@Override
	public int getOffsetX() {
		return this.getX() + this.getSumWidthFromChildren();
	}

	/**
	 * Returns the offset y, this is the y-coordinate plus sum of the height of the children.
	 * 
	 * @return The y coordinate plus the sum of the heights of the children (components).
	 *         | this.getY() + this.getSumHeightFromChildren()
	 */
	@Override
	public int getOffsetY() {
		return this.getY() + this.getSumHeightFromChildren();
	}

	/**
	 * Calls the outside click method on the children in the container.
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
		for (Component c : getComponentsList()) {
			c.outsideClick(id, x, y, clickCount);
		}
	}

	/**
	 * Calls the super method and changes the y of all the components in the componentList.
	 * @param y the y-coordinate movement.
	 * @effect The super constructor is called an all the components in the container are moved.
	 *         | super.changeY(y);
	 *         | for(Component c: getComponentsList())
	 *         |		c.changeY(y);
	 */
	@Override
	public void changeY(int y) {
		super.changeY(y);
		
		for(Component c: getComponentsList()) {
			c.changeY(y);
		}
	}

	/**
	 * Calls the super method and changes the x of all the components in the componentList.
	 * @param x the x-coordinate movement.
	 * @effect The super constructor is called an all the components in the container are moved.
	 *         | super.changeX(x);
	 *         | for(Component c: getComponentsList())
	 *         |		c.changeX(x);
	 */
	@Override
	public void changeX(int x) {
		super.changeX(x);
		
		for(Component c: getComponentsList()) {
			c.changeX(x);
		}
	}

}
