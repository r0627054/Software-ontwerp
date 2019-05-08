package domain.model.sql.tablespecs;

import domain.model.sql.SqlException;

public abstract class TableSpec {

	private String realTableName;
	private String displayTableName;

	/**
	 * @return the realTableName
	 */
	public String getRealTableName() {
		return realTableName;
	}

	/**
	 * @param realTableName the realTableName to set
	 */
	protected void setRealTableName(String realTableName) {
		if (realTableName == null || realTableName.trim().isEmpty()) {
			throw new SqlException("Cannot set an empty realTableName to a TableSpec.");
		}
		this.realTableName = realTableName;
	}

	/**
	 * @return the displayTableName
	 */
	public String getDisplayTableName() {
		return displayTableName;
	}

	/**
	 * @param displayTableName the displayTableName to set
	 */
	protected void setDisplayTableName(String displayTableName) {
		if (displayTableName == null || displayTableName.trim().isEmpty()) {
			throw new SqlException("Cannot set an empty displayTableName to a TableSpec.");
		}
		this.displayTableName = displayTableName;
	}

}
