package ui.model.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class EditableTextField extends TextField {

	/**
	 * Variable to determine if the checkbox is selected
	 */
	private boolean selected = false;

	/**
	 * Position of the cursor.
	 */
	private int position;

	public EditableTextField(int x, int y, int width, int height, String defaultValue) {
		this(x, y, width, height, false, defaultValue);
	}

	public EditableTextField(int x, int y, int width, int height, boolean hidden, String defaultValue) {
		super(x, y, width, height, hidden, defaultValue);
		this.resetPosition();
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
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (isWithinComponent(x, y)) {
			if (!this.selected) {
				resetPosition();
				this.selected = true;
			}
		} else {
			this.selected = false;
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		if (this.selected) {
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
			}
		}
	}

	private void deleteChar() {
		String left = getText().substring(0, position);
		String right = getText().substring(position + 1, getText().length());
		setText(left + right);
		moveCursorLocationLeft();
	}

	private void setPosition(int pos) {
		if (pos <= 0 || pos > getText().length() - 1) {
			throw new IllegalArgumentException(
					"The position cannot be set to below 0 or higher than the length of the text.");
		}
		this.position = pos;
	}

	public int getPosition() {
		return position;
	}

	private void moveCursorLocationRight() {
		if (this.position < this.getText().length() - 1) {
			this.setPosition(getPosition() + 1);
		}

	}

	private void moveCursorLocationLeft() {
		if (this.position > 1) {
			this.setPosition(getPosition() - 1);
		}

	}

	private void resetPosition() {
		this.setPosition(getText().length() - 1);
	}

}
