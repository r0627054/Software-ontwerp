package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class ColumnAllowBlanksChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		System.out.println("Allowblanks");
		
	}

}
