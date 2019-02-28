package domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of columns, containing a name, type and if the column allows blanks.
 *
 * @version 1.0
 * @author Dries Janse
 */
public class Column {

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
	private void setName(String name) {
		if (name == null) {
			throw new DomainException("The column name cannot be null.");
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
	 * @post The allowsblanks variable is set, with the given value. |
	 
	 *       new.getAllowsBlanks.equals(allowsBlanks)
	 */
	public void setAllowsBlanks(boolean allowsBlanks) {
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
		if (value == null && !this.allowsBlanks)
			throw new DomainException("Blanks are not allowed as default value.");
		if (!type.canHaveAsValue(value) && value != null)
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
	 * @throws DomainException The cell equals null of the cell is of a different type than the expected type.
	 * 			| cells == null
	 * @effect The cell is added to the list of cells.
	 * 			| cells.add(cell)
	 */
	public void addCell(Cell cell) {
		if (cell == null) {
			throw new DomainException("A cell cannot be null in a column.");
		}
		if (cell.getType().equals(this.getType())) {
			throw new DomainException("The type of the cell does not equals the column type.");
		}
		cells.add(cell);
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

}
