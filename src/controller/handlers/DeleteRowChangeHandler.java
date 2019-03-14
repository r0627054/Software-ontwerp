package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A DeleteRowChangeHandler is a ChangeHandler,
 * specifically made for handling the deletion of a row in a table. 
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class DeleteRowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Deletes the row selected row in the table which the user is currently editing.
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
		UUID cellIdOfFirstElement = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		
		try {
			UUID rowId = domainfacade.getRowId(tableId, cellIdOfFirstElement);
			domainfacade.deleteRow(tableId, rowId);
			uifacade.updateTableRowsViewMode(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}

}
