package controller.handlers;


import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateTableChangeHandler is a ChangeHandler,
 * specifically made for handling the creation of a new table.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateTableChangeHandler implements ChangeHandlerInterface {

	/**
	 * Creates a new empty table.
	 * 
	 * It updates the tablesViewMode with the newly created table.
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
			domainfacade.createNewTable();
			uifacade.updateTablesSubWindows(domainfacade.getTableNames());
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}

}
