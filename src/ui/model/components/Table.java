package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table extends Component {

	private List<HorizontalComponentList> columns;
	private List<Row> rows;

	public Table(int x, int y, int width, int height, boolean hidden) {
		super(x, y, width, height, false);

		this.columns = new ArrayList<>();
		this.rows = new ArrayList<>();
	}

	public Table(int x, int y, int width, int height, Map<String, List<Object>> values) {
		this(x, y, width, height, false);

		int currX = getX();
		int currY = getY();
		int currWidth = this.getWidth() / values.size();
		int currHeight;

		for (String columnKey : values.keySet()) {
			List<Object> columnValues = values.get(columnKey);
			currHeight = getHeight() / (columnValues.size() + 1);
			List<Component> compList = new ArrayList<>();

			ColumnHeader header = new ColumnHeader(currX, getY(), currWidth, currHeight, columnKey);
			compList.add(header);
			currY += currHeight;

			for (Object obj : columnValues) {
				if (obj instanceof String || obj instanceof Integer) {
					compList.add(new EditableTextField(currX, currY, currWidth, currHeight, (String) obj.toString()));
				} else if (obj instanceof Boolean) {
					compList.add(new CheckBox(currX, currY, currWidth, currHeight, (Boolean) obj));
					System.out.println("Creating checkbox with y= " + currY);
				}
				currY += currHeight;
			}
			Row tempCol = new Row(currX, getY(), currWidth, this.getHeight(), compList);
			rows.add(tempCol);
			currX += currWidth;
			currY = getY();
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);

		for (Row list : rows) {
			list.paint((Graphics2D) g.create());
		}
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		for (Row r : rows) {
			if (r.isWithinComponent(x, y)) {
				r.mouseClicked(id, x, y, clickCount);
			}
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

}
