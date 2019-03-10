package ui.model.viewmodes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.TextField;

public class TableDesignViewMode extends TableViewMode {
	private Container container;

	public TableDesignViewMode(UUID id, String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, tableName);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);
		this.createDesignTable(columnCharacteristics);
		this.addComponent(new TextField(50, 3, 200, 25, "Designing table: "+tableName, id));
	}
	
	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		container = new Container(0, 0, 600, 600);
		DesignTable table = new DesignTable(50, 30, 200, 200, getTableName(), this.getId(), columnCharacteristics);
		table.addPropertyChangeListener(this);
		this.addClickListener(table);
		getContainer().addComponent(table);
		this.addComponent(container);	
	}
	
	public void updateDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.removeAllClickAndKeyListeners();
		this.removeComponent(getContainer());
		this.createDesignTable(columnCharacteristics);
	}

	private Container getContainer() {
		return container;
	}


}
