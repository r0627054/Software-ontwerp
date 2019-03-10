package ui.model.components;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class DesignTable extends IdentifiableComponent {
	private VerticalComponentList rows;

	public DesignTable(int x, int y, int width, int height, String tableName, UUID tableId, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		super(x, y, width, height, false, tableId);
		
		createTable(tableName, columnCharacteristics);
	}

	private void createTable(String tableName, Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> rowList = new ArrayList<>();

		rowList.add(createHeader(tableName, columnCharacteristics));

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnCharacteristics.entrySet()) {
			List<Component> cells = new ArrayList<>();
			UUID id = entry.getKey();

			cells.add(new Cell(0, 0, "", id));
			for (Map.Entry<String, Object> obj : entry.getValue().entrySet()) {
				cells.add(new Cell(0, 0, obj.getValue(), id));
			}
			rowList.add(new HorizontalComponentList(0, 0, cells));
		}

		this.setRows(new VerticalComponentList(this.getX(), this.getY(), 200, 200, rowList));
	}

	private HorizontalComponentList createHeader(String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> headerList = new ArrayList<>();
		headerList.add(new ColumnHeader(tableName, UUID.randomUUID()));

		Iterator<LinkedHashMap<String, Object>> it = columnCharacteristics.values().iterator();

		if (it.hasNext()) {
			LinkedHashMap<String, Object> firstEntry = it.next();

			for (String entry : firstEntry.keySet()) {
				headerList.add(new ColumnHeader(entry, UUID.randomUUID()));
			}
		}

		return new HorizontalComponentList(getX(), getY(), headerList);
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
	
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if(id == MouseEvent.MOUSE_CLICKED) {
			if(clickCount == 2 && y > this.getOffsetY() ) {
				propertyChanged(this.getId(), ChangeEventType.CREATE_COLUMN.getEventString(), null, null);
			}
		}
	}

	private VerticalComponentList getRows() {
		return rows;
	}

	private void setRows(VerticalComponentList rows) {
		this.rows = rows;
		this.setWidth(rows.getSumWidthFromChildren());
		this.setHeight(rows.getSumHeightFromChildren());
		this.setX(rows.getX());
		this.setY(this.getY());
	}

}
