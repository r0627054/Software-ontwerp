package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateRowChangeHandler is a ChangeHandler,
 * specifically made for handling the creation of a row in a table.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class CreateRowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Creates an empty row in the table that the user is currently editing.
	 * Its value for each column is the column's default value.
	 *  
	 *  It updates the tableRowsViewMode with the newly created table.
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
		UUID tableId = (UUID) evt.getSource();

		try {
			domainfacade.createNewRow(tableId);
			uifacade.updateTableRowsViewMode(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
