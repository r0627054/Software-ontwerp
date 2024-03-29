package domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.sql.CellId;
import domain.model.sql.Query;
import domain.model.sql.SQLParser;
import domain.model.sql.columnSpec.ColumnSpec;

/**
 * The actual implementation of the domainFacadeInterface.
 * This handles all the controls defined in the domainFacadeInterface.
 * It makes use of an in-memory map of tables.
 * 
 * @version 3.0
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
//		addMockedTable(dummyTable1());
//		addMockedTable(dummyTable2());
//		addMockedTable(dummyTable3());
	}

	/**
	 * This method is used during development of the project.
	 * Creates a dummyTable with dummy data.
	 * @return A dummy table, with exampled data.
	 */
	public static Table dummyTable1() {
		DomainCell c01 = new DomainCell(ValueType.STRING, "Steven");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Mauro");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Laurens");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 5);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 15);
		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);

		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
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
		Column col3 = new Column("Grade", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Students");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRowWithoutAddingToColumns(r1);
		persons.addRowWithoutAddingToColumns(r2);
		persons.addRowWithoutAddingToColumns(r3);
		persons.addRowWithoutAddingToColumns(r4);
		return persons;
	}

	/**
	 * This method is used during development of the project.
	 * Creates a dummyTable with dummy data.
	 * @return A dummy table, with exampled data.
	 */
	public static Table dummyTable2() {
		DomainCell c01 = new DomainCell(ValueType.STRING, "John");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Quinten");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Frederik");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 20);
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

		Column col1 = new Column("Firstname", ValueType.STRING);
		Column col2 = new Column("Smart", ValueType.BOOLEAN);
		Column col3 = new Column("Age", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Work");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRowWithoutAddingToColumns(r1);
		persons.addRowWithoutAddingToColumns(r2);
		persons.addRowWithoutAddingToColumns(r3);
		persons.addRowWithoutAddingToColumns(r4);
		return persons;
	}

	/**
	 * This method is used during development of the project.
	 * Creates a dummyTable with dummy data.
	 * @return A dummy table, with exampled data.
	 */
	public static Table dummyTable3() {
		DomainCell c01 = new DomainCell(ValueType.STRING, "Dirk");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Rita");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Jos");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Filip");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, false);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);

		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@R.be"));
		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("Info@J.be"));
		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("Info@F.be"));

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

		Column col1 = new Column("Nickname", ValueType.STRING);
		Column col2 = new Column("Paid", ValueType.BOOLEAN);
		Column col3 = new Column("Money", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Group");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRowWithoutAddingToColumns(r1);
		persons.addRowWithoutAddingToColumns(r2);
		persons.addRowWithoutAddingToColumns(r3);
		persons.addRowWithoutAddingToColumns(r4);
		return persons;
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

	/**
	 * Returns the table with the given name.
	 * @param tableName The name of the table.
	 * @return The table with the given name.
	 * @throws DomainException when there is no table with the given name.
	 * @effect The table with the given name is returned.
	 *         | for (Table t : getTableMap().values()) 
	 *         | 	if (t.getName().equals(tableName)) 
	 *         |    	return t;
	 */
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
	 * @return a map of all the table names.
	 */
	public Map<UUID, List<String>> getTableNames() {
		Map<UUID, List<String>> map = new HashMap<>();
		for (Table table : getTableMap().values()) {
			List<String> tableNameAndQueryList = new ArrayList<>();
			tableNameAndQueryList.add(table.getName());
			if (table instanceof ComputedTable) {
				tableNameAndQueryList.add(((ComputedTable) table).getQuery().toString());
			}

			map.put(table.getId(), tableNameAndQueryList);
		}
		return map;
	}

	/**
	 * Creates a new table with the given name.
	 * It adds the table to the table map.
	 * 
	 * @param name
	 *        The name the new table will have.
	 * @effect a table is created with a given name and added to the list.
	 *        | Table table = new Table(name);
	 *        | this.tableMap.put(table.getId(), table);
	 */
	public void addTable(String name) {
		Table table = new Table(name);
		this.tableMap.put(table.getId(), table);
	}

	/**
	 * Creates a new table with the given name and id.
	 * It adds the table to the table map.
	 * 
	 * @param id
	 *        The id the new table should have.
	 * @param name
	 *        The name the new table will have.
	 * @effect a table is created with a given name and added to the list.
	 *        | Table table = new Table(id, name);
	 *        | this.tableMap.put(id, table);
	 */
	private void addTable(UUID id, String name) {
		Table table = new Table(id, name);
		this.tableMap.put(id, table);
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
		if (checkComputedTableUsesTableId(id) && !getTableNameOfId(id).equals(newName)) {
			throw new DomainException("A computed table uses this table");
		}
		this.getTable(id).setName(newName);
	}

	/**
	 * Checks whether or whether there is computed table which uses a table with the given id.
	 * @param id The id of the table, which need to be checked if the table of that id is used somewhere.
	 * @return True When there is a table which uses that table with the given id.
	 */
	private boolean checkComputedTableUsesTableId(UUID id) {
		for (Table t : getTableMap().values()) {
			if (t instanceof ComputedTable) {
				for (Table usedTables : ((ComputedTable) t).getQueryTables()) {
					if (usedTables.getId().equals(id)) {
						return true;
					}
				}
			}
		}
		return false;
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
	public Map<List<Object>, List<Object[]>> getTableWithIds(UUID id) {
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
	 * @post the table of the id is deleted and all the tables having a reference are also deleted.
	 */
	@Override
	public void deleteTable(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot delete a table with a null index");
		}
		Table table = getTable(id);
		List<Table> allRemovedList = new ArrayList<>();
		List<Table> currentIterationList = new ArrayList<>();
		currentIterationList.add(table);
		allRemovedList.add(table);
		while (currentIterationList.size() != 0) {
			List<Table> tempRemovedList = new ArrayList<>();

			for (Table t : getTableMap().values()) {
				if (t instanceof ComputedTable && !allRemovedList.contains(t)) {
					List<Table> iterationAllRemoved = new ArrayList<>();
					for (Table removedTable : allRemovedList) {
						if (((ComputedTable) t).containsMatchingTable(removedTable.getName())) {
							iterationAllRemoved.add(t);
							tempRemovedList.add(t);
						}
					}
					allRemovedList.addAll(iterationAllRemoved);
				}
			}
			currentIterationList = tempRemovedList;
		}

		for (Table t : allRemovedList) {
			this.getTableMap().remove(t.getId());
		}
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
	 * @effect a new column is added to the table with given id.
	 *         | Table table = getTable(id);
	 *         | 	if (table != null) {
	 *         | 		table.createNewColumn();
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
		} else if (columnNameIsBeingUsedByComputedTable(getTable(tableId).getColumnNameOfColumnId(columnId),
				getTableNameOfId(tableId)) && !getTable(tableId).getColumnNameOfColumnId(columnId).equals(newName)) {
			throw new DomainException("Cannot change a column which is being used by a computed table");
		}
		Table table = getTable(tableId);
		if (table != null) {
			table.updateColumnName(columnId, newName);
		}
	}

	/**
	 * Checks whether a columnName of a table is used in a computed table with the given name.
	 * @param oldColName The column name which needs to be checked.
	 * @param tableName  The name of the table in which the column name needs to be checked.
	 * @return True when there exists a column name in the table with the given name.
	 */
	private boolean columnNameIsBeingUsedByComputedTable(String oldColName, String tableName) {
		for (Table t : getTableMap().values()) {
			if (t instanceof ComputedTable) {
				ComputedTable comp = (ComputedTable) t;
				Map<String, String> namesMap = comp.getQuery().getDisplayToRealNamesMap();

				for (CellId id : comp.getQuery().getAllCellIds()) {
					if (namesMap.get(id.getTableId()).equals(tableName) && id.getColumnName().equals(oldColName)) {
						return true;
					}
				}

			}
		}
		return false;
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
		String col = table.getColumnNameOfColumnId(columnId);
		List<Table> deleteTables = new ArrayList<>();
		if (table != null) {
			if (!(table instanceof ComputedTable)) {

				for (Table t : getTableMap().values()) {
					if (t instanceof ComputedTable
							&& ((ComputedTable) t).containsMatchingColumn(col, table.getName())) {
						deleteTables.add(t);
					}
				}
				for (Table t : deleteTables) {
					this.deleteTable(t.getId());
				}
				table.deleteColumn(columnId);
			} else {
				throw new DomainException("You should not be able to delete columns from a computedTable");
			}
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
		List<Table> usedTables = new ArrayList<>();
		if (table != null) {
			usedTables.add(table);

			if (!(table instanceof ComputedTable)) {
				for (Table t : this.getTableMap().values()) {
					if ((t instanceof ComputedTable) && (((ComputedTable) t).containsMatchingTable(table.getName()))) {
						((ComputedTable) t).updateCellWithComputation(columnId,
								table.getIndexOfCellInColumnId(columnId, cellId), newValue);
						usedTables.add(t);
					}
				}
			}
			table.editCell(columnId, cellId, newValue);

			for (Table t : usedTables) {
				if (t instanceof ComputedTable) {
					((ComputedTable) t).runQuery();
				}
			}
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
			if (!(table instanceof ComputedTable)) {
				table.deleteRow(rowId);
				for (Table t : getTableMap().values()) {
					if (t instanceof ComputedTable && ((ComputedTable) t).containsMatchingTable(table.getName())) {
						((ComputedTable) t).runQuery();
					}
				}
			} else {
				throw new DomainException("You should not be able to delete a row of a computed table");
			}
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

	/**
	 * Creates a computed table with the given id.
	 * The query is created out of the String, it is checked and given to the computed table.
	 * @param tableId The id which the computed table should have.
	 * @param query   The query string which belongs to the computed table.
	 */
	@Override
	public void createComputedTable(UUID tableId, String query) {
		String parsedQuery = SQLParser.parseQuery(query);
		SQLParser parser = new SQLParser(parsedQuery);
		Query newQuery = parser.getQueryFromString();

		if (getTable(tableId) instanceof ComputedTable) {
			ComputedTable comp = (ComputedTable) getTable(tableId);

			for (Table t : getTableMap().values()) {
				if (t instanceof ComputedTable && !t.getId().equals(comp.getId())) {
					ComputedTable compTemp = (ComputedTable) t;

					if (compTemp.getQuery().usesTable(comp.getName())) {
						List<String> columnList = compTemp.getColumnsNamesUsed(comp.getName());

						List<String> newColumnNames = newQuery.getAllSelectColumnNames();
						for (String columnName : columnList) {
							if (!newColumnNames.contains(columnName)) {
								throw new DomainException(
										"Cannot change the display name of a column that is being used by another table");
							}
						}
					}
				}
			}
		}

		List<Table> tables = new ArrayList<>();
		String oldTableName = getTableNameOfId(tableId);

		for (String tableName : newQuery.getAllUsedTables()) {

			// A query cannot contain a reference to itself, if so throw error
			if (tableName.equals(getTableNameOfId(tableId))) {
				throw new DomainException("Computed table cannot refer to itself.");
			}

			// If no table found, throw error (inside function)
			Table table = getTableOfTableName(tableName);

			// 2 computed tables cannot point to each other
			if (table instanceof ComputedTable && ((ComputedTable) table).containsMatchingTable(oldTableName)) {
				throw new DomainException(
						"A new computed table cannot create a table with a reference to another computed table.");
			}

			tables.add(table);
		}

		ComputedTable newTable = new ComputedTable(tableId, oldTableName, newQuery, tables);
		this.deleteTable(tableId);
		this.getTableMap().put(tableId, newTable);
	}

	/**
	 * Checks whether the table with the given id a computed table or not.
	 * @param tableId The id of the table which needs to be checked.
	 * @return True when the table with the id exits and is a computed table, otherwise false.
	 */
	@Override
	public boolean isComputedTable(UUID tableId) {
		return getTable(tableId) instanceof ComputedTable;
	}

	/**
	 * Gives all the ids of the tables which uses the table with the given cellid.
	 * @param tableId The table id which the cellId is stored in.
	 * @param cellId  The cellId which is shown in different tables.
	 * @return The list of UUIDs of tables which uses the cellid.
	 */
	@Override
	public List<UUID> getTableIdOfUsedTables(UUID tableId, UUID cellId) {
		List<UUID> result = new ArrayList<>();
		Table table = getTable(tableId);

		if (table != null) {
			if (table instanceof ComputedTable) {

				String columnNameOfEditedCell = table.getColumnName(cellId);

				ComputedTable comp = (ComputedTable) table;
				Query query = comp.getQuery();

				List<CellId> ids = query.getCellIdOfColumnName(columnNameOfEditedCell);
				CellId id = ids.get(0);
				for (CellId tempId : ids) {
					if (!id.equals(tempId)) {
						throw new DomainException(
								"Cannot edit a column of a computed table which uses more than one other column");
					}
				}

				String referencedTable = query.getDisplayToRealNamesMap().get(id.getTableId());
				result.add(getTableOfTableName(referencedTable).getId());
			} else {
				for (Table t : getTableMap().values()) {
					if (t instanceof ComputedTable) {
						ComputedTable comp = (ComputedTable) t;
						Query query = comp.getQuery();

						if (query.usesTable(this.getTableNameOfId(tableId))) {
							result.add(t.getId());
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Converts the computed table back to a stored table, by setting the query to an empty query.
	 * @param tableId The tableId which needs to be converted/ Sets to a blank query.
	 */
	@Override
	public void setEmptyQuery(UUID tableId) {
		if (tableId == null) {
			throw new DomainException("Cannot get a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table instanceof ComputedTable) {
			this.deleteTable(tableId);
			this.addTable(table.getId(), table.getName());
		}
	}

}
