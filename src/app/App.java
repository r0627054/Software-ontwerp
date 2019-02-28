package app;

import controller.Controller;
import domain.model.DomainFacade;
import ui.model.view.UIFacade;

public class App {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(() -> {			
			new Controller(new UIFacade(), new DomainFacade());
		});
	}

}
