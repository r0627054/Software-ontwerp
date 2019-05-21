package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.CellId;
import domain.model.sql.Operator;
import domain.model.sql.SqlException;

/**
 * An Operator expression is an expression with two subExpressions and one operator.
 *  The class is abstract. Further distinction should be made.
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public abstract class OperatorExpression implements Expression {
	
	/**
	 * Variable storing the expression on the left of the operator.
	 */
	private Expression leftExpression;
	
	/**
	 * Variable storing the expression on the right of the operator.
	 */
	private Expression rightExpression;
	
	/**
	 * Variable storing the Operator of the expression.
	 */
	private Operator operator;

	/**
	 * Initialses a new OperatorExpression with an expression on the left and an expression on the right and an operator.
	 * @param leftExpression  The expression on the left of the operator
	 * @param rightExpression The expression on the right of the operator
	 * @param operator        The operator which will be set.
	 */
	public OperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		this.setLeftExpression(leftExpression);
		this.setRightExpression(rightExpression);
		this.setOperator(operator);
	}

	/**
	 * Returns the expression on the left of the operator.
	 * @return The expression on the left of the operator.
	 */
	public Expression getLeftExpression() {
		return leftExpression;
	}

	/**
	 * Sets the expression on the left of the operator.
	 * @param leftExpression the expression on the left of the operator.
	 * @throws SqlException when the leftExpression variable equals null.
	 *        | leftExpression == null
	 * @post the leftExpression variable is set.
	 *        | new.getLeftExpression() = leftExpression
	 */
	private void setLeftExpression(Expression leftExpression) {
		if (leftExpression == null) {
			throw new SqlException("The leftExpression cannot be null.");
		}
		this.leftExpression = leftExpression;
	}

	/**
	 * Returns the expression on the right of the operator.
	 * @return the expression on the right of the operator.
	 */
	public Expression getRightExpression() {
		return rightExpression;
	}

	/**
	 * Sets the expression on the right of the operator.
	 * @param rightExpression the expression on the right of the operator.
	 * @throws SqlException when the rightExpression equals null
	 *         | rightExpression == null
	 * @post the rightExpression variable is set.
	 *        | new.getRightExpression() = rightExpression
	 */
	private void setRightExpression(Expression rightExpression) {
		if (rightExpression == null) {
			throw new SqlException("The rightExpression cannot be null.");
		}
		this.rightExpression = rightExpression;
	}

	/**
	 * Returns the operator of the expression.
	 * @return the operator of the expression.
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Sets the operator of the expression.
	 * @param operator the operator of the expression.
	 * @throws SqlException when the operator equals null.
	 *         | operator == null
	 */
	private void setOperator(Operator operator) {
		if (operator == null) {
			throw new SqlException("The operator cannot be null.");
		}
		this.operator = operator;
	}

	/**
	 * {@inheritDoc}
	 *  The String is constructed as following: this.getLeftExpression().toString() + " " + this.getOperator().toString() + " "	+ this.getRightExpression().toString();
	 */
	@Override
	public String toString() {
		return this.getLeftExpression().toString() + " " + this.getOperator().toString() + " "
				+ this.getRightExpression().toString();
	}

	/**
	 * {@inheritDoc}
	 * Returns all the cellIds of the combined of the left and right expressions.
	 */
	@Override
	public List<CellId> getAllCellIds() {
		List<CellId> result = new ArrayList<>();
		result.addAll(this.getLeftExpression().getAllCellIds());
		result.addAll(this.getRightExpression().getAllCellIds());
		return result;
	}
}
