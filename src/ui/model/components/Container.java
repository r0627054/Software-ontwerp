package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Container extends Component {

	private List<Component> components = new ArrayList<>();

	public Container(int x, int y, int width, int height) {
		this(x, y, width, height, false);
	}

	public Container(int x, int y, int width, int height, boolean hidden) {
		super(x, y, width, height, hidden);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
		
		for (Component c : components) {
			c.paint((Graphics2D) g.create());
		}
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

	public void addComponent(Component c) {
		if (c == null) {
			throw new IllegalArgumentException("Null component cannot be added to a container");
		}

		this.components.add(c);
	}

}
