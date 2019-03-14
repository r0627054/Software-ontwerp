package usecases;

public interface DesignTableConstants {
	public static final String NEW_TABLE_NAME = "Table";

	public static final int BELOW_TABLE_X = 500;
	public static final int BELOW_TABLE_Y = 500;
	
	public static final int COLUMN_NAME_X = 60;
	public static final int COLUMN_TYPE_X = 160;
	public static final int COLUMN_BLANKS_X = 260;
	public static final int COLUMN_DEFAULT_X = 360;
	public static final int FIRST_ROW_Y = 110;
	
	public static final String NEW_COLUMN_NAME = "Column";
	public static final Class<String> NEW_COLUMN_TYPE = String.class;
	public static final boolean NEW_COLUMN_ALLOW_BLANKS = true;
	public static final String NEW_COLUMN_DEFAULT = "";

	public static final String COLUMN_NAME = "Column Name";
	public static final String COLUMN_TYPE = "Type";
	public static final String COLUMN_ALLOW_BLANKS = "Allow Blanks";
	public static final String COLUMN_DEFAULT = "Default Value";
	
	public static final String STRING = "String";
	public static final String EMAIL = "Email";
	public static final String BOOLEAN = "Boolean";
	public static final String INTEGER = "Integer";
}
