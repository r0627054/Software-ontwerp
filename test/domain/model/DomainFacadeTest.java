package domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class DomainFacadeTest {
	private DomainFacade df;
	private DomainFacade correctDf = new DomainFacade();

	/**
	 * Test 1 : Creates instance of domainfacade if not already exists
	 * | should initialise the df variable
	 */
	@Test
	void test1CreateDomainFacadeInstance() {
		df = new DomainFacade();
		assertNotNull(df);
	}

	/**
	 * Test 2 : Create instance of domainfacade when its already instantiated
	 * | should do nothing
	 */
	/*
	 * @Test void test2CreateDomainFacadeInstanceTwice() { df = new DomainFacade();
	 * int hashBefore = df.hashCode(); df = DomainFacade.getInstance();
	 * assertEquals(hashBefore,df.hashCode()); }
	 */

	/**
	 * Test 3 : Add dummy table to domainfacade
	 * | should instantiate tablemMap
	 */
	@Test
	void test3AddDummyTableWithComponents() {
		String tableName = "testTable";
		df = new DomainFacade();
		df.addTable(tableName);

		boolean contains = false;

		for (List<String> list : df.getTableNames().values()) {
			if (list.contains(tableName)) {
				contains = true;
			}
		}

		assertTrue(contains);
	}

	/**
	 * Test 4 : Add mocked table to tablename
	 * | to already instantiated tableMap
	 */
	@Test
	void test4AddMockedTable() {
		correctDf.addTable("test");
		Table t = new Table("testMockTable");
		correctDf.addMockedTable(t);

		boolean contains = false;

		for (List<String> list : correctDf.getTableNames().values()) {
			if (list.contains("testMockTable")) {
				contains = true;
			}
		}

		assertTrue(contains);
	}

	/**
	 * Test 5 : Add empty table to domain
	 * | domain should have an table 
	 */
	@Test
	void test5AddTable() {
		String tableName = "testTable";
		df = new DomainFacade();
		df.addTable(tableName);
		
		boolean contains = false;

		for (List<String> list : df.getTableNames().values()) {
			if (list.contains(tableName)) {
				contains = true;
			}
		}

		assertTrue(contains);
	}

	/**
	 * Test 6 : Get table
	 * | should return a table map instance
	 */
	@Test
	void getTableMap() {
		String tableName = "testTable";
		df = new DomainFacade();
		df.addTable(tableName);
		assertFalse(df.getTableMap().isEmpty());

	}

	/**
	 * Test 7 : Update table name
	 * | should change the name of the table
	 */
	@Test
	void test7UpdateTableNameWithValidString() {
		String tableName = "testTable";
		df = new DomainFacade();
		Table t = new Table("testTable");
		df.addTable(tableName);
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);
		df.updateTableName(id, "newTableName");
		assertEquals("newTableName", df.getTableNameOfId(id));
	}

	/**
	 * Test 8 : Update table name with null value
	 * | should throw Domain Exception
	 */
	@Test
	void test8UpdateTableNameWithNullValue() {
		String tableName = "testTable";
		df = new DomainFacade();
		Table t = new Table("testTable");
		df.addTable(tableName);
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);

		Exception e = assertThrows(DomainException.class, () -> df.updateTableName(id, null));
		assertEquals("Table name already exists in another table or is empty.", e.getMessage());
	}

	/**
	 * Test 8 : Get table names
	 * | should return the names of every table in domainfacade
	 */
	@Test
	void test9UpdateTableNameWithNullValue() {
		String tableName = "testTable";
		String tableName2 = "test2Table";
		df = new DomainFacade();
		Table t = new Table(tableName);
		Table t2 = new Table(tableName2);
		df.addTable(tableName);
		UUID id = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		df.getTableMap().put(id, t);
		df.getTableMap().put(id2, t2);

		boolean contains1 = false;
		boolean contains2 = false;

		for (List<String> list : df.getTableNames().values()) {
			if (list.contains(tableName)) {
				contains1 = true;
			} else if (list.contains(tableName2)) {
				contains2 = true;
			}
		}

		assertTrue(contains1);
		assertTrue(contains2);
	}

	/**
	 * Test 9 : Get name of table given id
	 * | should return the corresponding table name
	 */
	@Test
	void test9GetTableNameFromId() {
		String tableName = "testTable";
		df = new DomainFacade();
		Table t = new Table("testTable");
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);
		assertEquals(tableName, df.getTableNameOfId(id));
	}

	/**
	 * Test 10 : Get table name with id null
	 * | should throw Domain exception
	 */
	@Test
	void test10GetTableNameFromIdAsNull() {
		String tableName = "testTable";
		df = new DomainFacade();
		Table t = new Table("testTable");
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);
		Exception e = assertThrows(DomainException.class, () -> df.getTableNameOfId(null));
		assertEquals("Cannot get table name of a null id", e.getMessage());
	}

	/**
	 * Test 11 : Get table name with id that not exists
	 * | should throw an domain exception
	 */
	@Test
	void test11GetTableNameFromIdNotExist() {
		String tableName = "testTable";
		df = new DomainFacade();
		Table t = new Table("testTable");
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);
		Exception e = assertThrows(DomainException.class, () -> df.getTableNameOfId(UUID.randomUUID()));
		assertEquals("Cannot find table for id", e.getMessage());
	}

	/**
	 * Test 12 : Create new table
	 * | should add table to domainfacade
	 */
	@Test
	void test12createNewTableInDomainFacade() {
		df = new DomainFacade();
		int sizeBefore = df.getTableMap().size();
		df.createNewTable();
		assertEquals(sizeBefore, df.getTableMap().size() - 1);
	}

	/**
	 * Test 13 : Create new table with name that exists
	 * | should add table to domainfacade
	 */
	@Test
	void test13createNewTableInDomainFacade() {
		df = new DomainFacade();

		int sizeBefore = df.getTableMap().size();
		df.createNewTable();
		df.createNewTable();
		assertEquals(sizeBefore, df.getTableMap().size() - 2);
	}

	/**
	 * Test 14 : Remove a table from table	
	 * | number of tables should be less 
	 */
	@Test
	void test14RemoveTableFromDomainFacade() {
		df = new DomainFacade();
		UUID id = UUID.randomUUID();
		Table t = new Table(id, "test");

		df.addMockedTable(t);
		int sizeBefore = df.getTableMap().keySet().size();
		df.deleteTable(id);

		assertEquals(sizeBefore -1 , df.getTableNames().keySet().size());
	}

	/**
	 * Test 15 : Remove a table from table
	 * | with null as table id.
	 * | should throw an domain exception
	 */
	@Test
	void test15RemoveTableWithNullAsIdFromDomainFacade() {
		df = new DomainFacade();
		Table t = new Table("test");
		UUID id = UUID.randomUUID();

		df.getTableMap().put(id, t);
		int sizeBefore = df.getTableMap().size();

		Exception e = assertThrows(DomainException.class, () -> df.deleteTable(null));
		assertEquals("Cannot delete a table with a null index", e.getMessage());
	}

	/**
	 * Test 16 : Get column characteristics
	 * | should return the characteristics
	 */
	@Test
	void test16GetColumnCharacteristics() {
		df = new DomainFacade();
		UUID id = UUID.randomUUID();
		Table t = new Table("tableName");
		df.getTableMap().put(id, t);
		df.addColumnToTable(id);
		df.getColumnCharacteristics(id);
		assertTrue(df.getColumnCharacteristics(id) instanceof Map);
	}

	/**
	 * Test 17 : Get column characteristics
	 * | given table id is null
	 * | should throw an domain exception
	 */
	@Test
	void test17GetColumnCharacteristicsWithNullAsTableId() {
		df = new DomainFacade();
		UUID id = UUID.randomUUID();
		Table t = new Table("tableName");
		df.getTableMap().put(id, t);
		df.addColumnToTable(id);
		Exception e = assertThrows(DomainException.class, () -> df.getColumnCharacteristics(null));
		assertEquals("Cannot get column characteristiscs with a null id", e.getMessage());

	}

	/**
	 * Test 18 : Get column characteristics
	 * | given table id is not existing
	 * | should throw an domain exception
	 */
	@Test
	void test18GetColumnCharacteristicsWithNotExistingAsTableId() {
		df = new DomainFacade();
		UUID id = UUID.randomUUID();
		Table t = new Table("tableName");
		df.getTableMap().put(id, t);
		df.addColumnToTable(id);
		Exception e = assertThrows(DomainException.class, () -> df.getColumnCharacteristics(UUID.randomUUID()));
		assertEquals("No table could be found with given id.", e.getMessage());
	}

	/**
	 * Test 19 : Add column to table
	 * | with null as table id
	 * | should throw an domain exception
	 */
	@Test
	void test19AddColumnToTableWithNullAsTableId() {
		df = new DomainFacade();
		UUID id = UUID.randomUUID();
		Table t = new Table("tableName");
		df.getTableMap().put(id, t);
		Exception e = assertThrows(DomainException.class, () -> df.addColumnToTable(null));
		assertEquals("Cannot add a column to a table with a null id.", e.getMessage());
	}

	/**
	 * Test 20 : Add column to table
	 * | with an not existing table id
	 * | table should not have been altered and hascode should remain the same
	 */
	@Test
	void test20AddColumnToTableWithNotExistingAsTableId() {
		df = new DomainFacade();
		UUID id = UUID.randomUUID();
		Table t = new Table("tableName");
		df.getTableMap().put(id, t);
		int hashBefore = df.hashCode();
		df.addColumnToTable(UUID.randomUUID());
		assertEquals(hashBefore, df.hashCode());
	}

	/**
	 * Test 21 : Update column name
	 * | with table id as null
	 * | should throw domain exception
	 */
	@Test
	void test21UpdateColumnNameWithNullAsTableId() {
		df = new DomainFacade();
		UUID tableId = UUID.randomUUID();
		Table table = new Table("tableName");
		UUID columnId = UUID.randomUUID();
		Column column = new Column("oldName");
		table.addColumn(column);
		df.getTableMap().put(tableId, table);
		Exception e = assertThrows(DomainException.class, () -> df.updateColumnName(null, columnId, "newColumnName"));
		assertEquals("Cannot update a column name with a null id.", e.getMessage());
	}

	/**
	 * Test 22 : Update column name
	 * | with column id as null
	 * | should return domain exception
	 */
	@Test
	void test22UpdateColumnNameWithNullAsColumnId() {
		df = new DomainFacade();
		UUID tableId = UUID.randomUUID();
		Table table = new Table("tableName");
		UUID columnId = UUID.randomUUID();
		Column column = new Column("oldName");
		table.addColumn(column);
		df.getTableMap().put(tableId, table);
		Exception e = assertThrows(DomainException.class, () -> df.updateColumnName(tableId, null, "newColumnName"));
		assertEquals("Cannot update a column name with a null id.", e.getMessage());
	}

	/**
	 * Test 23 : Update column name
	 * | with valid parameters
	 */
	@Test
	void test23UpdateColumnNameWithValidParams() {
		df = new DomainFacade();
		Table table = new Table("tableName");
		Column column = new Column("oldName");
		table.addColumn(column);
		df.getTableMap().put(table.getId(), table);
		int hashBefore = df.hashCode();
		df.updateColumnName(table.getId(), column.getId(), "newColumnName");
		assertTrue(hashBefore == df.hashCode());

	}

	/**
	 * Test 24 : Update column name
	 * | with null as new column name
	 * | should return domain exception
	 */
	@Test
	void test24UpdateColumnNameWithNullAsNewColumnName() {
		df = new DomainFacade();
		UUID tableId = UUID.randomUUID();
		Table table = new Table("tableName");
		UUID columnId = UUID.randomUUID();
		Column column = new Column("oldName");
		table.addColumn(column);
		df.getTableMap().put(tableId, table);
		Exception e = assertThrows(DomainException.class, () -> df.updateColumnName(tableId, columnId, null));
		assertEquals("Cannot set a new column name with a null or empty name.", e.getMessage());
	}

	/**
	 * Test 25 : Update column name
	 * | with empty String as new column name
	 * | should return domain exception
	 */
	@Test
	void test25UpdateColumnNameWithEmptyStringAsNewColumnName() {
		df = new DomainFacade();
		UUID tableId = UUID.randomUUID();
		Table table = new Table("tableName");
		UUID columnId = UUID.randomUUID();
		Column column = new Column("oldName");
		table.addColumn(column);
		df.getTableMap().put(tableId, table);
		Exception e = assertThrows(DomainException.class, () -> df.updateColumnName(tableId, columnId, ""));
		assertEquals("Cannot set a new column name with a null or empty name.", e.getMessage());
	}

}
