package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table extends Component {

	private HorizontalComponentList columns;

	public Table(int x, int y, int width, int height, boolean hidden) {
		super(x, y, width, height, false);
		
		//TODO: Set max width/Height of table;

		this.columns = new HorizontalComponentList(x, y);
	}

	public Table(int x, int y, int width, int height, Map<String, List<Object>> values) {
		this(x, y, width, height, false);

		for (String columnKey : values.keySet()) {
			List<Object> columnValues = values.get(columnKey);
			List<Component> componentList = new ArrayList<>();

			for (Object obj : columnValues) {
				componentList.add(new Cell(0, 0, obj));
			}

			columns.addComponent(new Column(0, 0, componentList));
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), getWidth(), getHeight());

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
