package domain.model.sql.expression;

import java.util.Map;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

public class RelationalOperatorExpression extends OperatorExpression {

	public RelationalOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		Expression left = getLeftExpression().simplify(row, cellIdMap);
		Expression right = getRightExpression().simplify(row, cellIdMap);

		if (left instanceof CellIdExpression && right instanceof CellIdExpression) {
			CellId leftCellId = ((CellIdExpression) left).getValue();
			CellId rightCellId = ((CellIdExpression) right).getValue();

			Integer leftIndex = cellIdMap.get(leftCellId);
			Integer rightIndex = cellIdMap.get(rightCellId);

			DomainCell leftCell = row.getCellAtIndex(leftIndex);
			DomainCell rightCell = row.getCellAtIndex(rightIndex);

			if (leftCell != null && rightCell != null) {
				if (leftCell.compare(rightCell, getOperator())) {
					return new BooleanExpression(true);
				}
			}

		}
		return new BooleanExpression(false);

	}

}
