package ui.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;

class ContainerTest {
	private Container cont;
	private int x = 20;
	private int y = 10;
	private int width = 90;
	private int height = 50;
	private List<Component> listItems = new ArrayList<>();
	private Component comp1 = new CheckBox(false, UUID.randomUUID());
	private Component comp2 = new EditableTextField("teststring", UUID.randomUUID());
	private Component comp3 = new CheckBox(true, UUID.randomUUID());
	private Container correctContainer = new Container(x, y, width, height);

	
	
	/**
	 * Test 1 : Basic contructor
	 * | dimensions are configured by the parameters.
	 */	
	@Test
	void test1CreateContainerWithBasicParameters() {
		cont = new Container(x, y, width, height);
		assertTrue(this.width == cont.getWidth() && this.height == cont.getHeight());
	}
	
	/**
	 * Test 2 : Constructor with defined list
	 * | should return the correct component list
	 */
	@Test
	void test2CreateContainerWithComponentList() {
		listItems.add(comp1);
		listItems.add(comp2);
		listItems.add(comp3);
		cont = new Container(x, y, width, height, listItems);
		assertEquals(listItems.hashCode(), cont.getComponentsList().hashCode());
	}
	
	/**
	 * Test 3 : Constructor with null list
	 * | should throw an exception.
	 */
	@Test
	void test3CreateContainerWithNullComponentList() {
		Exception e = assertThrows(IllegalArgumentException.class, () ->  cont = new Container(x, y, width, height, null));
		assertEquals("ListItems can not be null", e.getMessage());
	}
	
	/**
	 * Test 4 : Add component to container
	 * | container should hold an component.
	 */
	@Test
	void test4AddComponentToContainer() {
		correctContainer.addComponent(comp1);
		assertTrue(correctContainer.getComponentsList().contains(comp1));
	}
	
	/**
	 * Test 5 : Add null component to container
	 * | should throw an IllegalArgumentException
	 */
	@Test
	void test5AddNullComponentToContainer() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> correctContainer.addComponent(null));
		assertEquals("Null component cannot be added to a container", e.getMessage());
	}
	
	
	/**
	 * Test 6 : Throw an error given the uuid.
	 */
	@Test
	void test6ThrowErrorWithUUID() {
		correctContainer.addComponent(comp1);
		correctContainer.throwError(UUID.randomUUID());
	}
	
	/**
	 * Test 7 : Return the offset of X
	 */
	@Test
	void test7GetOffsetXOfContainerComponent() {
		listItems.add(comp1);
		listItems.add(comp2);
		listItems.add(comp3);
		cont = new Container(x, y, width, height, listItems);
		assertEquals(this.comp1.getWidth() + this.comp2.getWidth() + this.comp3.getWidth() + cont.getX(), cont.getOffsetX());
	}
	
	/**
	 * Test 8 : Return the offset of Y
	 */
	@Test
	void test8GetOffsetYOfContainerComponent() {
		listItems.add(comp1);
		listItems.add(comp2);
		listItems.add(comp3);
		cont = new Container(x, y, width, height, listItems);
		assertEquals(this.comp1.getHeight() + this.comp2.getHeight() + this.comp3.getHeight() + cont.getY(), cont.getOffsetY());
	}

}
