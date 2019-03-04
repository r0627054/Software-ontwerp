package ui.model.components;

import java.util.ArrayList;
import java.util.List;

public class TableList extends VerticalComponentList {

	public TableList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
		// TODO Auto-generated constructor stub
	}
	
	public TableList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(id, x, y, clickCount);
	}
	
}
