package ui.model.components;

import java.util.ArrayList;
import java.util.List;

/**
 *  A containerList is an abstract subType of a container list.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public abstract class ContainerList extends Container {

	/**
	 * Initialise a new ContainerList with the given variables.
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
	 *        | super(x, y, width, height, listItems)
	 */
	public ContainerList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}

	/**
	 * Initialise a new ContainerList with the given variables.
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
	public ContainerList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

}
