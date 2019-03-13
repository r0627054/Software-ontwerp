package ui.model.components;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class TableList extends VerticalComponentList {

	public TableList(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void createTableList(Map<UUID, String> map, PropertyChangeListener pcl) {
		for (Map.Entry<UUID, String> entry : map.entrySet()) {
			TextField textField = new EditableTextField(0, 0, 200, 40, entry.getValue(), entry.getKey());
			textField.addPropertyChangeListener(pcl);
			this.addComponent(textField);
		}
		this.positionChildren();
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		for (Component c : getComponentsList()) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	private boolean hasCurrentError() {
		boolean hasCurrentlyError = false;
		for (Component c : getComponentsList()) {
			EditableTextField textField = (EditableTextField) c;
			if (textField.isError()) {
				hasCurrentlyError = true;
			}
		}
		return hasCurrentlyError;
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (!hasCurrentError()) {
			super.mouseClicked(id, x, y, clickCount);
		}
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (!hasCurrentError()) {
			if (id == MouseEvent.MOUSE_CLICKED) {
				if (clickCount == 2 && y > this.getOffsetY()) {
					propertyChanged(this.toString(), ChangeEventType.CREATE_TABLE.getEventString(), null, null);
				}

				for (Component c : getComponentsList()) {
					EditableTextField textField = (EditableTextField) c;
					c.outsideClick(id, x, y, clickCount);

					if (y > c.getY() && y < c.getOffsetY() && x < c.getX()) {
						textField.setSelectedForDelete(true);
					} else {
						textField.setSelectedForDelete(false);
					}
				}
			}
		}
	}

}
