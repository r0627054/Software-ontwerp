package controller.observer;

import java.util.ArrayList;
import java.util.List;

public class PropertyChangeSupport {

	List<PropertyChangeListener> listeners;
	
	public PropertyChangeSupport() {
		listeners = new ArrayList<>();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if(listener == null) {
			throw new IllegalArgumentException("Cannot add a null listener");
		}
		this.getListeners().add(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if(listener == null) {
			throw new IllegalArgumentException("Cannot remove a null listener");
		}
		this.getListeners().remove(listener);
	}

	private List<PropertyChangeListener> getListeners() {
		return listeners;
	}
	
	public void firePropertyChange(PropertyChangeEvent evt) {
		for(PropertyChangeListener listener: getListeners()) {
			listener.propertyChange(evt);
		}
	}
	
	public int size() {
		return this.getListeners().size();
	}
}
