package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;

public interface Expression {

	/*public default Expression getResult() {
		return this;
	}*/
	
	public default List<CellId> getAllCellIds(){
		return new ArrayList<>();
	}

	/**
	 * First element is Map<UUID,Integer> columnIds en hoe vaak column gebruikt
	 * Second element is Boolean isEditable until know
	 * @return
	 */
	public Object[] isEditable();
	
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap);
}
