package ui.model.viewmodes;

import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.Table;

public class TableRowsViewMode extends TableViewMode {
	private Container container;
	
	public TableRowsViewMode(UUID id, Map<Map<UUID, String>, Map<UUID, Object>> table) {
		super(id);
		this.setType(ViewModeType.TABLEROWSVIEWMODE);
		updateTable(table);
	}

	public void updateTable(Map<Map<UUID, String>, Map<UUID, Object>> tableInformation) {
		if(getContainer() != null && this.hasComponent(getContainer())) {
			this.removeComponent(this.getContainer());
			this.removeAllClickAndKeyListeners();
		}
		container = new Container(0, 0, 600, 600);
		Table table = new Table(50, 30, 200, 200, tableInformation);
		this.addClickListener(table);
		container.addComponent(table);
		this.addComponent(container);
	}

	private Container getContainer() {
		return container;
	}

}
