package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateFormDesignSubWindowChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID tableId = evt.getSource();

		try {
			uifacade.createFormSubWindow(tableId, (String) evt.getOldValue(), domainfacade.getTableWithIds(tableId),
					domainfacade.isComputedTable(tableId));
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}
}
