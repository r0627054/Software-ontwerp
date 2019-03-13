package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import controller.handlers.ChangeEventType;
import controller.handlers.ChangeHandlerFactory;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class Controller implements PropertyChangeListener {
	private UIFacadeInterface uiFacade;
	private DomainFacadeInterface domainFacade;
	private ChangeHandlerFactory changeHandler;

	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade) {
		this(uiFacade, domainFacade, true);
	}

	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade, boolean show) {
		this.setUiFacade(uiFacade);
		this.setDomainFacade(domainFacade);
		setChangeHandler(new ChangeHandlerFactory());

		this.getUiFacade().startup(domainFacade.getTableNames());
		this.getUiFacade().addPropertyChangeListener(this);

		if (show) {
			//Testing purposes (use cases)
			this.getUiFacade().show();
		}
	}

	private void handleChange(PropertyChangeEvent evt) {
		changeHandler.handleChange(evt, getUiFacade(), getDomainFacade());
	}

	public UIFacadeInterface getUiFacade() {
		return uiFacade;
	}

	private void setUiFacade(UIFacadeInterface uiFacade) {
		if (uiFacade == null) {
			throw new IllegalArgumentException("Cannot set a null uiFacade.");
		}
		this.uiFacade = uiFacade;
	}

	public DomainFacadeInterface getDomainFacade() {
		return domainFacade;
	}

	private void setDomainFacade(DomainFacadeInterface domainFacade) {
		if (domainFacade == null) {
			throw new IllegalArgumentException("Cannot set a null domainFacade.");
		}
		this.domainFacade = domainFacade;
	}

	private void setChangeHandler(ChangeHandlerFactory changeHandler) {
		if (changeHandler == null) {
			throw new IllegalArgumentException("Cannot set a null changeHandler.");
		}
		this.changeHandler = changeHandler;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equalsIgnoreCase(ChangeEventType.REPAINT.getEventString())) {
			handleChange(evt);
		}
	}

}
