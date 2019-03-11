package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class ColumnNameChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID columnId = (UUID) evt.getSource();
		try {
			String newName = (String) evt.getNewValue();
			UUID tableId = uifacade.getCurrentTableId();
			domainfacade.updateColumnName(tableId, columnId, newName);
			
			int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Column Name");
			uifacade.unpause(columnIndex, columnId);
		} catch (DomainException e) {
			uifacade.pauseApplication(0, columnId);
		}

	}

}
