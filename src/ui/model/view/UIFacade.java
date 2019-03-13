package ui.model.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import ui.model.viewmodes.ViewModeType;

public class UIFacade implements UIFacadeInterface, PropertyChangeListener {

	private static UIFacade uifInstance = null;
	private View view;
	private PropertyChangeSupport support;

	private UIFacade() {
		this.view = new View("Tablr");
		support = new PropertyChangeSupport(this);
		this.view.addPropertyChangeListener(this);
	}

	/**
	* Creates an UIFacade instance only once.
	* Returns the only existing instance.
	*
	* @post uifInstance is instanciated
	* | new.getInstance == UIFacadeInstance
	*
	* @notes
	* synchronized makes sure that every thread is synchronized and
	* prevents creating another instance in a other thread.
	**/
	public static synchronized UIFacade getInstance() {
		if (uifInstance == null) {
			uifInstance = new UIFacade();
		}
		return uifInstance;
	}

	public void show() {
		this.view.show();
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.support.firePropertyChange(evt);
	}

	@Override
	public void startup(Map<UUID, String> map) {
		view.startup(map);
	}

	@Override
	public ViewModeType getCurrentViewModeType() {
		return this.getView().getCurrentViewModeType();
	}

	private View getView() {
		return this.view;
	}

	@Override
	public void throwError(UUID id) {
		this.getView().throwErrorOnCurrentViewMode(id);
	}

	@Override
	public void openTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		this.getView().openTableRowsViewMode(tableId, tableName, table, columnTypes);
	}

	@Override
	public void updateTableRowsViewMode(UUID tableId, String tableName,
			Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table, Map<UUID, Class<?>> columnTypes) {
		this.getView().updateTableRowsViewMode(tableId, tableName, table, columnTypes);
	}

	@Override
	public void updateTablesViewMode(Map<UUID, String> map) {
		this.getView().updateTablesViewMode(map);
	}

	@Override
	public void openTableDesignViewMode(UUID id, String tableName,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.getView().openTableDesignViewMode(id, tableName, columnCharacteristics);
	}

	@Override
	public void updateTableDesignViewMode(UUID id, String tableNameOfId,
			Map<UUID, LinkedHashMap<String, Object>> columnCharacteristics) {
		this.getView().updateTableDesignViewMode(id, tableNameOfId, columnCharacteristics);
	}

	@Override
	public void pauseApplication(int i, UUID id) {
		this.getView().pauseApplication(i, id);

	}

	@Override
	public void unpause(int columnIndex, UUID columnId) {
		this.getView().unpause(columnIndex, columnId);
	}

	@Override
	public UUID getCurrentTableId() {
		return this.getView().getCurrentViewModeId();
	}

	@Override
	public void setErrorDesignTableCell(int columnIndex, UUID columnId, Object newValue) {
		this.getView().setErrorDesignTableCell(columnIndex, columnId, newValue);
	}

	public void emulateClick(int id, int x, int y, int clickCount) {
		this.getView().emulateMouseClick(id, x, y, clickCount);
	}

	public void emulateKeyPress(int id, int keyCode, char keyChar) {
		this.getView().emulateKeypress(id, keyCode, keyChar);
	}

}
