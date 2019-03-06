package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
//	private CheckBox cb;
//	private TextField textfield;
//	private TextField textfield1;
//	private TextField textfield2;
//	private TextField textfield3;
//	private VerticalComponentList col1;
//	private VerticalComponentList col2;
//	private VerticalComponentList col3;
	private Container container;

	public TablesViewMode(String name, List<String> tableNames) {
		super(name);
		container = new Container(0, 0, 600, 600);

//		textfield = new EditableTextField(50, 50, 200, 30, "test");
//		cb = new CheckBox(10, 10, true);
//		//register this as changelistener for the components
//		cb.addPropertyChangeListener(this);
//		textfield.addPropertyChangeListener(this);

		List<Component> tablesList = new ArrayList<>();
		
		tableNames.forEach(tableName -> {
			TextField textField = new EditableTextField(50, 50, 200, 30, tableName);
			textField.addPropertyChangeListener(this);
			this.addClickListener(textField);
			this.addKeyListener(textField);
			tablesList.add(textField);
		});
		container.addComponent(new VerticalComponentList(0, 0, 600, 600, tablesList));
		
		
//		textfield1 = new EditableTextField(50, 50, 250, 35, "testfadsf");
//		textfield2 = new EditableTextField(50, 50, 200, 30, "test123");
//		textfield3 = new EditableTextField(50, 50, 150, 25, "test44444");
//		testlist.add(textfield1);
//		testlist.add(textfield2);
//		testlist.add(textfield3);
//		testlist.forEach(c -> {
//			c.addPropertyChangeListener(this);
//			this.addClickListener(c);
//			this.addKeyListener(c);
//		});

//		container.addComponent(cb);
//		container.addComponent(textfield);
//		container.addComponent(new TableList(30, 100, 500, 500, testlist));
//		List<Component> colList = new ArrayList<>();
//		col1 = new VerticalComponentList(0, 200, 500, 500, testlist);
//		col2 = new VerticalComponentList(0, 200, 500, 500, testlist);
//		col3 = new VerticalComponentList(0, 200, 500, 500, testlist);
//		colList.add(col1);
//		colList.add(col2);
//		colList.add(col3);
//		container.addComponent(new VerticalComponentList(0, 0, 600, 600, testlist));
		this.addComponent(container);

//		this.registerAllClickListeners();
//		this.registerAllKeyListeners();

	}

	@Override
	void registerAllKeyListeners() {
		//this.addKeyListener(textfield);
	}

	@Override
	void registerAllClickListeners() {
//		this.addClickListener(cb);
//		this.addClickListener(textfield);

	}

	@Override
	void registerWindowChangeListeners() {
	}

}
