package ui.model.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class EditableTextField extends TextField {
	
	private boolean editmode;

	public EditableTextField(int x, int y, int width, int height, boolean hidden, String defaultValue) {
		super(x, y, width, height, hidden, defaultValue);
		this.editmode = false; 		
	}
	
	public EditableTextField(int x, int y, int width, int height, boolean hidden, String defaultValue, boolean editmode) {
		super(x, y, width, height, hidden, defaultValue);
		this.editmode = editmode;
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.setBackground(Color.BLACK);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		if(editmode) {
			g.setColor(Color.RED);
		}

		
		
		
		//Dit zorgt ervoor dat de tekst niet buiten de width/height gaat.
		g.setClip(getX(), getY(), getWidth(), getHeight());

		//Font setten met de hoogte vd param die je via de constructor meegeeft
		Font f = new Font("TimesRoman", Font.PLAIN, getHeight());
		g.setFont(f);
		
		//Je begint linksonder te tekenen
		g.drawString(getText(), getX(), getOffsetY());
		
	}

	
	
	
	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		//TODO Auto-generated method stub

		//indien dubbelklik -> edit mode
		if (id == MouseEvent.MOUSE_CLICKED && x <= getOffsetX() && y >= getY() && y <= getOffsetY() && clickCount == 2) {
			System.out.println("clickcount = " + clickCount);
			this.editmode = !editmode;
			System.out.println(super.getText());
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub
		
	}
}
