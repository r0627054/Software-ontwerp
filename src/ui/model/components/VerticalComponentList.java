package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class VerticalComponentList extends ContainerList {

//	private int currentY;

	public VerticalComponentList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		//setCurrentY(y);
	}

	public VerticalComponentList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
		//setCurrentY(y);
	}

//	private void setCurrentY(int y) {
//		if (y < 0)
//			throw new IllegalArgumentException("Cannot have negative position when adding a VerticalComponentList");
//		this.currentY = y;
//	}
//	
	private void positionChildren() {
		int tempY = getY();
		int width = getMaxWidthFromChildren();
		for (Component c : getComponentsList()) {
			c.setY(tempY);
			c.setX(this.getX());
			c.setWidth(width);
			tempY += c.getHeight();
		}
	}
	
	private void positionSelfCenter() {
		int selfWidth = this.getWidth();
		int selfHeight = this.getHeight();
		int childrenWidth = getMaxWidthFromChildren();
		int childrenHeight = getComponentsList().stream().mapToInt(c -> c.getHeight()).sum();
		this.setX((selfWidth-childrenWidth)/2);
		this.setY((selfHeight-childrenHeight)/2);
		
	}

	@Override
	public void paint(Graphics2D g) {
		positionChildren();
		positionSelfCenter();
		for (Component c : getComponentsList()) {
			c.paint((Graphics2D) g.create());
		}
		//setCurrentY(getY());
	}


}
