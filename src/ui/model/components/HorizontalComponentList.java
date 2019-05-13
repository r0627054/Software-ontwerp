package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *  A horizontalComponentList is subclass of containerList.
 *  It organises components horizontally.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class HorizontalComponentList extends Container {

	/**
	 * Initialise a new HorizontalComponentList with the given variables.
	 * It calculates the dimensions and positions all the children automatically.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param listItems
	 *        The list of components.
	 * @effect All the variables are set/calculated and the children are automatically positioned.
	 *        | super(x, y, 0, 0, listItems)
     *        | this.setWidth(getSumWidthFromChildren())
	 *        |	this.setHeight(getMaxHeightFromChildren())
     *        | positionChildren()
	 */
	public HorizontalComponentList(int x, int y, List<Component> listItems) {
		super(x, y, 0, 0, listItems);

		this.setWidth(getSumWidthFromChildren());
		this.setHeight(getMaxHeightFromChildren());

		positionChildren();
	}

	/**
	 * Initialise a new HorizontalComponentList with the given x and y-coordinates.
	 *  By default it does not contain any components.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @effect The variables are set and the component is initialised with an empty component list.
	 *        |this(x, y, new ArrayList<Component>())
	 */
	public HorizontalComponentList(int x, int y) {
		this(x, y, new ArrayList<Component>());
	}

	/**
	 * Automatically positions all the components inside the HorizontalComponentList.
	 */
	protected void positionChildren() {
		int tempX = getX();
		for (Component c : getComponentsList()) {
			c.setX(tempX);
			c.setY(this.getY());
			tempX += c.getWidth();
		}
	}

	/**
	 * Draws the all the components of the horizontalComponentList.
	 * But first repositions all the components.
	 * 
	 * @param g 
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 * @effect All the children are positioned.
	 *         | for (Component c : getComponentsList()) {
	 * 		   |   c.paint((Graphics2D) g.create());
	 */
	@Override
	public void paint(Graphics2D g) {
		positionChildren();
		for (Component c : getComponentsList()) {
			c.paint((Graphics2D) g.create());
		}
	}

	/**
	 * Adds a component to the list of components.
	 *  And repositions all the children components.
	 *  
	 * @param c
	 *        | the component which will be added to the container.
	 * @effect the component is added and the children are repositioned.
	 *        | super.addComponent(c);
	 *        |	positionChildren();
	 */
	@Override
	public void addComponent(Component c) {
		super.addComponent(c);
		positionChildren();
	}

	/**
	 * Returns the offset y, this is the y-coordinate plus the maximum height of the children.
	 * 
	 * @return The y coordinate plus the maximum height of the children (components).
	 *         | this.getY() + getMaxHeightFromChildren()
	 */
	@Override
	public int getOffsetY() {
		return this.getY() + getMaxHeightFromChildren();
	}


	/**
	 * Sets the y-coordinate of the component.
	 *  And repositions the children of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @effect The y coordinate of the component is and the position of the children is updated.
	 *        | super.setY(y)
	 *        | positionChildren()
	 */
	@Override
	protected void setY(int y) {
		super.setY(y);

		if (this.getComponentsList() != null) {
			positionChildren();
		}
	}

	/**
	 * Sets the x-coordinate of the component.
	 *  And repositions the children of the component.
	 * @param x
	 *        The x-coordinate of the component.
	 * @effect The x coordinate of the component is and the position of the children is updated.
	 *        | super.setX(x)
	 *        | positionChildren()
	 */
	@Override
	protected void setX(int x) {
		super.setX(x);

		if (this.getComponentsList() != null) {
			positionChildren();
		}
	}

}
