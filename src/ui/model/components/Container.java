package ui.model.components;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Container extends Component {

	private List<Component> components = new ArrayList<>();

	public Container(int x, int y, int width, int height, List<Component> listItems) {
		super(x, y, width, height, false);
		this.setListItems(listItems);
	}

	public Container(int x, int y, int width, int height) {
		this(x, y, width, height, new ArrayList<Component>());
	}

	private void setListItems(List<Component> listItems) {
		if (listItems == null) {
			throw new IllegalArgumentException("ListItems can not be null");
		}
		this.components = listItems;

	}

	public List<Component> getComponentsList() {
		return this.components;
	}

	public int getMaxHeightFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getHeight()).max().orElse(0);
	}

	public int getMaxWidthFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getWidth()).max().orElse(0);
	}

	public int getSumWidthFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getWidth()).sum();
	}

	public int getSumHeightFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getHeight()).sum();
	}

	@Override
	public void paint(Graphics2D g) {
		g.clipRect(getX(), getY(), getWidth(), getHeight());

		for (Component c : components) {
			c.paint((Graphics2D) g.create());
		}
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		for (Component c : getComponentsList()) {
			if (c.isWithinComponent(x, y)) {
				c.mouseClicked(id, x, y, clickCount);
			} else {
				c.outsideClick(id, x, y, clickCount);
			}
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		for (Component c : getComponentsList()) {
			c.keyPressed(id, keyCode, keyChar);
		}
	}

	public void addComponent(Component c) {
		if (c == null) {
			throw new IllegalArgumentException("Null component cannot be added to a container");
		}
		this.components.add(c);
	}

	@Override
	public void throwError(UUID id) {
		super.throwError(id);
		for (Component c : getComponentsList()) {
			c.throwError(id);
		}
	}

	@Override
	public int getOffsetX() {
		return this.getX() + this.getSumWidthFromChildren();
	}

	@Override
	public int getOffsetY() {
		return this.getY() + this.getSumHeightFromChildren();
	}

	@Override
	public void outsideClick(int id, int x, int y, int clickCount) {
		for (Component c : getComponentsList()) {
			c.outsideClick(id, x, y, clickCount);
		}
	}

}
