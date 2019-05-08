package domain.model.sql.expression;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class CellIdExpression implements Expression {
	private CellId value;

	public CellIdExpression(CellId cellId) {
		this.setValue(cellId);
	}

	private CellId getValue() {
		return value;
	}

	private void setValue(CellId value) {
		if (value == null) {
			throw new SqlException("CellId cannot be null within the expression");
		}
		this.value = value;
	}

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

	@Override
	public boolean greaterThan(Expression e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean smallerThan(Expression e) {
		// TODO Auto-generated method stub
		return false;
	}
}