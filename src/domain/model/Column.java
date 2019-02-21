package domain.model;

import java.util.ArrayList;

/**
 *  A class of columns, containing a name, type, allowsblanks and a default value
 * 
 * @version 1.0
 * @author Dries Janse
 */
public class Column {

	private String name;
	private ColumnType type;
	private boolean allowsBlanks;
	private String defaultValue;
	private ArrayList<Cell> cells;
	
	public Column(String name, ColumnType type, String defaultValue) {
		this(name,type,true, defaultValue);
	}
	
	public Column(String name, ColumnType type, boolean allowsBlanks, String defaultValue) {
		this.setName(name);
		this.setType(type);
		this.setAllowsBlanks(allowsBlanks);
		this.setDefaultValue(defaultValue);
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

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	private void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
	
	
	
	
}
