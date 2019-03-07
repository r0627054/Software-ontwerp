package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class ChangeHandler {
	
	public ChangeHandler() {
		
	}
	
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade ) {
		switch (evt.getPropertyName()) {
		case "tablename":
			new TableNameChangeHandler().handleChange(evt, uifacade, domainfacade);
			break;

		default:
			break;
		}
	}

}
