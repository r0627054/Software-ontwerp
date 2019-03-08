package domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class RowTest {
	private Row r;

	
	/**
	 * Test 1 : Constructor with list of cells
	 * Testing if the given cells are stored correctly in the constructor.
	 */
	@Test
	void test1CreateNewRowWithCells() {
		List<Cell> cells = new ArrayList<>();
		Cell c1 = new Cell(ValueType.BOOLEAN);
		cells.add(c1);
		Cell c2 = new Cell(ValueType.EMAIL);
		cells.add(c2);
		Cell c3 = new Cell(ValueType.INTEGER);
		cells.add(c3);
		
		r = new Row((ArrayList<Cell>) cells);
		
		assertEquals(cells, r.getCells());
		
	}
	/**
	 * Test 2 : Constructor with null
	 * The method should return an exception when a null list is given to the constructor.
	 */
	@Test
	void test2CreateNewRowWithNoCells() {
		ArrayList<Cell>  cells = null;
		DomainException e = assertThrows(DomainException.class, () -> r = new Row(cells));
		assertEquals(e.getMessage(), "The List of cells cannot be null for a column.");
	}
	
	/**
	 * Test 3 : Add new cell list as null
	 * The method should return an exception when a cell with value null is passed on.
	 */
	@Test
	void test3SetCellsAsNull() {
		List<Cell> cells = new ArrayList<>();
		r = new Row((ArrayList<Cell>) cells);
		DomainException e = assertThrows(DomainException.class, () -> r.addCell(null));
		assertEquals(e.getMessage(),"A cell cannot be null in a column.");
	}
	
	/**
	 * Test 4 : Get cell at index
	 * Should return the cell at the right index.
	 */
	@Test
	void test4GetCellAtIndex() {
		List<Cell> cells = new ArrayList<>();
		Cell c1 = new Cell(ValueType.BOOLEAN);
		cells.add(c1);
		Cell c2 = new Cell(ValueType.EMAIL);
		cells.add(c2);
		Cell c3 = new Cell(ValueType.INTEGER);
		cells.add(c3);
		
		r = new Row((ArrayList<Cell>) cells);
		assertEquals(c2, r.getCellAtIndex(1)); // the second element should be found on index 1
	}
	
	/**
	 * Test 5 : Get cell at negative index
	 * A negative index should throw an domainexception.
	 */
	@Test
	void test5GetCellAtNegativeIndex() {
		List<Cell> cells = new ArrayList<>();
		Cell c1 = new Cell(ValueType.BOOLEAN);
		cells.add(c1);
		r = new Row((ArrayList<Cell>) cells);
		DomainException e = assertThrows(DomainException.class, () -> r.getCellAtIndex(-1));
		assertEquals(e.getMessage(), "Index of cell cannot be negative.");

	}
	

	
	/**
	 * Test 6 : Add a cell to the row
	 * Number of elements should be incremented with adding a cell.
	 */
	@Test
	void test6AddCellToRow() {
		List<Cell> cells = new ArrayList<>();
		Cell c1 = new Cell(ValueType.BOOLEAN);
		cells.add(c1);
		r = new Row((ArrayList<Cell>) cells);
		int oldAmount = r.getCells().size();
		Cell c2 = new Cell(ValueType.INTEGER);
		r.addCell(c2);
		assertEquals(oldAmount +1, r.getCells().size());
		
	}
	/**
	 * Test 7 : 
	 * A negative index should throw an domainexception.
	 */
	@Test
	void test7AddNullCellToRow() {
		List<Cell> cells = new ArrayList<>();
		Cell c1 = new Cell(ValueType.BOOLEAN);
		cells.add(c1);
		r = new Row((ArrayList<Cell>) cells);
		DomainException e = assertThrows(DomainException.class, () -> r.addCell(null));
		assertEquals(e.getMessage(), "A cell cannot be null in a column.");

	}
	

}
