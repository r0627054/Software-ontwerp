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

	private final ChangeEventType submitAction;

	/**
	 * 
	 *  Initialise this new toggleTextField with all the given variables.
	 * 
	 * @param x
	 *        The x-coordinate of the textField.
	 * @param y
	 *        The y-coordinate of the textField.
	 * @param width
	 *        The width of the textField.
	 * @param height
	 *        The height of the textField.
	 * @param text
	 *        The text stored in the textField.
	 * @param id
	 *        The id of the textField.
	 * @effect All the variables are of the TextField.
	 *       | super(x, y, width, height, text, id)
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
	 */
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (id == MouseEvent.MOUSE_PRESSED && getSubmitAction() != null) {
			this.propertyChanged(this.getId(), getSubmitAction(), getText(), null);

//			this.propertyChanged(this.getId(), ChangeEventType.TABLE_CHANGE_NAME, getText(), null);
		}
	}

	/**
	 * The UUID if the textField.
	 * Checks whether the error is thrown for this textField.
	 */
	@Override
	public void throwError(UUID id) {
		if (this.getId() == id) {
			super.throwError(id);
			this.setError(true);
		}
	}

	private ChangeEventType getSubmitAction() {
		return submitAction;
	}

}
