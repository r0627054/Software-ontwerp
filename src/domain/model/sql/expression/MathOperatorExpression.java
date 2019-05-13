package domain.model.sql.expression;

import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

public class MathOperatorExpression extends OperatorExpression {

	public MathOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		Expression left = getLeftExpression().simplify(row, cellIdMap);
		Expression right = getRightExpression().simplify(row, cellIdMap);

		if (left instanceof LiteralNumberExpression && right instanceof LiteralNumberExpression) {
			int result = 0;
			if (getOperator().equals(Operator.PLUS)) {
				result = ((LiteralNumberExpression) left).getValue() + ((LiteralNumberExpression) right).getValue();
			} else if (getOperator().equals(Operator.MINUS)) {
				result = ((LiteralNumberExpression) left).getValue() - ((LiteralNumberExpression) right).getValue();
			} 
			return new LiteralNumberExpression(result);

		} else if (left instanceof LiteralStringExpression && right instanceof LiteralStringExpression) {
			if (getOperator().equals(Operator.PLUS)) {
				String result = ((LiteralStringExpression) left).getValue()
						+ ((LiteralStringExpression) right).getValue();
				return new LiteralNumberExpression(result);
			}
		}
		return new BooleanExpression(false);
	}

}
