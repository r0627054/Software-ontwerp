package ui.model.components;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class RowsTable extends EditableComponent {

	private HorizontalComponentList columns;

	public RowsTable(int x, int y, int width, int height, UUID id) {
		super(x, y, width, height, false, id);
	}

	public void createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values) {

		List<Component> columnList = new ArrayList<Component>();
		for (Map<UUID, String> columnIdMap : values.keySet()) {

			Map<UUID, Object> columnCellsMap = values.get(columnIdMap);

			List<Component> cells = new ArrayList<Component>();

			String headerName = (String) columnIdMap.values().toArray()[0];
			UUID headerId = (UUID) columnIdMap.keySet().toArray()[0];

			ColumnHeader header = new ColumnHeader(headerName, headerId);
			cells.add(header);

			for (UUID cellId : columnCellsMap.keySet()) {
				cells.add(new Cell(0, 0, columnCellsMap.get(cellId), cellId));
			}
			columnList.add(new Column(0, 0, cells));
		}
		this.setColumns(new HorizontalComponentList(this.getX(), getY(), columnList));
	}

	@Override
	public void paint(Graphics2D g) {
		this.columns.paint((Graphics2D) g.create());
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (this.columns.isWithinComponent(x, y)) {
			columns.mouseClicked(id, x, y, clickCount);
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2 && y > getColumns().getOffsetY()) {
				propertyChanged(getId(), ChangeEventType.CREATE_ROW.getEventString(), null, null);
			}
		}
	}

	private HorizontalComponentList getColumns() {
		return columns;
	}

	private void setColumns(HorizontalComponentList columns) {
		this.columns = columns;
	}
}
