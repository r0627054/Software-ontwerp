package controller.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import domain.model.ValueType;
import ui.model.view.UIFacadeInterface;

/**
 * A RowEditedChangeHandler is a ChangeHandler,
 * specifically made for handling the editing of a cell in the TableRowsWindow.
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class RowEditedChangeHandler implements ChangeHandlerInterface, TypeConverterInterface {

	/**
	 * Updates the value in a table.
	 * The handler tries to change the property.
	 * If the property is successfully changed in the cell the table is updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI and the next possible value is displayed
	 * and the subwindow is paused (only the current cell can be edited in that subwindow).
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
		UUID cellId = evt.getSource();
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

			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId),
					domainfacade.isComputedTable(tableId));

			List<UUID> otherIds = domainfacade.getTableIdOfUsedTables(tableId, cellId);
			for (UUID otherId : otherIds) {
				uifacade.updateTableRowsAndDesignSubWindows(otherId, domainfacade.getTableNameOfId(otherId),
						domainfacade.getColumnCharacteristics(otherId), domainfacade.getTableWithIds(otherId),
						domainfacade.isComputedTable(otherId));
			}
		} catch (DomainException | NumberFormatException e) {
			e.printStackTrace();
			UUID columnId = domainfacade.getColumnId(tableId, cellId);
			uifacade.pauseCurrentSubWindow(domainfacade.getIndexOfCellInColumnId(tableId, columnId, cellId), columnId);
		}

	}

}
