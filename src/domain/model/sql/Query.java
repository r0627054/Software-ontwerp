package domain.model.sql;

import java.util.List;

public class Query {
	private String query;
	
	public Query(String query) {
		this.setQuery(SQLParser.parseQuery(query));
	}

	public String getQuery() {
		return query;
	}

	private void setQuery(String query) {
		this.query = query;
	}
	
	public List<String> getTableNames(){
		//TO DO implementation
		return null;
	}
	
	public List<String> getColumnNames(){
		//TO DO implementation
		return null;
	}
	
}
