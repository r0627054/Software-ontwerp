package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class DeleteTableChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			UUID tableId = (UUID) evt.getSource();
			domainfacade.deleteTable(tableId);
			uifacade.updateTablesViewMode(domainfacade.getTableNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
