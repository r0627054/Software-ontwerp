package ui.model.components;

import java.awt.Graphics2D;
import java.util.UUID;

public class Cell extends Component {

	private Component component;
	private static int defaultHeight = 50;
	private static int defaultWidth = 100;

	public Cell(int x, int y, int width, int height, Object value, UUID id) {
		super(x, y, width, height, false);
		createComponent(value, id);
		setComponentCoordinates(x, y, width, height);
	}

	public Cell(int x, int y, Object value, UUID id) {
		this(x, y, defaultWidth, defaultHeight, value, id);
	}

	private void setComponentCoordinates(int x, int y, int width, int height) {
		component.setX(x);
		component.setY(y);
		component.setWidth(width);
		component.setHeight(height);

	}

	private void refreshComponentCoordinates() {
		setComponentCoordinates(getX(), getY(), getWidth(), getHeight());
	}

	private void createComponent(Object value, UUID id) {
		if (value instanceof String || value instanceof Integer) {
			this.setComponent(new EditableTextField((String) value.toString(), id));
		} else if (value instanceof Boolean) {
			this.setComponent(new CheckBox((boolean) value, id));
		} else {
			this.setComponent(new EditableTextField(value.toString(), id));
		}
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	@Override
	public void paint(Graphics2D g) {
		refreshComponentCoordinates();
		// System.out.println("Cell painted at:" + getComponent().getX() + "| y= " +
		// getComponent().getY());
		component.paint(g);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
	}

}
