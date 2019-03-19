package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import domain.model.ValueType;
import ui.model.components.Component;
import ui.model.components.HorizontalComponentList;
import ui.model.components.VerticalComponentList;

public class UseCase8Test extends UseCaseTest implements RowTableConstants {

	/**
	 * Test 1 : Creating a new row
	 * | If you double click below the rows table, a new row should be created.
	 * | The values of these cells should be the values of the default values of the column of each cell.
	 */
	@Test
	public void test1doubleClickBelowTableRowsShouldCreateANewRow() {
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

		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> dataMapAfter = getDomainFacade().getTableWithIds(tableId);
		HorizontalComponentList rowsTableAfter = getTableViewModeRowsTable(tableId).getColumns();

		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry2 : dataMapAfter.entrySet()) {
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
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapBefore.entrySet()) {
			for (Map.Entry<UUID, Object> valueEntry : entry.getValue().entrySet()) {
				oldCellIds.add(valueEntry.getKey());
			}
		}

		Set<Object> newValues = new HashSet<>();
		for (Map.Entry<Map<UUID, String>, LinkedHashMap<UUID, Object>> entry : dataMapAfter.entrySet()) {
			for (Map.Entry<UUID, Object> valueEntry : entry.getValue().entrySet()) {
				if (!oldCellIds.contains(valueEntry.getKey())) {
					newValues.add(valueEntry.getValue());
				}
			}
		}
		
		assertTrue(newValues.contains(ValueType.BOOLEAN.getDefaultValue()));
		assertTrue(newValues.contains(ValueType.STRING.getDefaultValue()));
		assertTrue(newValues.contains(ValueType.EMAIL.getDefaultValue()));
		assertTrue(newValues.contains(ValueType.INTEGER.getDefaultValue()));
	}

}