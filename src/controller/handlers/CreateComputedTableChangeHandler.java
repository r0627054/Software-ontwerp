package controller.handlers;

import java.util.UUID;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class CreateComputedTableChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		UUID tableId = evt.getSource();
		String newQuery = (String) evt.getNewValue();

		try {
			String tableName = domainfacade.getTableNameOfId(tableId);
			domainfacade.createComputedTable(tableId, newQuery);
			uifacade.updateTablesSubWindows(domainfacade.getTableNames());
			uifacade.updateTableRowsAndDesignSubWindows(tableId, tableName,
					domainfacade.getColumnCharacteristics(tableId), domainfacade.getTableWithIds(tableId),
					domainfacade.isComputedTable(tableId));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
