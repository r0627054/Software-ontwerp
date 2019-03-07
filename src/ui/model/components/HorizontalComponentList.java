package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class HorizontalComponentList extends ContainerList {

	public HorizontalComponentList(int x, int y, List<Component> listItems) {
		super(x, y, 0, 0, listItems);

		this.setWidth(getSumWidthFromChildren());
		this.setHeight(getMaxHeightFromChildren());

		positionChildren();
	}

	public HorizontalComponentList(int x, int y) {
		this(x, y, new ArrayList<Component>());
	}

	public HorizontalComponentList(int x, int y, int width, int height) {
		this(x, y, new ArrayList<Component>());
	}

	private void positionChildren() {
		int tempX = getX();
		for (Component c : getComponentsList()) {
			c.setX(tempX);
			c.setY(this.getY());
			tempX += c.getWidth();
//			System.out.println(c.toString() + "Set x & y to: x=" + c.getX() + "| y= " + c.getY() + "| width=" + c.getWidth()
//					+ "|height =" + c.getHeight());
		}
	}

	@Override
	public void paint(Graphics2D g) {
		positionChildren();
		//System.out.println("HorizontalComponentList paint method");
		for (Component c : getComponentsList()) {
			c.paint((Graphics2D) g.create());
		}
	}

	@Override
	public void addComponent(Component c) {
		super.addComponent(c);
		positionChildren();
	};
}
