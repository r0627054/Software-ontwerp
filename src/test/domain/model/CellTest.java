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

	@Test
	void test1ConstructorCorrectType() {
		cell = new Cell(ColumnType.BOOLEAN);
		assertEquals(cell.getType(), ColumnType.BOOLEAN);
	}

	@Test
	void test2_Constructor_Null_Type_throws_exception() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new Cell(null));
		assertEquals(e.getMessage(), "Invalid columntype for the cell.");
	}
	
	@Test
	void test3ConstructorCorrectTypeAndCorrectValue() {
		cell = new Cell(ColumnType.BOOLEAN, true);
		assertEquals(cell.getType(), ColumnType.BOOLEAN);
		assertEquals(cell.getValue(), true);
	}
	
	@Test
	void test4ConstructorCorrectTypeAndWrongValue() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new Cell(ColumnType.BOOLEAN, "Invalid value"));
		assertEquals(e.getMessage(), "Invalid value for this cell.");
	}
	
	@Test
	void test5ConstructorWrongTypeAndCorrectValue() {
		DomainException e = assertThrows(DomainException.class, () -> cell = new Cell(null, "Value"));
		assertEquals(e.getMessage(), "Invalid columntype for the cell.");
	}
}
