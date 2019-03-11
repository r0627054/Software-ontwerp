package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

public class ColumnTypeChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		
		UUID columnId = (UUID) evt.getSource();
		ValueType currentType = ValueType.getValueTypeForString((String) evt.getOldValue());
		ValueType newType = null;

		ArrayList<ValueType> rotation = new ArrayList<>();
		rotation.add(ValueType.STRING);
		rotation.add(ValueType.EMAIL);
		rotation.add(ValueType.BOOLEAN);
		rotation.add(ValueType.INTEGER);
		
		int currentRotationIndex = rotation.indexOf(currentType);

		if (currentRotationIndex == rotation.size() - 1) {
			newType = rotation.get(0);
		} else {
			newType = rotation.get(currentRotationIndex + 1);
		}
		
		try {
			UUID tableId = uifacade.getCurrentTableId();
			int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Type");

			domainfacade.setColumnType(tableId, columnId, newType);
			uifacade.updateTableDesignViewMode(tableId, domainfacade.getTableNameOfId(tableId), domainfacade.getColumnCharacteristics(tableId));
			uifacade.unpause(columnIndex, columnId);
		} catch (DomainException e) {
			UUID tableId = uifacade.getCurrentTableId();
			int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Type");
			uifacade.setErrorDesignTableCell(columnIndex, columnId, newType);
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

}
