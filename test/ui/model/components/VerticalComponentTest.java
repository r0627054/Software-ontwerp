package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import sun.net.www.content.audio.x_aiff;

class VerticalComponentTest {
	private VerticalComponentList vc;
	private int x = 20;
	private int y = 10;
	private int width = 90;
	private int height = 50;
	private List<Component> listItems = new ArrayList<>();
	private Component comp1 = new CheckBox(false, UUID.randomUUID());
	private Component comp2 = new EditableTextField("teststring", UUID.randomUUID());
	private Component comp3 = new CheckBox(true, UUID.randomUUID());
	
	
	
	
	/**
	 * Test 1 : Constructor init
	 * | get width and height should return dimensions of max children.
	 * | With an empty list those dimensions should be zero.
	 */

	@Test
	void test1ConstructorWithInitParams() {
	vc = new VerticalComponentList(x, y, width, height); 
	assertTrue(x == vc.getX() && y == vc.getY() && 0 == vc.getWidth() && 0 == vc.getHeight());
	}
	
	
	/**
	 * Test 2 : Constructor with list of components.
	 * | width and height should be the largest dimensions.
	 */
	@Test
	void test2ConstructorWithList() {
		comp1.setHeight(500);
		listItems.add(comp1);
		comp3.setWidth(678);
		listItems.add(comp2);
		listItems.add(comp3);
		vc = new VerticalComponentList(x, y, width, height, listItems);
		assertTrue(this.x == vc.getX() && this.y == vc.getY() && 678 == vc.getWidth() && (comp1.getHeight() + comp2.getHeight() + comp3.getHeight()) == vc.getHeight());
	}
	
	/**
	 * Test 2 : 
	 */
	@Test
	void test3ConstructorWithNegativeCoords() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> vc = new VerticalComponentList(-1, y, width, height));
		assertEquals("The x-coordinate of the component cannot be negative.", e.getMessage());
	}

}
