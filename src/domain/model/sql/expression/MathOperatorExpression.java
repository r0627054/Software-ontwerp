package domain.model.sql.expression;

import java.util.Map;

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
		} else if(left instanceof CellIdExpression && right instanceof CellIdExpression) {
			DomainCell leftCell = this.getDomainCellOfOutOfCellId(left, row, cellIdMap);
			DomainCell rightCell = this.getDomainCellOfOutOfCellId(right, row, cellIdMap);
			if( (leftCell.getValue() instanceof Integer) && (rightCell.getValue() instanceof Integer) ) {
				int result = 0;
				Integer lc = (Integer) leftCell.getValue();
				Integer rc = (Integer) rightCell.getValue();
				if(getOperator().equals(Operator.PLUS)) {
					result = lc + rc;
				}else if(getOperator().equals(Operator.MINUS)) {
					result = lc - rc;
				}
				return new LiteralNumberExpression(result);
			}
		} else if(left instanceof CellIdExpression && right instanceof LiteralNumberExpression) {
			DomainCell leftCell = this.getDomainCellOfOutOfCellId(left, row, cellIdMap);
			if(leftCell.getValue() instanceof Integer) {
				int result = 0;
				Integer lc = (Integer) leftCell.getValue();
				if(getOperator().equals(Operator.PLUS)) {
					result = lc + ((LiteralNumberExpression) right).getValue();
				}else if(getOperator().equals(Operator.MINUS)) {
					result = lc - ((LiteralNumberExpression) right).getValue();
				}
				return new LiteralNumberExpression(result);
			}
		}else if(left instanceof LiteralNumberExpression && right instanceof CellIdExpression) {
			DomainCell rightCell = this.getDomainCellOfOutOfCellId(right, row, cellIdMap);
			if(rightCell.getValue() instanceof Integer) {
				int result = 0;
				Integer rc = (Integer) rightCell.getValue();
				if(getOperator().equals(Operator.PLUS)) {
					result =  ((LiteralNumberExpression) left).getValue() + rc;
				}else if(getOperator().equals(Operator.MINUS)) {
					result = ((LiteralNumberExpression) left).getValue() - rc;
				}
				return new LiteralNumberExpression(result);
			}
		}
		return new BooleanExpression(false);
	}
	
	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ( (CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}

}
