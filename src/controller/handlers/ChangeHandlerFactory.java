package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class ChangeHandlerFactory {
	
	public ChangeHandlerFactory() {
		
	}
	
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade ) {
		String value = evt.getPropertyName();
		ChangeHandlerInterface handler= null;
		if(value.equals(ChangeEventType.VALUE.getEventString())) {
			 handler = new ValueChangeHandler();
		}
		
		if(handler != null) {
			handler.handleChange(evt, uifacade, domainfacade);
		}
	}

}
