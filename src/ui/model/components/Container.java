package ui.model.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;


public class Container extends Component {

	// Steven:
	// In mijn ogen is dit mss geen Component omdat:
	// 1) Moet een speciale link naar 'boven' hebben om de repaint methode te
	// callen.
	// 2) Heeft geen actionListeners op key/mouse (?)
	// 3) x/y & width/height is bij ons altijd hetzelfde als parent
	// 4) Een component moet aan zijn container zeggen a.d.h.v. propertyChanged om
	// te repainten
	// -> Een container kan dit moeilijk doen
	// -> Je kan wel evt in Container die repaintContainer() overriden & linken aan
	// parent.repaint();
	// 5) In Swing/AWT zijn Containers ook geen JComponent's

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
	
	public List<Component> getComponentsList(){
		return this.components;
	}
	public int getMaxHeightFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getHeight()).max().orElse(0);
	}
	
	public int getMaxWidthFromChildren() {
		return getComponentsList().stream().mapToInt(c -> c.getWidth()).max().orElse(0);
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(226, 226, 226));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.clipRect(getX(), getY(), getWidth(), getHeight());

		for (Component c : components) {
			c.paint((Graphics2D) g.create());
		}
	}

	@Override
	public void mouseClicked(int id, int x, int y, int clickCount) {
		for(Component c: getComponentsList()) {
			if (c.isWithinComponent(x, y))
				c.mouseClicked(id, x, y, clickCount);
		}
	}

	@Override
	public void keyPressed(int id, int keyCode, char keyChar) {
		// TODO Auto-generated method stub

	}

	public void addComponent(Component c) {
		if (c == null) {
			throw new IllegalArgumentException("Null component cannot be added to a container");
		}

		this.components.add(c);
	}

}
