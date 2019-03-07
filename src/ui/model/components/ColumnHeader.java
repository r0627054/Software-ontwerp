package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;

public class ColumnHeader extends TextField {

	public ColumnHeader(int x, int y, int width, int height, String text, UUID id) {
		super(x, y, width, height, false, text,id);
	}
	//TODO replace with default values
	public ColumnHeader(String text, UUID id) {
		super(0, 0, 100, 50, text,id);
	}
	
	@Override
	public void paint(Graphics2D g) {
//		System.out.println("header: " + getX() + " " + getY());
		g.setColor(Color.PINK);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		super.paint((Graphics2D) g.create());
	}

}
