package ui.model.window.sub;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.swing.text.TabExpander;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.RowsTable;
import ui.model.components.TextField;
import ui.model.components.UICell;
import ui.model.components.VerticalComponentList;

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

	private boolean isComputed;

	private List<Component> columnCells;

	private Map<List<Object>, List<Object[]>> tableData;

	public FormWindow(UUID id, String tableName, Map<List<Object>, List<Object[]>> tableData, boolean isComputed) {
		super(id, TITLE_STRING_PREFIX + tableName + ROW_STRING_PREFIX + 0);
		this.setTableData(tableData);
		this.setComputed(isComputed);
		updateForm();
	}

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
		VerticalComponentList vcl = new VerticalComponentList(x2, getY() + 50, columnCells);

		getContainer().addComponent(vcl);

		this.resetAllListeners();
	}

	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
	}

	@Override
	public void pauseSubWindow(int columnIndex, UUID columnId) {
		UUID idOfErrorCell = getUUIDOfCell(columnIndex, columnId);
		UICell errorCell = getCellWithId(idOfErrorCell);
		errorCell.setError(true);
		this.removeAllContentListenersButOne(errorCell);
		this.setPaused(true);
	}

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

	@Override
	public void resumeSubWindow() {
		this.setPaused(false);
		this.resetAllListeners();
	}

	@Override
	public void updateContent(Object... tableData) {
		super.updateContent(tableData);
		this.setTableData((Map<List<Object>, List<Object[]>>) tableData[2]);
		this.setComputed((boolean) tableData[3]);
		this.updateForm();
		this.setTableName(TITLE_STRING_PREFIX + (String) tableData[0] + ROW_STRING_PREFIX + getCurrentRow());
	}

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

	private void createNewRow() {
		this.getSupport()
				.firePropertyChange(new PropertyChangeEvent(this.getId(), ChangeEventType.CREATE_ROW, null, null));
		this.updateForm();
	}

	private void deleteCurrentRow() {
		//if (getCurrentRow() >= 0 && getCurrentRow() < this.getTableData().keySet().size()
		//		&& this.getTableData().keySet().size() > 0) {
		//niet leeg
		//groter dan 0'de element
		//
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

	private int getCurrentRow() {
		return currentRow;
	}

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

	public List<UUID> getAllUUIDs(List<Object[]> data) {
		List<UUID> ids = new ArrayList<>();
		for (Object[] obj : data) {
			ids.add((UUID) obj[0]);
		}
		return ids;
	}

	public List<Object> getAllData(List<Object[]> data) {
		List<Object> result = new ArrayList<>();
		for (Object[] obj : data) {
			result.add(obj[1]);
		}
		return result;
	}

	private Map<List<Object>, List<Object[]>> getTableData() {
		return tableData;
	}

	private void setTableData(Map<List<Object>, List<Object[]>> tableData) {
		this.tableData = tableData;
	}

	private boolean isComputed() {
		return isComputed;
	}

	private void setComputed(boolean isComputed) {
		this.isComputed = isComputed;
	}
	
	public int getNbrOfRowsInData() {
		int amountOfRowsInData = 0;

		for (List<Object> key : getTableData().keySet()) {
			amountOfRowsInData = this.getAllData(getTableData().get(key)).size();
			break;
		}
		return amountOfRowsInData;
	}

}
