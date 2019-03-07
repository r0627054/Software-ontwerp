package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class TableNameChangeHandler {

	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		//TODO get info from evt and actually change the tablename via the facade
		//TODO somehow make components store id's from domain, and pass those id's within evt so this class knows what to change exactly
		
	}

}
