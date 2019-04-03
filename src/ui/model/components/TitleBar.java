package ui.model.components;

import java.awt.Graphics2D;

import controller.handlers.ChangeEventType;
import controller.observer.PropertyChangeEvent;
import controller.observer.PropertyChangeListener;

/**
 * The titleBar is a specific Container.
 * It is contains all the information the upper bar (= titlebar) of a window.
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class TitleBar extends Container implements PropertyChangeListener {

	private TextField title;

	/**
	 * Initialise the title bar with the given x-coordinate,
	 *  y-coordinate, width, height and the title which will be displayed.
	 * @param x The x-coordinate of the titleBar.
	 * @param y The y-coordinate of the titleBar.
	 * @param width The width of the titleBar.
	 * @param height The height of the titleBar.
	 * @param buttonWidth 
	 * @param titleText The title which the titleBar will display.
	 * @effect The variables are set and a button close button and textfield for the title are created.
	 */
	public TitleBar(int x, int y, int width, int height, int borderMargin, int contentOffset, int buttonWidth,
			String titleText) {
		super(x, y, width, height);

		setTitle(new TextField(contentOffset, y, width, height, titleText));
		addComponent(getTitle());
		Button closeButton = new Button(width - buttonWidth - borderMargin, y + borderMargin, buttonWidth,
				height - borderMargin, "Close", ChangeEventType.CLOSE_SUBWINDOW);
		addComponent(closeButton);
		closeButton.addPropertyChangeListener(this);
	}

	/**
	 * Draws a line under the title bar.
	 *  method to handle the other drawings (components in the container).
	 * @param g 
	 * 		 This object offers the methods that allow you to paint on the canvas.
	 * @effect A line below the title bar is drawn and all other components within the bar are drawn.
	 *        | g.drawLine(getX(), getY() + getHeight(), getOffsetX(), getY() + getHeight())
	 *        |	super.paint(g)
	 */
	@Override
	public void paint(Graphics2D g) {
		g.drawLine(getX(), getY() + getHeight(), getX() + getWidth(), getY() + getHeight());
		super.paint(g);
	}

	/**
	 * Changes the width of the of the titleBar.
	 * The x-coordinate of the close button in the titleBar is changed.
	 * @param change The amount the width has to be increased with.
	 * @effect The width of the titleBar is set
	 *         | this.setWidth(getWidth() + change)
	 * @effect The x-coordinate of the button in the bar is changed.
	 *         | for (Component c : getComponentsList())
	 *		   |   if (c instanceof Button) 
	 *		   |     ((Button) c).changeX(change);
	 */
	public void changeWidth(int change) {
		this.setWidth(getWidth() + change);
		for (Component c : getComponentsList()) {
			if (c instanceof Button) {
				((Button) c).changeX(change);
			}
		}
	}

	/**
	 * This method fires a property change with the given propertyChangeEvent.
	 * @param evt
	 *        | The propertyChangeEvent used to fire the change.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.getSupport().firePropertyChange(evt);
	}

	public void updateTitle(String newTitle) {
		this.getTitle().setText(newTitle);
	}

	private TextField getTitle() {
		return title;
	}

	private void setTitle(TextField title) {
		if (title == null || title.getText() == null || title.getText().isEmpty()) {
			throw new IllegalArgumentException("Cannot set empty title in TitleBar");
		}
		this.title = title;
	}

}
