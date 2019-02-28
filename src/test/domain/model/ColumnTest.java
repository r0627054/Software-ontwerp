package test.domain.model;

import static org.junit.Assert.assertNotEquals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import domain.model.Cell;
import domain.model.Column;
import domain.model.ColumnType;
import domain.model.DomainException;

public class ColumnTest {

	private final String columnName = "TestColumnName";
	private final ColumnType stringType = ColumnType.STRING;
	private final ColumnType boolType = ColumnType.BOOLEAN;
	
	private boolean allowBlanks = true;
	
	private Column column;
	private Column correctStringColumn = new Column("String column", stringType);

	@BeforeEach
	public void setup() {
		correctStringColumn = new Column("String column", stringType);
	}

	@Test
	public void test1ConstructorCorrectParamsNoBlanks(){
		allowBlanks = false;
		
		//assertDoesNotThrow(() -> column = new Column(columnName, stringType, allowBlanks));
		assertNotEquals(column.getDefaultValue(), "TODO: add non empty defaults");
	}
	
	@Test
	public void test2ConstructorCorrectParamsWithBlanks(){
		allowBlanks = true;
		
		//assertDoesNotThrow(() -> column = new Column(columnName, stringType, allowBlanks));
		assertEquals(column.getDefaultValue(), "");
	}
	
	@Test
	public void test3ConstructorCorrectParamsNoBlanksSpecified(){
		//assertDoesNotThrow(() -> column = new Column(columnName, stringType));
		assertEquals(column.getDefaultValue(), "");
	}
	
	@Test
	public void test4SetToNoBlanksAllowedWhenDefaultIsBlank(){
		correctStringColumn.setDefaultValue(null);
		
		DomainException e = assertThrows(DomainException.class, () -> correctStringColumn.setAllowsBlanks(false));
		assertEquals(e.getMessage(), "Default value is still empty.");
	}
	
	@Test
	public void test5SetDefaultValueWithInvalidType(){	
		DomainException e = assertThrows(DomainException.class, () -> correctStringColumn.setDefaultValue(true));
		assertEquals(e.getMessage(), "Invalid default column value.");
	}
	
	@Test
	public void test6SetBlankDefaultValueWitBlanksDisabled(){	
		correctStringColumn.setAllowsBlanks(false);
		
		DomainException e = assertThrows(DomainException.class, () -> correctStringColumn.setDefaultValue(null));
		assertEquals(e.getMessage(), "Blanks are not allowed as default value.");
	}
	
}
