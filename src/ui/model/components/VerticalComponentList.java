package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class VerticalComponentList extends ContainerList {

	private int currentX;
	private int currentY;

	public VerticalComponentList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		this.currentX = x;
		this.currentY = y;
		// TODO Auto-generated constructor stub
	}

	public VerticalComponentList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		for (Component c : getComponentsList()) {
			c.setX(this.currentX + c.getY());
			c.setY(this.currentY + c.getY());
			c.paint(g);
			this.currentY += c.getHeight();
			System.out.println(currentX);
		}
	}

}
