package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DesignTable extends Component {
	private VerticalComponentList rows;

	public DesignTable(int x, int y, String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(x, y);
		createTable(tableName, columnCharacteristics);
	}

	private void createTable(String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> rowList = new ArrayList<>();

		rowList.add(createHeader(tableName, columnCharacteristics));

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnCharacteristics.entrySet()) {
			List<Component> cells = new ArrayList<>();
			UUID id = entry.getKey();

			cells.add(new Cell(0,0, "", id));
			for (Map.Entry<String, Object> obj : entry.getValue().entrySet()) {
				System.out.println("----" + obj.getKey() + " == " + obj.getValue().toString());
				cells.add(new Cell(0, 0, obj.getValue(), id));
			}
			rowList.add(new HorizontalComponentList(0, 0, cells));
		}

		this.setRows(new VerticalComponentList(0, 0, 200, 200, rowList));
	}

	private HorizontalComponentList createHeader(String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> headerList = new ArrayList<>();
		headerList.add(new ColumnHeader(tableName, UUID.randomUUID()));

		System.out.println(columnCharacteristics);

		Iterator<LinkedHashMap<String, Object>> it = columnCharacteristics.values().iterator();

		if (it.hasNext()) {
			LinkedHashMap<String, Object> firstEntry = it.next();

			for (String entry : firstEntry.keySet()) {
				headerList.add(new ColumnHeader(entry, UUID.randomUUID()));
				System.out.println(entry);
			}
		}

		return new HorizontalComponentList(0, 0, headerList);
	}

	@Override
	public void paint(Graphics2D g) {
		getRows().paint(g);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

	private VerticalComponentList getRows() {
		return rows;
	}

	private void setRows(VerticalComponentList rows) {
		this.rows = rows;
	}

}
