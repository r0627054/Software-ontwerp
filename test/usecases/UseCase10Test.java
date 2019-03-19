package usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.HorizontalComponentList;
import ui.model.components.VerticalComponentList;

public class UseCase10Test extends UseCaseTest implements RowTableConstants {

	/**
	 * Test 1 : Deleting the row correctly
	 * | After clicking left of the row once in the rows table and pressing delete, 
	 * | the row should be removed from memory & in the ui.
	 */
	@Test
	public void test1clickingLeftOfRowAndPressingDeleteShouldDeleteRow() {
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
		VerticalComponentList firstVerticalList = (VerticalComponentList) rowsTableBefore.getComponentsList().get(0);
		int firstVerticalListSize = firstVerticalList.getComponentsList().size();

		emulateSingleClick(LEFT_TABLE_X, SECOND_ROW_Y);
		emulateKeyPress(KeyEvent.VK_DELETE);

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

		HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList firstVerticalListAfter = (VerticalComponentList) rowsTableAfter.getComponentsList()
				.get(0);
		int firstVerticalListSizeAfter = firstVerticalListAfter.getComponentsList().size();

		int beforeRowsCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			if (entry.getValue().size() > beforeRowsCounter) {
				beforeRowsCounter = entry.getValue().size();
			}
		}

		int afterRowsCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapAfter.entrySet()) {
			if (entry.getValue().size() > afterRowsCounter) {
				afterRowsCounter = entry.getValue().size();
			}
		}

		assertEquals(firstVerticalListSize - 1, firstVerticalListSizeAfter);
		assertEquals(beforeRowsCounter - 1, afterRowsCounter);
	}

	/**
	 * Test 2 : Deleting the row incorrectly
	 * | After clicking left of the row once in the rows table and clicking away,
	 * | if you then press delete, the row should be not removed from memory & in the ui.
	 */
	@Test
	public void test2clickingLeftOfRowAndClickingAwayAndPressingDeleteShouldNotDeleteRow() {
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
		VerticalComponentList firstVerticalList = (VerticalComponentList) rowsTableBefore.getComponentsList().get(0);
		int firstVerticalListSize = firstVerticalList.getComponentsList().size();

		emulateSingleClick(LEFT_TABLE_X, SECOND_ROW_Y);
		emulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		emulateKeyPress(KeyEvent.VK_DELETE);

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

		HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList firstVerticalListAfter = (VerticalComponentList) rowsTableAfter.getComponentsList()
				.get(0);
		int firstVerticalListSizeAfter = firstVerticalListAfter.getComponentsList().size();

		int beforeRowsCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			if (entry.getValue().size() > beforeRowsCounter) {
				beforeRowsCounter = entry.getValue().size();
			}
		}

		int afterRowsCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapAfter.entrySet()) {
			if (entry.getValue().size() > afterRowsCounter) {
				afterRowsCounter = entry.getValue().size();
			}
		}

		assertEquals(firstVerticalListSize, firstVerticalListSizeAfter);
		assertEquals(beforeRowsCounter, afterRowsCounter);
	}
	
	/**
	 * Test 3 : Deleting the row incorrectly
	 * | After clicking left of the row once in the rows table and clicking inside the table,
	 * | if you then press delete, the row should be not removed from memory & in the ui.
	 */
	@Test
	public void test3clickingLeftOfRowAndClickingInsideTheTableAndPressingDeleteShouldNotDeleteRow() {
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
		VerticalComponentList firstVerticalList = (VerticalComponentList) rowsTableBefore.getComponentsList().get(0);
		int firstVerticalListSize = firstVerticalList.getComponentsList().size();

		emulateSingleClick(LEFT_TABLE_X, SECOND_ROW_Y);
		emulateSingleClick(FIRST_ROW_X, SECOND_ROW_Y);
		emulateKeyPress(KeyEvent.VK_DELETE);

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);

		HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();
		VerticalComponentList firstVerticalListAfter = (VerticalComponentList) rowsTableAfter.getComponentsList()
				.get(0);
		int firstVerticalListSizeAfter = firstVerticalListAfter.getComponentsList().size();

		int beforeRowsCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			if (entry.getValue().size() > beforeRowsCounter) {
				beforeRowsCounter = entry.getValue().size();
			}
		}

		int afterRowsCounter = 0;
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapAfter.entrySet()) {
			if (entry.getValue().size() > afterRowsCounter) {
				afterRowsCounter = entry.getValue().size();
			}
		}

		assertEquals(firstVerticalListSize, firstVerticalListSizeAfter);
		assertEquals(beforeRowsCounter, afterRowsCounter);
	}

}
