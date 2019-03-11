package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class of Tables containing a name, columns and rows.
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public class Table extends ObjectIdentifier {

	/**
	 * Variable storing the name of the table.
	 */
	String name;

	/**
	 * Variable storing the columns of the table.
	 */
	List<Column> columns = new ArrayList<>();

	/**
	 * Variable storing the rows of the table.
	 */
	List<Row> rows = new ArrayList<>();

	/**
	 * Initialise a new Table with a given name.
	 * 
	 * @param name 
	 * 			The name given to a table.
	 * @effect the name of the Table is set. 
	 * 			| setName(name)
	 */
	public Table(String name) {
		this.setName(name);
	}

	/**
	 * Returns the name of the table.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the table.
	 * 
	 * @param name 
	 * 			The name of the table.
	 * @throws DomainException The name equals null.
	 *         | name == null
	 * @post  The name of the table is set with the given value.
	 *       new.getName() == name
	 */
	public void setName(String name) {
		if (name == null) {
			throw new DomainException("Invalid table name!");
		}
		this.name = name;
	}

	/**
	 * Returns a copy of the columns of the table.
	 */
	public List<Column> getColumns() {
		return new ArrayList<>(this.columns);
	}

	public Column getColumn(UUID columnId) {
		if (columnId == null) {
			throw new DomainException("Cannot get a column with a null columnId.");
		}
		for (Column col : getColumns()) {
			if (col.getId().equals(columnId)) {
				return col;
			}
		}
		return null;
	}

	/**
	 * Sets the columns of the Table.
	 * 
	 * @param columns 
	 * 			the given columns of the table.
	 * @throws DomainException The list of columns equals null. 
	 * 			| name == null
	 */
	private void setColumns(List<Column> columns) {
		if (columns == null) {
			throw new DomainException("Invalid list of columns.");
		}
		this.columns = columns;
	}

	/**
	 * Returns a copy of the rows of the table.
	 */
	public List<Row> getRows() {
		return new ArrayList<>(this.rows);
	}

	/**
	 * Sets the rows of the Table.
	 * 
	 * @param rows 
	 * 			the given rows of the table.
	 * @throws DomainException The list of rows equals null. 
	 * 			| name == null
	 */
	private void setRows(List<Row> rows) {
		if (this.rows == null) {
			throw new DomainException("Invalid list of Rows.");
		}
		this.rows = rows;
	}

	/**
	 * Returns a copy of all cells of the table.
	 * @return 
	 *         A List of all the cells of the tables, these cells are all copies.
	 */
	/*
	 * public List<Cell> getAllCells() { List<Cell> result = new ArrayList<Cell>();
	 * columns.stream().forEach(c -> result.addAll(c.getCells())); return result; }
	 */

	public LinkedHashMap<Map<UUID, String>, LinkedHashMap<UUID, Object>> getTableWithIds() {
		// the first inner map: UUID is column ID and the string is the columnname
		// the second inner map: UUID is the cell ID and the Object is the value
		LinkedHashMap<Map<UUID, String>, LinkedHashMap<UUID, Object>> tableMap = new LinkedHashMap<>();

		for (Column c : getColumns()) {
			tableMap.put(c.getNameWithId(), c.getCellsWithId());
		}

		return tableMap;
	}

	public void addColumn(String name, ValueType type) {
		if (name == null || type == null) {
			throw new DomainException("A name or type cannot be null when adding a column");
		}
		this.columns.add(new Column(name, type));
	}

	public void addColumn(String name, ValueType type, boolean allowBlanks) {
		if (name == null || type == null) {
			throw new DomainException("A name or type cannot be null when adding a column");
		}
		this.columns.add(new Column(name, type, allowBlanks));
	}

	public void addColumn(Column column) {
		if (column == null) {
			throw new DomainException("A new collumn cannot be null when adding a column");
		}
		this.columns.add(column);
	}

	public void addRow() {
		ArrayList<Cell> newCells = new ArrayList<Cell>();

		for (Column c : this.columns) {
			newCells.add(new Cell(c.getType()));
		}

		this.rows.add(new Row(newCells));
	}

	public void addRow(Row r) {
		if (r == null) {
			throw new DomainException("A new row cannot be null when adding a row");
		}
		this.rows.add(r);
	}

	public LinkedHashMap<UUID, LinkedHashMap<String, Object>> getColumnCharacteristics() {
		LinkedHashMap<UUID, LinkedHashMap<String, Object>> characteristics = new LinkedHashMap<>();

		for (Column c : getColumns()) {
			characteristics.put(c.getId(), c.getCharacteristics());
		}

		return characteristics;
	}

	private boolean columnAlreadyExists(UUID id, String columnName) {
		if (columnName == null) {
			throw new DomainException("ColumnName cannot be null to check whether the name already exists.");
		}
		for (Column column : this.getColumns()) {
			if (column.getName().equals(columnName) && !column.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public void createNewColumn() {
		int i = 1;
		boolean newIndexIsFound = false;
		String columnName = null;
		while (!newIndexIsFound) {
			columnName = "Column" + i++;
			if (!this.columnNameAlreadyExists(columnName)) {
				newIndexIsFound = true;
			}
		}
		this.addColumn(new Column(columnName));
	}

	private boolean columnNameAlreadyExists(String columnName) {
		if (columnName == null) {
			throw new DomainException("ColumnName cannot be null to check whether the name already exists.");
		}
		for (Column column : this.getColumns()) {
			if (column.getName().equals(columnName)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasColumn(UUID id) {
		for (Column col : this.getColumns()) {
			if (col.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void updateColumnName(UUID id, String newName) {
		if (columnAlreadyExists(id, newName)) {
			throw new DomainException("This column name already exists!");
		}
		for (Column col : this.getColumns()) {
			if (col.getId() == id) {
				col.setName(newName);
			}
		}
	}
	
	public int getIndexOfColumnCharacteristic(UUID columnId, String string) {
		Column column = this.getColumn(columnId);
		return column.getIndexOfCharacteristic(string);
	}

}
