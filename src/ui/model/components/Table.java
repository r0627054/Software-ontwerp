package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Table extends Component {

	private HorizontalComponentList columns;

	public Table(int x, int y, int width, int height, boolean hidden,
			Map<Map<UUID, String>, Map<UUID, Object>> values) {
		super(x, y, width, height, false);
		initTable(values);
	}

	public Table(int x, int y, int width, int height, Map<Map<UUID, String>, Map<UUID, Object>> values) {
		this(x, y, width, height, false, values);
	}

	private void initTable(Map<Map<UUID, String>, Map<UUID, Object>> values) {

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
		columns = new HorizontalComponentList(this.getX(), getY(), columnList);

	}

	@Override
	public void paint(Graphics2D g) {
//		g.setColor(Color.WHITE);
//		g.fillRect(getX(), getY(), getWidth(), getHeight());

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
		// TODO Auto-generated method stub

	}

}
