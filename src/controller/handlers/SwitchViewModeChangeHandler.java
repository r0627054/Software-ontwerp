package controller.handlers;

import java.beans.PropertyChangeEvent;
import java.util.UUID;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;
import ui.model.viewmodes.ViewModeType;

public class SwitchViewModeChangeHandler implements ChangeHandlerInterface {

	@Override
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		try {
			UUID id = (UUID) evt.getSource();
			ViewModeType oldViewMode = (ViewModeType) evt.getOldValue();
			ViewModeType newViewMode = (ViewModeType) evt.getNewValue();

			if (oldViewMode == ViewModeType.TABLEDESIGNVIEWMODE && newViewMode == ViewModeType.TABLEROWSVIEWMODE) {
				
			} else if (oldViewMode == ViewModeType.TABLEROWSVIEWMODE && newViewMode == ViewModeType.TABLEDESIGNVIEWMODE) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
