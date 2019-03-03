package domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainFacade implements DomainFacadeInterface {

	private static DomainFacade dfInstance = null;
	private Map<String, Table> tableMap = new HashMap<>();

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

		Row r1 = new Row((ArrayList<Cell>) Arrays.asList(c1));
		Row r2 = new Row((ArrayList<Cell>) Arrays.asList(c2));
		Row r3 = new Row((ArrayList<Cell>) Arrays.asList(c3));
		Row r4 = new Row((ArrayList<Cell>) Arrays.asList(c4));

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
		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);

		this.tableMap.put(persons.getName(), persons);
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
	public Table getTable(String tableName) {
		return this.tableMap.get(tableName);
	}

	public List<String> getTableNames() {
		return new ArrayList<String>(this.tableMap.keySet());
	}
	
	public void addTable(String name) {
		this.tableMap.put(name, new Table(name));
	}

}
