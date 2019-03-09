package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.Table;

public class TableRowsViewMode extends ViewMode {
	private Container container;
	
	public TableRowsViewMode(String name, Map<Map<UUID, String>, Map<UUID, Object>> table) {
		super(name);
		this.setType(ViewModeType.TABLEROWSVIEWMODE);
		updateTable(table);
	}

	public void updateTable(Map<Map<UUID, String>, Map<UUID, Object>> tableInformation) {
		if(getContainer() != null && this.hasComponent(getContainer())) {
			this.removeComponent(this.getContainer());
		}
		container = new Container(0, 0, 600, 500);
		Table table = new Table(60, 60, 200, 200, tableInformation);
		this.addClickListener(table);
		container.addComponent(table);
		this.addComponent(container);
	}

	private Container getContainer() {
		return container;
	}

	@Override
	void registerWindowChangeListeners() {
		// TODO Auto-generated method stub

	}

}
