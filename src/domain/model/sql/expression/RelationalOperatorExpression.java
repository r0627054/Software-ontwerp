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

		} else if ((left instanceof CellIdExpression && right instanceof LiteralStringExpression)
				|| (left instanceof LiteralStringExpression && right instanceof CellIdExpression)) {

			boolean isCellIdLeft = left instanceof CellIdExpression;
			CellId cellIid = isCellIdLeft ? ((CellIdExpression) left).getValue()
					: ((CellIdExpression) right).getValue();
			String compareString = isCellIdLeft ? ((LiteralStringExpression) right).getValue()
					: ((LiteralStringExpression) left).getValue();

			Integer index = cellIdMap.get(cellIid);
			DomainCell cell = row.getCellAtIndex(index);

			return compareCellIdToString(cell, compareString, isCellIdLeft);

		} else if ((left instanceof CellIdExpression && right instanceof LiteralNumberExpression)
				|| (left instanceof LiteralNumberExpression && right instanceof CellIdExpression)) {

			boolean isCellIdLeft = left instanceof CellIdExpression;
			CellId cellIid = isCellIdLeft ? ((CellIdExpression) left).getValue()
					: ((CellIdExpression) right).getValue();
			int compareInt = isCellIdLeft ? ((LiteralNumberExpression) right).getValue()
					: ((LiteralNumberExpression) left).getValue();

			Integer index = cellIdMap.get(cellIid);
			DomainCell cell = row.getCellAtIndex(index);

			return compareCellIdToInt(cell, compareInt, isCellIdLeft);

		} else if ((left instanceof CellIdExpression && right instanceof BooleanExpression)
				|| (left instanceof BooleanExpression && right instanceof CellIdExpression)) {

			boolean isCellIdLeft = left instanceof CellIdExpression;
			CellId cellIid = isCellIdLeft ? ((CellIdExpression) left).getValue()
					: ((CellIdExpression) right).getValue();
			boolean compareBool = isCellIdLeft ? ((BooleanExpression) right).getValue()
					: ((BooleanExpression) left).getValue();

			Integer index = cellIdMap.get(cellIid);
			DomainCell cell = row.getCellAtIndex(index);

			return compareCellIdToBool(cell, compareBool, isCellIdLeft);

		} else if (left instanceof LiteralNumberExpression && right instanceof LiteralNumberExpression) {
			return compareTwoInts(((LiteralNumberExpression) left).getValue(),
					((LiteralNumberExpression) right).getValue());

		} else if (left instanceof LiteralStringExpression && right instanceof LiteralStringExpression) {
			return compareTwoStrings(((LiteralStringExpression) left).getValue(),
					((LiteralStringExpression) right).getValue());
		}

		return new BooleanExpression(false);

	}

	private Expression compareCellIdToBool(DomainCell cell, boolean compareBool, boolean isCellIdLeft) {
		if (cell.getValue() == null) {
			return new BooleanExpression(false);
		}

		Operator op = reverseOperatorIfCellRight(isCellIdLeft);
		boolean result = cell.compare(compareBool, op);

		return new BooleanExpression(result);
	}

	private Expression compareCellIdToString(DomainCell cell, String compareString, boolean isCellIdLeft) {
		if (cell.getValue() == null) {
			return new BooleanExpression(false);
		}
		Operator op = reverseOperatorIfCellRight(isCellIdLeft);
		boolean result = cell.compare(compareString, op);

		return new BooleanExpression(result);
	}

	private Expression compareCellIdToInt(DomainCell cell, int compareInt, boolean isCellIdLeft) {
		if (cell.getValue() == null) {
			return new BooleanExpression(false);
		}
		Operator op = reverseOperatorIfCellRight(isCellIdLeft);
		boolean result = cell.compare(compareInt, op);
		return new BooleanExpression(result);
	}

	private Operator reverseOperatorIfCellRight(boolean isCellIdLeft) {
		if (!isCellIdLeft)
			switch (getOperator()) {
			case GREATER:
				return Operator.SMALLER;
			case SMALLER:
				return Operator.GREATER;
			}
		return getOperator();
	}

	private Expression compareTwoStrings(String left, String right) {
		boolean result;

		switch (getOperator()) {
		case GREATER:
			result = left.compareTo(right) > 0;
			break;
		case SMALLER:
			result = left.compareTo(right) < 0;
			break;
		case EQUAL:
			result = left.compareTo(right) == 0;
			break;
		default:
			result = false;
			break;
		}
		return new BooleanExpression(result);
	}

	private BooleanExpression compareTwoInts(int left, int right) {
		boolean result;

		switch (getOperator()) {
		case GREATER:
			result = left > right;
			break;
		case SMALLER:
			result = left < right;
			break;
		case EQUAL:
			result = left == right;
			break;
		default:
			result = false;
			break;
		}
		return new BooleanExpression(result);
	}

}
