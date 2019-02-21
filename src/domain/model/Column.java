package domain.model;

/**
 * 
 * 
 * @version 1.0
 * @author Dries Janse
 */
public class Column {

	String name;
	ColumnType type;
	boolean allowsBlanks;
	
	public Column(String name, ColumnType type) {
		this(name,type,true);
	}
	
	public Column(String name, ColumnType type, boolean allowsBlanks) {
		this.setName(name);
		this.setType(type);
		this.setAllowsBlanks(allowsBlanks);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public ColumnType getType() {
		return type;
	}

	private void setType(ColumnType type) {
		this.type = type;
	}

	public boolean isAllowsBlanks() {
		return allowsBlanks;
	}

	private void setAllowsBlanks(boolean allowsBlanks) {
		this.allowsBlanks = allowsBlanks;
	}
	
	
	
}
