package ui.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ui.model.components.CheckBox;

class CheckBoxTest {
	private CheckBox cb;
	private UUID id = UUID.randomUUID();
	private int correctX = 100;
	private int correctY = 400;
	


	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test 1 : Returning the checked value
	 */
	@Test
	void CreateCBAndReturnCheckedValue() {
		cb = new CheckBox(false, id);
		assertFalse(cb.isChecked());
	}
	
	/**
	 * Test 2 : Constructor with coords
	 * | getting coords from super class
	 */
	@Test
	void test2CreateCBWithCoords() {
		int x = 20;
	    int y = 40;
		cb = new CheckBox(x, y, true, id);
		assertTrue(x == cb.getX() && y == cb.getY() && cb.isChecked() == true && id == cb.getId());
		
	}
	
	@Test
	void test3CreateCBWithCoordsAndSizes() {
		int x = 30;
	    int y = 50;
	    int width = 100;
	    int height = 60;
		cb = new CheckBox(x, y, width, height, true, id);
		assertTrue(x == cb.getX() && y == cb.getY() && cb.getWidth() == width && height == cb.getHeight() && cb.isChecked() == true && id == cb.getId());
	}

}
