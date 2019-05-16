package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;
import ui.model.components.UICell;
import ui.model.components.VerticalComponentList;

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
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();

			simulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPress(ADD_TABLE_NAME);
			simulateKeyPress(KeyEvent.VK_ENTER);

			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, List<String>> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(1, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			int testStringCounter = 0;

			VerticalComponentList vc =(VerticalComponentList) tableList.getComponentsList().get(0);
			for(Component c : vc.getComponentsList()) {
				if(c instanceof UICell) {
					EditableTextField etf = (EditableTextField) ((UICell) c).getComponent();
					ArrayList<String> ar = new ArrayList<>();
					ar.add(etf.getText());
					if (ar.get(0).indexOf(ADD_TABLE_NAME) >= 0) {
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
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();

			simulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPress(ADD_TABLE_NAME);
			simulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_X);

			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, List<String>> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(1, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			changedNamesCounter = 0;
			
			VerticalComponentList vc =(VerticalComponentList) tableList.getComponentsList().get(0);
			for (Component c : vc.getComponentsList()) {
				if (c instanceof UICell) {
					EditableTextField etf = (EditableTextField) ((UICell) c).getComponent();
					ArrayList<String> ar = new ArrayList<>();
					ar.add(etf.getText());
					assertTrue(endTableNamesList.containsValue(ar));
					if (ar.get(0).indexOf(ADD_TABLE_NAME) >= 0) {
						changedNamesCounter++;
					}
				}
			}

			assertEquals(1, changedNamesCounter);

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
			
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();
			simulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
			simulateKeyPress(KeyEvent.VK_ESCAPE);

			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, List<String>> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(0, changedNamesCounter);

			TableList tableList = this.getTablesViewModeTableList();

			changedNamesCounter = 0;
			VerticalComponentList vc = (VerticalComponentList) tableList.getComponentsList().get(0);
			for (Component c : vc.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					ArrayList<String> ar = new ArrayList<String>();
					ar.add(etf.getText());
					assertTrue(endTableNamesList.containsValue(ar));

					if (!(endTableNamesList.containsValue(ar))) {
						changedNamesCounter++;
					}
				}
			}
			assertEquals(0, changedNamesCounter);
			assertTrue(endTableNamesList.values().toString().contains(NEW_TABLE_NAME));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 4 : Erasing the name and pressing enter/clicking out of the textfield should do nothing
	 * | After clicking the table name once and clearing the textfield,
	 * | an error should be shown. After pressing enter of trying to click out of the textfield 
	 * | nothing should be done. After pressing escape, the value should be set to the initial text.
	 */
	@Test
	public void test4ChangeTableNameRemoveNameAndPressEnterOrClickOutShouldDoNothing() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();

			simulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
			simulateKeyPress(KeyEvent.VK_ENTER);
			simulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);

			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			assertEquals(startTableNamesList, endTableNamesList);

			TableList tableList = this.getTablesViewModeTableList();

			int changedNamesCounter = 0;
			VerticalComponentList vc = (VerticalComponentList) tableList.getComponentsList().get(0);
			
			for (Component c : vc.getComponentsList()) {
				if (c instanceof UICell) {
					EditableTextField etf = (EditableTextField) ((UICell) c).getComponent();
					ArrayList<String> ar = new ArrayList<>();
					ar.add(etf.getText());
					if(!startTableNamesList.containsValue(ar)) {
						assertTrue(!endTableNamesList.containsValue(ar));
					}
					if (!(endTableNamesList.containsValue(ar))) {
						changedNamesCounter++;
					}
				}
			}
			assertEquals(1, changedNamesCounter);

			simulateKeyPress(KeyEvent.VK_ESCAPE);
			endTableNamesList = getDomainFacade().getTableNames();

			changedNamesCounter = 0;
			for (Map.Entry<UUID, List<String>> entry : startTableNamesList.entrySet()) {
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
			if(!(getDomainFacade().getTableNames().isEmpty())) throw new Exception("mockup tables are still in domainfacade constr.");
			this.addDummyTable("A");
			this.addDummyTable("B");
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();
			simulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPress(KeyEvent.VK_BACK_SPACE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			simulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			simulateKeyPress('A');

			simulateSingleClick(SECOND_TABLE_Y, FIRST_TABLE_Y);
			simulateKeyPress(KeyEvent.VK_BACK_SPACE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			simulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			simulateKeyPress('A');

			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			System.out.println(startTableNamesList);
			System.out.println(endTableNamesList);
			int changedNamesCounter = 0;
			for (Map.Entry<UUID, List<String>> entry : startTableNamesList.entrySet()) {
				assertTrue(endTableNamesList.containsKey(entry.getKey()));

				if (!(entry.getValue().equals(endTableNamesList.get(entry.getKey())))) {
					System.out.println("o");
					changedNamesCounter++;
				}
			}
			assertEquals(0, changedNamesCounter);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			assertTrue(false);
		}
	}

}
