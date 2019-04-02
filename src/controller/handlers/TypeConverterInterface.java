package controller.handlers;

import java.util.ArrayList;
import java.util.List;

import domain.model.DomainException;


/**
 * 
 * The type converter interface contains the
 *  methods that needs to be implemented or can be used to cast to another value.
 * 
 * This interface is used in the ChangeHandlers, to cast or request a next value.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public interface TypeConverterInterface {

	/**
	 * Converts a String to a new Integer.
	 * The Integer cannot contain leading zeros.
	 * 
	 * @param newDefaultValue
	 *        | the Object which needs to be casted to a String.
	 * @return The casted Object to a string. 
	 *         If the value equals null, null is returned.
	 *         | Integer.parseInt(defaultValueString)
	 * @throws DomainException if the Object contains leading zeros.
	 *         | defaultValueString.length() > 1 && defaultValueString.startsWith("0")
	 */
	public default Integer getNewIntegerDefaultValue(Object newDefaultValue) {
		String defaultValueString = (String) newDefaultValue;
		if (defaultValueString == null || defaultValueString.trim().isEmpty()) {
			return null;
		} else if (defaultValueString.length() > 1 && defaultValueString.startsWith("0")) {
			throw new DomainException("Leading zeroes on the integer.");
		} else {
			return Integer.parseInt(defaultValueString);
		}
	}

	/**
	 * Returns the next Boolean default value.
	 * 
	 * @param oldValue
	 *        | The old boolean default value.
	 * @param allowBlanks
	 *        | Boolean whether or not blanks are allowed.
	 */
	public default Boolean getNextBooleanDefaultValue(Object oldValue, boolean allowBlanks) {
		List<Boolean> rotation = new ArrayList<Boolean>();
		rotation.add(true);
		rotation.add(false);

		if (allowBlanks) {
			rotation.add(null);
		}

		int currentIndex = -1;
		if (oldValue instanceof Boolean || oldValue == null) {
			currentIndex = rotation.indexOf(oldValue);
		} else if (!oldValue.equals("")) {
			currentIndex = rotation.indexOf(Boolean.parseBoolean((String) oldValue));
		} else {
			currentIndex = rotation.indexOf(oldValue);
		}

		if (currentIndex == rotation.size() - 1) {
			return rotation.get(0);
		} else {
			return rotation.get(currentIndex + 1);
		}
	}
}
