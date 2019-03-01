package ui.model.viewmodes;

import ui.model.components.CheckBox;
import ui.model.components.TextField;

public class TablesViewMode extends ViewMode {
	
	public TablesViewMode(String name) {
		super(name);
		
		CheckBox cb = new CheckBox(10, 10, true);
		this.addComponent(cb);
		this.addClickListener(cb);
		this.addComponent(new TextField(50, 50, 100, 50, "Wauw fantastisch leuke tekst"));

	}

}
