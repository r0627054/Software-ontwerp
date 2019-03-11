package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import domain.model.DomainException;
import domain.model.Row;



class ComponentTest { // testing component methods with cell;
	private Component comp;
	private UUID id = UUID.randomUUID();
	private int correctX = 100;
	private int correctY = 400;
	private Component correctComponent = new Cell(correctX, correctY, "test", id);

	/**
	 * Test 1 : component with given negative X
	 * | should throw an IllegalArgumentException.
	 */
	@Test
	void test1CreateComponentWithNegativeX() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> comp = new Cell(-3, 0, "test", id));
		assertEquals(e.getMessage(), "The x-coordinate of the component cannot be negative.");
	}
	
	/**
	 * Test 2 : component with given negative Y
	 * | should throw an IllegalArgumentException.
	 */
	@Test
	void test2CreateComponentWithNegativeY() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> comp = new Cell(0, -1, "test", id));
		assertEquals(e.getMessage(), "The y-coordinate of the component cannot be negative.");
	}
	
	/**
	 * Test 3 : The offset of x should be greater than the x coordinate.
	 */
	@Test
	void test3GetOffsetX() {
		assertTrue(correctComponent.getOffsetX() > this.correctX);
	}
	
	/**
	 * Test 4 : The offset of y should be greater than the y coordinate.
	 */
	@Test
	void test4GetOffsetY() {
		assertTrue(correctComponent.getOffsetY() > this.correctX);
	}
	
	/**
	 * Test 5 : The width cannot be negative
	 */
	@Test
	void test5GetWidthNotNegative() {
		assertTrue(correctComponent.getWidth() > 0);
	}
	
	/**
	 * Test 6 : The height cannot be negative
	 */
	@Test
	void test6GetHeightNotNegative() {
		assertTrue(correctComponent.getHeight() > 0);
	}
	
	/**
	 * Test 7 : +1 on each coordinate should be within the component.
	 */
	@Test
	void test7IsWithinComponent() {
		assertTrue(correctComponent.isWithinComponent(correctComponent.getX() + 1, correctComponent.getY() +1));
	}
	
}