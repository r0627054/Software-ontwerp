package domain.model.sql.tablespecs;

import domain.model.sql.SqlException;

/**
 * A TableSpec is building block of the from statement.
 *  It is an abstract class. Every TableSpec contains a realTableName and a display table name. (Display table name = table name locally used within the query)
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public abstract class TableSpec {

	/**
	 * Variable storing the real name of the table.
	 */
	private String realTableName;
	
	/**
	 * Variable storing the display name of the table (= table name locally used within the query)
	 */
	private String displayTableName;

	/**
	 * Returns the real name of the table.
	 * @return the realTableName The actual real name of the table.
	 */
	public String getRealTableName() {
		return realTableName;
	}

	/**
	 * Sets the real name of the table.
	 * @param realTableName the realTableName which will be set.
	 * @throws SqlException hen the realTableName equals null or when the name is empty.
	 *         | realTableName == null || realTableName.trim().isEmpty()
	 * @post the realTableName is set to the give parameter name.
	 *         | new.getRealTableName() == realTableName 
	 */
	protected void setRealTableName(String realTableName) {
		if (realTableName == null || realTableName.trim().isEmpty()) {
			throw new SqlException("Cannot set an empty realTableName to a TableSpec.");
		}
		this.realTableName = realTableName;
	}

	/**
	 * Returns the display name of the table.
	 * @return the display table name of the table. (Display table name = table name locally used within the query)
	 */
	public String getDisplayTableName() {
		return displayTableName;
	}

	/**
	 * Sets the display name of the table.
	 * @param displayTableName the display name of the table  (Display table name = table name locally used within the query)
	 * @throws SqlException hen the displayTableName equals null or when the name is empty.
	 *         | displayTableName == null || displayTableName.trim().isEmpty()
	 * @post the displayTableName is set to the give parameter name.
	 *         | new.getDisplayTableName() == displayTableName 
	 */
	protected void setDisplayTableName(String displayTableName) {
		if (displayTableName == null || displayTableName.trim().isEmpty()) {
			throw new SqlException("Cannot set an empty displayTableName to a TableSpec.");
		}
		this.displayTableName = displayTableName;
	}

}
