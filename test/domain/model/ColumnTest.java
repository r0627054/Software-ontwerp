package domain.model;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

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

	/**
	 * Test 1: Testing the constructor
	 * No blanks allowed should return a non blank default value
	 */
	@Test
	public void test1ConstructorCorrectParamsNoBlanksSetsNoBlankDefaultValue(){
		allowBlanks = false;

		new Column(columnName, stringType, allowBlanks);
		assertNotEquals(column.getDefaultValue(), "TODO: add non empty defaults");
	}

	/**
	 * Test 2: Testing the constructor
	 * With blanks allowed should return a blank default value
	 */
	@Test
	public void test2ConstructorCorrectParamsWithBlanksSetsBlankDefaultValue(){
		allowBlanks = true;

		//assertDoesNotThrow(() -> column = new Column(columnName, stringType, allowBlanks));
		assertEquals(column.getDefaultValue(), "");
	}

	/**
	 * Test 3: Testing the constructor
	 * With no blanks not specified should return a blank default value
	 */
	@Test
	public void test3ConstructorCorrectParamsNoBlanksSpecifiedSetsDefaultValue(){
		//assertDoesNotThrow(() -> column = new Column(columnName, stringType));
		assertEquals(column.getDefaultValue(), "");
	}

	/**
	 * Test 4: Testing the behaviour of default values
	 * With default value set to blank, you should not be able to set to not allow blanks.
	 */
	@Test
	public void test4SetToNoBlanksAllowedWhenDefaultIsBlankShouldThrowException(){
		correctStringColumn.setDefaultValue(null);

		DomainException e = assertThrows(DomainException.class, () -> correctStringColumn.setAllowsBlanks(false));
		assertEquals(e.getMessage(), "Default value is still empty.");
	}

	/**
	 * Test 5: Testing the behaviour of default values
	 * You should not be able to set a default value with an invalid type.
	 */
	@Test
	public void test5SetDefaultValueWithInvalidTypeShouldThrowException(){
		DomainException e = assertThrows(DomainException.class, () -> correctStringColumn.setDefaultValue(true));
		assertEquals(e.getMessage(), "Invalid default column value.");
	}

	/**
	 * Test 6: Testing the behaviour of default values
	 * You should not be able to set a blank default value with blanks disabled.
	 */
	@Test
	public void test6SetBlankDefaultValueWitBlanksDisabledShouldThrowException(){
		correctStringColumn.setAllowsBlanks(false);

		DomainException e = assertThrows(DomainException.class, () -> correctStringColumn.setDefaultValue(null));
		assertEquals(e.getMessage(), "Blanks are not allowed as default value.");
	}

}
