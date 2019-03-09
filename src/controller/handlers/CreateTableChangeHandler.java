package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateTableChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			domainfacade.createNewTable();
			uifacade.updateTablesViewMode(domainfacade.getTableNames());
		} catch (DomainException e) {
			e.printStackTrace();
		}
	}

}
