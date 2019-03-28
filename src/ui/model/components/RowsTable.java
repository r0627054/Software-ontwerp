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

/**
 * The rowsTable is sub class of EditableComponent.
 *  It creates a rowTable, a rowTable show all the information of the table.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class RowsTable extends EditableComponent {

	/**
	 * Variable storing the HorizontalComponentList of columns.
	 */
	private HorizontalComponentList columns;
	
	/**
	 * Variable storing the cells which are selected for deletion.
	 */
	private List<UICell> deleteCells;

	/**
	 * Initialise the RowsTable with the given information.
	 *  By default there are no cells selected for deletion.
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param id
	 *        The id of the table.
	 * @effect The variables are set and the rowsTable is initialised with an empty list of cells selected for deletion.
	 *        | super(x, y, 0, 0, false, id);
	 *        |	this.deleteCells = new ArrayList<>();
	 */
	public RowsTable(int x, int y, UUID id) {
		super(x, y, 0, 0, false, id);
		this.deleteCells = new ArrayList<>();
	}

	/**
	 * Creates an actual table with the given values.
	 * 
	 * @param values
	 *        | the values that will be displayed in the RowsTable.
	 * @param columnTypes
	 *        | the columns and their types.
	 * @return the list of cells created by the rowsTable.
	 * 
	 */
	public List<UICell> createTable(Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values,
			Map<UUID, Class<?>> columnTypes) {
		List<Component> columnList = new ArrayList<>();
		List<UICell> allCellsList = new ArrayList<>();

		for (Map<UUID, String> columnIdMap : values.keySet()) {
			Map<UUID, Object> columnCellsMap = values.get(columnIdMap);
			String columnName = (String) columnIdMap.values().toArray()[0];
			UUID columnId = (UUID) columnIdMap.keySet().toArray()[0];
			Class<?> tableType = columnTypes.get(columnId);

			ColumnHeader header = new ColumnHeader(columnName, columnId);

			List<Component> columnCells = new ArrayList<Component>();
			columnCells.add(header);

			for (UUID cellId : columnCellsMap.keySet()) {
				UICell newCell = new UICell(columnCellsMap.get(cellId), cellId, tableType, ChangeEventType.ROW_EDITED);
				columnCells.add(newCell);
				allCellsList.add(newCell);
			}
			columnList.add(new VerticalComponentList(0, 0, columnCells));
		}
		this.setColumns(new HorizontalComponentList(this.getX(), this.getY(), columnList));
		this.calculateWidthAndHeight();
		return allCellsList;
	}

	/**
	 * Calculates the height and width using the children.
	 *  The height and width of the rowsTable is set.
	 */
	private void calculateWidthAndHeight() {
		this.setWidth(this.getColumns().getSumWidthFromChildren());

		for (Component c : getColumns().getComponentsList()) {
			VerticalComponentList row = (VerticalComponentList) c;
			if (this.getHeight() < row.getSumHeightFromChildren()) {
				this.setHeight(row.getSumHeightFromChildren());
			}
		}

	}

	/**
	 * Draws the RowTable and draws all the components of the container.
	 *  Checks whether a row is selected for deletion.
	 * @param g 
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		checkPaintDeleteSelectedColumn();
		this.getColumns().paint((Graphics2D) g.create());
	}

	/**
	 * Resets the cells selected for deletion.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The cells selected for deletion.
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// Don't call the mouseClicked on the children!
		this.resetDeleteCells();
	}

	/**
	 * Checks if there was a click to the left of the column.
	 *  The cells of this row are selected for deletion.
	 *  Otherwise the previous selected cells for deletion are unselected for deletion.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 */
	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_CLICKED) {
			if (clickCount == 2 && y > getColumns().getOffsetY()) {
				propertyChanged(getId(), ChangeEventType.CREATE_ROW, null, null);
			}

			this.resetDeleteCells();

			if (y > this.getY() && y < this.getOffsetY() && x < this.getX()) {
				for (Component c : getColumns().getComponentsList()) {
					VerticalComponentList vertComponentList = (VerticalComponentList) c;

					for (Component componentOfList : vertComponentList.getComponentsList()) {
						if (y > componentOfList.getY() && y < componentOfList.getOffsetY()
								&& x < componentOfList.getX()) {
							if (componentOfList instanceof UICell) {
								UICell cell = (UICell) componentOfList;
								this.addDeleteCell(cell);
							}
						}
					}
				}
			}
		}

	}

	/**
	 * If there are cells selected for deletion, these will be deleted if the delete key is pressed.
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// Don't call the keyPressed on the children!
		if (keyCode == KeyEvent.VK_DELETE) {
			if (!this.getDeleteCells().isEmpty()) {
				UUID cellId = getDeleteCells().get(0).getId();
				propertyChanged(cellId, ChangeEventType.DELETE_ROW, null, null);
			}
		}
	}

	/**
	 * Returns the columns of the rowsTable.
	 */
	public HorizontalComponentList getColumns() {
		return columns;
	}

	/**
	 * Sets the columns of the rowsTable to the given parameter.
	 * 
	 * @param columns
	 *        | The horizontalComponentList of columns.
	 * @effect the columns are equal to the column parameter
	 *        | new.getColumns() == columns
	 */
	private void setColumns(HorizontalComponentList columns) {
		this.columns = columns;
	}

	/**
	 * Returns the cells selected for deletion.
	 */
	private List<UICell> getDeleteCells() {
		return deleteCells;
	}

	/**
	 * Add a cell to the array of cells which are selected for deletion.
	 * 
	 * @param deleteCell
	 *        | the cell selected for deletion.
	 * @effect the cell is added to the list.
	 *        | this.getDeleteCells().add(deleteCell)
	 */
	private void addDeleteCell(UICell deleteCell) {
		this.getDeleteCells().add(deleteCell);
	}

	/**
	 * Resets the cells which are selected for deletion.
	 */
	private void resetDeleteCells() {
		for (UICell c : getDeleteCells()) {
			c.setRedBackground(false);
		}
		propertyChanged();
		this.deleteCells = new ArrayList<>();
	}

	/**
	 * Sets the background to red of the cells selected for delete.
	 */
	private void checkPaintDeleteSelectedColumn() {
		for (UICell c : getDeleteCells()) {
			c.setRedBackground(true);
		}
	}

	/**
	 * Returns the cell at the columnIndex in the column with the given Id.
	 * @param columnIndex
	 *        | the index of the column
	 * @param columnId
	 *        | the id of the column.
	 * @return the requested cell at the given columnIndex in the column with the given id is returned.
	 *         When the cell isn't found, null is returned.
	 */
	public UICell getCell(int columnIndex, UUID columnId) {
		for (Component comp : getColumns().getComponentsList()) {
			VerticalComponentList verticalComponentList = (VerticalComponentList) comp;

			ColumnHeader headerOfColumn = (ColumnHeader) verticalComponentList.getComponentsList().get(0);
			if (headerOfColumn.getId().equals(columnId)) {
				return (UICell) verticalComponentList.getComponentsList().get(columnIndex + 1);
			}
		}
		return null;
	}

}
