package ui.model.viewmodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.model.components.Container;
import ui.model.components.Table;

public class TableRowsViewMode extends ViewMode {
	private Container container;

	public TableRowsViewMode(String name) {
		super(name);
		container = new Container(0, 0, 600, 500);
		addDummyTable();
		this.addComponent(container);

	}

	private void addDummyTable() {
		// Testing purposes
		List<Object> col1 = new ArrayList<>();
		col1.add("1. fmdksjmfk");
//		col1.add("2. fmdksjmfk");
//		col1.add("3. fmdksjmfk");
//		col1.add("4. fmdksjmfk");

		List<Object> col2 = new ArrayList<>();
		col2.add(new Boolean(true));
//		col2.add(new Boolean(true));
//		col2.add(new Boolean(false));
//		col2.add(new Boolean(true));

		List<Object> list3 = new ArrayList<>();
		list3.add(1546532);
//		list3.add(35653186);
//		list3.add(123);
//		list3.add(123);

		Map<String, List<Object>> tableMap = new HashMap<>();
		tableMap.put("Header1", col1);
		tableMap.put("Header2", col2);
		tableMap.put("Header3", list3);

		Table table = new Table(60, 60, 500, 150, tableMap);
		this.addClickListener(table);
		container.addComponent(table);
	}

	@Override
	void registerAllKeyListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	void registerAllClickListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	void registerWindowChangeListeners() {
		// TODO Auto-generated method stub

	}

}
