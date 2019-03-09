package ui.model.components;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controller.handlers.ChangeEventType;

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
	
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if(id == MouseEvent.MOUSE_CLICKED && clickCount == 2) {
			if(y > this.getSumHeightFromChildren()+ this.getY()){
				propertyChanged(this.toString(), ChangeEventType.CREATE_TABLE.getEventString(), null, null);
			}
		}
	}
	
}
