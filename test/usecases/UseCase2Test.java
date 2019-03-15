package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;

public class UseCase2Test extends UseCaseTest implements TableListConstants {

	/**
	 * Test 1 : Editing the table name correctly
	 * | After clicking the table name once and changing the name, 
	 * | the name should be edited after pressing enter.
	 */
	@Test
	public void test1ChangeTableNameWithCorrectTextUpdatesNameCorrectly() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			emulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			emulateKeyPress(ADD_TABLE_NAME);
			emulateKeyPress(KeyEvent.VK_ENTER);

			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(1, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			int testStringCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(endTableNamesList.containsValue(etf.getText()));

					if (etf.getText().indexOf(ADD_TABLE_NAME) >= 0) {
						testStringCounter++;
					}
				}
			}

			assertEquals(1, testStringCounter);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**
	 * Test 2 : Editing the table name correctly
	 * | After clicking the table name once and changing the name, 
	 * | the name should be edited after clicking out of the textfield.
	 */
	@Test
	public void test2ChangeTableNameWithCorrectTextAndOutsideClickUpdatesNameCorrectly() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			emulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			emulateKeyPress(ADD_TABLE_NAME);
			emulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_X);
			
			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(1, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			int testStringCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(endTableNamesList.containsValue(etf.getText()));

					if (etf.getText().indexOf(ADD_TABLE_NAME) >= 0) {
						testStringCounter++;
					}
				}
			}

			assertEquals(1, testStringCounter);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	/**
	 * Test 3 : Erasing the name and pressing escape should reset the name
	 * | After clicking the table name once and clearing the textfield,
	 * | an error should be shown. After pressing escape the textfield should be
	 * | set to the initial text.
	 */
	@Test
	public void test3ChangeTableNameRemoveNameAndPressEscapeShouldResetName() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			emulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
			emulateKeyPress(KeyEvent.VK_ESCAPE);

			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(0, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			changedNamesCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(endTableNamesList.containsValue(etf.getText()));

					if (!(endTableNamesList.containsValue(etf.getText()))) {
						changedNamesCounter++;
					}
				}
			}
			assertEquals(0, changedNamesCounter);
			assertTrue(endTableNamesList.values().contains(NEW_TABLE_NAME));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 4 : Erasing the name and pressing enter/clicking out of the textfield should do nothing
	 * | After clicking the table name once and clearing the textfield,
	 * | an error should be shown. After pressing enter of trying to click out of the textfield 
	 * | nothign should be done. After pressing escape, the value should be set to the initial text.
	 */
	@Test
	public void test4ChangeTableNameRemoveNameAndPressEnterOrClickOutShouldDoNothing() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			emulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			emulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
			emulateKeyPress(KeyEvent.VK_ENTER);
			emulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);

			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(1, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			changedNamesCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(!endTableNamesList.containsValue(etf.getText()));

					if (!(endTableNamesList.containsValue(etf.getText()))) {
						changedNamesCounter++;
					}
				}
			}
			assertEquals(1, changedNamesCounter);

			emulateKeyPress(KeyEvent.VK_ESCAPE);
			endTableNamesList = getDomainFacade().getTableNames();

			changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(0, changedNamesCounter);

			tableList = this.getTablesViewModeTableList();
			changedNamesCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(endTableNamesList.containsValue(etf.getText()));

					if (!(endTableNamesList.containsValue(etf.getText()))) {
						changedNamesCounter++;
					}
				}
			}
			assertEquals(0, changedNamesCounter);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	/**
	 * Test 5 : Changing the table name to an already existing table name should not save the name.
	 * | After clicking the table name once changing the table name to a table name that already exists,
	 * | an error should be shown. The invalid (second) table name should not be saved.
	 */
	@Test
	public void test5ChangeTableNameToExistingTableNameShouldConflict() {
		try {
			this.addDummyTable("A");
			this.addDummyTable("B");
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			emulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			emulateKeyPress(KeyEvent.VK_BACK_SPACE);
			emulateKeyPress(KeyEvent.VK_ENTER);
			emulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			emulateKeyPress('A');

			emulateSingleClick(SECOND_TABLE_Y, FIRST_TABLE_Y);
			emulateKeyPress(KeyEvent.VK_BACK_SPACE);
			emulateKeyPress(KeyEvent.VK_ENTER);
			emulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			emulateKeyPress('A');

			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(0, changedNamesCounter);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}


}
