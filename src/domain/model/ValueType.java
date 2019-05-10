package domain.model;

/**
 * An enumeration introducing the different types a Column can have.
 *   In its current form, the class only supports, Email, String, Boolean and Integer.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 */
public enum ValueType {

	EMAIL(new Email("email@"), Email.class), STRING("", String.class), BOOLEAN(new Boolean(true), Boolean.class),
	INTEGER(new Integer(0), Integer.class);

	/**
	 * Variable storing the default value.
	 */
	private Object defaultValue;

	/**
	 * Variable storing the Class of the type.
	 */
	private Class<?> cl;

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
	private ValueType(Object defaultValue, Class<?> cl) {
		setDefaultValue(defaultValue);
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
				String stringCast = String.valueOf(value);
				Email email = new Email(stringCast);
				return (Email.hasOneAtSign(stringCast) || stringCast.isEmpty())
						&& email.getClass().equals(this.getTypeClass());
			} else if (this.equals(ValueType.BOOLEAN)) {
				if (value instanceof String
						&& (String.valueOf(value).equals("true") || String.valueOf(value).equals("false"))) {
					value = Boolean.parseBoolean((String) value);
				}
				return value.getClass().equals(this.getTypeClass());

			} else if (this.equals(ValueType.INTEGER)) {
				if (!(value instanceof Integer) && String.valueOf(value).matches("[0-9]+")) {
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
			if (type.getTypeClass().getSimpleName().equals(name)) {
				return type;
			}
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
		return this.getTypeClass().getSimpleName();
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

		if (this.equals(ValueType.STRING)) {
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
		} else if (this.equals(ValueType.EMAIL)) {
			if (value instanceof String && !String.valueOf(value).isEmpty()) {
				value = new Email((String) value);
			} else if (String.valueOf(value).isEmpty()) {
				value = null;
			}
		}
		return value;
	}

	public boolean haveSameValue(Object value1, Object value2) {
		return this.compareTo(value1, value2) == 0;
	}

	public boolean isGreaterThan(Object value1, Object value2) {
		return this.compareTo(value1, value2) == 1;
	}

	public boolean isSmallerThan(Object value1, Object value2) {
		return this.compareTo(value1, value2) == -1;
	}

	private int compareTo(Object value1, Object value2) {
		int result = -2;

		if (value1 == null && value2 == null) {
			result = 0;
		} else if (value1 != null && value2 != null) {
			try {
				if (this.equals(ValueType.STRING)) {
					String casted1 = String.valueOf(value1);
					String casted2 = String.valueOf(value2);
					result = casted1.compareTo(casted2);
				} else if (this.equals(ValueType.BOOLEAN)) {
					Boolean casted1 = (boolean) value1;
					Boolean casted2 = (boolean) value2;
					result = casted1.compareTo(casted2);
				} else if (this.equals(ValueType.INTEGER)) {
					Integer casted1 = (int) value1;
					Integer casted2 = (int) value2;
					result = casted1.compareTo(casted2);
				} else if (this.equals(ValueType.EMAIL)) {
					Email casted1 = (Email) value1;
					Email casted2 = (Email) value2;
					result = casted1.compareTo(casted2);
				}
			} catch (Exception e) {
				result = -2;
			}
		}
		return result;
	}
}
