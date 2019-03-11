package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Cell;
import ui.model.components.Component;

public abstract class TableViewMode extends ViewMode {

	private UUID id;
	private String tableName;
	private boolean paused = false;

	public TableViewMode(UUID id, String tableName) {
		super();
		this.setId(id);
		this.setTableName(tableName);
	}

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
