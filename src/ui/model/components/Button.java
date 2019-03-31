package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Button extends TextField {

	/**
	 * The stroke width variable of the checkBox cross.
	 */
	private final static int STROKE_WIDTH_BORDER = 2;

	private boolean clicked;
	private String text;

	public Button(int x, int y, int width, int height, String text) {
		super(x, y, width, height, text);
		setClicked(false);
	}

	@Override
	public void paint(Graphics2D g) {
		System.out.println("painting");
		if (isClicked()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(226, 226, 226));
		}

		g.fillRect(getX(), getY(), getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(STROKE_WIDTH_BORDER, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		super.paint(g);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED || id == MouseEvent.MOUSE_DRAGGED || id == MouseEvent.MOUSE_PRESSED) {
			this.setClicked(true);
		} else if (id == MouseEvent.MOUSE_RELEASED && isClicked()) {
			this.setClicked(false);
			System.out.println("!! Button action");
		}
		this.propertyChanged();
	}
	
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		this.setClicked(false);
	}

	private boolean isClicked() {
		return clicked;
	}

	private void setClicked(boolean clicked) {
		System.out.println("set to :" + clicked);
		this.clicked = clicked;
	}

}
