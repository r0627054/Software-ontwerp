package ui.model.components;

import java.awt.Graphics2D;
import java.util.List;

public class HorizontalComponentList extends ContainerList {

	public HorizontalComponentList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		// TODO Auto-generated constructor stub
	}

	public HorizontalComponentList(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

}
