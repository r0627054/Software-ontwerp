package ui.model.components;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class DesignTable extends EditableComponent {
	private VerticalComponentList rows;
	private List<Cell> deleteCells;

	public DesignTable(int x, int y, int width, int height, String tableName, UUID tableId) {
		super(x, y, width, height, false, tableId);
		this.deleteCells = new ArrayList<>();
	}

	public List<Cell> createTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> rowList = new ArrayList<>();
		List<Cell> allCellsList = new ArrayList<>();

		rowList.add(createHeader(columnCharacteristics));

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnCharacteristics.entrySet()) {
			List<Component> rowCells = new ArrayList<>();
			UUID columnId = entry.getKey();

			for (Map.Entry<String, Object> obj : entry.getValue().entrySet()) {
				Cell newCell = null;

				// TODO: create a function (createTextFieldCell/createCheckBoxCell/...) to
				// create these cells
				switch (obj.getKey()) {
				case "Type":
					ToggleTextField toggleTextField = new ToggleTextField(0, 0, 100, 100, obj.getValue().toString(),
							columnId);
					newCell = new Cell(0, 0, toggleTextField, columnId);
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_TYPE);
					break;
				case "Column Name":
					EditableTextField nameTextField = new EditableTextField(0, 0, 100, 100, obj.getValue().toString(),
							columnId);
					newCell = new Cell(0, 0, nameTextField, columnId);
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_NAME);
					break;
				case "Allow Blanks":
					CheckBox blanksCheckBox = new CheckBox(0, 0, (Boolean) obj.getValue(), columnId);
					newCell = new Cell(0, 0, blanksCheckBox, columnId);
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_ALLOW_BLANKS);
					break;
				case "Default Value":
					if (obj.getValue() == null || obj.getValue() instanceof Boolean) {
						String value = obj.getValue() == null ? "" : obj.getValue().toString();

						TextField valueTextField = new ToggleTextField(0, 0, 100, 100, value, columnId);
						newCell = new Cell(0, 0, valueTextField, columnId);

					} else if (obj.getValue() instanceof String || obj.getValue() instanceof Integer) {
						Component valueTextField = new EditableTextField(0, 0, 100, 100, obj.getValue().toString(),
								columnId);
						newCell = new Cell(0, 0, valueTextField, columnId);
					}
					newCell.setActionType(ChangeEventType.COLUMN_CHANGE_DEFAULT_VALUE);
					break;
				default:
					newCell = new Cell(0, 0, obj.getValue(), columnId);
					break;
				}
				allCellsList.add(newCell);
				rowCells.add(newCell);
			}

			rowList.add(new HorizontalComponentList(0, 0, rowCells));
		}
		this.setRows(new VerticalComponentList(this.getX(), this.getY(), rowList));
		return allCellsList;
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
		checkPaintDeleteSelectedColumn();
		getRows().paint(g);
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2 && y > this.getOffsetY()) {
				propertyChanged(this.getId(), ChangeEventType.CREATE_COLUMN.getEventString(), null, null);
			}

			boolean isLeftClickOfARow = false;
			for (Component c : getRows().getComponentsList()) {
				HorizontalComponentList horizComponentList = (HorizontalComponentList) c;

				if (y > c.getY() && y < c.getOffsetY()) {
					isLeftClickOfARow = true;
					this.resetDeleteCells();
					for (Component componentOfList : horizComponentList.getComponentsList()) {
						if (componentOfList instanceof Cell) {
							Cell cell = (Cell) componentOfList;
							this.addDeleteCell(cell);
						}
					}
					propertyChanged();
					break;
				}
			}

			if (!isLeftClickOfARow) {
				this.resetDeleteCells();
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
	}

	private void checkPaintDeleteSelectedColumn() {
		for (Cell c : getDeleteCells()) {
			c.setRedBackground(true);
		}
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
		this.resetDeleteCells();
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// Don't call the keyPressed on the children!
		if (keyCode == KeyEvent.VK_DELETE) {
			if (!this.getDeleteCells().isEmpty()) {
				UUID columnId = getDeleteCells().get(0).getId();
				propertyChanged(columnId, ChangeEventType.DELETE_COLUMN.getEventString(), null, null);
			}
		}
	}

	private List<Cell> getDeleteCells() {
		return deleteCells;
	}

	private void addDeleteCell(Cell deleteCell) {
		this.getDeleteCells().add(deleteCell);

	}

	private void resetDeleteCells() {
		for (Cell c : getDeleteCells()) {
			c.setRedBackground(false);
		}
		propertyChanged();
		this.deleteCells = new ArrayList<>();
	}

}
