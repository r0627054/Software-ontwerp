package ui.model.viewmodes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import ui.model.components.Component;

public abstract class ViewMode implements PropertyChangeListener {
	private List<Component> components = new ArrayList<>();
	private List<Component> clickListeners = new ArrayList<>();
	private List<Component> keyListeners = new ArrayList<>();

	private PropertyChangeSupport support;

	private ViewModeType type;

	public ViewMode() {
		support = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	public List<Component> getComponents() {
		return new ArrayList<>(components);
	}

	protected void setComponents(List<Component> components) {
		if (components == null) {
			throw new IllegalArgumentException("The components of a view mode cannot be null");
		}
		this.components = components;
	}

	public boolean addComponent(Component component) {
		if (component == null) {
			throw new IllegalArgumentException("Cannot add null as component to the view mode.");
		}
		return components.add(component);
	}

	public boolean addAllComponents(Collection<? extends Component> c) {
		return components.addAll(c);
	}

	public void removeComponent(int index) {
		components.remove(index);
	}

	public void removeComponent(Component component) {
		components.remove(component);
	}

	public void paint(Graphics g) {
		for (Component component : components) {
			component.paint((Graphics2D) g.create());
		}
	}

	protected void addClickListener(Component c) {
		if (c == null)
			throw new IllegalArgumentException("A new click listener cannot be null");
		if (!this.getClickListeners().contains(c))
			this.clickListeners.add(c);
	}

	protected void addKeyListener(Component c) {
		if (c == null)
			throw new IllegalArgumentException("A new key listener cannot be null");
		if (!this.getKeyListeners().contains(c))
			this.keyListeners.add(c);
	}

	// We create a copy to make sure we're not editing the list while we are still
	// looping through it
	public void mouseClicked(int id, int x, int y, int clickCount) {
		List<Component> currentClickListeners = new ArrayList<>(getClickListeners());
		for (Component c : currentClickListeners) {
			if (c.isWithinComponent(x, y)) {
				c.mouseClicked(id, x, y, clickCount);
			} else {
				c.outsideClick(id, x, y, clickCount);
			}
		}
	}

	// We create a copy to make sure we're not editing the list while we are still
	// looping through it
	public void keyPressed(int id, int keyCode, char keyChar) {
		List<Component> currentKeyListeners = new ArrayList<>(getKeyListeners());
		for (Component c : currentKeyListeners) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	public ViewModeType getViewModeType() {
		return type;
	}

	public boolean hasComponent(Component component) {
		return this.getComponents().contains(component);
	}

	protected void setType(ViewModeType type) {
		if (type == null) {
			throw new IllegalArgumentException("ViewModeType cannot be null in a viewmode.");
		}
		this.type = type;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.support.firePropertyChange(evt);
	}

	public void throwError(UUID id) {
		for (Component c : getComponents()) {
			c.throwError(id);
		}
	}

	protected void removeAllClickAndKeyListeners() {
		this.clickListeners.clear();
		this.keyListeners.clear();
	}

	protected List<Component> getClickListeners() {
		return this.clickListeners;
	}

	protected List<Component> getKeyListeners() {
		return this.keyListeners;
	}

}
