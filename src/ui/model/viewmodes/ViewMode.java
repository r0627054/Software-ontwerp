package ui.model.viewmodes;

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
import ui.model.components.Button;
import ui.model.components.Component;
import ui.model.components.TextField;

/**
 * A viewMode is a mode the view can have, it's an abstract class.
 *  A viewMode is the actual view inside one frame.
 *  The viewMode keeps track of all the components and all the clickListeners/KeyListeners of the components.
 *
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public abstract class ViewMode implements PropertyChangeListener {

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
	public static final int TITLE_BAR_SIZE = 30;

	/**
	 * The constant storing the minimal width of the viewmode.
	 */
	public static final int MIN_WIDTH = 100;

	/**
	 * The constant storing the minimal height of the viewmode.
	 */
	public static final int MIN_HEIGHT = 100;

	/**
	 * The constant storing the content offset.
	 */
	public static final int CONTENT_OFFSET_X = 50;

	/**
	 * The constant storing the content offset.
	 */
	public static final int CONTENT_OFFSET_Y = 50;

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
	 * The variable storing whether the window, is currently resizing the X and Y coordinate.
	 * (resizing the corners)
	 */
	private boolean resizeRightBottomXY = false;

	/**
	 * The variable storing whether the window is currently resizing the X coordinate.
	 */
	private boolean resizeRightX = false;

	/**
	 * The variable storing whether the window is currently resizing the Y coordinate.
	 */
	private boolean resizeBottomY = false;

	/**
	 * The variable storing whether the window is currently dragging.
	 */
	private boolean dragWindow = false;

	private int windowDragX;
	private int windowDragY;

	private TextField title;
	private Button closeButton;

	/**
	 * The variable storing all the components of the specific viewMode.
	 */
	private List<Component> components = new ArrayList<>();

	/**
	 * The variable storing all the clickListeners of the components.
	 */
	private List<Component> clickListeners = new ArrayList<>();

	/**
	 * The variable storing all the keyListeners of the components.
	 */
	private List<Component> keyListeners = new ArrayList<>();

	/**
	 * The variable storing the propertyChangeSupport.
	 */
	private PropertyChangeSupport support;

	/**
	 * The variable storing the type of the viewMode.
	 */
	private ViewModeType type;

	/**
	 * Initialises a new ViewMode and sets the support variable.
	 */
	public ViewMode() {
		setSupport(new PropertyChangeSupport());
		setX(DEFAULT_X);
		setY(DEFAULT_Y);
		setWidth(DEFAULT_WIDTH);
		setHeight(DEFAULT_HEIGHT);

		setTitle(new TextField(10 + DEFAULT_X, DEFAULT_Y, 200, TITLE_BAR_SIZE, getTitle()));
		this.addComponent(getTitleField());
	}

	/**
	 * Adds a propertyChangeListener to support variable.
	 * @param pcl
	 *        | The propertyChangeListener which needs to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}

	/**
	 * Removes a propertyChangeListener from the support variable.
	 * @param pcl
	 *        | the propertyChangeListener which will be removed
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}

	/**
	 * Returns all the components of the viewMode. (copy)
	 */
	public List<Component> getComponents() {
		return new ArrayList<>(components);
	}

	/**
	 * Sets the list of components to the viewMode.
	 *
	 * @param components
	 *        | A list of components.
	 * @throws IllegalArgumentException when the components equal null
	 *        | components == null
	 * @post the list of components are equals to the components parameter
	 *        | new.getComponents() == components
	 */
	protected void setComponents(List<Component> components) {
		if (components == null) {
			throw new IllegalArgumentException("The components of a view mode cannot be null");
		}
		this.components = components;
	}

	/**
	 * Adds a component to the list of components.
	 *
	 * @param component
	 *        | The component which will be added to the viewMode.
	 * @return whether or not the component is added to the list of components.
	 * @throws IllegalArgumentException when the component is equal to null.
	 *       | component == null
	 * @effect the component is added to the list of components.
	 *       | components.add(component)
	 */
	public boolean addComponent(Component component) {
		if (component == null) {
			throw new IllegalArgumentException("Cannot add null as component to the view mode.");
		}
		return components.add(component);
	}

	/**
	 * Adds a list of components to the list of components.
	 *
	 * @param c
	 *       | The collection of components which needs to be added to the list of components.
	 * @return whether or not the collection is added to the list of components.
	 * @effect The collection of components is added to the list of components
	 *       |components.addAll(c)
	 */
	public boolean addAllComponents(Collection<? extends Component> c) {
		return components.addAll(c);
	}

	/**
	 * Removes a component at a given index.
	 *
	 * @param index
	 *        | The index of which the component needs to be deleted.
	 * @effect The component is removed at the given index.
	 *        | components.remove(index)
	 */
	public void removeComponent(int index) {
		components.remove(index);
	}

	/**
	 *
	 * Removes a components from the list of components.
	 *
	 * @param component
	 *        | The component which needs to be deleted.
	 * @effect The component is removed from the list.
	 *        | components.remove(component)
	 */
	public void removeComponent(Component component) {
		components.remove(component);
	}

	/**
	 * Draws all the components of a viewMode.
	 * @param g
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 */
	public void paint(Graphics g) {
		g.setClip(getX(), getY(), getWidth(), getHeight());

		this.drawBorder((Graphics2D) g);
		this.drawTitlebar((Graphics2D) g);

		for (Component component : components) {
			component.paint((Graphics2D) g.create());
		}
	}

	/**
	 * Draws the title of the viewMode.
	 *
	 * @param g This object offers the methods that allow you to paint on the canvas.
	 * @post The title is drawn in the window.
	 *       | g.drawLine(getX(), getY() + TITLE_BAR_SIZE, getOffsetX(), getY() + TITLE_BAR_SIZE)
	 */
	private void drawTitlebar(Graphics2D g) {
		g.drawLine(getX(), getY() + TITLE_BAR_SIZE, getOffsetX(), getY() + TITLE_BAR_SIZE);
	}

	/**
	 * Draws the border of the window. It also draws the resizing area's.
	 * This visualises where the user van resize the window.
	 *
	 * @param g This object offers the methods that allow you to paint on the canvas.
	 * @post The graphics object draws the different rectangles.
	 */
	private void drawBorder(Graphics2D g) {
		g.setColor(new Color((float) 0.9, (float) 0.9, (float) 0.9, (float) 0.5));
		g.fillRect(getOffsetX() - DRAG_BORDER_SIZE, getY(), DRAG_BORDER_SIZE, getHeight());
		g.fillRect(this.getX(),this.getY() + DRAG_BORDER_SIZE, DRAG_BORDER_SIZE, getHeight());

		g.fillRect(getX(), getOffsetY() - DRAG_BORDER_SIZE, getWidth(), DRAG_BORDER_SIZE);

		g.setColor(new Color((float) 0.8, (float) 0.8, (float) 0.8, (float) 0.5));
		g.fillRect(getOffsetX() - DRAG_BORDER_SIZE, getOffsetY() - DRAG_BORDER_SIZE, DRAG_BORDER_SIZE, DRAG_BORDER_SIZE);

		g.setColor(Color.BLACK);
		g.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);
	}

	/**
	 * Returns the title of the viewMode.
	 */
	protected abstract String getTitle();

	/**
	 * Adds a component to the list of ClickListeners.
	 * If the component isn't already in the list.
	 * @param c
	 *        |The component which will be added to the clickListeners
	 * @throws IllegalArgumentException when the component equals null
	 *        | c == null
	 * @effect the component is added to the list of ClickListeners
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
	 * @effect the component is added to the list of KeyListeners
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
	 * Tells every component of the viewMode whether there was a click inside or outside the component.
	 *  A copy is made to make sure there is no editing of the list while it is looping.
	 *
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

		if (isWithinComponent(x, y)) {
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
		if (id == MouseEvent.MOUSE_PRESSED && y >= this.getY() && y <= this.getY() + TITLE_BAR_SIZE) {
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
				//the window can be dragged outside (this will give an negative X value) and result in an IllegalArgumentException.
			}
			try {
				this.setY(y - windowDragY);
			} catch (IllegalArgumentException e) {
				this.setY(0);
				//the window can be dragged outside (this will give an negative Y value) and result in an IllegalArgumentException.
			}
			this.propertyChange(new PropertyChangeEvent(null, ChangeEventType.REPAINT, null, null));
		}

	}

	/**
	 *
	 * @param id
	 * @param x
	 * @param y
	 */
	protected void handleResizing(int id, int x, int y) {
		if (id == MouseEvent.MOUSE_PRESSED) {
			if (x < getOffsetX() && x > getOffsetX() - DRAG_BORDER_SIZE && y < getOffsetY() && y > getOffsetY() - DRAG_BORDER_SIZE) {
				this.resizeRightBottomXY = true;
			} else if (  (( x < getOffsetX() && x > (getOffsetX() - DRAG_BORDER_SIZE)  )  || ( x > this.getX() && x < (this.getX() + DRAG_BORDER_SIZE) ) )
					     && y < (getOffsetY() - DRAG_BORDER_SIZE) && y >(this.getY() + DRAG_BORDER_SIZE) ) {
				System.out.println("resize X");
				this.resizeRightX = true;
			} else if (x < getOffsetX() - DRAG_BORDER_SIZE && y < getOffsetY() && y > getOffsetY() - DRAG_BORDER_SIZE) {
				this.resizeBottomY = true;
			}
		}

		if (id == MouseEvent.MOUSE_RELEASED) {
			this.resizeRightBottomXY = false;
			this.resizeRightX = false;
			this.resizeBottomY = false;
		}

		if (id == MouseEvent.MOUSE_DRAGGED) {
			if (this.resizeRightBottomXY) {
				this.setWidth(x - getX());
				this.setHeight(y - getY());
				this.propertyChange(new PropertyChangeEvent(null, ChangeEventType.REPAINT, null, null));
			} else if (this.resizeRightX) {
				this.setWidth(x - getX());
				this.propertyChange(new PropertyChangeEvent(null, ChangeEventType.REPAINT, null, null));
			} else if (this.resizeBottomY) {
				this.setHeight(y - getY());
				this.propertyChange(new PropertyChangeEvent(null, ChangeEventType.REPAINT, null, null));
			}

		}
	}

	/**
	 * Delegates the keyPressed event to the components of the ViewMode.
	 * A copy is made to make sure there is no editing of the list while it is looping.
	 *
	 * @param id
	 *        | The id of the key pressed event.
	 * @param keyCode
	 *        | The key code of the key pressed event.
	 * @param keyChar
	 *        | The key character of a key pressed event.
	 * @effect The components are called an told whether there was a keyPressed.
	 */
	public void keyPressed(int id, int keyCode, char keyChar) {
		List<Component> currentKeyListeners = new ArrayList<>(getKeyListeners());
		for (Component c : currentKeyListeners) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	/**
	 * Returns the viewModeType of the viewMode.
	 */
	public ViewModeType getViewModeType() {
		return type;
	}

	/**
	 * Returns whether or not the component already is in the list of components.
	 *
	 * @param component
	 *        | The component which will be checked if it is in the list of components.
	 * @return true if the component occurs in the list of components; otherwise false.
	 */
	public boolean hasComponent(Component component) {
		return this.getComponents().contains(component);
	}

	/**
	 * Sets the type of the ViewMode.
	 *
	 * @param type
	 *        | the ViewModeType of the viewMode.
	 * @throws IllegalArgumentException if the type equals null
	 *        | type == null
	 * @post the type of the viewMode is equal to the type parameter.
	 *        | new.getType() == type
	 */
	protected void setType(ViewModeType type) {
		if (type == null) {
			throw new IllegalArgumentException("ViewModeType cannot be null in a viewmode.");
		}
		this.type = type;
	}

	/**
	 * This method fires a property change with the given propertyChangeEvent.
	 * @param evt
	 *        | The propertyChangeEvent used to fire the change.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.support.firePropertyChange(evt);
	}

	/**
	 * Handles the throw error of a component with the given ID.
	 * @param id
	 *        | The id of which element an error is thrown.
	 */
	public void throwError(UUID id) {
		for (Component c : getComponents()) {
			c.throwError(id);
		}
	}

	/**
	 * Removes all the clickListeners and KeyListeners from the viewMode.
	 */
	protected void removeAllClickAndKeyListeners() {
		this.clickListeners.clear();
		this.keyListeners.clear();
	}

	/**
	 * Returns all the clickListeners of the viewMode.
	 */
	protected List<Component> getClickListeners() {
		return this.clickListeners;
	}

	/**
	 * Returns all the KeyListeners of the viewMode.
	 */
	protected List<Component> getKeyListeners() {
		return this.keyListeners;
	}

	protected void setSupport(PropertyChangeSupport propertyChangeSupport) {
		if (propertyChangeSupport == null) {
			throw new IllegalArgumentException("Cannot set null propertyChangeSupport");
		}
		this.support = propertyChangeSupport;
	}

	protected PropertyChangeSupport getSupport() {
		return this.support;
	}

	/**
	 * Checks whether the x and y coordinates are within the subwindow.
	 *
	 * @param x
	 *        The x-coordinate of the click.
	 * @param y
	 *        The y-coordinate of the click.
	 * @return True if the x and y coordinates are within the subwindow; otherwise false.
	 *        | return x > getX() && x < getOffsetX() && y > getY() && y < getOffsetY();
	 */
	public boolean isWithinComponent(int x, int y) {
		return x > getX() && x < getOffsetX() && y > getY() && y < getOffsetY();
	}

	protected int getX() {
		return x;
	}

	private void setX(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("Cannot set negative X on ViewMode");
		}
		int change = x - this.x;
		this.x = x;

		for (Component c : getComponents()) {
			c.changeX(change);
		}
	}

	protected int getY() {
		return y;
	}

	private void setY(int y) {
		if (y < 0) {
			throw new IllegalArgumentException("Cannot set negative Y on ViewMode");
		}

		int change = y - this.y;
		this.y = y;

		for (Component c : getComponents()) {
			c.changeY(change);
		}
	}

	protected int getWidth() {
		return width;
	}

	private void setWidth(int width) {
		this.width = width >= MIN_WIDTH ? width : MIN_WIDTH;
	}

	protected int getHeight() {
		return height;
	}

	private void setHeight(int height) {
		this.height = height >= MIN_HEIGHT ? height : MIN_HEIGHT;
	}

	private int getOffsetX() {
		return this.getWidth() + getX();
	}

	private int getOffsetY() {
		return this.getHeight() + getY();
	}

	private void setTitle(TextField title) {
		if (title == null) {
			throw new IllegalArgumentException("Cannot set null title in viewmode");
		}
		this.title = title;
	}

	protected TextField getTitleField() {
		return this.title;
	}

}
