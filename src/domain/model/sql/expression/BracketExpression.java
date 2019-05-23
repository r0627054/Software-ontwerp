package domain.model.sql.expression;

import java.util.List;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

/**
 * The bracket expression is an expression which stores an expression between brackets.
 *   (It Stores an expression with another priority order.)
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class BracketExpression implements Expression {

	/**
	 * Variable storing the expression.
	 */
	private Expression expression;

	/**
	 * Initialise a new BracketExpression given an expression.
	 * @param expression The expression which is between the brackets.
	 * @effect The expression is set.
	 *        | this.setExpression(expression);
	 */
	public BracketExpression(Expression expression) {
		this.setExpression(expression);
	}
	
	/**
	 * Returns the expression.
	 * @return the expression.
	 */
	public Expression getResult() {
		return this.getExpression();
	}

	/**
	 * Sets the expression.
	 * @param expression The expression that will be set.
	 * @throws SqlException when the given expression equals null. 
	 *         | expression == null
	 * @post The expression is set.
	 *         | new.getExpression() = expression
	 */
	private void setExpression(Expression expression) {
		if (expression == null) {
			throw new SqlException("The expression cannot be null.");
		}
		this.expression = expression;
	}
	
	/**
	 * Returns the expression.
	 * @return the expression.
	 */
	private Expression getExpression() {
		return expression;
	}

	/**
	 * {@inheritDoc}
	 * Returns all the cellIds the expression contains.
	 */
	@Override
	public List<CellId> getAllCellIds() {
		return this.getExpression().getAllCellIds();
	}

	/**
	 * {@inheritDoc}
	 * simplifies the expression between the brackets.
	 */
	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this.getExpression().simplify(row, cellIdMap);
	}

	/**
	 * {@inheritDoc}
	 * Returns the String of the expression with brackets between the brackets. 
	 *   " ( " + expression.toString() + " ) "
	 */
	@Override
	public String toString() {
		return " ( " + this.getExpression().toString() + " ) ";
	}
	
	/**
	 * {@inheritDoc}
	 * Returns whether the expression is editable.
	 */
	@Override
	public Object[] isEditable() {
		return this.getExpression().isEditable();
	}

	/**
	 * Returns the sub total of the expression.
	 * @return sub total of the expression between the brackets.
	 */
	public int getSubTotal() {
		if(this.getExpression() instanceof LiteralNumberExpression) {
			return ((LiteralNumberExpression) this.getExpression()).getValue();
		} else if (this.getExpression() instanceof MathOperatorExpression) {
			return ((MathOperatorExpression) this.getExpression()).getSubtotal();
		} else if (this.getExpression() instanceof BracketExpression) {
			return ((BracketExpression) this.getExpression()).getSubTotal();
		}
		return 0;
	}


	/**
	 * Resets the expression. 
	 */
	@Override
	public void reset() {
		this.getExpression().reset();		
	}
	
}
