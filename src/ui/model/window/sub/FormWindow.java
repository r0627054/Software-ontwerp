package ui.model.window.sub;

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
import ui.model.components.TextField;
import ui.model.components.UICell;

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

	private Map<List<Object>, LinkedHashMap<UUID, Object>> tableData;

	public FormWindow(UUID id, String tableName, Map<List<Object>, LinkedHashMap<UUID, Object>> tableData) {
		super(id, TITLE_STRING_PREFIX + tableName + ROW_STRING_PREFIX + 0);
		this.setTableData(tableData);
		updateForm();
	}

	private void updateForm() {
		this.setStoredListeners(new ArrayList<Component>());
		setContainer(new Container(getX(), getY(), getWidth(), getHeight()));
		int y = getY() + 50;
		int x1 = getX() + 50;
		int x2 = getX() + 100;
		
		

		for (List<Object> key : getTableData().keySet()) {  // vb select name row 
			
			Boolean isBoolean = key.get(key.size() -1).toString().contains("Boolean");
			
			LinkedHashMap<UUID, Object> cellData = getTableData().get(key);
			Set<UUID> idList = cellData.keySet();
			
			Object[] list = cellData.values().toArray();		
			Object[] idArray = (Object[]) idList.toArray();
			UUID cellUUID = getCurrentRow() >= 0 && getCurrentRow() <= list.length - 1 // vb get id of number 2
					? cellUUID = (UUID) idArray[getCurrentRow()]
					: UUID.randomUUID();


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
		this.resetAllListeners();
	}

	@Override
	public void throwError(UUID id, int columnIndex, Object newValue) {
	}

	@Override
	public void pauseSubWindow(int columnIndex, UUID columnId) {
	}

	@Override
	public void resumeSubWindow() {
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
