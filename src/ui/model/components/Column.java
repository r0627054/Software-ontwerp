package ui.model.components;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Column extends VerticalComponentList {
	

	public Column(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}

	public Column(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	public Column(int x, int y, List<Component> listItems) {
		this(x, y, 0, 0, listItems);

		// TODO: Add columnHeader + add columnHeader height to height
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {

		if (id == MouseEvent.MOUSE_CLICKED) {
			System.out.println("Column is geklikt hehe");
			System.out.println("column clicked with x=" + getX() + "& y= " + getY() + "| width= " + getWidth()
					+ "|height= " + getHeight());
		}
		for (Component c : getComponentsList()) {
			if (c.isWithinComponent(x, y))
				c.mouseClicked(id, x, y, clickCount);
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
	}

}
