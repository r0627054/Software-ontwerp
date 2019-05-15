package domain.model.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.Column;
import domain.model.DomainCell;
import domain.model.Row;
import domain.model.sql.expression.Expression;
import domain.model.sql.statements.FromStatement;
import domain.model.sql.statements.SelectStatement;
import domain.model.sql.statements.Statement;
import domain.model.sql.statements.WhereStatement;

public class Query {
	private Statement selectStatement;
	private Statement fromStatement;
	private Statement whereStatement;

	public Query(SelectStatement selectStatement, FromStatement fromStatement, WhereStatement whereStatement) {
		this.setSelectStatement(selectStatement);
		this.setFromStatement(fromStatement);
		this.setWhereStatement(whereStatement);
	}

	public SelectStatement getSelectStatement() {
		return (SelectStatement) selectStatement;
	}

	private void setSelectStatement(SelectStatement selectStatement) {
		if (selectStatement == null) {
			throw new SqlException("Cannot set null selectStatement.");
		}
		this.selectStatement = selectStatement;
	}

	public FromStatement getFromStatement() {
		return (FromStatement) fromStatement;
	}

	private void setFromStatement(FromStatement fromStatement) {
		if (fromStatement == null) {
			throw new SqlException("Cannot set null fromStatement.");
		}
		this.fromStatement = fromStatement;
	}

	public WhereStatement getWhereStatement() {
		return (WhereStatement) whereStatement;
	}

	private void setWhereStatement(WhereStatement whereStatement) {
		if (whereStatement == null) {
			throw new SqlException("Cannot set null whereStatement.");
		}
		this.whereStatement = whereStatement;
	}

	public List<String> getAllUsedTables() {
		return getFromStatement().getAllTables();
	}

	public List<CellId> getAllCellIds() {
		List<CellId> result = new ArrayList<>();
		result.addAll(this.getSelectStatement().getAllCellIds());
		result.addAll(this.getFromStatement().getAllCellIds());
		result.addAll(this.getWhereStatement().getAllCellIds());
		return result;
	}

	public List<CellId> getCellIdsOfWhere() {
		return this.getWhereStatement().getAllCellIds();
	}

	public List<CellId> getCellIdsOfSelect() {
		return getSelectStatement().getAllCellIds();
	}

	public Map<String, String> getDisplayToRealNamesMap() {
		return this.getFromStatement().getDisplayToRealNamesMap();
	}

	public List<String> getAllDisplayTableNames() {
		return this.getFromStatement().getAllDisplayTableNames();
	}

	@Override
	public String toString() {
		return getSelectStatement().toString() + " " + getFromStatement().toString() + " "
				+ getWhereStatement().toString();
	}
	
	public boolean isRowValid(Row row, Map<CellId, Integer> cellIdMap) {
		return this.getWhereStatement().isRowValid(row, cellIdMap);
	}
	
	public DomainCell computeCell(Row row, Map<CellId, Integer> cellIdMap, int specIndex) {
		return this.getSelectStatement().computeCell(row, cellIdMap,specIndex);
	}

	public CellId getCellIdWithRealColumnNameFromSelect(CellId diffId) {
		return this.getSelectStatement().getCellIdWithRealColumnName(diffId);
	}
	
}
