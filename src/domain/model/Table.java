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
public class Table {

	String name;
	List<Column> columns = new ArrayList<>();
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

	public void addColumn(Column column) {

	}

	/**
	 * Return the name of the table.
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
	 * 			| name == null
	 */
	private void setName(String name) {
		if (name == null) {
			throw new DomainException("Invalid Table name!");
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

	public List<Cell> getAllCells() {
		List<Cell> result = new ArrayList<Cell>();
		columns.stream().forEach(c -> result.addAll(c.getCells()));
		return result;
	}

}
