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

	public TablesViewMode(String name, Map<UUID, String> map) {
		super(name);
		this.setType(ViewModeType.TABLESVIEWMODE);
		this.createTableList(map);
	}
	
	private void createTableList(Map<UUID, String> map) {
		container = new Container(0, 0, 600, 600);
		List<Component> tablesList = new ArrayList<>();		
		for(Map.Entry<UUID, String> entry: map.entrySet()) {
			TextField textField = new EditableTextField(50, 50, 200, 30, entry.getValue(), entry.getKey());
			textField.addPropertyChangeListener(this);
			this.addClickListener(textField);
			this.addKeyListener(textField);
			tablesList.add(textField);
		}
		TableList tableList = new TableList(0, 0, 600, 600,tablesList);
		tableList.addPropertyChangeListener(this);
		this.addClickListener(tableList);
		//container.addComponent(new VerticalComponentList(0, 0, 600, 600, tablesList));
		container.addComponent(tableList);
		this.addComponent(container);
	}
	
	
	
	
	@Override
	void registerWindowChangeListeners() {
	}

	public void updateTables(Map<UUID, String> map) {
		//System.out.println("update in tablesViewMode");
		this.removeComponent(container);
		this.createTableList(map);
	}
}
