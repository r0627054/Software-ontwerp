package usecases;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.UICell;
import ui.model.components.CheckBox;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;
import ui.model.components.TextField;
import ui.model.components.ToggleTextField;
import ui.model.components.VerticalComponentList;

public class UseCase6Test extends UseCaseTest implements DesignTableConstants {
	
	/**
	 * Test 1 : Editing the column name
	 * | When you select the column name and edit it without leaving a blank or writing the same column name twice,
	 * | after pressing enter the name should be shown and saved correctly.
	 */
	@Test
	public void test1clickingColumnNameAndEditingItCorrectlyAndPressingEnterShouldSetName() {
		addDummyTable(NEW_TABLE_NAME);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();

		simulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		simulateKeyPress(NEW_COLUMN_NAME);
		simulateKeyPress(KeyEvent.VK_ENTER);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		assertEquals(columnDataBefore.size(), columnDataAfter.size());
		assertEquals(uiRowsBefore.getComponentsList().size(), uiRowsAfter.getComponentsList().size());

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			UUID columnId = entry.getKey();

			if (!columnDataBefore.get(columnId).equals(columnDataAfter.get(columnId))) {

				for (Map.Entry<String, Object> columnEntry : entry.getValue().entrySet()) {
					if (columnEntry.getKey().equals(COLUMN_NAME)) {
						String columnName = (String) columnEntry.getValue();
						assertTrue(columnName.contains(NEW_COLUMN_NAME));
					}
				}
			}
		}

		HorizontalComponentList hzcl = (HorizontalComponentList) uiRowsAfter.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertTrue(firstCell.getText().contains(NEW_COLUMN_NAME));
	}

	/**
	 * Test 2 : Editing the column name
	 * | When you select the column name and edit it without leaving a blank or writing the same column name twice,
	 * | after clicking out of the text field the name should be shown and saved correctly.
	 */
	@Test
	public void test2clickingColumnNameAndEditingItCorrectlyAndClickingOutOfTextFieldShouldSetName() {
		addDummyTable(NEW_TABLE_NAME);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();

		simulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		simulateKeyPress(NEW_COLUMN_NAME);
		simulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		assertEquals(columnDataBefore.size(), columnDataAfter.size());
		assertEquals(uiRowsBefore.getComponentsList().size(), uiRowsAfter.getComponentsList().size());

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			UUID columnId = entry.getKey();

			if (!columnDataBefore.get(columnId).equals(columnDataAfter.get(columnId))) {

				for (Map.Entry<String, Object> columnEntry : entry.getValue().entrySet()) {
					if (columnEntry.getKey().equals(COLUMN_NAME)) {
						String columnName = (String) columnEntry.getValue();
						assertTrue(columnName.contains(NEW_COLUMN_NAME));
					}
				}
			}
		}

		HorizontalComponentList hzcl = (HorizontalComponentList) uiRowsAfter.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertTrue(firstCell.getText().contains(NEW_COLUMN_NAME));
	}

	/**
	 * Test 3 : Editing the column name
	 * | When you select the column name and clear the text field, the application should pause.
	 * | You should only be able to edit the textfield or press escape to reset the value to the value when you clicked on the textfield.
	 * | After pressing escape, the old value should be saved.
	 */
	@Test
	public void test3clickingColumnNameAndRemovingAllTextShouldPauseTheApplicationAndPressingEscapeShouldReset() {
		addDummyTable(NEW_TABLE_NAME);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();

		simulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);

		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		simulateKeyPress(KeyEvent.VK_CONTROL);
		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		assertEquals(columnDataBefore.size(), columnDataAfter.size());
		assertEquals(uiRowsBefore.getComponentsList().size(), uiRowsAfter.getComponentsList().size());

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			UUID columnId = entry.getKey();

			if (!columnDataBefore.get(columnId).equals(columnDataAfter.get(columnId))) {

				for (Map.Entry<String, Object> columnEntry : entry.getValue().entrySet()) {
					if (columnEntry.getKey().equals(COLUMN_NAME)) {
						String columnName = (String) columnEntry.getValue();
						assertEquals(1, columnName.length());
					}
				}
			}
		}

		HorizontalComponentList hzcl = (HorizontalComponentList) uiRowsAfter.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertEquals(0, firstCell.getText().length());

		simulateKeyPress(KeyEvent.VK_ESCAPE);

		assertEquals(columnDataBefore, this.getDomainFacade().getColumnCharacteristics(tableId));
		assertEquals(uiRowsBefore, getTableViewModeDesignTable(tableId).getRows());
	}

	/**
	 * Test 4 : Editing the column name
	 * | When you select the column name and change the text field to an already existing column name,
	 * | the application should be paused. The newest value should not be saved but is still shown in the UI.
	 */
	public void test4clickingColumnTypeShouldPauseApplicationWhenConflictingValuesAndShouldNotChangeValueInDomain() {
		addDummyTableEmailColumnEmailCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();

		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		simulateKeyPress(KeyEvent.VK_CONTROL);
		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertEquals(columnDataBefore, this.getDomainFacade().getColumnCharacteristics(tableId));
		assertEquals(uiRowsBefore, getTableViewModeDesignTable(tableId).getRows());
	}
	
	/**
	 * Test 5 : Editing the column type (EMAIL -> STRING)
	 * | When you select to edit the column type (with correct values in the table data)
	 * | the column type should change to the next value. When all the table data and column default value
	 * | can be set to this type, the type is saved and shown without error in the UI.
	 * | 
	 */
	@Test
	public void test5clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
		addDummyTableEmailColumnEmailCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);
		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);
		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(1);
		typeField = (ToggleTextField) cell.getComponent();
		String uiTypeAfter = typeField.getText();

		String typeBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeBefore = (String) mapEntry.getValue();
			}
		}

		String typeAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeAfter = (String) mapEntry.getValue();
			}
		}

		assertEquals(EMAIL, typeBefore);
		assertEquals(EMAIL, uiTypeBefore);

		assertEquals(STRING, typeAfter);
		assertEquals(STRING, uiTypeAfter);
	}

	/**
	 * Test 6 : Editing the column type (STRING -> EMAIL)
	 * | When you select to edit the column type (with correct values in the table data)
	 * | the column type should change to the next value. When all the table data and column default value
	 * | can be set to this type, the type is saved and shown without error in the UI.
	 * | 
	 */
	@Test
	public void test6clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
		addDummyTableStringColumnEmailCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(1);
		typeField = (ToggleTextField) cell.getComponent();
		String uiTypeAfter = typeField.getText();

		String typeBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeBefore = (String) mapEntry.getValue();
			}
		}

		String typeAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeAfter = (String) mapEntry.getValue();
			}
		}

		assertEquals(STRING, typeBefore);
		assertEquals(STRING, uiTypeBefore);

		assertEquals(EMAIL, typeAfter);
		assertEquals(EMAIL, uiTypeAfter);
	}

	/**
	 * Test 7 : Editing the column type (INTEGER -> STRING)
	 * | When you select to edit the column type (with correct values in the table data)
	 * | the column type should change to the next value. When all the table data and column default value
	 * | can be set to this type, the type is saved and shown without error in the UI.
	 * | 
	 */
	@Test
	public void test7clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
		addDummyTableIntColumnStringCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(1);
		typeField = (ToggleTextField) cell.getComponent();
		String uiTypeAfter = typeField.getText();

		String typeBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeBefore = (String) mapEntry.getValue();
			}
		}

		String typeAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeAfter = (String) mapEntry.getValue();
			}
		}

		assertEquals(INTEGER, typeBefore);
		assertEquals(INTEGER, uiTypeBefore);

		assertEquals(STRING, typeAfter);
		assertEquals(STRING, uiTypeAfter);
	}

	/**
	 * Test 8 : Editing the column type (STRING -> BOOLEAN)
	 * | When you select to edit the column type (with correct values in the table data)
	 * | the column type should change to the next value. When all the table data and column default value
	 * | can be set to this type, the type is saved and shown without error in the UI.
	 * | 
	 */
	@Test
	public void test8clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
		addDummyTableStringColumnStringBoolanValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);
		simulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(1);
		typeField = (ToggleTextField) cell.getComponent();
		String uiTypeAfter = typeField.getText();

		String typeBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeBefore = (String) mapEntry.getValue();
			}
		}

		String typeAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_TYPE))
					typeAfter = (String) mapEntry.getValue();
			}
		}

		assertEquals(STRING, typeBefore);
		assertEquals(STRING, uiTypeBefore);

		assertEquals(BOOLEAN, typeAfter);
		assertEquals(BOOLEAN, uiTypeAfter);
	}

	/**
	 * Test 9 : Editing if the column allows blank values.
	 * | When you toggle the allows blanks checkbox from true to false while you still have blank values
	 * | inside the column default value or table data, an error should be shown and the application should be paused.
	 * | You should only be able to click on the checkbox again to proceed.
	 * | This test: table data has null String values.
	 */
	@Test
	public void test9disablingAllowBlanksShouldNotSucceedAndPauseTheApplicationIfTableHasBlanks() {
		addDummyTableStringColumnNullCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		simulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		simulateKeyPress(KeyEvent.VK_DELETE);
		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateKeyPress(KeyEvent.VK_ESCAPE);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}


	/**
	 * Test 10 : Editing if the column allows blank values.
	 * | When you toggle the allows blanks checkbox from true to false while you still have blank values
	 * | inside the column default value or table data, an error should be shown and the application should be paused.
	 * | You should only be able to click on the checkbox again to proceed.
	 * | This test: table data has null email values.
	 */
	@Test
	public void test10disablingAllowBlanksShouldNotSucceedIfTableHasBlanks() {
		addDummyTableEmailColumnNullCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		simulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}

	/**
	 * Test 11 : Editing if the column allows blank values.
	 * | When you toggle the allows blanks checkbox from true to false while you still have blank values
	 * | inside the column default value or table data, an error should be shown and the application should be paused.
	 * | You should only be able to click on the checkbox again to proceed.
	 * | This test: table data has null boolean values.
	 */
	@Test
	public void test11disablingAllowBlanksShouldNotSucceedIfTableHasBlanks() {
		addDummyTableBooleanColumnNullCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		simulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}

	/**
	 * Test 12 : Editing if the column allows blank values.
	 * | When you toggle the allows blanks checkbox from true to false while you still have blank values
	 * | inside the column default value or table data, an error should be shown and the application should be paused.
	 * | You should only be able to click on the checkbox again to proceed.
	 * | This test: table data has null integer values.
	 */
	@Test
	public void test12disablingAllowBlanksShouldNotSucceedIfTableHasBlanks() {
		addDummyTableIntegerColumnNullCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		simulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}
	
	/**
	 * Test 13 : Editing if the column allows blank values.
	 * | When you toggle the allows blanks checkbox from false to true, this should always be possible
	 * | since you're broadening the constraints. This should be shown in the ui and saved.
	 */
	@Test
	public void test14disablingAllowBlanksSucceedIfTableHasNoBlanks() {
		addDummyTableEmailColumnEmailCellValues();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		simulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		assertEquals(uiAllowsBefore, !uiAllowsAfter);

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			UUID columnId = entry.getKey();

			if (!columnDataBefore.get(columnId).equals(columnDataAfter.get(columnId))) {

				for (Map.Entry<String, Object> columnEntry : entry.getValue().entrySet()) {
					if (columnEntry.getKey().equals(COLUMN_ALLOW_BLANKS)) {
						Boolean changedAllowBlanks = (Boolean) columnEntry.getValue();
						assertEquals(false, changedAllowBlanks);
					}
				}
			}
		}
	}

	/**
	 * Test 14 : Editing if the column allows blank values.
	 * | When you toggle the allows blanks checkbox from false to true, this should always be possible
	 * | since you're broadening the constraints. This should be shown in the ui and saved.
	 */
	@Test
	public void test14disablingAllowBlanksSucceedIfTableHasNoBlanksAndDefaultValueIsNotBlank() {
		addDummyTableStringColumnEmailCellValuesNotEmptyDefaultColumnValue();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		simulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		assertEquals(uiAllowsBefore, !uiAllowsAfter);

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			UUID columnId = entry.getKey();

			if (!columnDataBefore.get(columnId).equals(columnDataAfter.get(columnId))) {

				for (Map.Entry<String, Object> columnEntry : entry.getValue().entrySet()) {
					if (columnEntry.getKey().equals(COLUMN_ALLOW_BLANKS)) {
						Boolean changedAllowBlanks = (Boolean) columnEntry.getValue();
						assertEquals(false, changedAllowBlanks);
					}
				}
			}
		}
	}

	/**
	 * Test 15 : Editing the default value.
	 * | When you select the column default value and clear the text field, the application should pause.
	 * | You should only be able to edit the text field or press escape to reset the value to the value when you clicked on the text field.
	 * | After pressing escape, the old value should be saved.
	 */
	@Test
	public void test15changingTheDefaultValueToAnInvalidValueShouldPauseTheApplicationAndEscapeShouldResetTheValue() {
		addDummyTableNotEmptyDefaultColumnValueNoBlanksAllowed();

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);

		simulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		simulateKeyPress(KeyEvent.VK_CONTROL);
		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		String uiDefaultAfter = textField.getText();

		String defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (String) mapEntry.getValue();
			}
		}

		String defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (String) mapEntry.getValue();
			}
		}

		assertNotEquals(defaultBefore, defaultAfter);
		assertNotEquals(uiDefaultBefore, uiDefaultAfter);

		simulateKeyPress(KeyEvent.VK_ESCAPE);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		uiDefaultAfter = textField.getText();

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (String) mapEntry.getValue();
			}
		}

		assertEquals(defaultBefore, defaultAfter);
		assertEquals(uiDefaultBefore, uiDefaultAfter);
	}

	/**
	 * Test 16 : Editing the default value.
	 * | When the current column type is Boolean, the column default value should be a toggle on click.
	 * | If you enabled blank values, a blank value should be possible to select. If not, only
	 * | true and false should be selectable.
	 */
	@Test
	public void test16changingBooleanDefaultValueShouldChangeValuesCorrectlyWithBlanksAllowed() {
		addDummyEmptyTableBooleanColumnVariableAllowsBlank(true);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		Boolean uiDefaultBefore = Boolean.parseBoolean(textField.getText());

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		Boolean uiDefaultAfter = Boolean.parseBoolean(textField.getText());

		Boolean defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(defaultBefore, !defaultAfter);
		assertEquals(uiDefaultBefore, !uiDefaultAfter);

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		String uiDefaultAfterEmptyString = textField.getText();

		defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(null, defaultAfter);
		assertEquals("", uiDefaultAfterEmptyString);

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		uiDefaultAfter = Boolean.parseBoolean(textField.getText());

		defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(defaultBefore, defaultAfter);
		assertEquals(uiDefaultBefore, uiDefaultAfter);
	}

	/**
	 * Test 17 : Editing the default value.
	 * | When the current column type is Boolean, the column default value should be a toggle on click.
	 * | If you enabled blank values, a blank value should be possible to select. If not, only
	 * | true and false should be selectable.
	 */
	@Test
	public void test17changingBooleanDefaultValueShouldChangeValuesCorrectlyWithBlanksNotAllowed() {
		addDummyEmptyTableBooleanColumnVariableAllowsBlank(false);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		Boolean uiDefaultBefore = Boolean.parseBoolean(textField.getText());

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		Boolean uiDefaultAfter = Boolean.parseBoolean(textField.getText());

		Boolean defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(defaultBefore, !defaultAfter);
		assertEquals(uiDefaultBefore, !uiDefaultAfter);

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		uiDefaultAfter = Boolean.parseBoolean(textField.getText());

		defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(defaultBefore, defaultAfter);
		assertEquals(uiDefaultBefore, uiDefaultAfter);
	}

	/**
	 * Test 18 : Editing the default value.
	 * | When the current column type is Integer, the column default should be editable in a textfield.
	 * | When clicked, the user can edit the value and should be saved if it is a valid integer.
	 * | If there are any non-numeric values or the textfield has a floating 0, 
	 * | an error should be shown and the application should be paused.
	 */
	@Test
	public void test18changingCorrectIntDefaultValueShouldChangeValuesCorrectlyToInt() {
		addDummyEmptyIntegerColumnWithVariableAllowsBlank(false);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateKeyPress(KeyEvent.VK_BACK_SPACE);
		simulateKeyPress("123");
		simulateKeyPress(KeyEvent.VK_ENTER);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		String uiDefaultAfter = textField.getText();

		Integer defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (Integer) mapEntry.getValue();
			}
		}

		Integer defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Integer) mapEntry.getValue();
			}
		}

		assertEquals(0, defaultBefore.intValue());
		assertEquals("0", uiDefaultBefore);
		assertEquals(123, defaultAfter.intValue());
		assertEquals("123", uiDefaultAfter);
	}

	/**
	 * Test 19 : Editing the default value.
	 * | When the current column type is Integer, the column default should be editable in a textfield.
	 * | When clicked, the user can edit the value and should be saved if it is a valid integer.
	 * | If there are any non-numeric values or the textfield has a floating 0, 
	 * | an error should be shown and the application should be paused.
	 */
	@Test
	public void test19changingWrongIntegerDefaultValueShouldNotSetValue() {
		addDummyEmptyIntegerColumnWithVariableAllowsBlank(false);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateKeyPress("123");

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		String uiDefaultAfter = textField.getText();

		Integer defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (Integer) mapEntry.getValue();
			}
		}

		Integer defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (Integer) mapEntry.getValue();
			}
		}

		assertEquals(defaultBefore.intValue(), defaultAfter.intValue());
		assertEquals("0", uiDefaultBefore);
		assertEquals("0123", uiDefaultAfter);
	}

	/**
	 * Test 20 : Editing the default value.
	 * | When the current column type is Email, the column default should be editable in a textfield.
	 * | When clicked, the user can edit the value and should be saved if it is a valid integer.
	 * | Extactly one '@' should be required to be valid if the column does not allow blanks.
	 * | Otherwise, an empty string is valid aswell.
	 */
	@Test
	public void test20changingCorrectEmailDefaultValueShouldSetValue() {
		addDummyEmptyTableEmailColumnVariableAllowsBlank(false);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
		simulateKeyPress(EXAMPLE_EMAIL);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (UICell) hzcl.getComponentsList().get(3);
		textField = (TextField) cell.getComponent();
		String uiDefaultAfter = textField.getText();

		String defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (String) mapEntry.getValue();
			}
		}

		String defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (String) mapEntry.getValue();
			}
		}
		assertEquals(defaultBefore, uiDefaultBefore);
		assertEquals(defaultAfter, uiDefaultAfter);
	}

	/**
	 * Test 21 : Editing the default value.
	 * | When the current column type is Email, the column default should be editable in a textfield.
	 * | When clicked, the user can edit the value and should be saved if it is a valid integer.
	 * | Extactly one '@' should be required to be valid if the column does not allow blanks.
	 * | Otherwise, an empty string is valid aswell.
	 */
	@Test
	public void test21changingInvalidEmailDefaultValueShouldNotSetValue() {
		addDummyEmptyTableEmailColumnVariableAllowsBlank(false);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
		simulateKeyPress(NEW_COLUMN_NAME);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultAfter = textField.getText();

		String defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (String) mapEntry.getValue();
			}
		}

		String defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (String) mapEntry.getValue();
			}
		}
		assertEquals(defaultBefore, defaultAfter);
		assertNotEquals(NEW_COLUMN_NAME, defaultAfter);
		assertEquals(NEW_COLUMN_NAME, uiDefaultAfter);
	}

	/**
	 * Test 22 : Editing the default value.
	 * | When the current column type is Email, the column default should be editable in a textfield.
	 * | When clicked, the user can edit the value and should be saved if it is a valid integer.
	 * | Extactly one '@' should be required to be valid if the column does not allow blanks.
	 * | Otherwise, an empty string is valid aswell.
	 */
	@Test
	public void test22changingBlankEmailDefaultValueShouldSetValueIfColumnAllowsBlanks() {
		addDummyEmptyTableEmailColumnVariableAllowsBlank(true);

		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		simulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
		simulateKeyPress(KeyEvent.VK_ENTER);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		UICell cell = (UICell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultAfter = textField.getText();

		String defaultBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultBefore = (String) mapEntry.getValue();
			}
		}

		String defaultAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_DEFAULT))
					defaultAfter = (String) mapEntry.getValue();
			}
		}
		assertNotEquals(defaultBefore, defaultAfter);
		assertEquals("", uiDefaultAfter);
		assertEquals(null, defaultAfter);
	}

}
