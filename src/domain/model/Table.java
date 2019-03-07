package domain.model;

import java.util.ArrayList;
import java.util.List;

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
	private void setName(String name) {
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
	public List<Cell> getAllCells() {
		List<Cell> result = new ArrayList<Cell>();
		columns.stream().forEach(c -> result.addAll(c.getCells()));
		return result;
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

}
