package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class DeleteColumnChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			UUID columnId = (UUID) evt.getSource();
			UUID tableId = uifacade.getCurrentTableId();
			domainfacade.deleteColumn(tableId, columnId);
			uifacade.updateTableDesignViewMode(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getColumnCharacteristics(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
