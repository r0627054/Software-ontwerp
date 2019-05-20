package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *  A horizontalComponentList is subclass of containerList.
 *  It organises components Vertically.
 *  
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class VerticalComponentList extends Container {
	
	/**
	 * Variable storing the heigth of the padding.
	 */
	private int paddingHeight = 0;

	/**
	 * Initialise a new VerticalComponentList with the given variables.
	 * It calculates the dimensions and positions all the children automatically.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param listItems
	 *        The list of components.
	 * @param paddingHeight
	 *        Variable storing the height of the padding.
	 * @effect All the variables are set/calculated and the children are automatically positioned.
	 *        | super(x, y, 0, 0, listItems);
	 *        |	setHeight(this.getSumHeightFromChildren())
	 *        | setWidth(this.getMaxWidthFromChildren())
	 *        | this.setPaddingHeight(paddingHeight);
	 *        | positionChildren()
	 */
	public VerticalComponentList(int x, int y, List<Component> listItems, int paddingHeight) {
		super(x, y, 0, 0, listItems);

		setHeight(this.getSumHeightFromChildren());
		setWidth(this.getMaxWidthFromChildren());
		this.setPaddingHeight(paddingHeight);

		positionChildren();
	}

	/**
	 * Initialise a new VerticalComponentList with the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the textField.
	 * @param y
	 *        The y-coordinate of the textField.
	 * @param width
	 *        The width of the textField.
	 * @param height
	 *        The height of the textField.
	 * @effect All the variables are set. 
	 *        | super(x, y, width, height, new ArrayList<Component>())
	 */
	public VerticalComponentList(int x, int y, int width, int height) {
		super(x, y, width, height, new ArrayList<Component>());
	}

	/**
	 * Automatically positions all the components inside the VerticalComponentList.
	 */
	protected void positionChildren() {
		int tempY = getY();
		int width = getMaxWidthFromChildren();
		for (Component c : getComponentsList()) {
			c.setY(tempY);
			c.setX(this.getX());
			c.setWidth(width);
			tempY += c.getHeight() + paddingHeight;
		}
	}

	/**
	 * Draws the all the components of the VerticalComponentList.
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
		this.setHeight(getHeight() + c.getHeight());
		positionChildren();
	}

	/**
	 * Returns the offset x, this is the x-coordinate plus the maximum width of the children.
	 * 
	 * @return The x coordinate plus the maximum width of the children (components).
	 *         | this.getX() + getMaxWidthFromChildren()
	 */
	@Override
	public int getOffsetX() {
		return this.getX() + getMaxWidthFromChildren();
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
	
	/**
	 * Returns the height of the padding.
	 * @return the height of the padding.
	 */
	private int getPaddingHeight(){
		return this.paddingHeight;
	}
	
	/**
	 * Sets the height of the padding.
	 * @param paddingHeight The height of the padding.
	 * @post the height of the padding is set.
	 *       | new.getPaddingHeight() = paddingHeight
	 */
	private void setPaddingHeight(int paddingHeight) {
		this.paddingHeight = paddingHeight;
	}

}
