package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
/**
 * A DeleteTableChangeHandler is a ChangeHandler,
 * specifically made for handling the deletion a table. 
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class DeleteTableChangeHandler implements ChangeHandlerInterface {

	/**
	 * Deletes the table which the user has selected.
	 * Closes all subwindows chowing the table.
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
			UUID tableId = evt.getSource();
			domainfacade.deleteTable(tableId);
			uifacade.closeAllSubWindowsOfTable(tableId);
			uifacade.updateTablesSubWindows(domainfacade.getTableNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
