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

public class WhereStatement implements Statement {

	private Expression expression;

	public WhereStatement(Expression parseSqlExpression) {
		this.setExpression(parseSqlExpression);
	}

	public Expression getExpression() {
		return expression;
	}

	private void setExpression(Expression expression) {
		if (expression == null) {
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
