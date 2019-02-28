package domain.model;

import java.util.HashMap;
import java.util.Map;

public class DomainFacade implements DomainFacadeInterface {

	private Map<String, Table> tableMap = new HashMap<>();

	public DomainFacade() {
		// TODO Auto-generated constructor stub
	}

	public Table getTable(String key) {
		return this.tableMap.get(key);
	}

}
