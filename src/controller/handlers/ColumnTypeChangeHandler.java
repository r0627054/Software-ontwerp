package controller.handlers;

import java.util.ArrayList;
import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

/**
 * A ColumnTypeChangeHandler is a ChangeHandler,
 * specifically made for handling the change of the type in a column. 
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class ColumnTypeChangeHandler implements ChangeHandlerInterface {

	/**
	 * Updates the type of a column.
	 * The handler tries to change the property.
	 * If the property is successfully changed in the column, the UI is updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI and the next possible value is displayed
	 * and that subwindow is paused (only the current cell can be edited in that subwindow).
	 * 
	 * If the type was String, it becomes Email. If it was Email, it becomes
	 * Boolean. If it was Boolean, it becomes Integer. If it was Integer, it
	 * becomes String
	 * 
	 * @param evt
	 *        | The propertyChangeEvent containing all the information of the event.
	 * @param uiFacade
	 *        | The uiFacadeInterface used.
	 * @param domainFacade
	 *        | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {

		UUID columnId = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Type");
		ValueType currentType = ValueType.getValueTypeForString((String) evt.getOldValue());

		ValueType newType = getNextValueType(currentType);

		try {
			domainfacade.setColumnType(tableId, columnId, newType);
			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId));
//			uifacade.resume();
		} catch (DomainException e) {
			uifacade.throwError(columnId, columnIndex, newType);
			uifacade.pauseCurrentSubWindow(columnIndex, columnId);
		}
	}

	/**
	 * Gets the new value type based on the previous.
	 *
	 * @param previousValueType
	 *        | The previous value type of the column.
	 * @return nextValueType
	 *        | The new value type of the column.
	 */
	private ValueType getNextValueType(ValueType previousValueType) {
		ArrayList<ValueType> rotation = new ArrayList<>();
		rotation.add(ValueType.STRING);
		rotation.add(ValueType.EMAIL);
		rotation.add(ValueType.BOOLEAN);
		rotation.add(ValueType.INTEGER);

		int currentRotationIndex = rotation.indexOf(previousValueType);

		if (currentRotationIndex == rotation.size() - 1) {
			return rotation.get(0);
		} else {
			return rotation.get(currentRotationIndex + 1);
		}
	}

}
