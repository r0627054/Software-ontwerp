package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateColumnChangeHandler is a ChangeHandler, specifically made for
 * handling the creation of a column in a table.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateColumnChangeHandler implements ChangeHandlerInterface {

	/**
	 * Creates an empty Column in the table that the user is currently editing.
	 * The column is created in the domain and the change is shown in the view.
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			UUID tableId = evt.getSource();
			domainfacade.addColumnToTable(tableId);
			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getColumnCharacteristics(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
