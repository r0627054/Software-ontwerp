package ui.model.components;

import java.awt.Graphics2D;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import ui.model.viewmodes.ViewMode;

public class TitleBar extends Container implements PropertyChangeListener {

	public static final int BUTTON_WIDTH = 50;

	public TitleBar(int x, int y, int width, int height, String titleText) {
		super(x, y, width, height);

		int borderMargin = ViewMode.DRAG_BORDER_SIZE;

		addComponent(new TextField(ViewMode.CONTENT_OFFSET_X, y, width, height, titleText));

		Button closeButton = new Button(width - BUTTON_WIDTH - borderMargin, y + borderMargin, BUTTON_WIDTH,
				height - borderMargin, "Close", ChangeEventType.CLOSE_SUBWINDOW);
		addComponent(closeButton);
		closeButton.addPropertyChangeListener(this);
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawLine(getX(), getY() + getHeight(), getOffsetX(), getY() + getHeight());
		super.paint(g);
	}

	public void changeWidth(int change) {
		this.setWidth(getWidth() + change);

		for (Component c : getComponentsList()) {
			if (c instanceof Button) {
				((Button) c).changeX(change);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getSupport().firePropertyChange(evt);
	}
}
