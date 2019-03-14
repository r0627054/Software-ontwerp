package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Cell;
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
		assertEquals(STRING ,uiTypeAfter);
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
	assertEquals(EMAIL ,uiTypeAfter);
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
		assertEquals(STRING ,uiTypeAfter);
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
//		System.out.println(typeBefore);
//		System.out.println(uiTypeBefore);
//		System.out.println(typeAfter);
//		System.out.println(uiTypeAfter);

		assertEquals(typeBefore, STRING);
		assertEquals(uiTypeBefore, STRING);

		assertEquals(typeAfter, BOOLEAN);
		assertEquals(uiTypeAfter, BOOLEAN);
	}

}
