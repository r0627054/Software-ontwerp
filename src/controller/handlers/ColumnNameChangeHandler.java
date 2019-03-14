package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A ColumnNameChangeHandler is a ChangeHandler,
 * specifically made for handling the change of the name in a column. 
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class ColumnNameChangeHandler implements ChangeHandlerInterface {

	/**
	 * Updates the name of a column.
	 * The handler tries to change the property.
	 * If the property is successfully changed in the column, the UI is updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI
	 * and the application is paused (only the current cell can be added).
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
		UUID columnId = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Column Name");
		String newName = (String) evt.getNewValue();

		try {
			domainfacade.updateColumnName(tableId, columnId, newName);
			uifacade.unpause(columnIndex, columnId);
		} catch (DomainException e) {
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

}
