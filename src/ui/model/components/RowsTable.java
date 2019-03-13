package ui.model.components;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class RowsTable extends EditableComponent {

	private HorizontalComponentList columns;
	private List<Cell> deleteCells;

	public RowsTable(int x, int y, UUID id) {
		super(x, y, 0, 0, false, id);
		this.deleteCells = new ArrayList<>();
	}

	public List<Cell> createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values,
			Map<UUID, Class<?>> columnTypes) {
		List<Component> columnList = new ArrayList<>();
		List<Cell> allCellsList = new ArrayList<>();

		for (Map<UUID, String> columnIdMap : values.keySet()) {
			Map<UUID, Object> columnCellsMap = values.get(columnIdMap);
			String columnName = (String) columnIdMap.values().toArray()[0];
			UUID columnId = (UUID) columnIdMap.keySet().toArray()[0];
			Class<?> tableType = columnTypes.get(columnId);

			ColumnHeader header = new ColumnHeader(columnName, columnId);

			List<Component> columnCells = new ArrayList<Component>();
			columnCells.add(header);

			for (UUID cellId : columnCellsMap.keySet()) {
				Cell newCell = new Cell(0, 0, columnCellsMap.get(cellId), cellId, tableType);
				columnCells.add(newCell);
				newCell.setActionType(ChangeEventType.ROW_EDITED);
				allCellsList.add(newCell);
			}
			columnList.add(new VerticalComponentList(0, 0, columnCells));
		}
		this.setColumns(new HorizontalComponentList(this.getX(), this.getY(), columnList));
		this.calculateWidthAndHeight();
		return allCellsList;
	}

	private void calculateWidthAndHeight() {
		this.setWidth(this.getColumns().getSumWidthFromChildren());

		for (Component c : getColumns().getComponentsList()) {
			VerticalComponentList row = (VerticalComponentList) c;
			if (this.getHeight() < row.getSumHeightFromChildren()) {
				this.setHeight(row.getSumHeightFromChildren());
			}
		}

	}

	@Override
	public void paint(Graphics2D g) {
		checkPaintDeleteSelectedColumn();
		this.getColumns().paint((Graphics2D) g.create());
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// Don't call the mouseClicked on the children!
		this.resetDeleteCells();
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2 && y > getColumns().getOffsetY()) {
				propertyChanged(getId(), ChangeEventType.CREATE_ROW.getEventString(), null, null);
			}

			this.resetDeleteCells();

			if (y > this.getY() && y < this.getOffsetY()) {

				for (Component c : getColumns().getComponentsList()) {
					VerticalComponentList vertComponentList = (VerticalComponentList) c;

					for (Component componentOfList : vertComponentList.getComponentsList()) {
						if (y > componentOfList.getY() && y < componentOfList.getOffsetY() && x < componentOfList.getX()) {
							if (componentOfList instanceof Cell) {
								Cell cell = (Cell) componentOfList;
								this.addDeleteCell(cell);
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// Don't call the keyPressed on the children!
		if (keyCode == KeyEvent.VK_DELETE) {
			if (!this.getDeleteCells().isEmpty()) {
				UUID cellId = getDeleteCells().get(0).getId();
				propertyChanged(cellId, ChangeEventType.DELETE_ROW.getEventString(), null, null);
			}
		}
	}

	private HorizontalComponentList getColumns() {
		return columns;
	}

	private void setColumns(HorizontalComponentList columns) {
		this.columns = columns;
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

	private void checkPaintDeleteSelectedColumn() {
		for (Cell c : getDeleteCells()) {
			c.setRedBackground(true);
		}
	}

	public Cell getCell(int columnIndex, UUID columnId) {
		for (Component comp : getColumns().getComponentsList()) {
			VerticalComponentList verticalComponentList = (VerticalComponentList) comp;

			ColumnHeader headerOfColumn = (ColumnHeader) verticalComponentList.getComponentsList().get(0);
			if (headerOfColumn.getId().equals(columnId)) {
				return (Cell) verticalComponentList.getComponentsList().get(columnIndex + 1);
			}
		}
		return null;
	}

}
