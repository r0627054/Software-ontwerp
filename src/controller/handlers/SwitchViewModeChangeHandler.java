package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
import ui.model.window.sub.ViewModeType;

/**
* A SwitchViewModeChangeHandler is a ChangeHandler,
* specifically made for switching between the different viewModes.
* Currently it can switch form: a) TableDesignViewMode to TableRowsViewMode
*                               b) TableRowsViewMode to TableDesignViewMode
* 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public class SwitchViewModeChangeHandler implements ChangeHandlerInterface {

	/**
	 * Switches between tablesViewMode of a table with the given id.
	 * If the currentViewMode is TableDesignViewMode it switches to TableRowsViewMode.
	 * If the currentViewMode is TableRowsViewMode it switches to TableDesignViewMode.
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
			UUID tableId = (UUID) evt.getSource();
			String tableName = domainfacade.getTableNameOfId(tableId);
			ViewModeType oldViewMode = (ViewModeType) evt.getOldValue();
			ViewModeType newViewMode = (ViewModeType) evt.getNewValue();

			if (oldViewMode == ViewModeType.TABLEDESIGNVIEWMODE && newViewMode == ViewModeType.TABLEROWSVIEWMODE) {
				uifacade.openTableRowsViewMode(tableId, tableName, domainfacade.getTableWithIds(tableId),
						domainfacade.getColumnTypes(tableId));
				
			} else if (oldViewMode == ViewModeType.TABLEROWSVIEWMODE && newViewMode == ViewModeType.TABLEDESIGNVIEWMODE) {
				uifacade.openTableDesignViewMode(tableId, tableName, domainfacade.getColumnCharacteristics(tableId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
