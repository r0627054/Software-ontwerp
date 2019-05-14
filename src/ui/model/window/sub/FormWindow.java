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
import java.util.Set;
import java.util.UUID;

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
	
	private List<Component> columnCells;

	private Map<List<Object>, LinkedHashMap<UUID, Object>> tableData;

	public FormWindow(UUID id, String tableName, Map<List<Object>, LinkedHashMap<UUID, Object>> tableData) {
		super(id, TITLE_STRING_PREFIX + tableName + ROW_STRING_PREFIX + 0);
		this.setTableData(tableData);
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
			
			LinkedHashMap<UUID, Object> cellData = getTableData().get(key);
			Set<UUID> idList = cellData.keySet();

			Object[] list = cellData.values().toArray();
			Object[] idArray = (Object[]) idList.toArray();
			if(getCurrentRow() >= 0 && getCurrentRow() <=list.length - 1) {
				UUID cellUUID = (UUID) idArray[getCurrentRow()];
				Object cellValue = list[getCurrentRow()];
				UICell uiCell = new UICell(cellValue, cellUUID, cellType, ChangeEventType.ROW_EDITED, null,
						null);
				columnCells.add(uiCell);
				this.addStoredListener(uiCell);
				uiCell.addPropertyChangeListener(this);
				
			}
			getContainer().addComponent(new TextField(x1, y, 200, 40, key.get(1).toString()));

			String cellValue = getCurrentRow() >= 0 && getCurrentRow() <= list.length - 1 // vb get value of number 2
					? cellValue = String.valueOf(list[getCurrentRow()])
					: "";

			if(isBoolean) {
				CheckBox cb = new CheckBox(x2, y, 200, 40, Boolean.valueOf(cellValue), cellUUID, ChangeEventType.ROW_EDITED);
				getContainer().addComponent(cb);
				this.addStoredListener(cb);
				cb.addPropertyChangeListener(this);
			}
			else {
				EditableTextField etf = new EditableTextField(x2, y, 200, 40, cellValue, cellUUID,ChangeEventType.ROW_EDITED , null, null);
				getContainer().addComponent(etf);
				this.addStoredListener(etf);
				etf.addPropertyChangeListener(this);
			}


			

			y += 40;
		}
		VerticalComponentList vcl = new VerticalComponentList(x2,getY() + 50, columnCells);
		
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
		Object[] x= this.tableData.keySet().toArray();
		for (List<Object> key : getTableData().keySet()) {  
			if(key.contains(columnId)) return (UUID) this.tableData.get(key).keySet().toArray()[columnIndex];			
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
				for(Component comp: vc.getComponentsList()) {
						if(comp instanceof UICell && ((UICell) comp).getId() == searchCell) {
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
		this.setTableData((Map<List<Object>, LinkedHashMap<UUID, Object>>) tableData[2]);
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

	
	private void createNewRow() {
		this.getSupport().firePropertyChange(new PropertyChangeEvent(this.getId(), ChangeEventType.CREATE_ROW, null, null));
		this.updateForm();
	}

	private void deleteCurrentRow() {
		UICell deleteCell = null;

		Set<UUID> keyList = null;
		for (Component c : getContainer().getComponentsList()) {

			for (List<Object> key : getTableData().keySet()) {

				LinkedHashMap<UUID, Object> cellData = getTableData().get(key);
				Object[] list = cellData.values().toArray();
				keyList = cellData.keySet();
				break;
			}
		}

		UUID deleteCellID = keyList.iterator().next();

		this.getSupport().firePropertyChange(new PropertyChangeEvent(deleteCellID,ChangeEventType.DELETE_ROW, null, null));


	}

	private int getCurrentRow() {
		return currentRow;
	}

	private void setCurrentRow(int currentRow) {
		int amountOfRowsInData = -1;

		for (List<Object> key : getTableData().keySet()) {
			amountOfRowsInData = getTableData().get(key).values().size();
			break;
		}

		if (currentRow < 0 || currentRow > amountOfRowsInData) {
			throw new IllegalArgumentException("Cannot set a negative current row or greater than the data +1");
		}
		this.currentRow = currentRow;
	}

	private Map<List<Object>, LinkedHashMap<UUID, Object>> getTableData() {
		return tableData;
	}

	private void setTableData(Map<List<Object>, LinkedHashMap<UUID, Object>> tableData) {
		this.tableData = tableData;
	}

}
