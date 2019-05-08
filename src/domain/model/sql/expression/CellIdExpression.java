package domain.model.sql.expression;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class CellIdExpression {
	private CellId value;

	public CellIdExpression(CellId cellId) {
		this.setValue(cellId);
	}
	
	private CellId getValue() {
		return value;
	}

	private void setValue(CellId value) {
		if(value == null) {
			throw new SqlException("CellId cannot be null within the expression");
		}
		this.value = value;
	}
	
}
