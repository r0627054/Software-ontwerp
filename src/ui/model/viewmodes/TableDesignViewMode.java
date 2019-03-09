package ui.model.viewmodes;

import java.util.UUID;

import ui.model.components.Container;

public class TableDesignViewMode extends TableViewMode {
	private Container container;

	public TableDesignViewMode(String name, UUID id) {
		super(name, id);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);
	}
	
	private Container getContainer() {
		return container;
	}

}
