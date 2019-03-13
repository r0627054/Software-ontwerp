package ui.model.viewmodes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Cell;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.TextField;

public class TableDesignViewMode extends TableViewMode {
	private Container container;

	public TableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, tableName);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);

		this.createDesignTable(columnCharacteristics);
	}

	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		container = new Container(0, 0, 600, 600);
		this.addComponent(new TextField(50, 5, 200, 25, "Designing table: " + getTableName(), getId()));

		DesignTable table = new DesignTable(50, 50, 200, 200, getTableName(), this.getId());
		List<Cell> cellList = table.createTable(columnCharacteristics);

		this.clearStoredListeners();
		for (Cell c : cellList) {
			this.addStoredListener(c);
			c.addPropertyChangeListener(this);
		}

		this.addStoredListener(table);
		table.addPropertyChangeListener(this);

		getContainer().addComponent(table);
		this.addComponent(getContainer());
		this.addAllListeners();
	}

	public void updateDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.removeAllClickAndKeyListeners();
		this.removeAllComponents();
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


	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
		Cell errorCell = this.getDesignTable().getCell(columnIndex, columnId);
		errorCell.setErrorWithNewValue(true, newValue);
	}

	public boolean hasASelectedCell() {
		for (Component comp : getStoredListeners()) {
			if (comp instanceof Cell) {
				Cell compCell = (Cell) comp;
				if (compCell.hasSelectedEditableTextField()) {
					return true;
				}
			}
		}
		return false;
	}
	
}
