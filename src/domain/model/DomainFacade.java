package domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DomainFacade implements DomainFacadeInterface {

	private static DomainFacade dfInstance = null;
	// private Map<String, Table> tableMap = new HashMap<>();
	private Map<UUID, Table> tableMap = new HashMap<>();

	private DomainFacade() {
		addDummyTable();
	}

	private void addDummyTable() {
		List<Cell> cellList = new ArrayList<>();

		Cell c01 = new Cell(ValueType.STRING, "Steven");
		Cell c02 = new Cell(ValueType.STRING, "Mauro");
		Cell c03 = new Cell(ValueType.STRING, "Dries");
		Cell c04 = new Cell(ValueType.STRING, "Laurens");

		Cell c11 = new Cell(ValueType.BOOLEAN, true);
		Cell c12 = new Cell(ValueType.BOOLEAN, true);
		Cell c13 = new Cell(ValueType.BOOLEAN, false);
		Cell c14 = new Cell(ValueType.BOOLEAN, false);

		Cell c21 = new Cell(ValueType.INTEGER, 10);
		Cell c22 = new Cell(ValueType.INTEGER, 20);
		Cell c23 = new Cell(ValueType.INTEGER, 30);
		Cell c24 = new Cell(ValueType.INTEGER, 40);

		Cell c31 = new Cell(ValueType.EMAIL, null);
		Cell c32 = new Cell(ValueType.EMAIL, "M@");
		Cell c33 = new Cell(ValueType.EMAIL, "D@");
		Cell c34 = new Cell(ValueType.EMAIL, "L@");

		Cell c1[] = { c01, c11, c21, c31 };
		Cell c2[] = { c02, c12, c22, c32 };
		Cell c3[] = { c03, c13, c23, c33 };
		Cell c4[] = { c04, c14, c24, c34 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(c1)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(c2)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(c3)));
		Row r4 = new Row(new ArrayList<Cell>(Arrays.asList(c4)));

		Cell colCells1[] = { c01, c02, c03, c04 };
		Cell colCells2[] = { c11, c12, c13, c14 };
		Cell colCells3[] = { c21, c22, c23, c24 };
		Cell colCells4[] = { c31, c32, c33, c34 };

		Column col1 = new Column("Name", ValueType.STRING, false);
		Column col2 = new Column("Baas", ValueType.BOOLEAN, true);
		Column col3 = new Column("Age", ValueType.INTEGER, true);
		Column col4 = new Column("Email", ValueType.EMAIL, true);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Persons");
		Table table2 = new Table("Second");
		Table table3 = new Table("Third");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);

		this.tableMap.put(persons.getId(), persons);
		this.tableMap.put(table2.getId(), table2);
		this.tableMap.put(table3.getId(), table3);
	}

	/**
	* Creates an domainFacade instance only once. 
	* Returns the only existing instance.
	*
	* @post dfInstance is instantiated
	* | new.getInstance == domainFacadeInstance
	*
	* @notes
	* synchronized makes sure that every thread is synchronized and
	* prevents creating another instance in a other thread.
	**/
	public static synchronized DomainFacade getInstance() {
		if (dfInstance == null)
			dfInstance = new DomainFacade();
		return dfInstance;
	}

	/**
	 * @param tableName
	 * name of table
	 * 
	 * @return
	 * the table corresponding to the tableName in the tableMap.
	 **/
	private Table getTable(UUID id) {
		return this.tableMap.get(id);
	}

	public Map<UUID, String> getTableNames() {
		Map<UUID, String> map = new HashMap<>();
		for (Table table : getTableMap().values()) {
			map.put(table.getId(), table.getName());
		}
		return map;
	}

	public void addTable(String name) {
		Table table = new Table(name);
		this.tableMap.put(table.getId(), table);
	}

	public Map<UUID, Table> getTableMap() {
		return tableMap;
	}

	@Override
	public void updateTableName(UUID id, String newName) {
		if (newName == null || newName.isEmpty() || tableAlreadyExists(id, newName)) {
			throw new DomainException("Table name already exists in another table or is empty.");
		}
		this.getTable(id).setName(newName);
	}

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
	 * The first inner map, the key: UUID of the column and value: columnname
	 * The second inner map, the key: UUID of cell and the value: the value of the cell in the column
	 */
	@Override
	public Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> getTableWithIds(UUID id) {
		return this.getTable(id).getTableWithIds();
	}

	@Override
	public String getTableNameOfId(UUID id) {
		return this.getTable(id).getName();
	}

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
		// System.out.println(tableName);
		this.addTable(tableName);
	}

	@Override
	public void deleteTable(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot delete a table with a null index");
		}
		this.getTableMap().remove(id);
	}

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

	@Override
	public void addColumnToTable(UUID id) {
		if (id == null) {
			throw new DomainException("Cannot add a column to a table with a null id.");
		}
		this.getTable(id).createNewColumn();
	}

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

	public int getIndexOfColumnCharacteristic(UUID tableId, UUID columnId, String characteristic) {
		Table table = getTable(tableId);
		return table.getIndexOfColumnCharacteristic(columnId, characteristic);
	}

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

	@Override
	public Map<UUID, Class> getColumnTypes(UUID tableId) {
		if (tableId == null) {
			throw new DomainException("Cannot get column types of a table with a null id.");
		}
		Table table = getTable(tableId);
		if (table != null) {
			return table.getColumnTypes();
		} else {
			throw new DomainException("No table found to return the column types.");
		}
	}

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
}
