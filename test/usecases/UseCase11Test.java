package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.HorizontalComponentList;
import ui.model.components.UICell;
import ui.model.components.VerticalComponentList;

public class UseCase11Test extends UseCaseTest implements RowTableConstants {

	/**
	 * Test 1 : Deleting the row correctly
	 * | After clicking left of the row once in the rows table and pressing delete, 
	 * | the row should be removed from memory & in the ui.
	 */
	@Test
	public void test1clickingLeftOfRowAndPressingDeleteShouldDeleteRow() {
		try {
			getDomainFacade().addMockedTable(dummyTable1());
			String tName = null;
			UUID tableId = null;

			
			
			
			for(Entry<UUID, List<String>> entry: getDomainFacade().getTableNames().entrySet()){
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}


			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);
			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore,false);

			HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();
			System.out.println(rowsTableBefore.getComponentsList().get(0).getClass());
			VerticalComponentList firstVerticalList = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);
			
			int firstVerticalListSize = firstVerticalList.getComponentsList().size();

			simulateSingleClick(LEFT_TABLE_X, SECOND_ROW_Y);
			simulateKeyPress(KeyEvent.VK_DELETE);

			Map<List<Object>, List<Object[]>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

			HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();
			VerticalComponentList firstVerticalListAfter = (VerticalComponentList) rowsTableAfter.getComponentsList()
					.get(0);
			int firstVerticalListSizeAfter = firstVerticalListAfter.getComponentsList().size();

			int beforeRowsCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapBefore.entrySet()) {
				if (entry.getValue().size() > beforeRowsCounter) {
					beforeRowsCounter = entry.getValue().size();
				}
			}

			int afterRowsCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapAfter.entrySet()) {
				if (entry.getValue().size() > afterRowsCounter) {
					afterRowsCounter = entry.getValue().size();
				}
			}
			VerticalComponentList vc = (VerticalComponentList) rowsTableBefore.getComponentsList().get(0);
			System.out.println("before " + vc.getComponentsList());
			for(Component c : vc.getComponentsList()) {
				if(c instanceof UICell) {
					System.out.println(((UICell) c).getId());
				}
			}
			
			VerticalComponentList vcc = (VerticalComponentList) rowsTableBefore.getComponentsList().get(0);
			System.out.println("after " + vcc.getComponentsList());

			assertEquals(firstVerticalListSize - 1, firstVerticalListSizeAfter);
			assertEquals(beforeRowsCounter - 1, afterRowsCounter);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 2 : Deleting the row incorrectly
	 * | After clicking left of the row once in the rows table and clicking away,
	 * | if you then press delete, the row should be not removed from memory & in the ui.
	 */
	@Test
	public void test2clickingLeftOfRowAndClickingAwayAndPressingDeleteShouldNotDeleteRow() {
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
			VerticalComponentList firstVerticalList = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);
			int firstVerticalListSize = firstVerticalList.getComponentsList().size();

			simulateSingleClick(LEFT_TABLE_X, SECOND_ROW_Y);
			simulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
			simulateKeyPress(KeyEvent.VK_DELETE);

			Map<List<Object>, List<Object[]>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

			HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();
			VerticalComponentList firstVerticalListAfter = (VerticalComponentList) rowsTableAfter.getComponentsList()
					.get(0);
			int firstVerticalListSizeAfter = firstVerticalListAfter.getComponentsList().size();

			int beforeRowsCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapBefore.entrySet()) {
				if (entry.getValue().size() > beforeRowsCounter) {
					beforeRowsCounter = entry.getValue().size();
				}
			}

			int afterRowsCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapAfter.entrySet()) {
				if (entry.getValue().size() > afterRowsCounter) {
					afterRowsCounter = entry.getValue().size();
				}
			}

			assertEquals(firstVerticalListSize, firstVerticalListSizeAfter);
			assertEquals(beforeRowsCounter, afterRowsCounter);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 3 : Deleting the row incorrectly
	 * | After clicking left of the row once in the rows table and clicking inside the table,
	 * | if you then press delete, the row should be not removed from memory & in the ui.
	 */
	@Test
	public void test3clickingLeftOfRowAndClickingInsideTheTableAndPressingDeleteShouldNotDeleteRow() {
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
			VerticalComponentList firstVerticalList = (VerticalComponentList) rowsTableBefore.getComponentsList()
					.get(0);
			int firstVerticalListSize = firstVerticalList.getComponentsList().size();

			simulateSingleClick(LEFT_TABLE_X, SECOND_ROW_Y);
			simulateSingleClick(FIRST_ROW_X, SECOND_ROW_Y);
			simulateKeyPress(KeyEvent.VK_DELETE);

			Map<List<Object>, List<Object[]>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

			HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();
			VerticalComponentList firstVerticalListAfter = (VerticalComponentList) rowsTableAfter.getComponentsList()
					.get(0);
			int firstVerticalListSizeAfter = firstVerticalListAfter.getComponentsList().size();

			int beforeRowsCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapBefore.entrySet()) {
				if (entry.getValue().size() > beforeRowsCounter) {
					beforeRowsCounter = entry.getValue().size();
				}
			}

			int afterRowsCounter = 0;
			for (Entry<List<Object>, List<Object[]>> entry : dataMapAfter.entrySet()) {
				if (entry.getValue().size() > afterRowsCounter) {
					afterRowsCounter = entry.getValue().size();
				}
			}

			assertEquals(firstVerticalListSize, firstVerticalListSizeAfter);
			assertEquals(beforeRowsCounter, afterRowsCounter);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}