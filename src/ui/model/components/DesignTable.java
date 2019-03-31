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

/**
 * A DesignTable is an editableComponet.
 * It contains a VerticalComponentList of rows.
 * The is the actual composition of a DesignTable.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class DesignTable extends EditableComponent {

	/**
	 * VerticalComponentList Variable containing all the rows.
	 */
	private VerticalComponentList rows;

	/**
	 * List of cells selected for deletion
	 */
	private List<UICell> deleteCells;

	/**
	 * Initialise a new DesignTable with the given variables.
	 * The list of cells selected for deletion equals a new empty ArrayList<>()
	 * 
	 * @param x
	 *        The x-coordinate of the component.
	 * @param y
	 *        The y-coordinate of the component.
	 * @param width
	 *        The width of the component.
	 * @param height
	 *        The height of the component.
	 * @param tableName
	 *        The name of the table.
	 * @param tableId
	 *        The id of the table.
	 * @effect all the variables are set. 
	 *        | super(x, y, width, height, false, tableId);
	 *        |	this.deleteCells = new ArrayList<>()
	 */
	public DesignTable(int x, int y, int width, int height, String tableName, UUID tableId) {
		super(x, y, width, height, false, tableId);
		this.deleteCells = new ArrayList<>();
	}

	/**
	 * Creates the full table with all the data and components.
	 * @param columnCharacteristics 
	 *        | all the information needed in the table.
	 * @return The list of cells which the designTable created.
	 */
	public List<UICell> createTable(Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		List<Component> rowList = new ArrayList<>();
		List<UICell> allCellsList = new ArrayList<>();

		rowList.add(createHeader(columnCharacteristics));

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnCharacteristics.entrySet()) {
			List<Component> rowCells = new ArrayList<>();
			UUID columnId = entry.getKey();
			String type = (String) entry.getValue().get("Type");

			for (Map.Entry<String, Object> obj : entry.getValue().entrySet()) {
				UICell newCell = null;

				// TODO: create a function (createTextFieldCell/createCheckBoxCell/...) to
				// create these cells
				switch (obj.getKey()) {
				case "Type":
					ToggleTextField toggleTextField = new ToggleTextField(0, 0, 100, 100, obj.getValue().toString(),
							columnId);
					newCell = new UICell(toggleTextField, columnId, ChangeEventType.COLUMN_CHANGE_TYPE);
					break;
				case "Column Name":
					EditableTextField nameTextField = new EditableTextField(0, 0, 100, 100, obj.getValue().toString(),
							columnId);
					newCell = new UICell(nameTextField, columnId, ChangeEventType.COLUMN_CHANGE_NAME);
					break;
				case "Allow Blanks":
					CheckBox blanksCheckBox = new CheckBox(0, 0, (Boolean) obj.getValue(), columnId);
					newCell = new UICell(blanksCheckBox, columnId, ChangeEventType.COLUMN_CHANGE_ALLOW_BLANKS);
					break;
				case "Default Value":
					if (type.equals("Boolean")) {
						String value = obj.getValue() == null ? "" : obj.getValue().toString();

						TextField valueTextField = new ToggleTextField(0, 0, 100, 100, value, columnId);
						newCell = new UICell(valueTextField, columnId, ChangeEventType.COLUMN_CHANGE_DEFAULT_VALUE);

					} else {
						String value = obj.getValue() == null ? "" : obj.getValue().toString();

						Component valueTextField = new EditableTextField(0, 0, 100, 100, value, columnId);
						newCell = new UICell(valueTextField, columnId, ChangeEventType.COLUMN_CHANGE_DEFAULT_VALUE);
					}
					break;
				default:
					newCell = new UICell(obj.getValue(), columnId, null);
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

	/**
	 * Creates the header of the table.
	 * @param columnCharacteristics 
	 *        | all the information needed in the table.
	 * @return the horizontalComponentList which contains all the cells of the header.
	 */
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

	/**
	 * Draws the all the components of the container.
	 * Sets all the components which are selected for delete.
	 * @param g 
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {
		checkPaintDeleteSelectedColumn();
		getRows().paint(g);
	}

	/**
	 * Calls the outside click method on the children in the container and delegates it behaviour.
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
			if (clickCount == 2 && y > this.getOffsetY()) {
				propertyChanged(this.getId(), ChangeEventType.CREATE_COLUMN, null, null);
			}

			boolean isLeftClickOfARow = false;
			for (Component c : getRows().getComponentsList()) {
				HorizontalComponentList horizComponentList = (HorizontalComponentList) c;

				if (y > c.getY() && y < c.getOffsetY()) {
					isLeftClickOfARow = true;
					this.resetDeleteCells();
					for (Component componentOfList : horizComponentList.getComponentsList()) {
						if (componentOfList instanceof UICell) {
							UICell cell = (UICell) componentOfList;
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

	/**
	 * Returns the verticalComponentList which contains all the rows.
	 */
	public VerticalComponentList getRows() {
		return this.rows;
	}

	/**
	 * Sets the verticalComponentList to the given rows parameter.
	 * The with and height of the designTable itself is updated.
	 * 
	 * @param rows
	 *        | The vertical componentList containing all the rows.
	 * @effect the rows are set and the width and height of the designTable are updated.
	 *        |this.rows = rows;
	 *        |this.setWidth(rows.getSumWidthFromChildren());
	 *        |this.setHeight(rows.getSumHeightFromChildren())
	 * @throws IllegalArgumentException if rows equals null
	 *        | rows == null
	 */
	private void setRows(VerticalComponentList rows) {
		if (rows == null) {
			throw new IllegalArgumentException("Cannot set an empty VerticalComponentList as rows in DesignTable.");
		}
		this.rows = rows;
		this.setWidth(rows.getSumWidthFromChildren());
		this.setHeight(rows.getSumHeightFromChildren());
	}

	/**
	 * Sets the background color of the cells to red if they are selected for deletion.
	 */
	private void checkPaintDeleteSelectedColumn() {
		for (UICell c : getDeleteCells()) {
			c.setRedBackground(true);
		}
	}

	/**
	 * Returns the cell at the given index in the given column.
	 * 
	 * @param index
	 *        | The index of the cell in the componentList
	 * @param columnId
	 *        | The id of the column.
	 * @return the cell at the given index in the given column.
	 */
	public UICell getCell(int index, UUID columnId) {
		for (Component comp : rows.getComponentsList()) {
			HorizontalComponentList list = (HorizontalComponentList) comp;

			if (list.getComponentsList().get(index) instanceof UICell) {

				UICell cell = (UICell) list.getComponentsList().get(index);

				if (cell.getId() == columnId) {
					return cell;
				}
			}
		}
		return null;
	}

	/**
	 * Mouse click resets the cells selected for deletion.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect Mouse click resets the cells selected for deletion.
	 *        | this.resetDeleteCells()
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// Don't call the mouseClicked on the children!
		this.resetDeleteCells();
	}

	/**
	 * Checks whether or not the delete key is pressed.
	 * If some cells where selected for deletion these will be deleted.
	 * 
	 * @param id
	 *        | The id of the key pressed event. 
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 * @effect Deletes a column if the delete key was pressed and a column was selected.
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// Don't call the keyPressed on the children!
		if (keyCode == KeyEvent.VK_DELETE) {
			if (!this.getDeleteCells().isEmpty()) {
				UUID columnId = getDeleteCells().get(0).getId();
				propertyChanged(columnId, ChangeEventType.DELETE_COLUMN, null, null);
			}
		}
	}

	/**
	 * Returns the cells selected for deletion.
	 */
	private List<UICell> getDeleteCells() {
		return deleteCells;
	}

	/**
	 * Add a cell to the list of selected for deletion.
	 * @param deleteCell
	 *        | The cell which will be added for deletion.
	 * @effect The cell is added to the list.
	 *        | this.getDeleteCells().add(deleteCell)
	 */
	private void addDeleteCell(UICell deleteCell) {
		this.getDeleteCells().add(deleteCell);

	}

	/**
	 * Resets the selected for delete cells.
	 * the previous selected for delete will be reseted to their normal background.
	 */
	private void resetDeleteCells() {
		for (UICell c : getDeleteCells()) {
			c.setRedBackground(false);
		}
		propertyChanged();
		this.deleteCells = new ArrayList<>();
	}
	
	@Override
	public void changeY(int y) {
		super.changeY(y);
		this.rows.changeY(y);
	}

	@Override
	public void changeX(int x) {
		super.changeX(x);
		this.rows.changeX(x);
	}

}
