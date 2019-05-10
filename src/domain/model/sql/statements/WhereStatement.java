package domain.model.sql.statements;

import java.util.List;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.expression.Expression;

public class WhereStatement implements Statement {

	private Expression expression;
	
	public WhereStatement(Expression parseSqlExpression) {
		this.setExpression(parseSqlExpression);
	}

	public Expression getExpression() {
		return expression;
	}

	private void setExpression(Expression expression) {
		if(expression == null) {
			throw new SqlException("The expression in the where statement cannot be null");
		}
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return "WHERE " + this.getExpression().toString();
	}

	@Override
	public List<CellId> getAllCellIds() {
		return this.getExpression().getAllCellIds();
	}
}
