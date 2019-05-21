package domain.model.sql.tablespecs;

/**
 * An SingleTableSpec is as a subclass of a tableSpec. It consists of a specification of a single table.
 * 
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class SingleTableSpec extends TableSpec {

	/**
	 * Initialise a new instance of a singleTableSpec with the given parameters.
	 * @param realTableName    The real name of the table.
	 * @param displayTableName The display name of the table.(Display table name = table name locally used within the query)
	 * @effect the variables are all set using the setters.
	 *                      | this.setDisplayTableName(displayTableName);
	 *                      | this.setRealTableName(realTableName);
	 */
	public SingleTableSpec(String realTableName, String displayTableName) {
		this.setRealTableName(realTableName);
		this.setDisplayTableName(displayTableName);
	}

	/**
	 * {@inheritDoc}
	 * Returns a string in the following format : "FROM " + getRealTableName() + " AS " + getDisplayTableName();
	 */
	@Override
	public String toString() {
		return "FROM " + getRealTableName() + " AS " + getDisplayTableName();
	}
}
