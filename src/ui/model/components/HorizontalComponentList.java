package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class HorizontalComponentList extends ContainerList {

	public HorizontalComponentList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		positionChildren();
	}

	public HorizontalComponentList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	private void positionChildren() {
		int tempX = getX();
		for (Component c : getComponentsList()) {
			c.setX(tempX);
			c.setY(this.getY());
			tempX += c.getWidth();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		System.out.println("\nPainting horizontalCompList| coords = " + getX() + " & " + getY());
		for (Component c : getComponentsList()) {
			c.paint((Graphics2D) g.create());
		}
	}

}
