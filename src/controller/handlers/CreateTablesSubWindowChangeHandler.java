package controller.handlers;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateTablesSubWindowChangeHandler is a ChangeHandler, specifically made for
 *  handling the creation of a tablesSubWindow (showing the names of the table).
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateTablesSubWindowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Requests all the table information in the domain and gives it to the view.
	 *  The view creates a tablesSubWindow.
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			uifacade.createTablesSubWindow(domainfacade.getTableNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
