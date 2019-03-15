package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

/**
 * A RowEditedChangeHandler is a ChangeHandler,
 * specifically made for handling the editing of a cell in the TableRowsViewMode.
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class RowEditedChangeHandler implements ChangeHandlerInterface, TypeConverterInterface {

	/**
	 * Updates the value in a table.
	 * The handler tries to change the property.
	 * If the property is successfully changed in the cell the table is updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI and the next possible value is displayed
	 * and the application is paused (only the current cell can be added).
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
		UUID cellId = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		Object newValue = evt.getNewValue();
		Object oldValue = evt.getOldValue();

		List<Boolean> rotation = new ArrayList<Boolean>();
		rotation.add(true);
		rotation.add(false);

		try {
			UUID columnId = domainfacade.getColumnId(tableId, cellId);
			ValueType columnValueType = domainfacade.getValueTypeOfColumn(tableId, columnId);

			if (columnValueType.equals(ValueType.INTEGER)) {
				newValue = this.getNewIntegerDefaultValue(newValue);
			} else if (columnValueType.equals(ValueType.BOOLEAN)) {
				newValue = this.getNextBooleanDefaultValue(oldValue,
						domainfacade.getColumnAllowBlanks(tableId, columnId));
			}
			domainfacade.editCellInTable(tableId, columnId, cellId, newValue);

			if (columnValueType.equals(ValueType.BOOLEAN)) {
				uifacade.updateTableRowsViewMode(tableId, domainfacade.getTableNameOfId(tableId),
						domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
			}
			uifacade.unpause(domainfacade.getIndexOfCellInColumnId(tableId, columnId, cellId), columnId);
		} catch (DomainException | NumberFormatException e) {
			UUID columnId = domainfacade.getColumnId(tableId, cellId);
			uifacade.pauseApplication(domainfacade.getIndexOfCellInColumnId(tableId, columnId, cellId), columnId);
		}

	}

}
