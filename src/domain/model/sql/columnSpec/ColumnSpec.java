package domain.model.sql.columnSpec;

import domain.model.sql.SqlException;
import domain.model.sql.expression.Expression;

public class ColumnSpec {
	
	private Expression expression;
	private String columnName;
	
	public ColumnSpec(Expression expression, String columnName) {
		this.setExpression(expression);
		this.setColumnName(columnName);
	}

	private Expression getExpression() {
		return expression;
	}
	
	private void setExpression(Expression expression) {
		if(expression== null) {
			throw new SqlException("The expression of a columnSpec cannot be null.");
		}
		this.expression = expression;
	}
	
	private String getColumnName() {
		return columnName;
	}
	
	private void setColumnName(String columnName) {
		if(columnName == null || columnName.trim().isEmpty()) {
			throw new SqlException("The columnName of ColumnSpec cannot be null of empty.");
		}
		this.columnName = columnName;
	}
}
