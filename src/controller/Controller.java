package controller;
import ui.model.UIFacade;

public class Controller {
	private UIFacade uiFacade;
	// private DomainFacade domainFacade;

	public Controller() {
		this.uiFacade = new UIFacade();
		addActionListeners();
		
		this.uiFacade.show();
	}

	private void addActionListeners() {
		uiFacade.addWindowSwitchListeners();
	}

}
