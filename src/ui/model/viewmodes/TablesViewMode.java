package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.List;


import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;
import ui.model.components.TableList;
import ui.model.components.TextField;
import ui.model.components.VerticalComponentList;
import ui.model.view.View;

public class TablesViewMode extends ViewMode {
	private CheckBox cb;
	private TextField textfield;
	private TextField textfield1;
	private TextField textfield2;
	private TextField textfield3;
	private VerticalComponentList col1;
	private VerticalComponentList col2;
	private VerticalComponentList col3;
	private Container container;

	public TablesViewMode(String name, View view) {
		super(name, view);
		
		container = new Container(0, 0, 600, 600);
		
		textfield = new EditableTextField(50, 50, 200, 30, "test");
		cb = new CheckBox(10, 10, true);
		//register this as changelistener for the components
		cb.addPropertyChangeListener(this);
		textfield.addPropertyChangeListener(this);


		List<Component> testlist = new ArrayList<>();
		textfield1 = new TextField(50, 50, 200, 35, "testfadsf");
		textfield2 = new TextField(50, 50, 200, 30, "test123");
		textfield3 = new TextField(50, 50, 200, 25, "test44444");
		testlist.add(textfield1);
		testlist.add(textfield2);
		testlist.add(textfield3);

		container.addComponent(cb);
		container.addComponent(textfield);
		container.addComponent(new TableList(30, 100, 500, 500, testlist));
//		List<Component> colList = new ArrayList<>();
//		col1 = new VerticalComponentList(0, 200, 500, 500, testlist);
//		col2 = new VerticalComponentList(0, 200, 500, 500, testlist);
//		col3 = new VerticalComponentList(0, 200, 500, 500, testlist);
//		colList.add(col1);
//		colList.add(col2);
//		colList.add(col3);
//		container.addComponent(new HorizontalComponentList(0, 200, 500, 500, colList));
				
		this.addComponent(container);

		this.registerAllClickListeners();
		this.registerAllKeyListeners();

	}

	@Override
	void registerAllKeyListeners() {
		this.addKeyListener(textfield);
	}

	@Override
	void registerAllClickListeners() {
		this.addClickListener(cb);
		this.addClickListener(textfield);

	}

	@Override
	void registerWindowChangeListeners() {
	}

}
