package domain.model;

import domain.model.sql.Operator;

/**
 * A class of a cell involving a columType and a value.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
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

	private boolean hasSameValueAsCell(DomainCell cell) {
		if (cell == null) {
			return false;
		}
		if (this.getType() != null && cell.getType().equals(this.getType())) {
			ValueType type = this.getType();
			try {
				return type.haveSameValue(this.getValue(), cell.getValue());
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public boolean hasSameValueAs(Object obj) {
		try {
			return getType().haveSameValue(this.getValue(), obj);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		if (this.getValue() == null) {
			return "null";
		} else {
			return this.getValue().toString();
		}
	}

	public boolean compare(DomainCell rightCell, Operator op) {
		return this.compare(rightCell.getValue(), op);
	}
	
	public boolean compare(Object otherValue, Operator op) {
		switch (op) {
		case EQUAL:
			return getType().haveSameValue(this.getValue(), otherValue);
		case GREATER:
			return getType().isGreaterThan(this.getValue(), otherValue);
		case SMALLER:
			return getType().isSmallerThan(this.getValue(), otherValue);
		default:
			throw new DomainException("Operator not implemented");
		}
	}
}
