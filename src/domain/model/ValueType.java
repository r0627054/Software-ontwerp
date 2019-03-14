package domain.model;

/**
 * An enumeration introducing the different types a Column can have.
 *   In its current form, the class only supports, Email, String, Boolean and Integer.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
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

	/**
	 * Variable storing the value String which can be displayed.
	 */
	private String displayValue;

	/**
	 * Initialise the type with the given default value, class object and displayValue.
	 *  
	 * @param defaultValue
	 *        The default value of the type.
	 * @param cl
	 *        The default class object of the type.
	 * @param displayValue
	 *        The value used to display.
	 * @effect the defaultValue, class Object and displayValue are set with the given values.
	 *        | setDefaultValue(defaultValue)
	 *        | setDisplayValue(displayValue)
	 *        | setCl(cl)
	 */
	private ValueType(Object defaultValue, Class<?> cl, String displayValue) {
		setDefaultValue(defaultValue);
		setDisplayValue(displayValue);
		setCl(cl);
	}

	/**
	 * Sets the class used for the valueType.
	 * @param cl
	 *        | The Class used for the value.
	 * @post The class is set with the given class.
	 *        | this.getTypeClass() == cl
	 */
	private void setCl(Class<?> cl) {
		this.cl = cl;
	}

	/**
	 * Sets the defaultValue of the valueType.
	 * @param defaultValue
	 *        | The default value of the valueType.
	 * @post The default value is set for the valueType
	 *       | this.getDefaultValue() == defaultValue
	 */
	private void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Checks whether value can be of the used in the valueType.
	 * 
	 * @param value
	 *        The value to be checked.
	 * @return True if the given value can be used with a valueType.
	 */
	public boolean canHaveAsValue(Object value) {
		if (value == null) {
			return true;
		}
		if (String.valueOf(value).isEmpty()) {
			return true;
		}
		try {
			if (this.equals(ValueType.STRING)) {
				value = String.valueOf(value);
				return value.getClass().equals(this.getTypeClass());

			} else if (this.equals(ValueType.EMAIL)) {
				String casted = (String) value;
				return (casted.indexOf("@") >= 0 && (casted.indexOf("@") == casted.lastIndexOf("@"))
						|| casted.isEmpty()) && casted.getClass().equals(this.getTypeClass());

			} else if (this.equals(ValueType.BOOLEAN)) {
				if (value instanceof String
						&& (String.valueOf(value).contains("true") || String.valueOf(value).contains("false"))) {
					value = Boolean.parseBoolean((String) value);
				}
				return value.getClass().equals(this.getTypeClass());

			} else if (this.equals(ValueType.INTEGER)) {
				if (!(value instanceof Integer)) {
					value = Integer.parseInt((String) value);
				}
				return value.getClass().equals(this.getTypeClass());
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns the valueType when a display value is entered as parameter.
	 * Null if parameter does not equal a default value.
	 * @param name
	 * @return the ValueType when name equals a displayValue of one of the valueTypes; otherwise null;
	 */
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

	/**
	 * Returns the display value of the valueType.
	 */
	@Override
	public String toString() {
		return displayValue;
	}

	/**
	 * Returns the display value of the valueType.
	 */
	private String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Sets the displayValue of the valueType.
	 * @param displayValue
	 *        | the display value of the type. The printable String of a type.
	 */
	private void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	/**
	 * Casts the value to ValueType.
	 * If the value parameter equals null, null is returned.
	 * @param value
	 *        | The value which will be returned.
	 * @return The casted value to the given ValueType.
	 * 
	 */
	public Object castTo(Object value) {
		if (value == null) {
			return null;
		}

		if (this.equals(ValueType.STRING) || this.equals(ValueType.EMAIL)) {
			value = String.valueOf(value);
		} else if (this.equals(ValueType.BOOLEAN)) {
			if (value instanceof String && !String.valueOf(value).isEmpty()) {
				value = Boolean.parseBoolean((String) value);
			} else if (String.valueOf(value).isEmpty()) {
				value = null;
			}
		} else if (this.equals(ValueType.INTEGER)) {
			if (!(value instanceof Integer)) {
				value = Integer.parseInt((String) value);
			}
		}
		return value;
	}

}
