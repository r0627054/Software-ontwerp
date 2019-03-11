package ui.model.components;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import ui.model.viewmodes.TableDesignViewMode;

public class DesignTable extends EditableComponent {
	private VerticalComponentList rows;

	public DesignTable(int x, int y, int width, int height, String tableName, UUID tableId) {
		super(x, y, width, height, false, tableId);
	}

	public List<Cell> createTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> rowList = new ArrayList<>();
		List<Cell> cellList = new ArrayList<>();

		rowList.add(createHeader(columnCharacteristics));

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnCharacteristics.entrySet()) {
			List<Component> cells = new ArrayList<>();
			UUID id = entry.getKey();

			for (Map.Entry<String, Object> obj : entry.getValue().entrySet()) {
				Cell newCell = null;

				// TODO: create a function (createTextFieldCell/createCheckBoxCell/...) to
				// create these cells
				switch (obj.getKey()) {
				case "Type":
					TextField textField = new TextField(0, 0, 100, 100, obj.getValue().toString(), id);
					newCell = new Cell(0, 0, textField, id);
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_TYPE);
					break;
				case "Column Name":
					EditableTextField nameTextField = new EditableTextField(0, 0, 100, 100, obj.getValue().toString(),
							id);
					newCell = new Cell(0, 0, nameTextField, id);
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_NAME);
					break;
				case "Allow Blanks":
					CheckBox blanksCheckBox = new CheckBox(0, 0, (Boolean) obj.getValue(), id);
					newCell = new Cell(0, 0, blanksCheckBox, id);
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_ALLOW_BLANKS);
					break;
				case "Default Value":
					if (obj.getValue() == null) {
						TextField valueTextField = new TextField(0, 0, 100, 100, "", id);
						newCell = new Cell(0, 0, valueTextField, id);
					} else if (obj.getValue() instanceof String || obj.getValue() instanceof Integer) {
						Component valueTextField = new EditableTextField(0, 0, 100, 100, obj.getValue().toString(), id);
						newCell = new Cell(0, 0, valueTextField, id);
						newCell.setActionType(ChangeEventType.COLUMN_CHANGE_DEFAULT_VALUE);
					} else if (obj.getValue() instanceof Boolean) {
						CheckBox valueCheckBox = new CheckBox(0, 0, (Boolean) obj.getValue(), id);
						newCell = new Cell(0, 0, valueCheckBox, id);
						newCell.setActionType(ChangeEventType.COLUMN_CHANGE_DEFAULT_VALUE);
					}

					break;
				default:
					newCell = new Cell(0, 0, obj.getValue(), id);
					break;
				}
				cellList.add(newCell);
				cells.add(newCell);
			}

			rowList.add(new HorizontalComponentList(0, 0, cells));
		}
		this.setRows(new VerticalComponentList(this.getX(), this.getY(), 200, 200, rowList));
		return cellList;
	}

	public HorizontalComponentList createHeader(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> headerList = new ArrayList<>();

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
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2 && y > this.getOffsetY()) {
				propertyChanged(this.getId(), ChangeEventType.CREATE_COLUMN.getEventString(), null, null);
			}
		}
		getRows().outsideClick(id, x, y, clickCount);
	}

	private VerticalComponentList getRows() {
		return this.rows;
	}

	private void setRows(VerticalComponentList rows) {
		if (rows == null) {
			throw new IllegalArgumentException("Cannot set an empty VerticalComponentList as rows in DesignTable.");
		}
		this.rows = rows;
		this.setWidth(rows.getSumWidthFromChildren());
		this.setHeight(rows.getSumHeightFromChildren());
		//this.setX(rows.getX());
		//this.setY(this.getY());
	}

	public Cell getCell(int index, UUID columnId) {
		for (Component comp : rows.getComponentsList()) {
			HorizontalComponentList list = (HorizontalComponentList) comp;

			if (list.getComponentsList().get(index) instanceof Cell) {

				Cell cell = (Cell) list.getComponentsList().get(index);

				if (cell.getId() == columnId) {
					return cell;
				}
			}
		}
		return null;
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// Don't call the mouseClicked on the children!
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// Don't call the keyPressed on the children!
	}

}
