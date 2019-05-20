package ui.model.window.sub;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import java.util.UUID;



import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;

import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.TextField;
import ui.model.components.UICell;
import ui.model.components.VerticalComponentList;

/**
 * A FromWindow is a TableWindow showing the table information in a different format.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class FormWindow extends TableWindow {

	/**
	 * Variable holding the title String that comes before the table name.
	 */
	public static final String TITLE_STRING_PREFIX = "Form window of: ";

	/**
	 * Variable holding the title String that comes before the row number.
	 */
	public static final String ROW_STRING_PREFIX = "| Row: ";

	/**
	 * Variable holding the row number which is displayed.
	 */
	private int currentRow = 0;

	/**
	 * Variables storing if the table data is computed.
	 */
	private boolean isComputed;

	/**
	 * Variable storing the list of components (column cells)
	 */
	private List<Component> columnCells;

	/**
	 * Variable storing all the data displayed in the table.
	 */
	private Map<List<Object>, List<Object[]>> tableData;

	/**
	 * Initialise a new fromWindow with the given parameters.
	 * @param id        The id of the table.
	 * @param tableName The name of the table.
	 * @param tableData The data of the table.
	 * @param isComputed Whether or not the table contains computed data.
	 * @effect all the variables are set.
	 *         | super(id, TITLE_STRING_PREFIX + tableName + ROW_STRING_PREFIX + 0);
	 *         | this.setTableData(tableData);
	 *         | this.setComputed(isComputed);
	 *         | updateForm();
	 */
	public FormWindow(UUID id, String tableName, Map<List<Object>, List<Object[]>> tableData, boolean isComputed) {
		super(id, TITLE_STRING_PREFIX + tableName + ROW_STRING_PREFIX + 0);
		this.setTableData(tableData);
		this.setComputed(isComputed);
		updateForm();
	}

	/**
	 * Updates All the information in the form.
	 */
	private void updateForm() {
		this.setStoredListeners(new ArrayList<Component>());
		setContainer(new Container(getX(), getY(), getWidth(), getHeight()));
		columnCells = new ArrayList<Component>();
		int y = getY() + 50;
		int x1 = getX() + 50;
		int x2 = getX() + 110;

		for (List<Object> key : getTableData().keySet()) {
			Class<?> cellType = (Class<?>) key.get(2);
			boolean isEditable = (boolean) key.get(3);

			List<Object[]> cellData = getTableData().get(key);

			List<Object> list = this.getAllData(cellData);
			List<UUID> idArray = this.getAllUUIDs(cellData);
			if (getCurrentRow() >= 0 && getCurrentRow() <= list.size() - 1) {
				UUID cellUUID = (UUID) idArray.get(getCurrentRow());
				Object cellValue = list.get(getCurrentRow());
				UICell uiCell = new UICell(cellValue, cellUUID, cellType, ChangeEventType.ROW_EDITED, null, null);
				columnCells.add(uiCell);

				if (isEditable) {
					this.addStoredListener(uiCell);
				}
				uiCell.addPropertyChangeListener(this);

			}
			getContainer().addComponent(new TextField(x1, y, 200, 40, key.get(1).toString()));
			y += 50;
		}
		VerticalComponentList vcl = new VerticalComponentList(x2, getY() + 50, columnCells, 10);

		getContainer().addComponent(vcl);

		this.resetAllListeners();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
	}

	/**
	 * {@inheritDoc}
	 *
	 * Pauses the SubWindow at the given index with the given id.
	 * @param columnIndex The column index of component of the cause of the pause.
	 * @param columnId    The id of component of the cause of the pause.
	 *
	 */
	@Override
	public void pauseSubWindow(int columnIndex, UUID columnId) {
		UUID idOfErrorCell = getUUIDOfCell(columnIndex, columnId);
		UICell errorCell = getCellWithId(idOfErrorCell);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);
	}

	/**
	 * Returns the UUID of the data of the column with the given columnId at the given index.
	 * @param columnIndex  The index of the component in the column.
	 * @param columnId     The id of the column.
	 * @return the UUID of the data of the column with the given columnId at the given index.
	 */
	public UUID getUUIDOfCell(int columnIndex, UUID columnId) {
		for (List<Object> key : getTableData().keySet()) {
			if (key.contains(columnId)) {
				List<UUID> ids = this.getAllUUIDs(this.getTableData().get(key));
				return ids.get(columnIndex);
			}
		}
		return null;

	}

	/**
	 * Returns the RowsTable stored in the container of the SubWindow.
	 * @return The rowsTable inside the TableRowsWindow.
	 */
	private UICell getCellWithId(UUID searchCell) {
		for (Component c : getContainer().getComponentsList()) {
//			System.err.println(c);
			if (c instanceof VerticalComponentList) {
				VerticalComponentList vc = (VerticalComponentList) c;
				for (Component comp : vc.getComponentsList()) {
					if (comp instanceof UICell && ((UICell) comp).getId() == searchCell) {
						return (UICell) comp;
					}
				}

			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *  Resumes the window everything can be handled as normal.
	 */
	@Override
	public void resumeSubWindow() {
		this.setPaused(false);
		this.resetAllListeners();
	}

	/**
	 * {@inheritDoc}
	 * Updates the content of the SubWindow with the given tableData.
	 * @param tableData The data containing the data for updating the subWindow.
	 */
	@Override
	public void updateContent(Object... tableData) {
		super.updateContent(tableData);
		this.setTableData((Map<List<Object>, List<Object[]>>) tableData[2]);
		this.setComputed((boolean) tableData[3]);
		this.updateForm();
		this.setTableName(TITLE_STRING_PREFIX + (String) tableData[0] + ROW_STRING_PREFIX + getCurrentRow());
	}

	/**
	 * {@inheritDoc}
	 * @param id
	 *        | The id of the key pressed event.
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 * Checks which key is pressed and handles the control d and control N commands.
	 * 
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		super.keyPressed(id, keyCode, keyChar);

		if (keyCode == 34) {
			int old = String.valueOf(getCurrentRow()).length();
			try {
				setCurrentRow(getCurrentRow() + 1);
				this.setTableName(getTableName().substring(0, getTableName().length() - old) + getCurrentRow());
				updateForm();
				getSupport().firePropertyChange(new PropertyChangeEvent(ChangeEventType.REPAINT));
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		} else if (keyCode == 33) {
			int old = String.valueOf(getCurrentRow()).length();
			try {
				setCurrentRow(getCurrentRow() - 1);
				this.setTableName(getTableName().substring(0, getTableName().length() - old) + getCurrentRow());
				updateForm();
				getSupport().firePropertyChange(new PropertyChangeEvent(ChangeEventType.REPAINT));
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		}

		if (!isComputed()) {
			if (keyCode == KeyEvent.VK_CONTROL) {
				this.setCtrlPressed(true);
			} else if (keyCode == KeyEvent.VK_D && this.isCtrlPressed()) {
				deleteCurrentRow();
			} else if (keyCode == KeyEvent.VK_N && this.isCtrlPressed()) {
				createNewRow();
			} else {
				setCtrlPressed(false);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics g, boolean isActiveSubWindow) {
		g.setClip(getX(), getY(), getWidth(), getHeight());
		Color oldColor = g.getColor();
		if (isActiveSubWindow) {
			g.setColor(Color.LIGHT_GRAY);
		} else {
			g.setColor(new Color(225, 225, 225));
		}
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(oldColor);
		this.getTitleBar().paint((Graphics2D) g);
		for (Component component : getContainer().getComponentsList()) {
			component.paint((Graphics2D) g.create());
		}

		this.drawBorder((Graphics2D) g);

	}

	/**
	 * Creates a new row and updates the form.
	 */
	private void createNewRow() {
		this.getSupport()
				.firePropertyChange(new PropertyChangeEvent(this.getId(), ChangeEventType.CREATE_ROW, null, null));
		this.updateForm();
	}

	/**
	 * Deletes the current row and updates the form.
	 */
	private void deleteCurrentRow() {
			if( (this.getNbrOfRowsInData() > 0) && (getCurrentRow() >= 0) && getCurrentRow() <= (this.getNbrOfRowsInData()-1)) {
			
			
			List<UUID> list = null;
			for (List<Object> key : getTableData().keySet()) {
				list = this.getAllUUIDs(getTableData().get(key));
				break;
			}

			UUID deleteCellID = list.get(getCurrentRow());
			this.getSupport()
					.firePropertyChange(new PropertyChangeEvent(deleteCellID, ChangeEventType.DELETE_ROW, null, null));
		}
	}

	/**
	 * Return the index of the current row.
	 * @return the index of the current row.
	 */
	private int getCurrentRow() {
		return currentRow;
	}

	/**
	 * Sets the current row index.
	 * @param currentRow the index of the current row.
	 * @throws IllegalArgumentException when the index is invalid.
	 *         | currentRow < 0 || currentRow > amountOfRowsInData
	 */
	private void setCurrentRow(int currentRow) {
		int amountOfRowsInData = -1;

		for (List<Object> key : getTableData().keySet()) {
			amountOfRowsInData = this.getAllData(getTableData().get(key)).size();
			break;
		}

		if (currentRow < 0 || currentRow > amountOfRowsInData) {
			throw new IllegalArgumentException("Cannot set a negative current row or greater than the data +1");
		}
		this.currentRow = currentRow;
	}

	/**
	 * Returns all UUIDs out of the Computed Object
	 * @param data List where each object the first element is the UUID and the second is the correspondig data.
	 * @return All the UUIDs of the data.
	 */
	public List<UUID> getAllUUIDs(List<Object[]> data) {
		List<UUID> ids = new ArrayList<>();
		for (Object[] obj : data) {
			ids.add((UUID) obj[0]);
		}
		return ids;
	}

	/**
	 * Returns all Data out of the Computed Object
	 * @param data List where each object the first element is the UUID and the second is the correspondig data.
	 * @return All Data without the UUID.
	 */
	public List<Object> getAllData(List<Object[]> data) {
		List<Object> result = new ArrayList<>();
		for (Object[] obj : data) {
			result.add(obj[1]);
		}
		return result;
	}

	/**
	 * Returns the list of all the data shown in the table.
	 * @return the list of all the data shown in the table.
	 */
	private Map<List<Object>, List<Object[]>> getTableData() {
		return tableData;
	}

	/**
	 * Sets the list of all the data shown in the table.
	 * @param tableData the list of all the data shown in the table.
	 * @post the tableData variable is set.
	 *        | new.getTableData() = tableData.
	 */
	private void setTableData(Map<List<Object>, List<Object[]>> tableData) {
		this.tableData = tableData;
	}

	/**
	 * Returns whether the form is computed or not.
	 * @return True when the table form is computed; FALSE otherwise.
	 */
	private boolean isComputed() {
		return isComputed;
	}

	/**
	 * Sets the computed variable.
	 * @param isComputed Whether the data is computed or not.
	 * @post the variable is set.
	 *       | new.isComputed() == isComputed
	 */
	private void setComputed(boolean isComputed) {
		this.isComputed = isComputed;
	}
	
	/**
	 * Returns the number of rows the data contains.
	 * @return the number of rows the data contains.
	 */
	public int getNbrOfRowsInData() {
		int amountOfRowsInData = 0;

		for (List<Object> key : getTableData().keySet()) {
			amountOfRowsInData = this.getAllData(getTableData().get(key)).size();
			break;
		}
		return amountOfRowsInData;
	}

}
