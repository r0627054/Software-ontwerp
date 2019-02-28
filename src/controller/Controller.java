package controller;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class Controller {
	private UIFacadeInterface uiFacade;
	private DomainFacadeInterface domainFacade;

	public Controller(UIFacadeInterface uiFacade, DomainFacadeInterface domainFacade) {
		this.setUiFacade(uiFacade);
		this.setDomainFacade(domainFacade);

		addActionListeners();
		this.uiFacade.show();
	}

	private void addActionListeners() {
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

}
