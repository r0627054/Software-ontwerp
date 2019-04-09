package ui.model.components;

import java.awt.event.MouseEvent;
import java.util.UUID;

import controller.handlers.ChangeEventType;

/**
 * A ToggleTextField is a subclass of a TextField.
 *  It toggles between different texts.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class ToggleTextField extends TextField {

	/**
	 * Variable storing the submit action of the toggleTextField.
	 */
	private final ChangeEventType submitAction;

	/**
	 * 
	 * Initialise this new toggleTextField with all the given variables.
	 *  By default the x and y-coordinates are 0. 
	 * @param width
	 *        The width of the textField.
	 * @param height
	 *        The height of the textField.
	 * @param text
	 *        The text stored in the textField.
	 * @param id
	 *        The id of the textField.
	 * @effect All the variables are of the TextField.
	 *       | super(0, 0, width, height, text, id)
	 */
	public ToggleTextField(int width, int height, String text, UUID id, ChangeEventType action) {
		super(0, 0, width, height, text, id);
		this.submitAction = action;
	}

	/**
	 * Handles the mouse Click.
	 *  And changes the table name.
	 *   
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The propertyChanged event is raised with the TABLE_CHANGE_NAME eventType when the mouse is pressed.
	 *         | if (id == MouseEvent.MOUSE_PRESSED) 
	 *         |	this.propertyChanged(this.getId(), ChangeEventType.TABLE_CHANGE_NAME, getText(), null)
	 *         
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_PRESSED && getSubmitAction() != null) {
			this.propertyChanged(this.getId(), getSubmitAction(), getText(), null);
		}
	}

	/**
	 * Checks whether the error is thrown for this textField.
	 * If the error is thrown for this component the error variable is set to true. 
	 * @param id
	 *        | The id of which element an error is thrown.
	 */
	@Override
	public void throwError(UUID id) {
		if (this.getId() == id) {
			super.throwError(id);
			this.setError(true);
		}
	}

	/**
	 * Returns the submit action of the ToggleTextField.
	 * @return the submit action of the ToggleTextField.
	 */
	private ChangeEventType getSubmitAction() {
		return submitAction;
	}

}
