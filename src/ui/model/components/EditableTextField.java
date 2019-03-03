package ui.model.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.sun.glass.events.KeyEvent;

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
		super(x, y, width, height, false, defaultValue);
	}

	public EditableTextField(int x, int y, int width, int height, boolean hidden, String defaultValue) {
		this(x, y, width, height, hidden, defaultValue); 		
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
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getWidth(), getHeight());

		g.setClip(getX(), getY(), getWidth(), getHeight());

		Font f = new Font("TimesRoman", Font.PLAIN, getHeight());
		g.setFont(f);

		g.drawString(getText(), getX(), getOffsetY());
		
		//TODO: blinkende cursor
		//Eventueel een '|' char toevoegen & verwijderen vd tekst elke seconde?
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		if (isWithinComponent(x, y)) {
			this.selected = true;
		} else {
			this.selected = false;
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		if (this.selected) {
			System.out.println(id);
			System.out.println(keyCode);
			System.out.println(keyChar);
			if (id == KeyEvent.PRESS && keyCode == 8) {
				String text = getText();
				setText(text.substring(0, text.length() - 1));
				System.out.println(text);
			}
			
			//TODO: links & rechts op keyboard
		}
	}

}
