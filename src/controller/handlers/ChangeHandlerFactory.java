package controller.handlers;

import controller.observer.PropertyChangeEvent;
import domain.model.DomainFacadeInterface;
import ui.model.view.UIFacadeInterface;

/**
 * The changeHandlerFactory is a factory class delegating the differentEventTypes to the corresponding handlers.
 *  
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class ChangeHandlerFactory {

	/**
	 * Initialises a new ChangeHandlerFactory without any information.
	 */
	public ChangeHandlerFactory() {

	}

	/**
	 * Method used for handling all the change events.
	 * It delegates the different events to the corresponding changeHandler.
	 * 
	 * @param evt
	 *        | The propertyChangeEvent containing the information about the event.
	 * @param uifacade
	 *        | The UIFacadeInterface used in the controller.
	 * @param domainfacade
	 *        | The DomainFacadeInterface used in the controller.
	 * @effect The handler is created and all the information is passed to the specific handler.
	 */
	public void handleChange(PropertyChangeEvent evt, UIFacadeInterface uifacade, DomainFacadeInterface domainfacade) {
		ChangeEventType value = evt.getAction();
		ChangeHandlerInterface handler = null;

		switch (value) {
		case TABLE_CHANGE_NAME:
			handler = new TableNameChangeHandler();
			break;
		case OPEN_TABLEVIEWMODE:
			handler = new OpenTableViewModeChangeHandler();
			break;
		case CREATE_TABLE:
			handler = new CreateTableChangeHandler();
			break;
		case DELETE_TABLE:
			handler = new DeleteTableChangeHandler();
			break;
		case CLOSE_SUBWINDOW:
			handler = new CloseSubWindowChangeHandler();
			break;
		case CREATE_COLUMN:
			handler = new CreateColumnChangeHandler();
			break;
		case COLUMN_CHANGE_NAME:
			handler = new ColumnNameChangeHandler();
			break;
		case COLUMN_CHANGE_TYPE:
			handler = new ColumnTypeChangeHandler();
			break;
		case COLUMN_CHANGE_ALLOW_BLANKS:
			handler = new ColumnAllowBlanksChangeHandler();
			break;
		case COLUMN_CHANGE_DEFAULT_VALUE:
			handler = new ColumnDefaultValueChangeHandler();
			break;
		case DELETE_COLUMN:
			handler = new DeleteColumnChangeHandler();
			break;
		case CREATE_ROW:
			handler = new CreateRowChangeHandler();
			break;
		case ROW_EDITED:
			handler = new RowEditedChangeHandler();
			break;
		case DELETE_ROW:
			handler = new DeleteRowChangeHandler();
			break;
		case CREATE_TABLESSUBWINDOW:
			handler = new CreateTablesSubWindowChangeHandler();
			break;
		case CREATE_TABLEDESIGNWINDOW:
			handler = new CreateTableDesignSubWindowChangeHandler();
			break;
		case CREATE_TABLEROWSWINDOW:
			handler = new CreateTableRowsSubWindowChangeHandler();
		default:
			break;
		}

		if (handler != null) {
			handler.handleChange(evt, uifacade, domainfacade);
		}
	}

}
