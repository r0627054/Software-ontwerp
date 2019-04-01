package ui.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.handlers.ChangeEventType;
import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.TextField;
import ui.model.components.UICell;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.UUID;

import javax.swing.event.DocumentEvent.EventType;

class CellTest {
	private UICell cell;
	private TextField textField = new TextField(0, 0, 200, 20, "testtTextField");
	private UUID id = UUID.randomUUID();
//	private int correctX = 100;
//	private int correctY = 400;
	private UICell correctCell = new UICell("test", id, ChangeEventType.REPAINT);
	
	

	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test 1 : Constructor with string
	 * | should create an editable textfield component as value
	 */
	@Test
	void test1CreateCellWithString() {
		cell = new UICell("stringvalue", id, ChangeEventType.REPAINT);
		assertTrue(cell.getComponent() instanceof EditableTextField);
	}
	
	/**
	 * Test 2 : Constructor with bool
	 * | should create a checkbox component as value
	 */
	@Test
	void test2CreateCellWithBool() {
		cell = new UICell(true, id, ChangeEventType.REPAINT);
		assertTrue(cell.getComponent() instanceof CheckBox);
	}
	
	
	/**
	 * Test 3 : Constructor with integer as valuetyp
	 * | should create an editable textfield as value
	 */
	@Test
	void test3CreateCellWithInt() {
		cell = new UICell(1337, id, ChangeEventType.REPAINT);
		assertTrue(cell.getComponent() instanceof EditableTextField);
	}
	
	/**
	 * Test 4 : Constructor no string nor bool as value
	 * | should create an editable textfield by defaultvalue
	 */
	@Test
	void test4CreateCellWithDefaultValue() {  
		Exception e = assertThrows(IllegalArgumentException.class, () -> cell = new UICell(null, id, ChangeEventType.REPAINT));
		assertEquals("Cannot add a null component to a cell.", e.getMessage());
	}
	
	/**
	 * Test 5 : Change component
	 */
	@Test
	void test5UpdateComponentAfterCreating() {
		
		UICell newComponent = correctCell;
		newComponent.setComponent((Component) new UICell(21, id, ChangeEventType.REPAINT));
		assertTrue(correctCell.equals(newComponent));
	}
	

	
	
	

}
