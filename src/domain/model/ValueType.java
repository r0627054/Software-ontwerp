package domain.model;

/**
 * An enumeration introducing the different types a Column can have.
 *   In its current form, the class only supports, Email, String, Boolean and Integer.
 * 
 * @version 3.0
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

	/**
	 * Checks whether 2 object have the same value.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return whether the value is the same. True when they are the same; False otherwise.
	 */
	public boolean haveSameValue(Object value1, Object value2) {
		try {
			return this.compareTo(value1, value2) == 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks whether value 1 is greater than value 2.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return whether the value1 is greater. True when it is greater; False otherwise.
	 */
	public boolean isGreaterThan(Object value1, Object value2) {
		try {
			return this.compareTo(value1, value2) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks whether value 1 is smaller than value 2.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return whether the value1 is smaller. True when it is smaller; False otherwise.
	 */
	public boolean isSmallerThan(Object value1, Object value2) {
		try {
			return this.compareTo(value1, value2) < 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Compares 2 values to each other. Makes use of the compare to values of the Java compareTo method.
	 * 
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return The result of the compareTo function of Java, making use of the correct type.
	 * @throws DomainException when the values could be compared.
	 */
	private int compareTo(Object value1, Object value2) {
		if (value1 == null && value2 == null) {
			return 0;
		} else if (value1 != null && value2 != null) {
			if (this.equals(ValueType.STRING)) {
				String casted1 = (String) value1;
				String casted2 = (String) value2;
				return casted1.compareTo(casted2);
			} else if (this.equals(ValueType.BOOLEAN)) {
				Boolean casted1 = (boolean) value1;
				Boolean casted2 = (boolean) value2;
				return casted1.compareTo(casted2);
			} else if (this.equals(ValueType.INTEGER)) {
				Integer casted1 = (int) value1;
				Integer casted2 = (int) value2;
				return casted1.compareTo(casted2);
			} else if (this.equals(ValueType.EMAIL)) {
				if (value1 instanceof Email && value2 instanceof String) {
					return ((Email) value1).getEmail().compareTo((String) value2);
				} else if (value2 instanceof Email && value1 instanceof String) {
					return ((Email) value2).getEmail().compareTo((String) value1);
				} else {
					return ((Email) value2).getEmail().compareTo(((Email) value1).getEmail());
				}
			}
		}
		throw new DomainException("Cannot compare two objects");
	}
}
