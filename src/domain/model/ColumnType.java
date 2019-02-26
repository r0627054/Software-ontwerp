package domain.model;

import java.awt.Window.Type;

/**
 * An enumeration of ColumnTypes.
 * 
 * @version 1.0
 * @author Dries Janse
 */
public enum ColumnType {

	EMAIL("", String.class),
	STRING("", String.class),
	BOOLEAN(new Boolean(true),Boolean.class),
	INTEGER(new Integer(0),Integer.class);
	
	private Object defaultValue;
	private Class cl;
	
	private ColumnType(Object defaultValue, Class cl) {
		this.defaultValue = defaultValue;
		this.cl = cl;
	}
	 
	public boolean canHaveAsValue(Object value) {
		if( (value != null) && (value.getClass().equals( this.getTypeClass())) ){
			return true;
		}
		return false;
	}
	
	public Class getTypeClass() {
		return this.cl;
	}
	
	public Object getDefaultValue() {
		return this.defaultValue;	
	}
	
}
