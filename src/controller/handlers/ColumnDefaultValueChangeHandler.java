package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

/**
 * A ColumnDefaultValueChangeHandler is a ChangeHandler, specifically made for
 * handling the change of the default value in a column.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 */
public class ColumnDefaultValueChangeHandler implements ChangeHandlerInterface, TypeConverterInterface {

	/**
	 * Updates the DefaultValue of a column. The handler tries to change the
	 * property. If the property is successfully changed in the column, the UI is
	 * updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in that specific subwindow
	 * and the next possible value is displayed and the subwindow is paused (only
	 * the current cell can be edited in that subwindow).
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID columnId = evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		Object newDefaultValue = evt.getNewValue();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Default Value");

		try {
			ValueType columnValueType = domainfacade.getValueTypeOfColumn(tableId, columnId);

			if (columnValueType.equals(ValueType.INTEGER)) {
				newDefaultValue = this.getNewIntegerDefaultValue(newDefaultValue);
			} else if (columnValueType.equals(ValueType.BOOLEAN)) {
				newDefaultValue = this.getNextBooleanDefaultValue(evt.getOldValue(),
						domainfacade.getColumnAllowBlanks(tableId, columnId));
			}

			domainfacade.setColumnDefaultValue(tableId, columnId, newDefaultValue);

			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId),
					domainfacade.isComputedTable(tableId));

//			uifacade.resume();
		} catch (DomainException | NumberFormatException e) {
			uifacade.throwError(columnId, columnIndex, newDefaultValue);
			uifacade.pauseCurrentSubWindow(columnIndex, columnId);
		}

	}
}
