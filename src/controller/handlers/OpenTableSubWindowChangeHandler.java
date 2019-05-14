package controller.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/** 
* A OpenTableSubWindowChangeHandler is a ChangeHandler,
* specifically made for opening a TableWindow (TableDesignWindow or a TableRowsWindow).
* 
* @version 2.0
* @author Dries Janse, Steven Ghekiere, Laurens Druwel
*
*/
public class OpenTableSubWindowChangeHandler implements ChangeHandlerInterface {

	/**
	 * Opens a tableWindow of a table with the given id.
	 * If the requested table is empty the TableDesignWindow is opened,
	 * otherwise a TableRowsWindow is opened.
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
				String tableName = domainfacade.getTableNameOfId(tableId);

				// First map: map of one UUID of the column and one String of the
				// ColumnName
				// Second map: For each columnKey map: a map of ID's and objects of
				// each cell
				// Wrapper map: a list of columns with their respective cells
				
				Map<List<Object>, LinkedHashMap<UUID, Object>> table = domainfacade.getTableWithIds(tableId);

				if (domainfacade.isTableWithIdEmpty(tableId)) {
					uifacade.createTableDesignSubWindow(tableId, tableName, domainfacade.getColumnCharacteristics(tableId));
				} else {
					uifacade.createTableRowsSubWindow(tableId, tableName, table, domainfacade.isComputedTable(tableId));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

