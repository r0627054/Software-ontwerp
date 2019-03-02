package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

public class CheckBox extends Component {
	private final static int SIZE = 18;
	private final static int STROKE_WIDTH_BOX = 3;
	private final static int STROKE_WIDTH_CROSS = 2;

	private boolean checked;

	public CheckBox(int x, int y, boolean checked) {
		super(x, y, SIZE, SIZE, false);
		this.setChecked(checked);
	}

	public CheckBox(int x, int y, boolean hidden, boolean checked) {
		super(x, y, SIZE, SIZE, hidden);
		this.setChecked(checked);
	}

	private void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public void paint(Graphics2D g) {
		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(STROKE_WIDTH_BOX, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.drawRect(getX(), getY(), SIZE, SIZE);
		if (this.checked) {
			g.setStroke(new BasicStroke(STROKE_WIDTH_CROSS, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
			g.drawLine(getX(), getY(), getOffsetX(), getOffsetY());
			g.drawLine(getX(), getOffsetY(), getOffsetX(), getY());
		}
		g.setStroke(oldStroke);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED && x >= getX() && x <= getOffsetX() && y >= getY() && y <= getOffsetY()) {
			this.checked = !this.checked;
			System.out.println("CheckBox state: " + this.checked);
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

}
