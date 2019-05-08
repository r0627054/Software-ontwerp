package domain.model.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	public List<String> getDisplayTableNames() {
		return new ArrayList<String>(getTableNames().values());
	}

	public List<String> getRealTableNames() {
		return new ArrayList<String>(getTableNames().keySet());
	}

	public Map<String, String> getTableNames() {
		return new SQLParser(getQuery()).getTableNames();
	}
	
	public List<InnerJoinCondition> getJoinConditions(){
		return new SQLParser(getQuery()).getJoinConditions();
	}

	public List<String> getColumnNames() {
		// TO DO implementation
		return null;
	}

}
