package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class VerticalComponentList extends ContainerList {

	private int currentY;

	public VerticalComponentList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		setCurrentY(y);
	}

	public VerticalComponentList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
		setCurrentY(y);
	}

	private void setCurrentY(int y) {
		if (y < 0)
			throw new IllegalArgumentException("Cannot have negative position when adding a VerticalComponentList");
		this.currentY = y;
	}

	@Override
	public void paint(Graphics2D g) {
		for (Component c : getComponentsList()) {
			c.setY(this.currentY + c.getY());
			c.setX(this.getX());
			c.paint((Graphics2D) g.create());
			this.currentY += c.getHeight();
		}
		setCurrentY(getY());
	}

}
