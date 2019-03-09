package domain.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DomainFacadeInterface {
	
	//update the name of a table in tablesviewmode
	public void updateTableName(UUID id, String newName);
	
	public Map<UUID, String> getTableNames();
	
	public Map<Map<UUID, String>, Map<UUID, Object>> getTableWithIds(UUID tableId);
	
	public void addTable(String name);

	public String getTableNameOfId(UUID id);
	
	public void createNewTable();

	public void deleteTable(UUID id);

	public Map<UUID, Map<String, Object>> getColumnCharacteristics(UUID id);
	
}
