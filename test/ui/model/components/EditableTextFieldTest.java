package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class EditableTextFieldTest {
	private EditableTextField etextf;
	private int x = 20;
	private int y = 10;
	private int width = 90;
	private int height = 50;
	private List<Component> listItems = new ArrayList<>();
	private Component comp1 = new CheckBox(false, UUID.randomUUID());
	private Component comp2 = new EditableTextField("teststring", UUID.randomUUID());
	private Component comp3 = new CheckBox(true, UUID.randomUUID());
	private Container correctContainer = new Container(x, y, width, height);
	
	/**
	 * Test 1 : Basic constructor
	 * 
	 */
	@Test
	void test() {
		UUID id = UUID.randomUUID();
		etextf = new EditableTextField("testString", id);
		assertEquals(id, etextf.getId());
	}
	
//	/**
//	 * Test 2 : Test changed
//	 */
//	@Test
//	void test2Changed() {
//		etextf = new EditableTextField("testString", UUID.randomUUID());
//		etextf.setError(true);
//		assertNotEquals(true, etextf.textChanged());
//		
//	}

}
