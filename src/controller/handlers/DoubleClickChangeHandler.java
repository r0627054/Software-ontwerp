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
		if (uifacade.getCurrentViewModeType().equals(ViewModeType.TABLESVIEWMODE)) {
			try {
				UUID id = (UUID) evt.getSource();

				// First map: 'singleton' map of one UUID of the column and one String of the
				// ColumnName
				// Second map: For each 'singleton' columnKey map: a map of ID's and objects of
				// each cell
				// Wrapper map: a list of columns with their respective cells
				Map<Map<UUID, String>, Map<UUID, Object>> table = domainfacade.getTableWithIds(id);

				if (table.isEmpty()) {
					uifacade.openTableDesignViewMode(id, domainfacade.getColumnCharacteristics(id));
				} else {
					uifacade.openTableRowsViewMode(id, table);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
