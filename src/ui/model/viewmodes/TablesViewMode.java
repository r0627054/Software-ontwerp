package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;
import ui.model.components.Table;
import ui.model.components.TableList;
import ui.model.components.TextField;
import ui.model.components.VerticalComponentList;
import ui.model.view.View;

public class TablesViewMode extends ViewMode {

	private Container container;

	public TablesViewMode(Map<UUID, String> map) {
		super();
		this.setType(ViewModeType.TABLESVIEWMODE);
		this.createTableList(map);
	}
	
	private void createTableList(Map<UUID, String> map) {
		container = new Container(0, 0, 600, 600);
		this.addComponent(getContainer());

		List<Component> tablesList = new ArrayList<>();		
		
		for(Map.Entry<UUID, String> entry: map.entrySet()) {
			TextField textField = new EditableTextField(50, 50, 200, 30, entry.getValue(), entry.getKey());
			textField.addPropertyChangeListener(this);
			this.addClickListener(textField);
			this.addKeyListener(textField);
			tablesList.add(textField);
		}
		TableList tableList = new TableList(50, 30, 600, 600,tablesList);
		tableList.addPropertyChangeListener(this);
		getContainer().addComponent(tableList);
		this.addClickListener(tableList);
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
