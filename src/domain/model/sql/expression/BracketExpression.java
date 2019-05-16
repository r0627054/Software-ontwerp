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
	public Expression getResult() {
		return this.getExpression();
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

	@Override
	public Object[] isEditable() {
		return this.getExpression().isEditable();
	}

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
	
}
