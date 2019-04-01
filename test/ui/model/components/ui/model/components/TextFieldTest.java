package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class TextFieldTest {
	private TextField tf;
	private int x = 30;
	private int y = 40;
	private int height = 50;
	private int width = 60;
	private String text = "test";
	
	/**
	 * Test 1 : Basic constructor
	 */
	@Test
	void test1GettingAndSettingBasicConstructorInit() {
		tf = new TextField(x, y, width, height, text);
		assertAll(
				() -> assertTrue(x == tf.getX()),
				() -> assertTrue(y == tf.getY()),
				() -> assertTrue(width == tf.getWidth()),
				() -> assertTrue(height == tf.getHeight())
				);
	}
	
	/**
	 * Test 2 : Basic constructor
	 */
	@Test
	void test2GettingAndSetting() {
		UUID id = UUID.randomUUID();
		tf = new TextField(x, y, width, height, text, id);
		assertAll(
				() -> assertEquals(this.x, tf.getX()),
				() -> assertEquals(this.y, tf.getY()),
				() -> assertEquals(this.width, tf.getWidth()),
				() -> assertEquals(this.height, tf.getHeight()),
				() -> assertEquals(id , tf.getId())
				);
	}
	
	/**
	 * Test 3 : Set text as null
	 * | should throw illegalArgumentExcpeption
	 */
	@Test
	void test3setTextAsNull() {
		tf = new TextField(x,y,width,height,text);
		Exception e = assertThrows(IllegalArgumentException.class, () -> tf.setText(null));
		assertEquals("Text of TextField cannot be empty.", e.getMessage());
	}
	
	/**
	 * Test 4 : Get text from component
	 * | should match the given text
	 */
	@Test
	void test4GetTextFromComponent() {
		String myText = "demotext";
		tf = new TextField(x,y,width,height,myText);
		assertEquals(myText, tf.getText());
	}
	
	/**
	 * Test 5 : pressKey
	 */
	@Test
	void test5PressAnykey() {
		tf = new TextField(x,y,width,height,text);
		tf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_0, 'p'); // key en char maken niet uit
	}
	
	/**
	 * Test 6 : mouse clicked
	 */
	@Test
	void test6MouseClicked() {
		tf = new TextField(x,y,width,height,text);
		tf.mouseClicked(MouseEvent.MOUSE_CLICKED, x, y, 1); // key en char maken niet uit
	}

}
