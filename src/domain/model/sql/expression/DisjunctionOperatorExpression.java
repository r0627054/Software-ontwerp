package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.ValueType;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

public class DisjunctionOperatorExpression extends OperatorExpression {

	public DisjunctionOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		Expression left = getLeftExpression().simplify(row, cellIdMap);
		Expression right = getRightExpression().simplify(row, cellIdMap);

		if ((left instanceof BooleanExpression && ((BooleanExpression) left).getValue())
				|| (right instanceof BooleanExpression && ((BooleanExpression) right).getValue())) {
			return new BooleanExpression(true);
		} else if(left instanceof CellIdExpression && right instanceof BooleanExpression) {
			DomainCell cell = this.getDomainCellOfOutOfCellId(left, row, cellIdMap);
			if(cell.getType() == ValueType.BOOLEAN) {
				return new BooleanExpression( ((Boolean) cell.getValue()) || ((BooleanExpression) right).getValue());
			}
			
			
		} else if (left instanceof CellIdExpression && right instanceof CellIdExpression) {
			DomainCell lc = this.getDomainCellOfOutOfCellId(left, row, cellIdMap);
			DomainCell rc = this.getDomainCellOfOutOfCellId(right, row, cellIdMap);
			if(lc.getType() == ValueType.BOOLEAN && rc.getType() == ValueType.BOOLEAN) {
				return new BooleanExpression( ((Boolean) lc.getValue()) || ((Boolean) rc.getValue()) );
			}
			
		} else if(left instanceof BooleanExpression && right instanceof CellIdExpression){
			DomainCell cell = this.getDomainCellOfOutOfCellId(right, row, cellIdMap);
			if(cell.getType() == ValueType.BOOLEAN) {
				return new BooleanExpression( ((Boolean) cell.getValue()) || ((BooleanExpression) left).getValue());
			}
		}
		
		
		return new BooleanExpression(false);
	}
	
	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ((CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}
	

	@Override
	public Object[] isEditable() {
		Object[] result = {new HashMap<CellId,Integer>(),false};
		return result;
	}

}
