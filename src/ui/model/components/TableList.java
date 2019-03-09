package ui.model.components;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import controller.handlers.ChangeEventType;

public class TableList extends VerticalComponentList {

	public TableList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	public TableList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2 && y > this.getOffsetY()) {
				propertyChanged(this.toString(), ChangeEventType.CREATE_TABLE.getEventString(), null, null);
			}
		}
	}

}
