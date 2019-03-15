package domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class DomainFacadeTest {
	private DomainFacade df;
	private DomainFacade correctDf = DomainFacade.getInstance();
	
	/**
	 * Test 1 : Creates instance of domainfacade if not already exists
	 * | should initialise the df variable
	 */
	@Test
	void test1CreateDomainFacadeInstance() {
		df = DomainFacade.getInstance();
		assertNotNull(df);
	}
	
	/**
	 * Test 2 : Create instance of domainfacade when its already instantiated
	 * | should do nothing
	 */
	@Test
	void test2CreateDomainFacadeInstanceTwice() {
		df = DomainFacade.getInstance();
		int hashBefore = df.hashCode();
		df = DomainFacade.getInstance();
		assertEquals(hashBefore,df.hashCode());
	}
	
	/**
	 * Test 3 : Add dummy table to domainfacade
	 * | should instantiate tablemMap
	 */
	@Test
	void test3AddDummyTableWithComponents() {
		String tableName = "testTable";
		df = DomainFacade.getInstance();
		df.addDummyTable(tableName);
		assertTrue(df.getTableNames().containsValue(tableName));
	}
	
	/**
	 * Test 4 : Add mocked table to tablename
	 * | to already instantiated tableMap
	 */
	@Test
	void test4AddMockedTable() {
		correctDf.addDummyTable("test");
		Table t = new Table("testMockTable");
		correctDf.addMockedTable(t);
		assertTrue(correctDf.getTableNames().containsValue("testMockTable")); 
	}
	
	/**
	 * Test 5 : Add empty table to domain
	 * | domain should have an table 
	 */
	@Test
	void test5AddTable() {
		String tableName = "testTable";
		df = DomainFacade.getInstance();
		df.addTable(tableName);
		assertTrue(df.getTableNames().containsValue(tableName));
	}
	
	/**
	 * Test 6 : Get table
	 * | should return a table map instance
	 */
	@Test
	void getTableMap() {
		String tableName = "testTable";
		df = DomainFacade.getInstance();
		df.addDummyTable(tableName);
		assertFalse(df.getTableMap().isEmpty());
	
	}
	
	/**
	 * Test 7 : Update table name
	 * | should change the name of the table
	 */
	@Test
	void test7UpdateTableNameWithValidString() {
		String tableName = "testTable";
		df = DomainFacade.getInstance();
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
		df = DomainFacade.getInstance();
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
		df = DomainFacade.getInstance();
		Table t = new Table(tableName);
		Table t2 = new Table(tableName2);
		df.addTable(tableName);
		UUID id = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		df.getTableMap().put(id, t);
		df.getTableMap().put(id2, t2);
		
		assertTrue( df.getTableNames().containsValue(tableName) && df.getTableNames().containsValue(tableName2));
	}
	
	/**
	 * Test 9 : Get name of table given id
	 * | should return the corresponding table name
	 */
	@Test
	void test9GetTableNameFromId() {
		String tableName = "testTable";
		df = DomainFacade.getInstance();
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
		df = DomainFacade.getInstance();
		Table t = new Table("testTable");
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);
		System.out.println(df.getTableNameOfId(id));
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
		df = DomainFacade.getInstance();
		Table t = new Table("testTable");
		UUID id = UUID.randomUUID();
		df.getTableMap().put(id, t);
		System.out.println(df.getTableNameOfId(id));
		Exception e = assertThrows(DomainException.class, () -> df.getTableNameOfId(UUID.randomUUID()));
		assertEquals("Cannot find table for id", e.getMessage());
	}
	
	/**
	 * Test 12 : Create new table
	 * | should add table to domainfacade
	 */
	@Test
	void test12createNewTableInDomainFacade() {
		df = DomainFacade.getInstance();
		System.out.println();
		
		int sizeBefore = df.getTableMap().size();
		df.createNewTable();
		assertEquals(sizeBefore, df.getTableMap().size() -1);
	}
	
	/**
	 * Test 13 : Create new table with name that exists
	 * | should add table to domainfacade
	 */
	@Test
	void test13createNewTableInDomainFacade() {
		df = DomainFacade.getInstance();
		
		int sizeBefore = df.getTableMap().size();
		df.createNewTable();
		df.createNewTable();
		assertEquals(sizeBefore, df.getTableMap().size() -2);
	}
	
	/**
	 * Test 14 : Remove a table from table
	 * | number of tables should be less 
	 */
	@Test
	void test14RemoveTableFromDomainFacade() {
		df = DomainFacade.getInstance();
		Table t = new Table("test");
		UUID id = UUID.randomUUID();
		

		df.getTableMap().put(id, t);
		int sizeBefore = df.getTableMap().size();
		df.deleteTable(id);

		assertEquals(sizeBefore, df.getTableNames().size() + 1);
	}
	
	/**
	 * Test 15 : Remove a table from table
	 * | with null as table id.
	 * | should throw an domain exception
	 */
	@Test
	void test15RemoveTableWithNullAsIdFromDomainFacade() {
		df = DomainFacade.getInstance();
		Table t = new Table("test");
		UUID id = UUID.randomUUID();
		

		df.getTableMap().put(id, t);
		int sizeBefore = df.getTableMap().size();
		
		Exception e = assertThrows(DomainException.class, () -> df.deleteTable(null));
		assertEquals("Cannot delete a table with a null index", e.getMessage());
	}
}
