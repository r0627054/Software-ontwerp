package ui.model.window.sub;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;
import controller.observer.PropertyChangeSupport;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.TitleBar;

/**
 * A SubWindow is a window which can be seen in the view (CanvasWindow)
 *  A SubWindow is an actual window inside in the view..
 *  The SubWindow keeps track of all the sizes and all the clickListeners/KeyListeners of the components.
 *
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public abstract class SubWindow implements PropertyChangeListener {

	/**
	 * The constant storing the default X coordinate.
	 */
	public static final int DEFAULT_X = 0;

	/**
	 * The constant storing the default Y coordinate.
	 */
	public static final int DEFAULT_Y = 0;

	/**
	 * The constant storing the default width.
	 */
	public static final int DEFAULT_WIDTH = 500;

	/**
	 * The constant storing the default height.
	 */
	public static final int DEFAULT_HEIGHT = 500;

	/**
	 * The constant storing the draggable width size.
	 * This is the size starting from the border which, is used for resizing.
	 */
	public static final int DRAG_BORDER_SIZE = 10;

	/**
	 * The constant storing the size of the title bar.
	 */
	public static final int TITLE_BAR_SIZE = 40;

	/**
	 * The constant containing the width of the button.
	 */
	public static final int BUTTON_WIDTH = 50;

	/**
	 * The constant storing the minimal width of the subWindow.
	 */
	public static final int MIN_WIDTH = 200;

	/**
	 * The constant storing the minimal height of the subWindow.
	 */
	public static final int MIN_HEIGHT = 200;

	/**
	 * The constant storing the content offset.
	 */
	public static final int CONTENT_OFFSET_X = 40;

	/**
	 * The constant storing the content offset.
	 */
	public static final int CONTENT_OFFSET_Y = 60;

	/**
	 * The variable storing the x-coordinate.
	 */
	private int x;

	/**
	 * The variable storing the y-coordinate.
	 */
	private int y;

	/**
	 * The variable storing the width.
	 */
	private int width;

	/**
	 * The variable storing the height.
	 */
	private int height;

	/**
	 * The variable storing whether the window is currently resizing the X coordinate, on the right side of the window.
	 */
	private boolean resizeRightX = false;

	/**
	 * The variable storing whether the window is currently resizing the X coordinate, on the left side of the window.
	 */
	private boolean resizeLeftX = false;

	/**
	 * The variable storing whether the window is currently resizing the Y coordinate, at the bottom of the window.
	 */
	private boolean resizeBottomY = false;

	/**
	 * The variable storing whether the window is currently resizing the Y coordinate, at the top of the window.
	 */
	private boolean resizeTopY = false;

	/**
	 * The variable storing whether the window is currently dragging.
	 */
	private boolean dragWindow = false;

	/**
	 * The x-coordinate variable storing where the user clicked for dragging the window.
	 */
	private int windowDragX;

	/**
	 * The y-coordinate variable storing where the user clicked for dragging the window.
	 */
	private int windowDragY;

	/**
	 * Variable storing the titleBar.
	 */
	private TitleBar titleBar;

	/**
	 * Variable storing the container with the components of the SubWindow.
	 */
	private Container container;

	/**
	 * The variable storing all the clickListeners of the components.
	 */
	private List<Component> clickListeners = new ArrayList<>();

	/**
	 * The variable storing all the keyListeners of the components.
	 */
	private List<Component> keyListeners = new ArrayList<>();

	/**
	 * Variable storing all the listeners.
	 */
	private List<Component> storedListeners;

	/**
	 * The variable storing the propertyChangeSupport.
	 */
	private PropertyChangeSupport support;

	/**
	 * Variable storing the UUID of the window.
	 */
	private UUID id;

	/**
	 * Variable storing whether the subWindow is paused or not.
	 */
	private boolean paused = false;

	/**
	 * Initialises a new Subwindow with a given title and an Id.
	 *  All the other variables are set using the default values.
	 * @param id    The id for the subWindow.
	 * @param title The title for the subWindow.
	 */
	public SubWindow(UUID id, String title) {
		this.setSupport(new PropertyChangeSupport());
		this.setStoredListeners(new ArrayList<>());
		this.setId(id);

		this.setTitleBar(new TitleBar(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, TITLE_BAR_SIZE, DRAG_BORDER_SIZE,
				CONTENT_OFFSET_X, BUTTON_WIDTH, title));
		this.addClickListener(getTitleBar());
		this.getTitleBar().addPropertyChangeListener(this);

		this.setX(DEFAULT_X);
		this.setY(DEFAULT_Y);
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
	}

	/**
	 * Adds a propertyChangeListener to support variable.
	 * @param pcl
	 *        | The propertyChangeListener which needs to be added.
	 * @effect The propertyChangeListener is added to the support variable.
	 *        | support.addPropertyChangeListener(pcl) 
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a propertyChangeListener from the support variable.
	 * @param pcl
	 *        | the propertyChangeListener which will be removed
	 * @effect The propertyChangeListener is removed to the support variable.
	 *        | support.removePropertyChangeListener(pcl) 
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	/**
	 * Draws all the components of the subWindow.
	 * @param g
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 */
	public void paint(Graphics g, boolean isActiveSubWindow) {
		g.setClip(getX(), getY(), getWidth(), getHeight());
		Color oldColor = g.getColor();
		if (isActiveSubWindow) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(new Color(225, 225, 225));
		}
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(oldColor);
		this.getTitleBar().paint((Graphics2D) g);
		for (Component component : getContainer().getComponentsList()) {
			component.paint((Graphics2D) g.create());
		}

		this.drawBorder((Graphics2D) g);

	}

	/**
	 * Draws the border of the window. It also draws the resizing area's.
	 * This visualises where the user can resize the window.
	 *
	 * @param g This object offers the methods that allow you to paint on the canvas.
	 * @post The graphics object draws the different rectangles.
	 */
	private void drawBorder(Graphics2D g) {
		g.setColor(new Color((float) 0.9, (float) 0.9, (float) 0.9, (float) 0.5));
		// vertical bars
		g.fillRect(getOffsetX() - DRAG_BORDER_SIZE, getY() + DRAG_BORDER_SIZE, DRAG_BORDER_SIZE,
				getHeight() - (2 * DRAG_BORDER_SIZE));
		g.fillRect(this.getX(), this.getY() + DRAG_BORDER_SIZE, DRAG_BORDER_SIZE, getHeight() - (2 * DRAG_BORDER_SIZE));

		// horizontal bars
		g.fillRect(getX() + DRAG_BORDER_SIZE, getOffsetY() - DRAG_BORDER_SIZE, getWidth() - (2 * DRAG_BORDER_SIZE),
				DRAG_BORDER_SIZE);
		g.fillRect(this.getX() + DRAG_BORDER_SIZE, this.getY(), getWidth() - (2 * DRAG_BORDER_SIZE), DRAG_BORDER_SIZE);

		// corner squares
		g.setColor(new Color((float) 0.8, (float) 0.8, (float) 0.8, (float) 0.5));
		g.fillRect(getX(), getY(), DRAG_BORDER_SIZE, DRAG_BORDER_SIZE);
		g.fillRect(getOffsetX() - DRAG_BORDER_SIZE, getY(), DRAG_BORDER_SIZE, DRAG_BORDER_SIZE);
		g.fillRect(getX(), getOffsetY() - DRAG_BORDER_SIZE, DRAG_BORDER_SIZE, DRAG_BORDER_SIZE);
		g.fillRect(getOffsetX() - DRAG_BORDER_SIZE, getOffsetY() - DRAG_BORDER_SIZE, DRAG_BORDER_SIZE,
				DRAG_BORDER_SIZE);

		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);
	}

	/**
	 * Adds a component to the list of ClickListeners.
	 * If the component isn't already in the list.
	 * @param c
	 *        |The component which will be added to the clickListeners
	 * @throws IllegalArgumentException when the component equals null
	 *        | c == null
	 * @effect the component is added to the list of ClickListeners if the component isn't already inside the list
	 *        |  if (!this.getClickListeners().contains(c))
	 *        | 	this.clickListeners.add(c);
	 */
	protected void addClickListener(Component c) {
		if (c == null)
			throw new IllegalArgumentException("A new click listener cannot be null");
		if (!this.getClickListeners().contains(c))
			this.clickListeners.add(c);
	}

	/**
	 * Adds a component to the list of KeyListeners.
	 * If the component isn't already in the list.
	 * @param c
	 *        |The component which will be added to the KeyListeners
	 * @throws IllegalArgumentException when the component equals null
	 *        | c == null
	 * @effect The component is added to the list of KeyListeners.
	 *        |  if (!this.getKeyListeners().contains(c))
	 *        | 	this.keyListeners.add(c);
	 */
	protected void addKeyListener(Component c) {
		if (c == null)
			throw new IllegalArgumentException("A new key listener cannot be null");
		if (!this.getKeyListeners().contains(c))
			this.keyListeners.add(c);
	}

	/**
	 * Tells every component of the SubWindow whether there was a click inside or outside the component.
	 *  A copy is made to make sure there is no editing of the list while it is looping.
	 *
	 * @param id
	 *        | The id of the mouse event.
	 * @param x
	 *        | The x-coordinate of the component.
	 * @param y
	 *        | The y-coordinate of the component.
	 * @param clickCount
	 *        | The count of clicks.
	 * @effect The components are called an told whether there was a click inside or outside.
	 */
	public void mouseClicked(int id, int x, int y, int clickCount) {
		this.handleResizing(id, x, y);
		this.handleMoving(id, x, y);

		if (isWithinComponent(x, y, DRAG_BORDER_SIZE)) {
			List<Component> currentClickListeners = new ArrayList<>(getClickListeners());
			for (Component c : currentClickListeners) {
				if (c.isWithinComponent(x, y)) {
					c.mouseClicked(id, x, y, clickCount);
				} else {
					c.outsideClick(id, x, y, clickCount);
				}
			}
		}
	}

	/**
	 * This method handles the actual dragging of the window.
	 * The window can be dragged, using the title bar area.
	 *
	 * @param id The id of the mouse event.
	 * @param x The x-coordinate of the mouse event.
	 * @param y The y-coordinate of the mouse event.
	 * @post If the window is dragged properly the X and Y-coordinate of the window are changed.
	 */
	private void handleMoving(int id, int x, int y) {
		if (id == MouseEvent.MOUSE_PRESSED && y >= (this.getY() + DRAG_BORDER_SIZE) && y <= this.getY() + TITLE_BAR_SIZE
				&& x > getX() && x < getX() + getWidth() - BUTTON_WIDTH) {
			this.dragWindow = true;
			this.windowDragX = x - getX();
			this.windowDragY = y - getY();
		}
		if (id == MouseEvent.MOUSE_RELEASED) {
			this.dragWindow = false;
		}

		if (id == MouseEvent.MOUSE_DRAGGED && dragWindow) {
			try {
				this.setX(x - windowDragX);
			} catch (IllegalArgumentException e) {
				this.setX(0);
				// the window can be dragged outside (this will give an negative X value) and
				// result in an IllegalArgumentException.
			}
			try {
				this.setY(y - windowDragY);
			} catch (IllegalArgumentException e) {
				this.setY(0);
				// the window can be dragged outside (this will give an negative Y value) and
				// result in an IllegalArgumentException.
			}
			this.propertyChange(new PropertyChangeEvent(ChangeEventType.REPAINT));
		}

	}

	/**
	 * Handles the resizing feature of the window.
	 * It resizes the window by dragging the corners or borders of the subWindow.
	 * @param id The mouse event id.
	 * @param x  The x coordinate of the mouse event.
	 * @param y  The y coordinate of the mouse event.
	 * @effect The new X-coordinate, Y-coordinate, width and height are set depending on the resizing.
	 */
	protected void handleResizing(int id, int x, int y) {
		if (id == MouseEvent.MOUSE_PRESSED) {
			if (x < getOffsetX() && x > (getOffsetX() - DRAG_BORDER_SIZE) && y > getY() && y < getY() + getHeight()) {
				this.resizeRightX = true;
			}
			if (x > this.getX() && x < (this.getX() + DRAG_BORDER_SIZE) && y > getY() && y < getY() + getHeight()) {
				this.resizeLeftX = true;
			}
			if (y < getOffsetY() && y > getOffsetY() - DRAG_BORDER_SIZE && x > getX() && x < getX() + getWidth()) {
				this.resizeBottomY = true;
			}
			if (y > this.getY() && y < (this.getY() + DRAG_BORDER_SIZE) && x > getX() && x < getX() + getWidth()) {
				this.resizeTopY = true;
			}
		}
		if (id == MouseEvent.MOUSE_RELEASED) {
			this.resizeRightX = false;
			this.resizeLeftX = false;
			this.resizeBottomY = false;
			this.resizeTopY = false;
		}
		if (id == MouseEvent.MOUSE_DRAGGED
				&& (this.resizeRightX || this.resizeLeftX || this.resizeBottomY || this.resizeTopY) && x >= 0
				&& y >= 0) {
			if (this.resizeRightX) {
				this.setWidth(x - getX());
			}
			if (this.resizeLeftX) {
				if ((getOffsetX() - x) >= MIN_WIDTH) {
					this.setWidth(getOffsetX() - x);
					this.setX(x);
				}
			}
			if (this.resizeBottomY) {
				if ((y - getY()) >= MIN_HEIGHT) {
					this.setHeight(y - getY());
				}
			}
			if (this.resizeTopY) {
				if ((getOffsetY() - y) >= MIN_HEIGHT) {
					this.setHeight(getOffsetY() - y);
					this.setY(y);
				}
			}
			this.propertyChange(new PropertyChangeEvent(ChangeEventType.REPAINT));
		}
	}

	/**
	 * Delegates the keyPressed event to the components of the SubWindow.
	 * A copy is made to make sure there is no editing of the list while it is looping.
	 *
	 * @param id
	 *        | The id of the key pressed event.
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 * @effect The components are called and the press is given to the component
	 *        | for (Component c : currentKeyListeners) 
	 *        | 	c.keyPressed(id, keyCode, keyChar);
	 */
	public void keyPressed(int id, int keyCode, char keyChar) {
		List<Component> currentKeyListeners = new ArrayList<>(getKeyListeners());
		for (Component c : currentKeyListeners) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	/**
	 * Returns whether or not the component already is in the list of components.
	 *
	 * @param component
	 *        | The component which will be checked if it is in the list of components.
	 * @return true if the component occurs in the list of components; otherwise false.
	 *        | this.getComponents().contains(component)
	 */
	public boolean hasComponent(Component component) {
		return this.getContainer().getComponentsList().contains(component);
	}

	/**
	 * This method fires a property change with the given propertyChangeEvent.
	 * @param evt
	 *        | The propertyChangeEvent used to fire the change.
	 * @effect The support variable fires a propertyChange with the given evt.
	 *        | this.support.firePropertyChange(evt).
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.support.firePropertyChange(evt);
	}

	/**
	 * Handles the throw error of a component with the given ID.
	 * @param id
	 *        | The id of which element an error is thrown.
	 * @param newValue    The new value for the component.
	 * @param columnIndex The index of the component which contains the error.
	 */
	public abstract void throwError(UUID id, int columnIndex, Object newValue);

	/**
	 * Removes all the clickListeners and KeyListeners from the SubWindow.
	 * @effect The click and key listeners are cleared.
	 *         | this.clickListeners.clear()
	 *         | this.keyListeners.clear()
	 */
	protected void removeAllClickAndKeyListeners() {
		this.clickListeners.clear();
		this.keyListeners.clear();
	}

	/**
	 * Removes all the clickListeners and KeyListeners from the SubWindow.
	 * But the titleBar is kept in the list of components.
	 */
	protected void removeContentClickAndKeyListeners() {
		this.clickListeners.clear();
		this.keyListeners.clear();
		this.addClickListener(getTitleBar());
		this.addKeyListener(getTitleBar());
	}

	/**
	 * Returns all the clickListeners of the SubWindow.
	 */
	protected List<Component> getClickListeners() {
		return this.clickListeners;
	}

	/**
	 * Returns all the KeyListeners of the SubWindow.
	 */
	protected List<Component> getKeyListeners() {
		return this.keyListeners;
	}

	/**
	 * Sets the propertyChangeSupport of the SubWindow.
	 * @param propertyChangeSupport The propertyChangeSupport which will be set for the SubWindow.
	 * @throws IllegalArgumentException When the propertyChangeSupport Parameter equals nulls.
	 *         | propertyChangeSupport == null
	 * @post The support variable equals the propertyChangeSupport.
	 *         | new.getSupport() == this.getSupport()
	 */
	protected void setSupport(PropertyChangeSupport propertyChangeSupport) {
		if (propertyChangeSupport == null) {
			throw new IllegalArgumentException("Cannot set null propertyChangeSupport");
		}
		this.support = propertyChangeSupport;
	}

	/**
	 * Returns the propertyChangedSupport of the SubWindow.
	 * @return The propertyChangeSupport of the window.
	 *         | this.support
	 */
	protected PropertyChangeSupport getSupport() {
		return this.support;
	}

	/**
	 * Checks whether the x and y coordinates are within the subWindow.
	 *
	 * @param x
	 *        The x-coordinate of the click.
	 * @param y
	 *        The y-coordinate of the click.
	 * @param dragBorderSize 
	 * @return True if the x and y coordinates are within the SubWindow and not in the draggable areas; otherwise false.
	 *        | return x > getX() + dragBorderSize && x < getOffsetX() - dragBorderSize && y > getY() + dragBorderSize
	 *        |		&& y < getOffsetY() - dragBorderSize;
	 */
	public boolean isWithinComponent(int x, int y, int dragBorderSize) {
		return x > getX() + dragBorderSize && x < getOffsetX() - dragBorderSize && y > getY() + dragBorderSize
				&& y < getOffsetY() - dragBorderSize;
	}

	/**
	 * Returns the X-coordinate of the SubWindow.
	 */
	protected int getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate of the SubWindow, and gives the change to the components of the SubWindow.
	 * @param x The new x-coordinate value.
	 * @throws IllegalArgumentException when the x value is smaller than 0.
	 *         | x < 0
	 * @effect The subWindow changed his x coordinate and the components within are changed to the new x-coordinate.
	 *         | this.x =x;
	 *         | for (Component c : getComponents()) {
	 *         |   c.changeX(x - this.x);
	 */
	private void setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("Cannot set negative X on subWindow");
		}
		int change = x - this.x;
		this.x = x;
		if (getContainer() != null) {
			getContainer().changeX(change);
		}
		getTitleBar().changeX(change);
	}

	/**
	 * Returns the Y-coordinate of the subWindow.
	 */
	protected int getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate of the SubWindow, and gives the change to the components of the SubWindow.
	 * @param y The new y-coordinate value.
	 * @throws IllegalArgumentException when the y value is smaller than 0.
	 *         | y < 0
	 * @effect The subWindow changed his y-coordinate and the components within are changed to the new y-coordinate.
	 *         | this.y =y;
	 *         | for (Component c : getComponents()) {
	 *         |   c.changeY(y - this.y);
	 */
	private void setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("Cannot set negative Y on subWindow");
		}
		int change = y - this.y;
		this.y = y;

		if (getContainer() != null) {
			getContainer().changeY(change);
		}
		getTitleBar().changeY(change);
	}

	/**
	 * Returns the width of the SubWindow.
	 */
	protected int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the SubWindow and changes the width of the title bar if any.
	 * @param width The new width of the SubWindow.
	 * @effect The width of the SubWindow is changed and the width of the titleBar is changed.
	 */
	private void setWidth(int width) {
		if (width < MIN_WIDTH) {
			width = MIN_WIDTH;
		}
		int change = width - this.width;
		this.width = width;
		if (getTitleBar() != null) {
			this.getTitleBar().changeWidth(change);
		}
	}

	/**
	 * Returns the height of the SubWindow.
	 */
	protected int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the SubWindow.
	 * @param height The new height of the SubWindow.
	 */
	private void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the offset of the x-coordinate of the SubWindow.
	 */
	private int getOffsetX() {
		return this.getWidth() + getX();
	}

	/**
	 * Returns the offset of the y-coordinate of the SubWindow.
	 */
	private int getOffsetY() {
		return this.getHeight() + getY();
	}

	/**
	 * Returns the container of the SubWindow.
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * Sets the container of the SubWindow.
	 * @param container The new container of the SubWindow.
	 */
	protected void setContainer(Container container) {
		this.container = container;
	}

	/**
	 * Sets the titleBar of the SubWindow.
	 * @param titleBar The titleBar of the SubWindow.
	 * @throws IllegalArgumentException When the titleBar parameter equals null.
	 *         | titleBar == null
	 * @post The title bar of the SubWindow equals the titleBar parameter.
	 *         | new.getTitleBar() == titleBar.
	 */
	private void setTitleBar(TitleBar titleBar) {
		if (titleBar == null) {
			throw new IllegalArgumentException("Cannot set null titleBar in subWindow");
		}
		this.titleBar = titleBar;
	}

	/**
	 * Returns the titleBar of the SubWindow.
	 */
	protected TitleBar getTitleBar() {
		return this.titleBar;
	}

	/**
	 * Removes all the click and key listeners which are not equal the given component or the titleBar.
	 * @param component The component of which the click and key listeners has to be kept.
	 * @effect The all other click and key listeners are removed.
	 */
	protected void removeAllContentListenersButOne(Component component) {
		List<Component> currentClickListeners = new ArrayList<>(getClickListeners());
		for (Component c : currentClickListeners) {
			if (!c.equals(component) && !(c instanceof TitleBar)) {
				this.getClickListeners().remove(c);
			}
		}
		List<Component> currentKeyListeners = new ArrayList<>(getKeyListeners());
		for (Component c : currentKeyListeners) {
			if (!c.equals(component) && !(c instanceof TitleBar)) {
				this.getKeyListeners().remove(c);
			}
		}
	}

	/**
	 * Adds the listener to the list of stored listeners.
	 * @param listener The Component which needs to be added to the list of stored listeners.
	 * @throws IllegalArgumentException When the listener equals null.
	 *                                  | listener == null
	 * @effect The listener is added to the list of stored listeners.
	 *         | this.storedListeners.add(listener)
	 */
	protected void addStoredListener(Component listener) {
		if (listener == null) {
			throw new IllegalArgumentException("Cannot add a null component as a stored listener.");
		}
		this.storedListeners.add(listener);
	}

	/**
	 * Adds all the listeners in the storedListeners to the click and key listeners.
	 */
	protected void resetAllListeners() {
		this.removeContentClickAndKeyListeners();

		for (Component c : getStoredListeners()) {
			this.addClickListener(c);
			this.addKeyListener(c);
		}
	}

	/**
	 * Clears the list of stored listeners by setting it to an empty ArrayList.
	 * @effect The stored listeners list is set to an empty ArrayList.
	 *         | this.setStoredListeners(new ArrayList<Component>());
	 */
	protected void clearStoredListeners() {
		this.setStoredListeners(new ArrayList<Component>());
	}

	/**
	 * Returns the value of the paused variable.
	 * @return the value of the paused variable.
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Sets the paused variable to the given parameter.
	 * @param paused The paused value which will be set.
	 * @post The paused variable equals the parameter.
	 *       | new.isPaused() == paused
	 */
	protected void setPaused(boolean paused) {
		this.paused = paused;
	}

	/**
	 * Returns the storedListeners of the SubWindow.
	 * @return the storedListeners variable of the SubWindow.
	 */
	protected List<Component> getStoredListeners() {
		return storedListeners;
	}

	/**
	 * Sets the list of stored listeners.
	 * @param listeners The listeners which will be set as storedListeners.
	 * @throws IllegalArgumentException when the listeners variable equals null.
	 *         |listeners == null
	 * @post The storedListeners variable equals the listeners parameter
	 *        |new.getStoredListeners() == listeners.
	 */
	protected void setStoredListeners(List<Component> listeners) {
		if (listeners == null) {
			throw new IllegalArgumentException("Cannot set null stored listeners");
		}
		this.storedListeners = listeners;
	}

	/**
	 * Returns the id of the subWindow.
	 * @return The id of the subWindow.
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets the id of the subWindow.
	 * @param id The id to which it will be set.
	 * @post The id equals the given parameter id.
	 *       |new.getId() == id
	 */
	private void setId(UUID id) {
		// null is allowed for tableswindow
		this.id = id;
	}

	/**
	 * Handles the crtl enter behaviour.
	 */
	public abstract void ctrlEntrPressed();

	/**
	 * Updates the content of the SubWindow with the given tableData.
	 * @param tableData The data containing the data for updating the subWindow.
	 */
	public abstract void updateContent(Object... tableData);

	/**
	 * Pauses the SubWindow at the given index with the given id.
	 * @param columnIndex The column index of component of the cause of the pause.
	 * @param columnId    The id of component of the cause of the pause.
	 */
	public abstract void pauseSubWindow(int columnIndex, UUID columnId);

	/**
	 * Resumes the window everything can be handled as normal.
	 */
	public abstract void resumeSubWindow();

}
