package ui.model.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TextField extends Component {
	public Font font;
	public static final int MARGIN = 3;

	private String text;

	public TextField(int x, int y, int width, int height, String text) {
		this(x, y, width, height, true, text);
	}

	public TextField(int x, int y, int width, int height, boolean hidden, String text) {
		super(x, y, width, height, hidden);
		this.setText(text);
		this.setFont(new Font("NewTimesRoman", Font.PLAIN, 12));
	}

	private void setFont(Font font) {
		if (font == null)
			throw new IllegalArgumentException("Font cannot be null inside a TextField.");
		this.font = font;
	}

	public Font getFont() {
		return this.font;
	}

	public void setText(String text) {
		if (text == null)
			throw new IllegalArgumentException("Text of TextField cannot be empty.");

		this.text = text;
		propertyChanged();
	}

	public String getText() {
		return this.text;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getWidth(), getHeight());

		g.setClip(getX() + MARGIN, getY() + MARGIN, getWidth() - 2 * MARGIN, getHeight() - 2 * MARGIN);

		g.setFont(this.getFont());
		//g.getFontMetrics().stringWidth("breedte van deze string");

		g.drawString(text, getX() + MARGIN, getOffsetY() - MARGIN);
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
	}

}
