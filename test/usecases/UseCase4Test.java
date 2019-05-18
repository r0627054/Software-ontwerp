package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.window.sub.TableDesignWindow;
import ui.model.window.sub.TableRowsWindow;

public class UseCase4Test extends UseCaseTest implements TableListConstants {

	/**
	 * Test 1 : Double clicking on a table inside TablesViewMode
	 * | If the table is empty, it should open TableDesignMode
	 */
	@Test
	public void test1openTableWithNewTableShouldOpenDesignMode() {
		try {
			getUiFacade().createTablesSubWindow(new HashMap<UUID, List<String>>());
			
			simulateDoubleClick(BELOW_TABLELIST_X, BELOW_TABLELIST_Y);
			simulateDoubleClick(FIRST_TABLE_X, FIRST_TABLE_Y);

			assertTrue(this.getUiFacade().getView().getCurrentSubWindow() instanceof TableDesignWindow);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 2 : Double clicking on a table inside TablesViewMode
	 * | If the table is not empty, it should open TableRowsMode
	 */
	@Test
	public void test2openTableWithExistingTableShouldOpenRowsMode() {
		try {
			addDummyTableEmailColumnEmailCellValues();
			getUiFacade().createTablesSubWindow(getDomainFacade().getTableNames());
			simulateDoubleClick(FIRST_TABLE_X, FIRST_TABLE_Y);

			assertTrue(this.getUiFacade().getView().getCurrentSubWindow() instanceof TableRowsWindow);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
