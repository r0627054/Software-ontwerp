package domain.model;

/**
 * An enumeration introducing the different types a Column can have.
 *   In its current form, the class only supports, Email, String, Boolean and Integer.
 * 
 * @version 1.0
 * @author Dries Janse
 */
public enum ValueType {

	EMAIL("", String.class, "Type: Email"),
	STRING("", String.class, "Type: String"),
	BOOLEAN(new Boolean(true),Boolean.class, "Type: Bool"),
	INTEGER(new Integer(0),Integer.class, "Type: Int");
	
	/**
	 * Variable storing the default value.
	 */
	private Object defaultValue;
	
	/**
	 * Variable storing the Class of the type.
	 */
	private Class cl;
	
	private String displayValue;
	
	/**
	 * Initialise the type with the given default value and class object.
	 *  
	 * @param defaultValue
	 *        The default value of the type.
	 * @param cl
	 *        The default class object of the type.
	 * @post the defaultValue and class Object are set with the given values.
	 *        | new.getTypeClass() == cl
	 *        | new.getDefaultValue() == defaultValue
	 */
	private ValueType(Object defaultValue, Class cl, String displayValue) {
		this.defaultValue = defaultValue;
		this.cl = cl;
		this.displayValue = displayValue;
	}
	 
	/**
	 * Checks whether the given value is of the right type.
	 * 
	 * @param value
	 *        The value to be checked.
	 * @return True if the given value is valid; false otherwise
	 *         | result == (value != null) && (value.getClass().equals( this.getTypeClass()))
	 */
	public boolean canHaveAsValue(Object value) {
		if( (value != null) && (value.getClass().equals( this.getTypeClass())) ){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the class Object of the ValueType.
	 */
	public Class getTypeClass() {
		return this.cl;
	}
	
	/**
	 * Returns the default value of the ValueType.
	 */
	public Object getDefaultValue() {
		return this.defaultValue;	
	}
	
	@Override
	public String toString() {
		return displayValue;
	}
	
}
