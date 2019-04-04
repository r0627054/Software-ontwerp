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
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateRowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Creates an empty row in the table that the user is currently editing.
	 * Its value for each column is the column's default value.
	 *  
	 *  It updates the UI with the newly created row.
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
		UUID tableId = evt.getSource();

		try {
			domainfacade.createNewRow(tableId);
			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
