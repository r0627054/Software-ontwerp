package domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class of columns, containing a name, type and if the column allows blanks.
 *
 * @version 1.0
 * @author Dries Janse
 */
public class Column extends ObjectIdentifier {

	/**
	 * Variable storing the name of the column.
	 */
	private String name;

	/**
	 * Variable storing the type of the column.
	 */
	private ValueType type;

	/**
	 * Variable storing if the Column allows to store Blank values.
	 */
	private boolean allowsBlanks;

	/**
	 * Variable storing all the cells.
	 */
	private List<DomainCell> cells = new ArrayList<>();

	/**
	 * Variable storing the default value of the column. Initialised to the default
	 * value of the column.
	 */
	private Object defaultValue;

	/**
	 * Initialise a new column with the given name and the valueType equals ValueType.STRING.
	 * 
	 * @param columnName
	 *        The name of the column.
	 * @effect The name and the valueType (==ValueType.STRING) are set.
	 *         | this(columnName, ValueType.STRING);
	 */
	public Column(String columnName) {
		this(columnName, ValueType.STRING);
	}

	/**
	 * Initialise a new column with the given name and valueType and by default it
	 * allows blank spaces.
	 *
	 * @param name
	 * 			The name of the column.
	 * @param type
	 * 			The column type of the column.
	 * @effect The name, the type, allowblanks (default=true) and default value (default=type.getDefaultValue()) are set.
	 *         | this(name,type,true)
	 */
	public Column(String name, ValueType type) {
		this(name, type, true);
	}

	/**
	 * Initialise a new column with the given name, valuetype and allowsblanks
	 * variable.
	 *
	 * @param name
	 * 			The name of the column.
	 * @param type
	 * 			The column type of the column.
	 * @param allowsBlanks
	 * 			Whether the column allows blank spaces.
	 * @effect The name, the type, allowBlanks and default value (default=type.getDefaultValue()) are are set.
	 *         |this.setName(name)
	 *         |this.setType(type)
	 *	       |this.setAllowsBlanks(allowsBlanks)
	 *	       |this.setDefaultValue(type.getDefaultValue())
	 */
	public Column(String name, ValueType type, boolean allowsBlanks) {
		this.setName(name);
		this.setType(type);
		this.setAllowsBlanks(allowsBlanks);
		this.setDefaultValue(type.getDefaultValue());
	}

	/**
	 * Returns the name of the column.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the column with the given value.
	 *
	 * @param name
	 * 			The name of the column.
	 * @throws DomainException The name equals null.
	 *   		| name == null
	 * @post The name of the column equals the given name.
	 * 			| new.getName().equals(name)
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new DomainException("The column name cannot be empty.");
		}
		this.name = name;
	}

	/**
	 * Returns the type of the column.
	 */
	public ValueType getType() {
		return type;
	}

	/**
	 * Sets the type of the column with the given value.
	 *
	 * @param type
	 * 			The type of the column.
	 * @throws DomainException The ValueType equals null.
	 * 			| type == null
	 * @post The type of the column equals the given type.
	 * 			| new.getType.equals(type)
	 */
	private void setType(ValueType type) {
		if (type == null) {
			throw new DomainException("Invalid column type for the column.");
		}
		this.type = type;
	}

	/**
	 * Updates the type of the column. It checks whether the default value and all the cells
	 * of the table can be casted to this new type.
	 * 
	 * @param type
	 *        The type of the column
	 * @effect The type is set for the table
	 *         | this.setType(type)
	 * @throws DomainException the type is null or when the default value cannot be casted or when a cell in the column cannot be casted.
	 * 			| type == null || !canBeCastedTo(getDefaultValue(), type)
	 *          |
	 * 			| for (Cell c : getCells()) {
	 *			|	if (!canBeCastedTo(c.getValue(), type))
	 */
	public void updateType(ValueType type) {
		if (type == null) {
			throw new DomainException("Invalid column type for the column.");
		}
		if (!type.canHaveAsValue(getDefaultValue())) {
			throw new DomainException("Default value cannot be cast to the new type.");
		}
		for (DomainCell c : getCells()) {
			if (!type.canHaveAsValue(c.getValue())) {
				throw new DomainException("Cell value cannot be cast to the new type.");
			}
		}
		this.setType(type);
		for (DomainCell c : getCells()) {
			c.setType(type);
		}
		this.setDefaultValue(type.castTo(getDefaultValue()));
	}

	/**
	 * Returns a boolean whether the column allows blank values.
	 */
	public boolean isAllowsBlanks() {
		return allowsBlanks;
	}

	/**
	 *
	 * Sets the allowsBlanks variable. If the variable equals true, the column
	 * allows blank values. Otherwise the column does not allow blank values.
	 *
	 * @param allowsBlanks 
	 *        | Whether the column allows blank spaces.
	 * @post The allowsBlanks Variable is set, with the given value.
	 *        | new.getAllowsBlanks == allowsBlanks
	 * @throws DomainException when the default value equals null and the new allowsBlanks equals false and the currentAllowBlanks equals true.
	 *        | (this.getDefaultValue() == null) && !allowsBlanks && (this.isAllowsBlanks()) 
	 */
	public void setAllowsBlanks(boolean allowsBlanks) {
		if ((this.getDefaultValue() == null) && !allowsBlanks && (this.isAllowsBlanks())) { // disallow blanks but blank
																							// was set throws error.
			throw new DomainException("Default value is still empty.");
		}
		this.allowsBlanks = allowsBlanks;
	}

	/**
	 * Returns the default of the column type.
	 */
	public Object getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Sets a default value for the new cells.
	 *
	 * @param value
	 * 			The new default value.
	 * @throws DomainException The value equals null when blanks aren't allowed.
	 * 			| !allowBlanks && value == null
	 * @throws DomainException The value is of a wrong type when it is not empty.
	 * 			| !type.canHaveAsValue(value) && value != null
	 * @post The new default value is the value passed.
	 * 			| new.getDefaultValue().equals(value)
	 */
	public void setDefaultValue(Object value) {
		if (value == null && !this.isAllowsBlanks()) {
			throw new DomainException("Blanks are not allowed as default value.");
		}
		if (!getType().canHaveAsValue(value) && value != null) {
			throw new DomainException("Invalid default column value.");
		}
		this.defaultValue = value;
	}

	/**
	 * Returns a copy of the ArrayList of cells.
	 *
	 * @return A copy of the ArrayList of cells.
	 * 			| new ArrayList<>(this.cells)
	 */
	public List<DomainCell> getCells() {
		return new ArrayList<>(this.cells);
	}

	/**
	 * Sets an ArrayList of cells for the cells.
	 *
	 * @param cells
	 * 		   An ArrayList which contains Cells.
	 * @throws DomainException The cells equals null.
	 * 			| cells == null
	 * @post The ArrayList of cells equals the given cells
	 * 			| new.getCells().equals(cells)
	 */
	private void setCells(List<DomainCell> cells) {
		if (cells == null) {
			throw new DomainException("The List of cells cannot be null for a column.");
		}
		this.cells = cells;
	}

	/**
	 * Adds a cell at the bottom of the column.
	 *
	 * @param cell
	 * 			The cell that needs to be added to the column.
	 * @throws DomainException The cell equals null or the cell is of a different type than the expected type.
	 * 			| cells == null || !cell.getType().equals(this.getType())
	 * @effect The cell is added to the list of cells.
	 * 			| cells.add(cell)
	 */
	public void addCell(DomainCell cell) {
		if (cell == null) {
			throw new DomainException("A cell cannot be null in a column.");
		}
		if (!cell.getType().equals(this.getType())) {
			throw new DomainException("The type of the cell does not equals the column type.");
		}
		this.cells.add(cell);
	}

	/**
	 * Adds at the bottom of the column.
	 *
	 * @param cells
	 * 			The list of cells that needs to be added to the column.
	 * @throws DomainException The list of cells equals null or any of the cells is of a different type than the expected type.
	 * 			| cells == null
	 * @effect The cells are added to the list of cells.
	 * 			| cells.addAll(cells)
	 */
	public void addCells(List<DomainCell> cells) {
		for (DomainCell cell : cells) {
			if (cell == null) {
				throw new DomainException("A cell cannot be null in a column.");
			}
			if (!cell.getType().equals(this.getType())) {
				throw new DomainException("The type of the cell does not equals the column type.");
			}
			this.cells.add(cell);
		}
	}

	/**
	 * Returns the cell at the requested index of the column.
	 *
	 * @param index
	 * 			| The index in of which the Cell is positioned.
	 * @return The cell at the requested index of the column.
	 * 			| this.cells.get(index)
	 */
	public DomainCell getCellAtIndex(int index) {
		return this.cells.get(index);
	}

	/**
	 * Returns all the cells in a linkedHashMap;
	 * as key the UUID Of the cell, and the value is the value of the cell.
	 * 
	 * @return LinkedHashMap<UUID, Object> with UUID is the id of the cell and the value is the value of the cell.
	 */
	public LinkedHashMap<UUID, Object> getCellsWithId() {
		LinkedHashMap<UUID, Object> columnMap = new LinkedHashMap<>();

		for (DomainCell c : getCells()) {
			columnMap.put(c.getId(), c.getValue());
		}
		return columnMap;
	}

	/**
	 * Returns a map, the map only contains one entry.
	 * The key is the UUID of the column and the value is the name of the column.
	 * @return Map<UUID, String> with UUID is the id of the column and the String is the column name.
	 */
	public Map<UUID, String> getNameWithId() {
		Map<UUID, String> singlePairMap = new HashMap<UUID, String>();
		singlePairMap.put(this.getId(), this.getName());
		return singlePairMap;
	}

	/**
	 * Returns the characteristics of the column.
	 * This is a linkedHashMap<String, Object>; the keys are the variables of a column, the values are the corresponding values.
	 * 
	 */
	public LinkedHashMap<String, Object> getCharacteristics() {
		LinkedHashMap<String, Object> characteristics = new LinkedHashMap<>();

		characteristics.put("Column Name", getName());
		characteristics.put("Type", getType().toString());
		characteristics.put("Allow Blanks", new Boolean(isAllowsBlanks()));
		characteristics.put("Default Value", getDefaultValue());

		return characteristics;
	}

	/**
	 * Returns the position (index) where the specific characteristic is saved.
	 * If the characteristic isn't found it returns -1.
	 * 
	 * @param characteristic 
	 *        | The characteristic of the column.
	 * @return The corresponding index of the characteristic.
	 */
	public int getIndexOfCharacteristic(String characteristic) {
		LinkedHashMap<String, Object> list = getCharacteristics();

		int index = 0;

		for (String s : list.keySet()) {
			if (s.equals(characteristic)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	/**
	 * Updates the allowBlanks variable.
	 * This functions first checks whether the given value can be set.
	 * If the new update Allow Blanks is invalid a DomainException is thrown.
	 * 
	 * @param newBool the boolean to what allowBlanks will be set.
	 * @effect The allowBlanks is set to the new variable.
	 *       | this.setAllowsBlanks(newBool)
	 * @throws DomainException if the new value equals false and at least one of the values of a cell equals null
	 *         | if(!newBool) 
	 *         |   for (Cell c : getCells()) 
	 *         |       if (c.getValue() == null) 
	 * @throws DomainException if the new value equals false and the default value equals null
	 *                         or the default value equals an empty String.
	 *         | if(!newBool && (this.getDefaultValue() == null || this.getDefaultValue().equals("")) )
	 */
	public void updateAllowBlanks(boolean newBool) {
		if (!newBool) {
			if (this.getDefaultValue() == null || this.getDefaultValue().equals("")) {
				throw new DomainException("Default value isnt blank.");
			}
			for (DomainCell c : getCells()) {
				if (c.getValue() == null) {
					throw new DomainException("Cell isn't null.");
				}
			}
		}
		this.setAllowsBlanks(newBool);

	}

	/**
	 * Updates the default value with a new default value.
	 * The given new DefaultValue is casted to the correct type.
	 * 
	 * @param newDefaultValue
	 *        | the new default value.
	 * @post the new default value is set 
	 *        | this.setDefaultValue(newDefaultValue)
	 * @throws DomainException if the currentAllowBlanks  equals false and the newDefaultValue equals null.
	 *        | !this.isAllowsBlanks() && newDefaultValue == null
	 * @throws DomainException if the new default value cannot be casted to the ttpe of the column.
	 *        | !canBeCastedTo(newDefaultValue, this.getType())
	 * 
	 */
	public void updateDefaultValue(Object newDefaultValue) {
		if (!this.isAllowsBlanks() && newDefaultValue == null) {
			throw new DomainException("Default value is still blank");
		}
		if (!this.getType().canHaveAsValue(newDefaultValue)) {
			throw new DomainException("Default value cannot be changed to the columnType");
		}

		if (newDefaultValue != null && newDefaultValue instanceof String) {
			String defaultValueString = (String) newDefaultValue;
			if (defaultValueString.isEmpty()
					&& (this.getType().equals(ValueType.STRING) || this.getType().equals(ValueType.EMAIL))) {
				setDefaultValue(null);
			} else if (this.getType().equals(ValueType.INTEGER)) {
				this.setDefaultValue(Integer.parseInt(defaultValueString));
			} else {
				setDefaultValue(newDefaultValue);
			}
		} else {
			setDefaultValue(newDefaultValue);
		}

	}

	/**
	 * Returns whether or not the column contains a cell with the given id.
	 * 
	 * @param cellId the id of a cell.
	 * @return true if the table contains a cell with the given Id, false otherwise.
	 */
	public boolean containsCell(UUID cellId) {
		for (int i = 0; i < this.getCells().size(); i++) {
			DomainCell cell = this.getCellAtIndex(i);
			if (cell.getId().equals(cellId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * If the column contains a cell with the given id.
	 * The value of that cell is set to the newValue.
	 * 
	 * @param cellId
	 *        The id of a cell in the column.
	 * @param newValue
	 *        The new value the cell will get.
	 * @throws DomainException the cell in the column equals null.
	 *        | cell == null
	 */
	public void updateCellValue(UUID cellId, Object newValue) {
		DomainCell cell = this.getCellWithId(cellId);
		if (cell == null) {
			throw new DomainException("No cell found for cellId.");
		} else if (newValue == null && !this.allowsBlanks) {
			throw new DomainException("Null values are not allowed");
		} else if (String.valueOf(newValue).isEmpty() && !this.allowsBlanks) {
			throw new DomainException("Empty values are not allowed");
		}

		cell.setValue(newValue);
	}

	/**
	 * Returns the cell in the column with the given Id.
	 * If the column does not contain a cell with the given Id, then it will return null.
	 * 
	 * @param cellId
	 *        The id of a cell in the column.
	 * @return Cell with the given id in the column.
	 */
	private DomainCell getCellWithId(UUID cellId) {
		for (DomainCell c : getCells()) {
			if (c.getId().equals(cellId))
				return c;
		}
		return null;
	}

	/**
	 * Returns the index of the cell with the given id.
	 * 
	 * @param cellId
	 *        The id of the cell in the column.
	 * @return the index of cell with the given UUID in the column.
	 */
	public int getIndexOfCell(UUID cellId) {
		return this.getCells().indexOf(getCellWithId(cellId));
	}

	/**
	 * Deletes the cell on the given index of the column.
	 * 
	 * @param rowIndex the index of the cell which will be deleted.
	 * @effect the cell on the given index is removed.
	 *         | this.cells.remove(rowIndex)
	 */
	public void deleteCell(int rowIndex) {
		List<DomainCell> cells = this.getCells();
		cells.remove(rowIndex);
		this.setCells(cells);
	}

}
