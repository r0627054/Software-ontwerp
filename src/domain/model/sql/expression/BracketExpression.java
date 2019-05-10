package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class BracketExpression implements Expression {

	private Expression expression;

	public BracketExpression(Expression expression) {
		this.setExpression(expression);
	}

	@Override
	public String toString() {
		return " ( " + this.getExpression().toString() + " ) ";
	}

	private Expression getExpression() {
		return expression;
	}

	private void setExpression(Expression expression) {
		if (expression == null) {
			throw new SqlException("The expression cannot be null.");
		}
		this.expression = expression;
	}

	@Override
	public List<CellId> getAllCellIds() {
		return this.getExpression().getAllCellIds();
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this.getExpression().simplify(row, cellIdMap);
	}

}
