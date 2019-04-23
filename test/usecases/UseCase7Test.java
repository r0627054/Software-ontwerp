package usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.UICell;
import ui.model.components.Component;
import ui.model.components.HorizontalComponentList;
import ui.model.components.VerticalComponentList;

public class UseCase7Test extends UseCaseTest implements DesignTableConstants {

	/**
	 * Test 1 : Deleting the column correctly
	 * | After clicking left of the column name once in the design table and pressing delete, 
	 * | the column should be removed from memory & in the ui.
	 */
	@Test
	public void test1clickingLeftOfColumnNameAndPressingDeleteShouldDeleteColumn() {
		this.addDummyTable(NEW_TABLE_NAME);
		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();
		
		List<UICell> uiCellListBefore = new ArrayList<>();
		for (Component component : uiRowsBefore.getComponentsList()) {
			HorizontalComponentList hzcl = (HorizontalComponentList) component;

			for (Component hzclComponent : hzcl.getComponentsList()) {
				if (hzclComponent instanceof UICell) {
					uiCellListBefore.add((UICell) hzclComponent);
				}
			}
		}
		System.err.println(uiCellListBefore.size());
		
		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		simulateSingleClick(LEFT_TABLE_X, FIRST_ROW_Y);
		
		simulateKeyPress(KeyEvent.VK_DELETE);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		List<UICell> uiCellListAfter = new ArrayList<>();
		for (Component component : uiRowsAfter.getComponentsList()) {
			HorizontalComponentList hzcl = (HorizontalComponentList) component;

			for (Component hzclComponent : hzcl.getComponentsList()) {
				if (hzclComponent instanceof UICell) {
					uiCellListAfter.add((UICell) hzclComponent);
				}
			}
		}
		
		System.err.println(uiCellListAfter.size());
		
		assertEquals(uiCellListBefore.size() - 4, uiCellListAfter.size());
//		assertEquals(columnDataBefore.size() - 1, columnDataAfter.size());
	}

	/**
	 * Test 2 : Clicking left of the table and then clicking away should reset the delete behaviour.
	 * | After clicking left of the column name once and then clicking somewhere else, 
	 * | if you then press delete, the column should not be removed.
	 */
	@Test
	public void test2clickingLeftOfColumnNameAndClickingAwayAndPressingDeleteShouldNotDeleteColumn() {
		this.addDummyTable(NEW_TABLE_NAME);
		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		 
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();

		List<UICell> uiCellListBefore = new ArrayList<>();
		for (Component component : uiRowsBefore.getComponentsList()) {
			HorizontalComponentList hzcl = (HorizontalComponentList) component;

			for (Component hzclComponent : hzcl.getComponentsList()) {
				if (hzclComponent instanceof UICell) {
					uiCellListBefore.add((UICell) hzclComponent);
				}
			}
		}
		
		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		


		simulateSingleClick(LEFT_TABLE_X, FIRST_ROW_Y);		
		simulateSingleClick(BELOW_TABLE_X, BELOW_TABLE_Y);
		simulateKeyPress(KeyEvent.VK_DELETE);
		


		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		
		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		List<UICell> uiCellListAfter = new ArrayList<>();
		for (Component component : uiRowsAfter.getComponentsList()) {
			HorizontalComponentList hzcl = (HorizontalComponentList) component;

			for (Component hzclComponent : hzcl.getComponentsList()) {
				if (hzclComponent instanceof UICell) {
					uiCellListAfter.add((UICell) hzclComponent);
				}
			}
		}
		
		assertEquals(uiCellListBefore.size(), uiCellListAfter.size());
		assertEquals(columnDataBefore.size(), columnDataAfter.size());
	}
	/**
	 * Test 3 : Clicking left of the table and then clicking in the row should reset the delete behaviour.
	 * | After clicking left of the column name once and then clicking inside the same row, 
	 * | if you then press delete, the column should not be removed.
	 */
	@Test
	public void test3clickingLeftOfColumnNameAndInTheTableAndPressingDeleteShouldNotDeleteColumn() {
		this.addDummyTable(NEW_TABLE_NAME);
		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();

		List<UICell> uiCellListBefore = new ArrayList<>();
		for (Component component : uiRowsBefore.getComponentsList()) {
			HorizontalComponentList hzcl = (HorizontalComponentList) component;

			for (Component hzclComponent : hzcl.getComponentsList()) {
				if (hzclComponent instanceof UICell) {
					uiCellListBefore.add((UICell) hzclComponent);
				}
			}
		}
		
		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);

		simulateSingleClick(LEFT_TABLE_X, FIRST_ROW_Y);
		simulateSingleClick(COLUMN_NAME_X, FIRST_ROW_Y);
		simulateKeyPress(KeyEvent.VK_DELETE);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		List<UICell> uiCellListAfter = new ArrayList<>();
		for (Component component : uiRowsAfter.getComponentsList()) {
			HorizontalComponentList hzcl = (HorizontalComponentList) component;

			for (Component hzclComponent : hzcl.getComponentsList()) {
				if (hzclComponent instanceof UICell) {
					uiCellListAfter.add((UICell) hzclComponent);
				}
			}
		}
		
		assertEquals(uiCellListBefore.size(), uiCellListAfter.size());
		assertEquals(columnDataBefore.size(), columnDataAfter.size());
	}

}
