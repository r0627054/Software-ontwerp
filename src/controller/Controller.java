package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import controller.handlers.ChangeHandler;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class Controller implements PropertyChangeListener{
	private UIFacadeInterface uiFacade;
	private DomainFacadeInterface domainFacade;
	private ChangeHandler changeHandler;

	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade) {
		this.setUiFacade(uiFacade);
		this.setDomainFacade(domainFacade);
		this.getUiFacade().startup(domainFacade.getTableNames());
		this.getUiFacade().addPropertyChangeListener(this);
		this.getUiFacade().show();
		changeHandler = new ChangeHandler();
	}

	public UIFacadeInterface getUiFacade() {
		return uiFacade;
	}

	private void setUiFacade(UIFacadeInterface uiFacade) {
		this.uiFacade = uiFacade;
	}

	public DomainFacadeInterface getDomainFacade() {
		return domainFacade;
	}

	private void setDomainFacade(DomainFacadeInterface domainFacade) {
		this.domainFacade = domainFacade;
	}
	
	public void addTable(String name) {
		this.domainFacade.addTable(name);
	}

	private void handleChange(PropertyChangeEvent evt) {
		changeHandler.handleChange(evt, getUiFacade(), getDomainFacade());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(!evt.getPropertyName().equalsIgnoreCase("repaint")) {
			handleChange(evt);
		}
	}


}
