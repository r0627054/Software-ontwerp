package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;

public class ColumnHeader extends TextField {

	public ColumnHeader(int x, int y, int width, int height, String text) {
		super(x, y, width, height, false, text);
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.PINK);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		super.paint((Graphics2D) g.create());
	}

}
