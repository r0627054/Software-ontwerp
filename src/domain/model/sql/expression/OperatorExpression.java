package domain.model.sql.expression;

import domain.model.sql.Operator;
import domain.model.sql.SqlException;

public class OperatorExpression implements Expression {
	private Expression leftExpression;
	private Expression rightExpression;
	private Operator operator;
	
	public OperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		this.setLeftExpression(leftExpression);
		this.setRightExpression(rightExpression);
		this.setOperator(operator);
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	private void setLeftExpression(Expression leftExpression) {
		if(leftExpression == null) {
			throw new SqlException("The leftExpression cannot be null.");
		}
		this.leftExpression = leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	private void setRightExpression(Expression rightExpression) {
		if(rightExpression == null) {
			throw new SqlException("The rightExpression cannot be null.");
		}
		this.rightExpression = rightExpression;
	}

	public Operator getOperator() {
		return operator;
	}

	private void setOperator(Operator operator) {
		if(operator == null) {
			throw new SqlException("The operator cannot be null.");
		}
		this.operator = operator;
	}
	
	@Override
	public Expression getResult() {
		return this.getOperator().getResult(this.getLeftExpression(), this.getRightExpression());
	}
	
	
	
}
