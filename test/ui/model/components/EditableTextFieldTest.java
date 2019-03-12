package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.UUID;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class EditableTextFieldTest {
	private EditableTextField etextf;
	private int x = 20;
	private int y = 10;
	private int width = 90;
	private int height = 50;
	private String testValue = "test";
	UUID id = UUID.randomUUID();

	
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
	

	/**
	 * Test 2 : Click the mouse once
	 * | in a component, select component.
	 */
	@Test
	void test2MouseClickedCount1WithinComponent() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		assertTrue(etextf.isSelected());
	}
	
	/**
	 * Test 3 : Click the mouse twice
	 * | in a component. The component should be unselected
	 */
	@Test
	void test3MouseClickedCountTwiceWithinComponent() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 2);
		assertFalse(etextf.isSelected()); 
	}
	
	
	@Test
	void test4OnErrorSetSelectedAndReturnCursorPosition() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.setError(true);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		// click is juist within ook
		// selected is false -> true en clickcount is ook juist
		assertAll(
				() -> assertTrue(etextf.isSelected()),
	            () -> assertEquals(etextf.getText().length(), etextf.getPosition())
	            );
		
	
	}
	
	/**
	 * Test 5 : On mouse actions different from mouseclick
	 * | nothing should be changed or done.
	 */
	@Test
	void test5MouseDraggedCountTwiceShouldResetCursorPosition() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		int before = etextf.hashCode();
		etextf.mouseClicked(MouseEvent.MOUSE_DRAGGED, x+1, y+1, 1);
		assertEquals(before, etextf.hashCode());
	}
	
	/**
	 * Test 6 : Click once outside the component
	 * | nothing should be changed or done.
	 */
	@Test
	void test6MouseClickedCountTwiceShouldResetCursorPosition() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		int before = etextf.hashCode();
		etextf.mouseClicked(MouseEvent.MOUSE_DRAGGED, x+1, y+1, 1);
		assertEquals(before, etextf.hashCode());
	}
	
	/**
	 * Test 7 : MoveCursorToTheLeft
	 */
	@Test
	void test7MoveCursorToLeftWhenSelected() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		int positionBefore = etextf.getPosition();
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, '_');
		assert(positionBefore > etextf.getPosition());
	}
	
	/**
	 * Test 8 : MoveCursorToTheLeft
	 * | cursor position cant go further left than the position right before the line
	 */
	@Test
	void test8MoveCursorToLeftWhenSelectedExtreme() { 
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		for(int i = 0; i < etextf.getPosition() * 16; i++) { //TODO ulgy loop (*9 ???)
			etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, '_');
		}
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, '_');
		int positionBefore = etextf.getPosition();

		assertEquals(positionBefore, etextf.getPosition());
	}
	
	/**
	 * Test 9 : MoveCursorToTheRight
	 * | you only move the cursor to the right when the cursor is not yet at the end of the line
	 */
	@Test
	void test9MoveCursorToRightWhenSelected() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT, '_');
		int positionBefore = etextf.getPosition();
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT,'_');
		assertTrue(positionBefore < etextf.getPosition());
	}
	
	/**
	 * Test 10 : MoveCursorToTheRight
	 * | not possible when the position is already at the rightmost end
	 */
	@Test
	void test10MoveCursorToRightWhenSelectedExtreme() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		int positionBefore = etextf.getPosition();
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_RIGHT,'_');
		assertEquals(positionBefore, etextf.getPosition());
	}
	
	
	/**
	 * Test 11 : Press any letter or digit
	 * | when a digit or letter is pressed and the component selected 
	 * | the text should be changed
	 */
	@Test
	void test11PressLetterOrDigitWhenSelected() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		int positionBefore = etextf.getPosition();
		String textBefore = etextf.getText();
		char myChar = 'p';
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_N, myChar);
		assertAll(
				() -> assertEquals(textBefore + myChar, etextf.getText()),
				() -> assertTrue(positionBefore < etextf.getPosition())
				);
	}
	
	/**
	 * Test 12 : Press @
	 * | when the @ is pressed and the component selected
	 * | the text @ should be added to the text of the component
	 */
	@Test
	void test12PressAtForEmailWhenSelected() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		int positionBefore = etextf.getPosition();
		String textBefore = etextf.getText();
		char myChar = '@';
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_N, myChar);
		assertAll(
				() -> assertEquals(textBefore + myChar, etextf.getText()),
				() -> assertTrue(positionBefore < etextf.getPosition())
				);
	}
	
	/**
	 * Test 13 : Press .
	 * | when the . is pressed and the component selected
	 * | the text . should be added to the text of the component
	 */
	@Test
	void test13PressDotWhenSelected() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		int positionBefore = etextf.getPosition();
		String textBefore = etextf.getText();
		char myChar = '.';
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_N, myChar);
		assertAll(
				() -> assertEquals(textBefore + myChar, etextf.getText()),
				() -> assertTrue(positionBefore < etextf.getPosition())
				);
	}
	
	/**
	 * Test 14 : Press <BACKSPACE>
	 * | when the <BACKSPACE> is pressed and the component selected
	 * | the char right before the cursor should be removed
	 */
	@Test
	void test14PressBackSpaceWhenSelected() {
		etextf = new EditableTextField(x, y, width, height, testValue, id);
		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
		int positionBefore = etextf.getPosition();
		String textBefore = etextf.getText();
		char myChar = '.'; // maakt hier niet uit
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT,'_');
		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, myChar);
		assertAll(
				() -> assertNotEquals(textBefore, etextf.getText()),
				() -> assertEquals(positionBefore -1, etextf.getPosition())
				);
	}
	
//	/**
//	 * Test 15 : Press <BACKSPACE>
//	 * | when the <BACKSPACE> is pressed and the component selected
//	 * | the char right before the cursor should be removed
//	 */
//	@Test
//	void test15PressBackSpaceWhenSelected() {
//		etextf = new EditableTextField(x, y, width, height, testValue, id);
//		etextf.mouseClicked(MouseEvent.MOUSE_CLICKED, x+1, y+1, 1); 
//		int positionBefore = etextf.getPosition();
//		String textBefore = etextf.getText();
//		char myChar = '.'; // maakt hier niet uit
//		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_LEFT,'_');
//		etextf.keyPressed(KeyEvent.KEY_PRESSED, KeyEvent.VK_BACK_SPACE, myChar);
//		assertAll(
//				() -> assertNotEquals(textBefore, etextf.getText()),
//				() -> assertEquals(positionBefore -1, etextf.getPosition())
//				);
//	}
	
	
	
	
	
	
	
	
	
	
}
