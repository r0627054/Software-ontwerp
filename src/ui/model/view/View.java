package ui.model.view;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.model.viewmodes.TablesViewMode;
import ui.model.viewmodes.ViewMode;
import ui.model.window.CanvasWindow;

/**
 * 
 * A view is a subclass of CanvasWindow.
 * This is a frame which contains all the different viewModes. 
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public class View extends CanvasWindow implements PropertyChangeListener{

	/**
	 * Variable storing the current (view mode)
	 */
	private ViewMode currentMode;
	
	/**
	 * Variable storing all the different viewModes. With a String as key.
	 */
	private Map<String, ViewMode> viewModes = new HashMap<String, ViewMode>();

	/**
	 * Initialise this new view component with the given title.
	 * 
	 * @param title
	 *        The title of the view.
	 */
	public View(String title) {
		super(title);
		initTestingModes();
	}

	private void initTestingModes() {
		TablesViewMode tablesViewMode1 = new TablesViewMode("Tables1", this);
		TablesViewMode tablesViewMode2 = new TablesViewMode("Tables2", this);
		tablesViewMode1.addPropertyChangeListener(this);
		tablesViewMode2.addPropertyChangeListener(this);
		addViewMode(tablesViewMode1);
		addViewMode(tablesViewMode2);
		changeModeTo("Tables1");
	}

	/**
	 * Paints the current mode.
	 */
	@Override
	public void paint(Graphics g) {
		this.currentMode.paint(g);
	}

	public void repaintTest() {
		this.repaint();
	}
	
	/**
	 * Returns the current mode of the view.
	 */
	public ViewMode getCurrentMode() {
		return currentMode;
	}
	
	/**
	 * Returns the view mode associated with the given String.
	 * 
	 * @param name
	 *        The name (key) of the view mode.
	 * @return The ViewMode associated with the given String.
	 * @throws IllegalArgumentException
	 *         The name is null.
	 *         | name == null
	 */
	public ViewMode getViewMode(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Cannot get ViewMode for null string");
		}
		return this.viewModes.get(name);
	}

	/**
	 * Changes the current mode with the mode associated with the given key.
	 * 
	 * @param key
	 * @throws IllegalArgumentException
	 *         if the newCurrentMode is null
	 *         | viewModes.get(key) == null
	 * @post The current mode is switched with the mode associated with the given key.
	 *         | new.getCurrentMode == viewModes.get(key) 
	 */
	public void changeModeTo(String key) {
		ViewMode newCurrentMode = viewModes.get(key);
		if (newCurrentMode == null) {
			throw new IllegalArgumentException("No mode found for key");
		} else
			this.setCurrentMode(newCurrentMode);

	}

	
	public void addViewMode(ViewMode mode) {
		if (this.viewModes.containsKey(mode.getName())) {
			throw new IllegalArgumentException("Mode with duplicate name already exists");
		} else
			this.viewModes.put(mode.getName(), mode);
	}

	private void setCurrentMode(ViewMode currentMode) {
		this.currentMode = currentMode;
	}

	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		currentMode.mouseClicked(id, x, y, clickCount);

		//this.repaint();
		// Steven: Ik denk niet dat dit de juiste plaats is om te repainten?
		// Weet niet goed hoe ik terug vanaf de Components naar de View kan callen om te
		// repainten.
		// Eventueel Observer gebruiken?

		// Dit zorgt momenteel voor elke mouse click/drag/release voor een volledige
		// repaint
	}

	@Override
	protected void handleKeyEvent(int id, int keyCode, char keyChar) {
		currentMode.keyPressed(id, keyCode, keyChar);
		
		//this.repaint();
		//Same as handleMouseEvent
		//Dit is enkel om te testen/tonen dat EditableTextField werkt!
	}

	public void setTablesViewModeListValues(List<String> tableNames) {
		//this.viewModes.get("Tables1").
		
		//Steven: Hoe setten we deze values?
		//1) Oftewel casten we hier naar TablesViewMode bij deze 'get' en maken in TablesViewMode een functie
		//-> Lijkt me niet echt juist, te hardcoded
		
		//2) Oftewel slagen we specifieke ViewModes (ipv List<ViewMode>) toch op
		//-> Lijkt me ook niet echt juist, niet super goed ivm toekomstige nieuwe ViewModes
		
		//3) De benodigde functie in ViewMode zelf schrijven is al helemaal fout
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("View PropertyChange called");
		this.repaint();
		
	}

}
