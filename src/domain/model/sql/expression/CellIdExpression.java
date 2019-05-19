package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

/**
 * A cellId Expression is an expression which only contains a cellId.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class CellIdExpression implements Expression {
	
	/**
	 * Variable storing the value of a cellId.
	 */
	private CellId value;

	/**
	 * Creates an instance of a cellIdExpression.
	 * @param cellId The cellId in the expression.
	 * @effect the cellId variable is set.
	 *        | this.setValue(cellId);
	 */
	public CellIdExpression(CellId cellId) {
		this.setValue(cellId);
	}

	/**
	 * Returns the cellId of the expression.
	 * @return
	 */
	public CellId getValue() {
		return value;
	}

	/**
	 * Sets the value of the CellId expression.
	 * @param value The cellId of the expression.
	 * @throws SqlException when the value equals null.
	 *         | value == null
	 * @effect The value is set.
	 *         | new.getValue() = value
	 */
	private void setValue(CellId value) {
		if (value == null) {
			throw new SqlException("CellId cannot be null within the expression");
		}
		this.value = value;
	}

	/**
	 * Returns the column name of the cellId. 
	 * @return the column name of the cellId.
	 * @effect the column name is returned.
	 *         | this.getValue().getColumnName()
	 */
	public String getColumnNameOfCellId() {
		return this.getValue().getColumnName();
	}

	/**
	 * Returns the table name of the cellId.
	 * @return the table name of the cellId.
	 *         | this.getValue().getTableId()
	 */
	public String getTableNameOfCellId() {
		return this.getValue().getTableId();
	}
	
	
	/**
	 * {@inheritDoc}
	 * Two cellIdExpressions are equal when the cellId has the same value for the column and table id.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CellIdExpression) {
			CellId value = ((CellIdExpression) obj).getValue();
			return value.getColumnName().equals(this.getValue().getColumnName())
					&& value.getTableId().equals(this.getValue().getTableId());
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * The to String value of a cellIdExpression equals " " + table name + "." + column name + " "
	 */
	@Override
	public String toString() {
		return " " + this.getTableNameOfCellId() + "." + this.getColumnNameOfCellId() +" ";
	}
	
	/**
	 * {@inheritDoc}
	 * The cellIdExpression only has one expression.
	 * It returns a list of only one cellId.
	 */
	@Override
	public List<CellId> getAllCellIds() {
		List<CellId> result =new ArrayList<>();
		result.add(this.getValue());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * The cellId cannot be simplified further.
	 */
	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this; 
	}

	/**
	 * {@inheritDoc}
	 * A cellId is editable and returns the its cellId in the hashmap.
	 */
	@Override
	public Object[] isEditable() {
		HashMap<CellId, Integer> map = new HashMap<>();
		map.put(this.getValue(), 1);
		Object[] result = {map,Boolean.TRUE};
		return result;
	}

	
}
