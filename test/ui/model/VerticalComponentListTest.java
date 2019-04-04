package ui.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.VerticalComponentList;


class VerticalComponentListTest {
	private VerticalComponentList vc;
	private int x = 20;
	private int y = 10;
	private int width = 90;
	private int height = 50;
	private List<Component> listItems = new ArrayList<>();
	private Component comp1 = new CheckBox(false, UUID.randomUUID());
	private Component comp2 = new EditableTextField("teststring", UUID.randomUUID());
	private Component comp3 = new CheckBox(true, UUID.randomUUID());
	private Component comp4 = new CheckBox(0, 0, this.width, 500, false, UUID.randomUUID());
	private Component comp5 = new CheckBox(0,0, 678, this.height, false, UUID.randomUUID());
	
	
	
	
	/**
	 * Test 1 : Constructor init
	 * | get width and height should return dimensions of max children.
	 * | With an empty list those dimensions should be zero.
	 */

	@Test
	void test1ConstructorWithInitParams() {
	vc = new VerticalComponentList(x, y, width, height); 
	assertAll(
			() -> assertTrue(x == vc.getX()),
			() -> assertTrue(y == vc.getY()),
			() -> assertTrue(width == vc.getWidth()),
			() -> assertTrue(height == vc.getHeight())
			);
	}
	
	
	/**
	 * Test 2 : Constructor with list of components.
	 * | width and height should be the largest dimensions.
	 */
	@Test
	void test2ConstructorWithList() {
	//	comp1.setHeight(500); this is now component 4
		listItems.add(comp1);
	//	comp3.setWidth(678); now component 5
		listItems.add(comp2);
		listItems.add(comp3);
		listItems.add(comp4);
		listItems.add(comp5);
		vc = new VerticalComponentList(x, y, listItems);
		assertEquals(comp5, vc.getComponentsList().get(4));
		assertTrue(this.x == vc.getX() && this.y == vc.getY() && 678 == vc.getWidth() && (comp1.getHeight() + comp2.getHeight() + comp3.getHeight()) + comp4.getHeight() + comp5.getHeight() == vc.getHeight());
	}
	
	/**
	 * Test 2 : 
	 */
	@Test
	void test3ConstructorWithNegativeCoords() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> vc = new VerticalComponentList(-1, y, width, height));
		assertEquals("The x-coordinate of the component cannot be negative.", e.getMessage());
	}
	
	/**
	 * Test 3 : Add component to verticalComponentList
	 * | the hashcode from the component should match the hashcode of the most recently added component
	 */
	@Test
	void test4AddComponentToList() {
		listItems.add(comp1);
		listItems.add(comp2);
		vc = new VerticalComponentList(x, y, listItems);
		vc.addComponent(comp3);
		assertEquals(comp3.hashCode(), vc.getComponentsList().get(vc.getComponentsList().size() -1).hashCode());
	}
	
	/**
	 * Test 4 : Add null component to the verticalComponetList
	 * | should throw an exception from the super container class
	 */
	@Test
	void test4AddNullComponentToList() {
		listItems.add(comp1);
		listItems.add(comp2);
		vc = new VerticalComponentList(x, y, listItems);
		Exception e = assertThrows(IllegalArgumentException.class, () -> vc.addComponent(null));
		assertEquals("Null component cannot be added to a container", e.getMessage());
	}
	
	/**
	 * Test 5 :
	 */
	@Test
	void test5GetOffsetX() {
		listItems.add(comp1);
		listItems.add(comp2);
		vc = new VerticalComponentList(x, y, listItems);
		assertEquals(vc.getX() + vc.getMaxWidthFromChildren(), vc.getOffsetX());
	}
}
