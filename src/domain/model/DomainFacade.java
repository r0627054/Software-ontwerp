package domain.model;

import java.util.HashMap;
import java.util.Map;

public class DomainFacade implements DomainFacadeInterface {

	private static DomainFacade dfInstance = null;
	private Map<String, Table> tableMap = new HashMap<>();

	private DomainFacade() {
		// TODO Auto-generated constructor stub
	}
	
	private synchronized static void createInstance() {
		if (dfInstance == null) dfInstance = new DomainFacade();
	}
	
	public static DomainFacade getInstance() {
		if(dfInstance == null) createInstance();
		return dfInstance;
	}

	public Table getTable(String key) {
		return this.tableMap.get(key);
	}

}
