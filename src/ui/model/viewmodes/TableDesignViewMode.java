package ui.model.viewmodes;

import java.beans.PropertyChangeEvent;
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
	private boolean pauzed = false;

	public TableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, tableName);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);

		this.createDesignTable(columnCharacteristics);
		this.addComponent(new TextField(50, 5, 200, 25, "Designing table: " + tableName, id));
	}

	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		container = new Container(0, 0, 600, 600);

		DesignTable table = new DesignTable(50, 50, 200, 200, getTableName(), this.getId());
		List<Cell> cellList = table.createTable(columnCharacteristics);

		for (Cell c : cellList) {
			this.addClickListener(c);
			this.addKeyListener(c);
			c.addPropertyChangeListener(this);
		}

//		this.addKeyListener(table);
		table.addPropertyChangeListener(this);
		this.addComponent(table);
		this.addClickListener(table);

		getContainer().addComponent(table);
		this.addComponent(container);
	}

	public void updateDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.removeAllClickAndKeyListeners();
		this.removeComponent(getContainer());
		this.removeComponent(getDesignTable());
		this.setPauzed(false);
		this.createDesignTable(columnCharacteristics);
	}

	private Container getContainer() {
		return container;
	}

	public boolean isPauzed() {
		return pauzed;
	}

	private void setPauzed(boolean pauzed) {
		this.pauzed = pauzed;
	}

	private DesignTable getDesignTable() {
		for (Component c : getComponents()) {
			if (c instanceof DesignTable) {
				return (DesignTable) c;
			}
		}
		return null;
	}

	public void pauseViewMode(int columnIndex, UUID columnId) {
		this.setPauzed(true);
		Cell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.removeAllClickListenersButOne(errorCell);
		this.removeAllKeyListenersButOne(errorCell);
		errorCell.setError(true);
	}

	public void unpauseViewMode(int columnIndex, UUID columnId) {
		this.setPauzed(false);
		Cell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		this.addAllClickListenersDifferentFrom(errorCell);
		this.addAllKeyListenersDifferentFrom(errorCell);
		errorCell.setError(false);
	}
	
}
