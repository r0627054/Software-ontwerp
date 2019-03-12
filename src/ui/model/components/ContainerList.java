package ui.model.components;

import java.util.ArrayList;
import java.util.List;

public abstract class ContainerList extends Container {

	public ContainerList(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, listItems);
	}

	public ContainerList(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

}
