package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.CellId;
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
		if (leftExpression == null) {
			throw new SqlException("The leftExpression cannot be null.");
		}
		this.leftExpression = leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	private void setRightExpression(Expression rightExpression) {
		if (rightExpression == null) {
			throw new SqlException("The rightExpression cannot be null.");
		}
		this.rightExpression = rightExpression;
	}

	public Operator getOperator() {
		return operator;
	}

	private void setOperator(Operator operator) {
		if (operator == null) {
			throw new SqlException("The operator cannot be null.");
		}
		this.operator = operator;
	}

	@Override
	public Expression getResult() {
		return this.getOperator().getResult(this.getLeftExpression(), this.getRightExpression());
	}
	
	@Override
	public String toString() {
		return this.getLeftExpression().toString() + " " + this.getOperator().toString() +" "+ this.getRightExpression().toString();
	}
	
	@Override
	public List<CellId> getAllCellIds() {
		List<CellId> result =new ArrayList<>();
		result.addAll(this.getLeftExpression().getAllCellIds());
		result.addAll(this.getRightExpression().getAllCellIds());
		return result;
	}

	/*@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof OperatorExpression) {
			OperatorExpression value = (OperatorExpression) obj;

			return value.getLeftExpression().equals(this.getLeftExpression())
					&& value.getRightExpression().equals(this.getRightExpression())
					&& value.getOperator().equals(this.getOperator());
		}
		return false;
	}

	@Override
	public boolean greaterThan(Expression e) {
		return e != null && e instanceof OperatorExpression
				&& this.getResult().greaterThan(((OperatorExpression) e).getResult());
	}

	@Override
	public boolean smallerThan(Expression e) {
		return e != null && e instanceof OperatorExpression
				&& this.getResult().smallerThan(((OperatorExpression) e).getResult());
	}*/

}
