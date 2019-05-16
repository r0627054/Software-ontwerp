package domain.model.sql;

public class CellId {

	private String tableId;
	private String columnName;

	public CellId(String tableId, String columnName) {
		this.setTableId(tableId);
		this.setColumnName(columnName);
	}

	public CellId(String cellIdString) {
		parseValuesFromString(cellIdString);
	}

	private void parseValuesFromString(String cellIdString) {
		if (cellIdString == null || cellIdString.trim().isEmpty() || !cellIdString.contains(".")) {
			throw new SqlException(
					"Invalid CellIdString when parsing string to tableId and columnName in CellId class");
		}
		this.setTableId(cellIdString.split("\\.")[0]);
		this.setColumnName(cellIdString.split("\\.")[1]);
	}

	public String getTableId() {
		return tableId;
	}

	private void setTableId(String tableId) {
		if (tableId == null || tableId.trim().isEmpty()) {
			throw new SqlException("TableId cannot be null or empty.");
		}
		this.tableId = tableId;
	}

	public String getColumnName() {
		return columnName;
	}

	private void setColumnName(String columnName) {
		if (columnName == null || columnName.trim().isEmpty()) {
			throw new SqlException("columnName cannot be null or empty.");
		}
		this.columnName = columnName;
	}

	@Override
	public boolean equals(Object obj) {
		if ( (obj != null)  && (obj instanceof CellId)) {
			CellId objCasted = (CellId) obj;
			return objCasted.getColumnName().equals(this.getColumnName())
					&& objCasted.getTableId().equals(this.getTableId());
		}
		return false;
	}

	@Override
	public String toString() {
		return getTableId() + "." + getColumnName();
	}
}
