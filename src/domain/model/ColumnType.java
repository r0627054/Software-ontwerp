package domain.model;

/**
 * An enumeration of ColumnTypes.
 * 
 * @version 1.0
 * @author Dries Janse
 */
public enum ColumnType {

	EMAIL(""),
	STRING(""),
	BOOLEAN(new Boolean(true)),
	INTEGER(new Integer(0));
	
	private Object defaultValue;
	
	private ColumnType(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Object getDefaultValue() {
		return this.defaultValue;	
	}
	
}
