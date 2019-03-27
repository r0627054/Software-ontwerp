package domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellTest {
	private DomainCell cell;

	@BeforeEach
	void setup() {
	}

	/**
	 * Test 1: Testing the constructor
	 * Testing constructor with one parameter.
	 */
	@Test
	void test1ConstructorWithOnlyTypeSetsCorrectVariables() {
		cell = new DomainCell(ValueType.BOOLEAN);
		assertEquals(cell.getType(), ValueType.BOOLEAN);
		assertEquals(cell.getValue(), ValueType.BOOLEAN.getDefaultValue());
	}

	/**
	 * Test 2: Testing the constructor
	 * Testing constructor with null parameter.
	 */
	@Test
	void test2_Constructor_Null_Type_throws_exception() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new DomainCell(null));
		assertEquals(e.getMessage(), "Invalid valuetype for the cell.");
	}

	/**
	 * Test 3: Testing the constructor
	 * Testing constructor with two parameters.
	 */
	@Test
	void test3ConstructorCorrectTypeAndCorrectValueShouldSetCorrectVariables() {
		cell = new DomainCell(ValueType.BOOLEAN, true);
		assertEquals(cell.getType(), ValueType.BOOLEAN);
		assertEquals(cell.getValue(), true);
	}

	/**
	 * Test 4: Testing the constructor
	 * Testing constructor with two parameters with mismatching type/value.
	 */
	@Test
	void test4ConstructorCorrectTypeAndWrongValueShouldThrowException() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new DomainCell(ValueType.BOOLEAN, "Invalid value"));
		assertEquals(e.getMessage(), "Invalid value for this cell.");
	}

	/**
	 * Test 5: Testing the constructor
	 * Testing constructor with two parameters with mismatching type/value.
	 */
	@Test
	void test5ConstructorWrongTypeAndCorrectValueShouldThrowException() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new DomainCell(null, "Value"));
		assertEquals(e.getMessage(), "Invalid valuetype for the cell.");
	}
}
