package domain.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * 
 * A class of Tables containing a name, columns and rows.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Table extends ObjectIdentifier {

	/**
	 * Variable storing the name of the table.
	 */
	private String name;

	/**
	 * Variable storing the columns of the table.
	 */
	private List<Column> columns = new ArrayList<>();

	/**
	 * Variable storing the rows of the table.
	 */
	private List<Row> rows = new ArrayList<>();

	/**
	 * Initialise a new Table with a given name.
	 * @param tableId 
	 * 
	 * @param name 
	 * 			The name given to a table.
	 * @effect the name of the Table is set. 
	 * 			| setName(name)
	 */
	public Table(String name) {
		this.setName(name);
	}

	public Table(UUID tableId, String name) {
		super(tableId);
		this.setName(name);
	}

	public Table(String name, UUID id, List<Row> rows, List<Column> cols) {
		super(id);
		this.setName(name);
		this.setRows(rows);
		this.setColumns(cols);
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
	 *         | new.getName().equals(name)
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

	/**
	 * Returns the column which has the following columnId.
	 * If there is not column in the table with the given id,
	 * null is returned.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @return The column with the given id. Null if there is no such column in the table.
	 *         |for (Column col : getColumns()) {
	 *		   |   if (col.getId().equals(columnId)) {
	 *		   |	return col;
	 * @throws DomainException if the columnId equals null.
	 *                         | columnId == null
	 */
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
	 * 			| columns == null
	 * @post the columns of the table are equal to the given columns.
	 *          | new.getColumns().equals(columns)
	 */
	protected void setColumns(List<Column> columns) {
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
	 * 			| rows == null
	 * @post The rows of the table are equal to the given rows.
	 *         | new.getRows().equals(rows)
	 */
	protected void setRows(List<Row> rows) {
		if (this.rows == null) {
			throw new DomainException("Invalid list of Rows.");
		}
		this.rows = rows;
	}

	/**
	 * Returns a map with all the table information of the given tableId.
	 * The first list: ColumnId, ColumnName and Class of this column.
	 * The second inner map, the key: UUID of cell and the value: the value of the cell in the column.
	 * 
	 * @param tableId
	 *        The tableId of which the information should be gathered.
	 * @return a map with all the information associated with the given tableId.
	 */
	public LinkedHashMap<List<Object>, LinkedHashMap<UUID, Object>> getTableWithIds() {
		LinkedHashMap<List<Object>, LinkedHashMap<UUID, Object>> tableData = new LinkedHashMap<>();

		for (Column c : getColumns()) {
//			System.out.println(c.getColumnIdAndNameAndClass());
//			System.out.println("######\n" + c.getCellsWithId());
			tableData.put(c.getColumnIdAndNameAndClass(), c.getCellsWithId());
		}

		return tableData;
	}

	/**
	 * Adds a given column to the table.
	 * 
	 * @param column
	 * @effect The new column is created with the parameters and added to the table.
	 *        | this.columns.add(new Column(name, type))
	 * @throws DomainException if the column equal null
	 *        | column == null
	 */
	public void addColumn(Column column) {
		if (column == null) {
			throw new DomainException("A new collumn cannot be null when adding a column");
		}
		this.columns.add(column);
	}

	/**
	 * Adds an empty row to the bottom of the table.
	 * Every cell has the default value of their column.
	 * 
	 * @effect the row is added to the table and the cell of these rows are added to the correct column.
	 *       | for (Column col : this.getColumns()) {
	 *		 |    Cell newCell = new Cell(col.getType(), col.getDefaultValue());
	 *		 |    newCells.add(newCell);
	 *		 |    col.addCell(newCell);
	 *		 | rows.add(new Row(newCells));
	 *
	 */
	public void addRow() {
		ArrayList<DomainCell> newCells = new ArrayList<DomainCell>();

		for (Column col : this.getColumns()) {
			DomainCell newCell = new DomainCell(col.getType(), col.getDefaultValue());
			newCells.add(newCell);
			col.addCell(newCell);
		}

		this.rows.add(new Row(newCells));
	}

	/**
	 * Adds a row to the table.
	 * 
	 * @param r 
	 *       | the row which will be added
	 * @throws if the row equals null
	 *       | r == null
	 * @effect the row is added to the table
	 *       | this.rows.add(r);
	 */
	public void addRow(Row r) {
		if (r == null) {
			throw new DomainException("A new row cannot be null when adding a row");
		}
		for (int i = 0; i < r.getCells().size(); i++) {
			this.getColumns().get(i).addCell(r.getCells().get(i));
		}
		this.rows.add(r);
	}
	
	public void addRowWithoutAddingToColumns(Row r) {
		if(r == null) {
			throw new DomainException("A new row cannot be null when adding a row.");
		}
		this.rows.add(r);
	}

	/**
	 * Returns map where the key is the id of the column, and the value is a linkedHashList of characteristics.
	 * (the characteristic values are: column name, type, allow Blanks, Default Value)
	 * 
	 * @return a map where the key is the id of the column, and the value is a linkedHashList of characteristics. (column name, type, allow Blanks, Default Value)
	 *        | LinkedHashMap<UUID, LinkedHashMap<String, Object>> characteristics = new LinkedHashMap<>();
	 *        |    for (Column c : getColumns()) {
	 *		  |       characteristics.put(c.getId(), c.getCharacteristics());
	 */
	public LinkedHashMap<UUID, LinkedHashMap<String, Object>> getColumnCharacteristics() {
		LinkedHashMap<UUID, LinkedHashMap<String, Object>> characteristics = new LinkedHashMap<>();

		for (Column c : getColumns()) {
			characteristics.put(c.getId(), c.getCharacteristics());
		}

		return characteristics;
	}

	/**
	 * Checks whether there exists another column with the same name.
	 * 
	 * @param id
	 *        | the id of the column
	 * @param columnName
	 *        | the column name
	 * @return true if the there exists another column with this name, otherwise false.
	 * @throws DomainException if the column name equals null
	 *        | columnName == null 
	 * 
	 */
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

	/**
	 * Creates a new Column with a unique name.
	 * Its name is columnN, where N is a number, and it is different from the names of the existing columns.
	 * The cells contain the default value of the column.
	 */
	public void createNewColumn() {
		int i = 1;
		boolean newIndexIsFound = false;
		String columnName = null;
		while (!newIndexIsFound) {
			columnName = "Column" + i++;
			if (!this.columnNameExists(columnName)) {
				newIndexIsFound = true;
			}
		}

		Column newColumn = new Column(columnName);
		for (int j = 0; j < this.getRows().size(); j++) {
			newColumn.addCell(new DomainCell(newColumn.getType(), newColumn.getDefaultValue()));
		}

		this.addColumn(newColumn);
	}

	/**
	 * Checks whether a column already exists with the given name.
	 * 
	 * @param columnName
	 *        | The name of the column
	 * @return true if there already exists a column with the given name, otherwise false.
	 * @throws DomainException if the column name equals null
	 *        | columnName == null 
	 */
	public boolean columnNameExists(String columnName) {
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

	/**
	 * Checks whether a column already exists with the given id.
	 * @param id
	 *        | The id of the column
	 * @return true if there already exists a column with the given id, otherwise false.
	 */
	public boolean hasColumn(UUID id) {
		for (Column col : this.getColumns()) {
			if (col.getId() == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Updates the name of the column.
	 * The name of the column (with columnId), will be changed to the new name.
	 *  
	 * @param id
	 *        | The id of the column.
	 * @param newName
	 *        | The new name for the column.
	 * @throws DomainException when there already exists another column with that name.
	 *        | columnAlreadyExists(id, newName)
	 * @post The name is set to the newName for the column.
	 *        | this.getColumn(id).setName(newName)
	 */
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

	/**
	 * Gets the index position of the characteristic in the column (with the given columnId).
	 * 	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @param characteristic
	 *        | The characteristic string.
	 * @return the index position of the characteristic.
	 *        | getColumn(columnId).getIndexOfCharacteristic(characteristic);
	 * 
	 */
	public int getIndexOfColumnCharacteristic(UUID columnId, String characteristic) {
		Column column = this.getColumn(columnId);
		return column.getIndexOfCharacteristic(characteristic);
	}

	/**
	 * Updates the type of the column.
	 * The type of the column (with columnId), will be changed to the new type.
	 * 
	 * @param columnId
	 *         | The columnId of the column which name will be changed.
	 * @param newType
	 *         | The new type of the column.
	 * @effect The column is updated to the new type.
	 *         | getColumn(columnId).updateType(newType)
	 * 
	 */
	public void updateColumnType(UUID columnId, ValueType newType) {
		Column column = this.getColumn(columnId);
		column.updateType(newType);
	}

	/**
	 * Updates the allowBlanks variable of the column. 
	 *
	 * @param columnId
	 *        The columnId of the column which name will be changed.
	 * @param newBool
	 *        | The new allowBlanks value of the column.
	 * @effect The column is updated to the new allow blanks variable.
	 *         | getColumn(columnId).updateAllowBlanks(newBool)
	 * 
	 */
	public void updateColumnAllowBlanks(UUID columnId, boolean newBool) {
		Column column = this.getColumn(columnId);
		column.updateAllowBlanks(newBool);
	}

	/**
	 * Updates the default value of the column. 
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @param newDefaultValue
	 *        | The new default value of the column.
	 * @effect The column is updated to the new default value.
	 *        | getColumn(columnId).updateDefaultValue(newDefaultValue)
	 */
	public void updateColumnDefaultValue(UUID columnId, Object newDefaultValue) {
		Column column = this.getColumn(columnId);
		column.updateDefaultValue(newDefaultValue);
	}

	/**
	 * Returns the valueType of the column in the table with the given tableId.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @return the valueType of the column in the table with the given tableId.
	 *        | getColumn(columnId).getType()
	 */
	public ValueType getValueTypeOfColumn(UUID columnId) {
		Column column = this.getColumn(columnId);
		return column.getType();

	}

	/**
	 * Returns if the given column with columnId allows blank values.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @return Whether or not the column in the table with the given tableId allows blank values.
	 *        | getColumn(columnId).isAllowsBlanks()
	 */
	public boolean getColumnAllowBlanks(UUID columnId) {
		Column column = this.getColumn(columnId);
		return column.isAllowsBlanks();
	}

	/**
	 * The column with the given columnId is removed from the table.
	 * And it removes the cells of the deleted column from all of the table's rows.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @post the column is removed and the cells of that column are removed in the rows.
	 */
	public void deleteColumn(UUID columnId) {
		Column column = this.getColumn(columnId);
		int columnIndex = getColumns().indexOf(column);

		if (columnIndex >= 0) {
			List<Column> currentColumns = getColumns();
			currentColumns.remove(column);
			this.setColumns(currentColumns);

			for (Row r : getRows()) {
				r.deleteCell(columnIndex);
			}
		}
	}

	/**
	 * Edits the cell in the table.
	 * Sets the value of the cell to the new value.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @param newValue
	 *        | the new value of the cell.
	 * @post The value of the cell is updated to the new value.
	 *        | getColumn(columnId).updateCellValue(cellId, newValue)
	 */
	public void editCell(UUID columnId, UUID cellId, Object newValue) {
		Column column = this.getColumn(columnId);
		column.updateCellValue(cellId, newValue);
	}

	/**
	 * Returns the id of the column, of which a cell is located.
	 * 
	 * @param cellId
	 *        | The id of the cell.
	 * @return the id of the column where the cell with the given id is located.
	 *        | for (Column col : getColumns()) 
	 *		  |   if (col.containsCell(cellId)) 
	 *		  |  	return col.getId()
	 * @throws DomainException if the table does not have a cell with the given id.
	 */
	public UUID getcolumnId(UUID cellId) {
		for (Column col : getColumns()) {
			if (col.containsCell(cellId)) {
				return col.getId();
			}
		}
		throw new DomainException("No column id found for given cellId");
	}
	
	/**
	 * Returns the id of the column, of which a cell is located.
	 * 
	 * @param cellId
	 *        | The id of the cell.
	 * @return the name of the column where the cell with the given id is located.
	 *        | for (Column col : getColumns()) 
	 *		  |   if (col.containsCell(cellId)) 
	 *		  |  	return col.getName()
	 * @throws DomainException if the table does not have a cell with the given id.
	 */
	public String getcolumnName(UUID cellId) {
		for (Column col : getColumns()) {
			if (col.containsCell(cellId)) {
				return col.getName();
			}
		}
		throw new DomainException("No column id found for given cellId");
	}


	/**
	 * Returns the index of the cell in the column.
	 * 
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @return Returns the index of the cell in the column.
	 *        | getColumn(columnId).getIndexOfCell(cellId)
	 */
	public int getIndexOfCellInColumnId(UUID columnId, UUID cellId) {
		Column column = this.getColumn(columnId);
		return column.getIndexOfCell(cellId);
	}

	/**
	 * Removes the row (with rowId).
	 * It also removes the cells of that row in the columns.
	 * 
	 * @param rowId
	 *       | The id of the row.
	 * @effect the row is removed and the cells in the row are removed.
	 */
	public void deleteRow(UUID rowId) {
		int rowIndex = getIndexOfRow(rowId);
		List<Row> rows = getRows();
		rows.remove(rowIndex);
		this.setRows(rows);

		for (Column c : getColumns()) {
			c.deleteCell(rowIndex);
		}

	}

	/**
	 * Returns the index of the row in the table.
	 * It returns -1 when the row is not in the table.
	 * 
	 * @param rowId
	 *        | the id of the row.
	 * @return -1 if the row is not found, otherwise it returns the index of the row in the table.
	 */
	public int getIndexOfRow(UUID rowId) {
		for (Row r : this.getRows()) {
			if (r.getId().equals(rowId)) {
				return this.getRows().indexOf(r);
			}
		}
		return -1;
	}

	/**
	 * Returns the row id of which a cell with cellId is located.
	 * 
	 * @param cellId
	 *        | The id of a cell.
	 * @return The id of the row of which a cell with cellId is located.
	 * @throws DomainException if there does not exists a row with the given cellId.
	 */
	public UUID getRowId(UUID cellId) {
		for (Row row : getRows()) {
			if (row.containsCell(cellId)) {
				return row.getId();
			}
		}
		throw new DomainException("No column id found for given cellId");
	}

	public int getNbrOfColumns() {
		return this.getColumns().size();
	}

	public int getColumnIndexOfName(String columnName) {
		if (columnName == null) {
			throw new DomainException("ColumnName cannot be null.");
		}
		for (int i = 0; i < this.getNbrOfColumns(); i++) {
			Column currentColumn = this.getColumns().get(i);
			if (columnName.equals(currentColumn.getName())) {
				return i;
			}
		}
		return -1;
	}

	public Table copy() {
		return new Table(getName(), getId(), getRows(), getColumns());
	}

	@Override
	public String toString() {
		String result = "";
		for (Column c : this.getColumns()) {
			result += "|\t" + c.getName().toString() + "\t";
		}
		result += "|\n";
		for (Row r : this.getRows()) {
			result += r.toString() + "\n";
		}
		return result;
	}

	public int getHeightOfColumns() {
		if (this.getColumns().size() == 0) {
			return 0;
		}
		return getColumns().get(0).getCells().size();
	}

	public Column getColumnForIndex(int i) {
		return this.getColumns().get(i);
	}

}
