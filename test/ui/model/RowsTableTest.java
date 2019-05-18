package ui.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.RowsTable;
import ui.model.components.UICell;

class RowsTableTest {
	private int x = 10;
	private int y = 20;
	private UUID id = UUID.randomUUID();
	private RowsTable rt;
	List<UICell> cells;

	/**
	 * Test 1 : Basic Init Constructor
	 * | Creating RowsTable without any components
	 */

	@Test
	void test1CreateRowsTableWithoutComponents() {
		rt = new RowsTable(x, y, id, false);
		assertAll(() -> assertEquals(this.x, rt.getX()), () -> assertEquals(this.y, rt.getY()),
				() -> assertEquals(id, rt.getId()));
	}

	/**
	 * Test 2 : create a table within the rowTable
	 * | 
	 */

	@Test
	void test2CreateTabelWithinRowsTableWithValidParameters() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);
		assertTrue(cells.get(0).getId().equals(columnCell1[0]) || cells.get(0).getId().equals(columnCell1[1]));
		assertTrue(cells.get(1).getId().equals(columnCell1[1]) || cells.get(1).getId().equals(columnCell1[0]));
	}

	/**
	 * Test 3 : Click Twice Outside Table
	 * |
	 */
	@Test
	void Test3TestClickOutsideTheRowsTable() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);
		int hashCode = rt.hashCode();

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x - 5, rt.getOffsetY() + rt.getHeight(), 2); // click outside the
																								// table twice

		assertEquals(hashCode, rt.hashCode());

	}

	/**
	 * Test 3 : Click inside the table twice
	 * | call propertyChanged
	 * 
	 */
	@Test
	void Test4TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);
		int hashCode = rt.hashCode();

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x - 5, 5, 2); // click inside the table twice
	}

	/**
	 * Test 5 : Click inside the table once
	 */
	@Test
	void Test5TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);
		int hashCode = rt.hashCode();

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x - 5, 5, 1); // click inside the table once

		assertEquals(hashCode, rt.hashCode());

	}

	/**
	 * Test 6 : Mouse action other than click e.g. dragged
	 * | should not do anything
	 */
	@Test
	void Test6TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);
		int hashCode = rt.hashCode();

		rt.outsideClick(MouseEvent.MOUSE_DRAGGED, x - 5, 5, 1); // click inside the table once

		assertEquals(hashCode, rt.hashCode());

	}

	/**
	 * Test 7 : Mouse click within table
	 * | 
	 * | should not do anything
	 */
	@Test
	void Test7TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x - 5, 70, 1);
	}

	/**
	 * Test 8 : Mouse clicked
	 * | should reset delete cells list
	 */
	@Test
	void test8clickWithMouse() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);

		rt.mouseClicked(0, 0, 0, 0);
	}

	/**
	 * Test 9 : get cell at certain columnindex
	 */
	@Test
	void test9getCellAtCertainColumnIndex() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x - 5, 70, 1);

		assertTrue(rt.getCell(0, id1) instanceof UICell);

	}

	/**
	 * Test 10 : get columnheader at certain columnindex
	 */
	@Test
	void test10getCellAtCertainColumnIndex() {
		rt = new RowsTable(x, y, id, false);
		Map<List<Object>, List<Object[]>> values = new HashMap<>();
		// create table with 2 columns

		List<Object> rowData1 = new LinkedList<Object>();
		UUID id1 = UUID.randomUUID();
		rowData1.add(id1);
		rowData1.add("columnName1");
		rowData1.add(String.class);

		List<Object> rowData2 = new LinkedList<Object>();
		UUID id2 = UUID.randomUUID();
		rowData2.add(id2);
		rowData2.add("columnName2");
		rowData2.add(Boolean.class);

		Object[] columnCell1 = { UUID.randomUUID(), "" };
		Object[] columnCell2 = { UUID.randomUUID(), true };

		List<Object[]> list1 = new ArrayList<>();
		list1.add(columnCell1);

		List<Object[]> list2 = new ArrayList<>();
		list1.add(columnCell2);

		values.put(rowData1, list1);
		values.put(rowData2, list2);

		cells = rt.createTable(values);

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x - 5, 70, 1);

	}

}
