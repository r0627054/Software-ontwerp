package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class DeleteRowChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID cellIdOfFirstElement = (UUID) evt.getSource();
		UUID tableId = uifacade.getCurrentTableId();
		
		try {
			UUID rowId = domainfacade.getRowId(tableId, cellIdOfFirstElement);
			domainfacade.deleteRow(tableId, rowId);
			uifacade.updateTableRowsViewMode(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}

}
