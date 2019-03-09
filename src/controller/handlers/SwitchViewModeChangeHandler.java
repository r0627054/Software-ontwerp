package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.Map;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
import ui.model.viewmodes.ViewModeType;

public class SwitchViewModeChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			UUID id = (UUID) evt.getSource();
			String tableName = domainfacade.getTableNameOfId(id);
			ViewModeType oldViewMode = (ViewModeType) evt.getOldValue();
			ViewModeType newViewMode = (ViewModeType) evt.getNewValue();

			if (oldViewMode == ViewModeType.TABLEDESIGNVIEWMODE && newViewMode == ViewModeType.TABLEROWSVIEWMODE) {
				uifacade.openTableRowsViewMode(id, tableName, domainfacade.getTableWithIds(id));
				
			} else if (oldViewMode == ViewModeType.TABLEROWSVIEWMODE && newViewMode == ViewModeType.TABLEDESIGNVIEWMODE) {
				uifacade.openTableDesignViewMode(id, tableName, domainfacade.getColumnCharacteristics(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
