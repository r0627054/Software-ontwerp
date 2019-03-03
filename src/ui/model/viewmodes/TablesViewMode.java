package ui.model.viewmodes;

import ui.model.components.CheckBox;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.TextField;

public class TablesViewMode extends ViewMode {
	private CheckBox cb;
	private TextField textfield;
	private Container container;

	public TablesViewMode(String name) {
		super(name);
		
		container = new Container(0, 0, 600, 600);
		
		cb = new CheckBox(10, 10, true);
		textfield = new EditableTextField(50, 50, 200, 30, "test");

		container.addComponent(cb);
		container.addComponent(textfield);
		
		this.addComponent(container);
		this.registerAllClickListeners();

	}

	@Override
	void registerAllKeyListeners() {

	}

	@Override
	void registerAllClickListeners() {
		this.addClickListener(cb);
		this.addClickListener(textfield);

	}

	@Override
	void registerWindowChangeListeners() {
		this.addKeyListener(textfield);
	}

}
