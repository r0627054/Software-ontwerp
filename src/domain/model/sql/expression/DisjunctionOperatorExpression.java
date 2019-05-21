package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.DomainCell;
import domain.model.Row;
import domain.model.ValueType;
import domain.model.sql.CellId;
import domain.model.sql.Operator;

/**
 * A ConjunctionOperatorExpression is an OperatorExpression which has an "OR" operator.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class DisjunctionOperatorExpression extends OperatorExpression {

	/**
	 * An instance of a DisjunctionOperatorExpression is created with the given parameters.
	 * @param leftExpression The left expression of the disjunction.
	 * @param rightExpression The right expression of the disjunction.
	 * @param operator       The operator which will be set.
	 */
	public DisjunctionOperatorExpression(Expression leftExpression, Expression rightExpression, Operator operator) {
		super(leftExpression, rightExpression, operator);
	}

	/**
	 * {@inheritDoc}
	 * It simplifies the expression with the row and cellIdMap.
	 * It can be simplified only when the left and right expression are cellId's or BooleanExpressions.
	 *  This is made because of edge cases. This class will handle the disjunction edge cases and how it will be simplified.
	 */
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
	 * Returns the disjunction operator expression is not editable.
	 */
	@Override
	public Object[] isEditable() {
		Object[] result = {new HashMap<CellId,Integer>(),false};
		return result;
	}

	@Override
	public void reset() {
		// Do nothing		
	}

}
