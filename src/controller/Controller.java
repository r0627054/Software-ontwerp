package controller;

import controller.handlers.ChangeEventType;
import controller.handlers.ChangeHandlerFactory;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
/**
 * The controller is used to link the domain with the UI.
 * It listens to actions, by implementing the PropertyChangeListener Interface.
 * 
 * The controller contains a UIFacade which implements the UIFacadeInterface,
 * a DomainFacade which implement the DomainFacadeInterface
 * and a ChangeHandlerFactory which will delegate the different actions to the correct handler. 
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Controller implements PropertyChangeListener {
	
	/**
	 * Variable storing the UIFacade.
	 */
	private UIFacadeInterface uiFacade;
	
	/**
	 * Variable storing the domainFacade.
	 */
	private DomainFacadeInterface domainFacade;
	
	/**
	 * 
	 * Initialise a new Controller with the given UIFacade and domainFacade.
	 * 
	 * @param uiFacade
	 *        | The uiFacadeInterface used.
	 * @param domainFacade
	 *        | The domainFacadeInterface used.
	 * @effect The controller is initialised with the given UIFacade, DomainFacade and show equals false.
	 *        | this(uiFacade, domainFacade, true)
	 */
	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade) {
		this(uiFacade, domainFacade, true);
	}

	/**
	 * Initialise a new Controller with the given UIFacade and domainFacade and show variable.
	 * The show determines if the application window will be shown or not.
	 * 
	 * @param uiFacade
	 *        | The uiFacadeInterface used.
	 * @param domainFacade
	 *        | The domainFacadeInterface used.
	 * @param show
	 *        | The show determines if the application window will be shown or not.
	 * @effect The facades are set
	 *        | this.setUiFacade(uiFacade);
	 *	      | this.setDomainFacade(domainFacade)
	 * @effect The controller is added to the propertyChangeListeners of the uiFacade.
	 *        | setChangeHandler(new ChangeHandlerFactory())
	 * @effect Starts the application with the given domain data.
	 */
	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade, boolean show) {
		this.setUiFacade(uiFacade);
		this.setDomainFacade(domainFacade);
		this.getUiFacade().addPropertyChangeListener(this);

		if (show) {
			//Testing purposes (use cases)
			this.getUiFacade().show();
		}
	}

	/**
	 * Returns the UiFacade variable.
	 */
	public UIFacadeInterface getUiFacade() {
		return uiFacade;
	}

	/**
	 * Sets the UiFacadeInterface for the Controller.
	 * 
	 * @param uiFacade
	 *        | The UiFacadeInterface for the Controller.
	 * @post the UIFacadeInterface is equal to the given UIFacadeInterface.
	 *        | this.getUiFacade() == uiFacade
	 * @throws DomainException if the uiFacade equals null
	 *        | uiFacade == null
	 */
	private void setUiFacade(UIFacadeInterface uiFacade) {
		if (uiFacade == null) {
			throw new IllegalArgumentException("Cannot set a null uiFacade.");
		}
		this.uiFacade = uiFacade;
	}

	/**
	 * Returns the DomainFacadeInterface.
	 */
	public DomainFacadeInterface getDomainFacade() {
		return domainFacade;
	}

	/**
	 * Sets the DomainFacadeInterface for the Controller.
	 * 
	 * @param domainFacade
	 *        | The DomainFacadeInterface for the Controller.
	 * @post the DomainFacadeInterface is equal to the given DomainFacadeInterface.
	 *        | this.getDomainFacadeInterface() == DomainFacadeInterface
	 * @throws DomainException if the domainFacade equals null
	 *        | domainFacade == null
	 */
	private void setDomainFacade(DomainFacadeInterface domainFacade) {
		if (domainFacade == null) {
			throw new IllegalArgumentException("Cannot set a null domainFacade.");
		}
		this.domainFacade = domainFacade;
	}

	/**
	 * The propertyChange called by a change of the UIFacade.
	 *  If the change is a repaint, the controller ignores the request;
	 *   otherwise a handler is created by the factory and handles the event.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getAction().equals(ChangeEventType.REPAINT)) {
			ChangeHandlerFactory.handleChange(evt, getUiFacade(), getDomainFacade());
		}
	}

}
