package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ui.model.components.Component;

public abstract class TableViewMode extends ViewMode {

	private UUID id;
	private String tableName;
	private boolean paused = false;
	private List<Component> storedListeners;

	public TableViewMode(UUID id, String tableName) {
		super();
		this.setId(id);
		this.setTableName(tableName);
		this.setStoredListeners(new ArrayList<>());
	}

	public abstract void pauseViewMode(int columnIndex, UUID columnId);

	public abstract void resumeViewMode(int columnIndex, UUID columnId);

	public String getTableName() {
		return tableName;
	}

	private void setTableName(String tableName) {
		if (tableName == null) {
			throw new IllegalArgumentException("Cannot create a TableViewMode with a null tableName");
		}
		this.tableName = tableName;
	}

	public UUID getId() {
		return id;
	}

	private void setId(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot create a TableViewMode with a null ID");
		}
		this.id = id;
	}

	protected void removeAllClickListenersButOne(Component component) {
		List<Component> currentClickListeners = new ArrayList<>(getClickListeners());
		for (Component c : currentClickListeners) {
			if (!c.equals(component)) {
				this.getClickListeners().remove(c);
			}
		}
	}

	protected void removeAllKeyListenersButOne(Component component) {
		List<Component> currentKeyListeners = new ArrayList<>(getKeyListeners());
		for (Component c : currentKeyListeners) {
			if (!c.equals(component)) {
				this.getKeyListeners().remove(c);
			}
		}
	}

	protected void addAllKeyListeners(List<Component> keyListeners) {
		if (keyListeners == null) {
			throw new IllegalArgumentException("Cannot set a null list as a list of keyListeners");
		}

		for (Component c : keyListeners) {
			this.addKeyListener(c);
		}
	}

	protected void addAllClickListeners(List<Component> clickListeners) {
		if (clickListeners == null) {
			throw new IllegalArgumentException("Cannot set a null list as a list of clickListeners");
		}

		for (Component c : clickListeners) {
			this.addClickListener(c);
		}
	}

	protected List<Component> getStoredListeners() {
		return storedListeners;
	}

	protected void setStoredListeners(List<Component> listeners) {
		if (listeners == null) {
			throw new IllegalArgumentException("Cannot set null stored listeners");
		}
		this.storedListeners = listeners;
	}

	protected void clearStoredListeners() {
		this.setStoredListeners(new ArrayList<Component>());
	}

	protected void addStoredListener(Component listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Cannot add a null component as a stored listener.");
		}
		this.storedListeners.add(listener);
	}

	protected void addAllListeners() {
		this.removeAllClickAndKeyListeners();
		this.addAllClickListeners(getStoredListeners());
		this.addAllKeyListeners(getStoredListeners());
	}

	protected void removeAllComponents() {
		this.setComponents(new ArrayList<Component>());
	}

	public boolean isPaused() {
		return paused;
	}

	protected void setPaused(boolean paused) {
		this.paused = paused;
	}

}
