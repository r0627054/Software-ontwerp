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
 * The rowsTable is subclass of EditableComponent.
 *  It creates a rowTable, a rowTable show all the information of the table.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
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

	private boolean isComputedTable;

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
	 * @param isComputedTable 
	 * 		  Is this table a computed table
	 * @effect The variables are set and the rowsTable is initialised with an empty list of cells selected for deletion.
	 *        | super(x, y, 0, 0, false, id);
	 *        |	this.deleteCells = new ArrayList<>();
	 */
	public RowsTable(int x, int y, UUID id, boolean isComputedTable) {
		super(x, y, 0, 0, false, id);
		this.setComputedTable(isComputedTable);
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
	public List<UICell> createTable(Map<List<Object>, LinkedHashMap<UUID, Object>> values) {
		List<Component> columnList = new ArrayList<>();
		List<UICell> allCellsList = new ArrayList<>();

		ChangeEventType cellSubmitAction = ChangeEventType.ROW_EDITED;

		for (List<Object> columnData : values.keySet()) {
			Map<UUID, Object> columnCellsMap = values.get(columnData);
			UUID columnId = (UUID) columnData.get(0);
			String columnName = (String) columnData.get(1);
			Class<?> tableType = (Class<?>) columnData.get(2);

			ColumnHeader header = new ColumnHeader(columnName, columnId);

			List<Component> columnCells = new ArrayList<Component>();
			columnCells.add(header);

			for (UUID cellId : columnCellsMap.keySet()) {
				UICell newCell = new UICell(columnCellsMap.get(cellId), cellId, tableType, cellSubmitAction, null,
						null);
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
		if (!this.isComputedTable()) {
			this.resetDeleteCells();
		}
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
		if (id == MouseEvent.MOUSE_PRESSED && !isComputedTable()) {
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
								cell.setRedBackground(true);
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
		if (keyCode == KeyEvent.VK_DELETE && !isComputedTable()) {
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

	/**
	 * Sets the y-coordinate depending of the change of y. How far it is moved from the previous y location.
	 * @param y the y-coordinate movement.
	 * @effect The y-coordinate is set relative to the previous y-coordinate value and the rows of the designTable do also change.
	 *         | this.setY(this.getY() + y)
	 *         | this.getColumns().changeY(y);
	 */
	@Override
	public void changeY(int y) {
		super.changeY(y);
		this.getColumns().changeY(y);
	}

	/**
	 * Sets the x-coordinate depending of the change of x. How far it is moved from the previous x location.
	 * @param x the x-coordinate movement.
	 * @effect The x-coordinate is set relative to the previous y-coordinate value and the rows of the designTable do also change.
	 *         | this.setX(this.getX() + x)
	 *         | this.getColumns().change(x);
	 */
	@Override
	public void changeX(int x) {
		super.changeX(x);
		this.getColumns().changeX(x);
	}

	/**
	 * Resets the error.
	 * The cell in the rows table containing the error is set such that there is no more error in that cell.
	 */
	public void resetError() {
		boolean isFound = false;
		for (int i = 0; i < columns.getComponentsList().size() && !isFound; i++) {
			VerticalComponentList comp = (VerticalComponentList) columns.getComponentsList().get(i);

			for (int j = 0; j < comp.getComponentsList().size() && !isFound; j++) {
				Component comp2 = comp.getComponentsList().get(j);

				if (comp2 instanceof UICell) {
					UICell cell = (UICell) comp2;
					if (cell.isError()) {
						cell.setError(false);
						isFound = true;
					}
				}
			}
		}
	}

	/**
	 * @return the isComputedTable
	 */
	private boolean isComputedTable() {
		return isComputedTable;
	}

	/**
	 * @param isComputedTable the isComputedTable to set
	 */
	private void setComputedTable(boolean isComputedTable) {
		this.isComputedTable = isComputedTable;
	}

}
