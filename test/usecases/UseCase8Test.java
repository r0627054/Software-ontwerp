package usecases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import ui.model.components.UICell;
import ui.model.components.ColumnHeader;
import ui.model.components.Component;
import ui.model.components.DesignTable;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;
import ui.model.components.TableList;
import ui.model.components.VerticalComponentList;
import ui.model.window.sub.TableDesignWindow;
import ui.model.window.sub.TableRowsWindow;
import ui.model.window.sub.TablesWindow;

public class UseCase8Test extends UseCaseTest implements DesignTableConstants {

	/**
	 * Test 1 : Deleting the column correctly
	 * | After clicking left of the column name once in the design table and pressing delete, 
	 * | the column should be removed from memory & in the ui.
	 */
	@Test
	public void test1clickingLeftOfColumnNameAndPressingDeleteShouldDeleteColumn() {
		try {
			addDummyTableBooleanColumnNullCellValues();
			String tableName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tableName = entry.getValue().get(0);
				tableId = entry.getKey();
			}
			getUiFacade().createTableDesignSubWindow(tableId, tableName,
					getDomainFacade().getColumnCharacteristics(tableId));
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

			assertEquals(uiCellListBefore.size() - 4, uiCellListAfter.size());
//		assertEquals(columnDataBefore.size() - 1, columnDataAfter.size());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 2 : Clicking left of the table and then clicking away should reset the delete behaviour.
	 * | After clicking left of the column name once and then clicking somewhere else, 
	 * | if you then press delete, the column should not be removed.
	 */
	@Test
	public void test2clickingLeftOfColumnNameAndClickingAwayAndPressingDeleteShouldNotDeleteColumn() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			String tableName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tableName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			getUiFacade().createTableDesignSubWindow(tableId, tableName,
					getDomainFacade().getColumnCharacteristics(tableId));
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
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 3 : Clicking left of the table and then clicking in the row should reset the delete behaviour.
	 * | After clicking left of the column name once and then clicking inside the same row, 
	 * | if you then press delete, the column should not be removed.
	 */
	@Test
	public void test3clickingLeftOfColumnNameAndInTheTableAndPressingDeleteShouldNotDeleteColumn() {
		try {
			this.addDummyTable(NEW_TABLE_NAME);
			String tableName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tableName = entry.getValue().get(0);
				tableId = entry.getKey();
			}
			getUiFacade().createTableDesignSubWindow(tableId, tableName,
					getDomainFacade().getColumnCharacteristics(tableId));
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
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	/**
	 * Test 4 : 
	 */
	@Test
	public void test4() {
		try {
			this.addDummyTable("A");
			addDummyTableEmailColumnEmailCellValues();
			simulateSingleClick(SECOND_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPress(ADD_TABLE_QUERY_REF_SECOND_TABLE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			assertTrue(this.getUiFacade().getView().getCurrentSubWindow() instanceof TablesWindow);
			TableList tl = (TableList) this.getUiFacade().getView().getCurrentSubWindow().getContainer().getComponentsList().get(0);
			VerticalComponentList vcc = (VerticalComponentList) tl.getComponentsList().get(0);
			
			int numberOfTablesBefore = vcc.getComponentsList().size();
			
			simulateDoubleClick(COLUMN_NAME_X, FIRST_ROW_Y);

			simulateKeyPress(KeyEvent.VK_CONTROL);
			simulateKeyPress(KeyEvent.VK_ENTER);
			assertTrue(this.getUiFacade().getView().getCurrentSubWindow() instanceof TableDesignWindow);
			
			simulateSingleClick(LEFT_TABLE_X, FIRST_ROW_Y);
			
			simulateKeyPress(KeyEvent.VK_DELETE);
			
			this.getUiFacade().closeCurrentSubWindow();
			assertTrue(this.getUiFacade().getView().getCurrentSubWindow() instanceof TableRowsWindow);
			this.getUiFacade().closeCurrentSubWindow();
			assertTrue(this.getUiFacade().getView().getCurrentSubWindow() instanceof TablesWindow);

			TableList tll = (TableList) this.getUiFacade().getView().getCurrentSubWindow().getContainer().getComponentsList().get(0);

			VerticalComponentList vc = (VerticalComponentList) tll.getComponentsList().get(0);
			
			
			int numberOfTablesAfter = vc.getComponentsList().size();


			assertEquals(numberOfTablesBefore -1, numberOfTablesAfter);
			assertFalse(this.getUiFacade().getView().getCurrentSubWindow().isPaused());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
