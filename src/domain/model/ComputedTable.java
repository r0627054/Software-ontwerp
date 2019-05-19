package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.sql.CellId;
import domain.model.sql.Operator;
import domain.model.sql.Query;
import domain.model.sql.columnSpec.ColumnSpec;
import domain.model.sql.statements.FromStatement;
import domain.model.sql.statements.SelectStatement;
import domain.model.sql.tablespecs.InnerJoinTableSpec;
import domain.model.sql.tablespecs.TableSpec;

/**
 * 
 * A ComputedTable class is a subclass of a Table. It contains a query and the Tables of which it is composed.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class ComputedTable extends Table {
	
	/**
	 * The query on which the computedTable is created.
	 */
	private Query query;
	
	/**
	 * The list of tables of which the computed table is composed of.
	 */
	private List<Table> queryTables;
	
	/**
	 * Contains how often an editable columnSpec is referred to.
	 * It contains the columnSpec with the number of occurrences calculated for the specific table.
	 */
	private Map<ColumnSpec, Integer> editableOccurences;

	/**
	 * Creates a Computed table instance out of the given parameters.
	 *  
	 * 
	 * @param tableId
	 *        The id which will be given to a computed table.
	 * @param name
	 *        The name which will be given to a computed table.
	 * @param query
	 *        The query of how the computed table is composed.
	 * @param tables
	 *        The list of tables of which the table is composed.
	 * @effect The  variables are set and the new table is calculated.
	 *        | super(tableId, name);
	 *        | this.setEditableOccurences(new HashMap<>());
	 *        |	this.setQuery(query);
	 *        |	setQueryTables(tables);
	 *        |	runQuery();
	 */
	public ComputedTable(UUID tableId, String name, Query query, List<Table> tables) {
		super(tableId, name);
		this.setEditableOccurences(new HashMap<>());
		this.setQuery(query);
		setQueryTables(tables);
		runQuery();
	}
	
	/**
	 * Returns the editableOccurences variable.
	 * @return the editableOccurences variable.
	 */
	private Map<ColumnSpec, Integer> getEditableOccurences() {
		return editableOccurences;
	}

	/**
	 * Sets the editableOccurences parameter for the Computed table
	 * @param editableOccurences The parameter to which the variable will be set.
	 * @throws DomainException When the editableOccurences equal null
	 *        | editableOccurences == null
	 * @post The variable equal the given parameter
	 *        | new.editableOccurences.equals(editableOccurences)
	 */
	private void setEditableOccurences(Map<ColumnSpec, Integer> editableOccurences) {
		if(editableOccurences == null) {
			throw new DomainException("The editableOccurences variable cannot be null for the computedtable.");
		}
		this.editableOccurences = editableOccurences;
	}

	/**
	 * Executes the select statement on the given Table which is given as parameter.
	 * @param result
	 *        | The table is the result of the from and where statement. On this table the select statement is executed.
	 * @return The new table on which the select statement is executed.
	 */
	private Table executeSelectStatement(Table result) {
		SelectStatement select = getQuery().getSelectStatement();
		Table tempTable = new Table(getName());
		Map<CellId, Integer> cellIdMap = getCellIdsToIndexMap(getQuery().getCellIdsOfSelect());
		for (int specIndex = 0; specIndex < select.getColumnSpecs().size(); specIndex++) {

			Object[] isEditableObject = select.isEditableForColumnSpecIndex(specIndex);
			Map<CellId, Integer> cellIdsUsedCounterMap = (Map<CellId, Integer>) isEditableObject[0];
			Column c = null;
			if (((Boolean) isEditableObject[1]) && (cellIdsUsedCounterMap.keySet().size() == 1)
					&& ((Integer) cellIdsUsedCounterMap.values().toArray()[0] != 0)) {
				this.getEditableOccurences().put(select.getColumnSpec(specIndex),
						(Integer) ((Map<UUID, Integer>) isEditableObject[0]).values().toArray()[0]);
				CellId cellId = select.getCellIdOfEditable(specIndex);
				c = result.getColumnForIndex(this.getTableIndexFromCellId(cellId)).blindCopy();
				c.setName(select.getColumnNameOfColumnSpec(specIndex));
			} else {
				c = new Column(select.getColumnNameOfColumnSpec(specIndex), false);
			}

			for (int i = 0; i < result.getRows().size(); i++) {
				Row row = result.getRows().get(i);
				DomainCell cell = getQuery().computeCell(row, cellIdMap, specIndex);

				if (i == 0 && !cell.getType().equals(c.getType())) {
					c.setDefaultValue(cell.getType().getDefaultValue());
					c.updateType(cell.getType());
				}
				c.addCell(cell);

			}
			tempTable.addColumn(c);
		}

		if (!tempTable.getColumns().isEmpty()) {

			int rowIndex = 0;
			while (rowIndex < result.getHeightOfColumns()) {
				ArrayList<DomainCell> rowList = new ArrayList<>();
				for (Column c : tempTable.getColumns()) {
					rowList.add(c.getCellAtIndex(rowIndex));
				}
				// tempTable.addRow(new Row(rowList));
				tempTable.addRowWithoutAddingToColumns(new Row(rowList));
				rowIndex++;
			}
		}
		return tempTable;
	}

	/**
	 * Executes the where statement on the give table. It returns the result after the where statement.
	 * @param table
	 *        The table on which the where statement is executed.
	 * @return The table after execution of the where statement.
	 */
	private Table executeWhereStatement(Table table) {
		// Create a empty table with correct columns
		Table result = new Table(this.getName());

		for (Column c : table.getColumns()) {
			result.addColumn(c.blindCopy());
		}

		Map<CellId, Integer> cellIdMap = getCellIdsToIndexMap(getQuery().getCellIdsOfWhere());

		for (Row row : table.getRows()) {
			if (getQuery().getWhereStatement().isRowValid(row, cellIdMap)) {
				result.addRow(row);
			}
		}
		return result;
	}

	/**
	 * Returns all the cells with their corresponding index.
	 * @param cellIdList 
	 *        A list of cellIds.
	 * @return The corresponding mapping of cellId to their index.
	 */
	private Map<CellId, Integer> getCellIdsToIndexMap(List<CellId> cellIdList) {
		Map<CellId, Integer> result = new HashMap<>();

		for (CellId cellId : cellIdList) {
			result.put(cellId, this.getTableIndexFromCellId(cellId));
		}

		return result;
	}

	/**
	 * First checks if the all the columns are valid of the query.
	 * When all the checks are done the actual execution is done of the from statement.
	 * @return The composed table after execution of the from statement.
	 * @effect The validation check is done and the fromstatement is exectuted.
	 *         | checkValidColumnsAndTables();
	 *         | return this.executeSingleAndInnerJoins();
	 */
	private Table executeFromStatement() {
		checkValidColumnsAndTables();
		return this.executeSingleAndInnerJoins();
	}

	/**
	 * Executes the current from statements. This is composed of single from statements and inner joins.
	 * The composed table is composed out of the different tables.
	 * The composed table after the execution of the from statement is returned.
	 * @return The composed table after execution of the from statement.
	 */
	private Table executeSingleAndInnerJoins() {
		FromStatement from = getQuery().getFromStatement();
		Table subTotalTable = this.getTableAtIndex(0).copy();
		List<String> joinedDisplayTableNames = new ArrayList<>();
		joinedDisplayTableNames.add(from.getTableSpecs().get(0).getDisplayTableName());

		//per spec
		for (TableSpec spec : from.getTableSpecs()) {
			if (spec instanceof InnerJoinTableSpec) {
				InnerJoinTableSpec innerSpec = (InnerJoinTableSpec) spec;

				Table tempTable = new Table(this.getName());
				Table newTable = this.getTableOfName(innerSpec.getRealTableName());
				joinedDisplayTableNames.add(innerSpec.getDisplayTableName());

				// voeg alle kolommen toe
				for (Column c : subTotalTable.getColumns()) {
					tempTable.addColumn(c.blindCopy());
				}
				for (Column c : newTable.getColumns()) {
					tempTable.addColumn(c.blindCopy());
				}
				// rijen toevoegen
				for (Row subTotalRow : subTotalTable.getRows()) {
					for (Row newTableRow : newTable.getRows()) {
						ArrayList<DomainCell> allRowCells = new ArrayList<>(subTotalRow.getCells());
						allRowCells.addAll(newTableRow.getCells());
						Row newRow = new Row(allRowCells);

						int leftComparedIndex = this.getTableIndexFromCellId(joinedDisplayTableNames,
								innerSpec.getCellIdLeft());
						int rightComparedIndex = this.getTableIndexFromCellId(joinedDisplayTableNames,
								innerSpec.getCellIdRight());
						DomainCell leftComparedObject = newRow.getCellAtIndex(leftComparedIndex);
						DomainCell rightComparedObject = newRow.getCellAtIndex(rightComparedIndex);
						if (leftComparedObject != null && rightComparedObject != null
								&& (leftComparedObject.getValue() != null) && (rightComparedObject.getValue() != null)
								&& leftComparedObject.compare(rightComparedObject, Operator.EQUAL)) {
							tempTable.addRow(newRow);
						}
					}
				}
				subTotalTable = tempTable;
			}
		}
		return subTotalTable;
	}

	/**
	 *	Returns the (column) index of the cell in the table.
	 * @param tables List of already joined tables (containing the display tableNames)
	 * @param cellId  The cellId in the table.
	 * @param from    The from statement in the query.
	 * @return The index on which the cell is placed in the newly created table. 
	 */
	public int getTableIndexFromCellId(List<String> displayTableNames, CellId cellId) {
		Map<String, String> displayToRealNameMap = this.getQuery().getFromStatement().getDisplayToRealNamesMap();
		String currentDisplayTableName = cellId.getTableId();
		String currentActualcolumnName = cellId.getColumnName();
		if (!displayTableNames.contains(currentDisplayTableName)) {
			throw new DomainException("Join condition does not contain the table!");
		}
		// tables before
		int result = 0;
		for (int i = 0; i < displayTableNames.indexOf(currentDisplayTableName); i++) {
			result += this.getNbrOfColumnsOfTable(displayToRealNameMap.get(displayTableNames.get(i)));
		}
		// in actual table
		result += this.getIndexOfColumnName(displayToRealNameMap.get(currentDisplayTableName), currentActualcolumnName);
		return result;
	}

	/**
	 * Returns the index of the cell in the already computed table.
	 * This table is already composed, so it is a combination of all the tables.
	 * @param cellId The cellId which is placed in the current table.
	 * @return The (column) index it is placed in.
	 * @effect The index is calculated using all the displayTables.
	 *         | List<String> displayTableNames = getQuery().getAllDisplayTableNames();
	 *         | return this.getTableIndexFromCellId(displayTableNames, cellId);
	 */
	public int getTableIndexFromCellId(CellId cellId) {
		List<String> displayTableNames = getQuery().getAllDisplayTableNames();
		return this.getTableIndexFromCellId(displayTableNames, cellId);
	}

	/**
	 * Returns the Table out of which the computed table is composed at the given index.
	 * @param i The index of table.
	 * @return The table at the given index.
	 */
	private Table getTableAtIndex(int i) {
		return this.getQueryTables().get(i);
	}

	/**
	 * Checks if all the query is valid.
	 * This checks whether the table exits and whether all the given columns are valid.
	 * If this is not the case a domain exception is thrown.
	 * @throws DomainException When the table or column in the table does not exist.
	 */
	private void checkValidColumnsAndTables() {
		List<CellId> list = this.getQuery().getAllCellIds();
		Map<String, String> namesMap = this.getQuery().getDisplayToRealNamesMap();

		if(this.getQuery().selectStatementHasDuplicateColumnNames()) {
			throw new DomainException("The computed table cannot have multiple columns with the same name.");
		}
				
		for (CellId id : list) {
			Table table = getTableOfName(namesMap.get(id.getTableId()));

			if (table == null) {
				throw new DomainException("Invalid table in ON condition in INNER JOIN.");
			}

			if (!table.columnNameExists(id.getColumnName())) {
				throw new DomainException("Invalid table column cell id");
			}
		}
	}

	/**
	 * Returns the table of the given name.
	 *  This is a name of table of which the computedTable is composed of.
	 * @param name
	 *         The name of which the table which is request. This is a name of a table of which the computed table is composed of.
	 * @return The table of the given name.
	 * @throws DomainException When the name of the table is empty or when it is equal to null. 
	 *          | name == null || name.trim().isEmpty()
	 */
	private Table getTableOfName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new DomainException("Cannot get a table of an empty name in ComputedTable");
		}

		for (Table t : getQueryTables()) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Returns true when this table is composition of a table with the given name.
	 * @param tableName The name of a table to check whether it is used in this computedTable.
	 * @return True when the this table uses table with the given name; otherwise false.
	 */
	public boolean containsMatchingTable(String tableName) {
		for (Table t : getQueryTables()) {
			if (t.getName().equals(tableName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the query of the computed table.
	 * @return the query of the computed table.
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * Sets the query of (this) computed table.
	 * @param query The query which will be set.
	 * @throws DomainException when the query equals null.
	 *         | query == null
	 * @effect The query instance variable equal the given parameter.
	 *         | new.getQuery().equals(query)
	 */
	private void setQuery(Query query) {
		if (query == null) {
			throw new DomainException("Query cannot be null.");
		}
		this.query = query;
	}

	/**
	 * Returns the list tables of which is table is computed.
	 * @return the list tables of which is table is computed.
	 */
	public List<Table> getQueryTables() {
		return queryTables;
	}

	/**
	 * Sets the list of tables of which this table is computed.
	 * @param queryTables The queryTables of which the computed table is composed.
	 * @throws DomainException when the queryTable parameter equals null.
	 *         | queryTables == null
	 * @effect the query tables are set. So the instance variable equals the given parameter.
	 *         | new.getQueryTables() == queryTables
	 */
	private void setQueryTables(List<Table> queryTables) {
		if (queryTables == null) {
			throw new DomainException("QueryTables cannot be null.");
		}
		this.queryTables = queryTables;
	}

	/**
	 * Returns the number of columns of the requested table.
	 * @param realTableName The name of the actual table. Of which this table is composed.
	 * @return The number of columns the table of the specified name has.
	 */
	private int getNbrOfColumnsOfTable(String realTableName) {
		return this.getTableOfName(realTableName).getNbrOfColumns();
	}

	/**
	 * Returns the index of the column with the specified name of the specified table.
	 * @param actualTableName The name of table of which contains the column.
	 * @param columnName      The name of the column in the given table.
	 * @return The index of the column with the given name in the table with the given table name.
	 *         | this.getTableOfName(actualTableName).getColumnIndexOfName(columnName)
	 * @throws DomainException when the column with the given name couldn't be found in the given table.
	 */
	private int getIndexOfColumnName(String actualTableName, String columnName) {
		int index = this.getTableOfName(actualTableName).getColumnIndexOfName(columnName);
		if (index < 0) {
			throw new DomainException("Table does not contain a column on the given index.");
		}
		return index;
	}

	/**
	 * Returns a list of the column names which are used in table (this is real table name). 
	 * @param name The (real table) name of the  
	 * @return
	 */
	public List<String> getColumnsNamesUsed(String name) {
		String displayName = getQuery().getRealToDisplayNamesMap().get(name);
		if (displayName == null) {
			throw new DomainException("No Display Name found for real table name");
		}
		return getQuery().getUsedColumnNamesOfDisplayTableName(displayName);
	}

	/**
	 * Edits the cell in the table.
	 * Sets the value of the cell to the new value.
	 * And sets the calculated value to the original cell.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @param value
	 *        | the new value of the cell (without calculation).
	 * @post The value of the cell is updated to the new value.
	 *       And the original cell value is also updated
	 */
	@Override
	public void editCell(UUID columnId, UUID cellId, Object value) {
		Object newValue = value;
		if (value instanceof Integer) {
			Integer oldValue = (Integer) value;
			ColumnSpec spec = this.getQuery().getColumnSpecOfDisplayName(this.getColumnName(cellId));
			Integer nbrOfOffcurrences = this.getEditableOccurences().get(spec);
			newValue = (oldValue - spec.getSubtotal()) / nbrOfOffcurrences;
			for (Table t : getQueryTables()) {
				if (t.hasColumn(columnId)) {
					int index = this.getIndexOfCellInColumnId(columnId, cellId);
					Column col = t.getColumn(columnId);
					col.setValueForCellAtIndex(index, newValue);
					break;
				}
			}
		}
		Column column = this.getColumn(columnId);
		column.updateCellValue(cellId, value);
	}

	/**
	 * Updates the cell with the given value. The given value is the value which is computed.
	 * This calculation needs to be reverted to set the original cell.
	 *  
	 * @param columnId
	 *        | The id of the column
	 * @param cellIndex
	 *        | The index of the cell in the column
	 * @param value
	 *        | The value of the cell. The value is the calculated value.
	 * @post The original cell is set to the reverted value and this value is set to the given value.
	 */
	public void updateCellWithComputation(UUID columnId, int cellIndex, Object value) {
		try {
			Object newValue = value;
			if (value instanceof Integer) {
				Integer oldValue = (Integer) value;
				ColumnSpec spec = this.getQuery().getColumnSpecOfDisplayName(this.getColumnNameOfColumnId(columnId));
				Integer nbrOfOffcurrences = this.getEditableOccurences().get(spec);
				newValue = (oldValue) * nbrOfOffcurrences + spec.getSubtotal();

			}
			Column column = this.getColumn(columnId);
			column.setValueForCellAtIndex(cellIndex, newValue);
		} catch (DomainException e) {
			// Does update if the cell not exists.
		}

	}

	/**
	 * Checks whether there is a matching column in the given table.
	 * 
	 * @param colName
	 *        | The name of the column.
	 * @param tableName
	 *        | The name of the table. (display name)
	 * @return True when there exists a column with the given name in the table; Otherwise False.
	 */
	public boolean containsMatchingColumn(String colName, String tableName) {
		List<CellId> ids = getQuery().getAllCellIds();
		Map<String, String> map = getQuery().getDisplayToRealNamesMap();

		for (CellId id : ids) {
			if (map.get(id.getTableId()).equals(tableName) && id.getColumnName().equals(colName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Runs the query Starting from the From statement, where statement, select statement.
	 * Sets the actual columns and rows of the table.
	 */
	public void runQuery() {
		Table result = this.executeFromStatement();
		result = this.executeWhereStatement(result);
		result = this.executeSelectStatement(result);
		this.setColumns(result.getColumns());
		this.setRows(result.getRows());
	}

}
