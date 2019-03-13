package ui.model.view;

import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import ui.model.viewmodes.ViewModeType;

public interface UIFacadeInterface {

	public void show();

	public void startup(Map<UUID, String> map);

	public void addPropertyChangeListener(PropertyChangeListener pcl);

	public void removePropertyChangeListener(PropertyChangeListener pcl);

	public ViewModeType getCurrentViewModeType();

	public void throwError(UUID id);

	public void openTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes);

	public void updateTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes);

	public void updateTablesViewMode(Map<UUID, String> map);

	public void openTableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics);

	public void updateTableDesignViewMode(UUID id, String tableNameOfId,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics);

	public void pauseApplication(int index, UUID id);

	public void unpause(int index, UUID columnId);

	public UUID getCurrentTableId();

	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue);

}
