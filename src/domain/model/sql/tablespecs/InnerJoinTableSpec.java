package domain.model.sql.tablespecs;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;

/**
 * An InnerJoin is as a subclass of a tableSpec. It contains a tableSpec which it
 *   will be joined with and two cellIds for the on condition. 
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class InnerJoinTableSpec extends TableSpec {

	/**
	 * Variable storing the tableSpec which it is joined with.
	 */
	private TableSpec tableSpec;

	/**
	 * Left side of the ON condition.
	 */
	private CellId cellIdLeft;

	/**
	 * Right side of the ON condition.
	 */
	private CellId cellIdRight;

	/**
	 * Initialise a new instance of an inner join with the given parameters.
	 * @param realTableName    The real name of the table.
	 * @param displayTableName The display name of the table.(Display table name = table name locally used within the query)
	 * @param tableSpec        The tableSpec to which it is joined.
	 * @param left             The cellId on the left of the ON condition.
	 * @param right            The cellId on the right of the ON condition.
	 * @effect the variables are all set using the setters.
	 *                      | this.setDisplayTableName(displayTableName);
	 *                      | this.setRealTableName(realTableName);
	 *                      | this.setTableSpec(tableSpec);
	 *                      | this.setCellIdLeft(left);
	 *                      | this.setCellIdRight(right);
	 */
	public InnerJoinTableSpec(String realTableName, String displayTableName, TableSpec tableSpec, CellId left,
			CellId right) {
		this.setDisplayTableName(displayTableName);
		this.setRealTableName(realTableName);
		this.setTableSpec(tableSpec);
		this.setCellIdLeft(left);
		this.setCellIdRight(right);
	}

	/**
	 * Returns the tableSpec to which is joined.
	 * @return the tableSpec The table spec to which is joined.
	 */
	public TableSpec getTableSpec() {
		return tableSpec;
	}

	/**
	 * Sets the table spec of the inner join
	 * @param tableSpec the tableSpec of the inner join.
	 * @throws SqlException when the tableSpec equals null 
	 *          | tableSpec == null
	 * @post the tableSpec variable is set.
	 *          | new.getTableSpec() == tableSpec
	 */
	private void setTableSpec(TableSpec tableSpec) {
		if (tableSpec == null) {
			throw new SqlException("TableSpec cannot be null in an innerJoinTableSpec");
		}
		this.tableSpec = tableSpec;
	}

	/**
	 * Returns the cellId of the left of the ON condition.
	 * @return the cellIdLeft the cellId on the left of the ON condition.
	 */
	public CellId getCellIdLeft() {
		return cellIdLeft;
	}

	/**
	 * Sets the cellId of the left of the ON condition.
	 * @param cellIdLeft the cellId on the left of the ON condition.
	 * @throws SqlException when the cellIdLeft equals null 
	 *          | cellIdLeft == null
	 * @post the cellId on the left of the on condition is set.
	 *          | new.getCellIdLeft() == cellIdLeft
	 */
	private void setCellIdLeft(CellId cellIdLeft) {
		if (cellIdLeft == null) {
			throw new SqlException("CellIdLeft cannot be null in an innerJoinTableSpec");
		}
		this.cellIdLeft = cellIdLeft;
	}

	/**
	 * Returns the cellId of the right of the ON condition.
	 * @return the cellIdRight the cellId on the right of the ON condition.
	 */
	public CellId getCellIdRight() {
		return cellIdRight;
	}

	/**
	 * Sets the cellId of the right of the ON condition.
	 * @param cellIdRight the cellId on the right of the ON condition.
	 * @throws SqlException when the cellIdRight equals null 
	 *          | cellIdRight == null
	 * @post the cellId on the right of the on condition is set.
	 *          | new.getCellIdRight() == cellIdRight
	 */
	private void setCellIdRight(CellId cellIdRight) {
		if (cellIdRight == null) {
			throw new SqlException("CellIdRight cannot be null in an innerJoinTableSpec");
		}
		this.cellIdRight = cellIdRight;
	}

	/**
	 * {@inheritDoc}
	 * Returns a string in the following format: "INNER JOIN " + getRealTableName() + " AS " 
	 *                                         + getDisplayTableName() + " ON " + getCellIdLeft().toString()
	 *                               			+ " = " + getCellIdRight().toString();
	 */
	@Override
	public String toString() {
		return "INNER JOIN " + getRealTableName() + " AS " + getDisplayTableName() + " ON " + getCellIdLeft().toString()
				+ " = " + getCellIdRight().toString();
	}
}
