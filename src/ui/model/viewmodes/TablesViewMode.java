package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.List;

import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;
import ui.model.components.TextField;
import ui.model.view.View;

public class TablesViewMode extends ViewMode {
	private CheckBox cb;
	private TextField textfield;
	private TextField textfield1;
	private TextField textfield2;
	private TextField textfield3;
	private Container container;

	public TablesViewMode(String name, View view) {
		super(name, view);
		
		container = new Container(0, 0, 600, 600);
		
		cb = new CheckBox(10, 10, true);
		textfield = new EditableTextField(50, 50, 200, 30, "test");
		textfield1 = new EditableTextField(50, 50, 200, 30, "testfadsf");
		textfield2 = new EditableTextField(50, 50, 200, 30, "test123");
		textfield3 = new EditableTextField(50, 50, 200, 30, "test44444");
		container.addComponent(cb);
		container.addComponent(textfield);
		List<Component> testlist = new ArrayList<>();
		
		
		testlist.add(textfield1);
		testlist.add(textfield2);
		testlist.add(textfield3);

		this.addComponent(container);
		
		this.addComponent(new TableList(0, 100, 500, 500, testlist));
		
		
		
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
