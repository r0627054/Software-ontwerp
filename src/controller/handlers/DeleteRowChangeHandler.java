package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A DeleteRowChangeHandler is a ChangeHandler,
 * specifically made for handling the deletion of a row in a table. 
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class DeleteRowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Deletes the row selected row in the table which the user is currently editing.
	 *  The UI is updated with the information.
	 * @param evt
	 *        | The propertyChangeEvent containing all the information of the event.
	 * @param uiFacade
	 *        | The uiFacadeInterface used.
	 * @param domainFacade
	 *        | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID cellIdOfFirstElement = evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();

		try {
			UUID rowId = domainfacade.getRowId(tableId, cellIdOfFirstElement);
			domainfacade.deleteRow(tableId, rowId);
			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}

}
