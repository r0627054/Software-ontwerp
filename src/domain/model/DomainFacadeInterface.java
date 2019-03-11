package domain.model;

import java.util.LinkedHashMap;
import java.util.List;
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

//	public String getTableNameOfColumnId(UUID columnId);

//	public UUID getTableIdOfColumnId(UUID columnId);

}
