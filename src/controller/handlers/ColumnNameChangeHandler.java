package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A ColumnNameChangeHandler is a ChangeHandler,
 * specifically made for handling the change of the name in a column. 
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class ColumnNameChangeHandler implements ChangeHandlerInterface {

	/**
	 * Updates the name of a column.
	 * The handler tries to change the property.
	 * If the property is successfully changed in the column, the UI is updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI
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
		UUID columnId = evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Column Name");
		String newName = (String) evt.getNewValue();

		try {
			domainfacade.updateColumnName(tableId, columnId, newName);
			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getColumnCharacteristics(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
			uifacade.resume(columnIndex, columnId);
		} catch (DomainException e) {
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

}
