package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.UUID;

import controller.handlers.ChangeEventType;

/**
 * An EditableTextField is a subclass of TextField which is made editable.
 *  Text can be edited and updated, errors can be shown.
 * 
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class EditableTextField extends TextField {

	/**
	 * Variable storing the submit action.
	 */
	private final ChangeEventType submitAction;

	/**
	 * Variable storing the double click action.
	 */
	private final ChangeEventType doubleClickAction;

	/**
	 * Variable storing the delete action.
	 */
	private final ChangeEventType deleteAction;

	/**
	 * Variable to determine if the text field is selected
	 */
	private boolean selected = false;

	/**
	 * Variable to determine if the text field is selected for delete
	 */
	private boolean selectedForDelete = false;

	/**
	 * Variable storing the default value.
	 */
	private String defaultValue;

	/**
	 * Position of the cursor.
	 */
	private int position;

	/**
	 * Initialise this new component with all the given variables.
	 * By default is the editableTextField not hidden.
	 * 
	 * @param x
	 *        The x-coordinate of the EditableTextField.
	 * @param y
	 *        The y-coordinate of the EditableTextField.
	 * @param width
	 *        The width of the EditableTextField.
	 * @param height
	 *        The height of the EditableTextField.
	 * @param defaultValue
	 *        The default value of the EditableTextField.
	 * @param id
	 *        The id of the EditableTextField
	 * @param submitAction
	 *        The action fired when a submit is registered.
	 * @param doubleClickAction
	 *        The action fired when a double click is registered.
	 * @param deleteAction
	 *        The action fired when a delete is registered.
	 * @effect All the variables are set.
	 *        | this(x, y, width, height, false, defaultValue, id)
	 */
	public EditableTextField(int x, int y, int width, int height, String defaultValue, UUID id,
			ChangeEventType submitAction, ChangeEventType doubleClickAction, ChangeEventType deleteAction) {
		super(x, y, width, height, false, defaultValue, id);
		this.submitAction = submitAction;
		this.doubleClickAction = doubleClickAction;
		this.deleteAction = deleteAction;

		this.resetCursorPosition();
	}

	/**
	 * Initialise this new component with all the given variables.
	 * The EditableTextField uses default x,y, width and height.
	 * 
	 * @param defaultValue
	 *        | The default value of the editableTextField
	 * @param id
	 *        | The id of the EditableTextField.
	 * @param submitAction
	 *        The action fired when a submit is registered.
	 * @param doubleClickAction
	 *        The action fired when a double click is registered.
	 * @param deleteAction
	 *        The action fired when a delete is registered.
	 * @effect All the variables are set.
	 *        | this(x, y, width, height, false, defaultValue, id)
	 */
	public EditableTextField(String defaultValue, UUID id, ChangeEventType submitAction,
			ChangeEventType doubleClickAction, ChangeEventType deleteAction) {
		this(0, 0, 50, 100, defaultValue, id, submitAction, doubleClickAction, deleteAction); // TODO: Defaults
	}

	/**
	 * Submits a delete Table Event.
	 */
	private void delete() {
		if (this.getDeleteAction() != null) {
			propertyChanged(this.getId(), getDeleteAction(), null, null);
		}
	}

	/**
	 * Deletes a character of the text.
	 */
	private void deleteChar() {
		if (getPosition() != 0) {
			String left = getText().substring(0, getPosition() - 1);
			String right = getText().substring(getPosition(), getText().length());
			setText(left + right);
			moveCursorLocationLeft();
			if (isError())
				this.setError(false);
		}
	}

	/**
	 * Fires a property change for double clicking
	 * and unselects the editableTextField.
	 */
	private void doubleClicked() {
		if (this.getDoubleClickAction() != null) {
			this.setSelected(false);
			propertyChanged(this.getId(), getDoubleClickAction(), null, this.getText());
		}
	}

	/**
	 * Draws the actual String with the cursor.
	 * 
	 * @param g 
	 * 		This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	protected void drawString(Graphics2D g) {
		if (selected) {
			g.drawString(getCursorString(), getX() + MARGIN, getOffsetY() - MARGIN);
		} else {
			super.drawString(g);
		}
	}

	/**
	 * Returns the text with the cursor at the correct position.
	 */
	private String getCursorString() {
		return getText().substring(0, position) + "|" + getText().substring(position, getText().length());
	}

	/**
	 * Returns the default value.
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Returns the cursor position.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * returns whether or not the editableTextField is selected.
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Returns whether or not the editable textField is selected for deletion.
	 */
	public boolean isSelectedForDelete() {
		return this.selectedForDelete;
	}

	/**
	 * Handles the key pressed Event.
	 * 
	 * @param id
	 *        | The id of the key pressed event. 
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 */
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {

		if (isSelected()) {
			if (id == KeyEvent.KEY_PRESSED) {
				if (keyCode == KeyEvent.VK_LEFT) {
					moveCursorLocationLeft();
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					moveCursorLocationRight();
				} else if (keyCode == KeyEvent.VK_BACK_SPACE) {
					deleteChar();
				} else if (keyCode == KeyEvent.VK_ENTER) {
					textChangeSubmit();
				} else if (keyCode == KeyEvent.VK_ESCAPE) {
					this.setText(getDefaultValue());
					textResetSubmit();
				} else if (Character.isLetterOrDigit(keyChar) || keyChar == '@' || keyChar == '.' || keyChar == ' '
						|| keyChar == '<' || keyChar == '>' || keyChar == '=' || keyChar == '+' || keyChar == '-'
						|| keyChar == ',' || keyChar == '(' || keyChar == ')') {
					String text = getText();
					setText(text.substring(0, position) + keyChar + text.substring(position, text.length()));
					moveCursorLocationRight();
					this.setError(false);
				}
			}
		}
		if (isSelectedForDelete() && keyCode == KeyEvent.VK_DELETE && id == KeyEvent.KEY_PRESSED) {
			delete();
		}
	}

	/**
	 * Handles the click event.
	 * If the user clicks inside the editableTextField.
	 * The textField is selected now and can be edited.
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
		if (id == MouseEvent.MOUSE_PRESSED && isWithinComponent(x, y)) {
			if (!this.selected && clickCount == 1) {
				resetCursorPosition();
				this.setDefaultValue(this.getText());
				this.setSelected(true);
			} else if (clickCount == 2) {
				this.doubleClicked();
			}
		}
	}

	/**
	 * Moves the cursor position one location to the left.
	 */
	private void moveCursorLocationLeft() {
		if (this.position > 0) {
			this.setPosition(getPosition() - 1);
		}
	}

	/**
	 * Moves the cursor position one location to the right.
	 */
	private void moveCursorLocationRight() {
		if (this.position < this.getText().length()) {
			this.setPosition(getPosition() + 1);
		}
	}

	/**
	 * Checks whether the textField was selected.
	 * If it was selected the text is submitted.
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
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (this.isSelected()) {
			this.textChangeSubmit();
		}
	}

	/**
	 * Paints the text in the editableTextField.
	 * @param g 
	 * 		This object offers the methods that allow you to paint on the canvas.
	 */
	@Override
	public void paint(Graphics2D g) {

		if (this.isSelected()) {
			g.setColor(new Color(226, 226, 226));
			g.fillRect(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);
		} else if (this.isSelectedForDelete()) {
			g.setColor(Color.RED);
			g.fillRect(getX() + 1, getY() + 1, getWidth() - 1, getHeight() - 1);
		}
		super.paint((Graphics2D) g.create());
	}

	/**
	 * Resets the cursor position to the end of the String.
	 */
	private void resetCursorPosition() {
		this.setPosition(getText().length());
	}

	/**
	 * Sets the default value of the editableTextField.
	 * 
	 * @param defaultValue
	 *        | the default value for the editableTextField
	 * @throws IllegalArgumentException if the default value equals null
	 *        | defaultValue == null
	 * @post when there is no error, the default value is set. 
	 *        | this.defaultValue = defaultValue;
	 */
	private void setDefaultValue(String defaultValue) {
		if (defaultValue == null) {
			throw new IllegalArgumentException("The default value cannot be null in an editable textfield.");
		}
		if (!isError())
			this.defaultValue = defaultValue;
	}

	/**
	 * Sets an error and resets the cursor position.
	 */
	@Override
	public void setError(boolean error) {
		super.setError(error);
		if (error) {
			this.setSelected(true);
			this.resetCursorPosition();
		}
	}

	/**
	 * Sets the cursor position.
	 * 
	 * @param pos
	 *        | The position of the cursor
	 * @throws IllegalArgumentException if the position is lower than 0 and greater than the length of the text.
	 *        | pos < 0 || pos > getText().length()
	 * @post The position of the cursor equals the parameter position.
	 *        | this.position == pos
	 */
	private void setPosition(int pos) {
		if (pos < 0 || pos > getText().length()) {
			throw new IllegalArgumentException(
					"The position cannot be set to below 0 or higher than the length of the text.");
		}
		propertyChanged();
		this.position = pos;
	}

	/**
	 * Sets the selected variable of an editableTextField.
	 * @param selected
	 *        | Variable shows whether or not the editableTextField is selected.
	 */
	private void setSelected(boolean selected) {
		this.selected = selected;

		if (this.isSelectedForDelete()) {
			this.setSelectedForDelete(false);
		}
		propertyChanged();
	}

	/**
	 * Sets the selected for delete variable
	 * @param selected
	 *        | Whether or not the editableTextField is selected for deletion.
	 */
	public void setSelectedForDelete(boolean selected) {
		boolean changed = (this.selectedForDelete && !selected) || (!this.selectedForDelete && selected);
		this.selectedForDelete = selected;

		if (changed) {
			propertyChanged();
		}
	}

	/**
	 * Submits a table change name. 
	 * sets the error to false.
	 */
	private void textChanged() {
		if (this.getSubmitAction() != null) {
			this.setError(false);
			propertyChanged(this.getId(), getSubmitAction(), this.getDefaultValue(), this.getText());
		}
	}

	/**
	 * Submits a textChange and unselects the editableTextField.
	 */
	private void textChangeSubmit() {
		this.setSelected(false);
		textChanged();
	}

	/**
	 * Resets the text; the error is set to false, the box is not selected anymore and the default value is submitted.
	 */
	private void textResetSubmit() {
		if (getSubmitAction() != null) {
			this.setError(false);
			this.setSelected(false);
			propertyChanged(this.getId(), getSubmitAction(), "", this.getDefaultValue());
		}
	}

	/**
	 * The editableTextField shows an error if the id's are the same.
	 * 
	 * @param id
	 *        | The id of which element an error is thrown.
	 */
	@Override
	public void throwError(UUID id) {
		if (this.getId().equals(id)) {
			super.throwError(id);
			this.setError(true);
		}
	}

	/**
	 * Returns the submit action of the editableTextField.
	 * @return The submit action of the editableTextField.
	 */
	private ChangeEventType getSubmitAction() {
		return submitAction;
	}

	/**
	 * Returns the double click action of the editableTextField.
	 * @return the double click action of the editableTextField.
	 */
	private ChangeEventType getDoubleClickAction() {
		return doubleClickAction;
	}

	/**
	 * Returns the delete action of the editableTextField.
	 * @return the delete action of the editableTextField.
	 */
	private ChangeEventType getDeleteAction() {
		return deleteAction;
	}

}
