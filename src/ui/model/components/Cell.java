package ui.model.components;

import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class Cell extends EditableComponent implements PropertyChangeListener {

	private Component component;
	private ChangeEventType actionType = ChangeEventType.CHECKED;

	private static int defaultHeight = 50;
	private static int defaultWidth = 100;

	public Cell(int x, int y, int width, int height, Object value, UUID id) {
		super(x, y, width, height, false, id);
		createComponent(value, id);
		setComponentCoordinates(x, y, width, height);
	}

	public Cell(int x, int y, Component cellComponent, UUID id) {
		super(x, y, defaultWidth, defaultHeight, false, id);
		this.setComponent(cellComponent);
		setComponentCoordinates(x, y, defaultWidth, defaultHeight);
	}

	public Cell(int x, int y, Object value, UUID id) {
		this(x, y, defaultWidth, defaultHeight, value, id);
	}

	private void setComponentCoordinates(int x, int y, int width, int height) {
		Component c = getComponent();
		c.setX(x);
		c.setY(y);
		c.setWidth(width);
		c.setHeight(height);
	}

	private void refreshComponentCoordinates() {
		setComponentCoordinates(getX(), getY(), getWidth(), getHeight());
	}

	private void createComponent(Object value, UUID id) {
		Component component;

		if (value instanceof Boolean) {
			component = new CheckBox((boolean) value, id);
		} else if (value != null) {
			component = new EditableTextField(value.toString(), id);
		} else {
			component = new EditableTextField("", id);
		}
		this.setComponent(component);
	}

	@Override
	public void paint(Graphics2D g) {
		refreshComponentCoordinates();
		// System.out.println("Cell painted at:" + getComponent().getX() + "| y= " +
		// getComponent().getY());
		getComponent().paint(g);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		getComponent().mouseClicked(id, x, y, clickCount);
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		getComponent().outsideClick(id, x, y, clickCount);
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		getComponent().keyPressed(id, keyCode, keyChar);
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		if (component == null) {
			throw new IllegalArgumentException("Cannot add a null component to a cell.");
		}
		this.component = component;
		component.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!ChangeEventType.REPAINT.getEventString().equals(evt.getPropertyName())) {
			this.getSupport().firePropertyChange(new PropertyChangeEvent(getId(), this.actionType.getEventString(),
					evt.getOldValue(), evt.getNewValue()));
		} else {
			this.getSupport().firePropertyChange(evt);
		}
	}

	public ChangeEventType getActionType() {
		return actionType;
	}

	public void setActionType(ChangeEventType actionType) {
		if (actionType == null) {
			throw new IllegalArgumentException("Cannot set a null action type to a cell");
		}
		this.actionType = actionType;
	}

	public void setError(boolean error) {
		if (getComponent() instanceof EditableTextField) {
			EditableTextField editableTextField = (EditableTextField) getComponent();
			editableTextField.setError(error);
		}
	}

	public void setErrorWithNewValue(boolean b, Object newValue) {
		if (getComponent() instanceof ToggleTextField) {
			ToggleTextField toggleTextField = (ToggleTextField) getComponent();
			toggleTextField.setError(b);
			toggleTextField.setText(newValue.toString());
		} // TODO Add checkbox

	}

}
