package domain.model.sql.columnSpec;

import domain.model.sql.SqlException;
import domain.model.sql.expression.Expression;
import domain.model.sql.expression.LiteralNumberExpression;
import domain.model.sql.expression.MathOperatorExpression;

public class ColumnSpec {
	
	private Expression expression;
	private String columnName;
	
	public ColumnSpec(Expression expression, String columnName) {
		this.setExpression(expression);
		this.setColumnName(columnName);
	}

	public Expression getExpression() {
		return expression;
	}
	
	private void setExpression(Expression expression) {
		if(expression== null) {
			throw new SqlException("The expression of a columnSpec cannot be null.");
		}
		this.expression = expression;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	private void setColumnName(String columnName) {
		if(columnName == null || columnName.trim().isEmpty()) {
			throw new SqlException("The columnName of ColumnSpec cannot be null of empty.");
		}
		this.columnName = columnName;
	}
	
	public int getSubtotal() {
		if(this.getExpression() instanceof MathOperatorExpression) {
			return ((MathOperatorExpression) this.getExpression()).getSubtotal();
		}
		return 0;
	}
	
	
	@Override
	public String toString() {
		return this.getExpression().toString() + " AS " + this.getColumnName();
	}
	
}
