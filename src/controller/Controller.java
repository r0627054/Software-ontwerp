package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class Controller implements PropertyChangeListener{
	private UIFacadeInterface uiFacade;
	private DomainFacadeInterface domainFacade;

	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade) {
		this.setUiFacade(uiFacade);
		this.setDomainFacade(domainFacade);
		this.getUiFacade().setTableNames(domainFacade.getTableNames());
		this.getUiFacade().addPropertyChangeListener(this);
		this.getUiFacade().show();
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Controller change fired");
		
	}

}
