package ui.model.components;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ContainerListTest {
	private ContainerList contList;
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
	 * Test 1 : Basic constructor
	 */
	@Test
	void test1CreateContainerWithoutComponents() {
		contList = new ContainerList(x, y, width, height);
		assertTrue(this.width == contList.getWidth() && this.height == contList.getHeight());
	}
	
	/**
	 * Test 2 : Basic constructor
	 */
	@Test
	void test2CreateContainerlistWithComponents() {
		listItems.add(comp1);
		listItems.add(comp2);
		listItems.add(comp3);
		contList = new ContainerList(x, y, width, height,listItems);
		assertEquals(listItems.hashCode(), contList.getComponentsList().hashCode());
	}

}
