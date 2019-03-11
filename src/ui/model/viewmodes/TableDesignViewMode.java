package ui.model.viewmodes;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import ui.model.components.Cell;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.TextField;

public class TableDesignViewMode extends TableViewMode {
	private Container container;
	private List<Component> storedListeners;

	public TableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, tableName);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);
		this.storedListeners = new ArrayList<>();

		this.createDesignTable(columnCharacteristics);
		this.addComponent(new TextField(50, 5, 200, 25, "Designing table: " + tableName, id));
	}

	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		container = new Container(0, 0, 600, 600);

		DesignTable table = new DesignTable(50, 50, 200, 200, getTableName(), this.getId());
		List<Cell> cellList = table.createTable(columnCharacteristics);
		for (Cell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(table);
		table.addPropertyChangeListener(this);
		// this.addComponent(table);

		getContainer().addComponent(table);
		this.addComponent(container);
		this.addAllListeners();
		
	}
	
	private void addAllListeners() {
		this.removeAllClickAndKeyListeners();
		this.addAllClickListeners(this.getStoredListeners());
		this.addAllKeyListeners(this.getStoredListeners());
	}


	public void updateDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.removeAllClickAndKeyListeners();
		this.removeComponent(getContainer());
		this.removeComponent(getDesignTable());
		this.setPaused(false);
		this.createDesignTable(columnCharacteristics);
	}

	private Container getContainer() {
		return container;
	}

	private DesignTable getDesignTable() {
		for (Component container : getComponents()) {
			if (container instanceof Container) {
				Container containerCasted = (Container) container; 
				for (Component c : containerCasted.getComponentsList()) {
					if (c instanceof DesignTable) {
						return (DesignTable) c;
					}
				}
			}
		}
		return null;
	}

	public void pauseViewMode(int columnIndex, UUID columnId) {
		this.setPaused(true);
		Cell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.removeAllClickListenersButOne(errorCell);
		this.removeAllKeyListenersButOne(errorCell);
		errorCell.setError(true);
	}

	public void unpauseViewMode(int columnIndex, UUID columnId) {
		this.setPaused(false);
		Cell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.addAllListeners();
		errorCell.setError(false);
	}

	private List<Component> getStoredListeners() {
		return storedListeners;
	}

	private void addStoredListener(Component listener) {
		if(listener == null) {
			throw new IllegalArgumentException("Cannot add a null component as a stored listener.");
		}
		this.storedListeners.add(listener);
	}

	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
		Cell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		errorCell.setErrorWithNewValue(true, newValue);
	}

}
