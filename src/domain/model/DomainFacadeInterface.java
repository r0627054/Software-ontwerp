package domain.model;

import java.util.List;

public interface DomainFacadeInterface {
	
	//Note op een Table doorgeven via Controller:
	//Volgens mij mag dit, zolang je geen functies van Table zelf gebruikt.
	//Als je Table doorgeeft helemaal tot de Table Component, en deze de domain Table laat gebruiken
	//Mag dit wel volgens mij?
	public Table getTable(String tableName);

	public List<String> getTableNames();
	
	public void addTable(String name);

}
