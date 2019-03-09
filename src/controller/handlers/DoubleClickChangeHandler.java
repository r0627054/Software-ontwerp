package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
import ui.model.viewmodes.ViewModeType;

public class DoubleClickChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		if(uifacade.getCurrentViewModeType().equals(ViewModeType.TABLESVIEWMODE)) {
			try {
				UUID id = (UUID) evt.getSource();
				String tableName = domainfacade.getTableNameOfId(id);
				Map<Map<UUID, String>, Map<UUID, Object>> table = domainfacade.getTableWithIds(id);
				uifacade.openTableRowsViewMode(id, tableName, table);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
