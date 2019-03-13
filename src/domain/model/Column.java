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
	private List<Cell> cells = new ArrayList<>();

	/**
	 * Variable storing the default value of the column. Initialised to the default
	 * value of the valuetype.
	 */
	private Object defaultValue;

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
	 * @effect The name, the type, allowblanks and default value (default=type.getDefaultValue()) are are set.
	 *         | this(name,type,true)
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
	 * @throws DomainException The type equals null.
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

	public void updateType(ValueType type) {
		if (type == null) {
			throw new DomainException("Invalid column type for the column.");
		}
		if (!canBeCastedTo(getDefaultValue(), type)) {
			throw new DomainException("Default value cannot be cast to the new type.");
		}
		for (Cell c : getCells()) {
			if (!canBeCastedTo(c.getValue(), type)) {
				throw new DomainException("Cell value cannot be cast to the new type.");
			}
		}
		this.setType(type);
	}

	@SuppressWarnings("unused")
	private boolean canBeCastedTo(Object value, ValueType castType) {
		try {
			if (castType.equals(ValueType.BOOLEAN)) {
				Boolean casted = (Boolean) value;
				return true;
			} else if (castType.equals(ValueType.EMAIL)) {
				String casted = (String) value;
				return casted.contains("@");
			} else if (castType.equals(ValueType.STRING)) {
				String casted = (String) value;
				return true;
			} else if (castType.equals(ValueType.INTEGER)) {
				Integer casted = (Integer) value;
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
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
	 * @param allowsBlanks Whether the column allows blank spaces.
	 * @post The allowsBlanks variable is set, with the given value. |
	 
	 *       new.getAllowsBlanks.equals(allowsBlanks)
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
	 *
	 * @return Object The default value for the column type.
	 * 			| this.getType().getDefaultValue()
	 */
	public Object getDefaultValue() { // TODO: clone?
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
		if (value == null && !this.allowsBlanks) {
			System.out.println(value);
			throw new DomainException("Blanks are not allowed as default value.");
		}
		if (!getType().canHaveAsValue(value) && value != null)
			throw new DomainException("Invalid default column value.");
		this.defaultValue = value;
	}

	/**
	 * Returns a copy of the ArrayList of cells.
	 *
	 * @return A copy of the ArrayList of cells.
	 * 			| new ArrayList<>(this.cells)
	 */
	public List<Cell> getCells() {
		return new ArrayList<>(this.cells);
	}

	/**
	 * Sets an ArrayList of cells for the cells.
	 *
	 * @param cells
	 * 		An ArrayList which contains Cells.
	 * @throws DomainException The cells equals null.
	 * 			| cells == null
	 * @post The ArrayList of cells equals the given cells
	 * 			| new.getCells().equals(cells)
	 */
	private void setCells(List<Cell> cells) {
		if (cells == null) {
			throw new DomainException("The List of cells cannot be null for a column.");
		}
		this.cells = cells;
	}

	/**
	 * Adds at the bottom of the column.
	 *
	 * @param cell
	 * 			The cell that needs to be added to the column.
	 * @throws DomainException The cell equals null or the cell is of a different type than the expected type.
	 * 			| cells == null
	 * @effect The cell is added to the list of cells.
	 * 			| cells.add(cell)
	 */
	public void addCell(Cell cell) {
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
	public void addCells(List<Cell> cells) {
		for (Cell cell : cells) {
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
	public Cell getCellAtIndex(int index) {
		return this.cells.get(index);
	}

	public LinkedHashMap<UUID, Object> getCellsWithId() {
		LinkedHashMap<UUID, Object> columnMap = new LinkedHashMap<>();

		for (Cell c : getCells()) {
			columnMap.put(c.getId(), c.getValue());
		}
		return columnMap;
	}

	public Map<UUID, String> getNameWithId() {
		Map<UUID, String> singlePairMap = new HashMap<UUID, String>();
		singlePairMap.put(this.getId(), this.getName());
		return singlePairMap;
	}

	public LinkedHashMap<String, Object> getCharacteristics() {
		LinkedHashMap<String, Object> characteristics = new LinkedHashMap<>();

		characteristics.put("Column Name", getName());
		characteristics.put("Type", getType().toString());
		characteristics.put("Allow Blanks", new Boolean(isAllowsBlanks()));
		characteristics.put("Default Value", getDefaultValue());

		return characteristics;
	}

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

	public void updateAllowBlanks(boolean newBool) {
		if (!newBool) {
			if (this.getDefaultValue() == null || this.getDefaultValue().equals("")) {
				throw new DomainException("Default value isnt blank.");
			}
			for (Cell c : getCells()) {
				if (c.getValue() == null) {
					throw new DomainException("Cell isn't null.");
				}
			}
		}
		this.setAllowsBlanks(newBool);

	}

	public void updateDefaultValue(Object newDefaultValue) {
		if (!this.isAllowsBlanks() && newDefaultValue == null) {
			throw new DomainException("Default value is still blank");
		}
		if (!canBeCastedTo(newDefaultValue, this.getType())) {
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

	public boolean containsCell(UUID cellId) {
		for (int i = 0; i < this.getCells().size(); i++) {
			Cell cell = this.getCellAtIndex(i);
			if (cell.getId().equals(cellId)) {
				return true;
			}
		}
		return false;
	}

	public void updateCellValue(UUID cellId, Object newValue) {
		Cell cell = this.getCellWithId(cellId);
		if (cell == null)
			throw new DomainException("No cell found for cellId.");
		cell.setValue(newValue);
	}

	private Cell getCellWithId(UUID cellId) {
		for (Cell c : getCells()) {
			if (c.getId().equals(cellId))
				return c;
		}
		return null;
	}

	public int getIndexOfCell(UUID cellId) {
		return this.getCells().indexOf(getCellWithId(cellId));
	}

	public void deleteCell(int rowIndex) {
		List<Cell> cells = this.getCells();
		cells.remove(rowIndex);
		this.setCells(cells);
	}

}
