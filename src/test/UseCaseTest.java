package test;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import controller.Controller;
import domain.model.DomainFacade;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.TableList;
import ui.model.view.UIFacade;
import ui.model.viewmodes.ViewModeType;

public abstract class UseCaseTest {
	private static UIFacade uiFacade;
	private static DomainFacade domainFacade;
	private static Controller controller;

	@BeforeAll
	public static void init() {
		System.out.println("TestInit");
		setUiFacade(UIFacade.getInstance());
		setDomainFacade(DomainFacade.getInstance());
		setController(new Controller(uiFacade, domainFacade, false));
	}
	
	@BeforeEach
	public void resetDomain() {
		this.getDomainFacade().resetTables();
		this.getUiFacade().resetViewModes();
	}

	protected void addDummyTable(String tableName) {
		getDomainFacade().addDummyTable(tableName);
		getUiFacade().updateTablesViewMode(getDomainFacade().getTableNames());
	}

	protected void emulateKeyPress(int keyCode) {
		getUiFacade().emulateKeyPress(keyCode);
	}

	protected void emulateKeyPresses(int keyCode, int amount) {
		for (int i = 0; i < amount; i++) {
			getUiFacade().emulateKeyPress(keyCode);
		}
	}

	protected void emulateKeyPress(char keyChar) {
		getUiFacade().emulateKeyPress(keyChar);
	}

	protected void emulateKeyPress(String string) {
		for (int i = 0; i < string.length(); i++) {
			getUiFacade().emulateKeyPress(string.charAt(i));
		}
	}

	protected void emulateSingleClick(int x, int y) {
		getUiFacade().emulateClickClicked(x, y, 1);
	}

	protected void emulateDoubleClick(int x, int y) {
		getUiFacade().emulateClickClicked(x, y, 2);
	}

	protected UIFacade getUiFacade() {
		return uiFacade;
	}

	private static void setUiFacade(UIFacade uifacade) {
		uiFacade = uifacade;
	}

	protected DomainFacade getDomainFacade() {
		return domainFacade;
	}

	private static void setDomainFacade(DomainFacade domainfacade) {
		domainFacade = domainfacade;
	}

	protected Controller getController() {
		return controller;
	}

	private static void setController(Controller controller) {
		UseCaseTest.controller = controller;
	}

	protected TableList getTablesViewModeTableList() {
		for (Component c : getUiFacade().getView().getTablesViewMode().getComponents()) {
			if (c instanceof Container) {
				Container container = (Container) c;

				for (Component containerComponents : container.getComponentsList()) {
					if (containerComponents instanceof TableList) {
						return (TableList) containerComponents;
					}
				}
			}
		}
		return null;
	}
	

	protected DesignTable getTableViewModeDesignTable(UUID tableId) {
		for (Component c : getUiFacade().getView().getViewMode(tableId, ViewModeType.TABLEDESIGNVIEWMODE).getComponents()) {
			if (c instanceof Container) {
				Container container = (Container) c;

				for (Component containerComponents : container.getComponentsList()) {
					if (containerComponents instanceof DesignTable) {
						return (DesignTable) containerComponents;
					}
				}
			}
		}
		return null;
	}

}
