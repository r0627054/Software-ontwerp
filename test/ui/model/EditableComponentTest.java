package ui.model;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableComponent;
import ui.model.components.EditableTextField;

class EditableComponentTest {
	private EditableComponent ecomp;
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
	void test1BasicConstructor() {
		UUID id = UUID.randomUUID();
		ecomp = new EditableTextField("testString",id);
		assertEquals(id, ecomp.getId());
}

	/**
	 * Test 2 : Set error
	 */
	@Test
	void test2SetError() {
		ecomp = new CheckBox(true, UUID.randomUUID());
		boolean err = true;
		ecomp.setError(err);
		assertEquals(err, ecomp.isError());
	}
}