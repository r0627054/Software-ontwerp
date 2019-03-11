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
			Object oldValue = evt.getOldValue();
			domainfacade.updateColumnName(uifacade.getCurrentTableId(), columnId, newName);

			//TODO 11/03 BEGIN HIER
			uifacade.unpause(0, columnId);
//			System.out.println("ColName updated to: " + newName);

//			String tableName = domainfacade.getTableNameOfColumnId(columnId);
//			UUID tableId = domainfacade.getTableIdOfColumnId(columnId);
//			uifacade.updateTableDesignViewMode(tableId, tableName, domainfacade.getColumnCharacteristics(tableId));
		} catch (DomainException e) {
			System.out.println("ColName change error!");
			uifacade.pauseApplication(0, columnId);
		}

	}

}
