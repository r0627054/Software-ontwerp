package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class ContainerList extends Container {

	public ContainerList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}
	
	public ContainerList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
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
