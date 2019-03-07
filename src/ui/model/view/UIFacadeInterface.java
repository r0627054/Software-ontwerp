package ui.model.view;

import java.beans.PropertyChangeListener;
import java.util.List;
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
	
}
