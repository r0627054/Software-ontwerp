package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.sql.CellId;
import domain.model.sql.Operator;
import domain.model.sql.Query;
import domain.model.sql.SQLParser;
import domain.model.sql.SQLParser.ParseException;
import domain.model.sql.columnSpec.ColumnSpec;
import domain.model.sql.expression.Expression;
import domain.model.sql.statements.FromStatement;
import domain.model.sql.statements.SelectStatement;
import domain.model.sql.tablespecs.InnerJoinTableSpec;
import domain.model.sql.tablespecs.TableSpec;

public class ComputedTable extends Table {

	private Query query;
	private List<Table> queryTables;

	public ComputedTable(UUID tableId, String name, Query query, List<Table> tables) {
		super(tableId, name);
		this.setQuery(query);
		setQueryTables(tables);
		executeFromStatement();
		Table result = this.executeFromStatement();
		result = this.executeWhereStatement(result);
		result = this.executeSelectStatement(result);
		this.setColumns(result.getColumns());
		this.setRows(result.getRows());
	}

	private Table executeSelectStatement(Table result) {
		SelectStatement select = getQuery().getSelectStatement();
		Table tempTable = new Table(getName());
		Map<CellId, Integer> cellIdMap = getCellIdsToIndexMap(getQuery().getCellIdsOfSelect());
		// for (ColumnSpec cs : select.getColumnSpecs()) {
		//System.out.println(select.getColumnSpecs().size());
		for (int specIndex = 0; specIndex < select.getColumnSpecs().size(); specIndex++) {

			
			
			
			
			Column c = new Column(select.getColumnNameOfColumnSpec(specIndex));

			for (Row row : result.getRows()) {
				DomainCell cell = getQuery().computeCell(row, cellIdMap, specIndex);
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
				tempTable.addRow(new Row(rowList));
				rowIndex++;
			}
		}

		return tempTable;
	}

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

	private Map<CellId, Integer> getCellIdsToIndexMap(List<CellId> cellIdList) {
		List<String> displayTableNames = getQuery().getAllDisplayTableNames();
		Map<CellId, Integer> result = new HashMap<>();

		for (CellId cellId : cellIdList) {
			result.put(cellId, this.getTableIndexFromCellId(cellId));
		}

		return result;
	}

	private Table executeFromStatement() {
		checkValidColumnsAndTables();
		return this.executeSingleAndInnerJoins();
	}

	private Table executeSingleAndInnerJoins() {
		FromStatement from = getQuery().getFromStatement();
		Table subTotalTable = this.getTableAtIndex(0).copy();
		List<String> joinedDisplayTableNames = new ArrayList<>();
		joinedDisplayTableNames.add(from.getTableSpecs().get(0).getDisplayTableName());

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
	 * 
	 * @param tables List of already joined tables (containing the display tableNames)
	 * @param cellId
	 * @param from
	 * @return
	 */
	public int getTableIndexFromCellId(List<String> displayTableNames, CellId cellId) {
		//Map<String, String> displayToRealNameMap = from.getDisplayToRealNamesMap();
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

	
	public int getTableIndexFromCellId(CellId cellId) {
		List<String> displayTableNames = getQuery().getAllDisplayTableNames();
		return this.getTableIndexFromCellId(displayTableNames, cellId);
	}
	
	
	private Table getTableAtIndex(int i) {
		return this.getQueryTables().get(i);
	}

	private void checkValidColumnsAndTables() {
		List<CellId> list = this.getQuery().getAllCellIds();
		Map<String, String> namesMap = this.getQuery().getDisplayToRealNamesMap();

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

	public boolean containsMatchingTable(String tableName) {
		for (Table t : getQueryTables()) {
			if (t.getName().equals(tableName)) {
				return true;
			}
		}
		return false;
	}

	public Query getQuery() {
		return query;
	}

	private void setQuery(Query query) {
		if (query == null) {
			throw new DomainException("Query cannot be null.");
		}
		this.query = query;
	}

	public List<Table> getQueryTables() {
		return queryTables;
	}

	private void setQueryTables(List<Table> queryTables) {
		if (queryTables == null) {
			throw new DomainException("QueryTables cannot be null.");
		}
		this.queryTables = queryTables;
	}

	private int getNbrOfColumnsOfTable(String realTableName) {
		return this.getTableOfName(realTableName).getNbrOfColumns();
	}

	private int getIndexOfColumnName(String actualTableName, String columnName) {
		int index = this.getTableOfName(actualTableName).getColumnIndexOfName(columnName);
		if (index < 0) {
			throw new DomainException("Table does not contain a column on the given index.");
		}
		return index;
	}

}

/*
 * Table currentComputedTable = getQueryTables().get(0).copy();
 * 
 * List<InnerJoinCondition> joinConditions = getQuery().getJoinConditions();
 * List<String> displayNamesList = getQuery().getDisplayTableNames(); int
 * currentTableNameIndex = 1;
 * 
 * for (InnerJoinCondition joinCondition : joinConditions) { if
 * (!displayNamesList.contains(joinCondition.getTableName1()) ||
 * !displayNamesList.contains(joinCondition.getTableName2())) { throw new
 * DomainException("Invalid 'ON' tableDisplayName"); } String newTableColumnName
 * = null; String currentComputedName = null; if
 * (joinCondition.getTableName1().equals(displayNamesList.get(
 * currentTableNameIndex))) { newTableColumnName = joinCondition.getColTable1();
 * currentComputedName = joinCondition.getColTable2(); } else {
 * newTableColumnName = joinCondition.getColTable2(); currentComputedName =
 * joinCondition.getColTable1(); } if
 * (!currentComputedTable.columnNameAlreadyExists(currentComputedName) ||
 * !getQueryTables().get(currentTableNameIndex).columnNameAlreadyExists(
 * newTableColumnName)) { throw new
 * DomainException("Join condition columns does not match."); }
 * 
 * Table tempTable = new Table(getName());
 * 
 * for (Column c : currentComputedTable.getColumns()) { tempTable.addColumn(new
 * Column(c.getName())); //TODO Add UUID in deze constructor? } for (Column c :
 * getQueryTables().get(currentTableNameIndex).getColumns()) {
 * tempTable.addColumn(new Column(c.getName())); }
 * 
 * // for(DomainCell cell: )
 * 
 * //TODO Vul de rijen in met waardes van de 2 tabellen waarvan de condition ok
 * is //Current = temp //TODO SELECT // for (Column c: )
 * 
 * // Column col1 = getQueryTables(). currentTableNameIndex++; }
 * //this.setColumns(temptable.getcolumns) // this.setRows(temptTable.getRows);
 * return this.getTableWithIds();
 */