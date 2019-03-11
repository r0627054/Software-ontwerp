package ui.model.components;

import java.util.UUID;

public abstract class EditableComponent extends Component {

	private final UUID id;
	private boolean error;

	public EditableComponent(int width, int height, UUID id) {
		super(width, height);
		this.id = id;
	}

	public EditableComponent(int width, int height, boolean hidden, UUID id) {
		super(width, height, hidden);
		this.id = id;
	}

	public EditableComponent(int x, int y, int width, int height, boolean hidden, UUID id) {
		super(x, y, width, height, hidden);
		this.id = id;
	}

	protected UUID getId() {
		return id;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}
