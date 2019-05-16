package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.ValueType;
import domain.model.sql.CellId;
import domain.model.sql.Operator;
import domain.model.sql.SqlException;

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
			int subtotal = 0;
			LiteralNumberExpression lne = ((LiteralNumberExpression) left);
			LiteralNumberExpression rne = ((LiteralNumberExpression) right);
			if(lne.getValue() == null || rne.getValue() == null) {
				return new LiteralNumberExpression(null, subtotal, new HashMap<>());
			}else {
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

			//if ((leftCell.getValue() instanceof Integer) && (rightCell.getValue() instanceof Integer)) {
			if((leftCell.getType() == ValueType.INTEGER) && (rightCell.getType() == ValueType.INTEGER)) {
				if(leftCell.getValue() == null || rightCell.getValue() == null) {
					return new LiteralNumberExpression(null,0,usedIds);
				}else {
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
				if(leftCell.getValue() == null || rne.getValue() == null) {
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
				if(rightCell.getValue() == null || lne.getValue() == null) {
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

	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ((CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}

	@Override
	public Object[] isEditable() {
		Map<CellId, Integer> leftMap = (Map<CellId, Integer>) getLeftExpression().isEditable()[0];
		Map<CellId, Integer> rightMap = (Map<CellId, Integer>) getRightExpression().isEditable()[0];

		if (getLeftExpression() instanceof CellIdExpression && getRightExpression() instanceof CellIdExpression) {
			Map<CellId, Integer> resultMap = new HashMap<>();

			if (leftMap.keySet().toArray()[0].equals(rightMap.keySet().toArray()[0])) {
				resultMap.put((CellId) leftMap.keySet().toArray()[0], 2);
				Object[] result = { resultMap, Boolean.TRUE };
				return result;

			} else {
				resultMap.put((CellId) leftMap.keySet().toArray()[0], 1);
				resultMap.put((CellId) rightMap.keySet().toArray()[0], 1);
				Object[] result = { resultMap, Boolean.FALSE };
				return result;
			}
		} else if (getLeftExpression() instanceof LiteralNumberExpression
				&& getRightExpression() instanceof CellIdExpression) {
			Object[] result = { rightMap, Boolean.TRUE };
			return result;

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
			Object[] result = { rightMap, getRightExpression().isEditable()[1] };
			return result;

		} else if (getLeftExpression() instanceof CellIdExpression
				&& getRightExpression() instanceof MathOperatorExpression) {
			return isEditableMathAndCellId((MathOperatorExpression) getRightExpression(),
					(CellIdExpression) getLeftExpression());

		} else if (getLeftExpression() instanceof MathOperatorExpression
				&& getRightExpression() instanceof CellIdExpression) {
			return isEditableMathAndCellId((MathOperatorExpression) getLeftExpression(),
					(CellIdExpression) getRightExpression());

		} else if (getLeftExpression() instanceof MathOperatorExpression
				&& getRightExpression() instanceof MathOperatorExpression) {
			Map<CellId, Integer> resultMap = new HashMap<>(leftMap);

			for (Map.Entry<CellId, Integer> entry : rightMap.entrySet()) {
				if (resultMap.containsKey(entry.getKey())) {
					resultMap.put(entry.getKey(), resultMap.get(entry.getKey()) + rightMap.get(entry.getKey()));
				} else {
					resultMap.put(entry.getKey(), entry.getValue());
				}
			}

			Boolean leftBoolean = (Boolean) getLeftExpression().isEditable()[2];
			Boolean rightBoolean = (Boolean) getRightExpression().isEditable()[2];
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

	public Object[] isEditableMathAndCellId(MathOperatorExpression math, CellIdExpression cell) {
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
			mathMap.put(mathCellId, mathMap.get(mathCellId) + 1);
			Object[] result = { mathMap, getRightExpression().isEditable()[1] };
			return result;
		} else {
			mathMap.put(cellId, 1);
			Object[] result = { mathMap, new Boolean(mathMap.keySet().size() == 1) };
			return result;
		}
	}

	public int getSubtotal() {
		//left
		int leftSubResult = 0;
		if(this.getLeftExpression() instanceof LiteralNumberExpression) {
			leftSubResult = ((LiteralNumberExpression) this.getLeftExpression()).getValue();
		} else if(this.getLeftExpression() instanceof BracketExpression) {
			leftSubResult = ((BracketExpression) this.getLeftExpression()).getSubTotal();
		} else if(this.getLeftExpression() instanceof MathOperatorExpression) {
			leftSubResult = ((MathOperatorExpression) this.getLeftExpression()).getSubtotal();
		}

		//right
		int rightSubResult = 0;
		if(this.getRightExpression() instanceof LiteralNumberExpression) {
			rightSubResult = ((LiteralNumberExpression) this.getRightExpression()).getValue();
		} else if(this.getRightExpression() instanceof BracketExpression) {
			rightSubResult = ((BracketExpression) this.getRightExpression()).getSubTotal();
		} else if(this.getRightExpression() instanceof MathOperatorExpression) {
			rightSubResult = ((MathOperatorExpression) this.getRightExpression()).getSubtotal();
		}
		
		if(this.getOperator().equals(Operator.MINUS)) {
			rightSubResult = rightSubResult*(-1);
		}
		
		return leftSubResult+rightSubResult;
	}

}
