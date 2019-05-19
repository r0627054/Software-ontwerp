package usecases;

public interface TableListConstants {
	public static final int FIRST_TABLE_X = 70;
	public static final int SECOND_TABLE_X = 160;
	public static final int FIRST_TABLE_Y = 70;
	public static final int SECOND_TABLE_Y = 110;
	public static final int LEFT_FIRST_TABLE_X = 20;

	public static final int BELOW_TABLELIST_X = 400;
	public static final int BELOW_TABLELIST_Y = 400;

	public static final String NEW_TABLE_NAME = "Table";
	public static final String ADD_TABLE_NAME = "TEST123";
	
	public static final String ADD_TABLE_QUERY_REF_SECOND_TABLE = "SELECT e.Email AS emails FROM DummyEmail AS e WHERE TRUE";
	public static final String VALID_EDIT_TABLE_QUERY_REF_SECOND_TABLE =  "SELECT e.Name AS names FROM DummyEmail AS e WHERE TRUE";

}
