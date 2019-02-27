package test.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.model.DomainException;
import domain.model.Table;

class TableTest {
	
	private Table table;
	private String tableName;
	
	@BeforeEach
	void setup() {
		tableName = "tableName";
	}
	
	@Test
	void test1ConstructorCorrectName() {
		table = new Table(tableName);
		assertSame(table.getName(), tableName);
	}
	
	@Test
	void test2ConstructorNullName() {		
		DomainException e = assertThrows(DomainException.class, () -> table = new Table(null));
		assertEquals(e.getMessage(),"Invalid Table name!");
	}
	
	
	
}
