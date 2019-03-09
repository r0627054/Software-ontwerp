package domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		Cell c13 = new Cell(ValueType.BOOLEAN, true);
		Cell c14 = new Cell(ValueType.BOOLEAN, true);

		Cell c21 = new Cell(ValueType.INTEGER, 10);
		Cell c22 = new Cell(ValueType.INTEGER, 20);
		Cell c23 = new Cell(ValueType.INTEGER, 30);
		Cell c24 = new Cell(ValueType.INTEGER, 40);

		Cell c1[] = { c01, c11, c21 };
		Cell c2[] = { c02, c12, c22 };
		Cell c3[] = { c03, c13, c23 };
		Cell c4[] = { c04, c14, c24 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(c1)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(c2)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(c3)));
		Row r4 = new Row(new ArrayList<Cell>(Arrays.asList(c4)));

		Cell colCells1[] = { c01, c02, c03, c04 };
		Cell colCells2[] = { c11, c12, c13, c14 };
		Cell colCells3[] = { c21, c22, c23, c24 };

		Column col1 = new Column("Name", ValueType.STRING, false);
		Column col2 = new Column("Nerd", ValueType.BOOLEAN, false);
		Column col3 = new Column("Age", ValueType.INTEGER, true);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));

		Table persons = new Table("Persons");
		Table table2 = new Table("Second");
		Table table3 = new Table("Third");
		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);

		/*
		 * this.tableMap.put(persons.getName(), persons);
		 * this.tableMap.put(table2.getName(), table2);
		 * this.tableMap.put(table3.getName(), table3);
		 */

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
	public Table getTable(UUID id) {
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
	public Map<Map<UUID, String>, Map<UUID, Object>> getTableWithIds(UUID id) {
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
		while(!newIndexIsFound) {
			tableName = "Table" + i++;
			
			if(!this.tableNameAlreadyExists(tableName)) {
				newIndexIsFound = true;
			}
		}
		System.out.println(tableName);
		this.addTable(tableName);
	}

	@Override
	public void deleteTable(UUID id) {
		if(id == null) {
			throw new DomainException("Cannot delete a table with a null index");
		}
		this.getTableMap().remove(id);
	}
	
}
