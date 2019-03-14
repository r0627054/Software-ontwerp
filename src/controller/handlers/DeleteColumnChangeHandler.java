package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A DeleteColumnChangeHandler is a ChangeHandler,
 * specifically made for handling the deletion of a column in a table. 
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class DeleteColumnChangeHandler implements ChangeHandlerInterface {

	/**
	 * Deletes the column (with given column Id) in the table which the user is currently editing.
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
		try {
			UUID columnId = (UUID) evt.getSource();
			UUID tableId = uifacade.getCurrentTableId();
			domainfacade.deleteColumn(tableId, columnId);
			uifacade.updateTableDesignViewMode(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
