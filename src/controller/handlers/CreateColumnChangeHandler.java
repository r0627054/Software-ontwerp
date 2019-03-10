package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateColumnChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			UUID id = (UUID) evt.getSource();
			domainfacade.addColumnToTable(id);
			uifacade.updateTableDesignViewMode(id, domainfacade.getTableNameOfId(id), domainfacade.getColumnCharacteristics(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
