package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class CellIdExpression implements Expression {
	private CellId value;

	public CellIdExpression(CellId cellId) {
		this.setValue(cellId);
	}

	public CellId getValue() {
		return value;
	}

	private void setValue(CellId value) {
		if (value == null) {
			throw new SqlException("CellId cannot be null within the expression");
		}
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CellIdExpression) {
			CellId value = ((CellIdExpression) obj).getValue();
			return value.getColumnName().equals(this.getValue().getColumnName())
					&& value.getTableId().equals(this.getValue().getTableId());
		} else {
			return false;
		}
	}
	
	public String getColumnNameOfCellId() {
		return this.getValue().getColumnName();
	}

	public String getTableNameOfCellId() {
		return this.getValue().getTableId();
	}
	
	@Override
	public String toString() {
		return " " + this.getTableNameOfCellId() + "." + this.getColumnNameOfCellId() +" ";
	}
	
	@Override
	public List<CellId> getAllCellIds() {
		List<CellId> result =new ArrayList<>();
		result.add(this.getValue());
		return result;
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this; 
	}

	@Override
	public Object[] isEditable() {
		HashMap<CellId, Integer> map = new HashMap<>();
		map.put(this.getValue(), 1);
		Object[] result = {map,Boolean.TRUE};
		return result;
	}

	@Override
	public void reset() {
		// Do nothing
	}

	
}
