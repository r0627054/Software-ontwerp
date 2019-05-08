package domain.model.sql;

public class CellId {
	
	private String tableId;
	private String columnName;


	public CellId(String tableId, String columnName) {
		this.setTableId(tableId);
		this.setColumnName(columnName);
	}

	public String getTableId() {
		return tableId;
	}

	private void setTableId(String tableId) {
		if(tableId == null || tableId.trim().isEmpty()) {
			throw new SqlException("TableId cannot be null or empty.");
		}
		this.tableId = tableId;
	}

	public String getColumnName() {
		return columnName;
	}

	private void setColumnName(String columnName) {
		if(columnName == null || columnName.trim().isEmpty()) {
			throw new SqlException("columnName cannot be null or empty.");
		}
		this.columnName = columnName;
	}

}
