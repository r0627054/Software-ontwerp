package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

class CellTest {
	private Cell cell;
	private UUID id = UUID.randomUUID();
	private int correctX = 100;
	private int correctY = 400;
	private Cell correctCell = new Cell(correctX, correctY, "test", id);

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test 1 : Constructor with string
	 * | should create an editable textfield component as value
	 */
	@Test
	void test1CreateCellWithString() {
		cell = new Cell(100,400, "stringvalue", id);
		assertTrue(cell.getComponent() instanceof EditableTextField);
	}
	
	/**
	 * Test 2 : Constructor with bool
	 * | should create a checkbox component as value
	 */
	@Test
	void test2CreateCellWithBool() {
		cell = new Cell(100,400, true, id);
		assertTrue(cell.getComponent() instanceof CheckBox);
	}
	
	
	/**
	 * Test 3 : Constructor with integer as valuetyp
	 * | should create an editable textfield as value
	 */
	@Test
	void test3CreateCellWithInt() {
		cell = new Cell(100,400, 21, id);
		assertTrue(cell.getComponent() instanceof EditableTextField);
	}
	
	/**
	 * Test 4 : Constructor no string nor bool as value
	 * | should create an editable textfield by defaultvalue
	 */
	@Test
	void test4CreateCellWithDefaultValue() {  
		Exception e = assertThrows(IllegalArgumentException.class, () -> cell = new Cell(100,400,null,id));
		assertEquals("Cannot add a null component to a cell.", e.getMessage());
	}
	
	/**
	 * Test 5 : Change component
	 */
	@Test
	void test5UpdateComponentAfterCreating() {
		
		Cell newComponent = correctCell;
		newComponent.setComponent((Component) new Cell(100,400,21, id));
		assertTrue(correctCell.equals(newComponent));
	}
	

	
	
	

}
