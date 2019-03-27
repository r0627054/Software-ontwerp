package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateColumnChangeHandler is a ChangeHandler,
 * specifically made for handling the creation of a column in a table.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class CreateColumnChangeHandler implements ChangeHandlerInterface {

	/**
	 * Creates an empty Column in the table that the user is currently editing.
	 *  
	 * It updates the tableDesignViewMode with the newly created column.
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
			UUID id = (UUID) evt.getSource();
			domainfacade.addColumnToTable(id);
			uifacade.updateTableDesignViewMode(id, domainfacade.getTableNameOfId(id), domainfacade.getColumnCharacteristics(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
