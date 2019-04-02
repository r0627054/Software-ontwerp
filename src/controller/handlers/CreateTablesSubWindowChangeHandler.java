package controller.handlers;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateTablesSubWindowChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			uifacade.createTablesSubWindow(domainfacade.getTableNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
