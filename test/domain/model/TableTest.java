package domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableTest {
	
	private Table table;
	private String tableName;
	
	@BeforeEach
	void setup() {
		tableName = "tableName";
	}
	
	/**
	 * Test 1: Testing the constructor
	 * Testing constructor with correct name should set the name correctly.
	 */
	@Test
	void test1ConstructorCorrectNameSetsNameCorrectly() {
		table = new Table(tableName);
		assertSame(table.getName(), tableName);
	}
	
	/**
	 * Test 2: Testing the constructor
	 * Testing constructor with an empty name should throw an error.
	 */
	@Test
	void test2ConstructorNullNameParamShouldThrowException() {		
		DomainException e = assertThrows(DomainException.class, () -> table = new Table(null));
		assertEquals(e.getMessage(),"Invalid Table name!");
	}
	
	
	
}
