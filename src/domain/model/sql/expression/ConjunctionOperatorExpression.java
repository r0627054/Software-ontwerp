package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

public class ConjunctionOperatorExpression extends OperatorExpression {

	public ConjunctionOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		Expression left = getLeftExpression().simplify(row, cellIdMap);
		Expression right = getRightExpression().simplify(row, cellIdMap);
		 
		if (left instanceof BooleanExpression && ((BooleanExpression) left).getValue()
				&& right instanceof BooleanExpression && ((BooleanExpression) right).getValue()) {
			return new BooleanExpression(true);
		} else
			return new BooleanExpression(false);
	}

	@Override
	public Object[] isEditable() {
		Object[] result = {new HashMap<CellId,Integer>(),false};
		return result;
	}

}
