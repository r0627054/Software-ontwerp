package ui.model.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.UUID;

import javax.swing.event.ChangeEvent;

import controller.handlers.ChangeEventType;

public class EditableTextField extends TextField {
	
	private final static int ERROR_RECT_SIZE = 2;


	/**
	 * Variable to determine if the textfield is selected
	 */
	private boolean selected = false;
	
	/**
	 * Variable to show if the user caused an error
	 */
	private boolean error = false;

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
		if (this.selected) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.LIGHT_GRAY);
		}
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		super.paint((Graphics2D) g.create());

		g.setStroke(new BasicStroke(ERROR_RECT_SIZE, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER));
		if (getError()) {
			g.setColor(Color.RED);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
//		System.out.println("X= " + getX() + "|y= " + getY() + "| xClick = " + x + "| yClick= " + y);
//		System.out.println("ISWITHIN: " + isWithinComponent(x, y));
		if (id == MouseEvent.MOUSE_CLICKED && isWithinComponent(x, y)) {
			if (!this.selected) {
				resetCursorPosition();
				this.setDefaultValue(this.getText());
				select();
			}
		}
	}

	@Override
	public void outsideClick() {
		unselect();
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		if (isSelected()) {
			if (id == KeyEvent.KEY_PRESSED) {
				if (keyCode == KeyEvent.VK_BACK_SPACE) {
					deleteChar();
				}
				if (keyCode == KeyEvent.VK_LEFT) {
					moveCursorLocationLeft();
				}
				if (keyCode == KeyEvent.VK_RIGHT) {
					moveCursorLocationRight();
				}
				if (Character.isLetterOrDigit(keyChar)) {
					String text = getText();
					setText(text + keyChar);
					moveCursorLocationRight();
				}
				if (keyCode == KeyEvent.VK_ENTER) {
					textChangeSubmit();
				}
			}
		}
	}

	private void textChangeSubmit() {
		this.selected = false;
		propertyChanged(this.getId(), ChangeEventType.VALUE.getEventString(), this.getDefaultValue(), this.getText());
	}

	private void select() {
		this.selected = true;
		propertyChanged();
	}

	private void unselect() {
		this.selected = false;
		propertyChanged();
	}

	public boolean isSelected() {
		return this.selected;
	}

	private void deleteChar() {
		if (position != 0) {
			String left = getText().substring(0, position - 1);
			String right = getText().substring(position, getText().length());
			setText(left + right);
			moveCursorLocationLeft();
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
		if (defaultValue == null) {
			throw new IllegalArgumentException("The default value cannot be null in an editable textfield.");
		}
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
			this.setText(getDefaultValue());
		}
	}

	public void setError(boolean error) {
		this.error = error;
	}

	private boolean getError() {
		return error;
	}
}
