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
		UUID tableId = uifacade.getCurrentTableId();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Column Name");
		String newName = (String) evt.getNewValue();

		try {
			domainfacade.updateColumnName(tableId, columnId, newName);
			uifacade.unpause(columnIndex, columnId);
		} catch (DomainException e) {
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

}
