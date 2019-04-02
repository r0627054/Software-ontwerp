package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A ColumnAllowBlanksChangeHandler is a ChangeHandler, specifically made for
 * handling the change of the allow blanks option in a column.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class ColumnAllowBlanksChangeHandler implements ChangeHandlerInterface {

	/**
	 * Updates the allow blanks value of a column. The handler tries to change the
	 * property. If the property is successfully changed in the column, the UI is
	 * updated.
	 * 
	 * If the property couldn't be updated an error (red border) is shown in the UI
	 * and the application is paused (only the current cell can be added).
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID columnId = (UUID) evt.getSource();

		boolean newBool = (boolean) evt.getNewValue();
		UUID tableId = uifacade.getCurrentTableId();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Allow Blanks");

		try {
			domainfacade.setAllowBlanks(tableId, columnId, newBool);
			uifacade.updateTableRowsAndDesignSubWindows(tableId, domainfacade.getColumnCharacteristics(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
			uifacade.resume(columnIndex, columnId);
		} catch (DomainException e) {
			uifacade.throwError(columnId, columnIndex, newBool);
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

}
