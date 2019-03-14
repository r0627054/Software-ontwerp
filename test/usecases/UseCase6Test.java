package usecases;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Cell;
import ui.model.components.CheckBox;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;
import ui.model.components.TextField;
import ui.model.components.ToggleTextField;
import ui.model.components.VerticalComponentList;

public class UseCase6Test extends UseCaseTest implements DesignTableConstants {

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

		emulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		emulateKeyPress(NEW_COLUMN_NAME);
		emulateKeyPress(KeyEvent.VK_ENTER);

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
		Cell cell = (Cell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertTrue(firstCell.getText().contains(NEW_COLUMN_NAME));
	}

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

		emulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		emulateKeyPress(NEW_COLUMN_NAME);
		emulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

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
		Cell cell = (Cell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertTrue(firstCell.getText().contains(NEW_COLUMN_NAME));
	}

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

		emulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);

		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

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
		Cell cell = (Cell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertEquals(0, firstCell.getText().length());

		emulateKeyPress(KeyEvent.VK_ESCAPE);

		assertEquals(columnDataBefore, this.getDomainFacade().getColumnCharacteristics(tableId));
		assertEquals(uiRowsBefore, getTableViewModeDesignTable(tableId).getRows());
	}

	@Test
	public void test4clickingColumnNameAndRemovingAllTextShouldPauseTheApplicationAndPressingEscapeShouldReset() {
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

		emulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);

		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

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
		Cell cell = (Cell) hzcl.getComponentsList().get(0);
		EditableTextField firstCell = (EditableTextField) cell.getComponent();
		assertEquals(0, firstCell.getText().length());

		emulateKeyPress(KeyEvent.VK_ESCAPE);

		assertEquals(columnDataBefore, this.getDomainFacade().getColumnCharacteristics(tableId));
		assertEquals(uiRowsBefore, getTableViewModeDesignTable(tableId).getRows());
	}

	public void test5clickingColumnTypeShouldPauseApplicationWhenConflictingValuesAndShouldNotChangeValueInDomain() {
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

		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertEquals(columnDataBefore, this.getDomainFacade().getColumnCharacteristics(tableId));
		assertEquals(uiRowsBefore, getTableViewModeDesignTable(tableId).getRows());
	}

	@Test
	public void test6clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);
		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);
		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(1);
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

	@Test
	public void test7clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(1);
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

	@Test
	public void test8clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(1);
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

	@Test
	public void test9clickingColumnTypeShouldChangeTypeIfDefaultValueAndRowValuesAreValid() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(1);
		ToggleTextField typeField = (ToggleTextField) cell.getComponent();
		String uiTypeBefore = typeField.getText();

		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);
		emulateSingleClick(COLUMN_TYPE_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(1);
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

	@Test
	public void test10disablingAllowBlanksShouldNotSucceedAndPauseTheApplicationIfTableHasBlanks() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		emulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		emulateKeyPress(KeyEvent.VK_DELETE);
		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateKeyPress(KeyEvent.VK_ESCAPE);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		Boolean blanksBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean blanksAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksAfter = (Boolean) mapEntry.getValue();
			}
		}
//		System.out.println(typeBefore);
//		System.out.println(uiTypeBefore);
//		System.out.println(typeAfter);
//		System.out.println(uiTypeAfter);

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}

	@Test
	public void test11disablingAllowBlanksShouldNotSucceedIfTableHasBlanks() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		emulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		Boolean blanksBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean blanksAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksAfter = (Boolean) mapEntry.getValue();
			}
		}

//		System.out.println(typeBefore);
//		System.out.println(uiTypeBefore);
//		System.out.println(typeAfter);
//		System.out.println(uiTypeAfter);

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}

	@Test
	public void test12disablingAllowBlanksShouldNotSucceedIfTableHasBlanks() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		emulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		Boolean blanksBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean blanksAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}

	@Test
	public void test13disablingAllowBlanksShouldNotSucceedIfTableHasBlanks() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		emulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		Boolean blanksBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean blanksAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksAfter = (Boolean) mapEntry.getValue();
			}
		}

		assertEquals(uiAllowsBefore, !uiAllowsAfter);
		assertEquals(columnDataBefore, columnDataAfter);
	}

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
		Cell cell = (Cell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		emulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		Boolean blanksBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean blanksAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksAfter = (Boolean) mapEntry.getValue();
			}
		}

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

	@Test
	public void test15disablingAllowBlanksSucceedIfTableHasNoBlanksAndDefaultValueIsNotBlank() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(2);
		CheckBox checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsBefore = checkBox.isChecked();

		emulateSingleClick(COLUMN_BLANKS_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(2);
		checkBox = (CheckBox) cell.getComponent();
		Boolean uiAllowsAfter = checkBox.isChecked();

		Boolean blanksBefore = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataBefore.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksBefore = (Boolean) mapEntry.getValue();
			}
		}

		Boolean blanksAfter = null;
		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			for (Map.Entry<String, Object> mapEntry : entry.getValue().entrySet()) {
				if (mapEntry.getKey().equals(COLUMN_ALLOW_BLANKS))
					blanksAfter = (Boolean) mapEntry.getValue();
			}
		}

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

	@Test
	public void test16changingTheDefaultValueToAnInvalidValueShouldPauseTheApplicationAndEscapeShouldResetTheValue() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);

		emulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

		emulateKeyPress(KeyEvent.VK_ESCAPE);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test17changingBooleanDefaultValueShouldChangeValuesCorrectlyWithBlanksAllowed() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		Boolean uiDefaultBefore = Boolean.parseBoolean(textField.getText());

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test18changingBooleanDefaultValueShouldChangeValuesCorrectlyWithBlanksNotAllowed() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		Boolean uiDefaultBefore = Boolean.parseBoolean(textField.getText());

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);

		columnDataAfter = this.getDomainFacade().getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test19changingCorrectIntDefaultValueShouldChangeValuesCorrectlyToInt() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateKeyPress(KeyEvent.VK_BACK_SPACE);
		emulateKeyPress("123");

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test20changingWrongIntegerDefaultValueShouldNotSetValue() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateKeyPress("123");

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test21changingCorrectEmailDefaultValueShouldSetValue() {
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
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
		TextField textField = (TextField) cell.getComponent();
		String uiDefaultBefore = textField.getText();

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
		emulateKeyPress(EXAMPLE_EMAIL);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows().getComponentsList().get(1);
		cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test22changingInvalidEmailDefaultValueShouldNotSetValue() {
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

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
		emulateKeyPress(NEW_COLUMN_NAME);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
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

	@Test
	public void test23changingBlankEmailDefaultValueShouldSetValueIfColumnAllowsBlanks() {
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

		emulateSingleClick(COLUMN_DEFAULT_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
		emulateKeyPress(KeyEvent.VK_ENTER);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		HorizontalComponentList hzcl = (HorizontalComponentList) getTableViewModeDesignTable(tableId).getRows()
				.getComponentsList().get(1);
		Cell cell = (Cell) hzcl.getComponentsList().get(3);
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
