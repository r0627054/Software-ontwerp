package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

public class ColumnDefaulValueChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID columnId = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		Object newDefaultValue = evt.getNewValue();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Default Value");

		List<Boolean> rotation = new ArrayList<Boolean>();
		rotation.add(true);
		rotation.add(false);

		try {
			if (domainfacade.getColumnAllowBlanks(tableId, columnId)) {
				rotation.add(null);
			}

			ValueType columnValueType = domainfacade.getValueTypeOfColumn(tableId, columnId);

			if (columnValueType.equals(ValueType.INTEGER)) {
				newDefaultValue = this.getNewIntegerDefaultValue(newDefaultValue);
			} else if (columnValueType.equals(ValueType.BOOLEAN)) {
				newDefaultValue = this.getNextBooleanDefaultValue(rotation, evt.getOldValue());
			}

			domainfacade.setColumnDefaultValue(tableId, columnId, newDefaultValue);

			if (columnValueType.equals(ValueType.BOOLEAN)) {
				uifacade.updateTableDesignViewMode(tableId, domainfacade.getTableNameOfId(tableId),
						domainfacade.getColumnCharacteristics(tableId));
			}

			uifacade.unpause(columnIndex, columnId);
		} catch (DomainException | NumberFormatException e) {
			uifacade.setErrorDesignTableCell(columnIndex, columnId, newDefaultValue);
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

	private Boolean getNextBooleanDefaultValue(List<Boolean> rotation, Object oldValue) {
		int currentIndex = -1;
		if (!oldValue.equals("")) {
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

	private Integer getNewIntegerDefaultValue(Object newDefaultValue) {
		String defaultValueString = (String) newDefaultValue;
		if (defaultValueString.trim().isEmpty()) {
			return null;
		} else if (defaultValueString.length() > 1 && defaultValueString.startsWith("0")) {
			throw new DomainException("Leading zeroes on the integer.");
		} else {
			return Integer.parseInt(defaultValueString);
		}
	}

}
