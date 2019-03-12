package ui.model.viewmodes;

import java.util.Map;
import java.util.UUID;

import ui.model.components.Container;
import ui.model.components.TableList;
import ui.model.components.TextField;

public class TablesViewMode extends ViewMode {

	private Container container;

	public TablesViewMode(Map<UUID, String> map) {
		super();
		this.setType(ViewModeType.TABLESVIEWMODE);
		this.createTableList(map);
		this.addComponent(new TextField(50, 5, 200, 25, "TABLES LIST:"));
	}

	private void createTableList(Map<UUID, String> map) {
		container = new Container(0, 0, 600, 600);
		this.addComponent(getContainer());

		TableList tableList = new TableList(50, 50, 600, 600);
		tableList.createTableList(map, this);
		this.addClickListener(tableList);
		this.addKeyListener(tableList);
		tableList.addPropertyChangeListener(this);

		getContainer().addComponent(tableList);
	}

	public void updateTables(Map<UUID, String> map) {
		this.removeAllClickAndKeyListeners();
		this.removeComponent(getContainer());
		this.createTableList(map);
	}

	private Container getContainer() {
		return container;
	}

}
