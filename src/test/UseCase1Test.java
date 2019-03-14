package test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;

public class UseCase1Test extends UseCaseTest implements TableListConstants {

	/**
	 * Test 1 : Creating a new table, by clicking below the current table (x:200, y:200)
	 * | A new table should be shown with a name with 'TableN'
	 * | with N being a unique character.
	 */
	@Test
	public void test1DoubleClickBelowListAddsOneTableWithCorrectName() {
		try {
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			TableList tableList = getTablesViewModeTableList();
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(startTableNamesList.containsValue(etf.getText()));
				}
			}

			emulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, String> entry : endTableNamesList.entrySet()) {

				if (!(entry.getValue().equals(startTableNamesList.get(entry.getKey())))) {
					assertTrue(entry.getValue().contains(NEW_TABLE_NAME));
				}

				if (!(entry.getValue().equals(startTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(1, changedNamesCounter);

			tableList = getTablesViewModeTableList();
			changedNamesCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(endTableNamesList.containsValue(etf.getText()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}


	/**
	 * Test 2 : Creating 2 new tables, by clicking below the current table (x:200, y:200)
	 * | 2 new tables should be shown with a name with 'TableN'
	 * | with N being a unique character.
	 */
	@Test
	public void test2TwoDoubleClickBelowListAddsTwoTableWithCorrectNames() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, String> startTableNamesList = getDomainFacade().getTableNames();

			TableList tableList = getTablesViewModeTableList();
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(startTableNamesList.containsValue(etf.getText()));
				}
			}
			
			emulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			emulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);

			Map<UUID, String> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			String newTableName1 = null;
			String newTableName2 = null;
			for (Map.Entry<UUID, String> entry : endTableNamesList.entrySet()) {

				if (!(entry.getValue().equals(startTableNamesList.get(entry.getKey())))) {
					assertTrue(entry.getValue().contains(NEW_TABLE_NAME));

					if (newTableName1 == null && newTableName2 == null) {
						newTableName1 = entry.getValue();
					} else if (newTableName1 != null && newTableName2 == null) {
						newTableName2 = entry.getValue();
					}

				}

				if (!(entry.getValue().equals(startTableNamesList.get(entry.getKey())))) {
					changedNamesCounter++;
				}
			}
			assertEquals(2, changedNamesCounter);
			assertNotSame(newTableName1, newTableName2);
			
			tableList = getTablesViewModeTableList();
			changedNamesCounter = 0;
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(endTableNamesList.containsValue(etf.getText()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
