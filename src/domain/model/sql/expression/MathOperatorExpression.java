package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import domain.model.DomainCell;
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
			int subtotal = 0;
			LiteralNumberExpression lne = ((LiteralNumberExpression) left);
			LiteralNumberExpression rne = ((LiteralNumberExpression) right);
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

			if ((leftCell.getValue() instanceof Integer) && (rightCell.getValue() instanceof Integer)) {
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

			if (leftCell.getValue() instanceof Integer) {
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
			
			if (rightCell.getValue() instanceof Integer) {
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

}
