package ui.model.components;

import java.util.ArrayList;
import java.util.List;

public class Column extends VerticalComponentList {

	public Column(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}

	public Column(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		System.out.println();
		for (Component c : getComponentsList()) {
			if (c.isWithinComponent(x, y))
				c.mouseClicked(id, x, y, clickCount);
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

}
