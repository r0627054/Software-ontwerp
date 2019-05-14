package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A TableNameChangeHandler is a ChangeHandler, specifically made for handling
 * the change of the name of a table.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class TableNameChangeHandler implements ChangeHandlerInterface {

	/**
	 * Updates the name of a table. The handler tries to change the property. If the
	 * property is successfully changed in the table, the UI is updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI
	 * and the subwindow is paused (only the current tablename can be edited).
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		String newTableName = (String) evt.getNewValue();
		UUID tableId = evt.getSource();

		try {
			domainfacade.updateTableName(tableId, newTableName);
			uifacade.updateTablesSubWindows(domainfacade.getTableNames());
			uifacade.updateTableRowsAndDesignSubWindows(tableId, newTableName, domainfacade.getColumnCharacteristics(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.isComputedTable(tableId));
		} catch (Exception e) {
			uifacade.throwError(tableId, 0, 0);
			uifacade.pauseCurrentSubWindow(0, tableId);
		}
	}

}
