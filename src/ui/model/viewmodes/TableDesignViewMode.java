package ui.model.viewmodes;

import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.DesignTable;

public class TableDesignViewMode extends TableViewMode {
	private Container container;

	public TableDesignViewMode(UUID id, String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(id, tableName);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);
		this.createDesignTable(columnCharacteristics);
	}
	
	private void createDesignTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		container = new Container(0, 0, 600, 600);
		DesignTable table = new DesignTable(50, 30, getTableName(), columnCharacteristics);
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
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

}
