package domain.model;
/**
 * A class of a cell involving a columType and a value.
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public class Cell {

	/**
	 * Variable storing the type of the cell.
	 */
	private ColumnType type;
	/**
	 * Variable storing the value of the cell.
	 */
	private Object value;
	
	/**
	 * Initialize the new cell with given type and value.
	 * 
	 * @param type
	 *        The columntype of the cell.
	 * @param value
	 *        The value of the cell.
	 * @effect The type and the value of the cell are set.
	 *        |setType(type)
	 *        |setValue(value)
	 */
	public Cell(ColumnType type, Object value) {
		this.setType(type);
		this.setValue(value);
	}
	
	/**
	 * Initialize the new cell with given type and the default value of the type.
	 * 
	 * @param type
	 *        The columntype of the cell.
	 * @effect The type and the default value of that type is set.
	 *        | this(type, type.getDefaultValue())
	 */
	public Cell(ColumnType type) {
		this(type, type.getDefaultValue());
	}

	/**
	 * Returns the columnType of the cell.
	 */
	public ColumnType getType() {
		return type;
	}

	/**
	 * Sets the column type for the cell
	 * 
	 * @param type
	 *        The columntype of the cell.
	 * @throws DomainException
	 *         The type equals null.
	 *         | type == null
	 * @post The column type of the cell equals the given columnType.
	 *         | new.getType().equals(type)
	 */
	private void setType(ColumnType type) {
		if(type == null) {
			throw new DomainException("Invalid columntype for the cell.");
		}
		this.type = type;
	}

	/**
	 * Returns the value stored in the cell.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value of the cell.
	 * 
	 * @param value
	 *        The value of the cell.
	 * @post The value of the cell equals the given value.
	 *       | new.getValue().equals(value)
	 */
	private void setValue(Object value) {
		this.value = value;
	}
}
