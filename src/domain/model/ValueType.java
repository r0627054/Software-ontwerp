package domain.model;

/**
 * An enumeration introducing the different types a Column can have.
 *   In its current form, the class only supports, Email, String, Boolean and Integer.
 * 
 * @version 1.0
 * @author Dries Janse
 */
public enum ValueType {

	EMAIL("email@", String.class, "Email"), STRING("", String.class, "String"),
	BOOLEAN(new Boolean(true), Boolean.class, "Boolean"), INTEGER(new Integer(0), Integer.class, "Integer");

	/**
	 * Variable storing the default value.
	 */
	private Object defaultValue;

	/**
	 * Variable storing the Class of the type.
	 */
	private Class<?> cl;

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
	private ValueType(Object defaultValue, Class<?> cl, String displayValue) {
		setDefaultValue(defaultValue);
		setDisplayValue(displayValue);
		setCl(cl);
	}

	private void setCl(Class<?> cl) {
		this.cl = cl;
	}

	private void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
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
		if (this.equals(EMAIL)) {
			if (value instanceof String) {
				String valueString = (String) value;
				return value.getClass().equals(this.getTypeClass())
						&& (valueString.indexOf("@") == valueString.lastIndexOf("@"));
			}
		}
		return (value == null || value.getClass().equals(this.getTypeClass()));
	}

	public static ValueType getValueTypeForString(String name) {
		for (ValueType type : ValueType.values()) {
			if (type.getDisplayValue().equals(name))
				return type;
		}
		return null;
	}

	/**
	 * Returns the class Object of the ValueType.
	 */
	public Class<?> getTypeClass() {
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

	private String getDisplayValue() {
		return displayValue;
	}

	private void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
