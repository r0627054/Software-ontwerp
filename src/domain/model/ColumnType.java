package domain.model;

/**
 * An enumeration introducing the different types a Column can have.
 *   In its current form, the class only supports, Email, String, Boolean and Integer.
 * 
 * @version 1.0
 * @author Dries Janse
 */
public enum ColumnType {

	EMAIL("", String.class),
	STRING("", String.class),
	BOOLEAN(new Boolean(true),Boolean.class),
	INTEGER(new Integer(0),Integer.class);
	
	/**
	 * Variable storing the default value.
	 */
	private Object defaultValue;
	
	/**
	 * Variable storing the Class of the type.
	 */
	private Class cl;
	
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
	private ColumnType(Object defaultValue, Class cl) {
		this.defaultValue = defaultValue;
		this.cl = cl;
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
	 * Returns the class Object of the ColumnType.
	 */
	public Class getTypeClass() {
		return this.cl;
	}
	
	/**
	 * Returns the default value of the ColumnType.
	 */
	public Object getDefaultValue() {
		return this.defaultValue;	
	}
	
}
