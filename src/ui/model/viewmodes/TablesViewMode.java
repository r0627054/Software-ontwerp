package ui.model.viewmodes;

import ui.model.components.CheckBox;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.TextField;
import ui.model.view.View;

public class TablesViewMode extends ViewMode {
	private CheckBox cb;
	private TextField textfield;
	private Container container;

	public TablesViewMode(String name, View view) {
		super(name, view);
		
		container = new Container(0, 0, 600, 600, this);
		
		cb = new CheckBox(10, 10, true);
		textfield = new EditableTextField(50, 50, 200, 30, "test");

		container.addComponent(cb);
		container.addComponent(textfield);
		
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
