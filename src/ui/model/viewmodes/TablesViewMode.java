package ui.model.viewmodes;

import ui.model.components.CheckBox;
import ui.model.components.EditableTextField;
import ui.model.components.TextField;

public class TablesViewMode extends ViewMode {
	
	public TablesViewMode(String name) {
		super(name);
		
		CheckBox cb = new CheckBox(10, 10, true);
		this.addComponent(cb);
		this.addClickListener(cb);
		TextField tf = new EditableTextField(50, 50, 100, 50, false, "Wauw fantastisch leuke tekst");
		this.addComponent(tf);
		this.addClickListener(tf);

	}

}
