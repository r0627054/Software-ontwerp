package app;

import controller.Controller;
import domain.model.DomainFacade;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacade;
import ui.model.view.UIFacadeInterface;

public class App {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {	
			DomainFacadeInterface domainFacadeInstance = DomainFacade.getInstance();
			UIFacadeInterface UIFacadeInstance = UIFacade.getInstance();
			new Controller(UIFacadeInstance, domainFacadeInstance);
		});
	}

}
