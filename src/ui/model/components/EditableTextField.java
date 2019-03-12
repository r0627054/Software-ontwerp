package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.UUID;

import controller.handlers.ChangeEventType;

public class EditableTextField extends TextField {

	/**
	 * Variable to determine if the textfield is selected
	 */
	private boolean selected = false;

	/**
	 * Variable to determine if the texfield is selected for delete
	 */
	private boolean selectedForDelete = false;

	private String defaultValue;

	/**
	 * Position of the cursor.
	 */
	private int position;

	public EditableTextField(int x, int y, int width, int height, String defaultValue, UUID id) {
		this(x, y, width, height, false, defaultValue, id);
	}

	public EditableTextField(int x, int y, int width, int height, boolean hidden, String defaultValue, UUID id) {
		super(x, y, width, height, hidden, defaultValue, id);
		this.resetCursorPosition();
		this.setDefaultValue(defaultValue);
	}

	public EditableTextField(String string, UUID id) {
		this(0, 0, 50, 100, string, id); // TODO: Defaults
	}

	@Override
	public void paint(Graphics2D g) {

		if (this.isSelected()) {
			g.setColor(new Color(226, 226, 226));
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		} else if (this.isSelectedForDelete()) {
			g.setColor(Color.RED);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
		super.paint((Graphics2D) g.create());
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
//		System.out.println("X= " + getX() + "|y= " + getY() + "| xClick = " + x + "| yClick= " + y);
//		System.out.println("ISWITHIN: " + isWithinComponent(x, y));
		if (id == MouseEvent.MOUSE_CLICKED && isWithinComponent(x, y)) {
			if (!this.selected && clickCount == 1) {
				resetCursorPosition();
				this.setDefaultValue(this.getText());
				select();
			} else if (clickCount == 2) {
				this.doubleClicked();
			}
		}
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		if (this.isSelected()) {
			this.textChangeSubmit();
		}
	}

	
	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		if (isSelected()) {
			if (id == KeyEvent.KEY_PRESSED) {
				if (keyCode == KeyEvent.VK_LEFT) {
					moveCursorLocationLeft();
				}
				if (keyCode == KeyEvent.VK_RIGHT) {
					moveCursorLocationRight();
				}
				if (Character.isLetterOrDigit(keyChar) || keyChar == '@' || keyChar == '.') {
					String text = getText();
					setText(text + keyChar);
					moveCursorLocationRight();
					textChanged();
				}
				if (keyCode == KeyEvent.VK_BACK_SPACE) {
					deleteChar();
					textChanged();
				}
				if (keyCode == KeyEvent.VK_ENTER) {
					textChangeSubmit();
				}
				if (keyCode == KeyEvent.VK_ESCAPE) {
					this.setText(getDefaultValue());
					textResetSubmit();
				}
			}
		}
		if (isSelectedForDelete() && keyCode == KeyEvent.VK_DELETE && id == KeyEvent.KEY_PRESSED) {
			delete();
		}
	}

	private void textResetSubmit() {
		this.setError(false);
		this.setSelected(false);
		propertyChanged(this.getId(), ChangeEventType.VALUE.getEventString(), "", this.getDefaultValue());
	}

	private void delete() {
		propertyChanged(this.getId(), ChangeEventType.DELETE_TABLE.getEventString(), null, null);
	}

	private void textChanged() {
		this.setError(false);
		propertyChanged(this.getId(), ChangeEventType.VALUE.getEventString(), this.getDefaultValue(), this.getText());
	}

	private void textChangeSubmit() {
//		System.out.println("Submit ETF");
		this.setSelected(false);
		textChanged();
	}

	private void doubleClicked() {
//		System.out.println("Double Click ETF");
		this.setSelected(false);
		propertyChanged(this.getId(), ChangeEventType.DOUBLEClICK.getEventString(), null, this.getText());
	}

	private void select() {
		this.selected = true;
		propertyChanged();
	}

	private void unselect() {
		if (this.isSelected()) {
//			System.out.println("unselect ETF");
			this.setText(getDefaultValue());
			this.setSelected(false);
			this.setError(false);
		}
	}

	public boolean isSelected() {
		return this.selected;
	}

	private void setSelected(boolean selected) {
		this.selected = selected;
		propertyChanged();
	}

	private void deleteChar() {
		if (position != 0) {
			String left = getText().substring(0, position - 1);
			String right = getText().substring(position, getText().length());
			setText(left + right);
			moveCursorLocationLeft();
			if (isError())
				this.setError(false);
		}

	}

	private void setPosition(int pos) {
		if (pos < 0 || pos > getText().length()) {
			throw new IllegalArgumentException(
					"The position cannot be set to below 0 or higher than the length of the text.");
		}
		propertyChanged();
		this.position = pos;
	}

	public int getPosition() {
		return position;
	}

	private void moveCursorLocationRight() {
		if (this.position < this.getText().length()) {
			this.setPosition(getPosition() + 1);
		}
	}

	private void moveCursorLocationLeft() {
		if (this.position > 0) {
			this.setPosition(getPosition() - 1);
		}
	}

	private void resetCursorPosition() {
		this.setPosition(getText().length());
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	private void setDefaultValue(String defaultValue) {
		if (defaultValue == null) { //TODO hier kom je nooit omdat superklasse reeds checkt op nullvalues
			throw new IllegalArgumentException("The default value cannot be null in an editable textfield.");
		}
		if (!isError())
			this.defaultValue = defaultValue;
	}

	@Override
	protected void drawString(Graphics2D g) {
		if (selected) {
			g.drawString(getCursorString(), getX() + MARGIN, getOffsetY() - MARGIN);
		} else {
			super.drawString(g);
		}
	}

	private String getCursorString() {
		return getText().substring(0, position) + "|" + getText().substring(position, getText().length());
	}

	@Override
	public void throwError(UUID id) {
		if (this.getId().equals(id)) {
			super.throwError(id);
			this.setError(true);
		}
	}

	@Override
	public void setError(boolean error) {
		super.setError(error);

		if (error) {
			this.select();
			this.resetCursorPosition();
		}
	}

	public void setSelectedForDelete(boolean selected) {
		this.selectedForDelete = selected;
		propertyChanged();
	}

	public boolean isSelectedForDelete() {
		return this.selectedForDelete;
	}
}
