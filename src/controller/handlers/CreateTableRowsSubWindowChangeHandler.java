package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateTableRowsSubWindowChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID tableId = evt.getSource();

		try {
			uifacade.createTableRowsSubWindow(tableId, domainfacade.getTableNameOfId(tableId),
					domainfacade.getTableWithIds(tableId), domainfacade.getColumnTypes(tableId));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
