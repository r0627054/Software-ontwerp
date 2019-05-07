package domain.model;

import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class ComputedTable extends Table {

	private String query;

	public ComputedTable(String name, String query, List<Table> originalTables) {
		super(name);
		this.updateQuery(query, originalTables);
	}

	public void updateQuery(String query, List<Table> originalTables) {
		try {
			SQLParser.parseQuery(query);
			this.setQuery(query);
			// TODO: Add logic that edits columns/rows
		} catch (ParseException e) {
		}
	}

	public String getQuery() {
		return query;
	}

	private void setQuery(String query) {
		if (query == null || query.trim().isEmpty()) {
			throw new DomainException("Query cannot be null of a ComputedTable");
		}
		this.query = query;
	}

}
