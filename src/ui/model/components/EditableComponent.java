package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.UUID;

public abstract class EditableComponent extends Component {
	private final static int ERROR_RECT_SIZE = 2;

	private final UUID id;
	private boolean error;

	public EditableComponent(int width, int height, UUID id) {
		super(width, height);
		this.id = id;
	}

	public EditableComponent(int width, int height, boolean hidden, UUID id) {
		super(width, height, hidden);
		this.id = id;
	}

	public EditableComponent(int x, int y, int width, int height, boolean hidden, UUID id) {
		super(x, y, width, height, hidden);
		this.id = id;
	}

	protected UUID getId() {
		return id;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	protected void displayError(Graphics2D g) {
		g.setStroke(new BasicStroke(ERROR_RECT_SIZE, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		g.setColor(Color.RED);

		// -1 & +/- Error_rect_size zodat rode kader niet overlapped met zwarte kader
		// van TextField
		g.drawRect(getX() + ERROR_RECT_SIZE, getY() + ERROR_RECT_SIZE, getWidth() - ERROR_RECT_SIZE - 1,
				getHeight() - ERROR_RECT_SIZE - 1);
	}

}
