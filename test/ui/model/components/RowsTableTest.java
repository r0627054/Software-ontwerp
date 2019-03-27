package ui.model.components;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

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
		rt = new RowsTable(x, y, id);
		assertAll(
				() -> assertEquals(this.x, rt.getX()),
				() -> assertEquals(this.y, rt.getY()),
				() -> assertEquals(id , rt.getId())
				);
	}
	
	/**
	 * Test 2 : create a table within the rowTable
	 * | 
	 */

	@Test
	void test2CreateTabelWithinRowsTableWithValidParameters() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		// create table with 2 columns

		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);
		assertAll(
				() -> assertEquals(ccmId1, cells.get(0).getId()), 
				() -> assertEquals(ccmId2, cells.get(1).getId())
				);
	}
	
	/**
	 * Test 3 : Click Twice Outside Table
	 * |
	 */
	@Test
	void Test3TestClickOutsideTheRowsTable() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);
		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, String.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);
		int hashCode = rt.hashCode();
		
		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x-5, rt.getOffsetY() + rt.getHeight(),2); // click outside the table twice
		
		assertEquals(hashCode, rt.hashCode());
		
	}
	
	/**
	 * Test 3 : Click inside the table twice
	 * | call propertyChanged
	 * 
	 */
	@Test
	void Test4TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		columnCellsMap.put(id1, "test");
		columnCellsMap.put(id2, true);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		
		
		columnTypes.put(id1, String.class);columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);
		int hashCode = rt.hashCode();
		
		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x-5, 5, 2); // click inside the table twice	
	}
	
	/**
	 * Test 5 : Click inside the table once
	 */
	@Test
	void Test5TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);
		int hashCode = rt.hashCode();
		
		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x-5, 5, 1); // click inside the table once
		
		assertEquals(hashCode, rt.hashCode());
		
	}
	
	/**
	 * Test 6 : Mouse action other than click e.g. dragged
	 * | should not do anything
	 */
	@Test
	void Test6TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);
		int hashCode = rt.hashCode();
		
		rt.outsideClick(MouseEvent.MOUSE_DRAGGED, x-5, 5, 1); // click inside the table once
		
		assertEquals(hashCode, rt.hashCode());
		
	}
	
	/**
	 * Test 7 : Mouse click within table
	 * | 
	 * | should not do anything
	 */
	@Test
	void Test7TestOutsideTheRowsTableCoordsInsideTable() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x-5, 70, 1);
	}
	
	/**
	 * Test 8 : Mouse clicked
	 * | should reset delete cells list
	 */
	@Test
	void test8clickWithMouse() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);	

		rt.mouseClicked(0, 0, 0, 0);
	}
	
	
	/**
	 * Test 9 : get cell at certain columnindex
	 */
	@Test
	void test9getCellAtCertainColumnIndex() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x-5, 70, 1);

		assertTrue(rt.getCell(0, id1) instanceof UICell);
		
	}
	
	/**
	 * Test 10 : get columnheader at certain columnindex
	 */
	@Test
	void test10getCellAtCertainColumnIndex() {
		rt = new RowsTable(x, y, id);
		Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> values = new HashMap<>();
		Map<UUID, String> columnIdMap = new HashMap<>();
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();
		columnIdMap.put(id1, "columnName1");
		columnIdMap.put(id2, "columnName2");
		
		LinkedHashMap<UUID, Object> columnCellsMap = new LinkedHashMap<>(); // linked orde blijft
		
		UUID ccmId1 = UUID.randomUUID();
		columnCellsMap.put(ccmId1, "");
		UUID ccmId2 = UUID.randomUUID(); 
		columnCellsMap.put(ccmId2, false);

		 
		Map<UUID, Class<?>> columnTypes = new HashMap<>();
		columnTypes.put(id1, String.class);
		columnTypes.put(id2, Boolean.class);
		
		values.put(columnIdMap, columnCellsMap);
		cells = rt.createTable(values, columnTypes);

		rt.outsideClick(MouseEvent.MOUSE_CLICKED, x-5, 70, 1);
		
	}
	
}
	