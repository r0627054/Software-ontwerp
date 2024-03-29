package usecases;

public interface RowTableConstants {
	public static final String NEW_TABLE_NAME = "NewTable";

	public static final int FIRST_ROW_X = 50;
	public static final int SECOND_ROW_X = 160;
	public static final int THIRD_ROW_X = 260;

	public static final int FIRST_ROW_Y = 120;
	public static final int SECOND_ROW_Y = 140;

	public static final int BELOW_TABLE_X = 400;
	public static final int BELOW_TABLE_Y = 400;
	public static final int LEFT_TABLE_X = 20;
	
	public static final String ADD_TABLE_QUERY_REF_SECOND_TABLE = 			"SELECT e.Email AS c FROM DummyEmail AS e WHERE TRUE";
	public static final String EDIT_STRING_TEXT = "TestString123";

}
