package ui.model.viewmodes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ui.model.components.Component;

public abstract class ViewMode {
	private List<Component> components = new ArrayList<>();
	private String name;
	
	public ViewMode(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}

	private void setName(String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("The name of a view cannot be null or empty");
		}
		this.name = name;
	}

	public List<Component> getComponents() {
		return new ArrayList<>(components);
	}

	private void setComponents(List<Component> components) {
		if(components == null) {
			throw new IllegalArgumentException("The components of a view mode cannot be null");
		}
		this.components = components;
	}

	public boolean addComponent(Component component) {
		if(component == null) {
			throw new IllegalArgumentException("Cannot add null as component to the view mode.");
		}
		return components.add(component);
	}

	public void addComponent(int index, Component component) {
		if(component == null) {
			throw new IllegalArgumentException("Cannot add null as component to the view mode.");
		}
		components.add(index, component);
	}

	public boolean addAllComponents(Collection<? extends Component> c) {
		return components.addAll(c);
	}

	public Component removeComponent(int index) {
		return components.remove(index);
	}

	public boolean removeComponent(Component component) {
		return components.remove(component);
	}
	
	public void paint(Graphics g) {
		for(Component component: components) {
			component.paint(g);
		}
	}	
	
	
	
	
	
}
