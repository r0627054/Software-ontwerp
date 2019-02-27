package test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.model.Cell;
import domain.model.ColumnType;
import domain.model.DomainException;

public class CellTest {
	private Cell cell;

	@BeforeEach
	void setup() {
	}

	/**
	 * Test 1: Testing the constructor
	 * Testing constructor with one parameter.
	 */
	@Test
	void test1ConstructorWithOnlyTypeSetsCorrectVariables() {
		cell = new Cell(ColumnType.BOOLEAN);
		assertEquals(cell.getType(), ColumnType.BOOLEAN);
		assertEquals(cell.getValue(), ColumnType.BOOLEAN.getDefaultValue());
	}

	/**
	 * Test 2: Testing the constructor
	 * Testing constructor with null parameter.
	 */
	@Test
	void test2ConstructorWithNullTypeParamShouldThrowException() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new Cell(null));
		assertEquals(e.getMessage(), "Invalid columntype for the cell.");
	}
	
	/**
	 * Test 3: Testing the constructor
	 * Testing constructor with two parameters.
	 */
	@Test
	void test3ConstructorCorrectTypeAndCorrectValueShouldSetCorrectVariables() {
		cell = new Cell(ColumnType.BOOLEAN, true);
		assertEquals(cell.getType(), ColumnType.BOOLEAN);
		assertEquals(cell.getValue(), true);
	}
	
	/**
	 * Test 4: Testing the constructor
	 * Testing constructor with two parameters with mismatching type/value.
	 */
	@Test
	void test4ConstructorCorrectTypeAndWrongValueShouldThrowException() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new Cell(ColumnType.BOOLEAN, "Invalid value"));
		assertEquals(e.getMessage(), "Invalid value for this cell.");
	}
	
	/**
	 * Test 5: Testing the constructor
	 * Testing constructor with two parameters with mismatching type/value.
	 */
	@Test
	void test5ConstructorWrongTypeAndCorrectValueShouldThrowException() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new Cell(null, "Value"));
		assertEquals(e.getMessage(), "Invalid columntype for the cell.");
	}
}
