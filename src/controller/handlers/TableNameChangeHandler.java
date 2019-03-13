package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class TableNameChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		String newTableName = (String) evt.getNewValue();
		UUID id = (UUID) evt.getSource();

		try {
			domainfacade.updateTableName(id, newTableName);
		} catch (Exception e) {
			uifacade.throwError(id);
		}
	}

}
