package ui.model.viewmodes;

import java.awt.Graphics;
import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;

public class TableDesignViewMode extends TableViewMode {
	private Container container;

	public TableDesignViewMode(UUID id, Map<UUID, Map<String, Object>> columnCharacteristics) {
		super(id);
		this.setType(ViewModeType.TABLEDESIGNVIEWMODE);
	}
	
	private Container getContainer() {
		return container;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawLine(50, 50, 200, 200);
	}

}
