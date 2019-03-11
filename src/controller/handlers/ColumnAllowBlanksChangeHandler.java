package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainException;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class ColumnAllowBlanksChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID columnId = (UUID) evt.getSource();
		
		boolean newBool = (boolean) evt.getNewValue();
		boolean oldBool = (boolean) evt.getOldValue();
		UUID tableId = uifacade.getCurrentTableId();
		int columnIndex = domainfacade.getIndexOfColumnCharacteristic(tableId, columnId, "Allow Blanks");		
		
		try {
			domainfacade.setAllowBlanks(tableId, columnId, newBool);
			uifacade.updateTableDesignViewMode(tableId, domainfacade.getTableNameOfId(tableId),
			domainfacade.getColumnCharacteristics(tableId));
			uifacade.unpause(columnIndex, columnId);		
		} catch (DomainException e) {
			uifacade.setErrorDesignTableCell(columnIndex, columnId, newBool);
			uifacade.pauseApplication(columnIndex, columnId);
		}

	}

}
