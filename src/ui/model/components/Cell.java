package ui.model.components;

import java.awt.Graphics2D;

public class Cell extends Component {

	private Component component;
	private static int defaultHeight = 50;
	private static int defaultWidth = 100;

	public Cell(int x, int y, int width, int height, Object value) {
		super(x, y, width, height, false);
		createComponent(value);
	}

	public Cell(int x, int y, Object value) {
		this(x, y, defaultWidth, defaultHeight, value);
	}

	private void createComponent(Object value) {
		if (value instanceof String || value instanceof Integer) {
			this.setComponent(new EditableTextField((String) value.toString()));
		} else if (value instanceof Boolean) {
			this.setComponent(new CheckBox((boolean) value));
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
		System.out.println("Cell painted at:" + getX() + "| y= " + getY());
		component.paint(g);

	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

}
