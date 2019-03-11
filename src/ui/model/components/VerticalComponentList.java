package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class VerticalComponentList extends ContainerList {

	public VerticalComponentList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		//System.out.println("Constructor verticalComponentList x:" + x + "|getY" + y);
		
		setHeight(this.getSumHeightFromChildren());
		setWidth(this.getMaxWidthFromChildren());

		positionChildren();
	}

	public VerticalComponentList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	protected void positionChildren() {
		//System.out.println(this.getX() + " " + this.getY());
		int tempY = getY();
		int width = getMaxWidthFromChildren();
		for (Component c : getComponentsList()) {
			c.setY(tempY);
			c.setX(this.getX());
			c.setWidth(width);
			tempY += c.getHeight();
//			System.out.println(c.toString() + "Set x & y to: x=" + c.getX() + "| y= " + c.getY() + "| width="
//					+ c.getWidth() + "|height =" + c.getHeight());
		}
	}

	private void positionSelfCenter() {
		int selfWidth = this.getWidth();
		int selfHeight = this.getHeight();
		int childrenWidth = getMaxWidthFromChildren();
		int childrenHeight = getComponentsList().stream().mapToInt(c -> c.getHeight()).sum();
		this.setX((selfWidth - childrenWidth) / 2);
		this.setY((selfHeight - childrenHeight) / 2);
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
	}

}
