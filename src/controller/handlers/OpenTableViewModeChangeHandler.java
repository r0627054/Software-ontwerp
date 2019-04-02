package controller.handlers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/** 
* A OpenTableViewModeChangeHandler is a ChangeHandler,
* specifically made for opening a TableViewMode (DesignViewMode or a TableRowsViewMode).
* 
* @version 2.0
* @author Dries Janse, Steven Ghekiere, Laurens Druwel
*
*/
public class OpenTableViewModeChangeHandler implements ChangeHandlerInterface {

	/**
	 * Opens a tablesViewMode of a table with the given id.
	 * If the requested table is empty the tableDesignMode is shown,
	 * otherwise the TableRowsViewMode is shown.
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

				// First map: 'singleton' map of one UUID of the column and one String of the
				// ColumnName
				// Second map: For each 'singleton' columnKey map: a map of ID's and objects of
				// each cell
				// Wrapper map: a list of columns with their respective cells
				Map<Map<UUID, String>, LinkedHashMap<UUID, Object>> table = domainfacade.getTableWithIds(tableId);

				if (table.isEmpty()) {
					uifacade.createTableDesignSubWindow(tableId, tableName, domainfacade.getColumnCharacteristics(tableId));
				} else {
					uifacade.createTableRowsSubWindow(tableId, tableName, table, domainfacade.getColumnTypes(tableId));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

