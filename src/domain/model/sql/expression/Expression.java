package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;

/**
 * The expression interface contains the methods which every expression should implement.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public interface Expression {
	
	/**
	 * Returns all the cellIds of the expression.
	 * @return All the cellIds an expression contains.
	 * By default it returns an empty array List
	 */
	public default List<CellId> getAllCellIds(){
		return new ArrayList<>();
	}

	/**
	 * It returns an Object which contains two elements.
	 * The first element is Map<UUID,Integer> which has the columnId and how often it is used.
	 * Second element is Boolean value. Saying whether it is editable or not.
	 * @return The above described object. Saying if the expression is editable and how often all the columnIds occur.
	 */
	public Object[] isEditable();
	
	/**
	 * Simplifies the expression using the row and map of cellId and integers.
	 * @param row      The row of cells which will be used to simplify the expression.
	 * @param cellIdMap The cellIdMap It says which cellId is at which index in the row.
	 * @return A simplified version of the expression. It only contains the basic expression types.
	 */
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap);
	
	public void reset();
}
