package ui.model.view;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface UIFacadeInterface {

	public void show();
	
	//Voor TablesViewMode lijst van tabellen
	//Controller set de lijst van Strings (domain > UI) via constructor
	//Normaal is dit leeg, tenzij je al data opgeslagen had.
	public void startup(List<String> tableNames);
	public void addPropertyChangeListener(PropertyChangeListener pcl);
	public void removePropertyChangeListener(PropertyChangeListener pcl);

	
}
