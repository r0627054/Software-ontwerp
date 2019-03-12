package controller.handlers;

import java.util.ArrayList;
import java.util.List;

import domain.model.DomainException;

public interface TypeCoverterInterface {

	default Integer getNewIntegerDefaultValue(Object newDefaultValue) {
		String defaultValueString = (String) newDefaultValue;
		if (defaultValueString.trim().isEmpty()) {
			return null;
		} else if (defaultValueString.length() > 1 && defaultValueString.startsWith("0")) {
			throw new DomainException("Leading zeroes on the integer.");
		} else {
			return Integer.parseInt(defaultValueString);
		}
	}

	default Boolean getNextBooleanDefaultValue(Object oldValue, boolean allowBlanks) {
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
