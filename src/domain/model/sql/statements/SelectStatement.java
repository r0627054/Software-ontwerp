package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.ValueType;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.columnSpec.ColumnSpec;
import domain.model.sql.expression.BooleanExpression;
import domain.model.sql.expression.CellIdExpression;
import domain.model.sql.expression.Expression;
import domain.model.sql.expression.LiteralNumberExpression;
import domain.model.sql.expression.LiteralStringExpression;

/**
 * The SelectStatement is a statement of an SQL Query. It is composed of a list of columnSpecs.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class SelectStatement implements Statement {

	/**
	 * Variable storing the list of columnSpecs.
	 */
	private List<ColumnSpec> columnSpecs;

	/**
	 * Creates a select statement without any columnSpecs.
	 */
	public SelectStatement() {
		this.columnSpecs = new ArrayList<>();
	}

	/**
	 * Adds a columnSpec to the list of columnSpecs.
	 * @param columnSpec The columnSpec which will be added to the list of columnSpecs.
	 * @throws SqlException when the columnSpec equals null.
	 *           | columnSpec == null
	 * @effect The columnSpec is added to the list of columnSpecs.
	 *           | getColumnSpec().add(columnSpec);
	 */
	public void addColumnSpec(ColumnSpec columnSpec) {
		if (columnSpec == null) {
			throw new SqlException("The columnSpec cannot be null");
		}
		this.getColumnSpecs().add(columnSpec);
	}

	/**
	 * Returns the columnSpec at the given index.
	 * @param index The index of the requested columnSpec.
	 * @return  The columnSpec on the requested index.
	 *          | this.getColumnSpecs().get(index)
	 */
	public ColumnSpec getColumnSpec(int index) {
		return this.getColumnSpecs().get(index);
	}

	/**
	 * Returns the name of the column of the columnSpec at the given index.
	 * @param index The index of the columnSpec of which the name is requested.
	 * @return The name of the column of the columnSpec at the given index.
	 */
	public String getColumnNameOfColumnSpec(int index) {
		return this.getColumnSpec(index).getColumnName();
	}

	/**
	 * Returns the expression of the columnSpec at the given index.
	 * @param index The index of the columnSpec of which the expression is requested.
	 * @return The expression of the columnSpec at the given index.
	 */
	public Expression getExpressionOfColumnSpec(int index) {
		return this.getColumnSpec(index).getExpression();
	}

	/**
	 * Returns the list of columnSpecs.
	 * @return the list of columnSpecs.
	 */
	public List<ColumnSpec> getColumnSpecs() {
		return columnSpecs;
	}

	/**
	 * {@inheritDoc}
	 * Returns the list of cellIds.
	 * This is request from the expression of all the specs.
	 */
	@Override
	public List<CellId> getAllCellIds() {
		List<CellId> result = new ArrayList<>();
		for (ColumnSpec spec : this.getColumnSpecs()) {
			result.addAll(spec.getExpression().getAllCellIds());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * The result String is one long string composed using all toString methods of the columnSpecs.
	 */
	@Override
	public String toString() {
		String result = "SELECT";
		for (int i = 0; i < this.getColumnSpecs().size(); i++) {
			result += " " + this.getColumnSpecs().get(i).toString();
			if (i != (this.getColumnSpecs().size() - 1)) {
				result += ",";
			}
		}
		return result;
	}

	/**
	 * Compute the domainCell. 
	 * @param row       The current row.
	 * @param cellIdMap The map which links the cellid to the correct index.
	 * @param specIndex The index of the tableSpec.
	 * @return  The newly calculated DomainCell. out of the row op cells.
	 */
	public DomainCell computeCell(Row row, Map<CellId, Integer> cellIdMap, int specIndex) {
		Expression exp = this.getExpressionOfColumnSpec(specIndex).simplify(row, cellIdMap);

		if (exp instanceof CellIdExpression) {
			return this.getDomainCellOfOutOfCellId(exp, row, cellIdMap);
		} else if (exp instanceof LiteralNumberExpression) {
			LiteralNumberExpression litNum = (LiteralNumberExpression) exp;

			
			if (litNum.isOneEditable()) {
				return new DomainCell(litNum.getFirstUUIDOfMap(), litNum.getValue(), ValueType.INTEGER);
			} else {
				return new DomainCell(ValueType.INTEGER, litNum.getValue());
			}

		} else if (exp instanceof BooleanExpression) {
			return new DomainCell(ValueType.BOOLEAN, ((BooleanExpression) exp).getValue());
		} else if (exp instanceof LiteralStringExpression) {
			return new DomainCell(ValueType.STRING, ((LiteralStringExpression) exp).getValue());
		} else {
			throw new SqlException("Not implemented computeCell Expression");
		}
	}

	/**
	 * Extracts the cellId value out of the expression.
	 * @param exp a cellIdExpression.
	 * @param row The row on which the current calculation is in progress.
	 * @param cellIdMap The mapping of cellId to the index.
	 * @return The domainCell a the given index in the row.
	 */
	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ((CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}

	/**
	 * Checks whether the column at the given index is editable
	 * It returns an Object which contains two elements.
	 * The first element is Map<UUID,Integer> which has the columnId and how often it is used.
	 * Second element is Boolean value. Saying whether it is editable or not.
	 * @param i the index of the column (spec)
	 * @return The object which contains the number of occurrences and if the column is editable.
	 */
	public Object[] isEditableForColumnSpecIndex(int i) {
		return getColumnSpec(i).getExpression().isEditable();
	}

	/**
	 * Returns the cellId of the editable column spec on the given index.
	 * @param specIndex  the index of the column (spec)
	 * @return the cellId of the editable column spec on the given index.
	 */
	public CellId getCellIdOfEditable(int specIndex) {
		return (CellId) ((Map<CellId, Integer>) this.isEditableForColumnSpecIndex(specIndex)[0]).keySet().toArray()[0];
	}

	/**
	 * Given a cellId of a cellId of a columnSpec.
	 *  It returns a new CellId with the real columnName.
	 * @param diffId The cellId which will be converted.
	 * @return a new CellId with the real columnName
	 */
	public CellId getCellIdWithRealColumnName(CellId diffId) {
		for (ColumnSpec cs : getColumnSpecs()) {
			for (CellId id : cs.getExpression().getAllCellIds()) {
				if (id.equals(diffId)) {
					return new CellId(id.getTableId(), cs.getColumnName());
				}
			}
		}
		return null;
	}

	/**
	 * Returns a list of column names that where used in the table with the given display name (specific name inside a query)
	 * @param displayName The display name of a table (specific name inside a query)
	 * @return the list of column names that where used in the table
	 * @throws SqlException when the display name equals null. 
	 */
	public List<String> getUsedColumnNamesOfDisplayTableName(String displayName) {
		if (displayName == null) {
			throw new SqlException("Cannot get used column names for null display table name");
		}

		List<String> result = new ArrayList<>();
		for (ColumnSpec cs : getColumnSpecs()) {
			for (CellId id : cs.getExpression().getAllCellIds()) {
				if (id.getTableId().equals(displayName)) {
					result.add(id.getColumnName());
				}
			}
		}
		return result;
	}

	/**
	 * Returns all the column names.
	 * @return a List of all the column names.
	 */
	public List<String> getAllSelectColumnNames() {
		List<String> result = new ArrayList<>();

		for (ColumnSpec cs : getColumnSpecs()) {
			result.add(cs.getColumnName());
		}

		return result;
	}
	
	/**
	 * Returns the columnSpec Object of the specific column name.
	 * @param displayName The name of column displayed in the select statement.
	 * @return the columnSpec Object of the specific column name.
	 */
	public ColumnSpec getColumnSpecOfDisplayName(String displayName) {
		for (ColumnSpec columnSpec : this.getColumnSpecs()) {
			if(columnSpec.getColumnName().equals(displayName)) {
				return columnSpec;
			}
		}
		throw new SqlException("ColumnSpec requested which does not exist");
	}

	/**
	 * Returns a list of all CellIds used in the expression of the columnSpec of a table with the given name.
	 * @param columnNameOfEditedCell The column name.
	 * @return a list of all CellIds used in the expression of the columnSpec of a table with the given name.
	 */
	public List<CellId> getCellIdOfColumnName(String columnNameOfEditedCell) {
		for (ColumnSpec cs : getColumnSpecs()) {
			if (cs.getColumnName().equals(columnNameOfEditedCell)) {
				return cs.getExpression().getAllCellIds();
			}
		}
		return null;
	}
	
	/**
	 * Checks whether there are multiple columns with the same name.
	 * @return True when there are multiple columns with the same name: otherwise False.
	 */
	public boolean hasDuplicateColumnNames() {
		int nmbrOfColumns =this.getAllSelectColumnNames().size();
		int nmbrOfcolumnsRemovedDuplicatedColumns = new HashSet<String>(getAllSelectColumnNames()).size();
		if(nmbrOfColumns == nmbrOfcolumnsRemovedDuplicatedColumns) {
			return false;
		}
		return true;
	}

}
