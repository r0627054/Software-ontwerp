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
		container = new Container(0, 0, 600, 600);


		List<Component> tablesList = new ArrayList<>();
		
		
		
		//LAST MODIFIED
		/*map.forEach(tableName -> {
			TextField textField = new EditableTextField(50, 50, 200, 30, tableName);
			textField.addPropertyChangeListener(this);
			this.addClickListener(textField);
			this.addKeyListener(textField);
			tablesList.add(textField);
		});*/
		
		for(Map.Entry<UUID, String> entry: map.entrySet()) {
			TextField textField = new EditableTextField(50, 50, 200, 30, entry.getValue(), entry.getKey());
			textField.addPropertyChangeListener(this);
			this.addClickListener(textField);
			this.addKeyListener(textField);
			tablesList.add(textField);
		}
		
				
		
		
		
		
		
		
		
		
		
		
		
		container.addComponent(new VerticalComponentList(0, 0, 600, 600, tablesList));
		
		

		this.addComponent(container);


	}


	@Override
	void registerWindowChangeListeners() {
	}

}
