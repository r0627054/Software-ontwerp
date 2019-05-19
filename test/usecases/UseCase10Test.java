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

import domain.model.DomainException;
import ui.model.components.UICell;
import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;
import ui.model.components.VerticalComponentList;

public class UseCase10Test extends UseCaseTest implements RowTableConstants {

	/**
	 * Test 1 : Editing a row
	 * | If you click a text field with valid text and press enter, the text 
	 * | should be displayed and saved.
	 */
	@Test
	public void test1editingATextFieldWithValidTextAndEnterPressShouldSave() {
		try {
			addDummyTableStringColumnEmailCellValues();
			String tName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore,false);

			HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
			VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);
			UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
			EditableTextField etf = (EditableTextField) cellBefore.getComponent();
			String textBefore = etf.getText();

			simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
			simulateKeyPress(EDIT_STRING_TEXT);
			simulateKeyPress(KeyEvent.VK_ENTER);

			Map<List<Object>, List<Object[]>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

			int newTextCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapBefore.entrySet()) {
				for (Object[] entry2 : entry.getValue()) {
					if (String.valueOf(entry2[1]).contains(EDIT_STRING_TEXT)) {
						newTextCounter++;
					}
				}
			}
			assertEquals(0, newTextCounter);

			newTextCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapAfter.entrySet()) {
				for (Object[] entry2 : entry.getValue()) {
					if (String.valueOf(entry2[1]).contains(EDIT_STRING_TEXT)) {
						newTextCounter++;
					}
				}
			}
			assertEquals(1, newTextCounter);

			assertTrue(etf.getText().contains(EDIT_STRING_TEXT));
			assertNotEquals(textBefore, etf.getText());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 2 : Editing a row
	 * | If you click a check box, the checked value should flip from true to false
	 * | and from false to true.
	 */
	@Test
	public void test2clickingACheckBoxShouldSave() {
		try {
			addDummyTableBooleanColumnCellValues();
			String tName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore,false);
			
			HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
			
			VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);


			UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
			for(Component c :verticalCompListBefore.getComponentsList()) {
				if(c instanceof UICell) {
					UICell uic = (UICell) c;
					if(uic.getComponent() instanceof EditableTextField) {
					}
				}
			}
			CheckBox checkBox = (CheckBox) cellBefore.getComponent();
			boolean isCheckedStart = checkBox.isChecked();
			simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
			
			
			assertEquals(isCheckedStart, !checkBox.isChecked());
			assertFalse(checkBox.isError());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 3 : Editing a row
	 * | If you clear the text field while blanks are not allowed in the column, the application
	 * | should pause. Pressing escape should reset the text field and reset the value in memory.
	 */
	@Test
	public void test3clearingAStringTextFieldShouldPauseTheApplicationAndEscapeShouldResetIfTheColumnDoesntAllowBlanks() {
		try {
			this.addDummyTableNotEmptyDefaultColumnValueNoBlanksAllowed();

			String tName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore,false);

			HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
			VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);
			UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
			EditableTextField etf = (EditableTextField) cellBefore.getComponent();
			String textBefore = etf.getText();

			simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
			simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 10);
			simulateKeyPress(KeyEvent.VK_ENTER);
			simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

			assertEquals(0, etf.getText().length());
			assertNotEquals(textBefore, etf.getText());
			assertTrue(etf.isError());

			simulateKeyPress(KeyEvent.VK_ESCAPE);

			assertEquals(textBefore, etf.getText());
			assertFalse(etf.isError());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	/**
	 * Test 4 : Editing a row
	 * | An email text field with no blanks allowed needs exactly one '@' since
	 * | blank values aren't allowed.
	 */
	@Test
	public void test4clearingAEmailTextFieldWithNoBlanksAllowedShouldOnlyResumeWhenAnAtSignIsTyped() {
		try {
			this.addDummyTableNotEmptyEmailDefaultColumnValueNoBlanksAllowed();

			String tName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore,false);
			HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
			VerticalComponentList verticalCompListBefore = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);
			UICell cellBefore = (UICell) verticalCompListBefore.getComponentsList().get(1);
			EditableTextField etf = (EditableTextField) cellBefore.getComponent();
			String textBefore = etf.getText();

			simulateSingleClick(FIRST_ROW_X, FIRST_ROW_Y);
			simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 10);

			simulateKeyPress(KeyEvent.VK_ENTER);
			simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

			assertEquals(0, etf.getText().length());
			assertNotEquals(textBefore, etf.getText());
			assertTrue(etf.isError());

			simulateKeyPress("test");
			simulateKeyPress(KeyEvent.VK_ENTER);
			assertTrue(etf.isError());

			simulateKeyPress("@");
			assertFalse(etf.isError());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 5 : Editing a row
	 * | An Integer text field never allows leading zeroes.
	 */
	@Test
	public void test5leadingZeroesShouldNotBeAllowedInAnIntegerTextField() {
		try {
			this.addDummyTableIntColumnStringCellValues();

			String tName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore,false);
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
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
