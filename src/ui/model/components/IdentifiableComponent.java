package ui.model.components;

import java.util.UUID;

public abstract class IdentifiableComponent extends Component {

	private final UUID id;
	
	public IdentifiableComponent(int width, int height, UUID id) {
		super(width, height);
		this.id = id;
	}
	
	public IdentifiableComponent(int width, int height, boolean hidden, UUID id) {
		super(width, height, hidden);
		this.id = id;
	}

	public	IdentifiableComponent(int x, int y, int width, int height, boolean hidden, UUID id) {
		super(x, y, width, height, hidden);
		this.id = id;
	}
	
	protected UUID getId() {
		return id;
	}
	
}
