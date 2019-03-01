package domain.model;

import java.util.HashMap;
import java.util.Map;

public class DomainFacade implements DomainFacadeInterface {

	private static DomainFacade dfInstance = null;
	private Map<String, Table> tableMap = new HashMap<>();

	private DomainFacade() {
		// TODO Auto-generated constructor stub
	}

	/**
	* Creates an domainFacade instance only once. 
	* Returns the only existing instance.
	*
	* @post dfInstance is instantiated
	* | new.getInstance == domainFacadeInstance
	*
	* @notes
	* synchronized makes sure that every thread is synchronized and
	* prevents creating another instance in a other thread.
	**/	
	public static synchronized DomainFacade getInstance() {
		if(dfInstance == null) dfInstance = new DomainFacade();
		return dfInstance;
	}

	public Table getTable(String key) {
		return this.tableMap.get(key);
	}


