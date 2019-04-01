package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import controller.handlers.ChangeEventType;

public class Button extends TextField {

	/**
	 * The stroke width variable of the checkBox cross.
	 */
	private final static int STROKE_WIDTH_BORDER = 2;

	private boolean clicked;
	private ChangeEventType action;

	public Button(int x, int y, int width, int height, String text, ChangeEventType action) {
		super(x, y, width, height, text);
		setClicked(false);
		setAction(action);
	}

	@Override
	public void paint(Graphics2D g) {
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
		if (id == MouseEvent.MOUSE_PRESSED) {
			this.setClicked(true);
		} else if (id == MouseEvent.MOUSE_RELEASED && isClicked()) {
			this.setClicked(false);
		}
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		this.setClicked(false);
	}

	private boolean isClicked() {
		return clicked;
	}

	private void setClicked(boolean clicked) {
		this.clicked = clicked;
		this.propertyChanged();
	}

	private ChangeEventType getAction() {
		return action;
	}

	private void setAction(ChangeEventType action) {
		this.action = action;
	}

}
