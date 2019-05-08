package domain.model.sql.tablespecs;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class InnerJoinTableSpec extends TableSpec {

	/**
	 * Original table where this Inner Join Table is referenced to
	 */
	private TableSpec tableSpec;

	/**
	 * Left side of the ON condition
	 */
	private CellId cellIdLeft;

	/**
	 * Right side of the ON condition
	 */
	private CellId cellIdRight;

	public InnerJoinTableSpec(String realTableName, String displayTableName, TableSpec tableSpec, CellId left,
			CellId right) {
		this.setDisplayTableName(displayTableName);
		this.setRealTableName(realTableName);
		this.setTableSpec(tableSpec);
		this.setCellIdLeft(left);
		this.setCellIdRight(right);
	}

	/**
	 * @return the tableSpec
	 */
	public TableSpec getTableSpec() {
		return tableSpec;
	}

	/**
	 * @param tableSpec the tableSpec to set
	 */
	private void setTableSpec(TableSpec tableSpec) {
		if (tableSpec == null) {
			throw new SqlException("TableSpec cannot be null in an innerJoinTableSpec");
		}
		this.tableSpec = tableSpec;
	}

	/**
	 * @return the cellIdLeft
	 */
	public CellId getCellIdLeft() {
		return cellIdLeft;
	}

	/**
	 * @param cellIdLeft the cellIdLeft to set
	 */
	private void setCellIdLeft(CellId cellIdLeft) {
		if (cellIdLeft == null) {
			throw new SqlException("CellIdLeft cannot be null in an innerJoinTableSpec");
		}
		this.cellIdLeft = cellIdLeft;
	}

	/**
	 * @return the cellIdRight
	 */
	public CellId getCellIdRight() {
		return cellIdRight;
	}

	/**
	 * @param cellIdRight the cellIdRight to set
	 */
	private void setCellIdRight(CellId cellIdRight) {
		if (cellIdRight == null) {
			throw new SqlException("CellIdRight cannot be null in an innerJoinTableSpec");
		}
		this.cellIdRight = cellIdRight;
	}

	@Override
	public String toString() {
		return "INNER JOIN " + getRealTableName() + " AS " + getDisplayTableName() + " ON " + getCellIdLeft().toString()
				+ " = " + getCellIdRight().toString();
	}
}
