package ui.model.components;

import java.awt.Graphics2D;
import java.util.List;

public class ComponentList extends Component {

	private List<Component> componentList;
	private List<Object> listItems;

	public ComponentList(int x, int y, int width, int height, List<Object> listItems) {
		this(x, y, width, height, false, listItems);
	}

	public ComponentList(int x, int y, int width, int height, boolean hidden, List<Object> listItems) {
		super(x, y, width, height, hidden);
		this.setListItems(listItems);
		
		//TODO: Maak een component voor elke listItem
		//Misschien een Component.class meegeven id constructor
		
	}

	private void setListItems(List<Object> listItems) {
		if (listItems == null) {
			throw new IllegalArgumentException("ListItems can not be null");
		}
		this.listItems = listItems;

	}

	@Override
	public void paint(Graphics2D g) {
		// TODO Auto-generated method stub

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
