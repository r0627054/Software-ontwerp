package domain.model.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.sql.statements.FromStatement;
import domain.model.sql.statements.SelectStatement;
import domain.model.sql.statements.WhereStatement;

public class Query {
	private SelectStatement selectStatement;
	private FromStatement fromStatement;
	private WhereStatement whereStatement;

	public Query(SelectStatement selectStatement, FromStatement fromStatement, WhereStatement whereStatement) {
		this.setSelectStatement(selectStatement);
		this.setFromStatement(fromStatement);
		this.setWhereStatement(whereStatement);
	}

	public SelectStatement getSelectStatement() {
		return selectStatement;
	}

	private void setSelectStatement(SelectStatement selectStatement) {
		if(selectStatement == null) {
			throw new SqlException("Cannot set null selectStatement.");
		}
		this.selectStatement = selectStatement;
	}

	public FromStatement getFromStatement() {
		return fromStatement;
	}

	private void setFromStatement(FromStatement fromStatement) {
		if(fromStatement == null) {
			throw new SqlException("Cannot set null fromStatement.");
		}
		this.fromStatement = fromStatement;
	}

	public WhereStatement getWhereStatement() {
		return whereStatement;
	}

	private void setWhereStatement(WhereStatement whereStatement) {
		if(whereStatement == null) {
			throw new SqlException("Cannot set null whereStatement.");
		}
		this.whereStatement = whereStatement;
	}

	public List<String> getAllUsedTables() {
		return getFromStatement().getAllTables();
	}

}
