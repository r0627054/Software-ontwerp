package domain.model;

import java.util.List;

import domain.model.sql.Query;
import domain.model.sql.SQLParser;
import domain.model.sql.SQLParser.ParseException;


public class ComputedTable extends Table {

	private Query query;
	
	public ComputedTable(String name, Query query) {
		super(name);
		this.setQuery(query);
	}

	private Query getQuery() {
		return query;
	}

	private void setQuery(Query query) {
		if(query == null) {
			throw new DomainException("Query cannot be null.");
		}
		this.query = query;
	}
	
	
	
}
