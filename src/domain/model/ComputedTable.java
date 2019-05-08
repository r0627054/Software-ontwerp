package domain.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.sql.CellId;
import domain.model.sql.Query;
import domain.model.sql.SQLParser;
import domain.model.sql.SQLParser.ParseException;
import domain.model.sql.statements.FromStatement;
import domain.model.sql.tablespecs.InnerJoinTableSpec;
import domain.model.sql.tablespecs.TableSpec;

public class ComputedTable extends Table {

	private Query query;
	private List<Table> queryTables;

	public ComputedTable(String name, Query query, List<Table> tables) {
		super(name);
		this.setQuery(query);
		setQueryTables(tables);
		executeFromStatement();
	}

	private void executeFromStatement() {
		FromStatement from = getQuery().getFromStatement();
		checkValidColumnsAndTables(from);
		Table subTotalTable = this.getTableAtIndex(0).copy();
		
		for (TableSpec spec : from.getTableSpecs()) {
			if(spec instanceof InnerJoinTableSpec) {
				
				
				
				Table tempTable = new Table(this.getName());
				Table newTable  = this.getTableOfName(spec.getRealTableName());
				for (Column c : subTotalTable.getColumns()) {
					tempTable.addColumn(c.blindCopy());
				}
				for(Column c : newTable.getColumns()) {
					tempTable.addColumn(c.blindCopy());
				}
				
				for(Row subTotalRow: subTotalTable.getRows()) {
					for(Row newTableRow: newTable.getRows()) {
						ArrayList<DomainCell> allRowCells = new ArrayList<>(subTotalRow.getCells());
						allRowCells.addAll(newTableRow.getCells());
						Row newRow = new Row(allRowCells);
						tempTable.addRow(newRow);
					}
				}
				subTotalTable = tempTable;
			}
		}
		this.setColumns(subTotalTable.getColumns());
		this.setRows(subTotalTable.getRows());
		
	}
	
	private Table getTableAtIndex(int i) {
		return this.getQueryTables().get(i);
	}

	private void checkValidColumnsAndTables(FromStatement from) {
		List<CellId> list = from.getAllCellIds();
		Map<String, String> namesMap = from.getDisplayToRealNamesMap();

		for (CellId id : list) {
			Table table = getTableOfName(namesMap.get(id.getTableId()));

			if (table == null) {
				throw new DomainException("Invalid table in ON condition in INNER JOIN.");
			}

			if (!table.columnNameExists(id.getColumnName())) {
				throw new DomainException("Invalid table column in ON condition in INNER JOIN.");
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

	public Map<List<Object>, LinkedHashMap<UUID, Object>> getData() {
		return null;
	}

	private Query getQuery() {
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