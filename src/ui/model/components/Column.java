package ui.model.components;

import java.awt.Graphics2D;
import java.util.List;

public class Column extends Component {

	public Column(int x, int y, int width, int height) {
		super(x, y, width, height, false);
	}

	public Column(int x, int y, int width, int height, List<Object> values) {
		this(x, y, width, height);
	}

	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub

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
