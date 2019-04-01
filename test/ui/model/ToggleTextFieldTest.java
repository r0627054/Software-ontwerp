package ui.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseEvent;
import java.util.UUID;

import org.junit.jupiter.api.Test;

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
		ttf = new ToggleTextField(x, y, width, height, text, id);
		assertAll(
				() -> assertEquals(this.x, ttf.getX()),
				() -> assertEquals(this.y, ttf.getY()),
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
		ttf = new ToggleTextField(x, y, width, height, text, id);
		ttf.mouseClicked(MouseEvent.MOUSE_CLICKED, x, y, 1);
	}
	
	/**
	 * Test 3 : Mouse clicked
	 * with no valid event
	 */
	@Test
	void test3MouseClickedWrongEvent() {
		ttf = new ToggleTextField(x, y, width, height, text, id);
		ttf.mouseClicked(MouseEvent.BUTTON2, x, y, 1);
	}
	
	/**
	 * Test 4 : Throw error
	 */
	@Test
	void test4ThrowAnErrorIdsMatch() {
		ttf = new ToggleTextField(x, y, width, height, text, id);
		ttf.throwError(id);
	}
	
	/**
	 * Test 5 : Throw error
	 * with no matching ids should do nothing
	 */
	@Test
	void test5ThrowAnErrorIdsNoMatch() {
		ttf = new ToggleTextField(x, y, width, height, text, id);
		ttf.throwError(UUID.randomUUID());
	}
	
	
	

}
