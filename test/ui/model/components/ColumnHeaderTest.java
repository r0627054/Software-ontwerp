package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class ColumnHeaderTest {
	private int x = 10;
	private int y = 20;
	private int width = 30;
	private int height = 40;
	private String text = "Test";
	private ColumnHeader ch;
	private UUID id = UUID.randomUUID();
	
	/**
	 * Test 1 : Basic constructor init
	 * | set and get params from constructor
	 */
	@Test
	void test1BasicConstructorInit() {
		ch = new ColumnHeader(text, id);
		assertAll(
				() -> assertEquals(id , ch.getId()),
				() -> assertEquals(text, ch.getText())
				);
	}
	
	/**
	 * Test 2 : Expanded Constructor
	 * | given the start coords and the dimensions of the component
	 */
	@Test
	void test2ConstructorWithCoordsAndDimensions() {
		ch = new ColumnHeader(x, y, width, height, text, id);
		assertAll(
				() -> assertEquals(this.x, ch.getX()),
				() -> assertEquals(this.y, ch.getY()),
				() -> assertEquals(this.width, ch.getWidth()),
				() -> assertEquals(this.height, ch.getHeight()),
				() -> assertEquals(id , ch.getId())
				);
	}

}
