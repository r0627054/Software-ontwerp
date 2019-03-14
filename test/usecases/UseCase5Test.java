package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.DesignTable;
import ui.model.components.HorizontalComponentList;
import ui.model.components.VerticalComponentList;

public class UseCase5Test extends UseCaseTest implements DesignTableConstants {
	@Test
	public void test1doubleClickBelowDesignTableToCreateAColumn() {
		this.addDummyTable(NEW_TABLE_NAME);
		String tableName = null;
		UUID tableId = null;

		for (Map.Entry<UUID, String> entry : getDomainFacade().getTableNames().entrySet()) {
			tableName = entry.getValue();
			tableId = entry.getKey();
		}
		getUiFacade().openTableDesignViewMode(tableId, tableName, getDomainFacade().getColumnCharacteristics(tableId));

		Map<UUID, LinkedHashMap<String, Object>> columnDataBefore = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		VerticalComponentList rowsBefore = getTableViewModeDesignTable(tableId).getRows();

		emulateDoubleClick(BELOW_TABLE_X, BELOW_TABLE_Y);

		Map<UUID, LinkedHashMap<String, Object>> columnDataAfter = this.getDomainFacade()
				.getColumnCharacteristics(tableId);
		getUiFacade().updateTableDesignViewMode(tableId, tableName, columnDataAfter);

		VerticalComponentList uiRowsAfter = getTableViewModeDesignTable(tableId).getRows();

		assertEquals(columnDataBefore.size(), columnDataAfter.size() - 1);
		assertEquals(rowsBefore.getComponentsList().size(), uiRowsAfter.getComponentsList().size() - 1);

		for (Map.Entry<UUID, LinkedHashMap<String, Object>> entry : columnDataAfter.entrySet()) {
			UUID columnId = entry.getKey();

			if (!columnDataAfter.containsKey(columnId)) {

				for (Map.Entry<String, Object> columnEntry : entry.getValue().entrySet()) {

					switch (columnEntry.getKey()) {
					case COLUMN_NAME:
						String columnName = (String) columnEntry.getValue();
						assertTrue(columnName.contains(NEW_COLUMN_NAME));
						break;
					case COLUMN_TYPE:
						assertEquals(columnEntry.getValue(), NEW_COLUMN_TYPE);
						break;
					case COLUMN_ALLOW_BLANKS:
						assertEquals(columnEntry.getValue(), NEW_COLUMN_ALLOW_BLANKS);
						break;
					case COLUMN_DEFAULT:
						assertEquals(columnEntry.getValue(), NEW_COLUMN_DEFAULT);
						break;
					}
				}
			}
		}
	}

}
