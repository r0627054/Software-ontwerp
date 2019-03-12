package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class CheckBox extends EditableComponent {
	private final static int SIZE = 18;
	private final static int STROKE_WIDTH_BOX = 3;
	private final static int STROKE_WIDTH_CROSS = 2;

	private boolean checked;

	public CheckBox(boolean checked, UUID id) {
		this(0, 0, checked, id);
	}

	public CheckBox(int x, int y, boolean checked, UUID id) {
		this(x, y, SIZE, SIZE, checked, id);
	}

	public CheckBox(int x, int y, int width, int height, boolean checked, UUID id) {
		super(x, y, width, height, false, id);
		setChecked(checked);
	}

	private void setChecked(boolean checked) {
		this.checked = checked;
//		System.out.println("new Checked value! =" + checked);
		propertyChanged(this.getId(), ChangeEventType.VALUE.toString(), !this.isChecked(), this.isChecked());
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	public void paint(Graphics2D g) {
		int x1 = getX() + (getWidth() - SIZE) / 2;
		int y1 = getY() + (getHeight() - SIZE) / 2;
		g.setColor(Color.BLACK);

		if (this.isError()) {
			this.displayError((Graphics2D) g.create());
		}

		if (getWidth() != SIZE) {
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}

		g.setStroke(new BasicStroke(STROKE_WIDTH_BOX, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.drawRect(x1, y1, SIZE, SIZE);

		if (this.checked) {
			g.setStroke(new BasicStroke(STROKE_WIDTH_CROSS, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
			g.drawLine(x1, y1, x1 + SIZE, y1 + SIZE);
			g.drawLine(x1, y1 + SIZE, x1 + SIZE, y1);
		}
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED && isWithinComponent(x, y)) {
//			System.out.println("Checkbox clicked");
			this.setChecked(!isChecked());
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		// TODO Auto-generated method stub

	}

}
