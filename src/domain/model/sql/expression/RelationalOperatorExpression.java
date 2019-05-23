package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

/**
 * A RelationalOperatorExpression is an OperatorExpression.
 *  The relationalOperators are "greater than" or "smaller than".
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class RelationalOperatorExpression extends OperatorExpression {

	

	/**
	 * An instance of a RelationalOperatorExpression is created with the given parameters.
	 * @param leftExpression The left expression of the relational operator.
	 * @param rightExpression The right expression of the relational operator.
	 * @param operator       The operator which will be set.
	 */
	public RelationalOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	/**
	 * {@inheritDoc}
	 * The expression can be simplified to a Boolean expression if the one of the two expression is
	 *  a cellIdExpression or a LiteralNumberExpression.
	 * In all other cases a false boolean expression is returned.
	 * All other edge cases will be in this method containing the  "greater than" or "smaller than" operators.
	 */
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
			} else if (leftCell == null && rightCell == null) {
				return new BooleanExpression(true);
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
			Integer compareInt = isCellIdLeft ? ((LiteralNumberExpression) right).getValue()
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
		} else if (left instanceof BooleanExpression && right instanceof BooleanExpression) {
			return compareTwoBooleans(((BooleanExpression) left).getValue(), ((BooleanExpression) right).getValue());
		}

		return new BooleanExpression(false);

	}

	/**
	 * Compares a boolean to cell and returns the result as a boolean expression.
	 * @param cell        The cell to which the boolean will be compared.
	 * @param compareBool The Boolean value to which it will be compared.
	 * @param isCellIdLeft True when the cellId expression is on the left; otherwise false.
	 * @return Compares the values and returns the result as a booleanExpression.
	 */
	private Expression compareCellIdToBool(DomainCell cell, boolean compareBool, boolean isCellIdLeft) {
		if (cell.getValue() == null) {
			return new BooleanExpression(false);
		}

		Operator op = reverseOperatorIfCellRight(isCellIdLeft);
		boolean result = cell.compare(compareBool, op);

		return new BooleanExpression(result);
	}

	/**
	 * Compares a String to cell and returns the result as a boolean expression.
	 * @param cell        The cell to which the String will be compared.
	 * @param compareString The String value to which it will be compared.
	 * @param isCellIdLeft True when the cellId expression is on the left; otherwise false.
	 * @return Compares the values and returns the result as a booleanExpression.
	 */
	private Expression compareCellIdToString(DomainCell cell, String compareString, boolean isCellIdLeft) {
		if (cell.getValue() == null) {
			return new BooleanExpression(false);
		}
		Operator op = reverseOperatorIfCellRight(isCellIdLeft);
		boolean result = cell.compare(compareString, op);

		return new BooleanExpression(result);
	}

	/**
	 * Compares an Integer to cell and returns the result as a boolean expression.
	 * @param cell        The cell to which the Integer will be compared.
	 * @param compareString The Integer value to which it will be compared.
	 * @param isCellIdLeft True when the cellId expression is on the left; otherwise false.
	 * @return Compares the values and returns the result as a booleanExpression.
	 */
	private Expression compareCellIdToInt(DomainCell cell, Integer compareInt, boolean isCellIdLeft) {
		Operator op = reverseOperatorIfCellRight(isCellIdLeft);
		boolean result = cell.compare(compareInt, op);
		return new BooleanExpression(result);
	}

	/**
	 * Returns the opposite operator of the current one if the cellIdExpression is not on the left.
	 * @param isCellIdLeft True when the cellIdExpression is on the left.
	 * @return The opposite operator of the the current one if the cellIdExpression is not on the left.
	 */
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

	/**
	 * Compares two Strings with each other.
	 * @param left  The String on the left.
	 * @param right The String on the right.
	 * @return the result as a BooleanExpression depending on the operator.
	 */
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

	/**
	 * Compares two booleans with each other.
	 * @param left  The boolean on the left.
	 * @param right The boolean on the right.
	 * @return the result as a BooleanExpression depending on the operator.
	 */
	private BooleanExpression compareTwoBooleans(boolean left, boolean right) {
		boolean result;
		switch (getOperator()) {
		case GREATER:
			result = left && !right;
			break;
		case SMALLER:
			result = !left && right;
			break;
		case EQUAL:
			result = (left && right) || (!left && !right);
			break;
		default:
			result = false;
			break;
		}
		return new BooleanExpression(result);
	}

	/**
	 * Compares two Integers with each other.
	 * @param left  The Integers on the left.
	 * @param right The Integers on the right.
	 * @return the result as a BooleanExpression depending on the operator.
	 */
	private BooleanExpression compareTwoInts(Integer left, Integer right) {
		if (left == null || right == null) {
			return new BooleanExpression(false);
		}

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

	/**
	 * {@inheritDoc}
	 * A RelationalOperatorExpression is not editable.
	 */
	@Override
	public Object[] isEditable() {
		Object[] result = { new HashMap<CellId, Integer>(), false };
		return result;
	}

	/**
	 * Resets the left and right expression.
	 */
	@Override
	public void reset() {
		getLeftExpression().reset();
		getRightExpression().reset();
	}
}
