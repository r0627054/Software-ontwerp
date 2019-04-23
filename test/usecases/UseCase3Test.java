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

public class UseCase3Test extends UseCaseTest implements TableListConstants {
	
	/**
	 * Test 1 : Deleting the table name correctly
	 * | After clicking left of the table name once and pressing delete, 
	 * | the table should be removed from memory & in the ui.
	 */
	@Test
	public void test1clickingLeftOfTableNameAndPressingDeleteShouldDeleteTable() {
		this.addDummyTable(NEW_TABLE_NAME);
		Map<UUID, String> startTableNames = this.getDomainFacade().getTableNames();

		simulateSingleClick(LEFT_FIRST_TABLE_X, FIRST_TABLE_Y);
		simulateKeyPress(KeyEvent.VK_DELETE);

		Map<UUID, String> endTableNames = this.getDomainFacade().getTableNames();
		

		
		assertEquals(startTableNames.size() - 1, endTableNames.size());

		TableList tableList = getTablesViewModeTableList();

		List<String> uiNamesList = new ArrayList<>();
		for (Component c : tableList.getComponentsList()) {
			if (c instanceof EditableTextField) {
				EditableTextField etf = (EditableTextField) c;
				uiNamesList.add(etf.getText());
			}
		}
		

		for (String s : endTableNames.values()) {
			assertTrue(uiNamesList.contains(s));
		}
	}
	
	/**
	 * Test 2 : Clicking left of the table and then clicking away should reset the delete behaviour.
	 * | After clicking left of the table name once and then clicking somewhere else, 
	 * | if you then press delete, the table should not be removed.
	 */
	@Test
	public void test2clickingLeftOfTableNameAndClickingAwayBeforePressingDeleteShouldNotDeleteTable() {
		this.addDummyTable(NEW_TABLE_NAME);
		Map<UUID, String> startTableNames = this.getDomainFacade().getTableNames();

		simulateSingleClick(LEFT_FIRST_TABLE_X, FIRST_TABLE_Y);
		simulateSingleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
		simulateKeyPress(KeyEvent.VK_DELETE);

		Map<UUID, String> endTableNames = this.getDomainFacade().getTableNames();
		
				
		assertEquals(startTableNames, endTableNames);
		
		TableList tableList = getTablesViewModeTableList();

		List<String> uiNamesList = new ArrayList<>();
		for (Component c : tableList.getComponentsList()) {
			if (c instanceof EditableTextField) {
				EditableTextField etf = (EditableTextField) c;
				System.out.println(etf.getText());
				uiNamesList.add(etf.getText());
			}
		}

		for (String s : startTableNames.values()) {
			assertTrue(uiNamesList.contains(s));
		}
	}
	
	/**
	 * Test 3 : Clicking left of the table and then clicking away should reset the delete behaviour.
	 * | After clicking left of the table name once and then clicking inside the table name text field, 
	 * | if you then press delete, the table should not be removed.
	 */
	@Test
	public void test3clickingLeftOfTableNameAndClickingInTheTableTextFieldBeforePressingDeleteShouldNotDeleteTable() {
		this.addDummyTable(NEW_TABLE_NAME);
		Map<UUID, String> startTableNames = this.getDomainFacade().getTableNames();

		simulateSingleClick(LEFT_FIRST_TABLE_X, FIRST_TABLE_Y);
		simulateSingleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
		simulateKeyPress(KeyEvent.VK_DELETE);

		Map<UUID, String> endTableNames = this.getDomainFacade().getTableNames();
		assertEquals(startTableNames, endTableNames);
		
		TableList tableList = getTablesViewModeTableList();

		List<String> uiNamesList = new ArrayList<>();
		for (Component c : tableList.getComponentsList()) {
			if (c instanceof EditableTextField) {
				EditableTextField etf = (EditableTextField) c;
				uiNamesList.add(etf.getText());
			}
		}

		for (String s : startTableNames.values()) {
			assertTrue(uiNamesList.contains(s));
		}
	}

}
