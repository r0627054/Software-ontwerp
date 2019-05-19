package domain.model.sql.columnSpec;

import domain.model.sql.SqlException;
import domain.model.sql.expression.Expression;
import domain.model.sql.expression.LiteralNumberExpression;
import domain.model.sql.expression.MathOperatorExpression;

/**
 * A ColumnSpec is a part of the SelectStatemenent. 
 *  A ColumnSpec contains an Expression and a columnName.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class ColumnSpec {
	
	/**
	 * Variable storing the expression.
	 */
	private Expression expression;
	
	/**
	 * Variable storing the column name. (The column name for the computed table).
	 */
	private String columnName;
	
	/**
	 * Creates an instance of a columnSpec. Given an expression and a column name.
	 * @param expression  the expression of the column specification.
	 * @param columnName  the name of the column.
	 * @effect The expression and the columnName are set using the setters.
	 *         | this.setExpression(expression);
	 *         | this.setColumnName(columnName);
	 */
	public ColumnSpec(Expression expression, String columnName) {
		this.setExpression(expression);
		this.setColumnName(columnName);
	}

	/**
	 * Returns the expression of the columnSpec.
	 * @return the expression of the columnSpec.
	 */
	public Expression getExpression() {
		return expression;
	}
	
	/**
	 * The expression variable is set.
	 * @param expression The expression variable which will be set.
	 * @throws SqlException when the expression parameter equals null.
	 *         | expression== null
	 * @post the expression of the columnSpec is set.
	 *         | new.getExpression() = expression
	 */
	private void setExpression(Expression expression) {
		if(expression== null) {
			throw new SqlException("The expression of a columnSpec cannot be null.");
		}
		this.expression = expression;
	}
	
	/**
	 * Returns the name of the column.
	 * @return The name of the column.
	 */
	public String getColumnName() {
		return columnName;
	}
	
	/**
	 * Sets the name of the column.
	 * @param columnName The name to which the column will be set.
	 * @throws SqlException when the column name equals null or when the string is empty.
	 *        | columnName == null || columnName.trim().isEmpty()
	 * @post the name of the column is set. 
	 *        | new.getColumnName() = columnName
	 */
	private void setColumnName(String columnName) {
		if(columnName == null || columnName.trim().isEmpty()) {
			throw new SqlException("The columnName of ColumnSpec cannot be null of empty.");
		}
		this.columnName = columnName;
	}
	
	/**
	 * Returns the subTotal of the expression when it is a mathOpertatorExpression.
	 * @return The subTotal of the mathOperatorExpression.
	 */
	public int getSubtotal() {
		if(this.getExpression() instanceof MathOperatorExpression) {
			return ((MathOperatorExpression) this.getExpression()).getSubtotal();
		}
		return 0;
	}
	
	/**
	 * @return The string is composed as it is written in a sql Query.
	 *  = The expression + " AS " + the column name.
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getExpression().toString() + " AS " + this.getColumnName();
	}
	
}
