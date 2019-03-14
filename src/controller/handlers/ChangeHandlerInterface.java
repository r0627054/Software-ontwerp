package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * An interface of the ChangeHandlerInterface.
 *  Every changeHandler has to implement a handle method.
 *  
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel, Mauro Luyten
 *
 */
public interface ChangeHandlerInterface {

	/**
	 * 
	 * @param evt
	 *        | The propertyChangeEvent containing all the information of the event.
	 * @param uiFacade
	 *        | The uiFacadeInterface used.
	 * @param domainFacade
	 *        | The domainFacadeInterface used.
	 */
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade );
	
	
}
