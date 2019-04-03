package domain.model;

/**
 * A class of a cell involving a columType and a value.
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public class DomainCell extends ObjectIdentifier {

	/**
	 * Variable storing the type of the cell.
	 */
	private ValueType type;
	/**
	 * Variable storing the value of the cell.
	 */
	private Object value;

	/**
	 * Initialize the new cell with given type and value.
	 * 
	 * @param type  
	 * 			The valuetype of the cell.
	 * @param value 
	 * 			The value of the cell.
	 * @effect The type and the value of the cell are set. 
	 * 			|setType(type)
	 *          |setValue(value)
	 */
	public DomainCell(ValueType type, Object value) {
		this.setType(type);
		this.setValue(value);
	}

	/**
	 * Initialize the new cell with given type and the default value of the type.
	 * 
	 * @param type 
	 * 			The valuetype of the cell.
	 * @effect The type and the default value of that type is set. 
	 * 			| this(type, type.getDefaultValue())
	 */
	public DomainCell(ValueType type) {
		setType(type);
		setValue(type.getDefaultValue());
	}

	/**
	 * Returns the valuetype of the cell.
	 */
	public ValueType getType() {
		return type;
	}

	/**
	 * Sets the column type for the cell
	 * 
	 * @param type 
	 * 			The valuetype of the cell.
	 * @throws DomainException The type equals null. 
	 * 			| type == null
	 * @post The column type of the cell equals the given valuetype. 
	 * 			| new.getType().equals(type)
	 */
	public void setType(ValueType type) {
		if (type == null) {
			throw new DomainException("Invalid valuetype for the cell.");
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
	 * 			The value of the cell.
	 * @post The value of the cell equals the given value. 
	 * 			| new.getValue().equals(value)
	 */
	public void setValue(Object value) {
		if (!type.canHaveAsValue(value))
			throw new DomainException("Invalid value for this cell.");
		this.value = value;
	}
}
