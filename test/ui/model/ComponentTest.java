package ui.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import controller.handlers.ChangeEventType;
import ui.model.components.Component;
import ui.model.components.UICell;

class ComponentTest { // testing component methods with cell;
	private Component comp;
	private UUID id = UUID.randomUUID();
	private int correctX = 100;
	private int correctY = 50;
	private Component correctComponent = new UICell("test", id, ChangeEventType.ROW_EDITED, null, null);

	/**
	 * Test 1 : The offset of x should be greater or equal to the x coordinate.
	 */
	@Test
	void test1GetOffsetX() {
		assertTrue(correctComponent.getOffsetX() >= this.correctX);// width is 100. x is 0 by default.
	}

	/**
	 * Test 2 : The offset of y should be greater or equal to the y coordinate.
	 */
	@Test
	void test2GetOffsetY() {
		assertTrue(correctComponent.getOffsetY() >= this.correctY);
	}

	/**
	 * Test 3 : The width cannot be negative
	 */
	@Test
	void test3GetWidthNotNegative() {
		assertTrue(correctComponent.getWidth() > 0);
	}

	/**
	 * Test 4 : The height cannot be negative
	 */
	@Test
	void test4GetHeightNotNegative() {
		assertTrue(correctComponent.getHeight() > 0);
	}

	/**
	 * Test 5 : +1 on each coordinate should be within the component.
	 */
	@Test
	void test5IsWithinComponent() {
		assertTrue(correctComponent.isWithinComponent(correctComponent.getX() + 1, correctComponent.getY() + 1));
	}

}
