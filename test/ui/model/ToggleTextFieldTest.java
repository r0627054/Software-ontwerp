package ui.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseEvent;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import controller.handlers.ChangeEventType;
import ui.model.components.ToggleTextField;

class ToggleTextFieldTest {
	private int x =10;
	private int y = 20;
	private int width = 30;
	private int height = 40;
	private UUID id =  UUID.randomUUID();
	private String text = "test";
	private ToggleTextField ttf;
	
	
	/**
	 * Test 1 : Constructor 
	 * | with start coords, dimensions, text and an id
	 */
	@Test
	void test() {
		ttf = new ToggleTextField(width, height, text, id, ChangeEventType.REPAINT);
		assertAll(
				() -> assertEquals(this.width, ttf.getWidth()),
				() -> assertEquals(this.height, ttf.getHeight()),
				() -> assertEquals(this.text, ttf.getText()),
				() -> assertEquals(id , ttf.getId())
				);
	}
	
	/**
	 * Test 2 : Mouse clicked
	 */
	@Test
	void test2MouseClicked() {
		ttf = new ToggleTextField(width, height, text, id, ChangeEventType.REPAINT);
		ttf.mouseClicked(MouseEvent.MOUSE_CLICKED, x, y, 1);
	}
	
	/**
	 * Test 3 : Mouse clicked
	 * with no valid event
	 */
	@Test
	void test3MouseClickedWrongEvent() {
		ttf = new ToggleTextField(width, height, text, id, ChangeEventType.REPAINT);
		ttf.mouseClicked(MouseEvent.BUTTON2, x, y, 1);
	}
	
	/**
	 * Test 4 : Throw error
	 */
	@Test
	void test4ThrowAnErrorIdsMatch() {
		ttf = new ToggleTextField(width, height, text, id, ChangeEventType.REPAINT);
		ttf.throwError(id);
	}
	
	/**
	 * Test 5 : Throw error
	 * with no matching ids should do nothing
	 */
	@Test
	void test5ThrowAnErrorIdsNoMatch() {
		ttf = new ToggleTextField(width, height, text, id, ChangeEventType.REPAINT);
		ttf.throwError(UUID.randomUUID());
	}
	
	
	

}
