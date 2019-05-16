package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.VerticalComponentList;

public class UseCase5Test extends UseCaseTest implements DesignTableConstants {

	/**
	 * Test 1 : Double clicking below the design table creates a column.
	 * | When you double click below the table a new row of column details should show
	 * | where we can edit the newly created column.
	 */
	@Test
	public void test1doubleClickBelowDesignTableToCreateAColumn() {
		try {
			addDummyEmptyTableEmailColumnVariableAllowsBlank(true);
			String tableName = null;
			UUID tableId = null;

			for (Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tableName = entry.getValue().get(0);
				tableId = entry.getKey();
			}
			getUiFacade().createTableDesignSubWindow(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId) );


			Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
					.getColumnCharacteristics(tableId);

			VerticalComponentList uiRowsBefore = getTableViewModeDesignTable(tableId).getRows();
			for (Component uir : uiRowsBefore.getComponentsList()) {
				System.out.println(uir);
			}

			simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

			Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
					.getColumnCharacteristics(tableId);

			VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

			assertEquals(columnDataBefore.size() + 1, columnDataAfter.size());
			assertEquals(uiRowsBefore.getComponentsList().size() + 1, uiRowsAfter.getComponentsList().size());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
