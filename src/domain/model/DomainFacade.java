package domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.sql.Query;

/**
 * The actual implementation of the domainFacadeInterface.
 * This handles all the controls defined in the domainFacadeInterface.
 * It makes use of an in-memory map of tables.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class DomainFacade implements DomainFacadeInterface {

	/**
	 * Map variable storing all the tables with their corresponding Id.
	 */
	private Map<UUID, Table> tableMap = new HashMap<>();

	/**
	 * Initialises a new DomainFacade.
	 */
	public DomainFacade() {
		addDummyTable("TabelDummy1");
		addDummyTable("TabelDummy2");
		addDummyTable("TabelDummy3");
	}

	/**
	 * This method is used during development of the project.
	 * Creates a dummyTable with dummy data.
	 * @param tableName
	 *        | the name of the table.
	 */
	public void addDummyTable(String tableName) {
		DomainCell c01 = new DomainCell(ValueType.STRING, "Steven");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Mauro");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Laurens");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 10);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 30);
		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);

		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("S@"));
		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("D@"));
		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("L@"));

		DomainCell c1[] = { c01, c11, c21, c31 };
		DomainCell c2[] = { c02, c12, c22, c32 };
		DomainCell c3[] = { c03, c13, c23, c33 };
		DomainCell c4[] = { c04, c14, c24, c34 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));

		DomainCell colCells1[] = { c01, c02, c03, c04 };
		DomainCell colCells2[] = { c11, c12, c13, c14 };
		DomainCell colCells3[] = { c21, c22, c23, c24 };
		DomainCell colCells4[] = { c31, c32, c33, c34 };

		Column col1 = new Column("Name", ValueType.STRING);
		Column col2 = new Column("Student", ValueType.BOOLEAN);
		Column col3 = new Column("Age", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table(tableName);

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);

		this.tableMap.put(persons.getId(), persons);
	}

	/**
	 * Adds a fully created table to the list of tables.
	 * This method is used for adding mock tables.
	 * No other higher level classes should know about actual domain tables.
	 * 
	 * @param t The table which will be added
	 * @effect The table is added to the map of tables.
	 *         | this.tableMap.put(t.getId(), t)
	 */
	public void addMockedTable(Table t) {
		this.tableMap.put(t.getId(), t);
	}

	/**
	 * Returns the table corresponding to the tableId in the tableMap.
	 * 
	 * @param tableName
	 *        | the name of table
	 * @return the table corresponding to the tableId in the tableMap.
	 **/
	private Table getTable(UUID id) {
		return this.tableMap.get(id);
	}

	private Table getTableOfTableName(String tableName) {
		for (Table t : getTableMap().values()) {
			if (t.getName().equals(tableName)) {
				return t;
			}
		}
		throw new DomainException("No table is found for certain tableName");
	}

	/**
	 * Returns a map of all the table names.
	 * The key is the UUID of the table and the value is the name of table.
	 */
	public Map<UUID, String> getTableNames() {
		Map<UUID, String> map = new HashMap<>();
		for (Table table : getTableMap().values()) {
			map.put(table.getId(), table.getName());
		}
		return map;
	}

	/**
	 * Creates a new table with the given name.
	 * It adds the table to the table map.
	 * 
	 * @param name
	 *        The name the new table will have.
	 */
	public void addTable(String name) {
		Table table = new Table(name);
		this.tableMap.put(table.getId(), table);
	}

	/**
	 * Returns the full in-memory map.
	 * The map contains all the tableId's with the corresponding table.
	 * 
	 * @return the full in-memory map.
	 */
	public Map<UUID, Table> getTableMap() {
		return tableMap;
	}

	/**
	 * Updates the name of the table, of that given id, with the given name.
	 * 
	 * @param id
	 *        The id of the table.
	 * @param newName
	 *        The new name of the table.
	 * @throws DomainException if the new name equals null or when it is empty.
	 *        | newName == null || newName.isEmpty()
	 * @throws DomainException if another table already has the same name.
	 *        | tableAlreadyExists(id, newName)
	 */
	@Override
	public void updateTableName(UUID id, String newName) {
		if (newName == null || newName.isEmpty() || tableAlreadyExists(id, newName)) {
			throw new DomainException("Table name already exists in another table or is empty.");
		}
		this.getTable(id).setName(newName);
	}

	/**
	 * Returns whether or not another table already uses the name for its table name.
	 * 	 
	 * @param id
	 *        The id of the table which wants to set the name.
	 * @param name
	 *        The new name of the table.
	 * @return true if another table already already has that table name, otherwise false.
	 */
	private boolean tableAlreadyExists(UUID id, String name) {
		boolean exists = false;
		for (Map.Entry<UUID, Table> entry : getTableMap().entrySet()) {
			if (!(entry.getKey().equals(id)) && entry.getValue().getName().equals(name)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	/**
	 * Checks whether a table already has the given name as table name.
	 * @param name
	 *        | The name for a table.
	 * @return True if some table already has that name, otherwise false.
	 */
	private boolean tableNameAlreadyExists(String name) {
		boolean exists = false;
		for (Table t : getTableMap().values()) {
			if (t.getName().equals(name)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

	/**
	 * Returns a map with all the table information of the given tableId.
	 * The first list: ColumnID, ColumnName and the Class of this column.
	 * The second inner map, the key: UUID of cell and the value: the value of the cell in the column.
	 * 
	 * @param tableId
	 *        The tableId of which the information should be gathered.
	 * @return a map with all the information associated with the given tableId.
	 */
	@Override
	public Map<List<Object>, LinkedHashMap<UUID, Object>> getTableWithIds(UUID id) {
		return this.getTable(id).getTableWithIds();
	}

	/**
	 * Returns the name of the table with the given id.
	 * 
	 * @param id
	 *        The id of the table.
	 * @return The name of the table with the given id.
	 * @throws DomainException if id is null.
	 *        | id == null
	 * @throws DomainException if no table is found for given id.
	 *        | getTable(id) == null
	 */
	@Override
	public String getTableNameOfId(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot get table name of a null id");
		}
		Table t = getTable(id);
		if (t != null) {
			return t.getName();
		}
		throw new DomainException("Cannot find table for id");
	}

	/**
	 * Creates a new Table with a unique name.
	 * Its name is tableN, where N is a number, and it is different from the names of the existing tables.
	 * The new table initially has no columns and no rows.
	 */
	@Override
	public void createNewTable() {
		int i = 1;
		boolean newIndexIsFound = false;
		String tableName = null;
		while (!newIndexIsFound) {
			tableName = "Table" + i++;

			if (!this.tableNameAlreadyExists(tableName)) {
				newIndexIsFound = true;
			}
		}
		this.addTable(tableName);
	}

	/**
	 * Deletes the table of the given id.
	 * @param id
	 *        The id of the table which will be deleted.
	 * @throws DomainException when the id equals null.
	 *         | id == null
	 */
	@Override
	public void deleteTable(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot delete a table with a null index");
		}
		this.getTableMap().remove(id);
	}

	/**
	 * Returns map where the key is the id of the column, and the value is a linkedHashList of characteristics.
	 * (the characteristic values are: column name, type, allow Blanks, Default Value)
	 * 
	 * @param id
	 *        The id of the table.
	 * @return a map where the key is the id of the column, and the value is a linkedHashList of characteristics. (column name, type, allow Blanks, Default Value)
	 * @throws DomainException when the id equals null
	 *         | id == null
	 * @throws DomainException when the there does not exist a table with the given id.
	 *         | getTable(id) == null
	 */
	@Override
	public Map<UUID, LinkedHashMap<String, Object>> getColumnCharacteristics(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot get column characteristiscs with a null id");
		}
		Table table = this.getTable(id);

		if (table == null) {
			throw new DomainException("No table could be found with given id.");
		}

		return table.getColumnCharacteristics();
	}

	/**
	 * Creates a new column in the table with the given table id.
	 * The name of the column is ColumnN, where N is a number, and it is different from the names of the existing columns.
	 * All existing rows of the table get a blank value as the value for the new column.
	 * 
	 * @param id
	 *        The id of the table where the column will be created.
	 * @throws DomainException when the id equals null
	 *         | id == null
	 */
	@Override
	public void addColumnToTable(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot add a column to a table with a null id.");
		}
		Table table = getTable(id);
		if (table != null) {
			table.createNewColumn();
		}
	}

	/**
	 * Updates the name of the column.
	 * The name of the column (with columnId) in the table (with tableId), will be changed to the new name.
	 * 
	 * @param tableId
	 *        The tableId of the table where the column is situated.
	 * @param columnId
	 *        The columnId of the column which name will be changed.
	 * @param newName
	 *        The new name of the column.
	 * @throws DomainException when the tableId or columnId or newName equals null. Or when the newName is empty.
	 *        | tableId ==null || columnId == null || newName == null || newName.isEmpty()
	 */
	@Override
	public void updateColumnName(UUID tableId, UUID columnId, String newName) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot update a column name with a null id.");
		} else if (newName == null || newName.isEmpty()) {
			throw new DomainException("Cannot set a new column name with a null or empty name.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.updateColumnName(columnId, newName);
		}
	}

	/**
	 * Returns the index of the characteristic in the given column with column Id.
	 * Returns -1 if the characteristic cannot be found in the column.
	 * 
	 * @param tableId 
	 *        The id of the table.
	 * @param columnId
	 *        The id of the column in the table.
	 * @param characteristic
	 *        The characteristic of the column.
	 * @return The index of the characteristic in the column.
	 *         When the characteristic cannot be found, -1 is returned.
	 */
	public int getIndexOfColumnCharacteristic(UUID tableId, UUID columnId, String characteristic) {
		Table table = getTable(tableId);
		return table.getIndexOfColumnCharacteristic(columnId, characteristic);
	}

	/**
	 * Updates the type of the column in the table with the given tableId. 
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param newType
	 *        | The new type of the column.
	 * @throws DomainException when the tableId or columnId or newType equals null
	 *         | tableId ==null || columnId == null || newType == null
	 */
	@Override
	public void setColumnType(UUID tableId, UUID columnId, ValueType newType) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot update a column type with a null id.");
		} else if (newType == null) {
			throw new DomainException("Cannot set a new column type with a null type.");
		}

		Table table = getTable(tableId);
		if (table != null) {
			table.updateColumnType(columnId, newType);
		}
	}

	/**
	 * Updates the allowBlanks variable of the column in the table with the given tableId. 
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param newBool
	 *        | The new allowBlanks value of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 */
	@Override
	public void setAllowBlanks(UUID tableId, UUID columnId, boolean newBool) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot update a column type with a null id.");
		}

		Table table = getTable(tableId);
		if (table != null) {
			table.updateColumnAllowBlanks(columnId, newBool);
		}

	}

	/**
	 * Updates the default value of the column in the table with the given tableId. 
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param newDefaultValue
	 *        | The new default value of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 */
	@Override
	public void setColumnDefaultValue(UUID tableId, UUID columnId, Object newDefaultValue) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot update a column default value with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.updateColumnDefaultValue(columnId, newDefaultValue);
		}

	}

	/**
	 * Returns the valueType of the column in the table with the given tableId.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 * @return the valueType of the column in the table with the given tableId.
	 */
	@Override
	public ValueType getValueTypeOfColumn(UUID tableId, UUID columnId) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot get a column type with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			return table.getValueTypeOfColumn(columnId);
		}
		return null;
	}

	/**
	 * Returns whether or not the column in the table with the given tableId allows blank values.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 * @return Whether or not the column in the table with the given tableId allows blank values.
	 */
	@Override
	public boolean getColumnAllowBlanks(UUID tableId, UUID columnId) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot get a column type with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			return table.getColumnAllowBlanks(columnId);
		} else {
			throw new DomainException("No table found to return the allowBlanks value.");
		}
	}

	/**
	 * The column with the given columnId is removed from the table with the given tableId.
	 * And it removes the cells of the deleted column from all of the table's rows.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @throws DomainException when the tableId or columnId  equals null
	 *        | tableId ==null || columnId == null
	 */
	@Override
	public void deleteColumn(UUID tableId, UUID columnId) {
		if (columnId == null || tableId == null) {
			throw new DomainException("Cannot delete a column type with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.deleteColumn(columnId);
		} else {
			throw new DomainException("No table found to return the allowBlanks value.");
		}
	}

	/**
	 * Creates a new row in the table with the given tableId.
	 * It's value for each column is the column's default value.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @throws DomainException when the tableId equals null
	 *        | tableId ==null
	 */
	@Override
	public void createNewRow(UUID tableId) {
		if (tableId == null) {
			throw new DomainException("Cannot add a row on a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.addRow();
		} else {
			throw new DomainException("No table found to return the allowBlanks value.");
		}
	}

	/**
	 * Edits the cell in the table.
	 *  
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @param newValue
	 *        | the new value of the cell.
	 * @throws DomainException when the tableId or columnId or cellId equals null
	 *        | tableId ==null || columnId == null || cellId == null
	 */
	@Override
	public void editCellInTable(UUID tableId, UUID columnId, UUID cellId, Object newValue) {
		if (tableId == null || columnId == null || cellId == null) {
			throw new DomainException("Cannot edit a cell on a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.editCell(columnId, cellId, newValue);
		} else {
			throw new DomainException("No table found to edit the cell.");
		}
	}

	/**
	 * Returns the columnId of the cell (with cellId) in the table (with tableId).
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param cellId
	 *        | The id of the cell.
	 * @return the columnId of the cell (with cellId) in the table (with tableId).
	 * @throws DomainException when the tableId or cellId equals null
	 *        | tableId ==null || cellId == null
	 */
	@Override
	public UUID getColumnId(UUID tableId, UUID cellId) {
		if (tableId == null || cellId == null) {
			throw new DomainException("Cannot get a column of a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			return table.getcolumnId(cellId);
		} else {
			throw new DomainException("No table found to return the column.");
		}
	}

	/**
	 * Returns the index of the cell in the column.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @return Returns the index of the cell in the column.
	 * @throws DomainException when the tableId or columnId or cellId or table equals null
	 *        | tableId ==null || table == null || cellId == null || columnId == null
	 */
	@Override
	public int getIndexOfCellInColumnId(UUID tableId, UUID columnId, UUID cellId) {
		if (tableId == null || columnId == null || cellId == null) {
			throw new DomainException("Cannot get column index of a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			return table.getIndexOfCellInColumnId(columnId, cellId);
		} else {
			throw new DomainException("No table found to return the column index.");
		}
	}

	/**
	 * Removes the row (with rowId) in the table (with table id).
	 * It also removes the cells of that row in the columns.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param rowId
	 *        | The id of the row.
	 * @throws DomainException when the tableId or rowId or table equals null
	 *        | tableId ==null || table == null || rowId == null
	 */
	@Override
	public void deleteRow(UUID tableId, UUID rowId) {
		if (rowId == null || tableId == null) {
			throw new DomainException("Cannot delete a row with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.deleteRow(rowId);
		} else {
			throw new DomainException("No table found to delete a row.");
		}
	}

	/**
	 * Returns the id of the row which has a cell at the first position with the given cellId.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param cellIdOfFirstElement
	 *        | The id of the first cell in a row in the table.
	 * @return the id of the row which has a cell at the first position with the given cellId.
	 * @throws DomainException when the tableId or cellIdOfFirstElement or table equals null
	 *        | tableId ==null || table == null || cellIdOfFirstElement == null
	 */
	@Override
	public UUID getRowId(UUID tableId, UUID cellIdOfFirstElement) {
		if (tableId == null || cellIdOfFirstElement == null) {
			throw new DomainException("Cannot get a column of a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			return table.getRowId(cellIdOfFirstElement);
		} else {
			throw new DomainException("No table found to return the column.");
		}
	}

	/**
	 * Resets the table to an empty hashMap.
	 * @post The tableMap is set to a new hashMap.
	 *       | this.getTableMap() == new HashMap<>();
	 */
	public void resetTables() {
		this.tableMap = new HashMap<>();
	}

	/**
	 * Checks if table with certain id is empty.
	 * 
	 * @param tableId
	 * 		| The id of the table to check.
	 * @return the truth value of the table with an id
	 */
	@Override
	public boolean isTableWithIdEmpty(UUID tableId) {
		return this.getTableWithIds(tableId).isEmpty();
	}

	public void createComputedTable(UUID tableId, String query) {

		Query newQuery = new Query(query);
		List<Table> tables = new ArrayList<>();

		for (String tableName : newQuery.getRealTableNames()) {

			if (tableName.equals(getTableNameOfId(tableId))) {
				throw new DomainException("Computed table cannot refer to itself.");
			}

			tables.add(getTableOfTableName(tableName));
		}

		ComputedTable newTable = new ComputedTable(getTableNameOfId(tableId), newQuery, tables);
		
		this.deleteTable(tableId);
//		return newTable.getData();
	}

	public void updateComputedTable(UUID tableId, String query) {

	}
}
