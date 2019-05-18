package app;

import controller.Controller;
import domain.model.DomainFacade;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacade;
import ui.model.view.UIFacadeInterface;

/**
 *  The Application class is used to start the Program.
 *  It create an instance of the domainFacadeInterface and an instance of the UIFacadeInterface,
 *  and gives it to the controller.
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class App {

	/**
	 * It creates a DomainFacade Instance and a UIFacade Instance.
	 * And it creates a Controller which links both the domain with the UI.
	 * 
	 * @param args
	 *        | The arguments are not used.
	 *        | The application starts without any arguments.
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {	
			DomainFacadeInterface domainFacadeInstance = new DomainFacade();
			UIFacadeInterface UIFacadeInstance = new UIFacade();
			new Controller(UIFacadeInstance, domainFacadeInstance);
		});
	}

}
