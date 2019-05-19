package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.ValueType;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

/**
 * A MathOperatorExpression is an OperatorExpression which has an addition or subtraction operator.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class MathOperatorExpression extends OperatorExpression {

	/**
	 * An instance of a MathOperatorExpression is created with the given parameters.
	 * @param leftExpression The left expression of the Math operator.
	 * @param rightExpression The right expression of the Math operator.
	 * @param operator       The operator which will be set.
	 */
	public MathOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	/**
	 * {@inheritDoc}
	 * The expression can be simplified to a literal number expression if the one of the two expression is
	 *  a cellIdExpression or a LiteralNumberExpression.
	 * In all other cases a false boolean expression is returned.
	 * All other edge cases will be in this method containig the addition or subtraction operator.
	 */
	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		Expression left = getLeftExpression().simplify(row, cellIdMap);
		Expression right = getRightExpression().simplify(row, cellIdMap);

		if (left instanceof LiteralNumberExpression && right instanceof LiteralNumberExpression) {
			int result = 0;
			int subtotal = 0;
			LiteralNumberExpression lne = ((LiteralNumberExpression) left);
			LiteralNumberExpression rne = ((LiteralNumberExpression) right);
			if (lne.getValue() == null || rne.getValue() == null) {
				return new LiteralNumberExpression(null, subtotal, new HashMap<>());
			} else {
				if (getOperator().equals(Operator.PLUS)) {
					result = lne.getValue() + rne.getValue();
					subtotal = lne.getSubTotal() + rne.getSubTotal();
				} else if (getOperator().equals(Operator.MINUS)) {
					result = lne.getValue() - rne.getValue();
					subtotal = lne.getSubTotal() - rne.getSubTotal();
				}
				Map<UUID, Integer> usedIds = this.mergeUsedIds(lne.getUsedIds(), rne.getUsedIds());
				// return new LiteralNumberExpression(result);
				return new LiteralNumberExpression(result, subtotal, usedIds);
			}
		} else if (left instanceof CellIdExpression && right instanceof CellIdExpression) {
			DomainCell leftCell = this.getDomainCellOfOutOfCellId(left, row, cellIdMap);
			DomainCell rightCell = this.getDomainCellOfOutOfCellId(right, row, cellIdMap);
			Map<UUID, Integer> usedIds = new HashMap<>();

			if ((leftCell.getId() == null && rightCell.getId() == null) || leftCell.getId().equals(rightCell.getId())) {
				usedIds.put(leftCell.getId(), 2);
			} else {
				usedIds.put(leftCell.getId(), 1);
				usedIds.put(rightCell.getId(), 1);
			}

			// if ((leftCell.getValue() instanceof Integer) && (rightCell.getValue()
			// instanceof Integer)) {
			if ((leftCell.getType() == ValueType.INTEGER) && (rightCell.getType() == ValueType.INTEGER)) {
				if (leftCell.getValue() == null || rightCell.getValue() == null) {
					return new LiteralNumberExpression(null, 0, usedIds);
				} else {
					int result = 0;
					Integer lc = (Integer) leftCell.getValue();
					Integer rc = (Integer) rightCell.getValue();
					if (getOperator().equals(Operator.PLUS)) {
						result = lc + rc;
					} else if (getOperator().equals(Operator.MINUS)) {
						result = lc - rc;
					}
					return new LiteralNumberExpression(result, 0, usedIds);
				}
			}
		} else if (left instanceof CellIdExpression && right instanceof LiteralNumberExpression) {
			LiteralNumberExpression rne = ((LiteralNumberExpression) right);
			Map<UUID, Integer> usedIdsMap = rne.getUsedIds();
			int subtotal = rne.getSubTotal();

			DomainCell leftCell = this.getDomainCellOfOutOfCellId(left, row, cellIdMap);

			if (usedIdsMap.containsKey(leftCell.getId())) {
				usedIdsMap.put(leftCell.getId(), usedIdsMap.get(leftCell.getId()) + 1);
			} else {
				usedIdsMap.put(leftCell.getId(), 1);
			}

			if (leftCell.getType() == ValueType.INTEGER) {
				if (leftCell.getValue() == null || rne.getValue() == null) {
					return new LiteralNumberExpression(null, subtotal, new HashMap<>());
				}
				int result = 0;
				Integer lc = (Integer) leftCell.getValue();
				if (getOperator().equals(Operator.PLUS)) {
					result = lc + rne.getValue();
					subtotal += rne.getValue();

				} else if (getOperator().equals(Operator.MINUS)) {
					result = lc - rne.getValue();
					subtotal -= rne.getValue();
				}
				return new LiteralNumberExpression(result, subtotal, usedIdsMap);
			}
		} else if (left instanceof LiteralNumberExpression && right instanceof CellIdExpression) {
			LiteralNumberExpression lne = ((LiteralNumberExpression) left);
			Map<UUID, Integer> usedIdsMap = lne.getUsedIds();
			int subtotal = lne.getSubTotal();

			DomainCell rightCell = this.getDomainCellOfOutOfCellId(right, row, cellIdMap);

			if (usedIdsMap.containsKey(rightCell.getId())) {
				usedIdsMap.put(rightCell.getId(), usedIdsMap.get(rightCell.getId()) + 1);
			} else {
				usedIdsMap.put(rightCell.getId(), 1);
			}

			if (rightCell.getType() == ValueType.INTEGER) {
				if (rightCell.getValue() == null || lne.getValue() == null) {
					return new LiteralNumberExpression(null, subtotal, new HashMap<>());
				}
				int result = 0;
				Integer rc = (Integer) rightCell.getValue();
				if (getOperator().equals(Operator.PLUS)) {
					result = lne.getValue() + rc;
					subtotal += lne.getValue();

				} else if (getOperator().equals(Operator.MINUS)) {
					result = lne.getValue() - rc;
					subtotal -= lne.getValue();

				}
				return new LiteralNumberExpression(result, subtotal, usedIdsMap);
			}
		}
		return new BooleanExpression(false);
	}

	/**
	 * Merges two maps with Ids and number of occurences.
	 * @param map1 The first map with UUIDS and number of occurrences.
	 * @param map2 The second map with UUIDS and number of occurrences.
	 * @return The merged map.
	 */
	private Map<UUID, Integer> mergeUsedIds(Map<UUID, Integer> map1, Map<UUID, Integer> map2) {
		Map<UUID, Integer> result = new HashMap<>(map1);
		for (UUID id : map2.keySet()) {
			if (map1.containsKey(id)) {
				result.put(id, (map1.get(id) + map2.get(id)));
			} else {
				result.put(id, map2.get(id));
			}
		}
		return result;
	}

	/**
	 * Extracts the cellId value out of the expression.
	 * @param exp a cellIdExpression.
	 * @param row The row on which the current calculation is in progress.
	 * @param cellIdMap The mapping of cellId to the index.
	 * @return The domainCell a the given index in the row.
	 */
	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ((CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}

	/**
	 * {@inheritDoc}
	 * Checks whether the expression is editable.
	 * Depending on the left and right expression says if the expression is editable.
	 *  One or both of the expressions should be cellId or a LiteralNumberExpression or another math expression or bracketExpression.
	 */
	@Override
	public Object[] isEditable() {
		Map<CellId, Integer> leftMap = (Map<CellId, Integer>) getLeftExpression().isEditable()[0];
		Map<CellId, Integer> rightMap = (Map<CellId, Integer>) getRightExpression().isEditable()[0];

		if (getLeftExpression() instanceof CellIdExpression && getRightExpression() instanceof CellIdExpression) {
			Map<CellId, Integer> resultMap = new HashMap<>();

			if (leftMap.keySet().toArray()[0].equals(rightMap.keySet().toArray()[0])) {
				if (getOperator() == Operator.PLUS) {
					resultMap.put((CellId) leftMap.keySet().toArray()[0], 2);
				} else {
					resultMap.put((CellId) leftMap.keySet().toArray()[0], 0);
				}

				Object[] result = { resultMap, Boolean.TRUE };
				return result;

			} else {
				resultMap.put((CellId) leftMap.keySet().toArray()[0], 1);
				if (getOperator().equals(Operator.PLUS)) {
					resultMap.put((CellId) rightMap.keySet().toArray()[0], 1);
				} else {
					resultMap.put((CellId) rightMap.keySet().toArray()[0], -1);
				}
				Object[] result = { resultMap, Boolean.FALSE };
				return result;
			}
		} else if (getLeftExpression() instanceof LiteralNumberExpression
				&& getRightExpression() instanceof CellIdExpression) {
			if (getOperator().equals(Operator.PLUS)) {
				Object[] result = { rightMap, Boolean.TRUE };
				return result;
			} else {
				Object[] result = { reverseMap(rightMap), Boolean.TRUE };
				return result;
			}

		} else if (getLeftExpression() instanceof CellIdExpression
				&& getRightExpression() instanceof LiteralNumberExpression) {
			Object[] result = { leftMap, Boolean.TRUE };
			return result;

		} else if (getLeftExpression() instanceof MathOperatorExpression
				&& getRightExpression() instanceof LiteralNumberExpression) {
			Object[] result = { leftMap, getLeftExpression().isEditable()[1] };
			return result;

		} else if (getLeftExpression() instanceof LiteralNumberExpression
				&& getRightExpression() instanceof MathOperatorExpression) {
			if (getOperator().equals(Operator.PLUS)) {
				Object[] result = { rightMap, getRightExpression().isEditable()[1] };
				return result;
			} else {
				Object[] result = { reverseMap(rightMap), getRightExpression().isEditable()[1] };
				return result;
			}
		} else if (getLeftExpression() instanceof CellIdExpression
				&& getRightExpression() instanceof MathOperatorExpression) {
			return isEditableMathAndCellId((MathOperatorExpression) getRightExpression(),
					(CellIdExpression) getLeftExpression(), true);

		} else if (getLeftExpression() instanceof MathOperatorExpression
				&& getRightExpression() instanceof CellIdExpression) {
			return isEditableMathAndCellId((MathOperatorExpression) getLeftExpression(),
					(CellIdExpression) getRightExpression(), false);

		} else if (getLeftExpression() instanceof MathOperatorExpression
				&& getRightExpression() instanceof MathOperatorExpression) {
			Map<CellId, Integer> resultMap = new HashMap<>(leftMap);

			for (Map.Entry<CellId, Integer> entry : rightMap.entrySet()) {
				if (resultMap.containsKey(entry.getKey())) {
					if (getOperator().equals(Operator.PLUS)) {
						resultMap.put(entry.getKey(), resultMap.get(entry.getKey()) + rightMap.get(entry.getKey()));
					} else {
						resultMap.put(entry.getKey(), resultMap.get(entry.getKey()) - rightMap.get(entry.getKey()));
					}
				} else {
					if (getOperator().equals(Operator.PLUS)) {
						resultMap.put(entry.getKey(), entry.getValue());
					} else {
						resultMap.put(entry.getKey(), entry.getValue() * (-1));
					}
				}
			}

			Boolean leftBoolean = (Boolean) getLeftExpression().isEditable()[1];
			Boolean rightBoolean = (Boolean) getRightExpression().isEditable()[1];
			int size = resultMap.keySet().size();

			boolean editable = size == 1 && (leftBoolean || rightBoolean);
			Object[] result = { resultMap, new Boolean(editable) };
			return result;

		} else if (getLeftExpression() instanceof BracketExpression) {
			MathOperatorExpression math = new MathOperatorExpression(
					((BracketExpression) getLeftExpression()).getResult(), getRightExpression(), getOperator());
			return math.isEditable();
		} else if (getRightExpression() instanceof BracketExpression) {
			MathOperatorExpression math = new MathOperatorExpression(getLeftExpression(),
					((BracketExpression) getRightExpression()).getResult(), getOperator());
			return math.isEditable();
		} else {
			Object[] result = { new HashMap<CellId, Integer>(), false };
			return result;
		}
	}

	/**
	 * Reverses the number of occurrences of each CellId will be reversed.
	 * @param rightMap The Map of which the number of occurrences will be reversed.
	 * @return The map with the same ids but all the occurrences are reversed.
	 */
	private Map<CellId, Integer> reverseMap(Map<CellId, Integer> rightMap) {
		Map<CellId, Integer> tempMap = new HashMap<>();
		for (Map.Entry<CellId, Integer> entry : rightMap.entrySet()) {
			tempMap.put(entry.getKey(), entry.getValue() * (-1));
		}
		return tempMap;
	}

	/**
	 * Creates the new return object when one expression is a math expression and the other is a cellId.
	 * @param math The MathOperatorExpression
	 * @param cell The CellIdExpression
	 * @param cellIdIsLeft True when cellIdExpression is left, otherwise false.
	 * @return The created return object of the isEditable method.
	 */
	public Object[] isEditableMathAndCellId(MathOperatorExpression math, CellIdExpression cell, boolean cellIdIsLeft) {
		Map<CellId, Integer> mathMap = (Map<CellId, Integer>) math.isEditable()[0];
		CellId cellId = cell.getValue();

		boolean contains = false;
		CellId mathCellId = null;
		for (CellId id : mathMap.keySet()) {
			if (cellId.equals(id)) {
				contains = true;
				mathCellId = id;
			}
		}
		if (contains) {
			if (cellIdIsLeft) {
				if (getOperator().equals(Operator.PLUS)) {
					mathMap.put(mathCellId, mathMap.get(mathCellId) + 1);
				} else {
					mathMap.put(mathCellId, (mathMap.get(mathCellId) * (-1)) + 1);
				}
			} else {
				if (getOperator().equals(Operator.PLUS)) {
					mathMap.put(mathCellId, mathMap.get(mathCellId) + 1);
				} else {
					mathMap.put(mathCellId, mathMap.get(mathCellId) - 1);
				}
			}
			Object[] result = { mathMap, getRightExpression().isEditable()[1] };
			return result;
		} else {
			if (cellIdIsLeft) {
				mathMap.put(cellId, 1);
			} else {
				if (getOperator().equals(Operator.PLUS)) {
					mathMap.put(cellId, 1);
				} else {
					mathMap.put(cellId, -1);
				}
			}

			Object[] result = { mathMap, new Boolean(mathMap.keySet().size() == 1) };
			return result;
		}
	}

	/**
	 * Calculates the subTotal of the MathOperatorExpression
	 * @return The subTotal out of the MathOperatorExpression
	 */
	public int getSubtotal() {
		// left
		int leftSubResult = 0;
		if (this.getLeftExpression() instanceof LiteralNumberExpression) {
			leftSubResult = ((LiteralNumberExpression) this.getLeftExpression()).getValue();
		} else if (this.getLeftExpression() instanceof BracketExpression) {
			leftSubResult = ((BracketExpression) this.getLeftExpression()).getSubTotal();
		} else if (this.getLeftExpression() instanceof MathOperatorExpression) {
			leftSubResult = ((MathOperatorExpression) this.getLeftExpression()).getSubtotal();
		}

		// right
		int rightSubResult = 0;
		if (this.getRightExpression() instanceof LiteralNumberExpression) {
			rightSubResult = ((LiteralNumberExpression) this.getRightExpression()).getValue();
		} else if (this.getRightExpression() instanceof BracketExpression) {
			rightSubResult = ((BracketExpression) this.getRightExpression()).getSubTotal();
		} else if (this.getRightExpression() instanceof MathOperatorExpression) {
			rightSubResult = ((MathOperatorExpression) this.getRightExpression()).getSubtotal();
		}

		if (this.getOperator().equals(Operator.MINUS)) {
			rightSubResult = rightSubResult * (-1);
		}

		return leftSubResult + rightSubResult;
	}

}
