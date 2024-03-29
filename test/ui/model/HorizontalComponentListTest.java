package ui.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import controller.handlers.ChangeEventType;
import ui.model.components.CheckBox;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.EditableTextField;
import ui.model.components.HorizontalComponentList;

class HorizontalComponentListTest {
	private HorizontalComponentList hcl;
	private int x = 20;
	private int y = 10;

	private List<Component> listItems = new ArrayList<>();
	private Component comp1 = new CheckBox(false, UUID.randomUUID(), ChangeEventType.REPAINT);
	private Component comp2 = new EditableTextField("teststring", UUID.randomUUID(), ChangeEventType.REPAINT, null,
			null);
	private Component comp3 = new CheckBox(true, UUID.randomUUID(), ChangeEventType.REPAINT);

	/**
	 * Test 1 : Basic constuctor init
	 * | given coords should be returned with the getters
	 */
	@Test
	void test1CreateHorizontalComponentList() {
		hcl = new HorizontalComponentList(x, y);
		assertTrue(this.x == hcl.getX() && this.y == hcl.getY());
	}

	/**
	 * Test 2 : Basic constructor with negative y value
	 * | should throw an exception.
	 */
	@Test
	void test2CreateHorizontalComponentListWithNegativeCoords() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> hcl = new HorizontalComponentList(0, -1));
		assertEquals("The y-coordinate of the component cannot be negative.", e.getMessage());
	}

	/**
	 * Test 3 : Basic constructor with dimensions
	 * | the dimensions correspond to the size of the biggest components
	 * | since there are none given and thus holds an empty componentList
	 * | the returned dimensions should be zero.
	 */
	@Test
	void test3CreateHorizontalComponentListWithDimensions() {
		hcl = new HorizontalComponentList(x, y);
		assertAll("Both dimensions should equal zeor", () -> assertEquals(0, hcl.getWidth()),
				() -> assertEquals(0, hcl.getHeight()));
	}

	/**
	 * Test 4 : Constructor with componentList
	 */
	@Test
	void test4CreateHorizontalComponentListWithGivenComponentList() {
		listItems.add(comp1);
		listItems.add(comp2);
		listItems.add(comp3);
		hcl = new HorizontalComponentList(x, y, listItems);
		assertTrue(comp1.getWidth() + comp2.getWidth() + comp3.getWidth() == hcl.getWidth());

	}

	/**
	 * Test 5 : Add component to Horizontal Component List
	 */
	@Test
	void test5AddComponentToComponentList() {
		listItems.add(comp1);
		listItems.add(comp2);
		hcl = new HorizontalComponentList(x, y, listItems);
		hcl.addComponent(comp3);
		assertEquals(comp3.hashCode(), hcl.getComponentsList().get(hcl.getComponentsList().size() - 1).hashCode());
	}

}
