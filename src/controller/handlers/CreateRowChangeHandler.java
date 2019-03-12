package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateRowChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID tableId = (UUID) evt.getSource();

		try {
			domainfacade.createNewRow(tableId);
			uifacade.updateTableRowsViewMode(tableId, domainfacade.getTableNameOfId(tableId), domainfacade.getTableWithIds(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
