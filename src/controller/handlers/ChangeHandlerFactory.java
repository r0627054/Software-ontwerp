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
		if (value.equals(ChangeEventType.TABLE_CHANGE_NAME.getEventString())) {
			handler = new TableNameChangeHandler();
		} else if (value.equals(ChangeEventType.OPEN_TABLEVIEWMODE.getEventString())) {
			handler = new OpenTableViewModeChangeHandler();
		} else if (value.equals(ChangeEventType.CREATE_TABLE.getEventString())) {
			handler = new CreateTableChangeHandler();
		} else if (value.equals(ChangeEventType.DELETE_TABLE.getEventString())) {
			handler = new DeleteTableChangeHandler();
		} else if (value.equals(ChangeEventType.SWITCH_VIEWMODE.getEventString())) {
			handler = new SwitchViewModeChangeHandler();
		} else if (value.equals(ChangeEventType.CREATE_COLUMN.getEventString())) {
			handler = new CreateColumnChangeHandler();
		} else if (value.equals(ChangeEventType.COLUMN_CHANGE_NAME.getEventString())) {
			handler = new ColumnNameChangeHandler();
		} else if (value.equals(ChangeEventType.COLUMN_CHANGE_TYPE.getEventString())) {
			handler = new ColumnTypeChangeHandler();
		} else if (value.equals(ChangeEventType.COLUMN_CHANGE_ALLOW_BLANKS.getEventString())) {
			handler = new ColumnAllowBlanksChangeHandler();
		} else if (value.equals(ChangeEventType.COLUMN_CHANGE_DEFAULT_VALUE.getEventString())) {
			handler = new ColumnDefaultValueChangeHandler();
		} else if (value.equals(ChangeEventType.DELETE_COLUMN.getEventString())) {
			handler = new DeleteColumnChangeHandler();
		} else if (value.equals(ChangeEventType.CREATE_ROW.getEventString())) {
			handler = new CreateRowChangeHandler();
		} else if (value.equals(ChangeEventType.ROW_EDITED.getEventString())) {
			handler = new RowEditedChangeHandler();
		} else if (value.equals(ChangeEventType.DELETE_ROW.getEventString())) {
			handler = new DeleteRowChangeHandler();
		}

		if (handler != null) {
			handler.handleChange(evt, uifacade, domainfacade);
		}
	}

}
