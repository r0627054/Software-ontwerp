package usecases;

import static org.junit.Assert.assertFalse;
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
import ui.model.components.VerticalComponentList;

public class UseCase9Test extends UseCaseTest implements RowTableConstants {

	/**
	 * Test 1 : Editing a row
	 * | If you click a text field with valid text and press enter, the text 
	 * | should be displayed and saved.
	 */
	@Test
	public void test1editingATextFieldWithValidTextAndEnterPressShouldSave() {
		this.addDummyTable(NEW_TABLE_NAME);
		String tName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tName = entry.getValue();
			tableId = entry.getKey();
		}

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));

		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		emulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
		emulateKeyPress(EDIT_STRING_TEXT);
		emulateKeyPress(KeyEvent.VK_ENTER);

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

		int newTextCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			for (Map.Entry<UUID, Object> entry2 : entry.getValue().entrySet()) {
				if (String.valueOf(entry2.getValue()).contains(EDIT_STRING_TEXT)) {
					newTextCounter++;
				}
			}
		}
		assertEquals(0, newTextCounter);

		newTextCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapAfter.entrySet()) {
			for (Map.Entry<UUID, Object> entry2 : entry.getValue().entrySet()) {
				if (String.valueOf(entry2.getValue()).contains(EDIT_STRING_TEXT)) {
					newTextCounter++;
				}
			}
		}
		assertEquals(1, newTextCounter);

		assertTrue(etf.getText().contains(EDIT_STRING_TEXT));
		assertNotEquals(textBefore, etf.getText());
	}

	/**
	 * Test 2 : Editing a row
	 * | If you click a check box, the checked value should flip from true to false
	 * | and from false to true.
	 */
	@Test
	public void test2clickingACheckBoxShouldSave() {
		this.addDummyTable(NEW_TABLE_NAME);
		String tName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tName = entry.getValue();
			tableId = entry.getKey();
		}

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));
		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(1);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		CheckBox checkBox = (CheckBox) cellBefore.getComponent();
		boolean isCheckedStart = checkBox.isChecked();

		emulateSingleClick(SECOND_ROW_X, FIRST_ROW_Y);

		assertEquals(isCheckedStart, !checkBox.isChecked());
		assertFalse(checkBox.isError());
	}

	/**
	 * Test 3 : Editing a row
	 * | If you clear the text field while blanks are not allowed in the column, the application
	 * | should pause. Pressing escape should reset the text field and reset the value in memory.
	 */
	@Test
	public void test3clearingAStringTextFieldShouldPauseTheApplicationAndEscapeShouldResetIfTheColumnDoesntAllowBlanks() {
		this.addDummyTableNotEmptyDefaultColumnValueNoBlanksAllowed();

		String tName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tName = entry.getValue();
			tableId = entry.getKey();
		}

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));

		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		emulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 10);

		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertEquals(0, etf.getText().length());
		assertNotEquals(textBefore, etf.getText());
		assertTrue(etf.isError());

		emulateKeyPress(KeyEvent.VK_ESCAPE);

		assertEquals(textBefore, etf.getText());
		assertFalse(etf.isError());

	}

	/**
	 * Test 4 : Editing a row
	 * | An email text field with no blanks allowed needs exactly one '@' since
	 * | blank values aren't allowed.
	 */
	@Test
	public void test4clearingAEmailTextFieldWithNoBlanksAllowedShouldOnlyResumeWhenAnAtSignIsTyped() {
		this.addDummyTableNotEmptyEmailDefaultColumnValueNoBlanksAllowed();

		String tName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tName = entry.getValue();
			tableId = entry.getKey();
		}

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));

		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		emulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 10);

		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertEquals(0, etf.getText().length());
		assertNotEquals(textBefore, etf.getText());
		assertTrue(etf.isError());

		emulateKeyPress("test");
		assertTrue(etf.isError());

		emulateKeyPress("@");
		assertFalse(etf.isError());
	}

	/**
	 * Test 4 : Editing a row
	 * | An Integer text field never allows leading zeroes.
	 */
	@Test
	public void test5leadingZeroesShouldNotBeAllowedInAnIntegerTextField() {
		this.addDummyTableIntColumnStringCellValues();

		String tName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tName = entry.getValue();
			tableId = entry.getKey();
		}

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));

		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		emulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);

		emulateKeyPress("0123");

		emulateKeyPress(KeyEvent.VK_CONTROL);
		emulateKeyPress(KeyEvent.VK_ENTER);
		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertTrue(etf.isError());

		emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 5);
		assertFalse(etf.isError());

		emulateKeyPress(KeyEvent.VK_BACK_SPACE);
		assertFalse(etf.isError());

		emulateKeyPress("00");
		assertTrue(etf.isError());

		emulateKeyPress(KeyEvent.VK_BACK_SPACE);
		assertEquals(textBefore, etf.getText());
	}
}
