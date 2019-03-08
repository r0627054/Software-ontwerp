package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
import ui.model.viewmodes.ViewModeType;

public class ValueChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		if(uifacade.getCurrentViewModeType().equals(ViewModeType.TABLESVIEWMODE)) {
			
			String newTableName = (String) evt.getNewValue();
			UUID id = (UUID) evt.getSource();
			
			try {
				domainfacade.updateTableName(id, newTableName);
			} catch (Exception e) {
				System.out.println("ValueChangedHandler error thrown!");
				uifacade.throwError(id);
			}
		}
	}

}
