package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import domain.model.ValueType;
import ui.model.components.Component;
import ui.model.components.HorizontalComponentList;
import ui.model.components.VerticalComponentList;

public class UseCase9Test extends UseCaseTest implements RowTableConstants {

	/**
	 * Test 1 : Creating a new row
	 * | If you double click below the rows table, a new row should be created.
	 * | The values of these cells should be the values of the default values of the column of each cell.
	 */
	@Test
	public void test1doubleClickBelowTableRowsShouldCreateANewRow() {
		try {
			getDomainFacade().addMockedTable(dummyTable1());
			String tName = null;
			UUID tableId = null;

			for (Map.Entry<UUID, List<String>> entry : getDomainFacade().getTableNames().entrySet()) {
				tName = entry.getValue().get(0);
				tableId = entry.getKey();
			}

			Map<List<Object>, List<Object[]>> dataMapBefore = getDomainFacade().getTableWithIds(tableId);

			getUiFacade().createTableRowsSubWindow(tableId, tName, dataMapBefore, false);
			HorizontalComponentList rowsTableBefore = getTableViewModeRowsTable(tableId).getColumns();

			simulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

			Map<List<Object>, List<Object[]>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);
			HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();

			for (Entry<List<Object>, List<Object[]>> entry : dataMapBefore.entrySet()) {
				for (Entry<List<Object>, List<Object[]>> entry2 : dataMapAfter.entrySet()) {
					assertEquals(entry.getValue().size() + 1, entry2.getValue().size());
				}
			}

			for (Component c : rowsTableBefore.getComponentsList()) {
				for (Component c2 : rowsTableAfter.getComponentsList()) {

					VerticalComponentList v = (VerticalComponentList) c;
					VerticalComponentList v2 = (VerticalComponentList) c2;

					assertEquals(v.getComponentsList().size() + 1, v2.getComponentsList().size());
				}
			}

			Set<UUID> oldCellIds = new HashSet<>();
			for (Entry<List<Object>, List<Object[]>> entry : dataMapBefore.entrySet()) {
				for (Object[] valueEntry : entry.getValue()) {
					oldCellIds.add((UUID) valueEntry[0]);
				}
			}

			Set<Object> newValues = new HashSet<>();
			for (Entry<List<Object>, List<Object[]>> entry : dataMapAfter.entrySet()) {
				for (Object[] valueEntry : entry.getValue()) {
					if (!oldCellIds.contains(valueEntry[0])) {
						newValues.add(valueEntry[1]);
					}
				}
			}
			assertTrue(newValues.contains(ValueType.BOOLEAN.getDefaultValue()));
			assertTrue(newValues.contains(ValueType.STRING.getDefaultValue()));
			assertTrue(newValues.contains(ValueType.EMAIL.getDefaultValue()));
			assertTrue(newValues.contains(ValueType.INTEGER.getDefaultValue()));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
