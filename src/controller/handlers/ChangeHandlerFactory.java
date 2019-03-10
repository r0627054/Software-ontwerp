package controller.handlers;

import java.beans.PropertyChangeEvent;

import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

public class ChangeHandlerFactory {

	public ChangeHandlerFactory() {

	}

	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		String value = evt.getPropertyName();
		ChangeHandlerInterface handler = null;
		if (value.equals(ChangeEventType.VALUE.getEventString())) {
			handler = new ValueChangeHandler();
		} else if (value.equals(ChangeEventType.DOUBLEClICK.getEventString())) {
			handler = new DoubleClickChangeHandler();
		} else if (value.equals(ChangeEventType.CREATE_TABLE.getEventString())) {
			handler = new CreateTableChangeHandler();
		} else if (value.equals(ChangeEventType.DELETE_TABLE.getEventString())) {
			handler = new DeleteTableChangeHandler();
		} else if (value.equals(ChangeEventType.SWITCH_VIEWMODE.getEventString())) {
			handler = new SwitchViewModeChangeHandler();
		}

		if (handler != null) {
			handler.handleChange(evt, uifacade, domainfacade);
		}
	}

}
