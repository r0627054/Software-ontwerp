package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

public class ColumnDefaultValueChangeHandler implements ChangeHandlerInterface, TypeConverterInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID columnId = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		Object newDefaultValue = evt.getNewValue();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Default Value");

		try {
			ValueType columnValueType = domainfacade.getValueTypeOfColumn(tableId, columnId);

			if (columnValueType.equals(ValueType.INTEGER)) {
				newDefaultValue = this.getNewIntegerDefaultValue(newDefaultValue);
			} else if (columnValueType.equals(ValueType.BOOLEAN)) {
				newDefaultValue = this.getNextBooleanDefaultValue(evt.getOldValue(), domainfacade.getColumnAllowBlanks(tableId, columnId));
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
}
