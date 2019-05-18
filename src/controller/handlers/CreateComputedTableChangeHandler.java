package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * A CreateComputedTableChangeHandler is a ChangeHandler, specifically made for
 * handling the creation of computedTable with the given query. It can also revert this.
 * If there is no query it will be changed back to a stored table.
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CreateComputedTableChangeHandler implements ChangeHandlerInterface {

	/**
	 * 
	 * Changes a stored table to a computed table if a good query is given.
	 * Changes a Computed table back to a stored table if the query is empty.
	 * 
	 * @param evt          | The propertyChangeEvent containing all the information
	 *                     of the event.
	 * @param uiFacade     | The uiFacadeInterface used.
	 * @param domainFacade | The domainFacadeInterface used.
	 */
	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID tableId = evt.getSource();
		String newQuery = (String) evt.getNewValue();

		try {
			String tableName = domainfacade.getTableNameOfId(tableId);

			if (newQuery.trim().length() > 0) {
				domainfacade.createComputedTable(tableId, newQuery);
				uifacade.updateTablesSubWindows(domainfacade.getTableNames());
				uifacade.updateTableRowsAndDesignSubWindows(tableId, tableName,
						domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId),
						domainfacade.isComputedTable(tableId));
				uifacade.closeAllDesignWindows(tableId);
			} else {
				domainfacade.setEmptyQuery(tableId);
				uifacade.updateTablesSubWindows(domainfacade.getTableNames());
				uifacade.updateTableRowsAndDesignSubWindows(tableId, tableName,
						domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId),
						domainfacade.isComputedTable(tableId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			uifacade.throwError(tableId, 1, 0);
			uifacade.pauseCurrentSubWindow(1, tableId);
		}

	}

}
