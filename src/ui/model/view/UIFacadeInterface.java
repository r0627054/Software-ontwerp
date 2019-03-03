package ui.model.view;

import java.util.List;

public interface UIFacadeInterface {

	public void show();
	
	//Voor TablesViewMode lijst van tabellen
	//Controller set de lijst van Strings (domain > UI) via constructor
	//Normaal is dit leeg, tenzij je al data opgeslagen had.
	public void setTableNames(List<String> tableNames);
	
}
