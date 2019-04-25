package ui.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import controller.handlers.ChangeEventType;
import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableComponent;
import ui.model.components.EditableTextField;

class EditableComponentTest {
	private EditableComponent ecomp;

	/**
	 * Test 1 : Basic constructor
	 * 
	 */
	@Test
	void test1BasicConstructor() {
		UUID id = UUID.randomUUID();
		ecomp = new EditableTextField("testString", id, ChangeEventType.REPAINT, null, null);
		assertEquals(id, ecomp.getId());
	}

	/**
	 * Test 2 : Set error
	 */
	@Test
	void test2SetError() {
		ecomp = new CheckBox(true, UUID.randomUUID(), ChangeEventType.REPAINT);
		boolean err = true;
		ecomp.setError(err);
		assertEquals(err, ecomp.isError());
	}
}