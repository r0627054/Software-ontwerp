package ui.model.components;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class TextField extends Component {
	private String text;

	public TextField(int x, int y, int width, int height, String text) {
		this(x, y, width, height, true, text);
	}

	public TextField(int x, int y, int width, int height, boolean hidden, String text) {
		super(x, y, width, height, hidden);
		this.setText(text);
	}

	public void setText(String text) {
		if (text != null && text.length() == 0)
			throw new IllegalArgumentException("Text of TextField cannot be empty.");

		this.text = text;
		propertyChanged();
	}

	public String getText() {
		return this.text;
	}
	
	public String getText() {
		return this.text;
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawRect(getX(), getY(), getWidth(), getHeight());

		// Dit zorgt ervoor dat de tekst niet buiten de width/height gaat.
		g.setClip(getX(), getY(), getWidth(), getHeight());

		// Font setten met de hoogte vd param die je via de constructor meegeeft
		Font f = new Font("TimesRoman", Font.PLAIN, getHeight());
		g.setFont(f);

		// Je begint linksonder te tekenen
		g.drawString(text, getX(), getOffsetY());
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

}
