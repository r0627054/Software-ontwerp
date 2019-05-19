package domain.model.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.Column;
import domain.model.DomainCell;
import domain.model.Row;
import domain.model.sql.columnSpec.ColumnSpec;
import domain.model.sql.expression.Expression;
import domain.model.sql.statements.FromStatement;
import domain.model.sql.statements.SelectStatement;
import domain.model.sql.statements.Statement;
import domain.model.sql.statements.WhereStatement;
/**
 * A Query contains a SelectStatement a FromStatement and a WhereStatement.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Query {
	
	/**
	 * Variable storing the selectStatement.
	 */
	private Statement selectStatement;
	
	/**
	 * Variable storing the fromStatement.
	 */
	private Statement fromStatement;
	
	/**
	 * Variable storing the whereStatement.
	 */
	private Statement whereStatement;

	/**
	 * Creates an instance and sets all the given statements.
	 * 
	 * @param selectStatement The select statement of the query.
	 * @param fromStatement   The from   statement of the query.
	 * @param whereStatement  The where  statement of the query.
	 * @effect The statements are set.
	 *         | this.setSelectStatement(selectStatement);
	 *         | this.setFromStatement(fromStatement);
	 *         | this.setWhereStatement(whereStatement);
	 */
	public Query(SelectStatement selectStatement, FromStatement fromStatement, WhereStatement whereStatement) {
		this.setSelectStatement(selectStatement);
		this.setFromStatement(fromStatement);
		this.setWhereStatement(whereStatement);
	}

	/**
	 * Returns the Select statement of the query.
	 * @return The select statement of the query.
	 */
	public SelectStatement getSelectStatement() {
		return (SelectStatement) selectStatement;
	}

	/**
	 * Sets the SelectStatment of the query.
	 * @param selectStatement the select statement to which it will be set.
	 * @throws SqlException when the select statement equals null.
	 *                      | selectStatement == null
	 * @effect The selectStatement equals the selectStatement parameter.
	 *          | new.getSelectStatement() == selectStatement 
	 */
	private void setSelectStatement(SelectStatement selectStatement) {
		if (selectStatement == null) {
			throw new SqlException("Cannot set null selectStatement.");
		}
		this.selectStatement = selectStatement;
	}

	/**
	 * Returns the from statement of the query.
	 * @return The from statement of the query.
	 */
	public FromStatement getFromStatement() {
		return (FromStatement) fromStatement;
	}

	/**
	 * Sets the fromStatment of the query.
	 * @param fromStatment the from statement to which it will be set.
	 * @throws SqlException when the from statement equals null.
	 *                      | fromStatment == null
	 * @effect The fromStatment equals the fromStatment parameter.
	 *          | new.getFromStatment() == fromStatment 
	 */
	private void setFromStatement(FromStatement fromStatement) {
		if (fromStatement == null) {
			throw new SqlException("Cannot set null fromStatement.");
		}
		this.fromStatement = fromStatement;
	}

	/**
	 * Returns the where statement of the query.
	 * @return The where statement of the query.
	 */
	public WhereStatement getWhereStatement() {
		return (WhereStatement) whereStatement;
	}

	/**
	 * Sets the whereStatement of the query.
	 * @param whereStatement the where statement to which it will be set.
	 * @throws SqlException when the where statement equals null.
	 *                      | whereStatement == null
	 * @effect The whereStatement equals the whereStatement parameter.
	 *          | new.getWhereStatement() == whereStatement 
	 */
	private void setWhereStatement(WhereStatement whereStatement) {
		if (whereStatement == null) {
			throw new SqlException("Cannot set null whereStatement.");
		}
		this.whereStatement = whereStatement;
	}

	/**
	 * Returns a list of names of tables which are used in the query.
	 * @return The list of names of tables which are used in the query.
	 *         | getFromStatement().getAllTables();
	 */
	public List<String> getAllUsedTables() {
		return getFromStatement().getAllTables();
	}

	/**
	 * Returns all the cellIds which are used in the query.
	 * @return all the cellIds which are used in the query.
	 */
	public List<CellId> getAllCellIds() {
		List<CellId> result = new ArrayList<>();
		result.addAll(this.getSelectStatement().getAllCellIds());
		result.addAll(this.getFromStatement().getAllCellIds());
		result.addAll(this.getWhereStatement().getAllCellIds());
		return result;
	}

	/**
	 * Returns all the cellIds which are used in the where statement.
	 * @return all the cellIds which are used in the where statement.
	 *       | this.getWhereStatement().getAllCellIds();
	 */
	public List<CellId> getCellIdsOfWhere() {
		return this.getWhereStatement().getAllCellIds();
	}

	/**
	 * Returns all the cellIds which are used in the select statement.
	 * @return all the cellIds which are used in the select statement.
	 *       | getSelectStatement().getAllCellIds();
	 */
	public List<CellId> getCellIdsOfSelect() {
		return getSelectStatement().getAllCellIds();
	}

	/**
	 * Returns the map which links the display tableNames to the real table names.
	 * @return the map which links the display tableNames to the real table names.
	 *        | this.getFromStatement().getDisplayToRealNamesMap();
	 */
	public Map<String, String> getDisplayToRealNamesMap() {
		return this.getFromStatement().getDisplayToRealNamesMap();
	}
	
	/**
	 * Returns the map which links the real tableNames to the display table names.
	 * @return the map which links the real tableNames to the display table names.
	 *        | this.getFromStatement().getRealToDisplayNamesMap();
	 */
	public Map<String,String> getRealToDisplayNamesMap(){
		return this.getFromStatement().getRealToDisplayNamesMap();
	}
	
	/**
	 * Returns all the display tableNames of the query.
	 * @return all the display tableNames of the query.
	 *        | this.getFromStatement().getAllDisplayTableNames();
	 */
	public List<String> getAllDisplayTableNames() {
		return this.getFromStatement().getAllDisplayTableNames();
	}

	/**
	 * Returns the String result of the toString method of the statements in the correct order.
	 * @return the String result of the toString method of the statements in the correct order.
	 *         | getSelectStatement().toString() + " " + getFromStatement().toString() + " " + getWhereStatement().toString();
	 */
	@Override
	public String toString() {
		return getSelectStatement().toString() + " " + getFromStatement().toString() + " "
				+ getWhereStatement().toString();
	}
	
	/**
	 * Checks whether a row is valid or not.
	 * @param row      The current row.
	 * @param cellIdMap The map which links the cellid to the correct index.
	 * @return True when the row is valid according to the query: otherwise False.
	 *         | this.getWhereStatement().isRowValid(row, cellIdMap)
	 */
	public boolean isRowValid(Row row, Map<CellId, Integer> cellIdMap) {
		return this.getWhereStatement().isRowValid(row, cellIdMap);
	}
	
	/**
	 * Compute the domainCell using the select statement. 
	 * @param row       The current row.
	 * @param cellIdMap The map which links the cellid to the correct index.
	 * @param specIndex The index of the tableSpec.
	 * @return  The newly calculated DomainCell.
	 *          | this.getSelectStatement().computeCell(row, cellIdMap,specIndex)
	 */
	public DomainCell computeCell(Row row, Map<CellId, Integer> cellIdMap, int specIndex) {
		return this.getSelectStatement().computeCell(row, cellIdMap,specIndex);
	}

	/**
	 * Returns the cellId with the real columnName given the cellId with the display columnName.
	 * @param diffId The CellId with the display columnName.
	 * @return the cellId with the real columnName given the cellId with the display columnName.
	 *          | this.getSelectStatement().getCellIdWithRealColumnName(diffId)
	 */
	public CellId getCellIdWithRealColumnNameFromSelect(CellId diffId) {
		return this.getSelectStatement().getCellIdWithRealColumnName(diffId);
	}

	/**
	 * Checks whether the query uses a table with the given name.
	 * @param name The name of the table.
	 * @return True when the query uses table with the given name.
	 *          | this.getFromStatement().usesTable(name)
	 */
	public boolean usesTable(String name) {
		return this.getFromStatement().usesTable(name);
	}

	/**
	 * Returns ColumnNames which are used for the real displayName.
	 * @param displayName The dispkay name of the table.
	 * @return The list of columnNames used from the display table name.
	 *         | this.getSelectStatement().getUsedColumnNamesOfDisplayTableName(displayName)
	 */
	public List<String> getUsedColumnNamesOfDisplayTableName(String displayName) {
		return this.getSelectStatement().getUsedColumnNamesOfDisplayTableName(displayName);
	}

	/**
	 * Returns all the columnNames of the select statement.
	 * @return a list of columnNames of the select statement.
	 *         | this.getSelectStatement().getAllSelectColumnNames();
	 */
	public List<String> getAllSelectColumnNames() {
		return this.getSelectStatement().getAllSelectColumnNames();
	}

	/**
	 * Returns a list of CellId's for the given columnName.
	 * @param columnNameOfEditedCell The name of the column.
	 * @return a list of CellId's for the given columnName.
	 *         | this.getSelectStatement().getCellIdOfColumnName(columnNameOfEditedCell);
	 */
	public List<CellId> getCellIdOfColumnName(String columnNameOfEditedCell) {
		return this.getSelectStatement().getCellIdOfColumnName(columnNameOfEditedCell);
	}
	
	/**
	 * Returns the columnSpec of where the display name occurs.
	 * @param displayName The display name of the column.
	 * @return The columnSpec of where the display name occurs.
	 *         | this.getSelectStatement().getColumnSpecOfDisplayName(displayName);
	 */
	public ColumnSpec getColumnSpecOfDisplayName(String displayName){
		return this.getSelectStatement().getColumnSpecOfDisplayName(displayName);
	}
	
	/**
	 * Checks whether the select statement has duplicate columnNames.
	 * @return True when there are multiple columns with the same columnName otherwise false.
	 */
	public boolean selectStatementHasDuplicateColumnNames() {
		return this.getSelectStatement().hasDuplicateColumnNames();
	}
	
	
}
