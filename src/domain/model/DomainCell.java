package domain.model;

import java.util.UUID;

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
	 * Initialise a new DomainCell with a given id, value and type.
	 * This id will be set to the cell, and will not be calculated.
	 * 
	 * @param cellId
	 *        | The id for the cell.
	 * @param value
	 *        | The value for the cell.
	 * @param type
	 *        | The type the cell will have.
	 * @effect the type, id, value are set for the cell. 
	 *        | super(cellId);
	 *        |	this.setType(type);
	 *        | this.setValue(value);
	 */
	public DomainCell(UUID cellId, Object value, ValueType type) {
		super(cellId);
		this.setType(type);
		this.setValue(value);
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

	/**
	 * Checks whether this cell has the same value as the given cell.
	 * @param cell The cell to which the type will be checked.
	 * @return True when the types are the same; false otherwise.
	 */
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

	/**
	 * Checks whether the value of the cell is the same as the value given as parameter.
	 * @param obj 
	 *        | The value to which it will be checked.
	 * @return True when the value are the same; False otherwise.
	 */
	public boolean hasSameValueAs(Object obj) {
		try {
			return getType().haveSameValue(this.getValue(), obj);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the value of the DomainCell.
	 * @return The value of the DomainCell.
	 */
	@Override
	public String toString() {
		if (this.getValue() == null) {
			return "null";
		} else {
			return this.getValue().toString();
		}
	}

	/**
	 * Compares two cell which each other. It returns True when the cells could be compared with the given Operator.
	 * @param rightCell The cell to which it will compared.
	 * @param op  The parameter used to compare the cells.
	 * @return Return true when the compare could be executed and the result equals true of the Operator compare; Otherwise False.
	 * @effect the compare function is called using the value of the domainCell.
	 *         | this.compare(rightCell.getValue(), op);
	 */
	public boolean compare(DomainCell rightCell, Operator op) {
		return this.compare(rightCell.getValue(), op);
	}

	/**
	 * Compares the value of this Cell with the given value.
	 * 
	 * @param otherValue
	 *         The other value to which it should be compared.
	 * @param op
	 *         The operator to which it should be compared.
	 * @return  True when the operator expression resulted in true; otherwise False.
	 * @throws DomainException when the operator is not implemented.
	 */
	public boolean compare(Object otherValue, Operator op) {
		if((op == Operator.SMALLER || op == Operator.GREATER) && (this.getValue() == null || otherValue == null) ) {
			return false;
		}
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
