package domain.model.sql.statements;

import java.util.List;
import java.util.Map;

import domain.model.Column;
import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.expression.BooleanExpression;
import domain.model.sql.expression.CellIdExpression;
import domain.model.sql.expression.Expression;
import domain.model.sql.expression.LiteralNumberExpression;

/**
 * The WhereStatement is a statement of an SQL Query. It is composed of an expression.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class WhereStatement implements Statement {

	/**
	 * Variable storing the expression.
	 */
	private Expression expression;

	/**
	 * Initialise a new Where statement with the given expression.
	 * @param parseSqlExpression the SQL expression which will be set
	 * @effect The expression is set using the setter.
	 *         | this.setExpression(parseSqlExpression);
	 */
	public WhereStatement(Expression parseSqlExpression) {
		this.setExpression(parseSqlExpression);
	}

	/**
	 * Returns the expression of the where statement.
	 * @return the expression of the where statement.
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * Sets the expression of the where statement 
	 * @param expression the expression of the where statement.
	 * @throws SqlException when the expression equals null.
	 *          | expression == null
	 * @post the expression variable equals the parameter
	 *          | new.getExpression() == expression
	 */
	private void setExpression(Expression expression) {
		if (expression == null) {
			throw new SqlException("The expression in the where statement cannot be null");
		}
		this.expression = expression;
	}

	/**
	 * {@inheritDoc}
	 * The String of a Where statement equals "WHERE " + the toString of the expression.
	 */
	@Override
	public String toString() {
		return "WHERE " + this.getExpression().toString();
	}

	/**
	 * {@inheritDoc}
	 * Returns all the cellIds that the expression contains.
	 */
	@Override
	public List<CellId> getAllCellIds() {
		return this.getExpression().getAllCellIds();
	}

	/**
	 * Checks whether a row is valid. It validates the row an returns whether or not the row satisfies the expression in the where statement.
	 * @param row     The which will be validated.
	 * @param cellIdMap The map matched the cellId with the correct index in the row.
	 * @return True when the Row statisfies the expression; otherwise false.
	 */
	public boolean isRowValid(Row row, Map<CellId, Integer> cellIdMap) {
		Expression result = this.getExpression().simplify(row, cellIdMap);
		//the end result of the simplify method.
		if(result instanceof BooleanExpression) {
			return ((BooleanExpression) result).getValue();
		}else if(result instanceof LiteralNumberExpression) {
			return ((LiteralNumberExpression) result).getValue() != 0;
		}
		return false;
	}

}
