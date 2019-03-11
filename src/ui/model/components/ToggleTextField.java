package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class ToggleTextField extends TextField {

	public ToggleTextField(int x, int y, int width, int height, String text, UUID id) {
		super(x, y, width, height, text, id);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			this.propertyChanged(this.getId(), null, getText(), null);
		}
	}

	@Override
	public void throwError(UUID id) {
		if (this.getId() == id) {
			super.throwError(id);
			this.setError(true);
		}
	}


}
