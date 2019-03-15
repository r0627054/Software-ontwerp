package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class VerticalComponentList extends ContainerList {

	public VerticalComponentList(int x, int y, List<Component> listItems) {
		super(x, y, 0, 0, listItems);

		setHeight(this.getSumHeightFromChildren());
		setWidth(this.getMaxWidthFromChildren());

		positionChildren();
	}

	public VerticalComponentList(int x, int y, int width, int height) {
		super(x, y, width, height, new ArrayList<Component>());
	}

	protected void positionChildren() {
		int tempY = getY();
		int width = getMaxWidthFromChildren();
		for (Component c : getComponentsList()) {
			c.setY(tempY);
			c.setX(this.getX());
			c.setWidth(width);
			tempY += c.getHeight();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		positionChildren();
		for (Component c : getComponentsList()) {
			c.paint((Graphics2D) g.create());
		}
	}

	@Override
	public void addComponent(Component c) {
		super.addComponent(c);
		positionChildren();
	};

	@Override
	public int getOffsetX() {
		return this.getX() + getMaxWidthFromChildren();
	}
	
	@Override
	protected void setY(int y) {
		super.setY(y);

		if (this.getComponentsList() != null) {
			positionChildren();
		}
	}

	@Override
	protected void setX(int x) {
		super.setX(x);

		if (this.getComponentsList() != null) {
			positionChildren();
		}
	}

}
