package usecases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

		Map<List<Object>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
//		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));
		getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore);
		
		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
		simulateKeyPress(EDIT_STRING_TEXT);
		simulateKeyPress(KeyEvent.VK_ENTER);

		Map<List<Object>, LinkedHashMap<UUID, Object>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

		int newTextCounter = 0;
		for (Entry<List<Object>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			for (Map.Entry<UUID, Object> entry2 : entry.getValue().entrySet()) {
				if (String.valueOf(entry2.getValue()).contains(EDIT_STRING_TEXT)) {
					newTextCounter++;
				}
			}
		}
		assertEquals(0, newTextCounter);

		newTextCounter = 0;
		for (Entry<List<Object>, LinkedHashMap<UUID, Object>> entry : dataMapAfter.entrySet()) {
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

		Map<List<Object>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
//		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));
		getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore);
		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		


		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(1);
		
		
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		CheckBox checkBox = (CheckBox) cellBefore.getComponent();
		boolean isCheckedStart = checkBox.isChecked();
		
//		System.out.println(checkBox.isError());

		simulateSingleClick(SECOND_ROW_X, FIRST_ROW_Y);
		
		VerticalComponentList verticalCompListAfter = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(1);
		UICell cellAfter = (UICell) verticalCompListBefore.getComponentsList().get(1);
		CheckBox checkBoxx = (CheckBox) cellBefore.getComponent();
//		System.out.println(checkBoxx.isChecked());

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

		Map<List<Object>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
//		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));
		getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore);
		
		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 10);

		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateKeyPress(KeyEvent.VK_CONTROL);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertEquals(0, etf.getText().length());
		assertNotEquals(textBefore, etf.getText());
		assertTrue(etf.isError());

		simulateKeyPress(KeyEvent.VK_ESCAPE);

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

		Map<List<Object>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
//		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));
		getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore);
		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 10);

		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateKeyPress(KeyEvent.VK_CONTROL);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		assertEquals(0, etf.getText().length());
		assertNotEquals(textBefore, etf.getText());
		assertTrue(etf.isError());

		simulateKeyPress("test");
		simulateKeyPress(KeyEvent.VK_ENTER);
		assertTrue(etf.isError());

		simulateKeyPress("@");
		assertFalse(etf.isError());
	}

	/**
	 * Test 5 : Editing a row
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

		Map<List<Object>, LinkedHashMap<UUID, Object>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
//		getUiFacade().openTableRowsViewMode(tableId, tName, dataMapBefore, getDomainFacade().getColumnTypes(tableId));
		getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore);
		HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
				.get(0);
		UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
		EditableTextField etf = (EditableTextField) cellBefore.getComponent();
		String textBefore = etf.getText();

		simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);

		simulateKeyPress("0123");


		simulateKeyPress(KeyEvent.VK_CONTROL);
		simulateKeyPress(KeyEvent.VK_ENTER);
		simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		assertTrue(etf.isError());

		simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 5);
		assertFalse(etf.isError());

		simulateKeyPress(KeyEvent.VK_BACK_SPACE);
		assertFalse(etf.isError());

		simulateKeyPress("00");
		simulateKeyPress(KeyEvent.VK_ENTER);
		assertTrue(etf.isError());

		simulateKeyPress(KeyEvent.VK_BACK_SPACE);
		assertEquals(textBefore, etf.getText());
	}
}
