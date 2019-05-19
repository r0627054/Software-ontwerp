package usecases;

public interface DesignTableConstants {
	public static final String NEW_TABLE_NAME = "Table";

	public static final int BELOW_TABLE_X = 400;
	public static final int BELOW_TABLE_Y = 400;
	public static final int LEFT_TABLE_X = 40;
	
	public static final int COLUMN_NAME_X = 60;
	public static final int COLUMN_TYPE_X = 160;
	public static final int COLUMN_BLANKS_X = 260;
	public static final int COLUMN_DEFAULT_X = 360;
	public static final int FIRST_ROW_Y = 120;

	
	public static final int DUMMY_TABLE_COLUMN_AMOUNT = 3;
	
	public static final String NEW_COLUMN_NAME = "Column";

	public static final String COLUMN_NAME = "Column Name";
	public static final String COLUMN_TYPE = "Type";
	public static final String COLUMN_ALLOW_BLANKS = "Allow Blanks";
	public static final String COLUMN_DEFAULT = "Default Value";
	
	public static final String STRING = "String";
	public static final String EMAIL = "Email";
	public static final String BOOLEAN = "Boolean";
	public static final String INTEGER = "Integer";
	
	public static final String EXAMPLE_EMAIL = "test@email";
	
	public static final int SECOND_TABLE_X = 160;
	public static final int FIRST_TABLE_Y = 70;
	
	public static final String ADD_TABLE_QUERY_REF_SECOND_TABLE = 			"SELECT e.Email AS c FROM DummyEmail AS e WHERE TRUE";
	public static final String VALID_EDIT_TABLE_QUERY_REF_SECOND_TABLE =  	"SELECT e.Email AS emails FROM DummyEmail AS e WHERE FALSE";
	public static final String INVALID_EDIT_TABLE_QUERY_REF_SECOND_TABLE =  "SELECT e.Name AS names FROM DummyEmail AS e WHERE TRUE";
}
