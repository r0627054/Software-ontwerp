package usecases;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.EditableComponent;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;
import ui.model.components.UICell;
import ui.model.components.VerticalComponentList;
import ui.model.view.UIFacade;
import ui.model.window.sub.TablesWindow;

public class UseCase1Test extends UseCaseTest implements TableListConstants {

	/**
	 * Test 1 : Creating a new table, by clicking below the current table (x:400, y:400)
	 * | A new table should be shown with a name with 'TableN' and its query its blank
	 * | with N being a unique character.
	 */
	@Test
	public void test1DoubleClickBelowListAddsOneTableWithCorrectName() {
		try {
			getUiFacade().createTablesSubWindow(new HashMap<UUID, List<String>>());
			
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();

			TableList tableList = getTablesViewModeTableList();
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(startTableNamesList.containsValue(etf.getText()));
				}
			}

			simulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			for (Map.Entry<UUID, List<String>> entry : endTableNamesList.entrySet()) {

				if (!(entry.getValue().equals(startTableNamesList.get(entry.getKey())))) {
					assertTrue(entry.getValue().toString().contains(NEW_TABLE_NAME));
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
			
			assertTrue(getUiFacade().getView().getCurrentSubWindow() instanceof TablesWindow);
			TablesWindow tw = (TablesWindow) getUiFacade().getView().getCurrentSubWindow();
			TableList tl = (TableList) tw.getContainer().getComponentsList().get(0);
			VerticalComponentList vc = (VerticalComponentList) tl.getComponentsList().get(0);
			for(int i = 0; i < vc.getComponentsList().size(); i++) {
			if(((EditableTextField) ((UICell) vc.getComponentsList().get(i)).getComponent()).getText().contains(NEW_TABLE_NAME)){
				VerticalComponentList vc2 = (VerticalComponentList) tl.getComponentsList().get(1);
				assertTrue(((EditableTextField) ((UICell) vc2.getComponentsList().get(i)).getComponent()).getText() == "");
			}
			}
			

		
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}


	/**
	 * Test 2 : Creating 2 new tables, by clicking below the current table (x:400, y:400)
	 * | 2 new tables should be shown with a name with 'TableN'
	 * | with N being a unique character.
	 */
	@Test
	public void test2TwoDoubleClickBelowListAddsTwoTableWithCorrectNames() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			Map<UUID, List<String>> startTableNamesList = getDomainFacade().getTableNames();

			TableList tableList = getTablesViewModeTableList();
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
					assertTrue(startTableNamesList.containsValue(etf.getText()));
				}
			}
			
			simulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			simulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);

			Map<UUID, List<String>> endTableNamesList = getDomainFacade().getTableNames();

			int changedNamesCounter = 0;
			List<String> newTableName1 = null;
			List<String> newTableName2 = null;
			for (Map.Entry<UUID, List<String>> entry : endTableNamesList.entrySet()) {

				if (!(entry.getValue().equals(startTableNamesList.get(entry.getKey())))) {
					assertTrue(entry.getValue().toString().contains(NEW_TABLE_NAME));
					changedNamesCounter++;

					if (newTableName1 == null && newTableName2 == null) {
						newTableName1 = entry.getValue();
					} else if (newTableName1 != null && newTableName2 == null) {
						newTableName2 = entry.getValue();
					}

				}
			}
			
			assertEquals(2, changedNamesCounter);
			assertNotSame(newTableName1, newTableName2);
			
			tableList = getTablesViewModeTableList();
			for (Component c : tableList.getComponentsList()) {
				if (c instanceof EditableTextField) {
					EditableTextField etf = (EditableTextField) c;
//					assertTrue(endTableNamesList.containsValue(etf.getText()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
