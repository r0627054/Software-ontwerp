package domain.model.sql;

/**
 * Is a small component used in SQL Queries. (More exactly in an expression, an inner join, ...).
 *  It stores a table name and a column name.
 *  The naming is the same as given in the assignment.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CellId {

	/**
	 * The name of the table.
	 */
	private String tableId;
	
	/**
	 * The name of the column.
	 */
	private String columnName;

	/**
	 * Create a new instance of a cellId with the given table name and column name.
	 * @param tableId The name of the table.
	 * @param columnName the name of the column.
	 * @effect The table name and the column name are set.
	 *        | this.setTableId(tableId);
	 *        | this.setColumnName(columnName);
	 */
	public CellId(String tableId, String columnName) {
		this.setTableId(tableId);
		this.setColumnName(columnName);
	}

	/**
	 * Creates a new instance of a cellId with a cellId in one string format.
	 *  It extracts the tableId and the columnName out of the String and sets the variables.
	 * @param cellIdString The String containing the tableId and the columnName separated with a dot.
	 * @effect the values are extracted and set.
	 *         | parseValuesFromString(cellIdString)
	 */
	public CellId(String cellIdString) {
		parseValuesFromString(cellIdString);
	}

	/**
	 * It extracts the tableId and the columnName out of the String and sets the variables.
	 * @param cellIdString The String containing the tableId and the columnName separated with a dot.
	 * @throws SqlException when the String equals null or when the values couldn't be extracted.
	 *         | cellIdString == null || cellIdString.trim().isEmpty() || !cellIdString.contains(".")
	 * @effect The variables are set.
	 *         | this.setTableId(cellIdString.split("\\.")[0])
	 *         | this.setColumnName(cellIdString.split("\\.")[1])
	 */
	private void parseValuesFromString(String cellIdString) {
		if (cellIdString == null || cellIdString.trim().isEmpty() || !cellIdString.contains(".")) {
			throw new SqlException(
					"Invalid CellIdString when parsing string to tableId and columnName in CellId class");
		}
		this.setTableId(cellIdString.split("\\.")[0]);
		this.setColumnName(cellIdString.split("\\.")[1]);
	}

	/**
	 * Returns the tableId (table name) of the CellId.
	 * @return The tableId (table name) of the CellId.
	 */
	public String getTableId() {
		return tableId;
	}

	/**
	 * The tableId (name) is set.
	 * @param tableId The table name to which the variable will be set.
	 * @throws SqlException when the tableId equals null or when it is empty.
	 *         | tableId == null || tableId.trim().isEmpty()
	 * @post the tableId variable is set to the tableId parameter.
	 *         | new.getTableId() = tableId
	 */
	private void setTableId(String tableId) {
		if (tableId == null || tableId.trim().isEmpty()) {
			throw new SqlException("TableId cannot be null or empty.");
		}
		this.tableId = tableId;
	}

	/**
	 * Returns the name of the column.
	 * @return The name of the column.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Sets the name of the column.
	 * @param columnName The name of the column.
	 * @throws SqlException when the columnName equals null or when the String is empty.
	 *         | columnName == null || columnName.trim().isEmpty()
	 * @post The column name variable equals columnName.
	 *         | new.getColumnName() = columnName
	 */
	private void setColumnName(String columnName) {
		if (columnName == null || columnName.trim().isEmpty()) {
			throw new SqlException("columnName cannot be null or empty.");
		}
		this.columnName = columnName;
	}

	/**
	 * Returns whether or not the CellId are the same. A CellId equals this CellId if they have the same columnName and TableId.
	 * @return True when the other object is a CellId with the same columnName and TableId.
	 */
	@Override
	public boolean equals(Object obj) {
		if ( (obj != null)  && (obj instanceof CellId)) {
			CellId objCasted = (CellId) obj;
			return objCasted.getColumnName().equals(this.getColumnName())
					&& objCasted.getTableId().equals(this.getTableId());
		}
		return false;
	}
	
	/**
	 * Returns a hashCode based on the columnName and the tableId.
	 * @return Creates a hashCode based on the columnName and the tableId.
	 */
	@Override
	public int hashCode() {
		return (this.getColumnName().hashCode()) * 700 + (this.getTableId().hashCode() *99);  
	}

	/**
	 * @return The CellId  as a String. This is represented as: the tableId + dot + columnName.
	 */
	@Override
	public String toString() {
		return getTableId() + "." + getColumnName();
	}
}
