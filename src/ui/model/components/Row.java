package ui.model.components;

import java.util.ArrayList;
import java.util.List;

public class Row extends VerticalComponentList {

	public Row(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}

	public Row(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

}
