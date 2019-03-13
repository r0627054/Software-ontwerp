package domain.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public interface DomainFacadeInterface {

	public void updateTableName(UUID id, String newName);

	public void updateColumnName(UUID tableId, UUID columnId, String newName);

	public Map<UUID, String> getTableNames();

	public Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> getTableWithIds(UUID tableId);

	public void addTable(String name);

	public String getTableNameOfId(UUID id);

	public void createNewTable();

	public void deleteTable(UUID id);

	public Map<UUID, LinkedHashMap<String, Object>> getColumnCharacteristics(UUID id);

	public void addColumnToTable(UUID id);

	public int getIndexOfColumnCharacteristic(UUID tableId, UUID columnId, String characteristic);

	public void setColumnType(UUID tableId, UUID columnId, ValueType newType);

	public void setAllowBlanks(UUID tableId, UUID columnId, boolean newBool);

	public void setColumnDefaultValue(UUID tableId, UUID columnId, Object newDefaultValue);

	public ValueType getValueTypeOfColumn(UUID tableId, UUID columnId);

	public boolean getColumnAllowBlanks(UUID tableId, UUID columnId);

	public void deleteColumn(UUID tableId, UUID columnId);

	public void createNewRow(UUID tableId);

	public void editCellInTable(UUID tableId, UUID columnId, UUID cellId, Object newValue);

	public UUID getColumnId(UUID tableId, UUID cellId);

	Map<UUID, Class<?>> getColumnTypes(UUID tableId);

	public int getIndexOfCellInColumnId(UUID tableId, UUID columnId, UUID cellId);

	public void deleteRow(UUID tableId, UUID rowId);

	public UUID getRowId(UUID tableId, UUID cellIdOfFirstElement);

//	public String getTableNameOfColumnId(UUID columnId);

//	public UUID getTableIdOfColumnId(UUID columnId);

}
