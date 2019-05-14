package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class LiteralNumberExpression implements Expression {
	private int value;
	private Map<UUID,Integer> usedIds = new HashMap<>(); 
	private int subTotal;

	public LiteralNumberExpression(int value, int subtotal, Map<UUID,Integer> usedIds) {
		this.setValue(value);
		this.setSubTotal(subtotal);
		this.setUsedIds(usedIds);
	}
	
	public LiteralNumberExpression(int value) {
		this(value, 0, new HashMap<>());
	}

	public LiteralNumberExpression(String intString) {
		this.setValue(this.parsetoInteger(intString));
	}
	
	public Map<UUID, Integer> getUsedIds() {
		return usedIds;
	}

	public void setUsedIds(Map<UUID, Integer> usedIds) {
		this.usedIds = usedIds;
	}
	
	public boolean isEditable() {
		return this.getUsedIds().keySet().size() == 1;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public int getValue() {
		return value;
	}
		
	private void setValue(int value) {
		this.value = value;
	}

	private int parsetoInteger(String intString) {
		try {
			return Integer.valueOf(intString);
		} catch (Exception e) {
			throw new SqlException("Invalid integer Expression.");
		}
	}
	
	@Override
	public String toString() {
		return " " + this.getValue() + " ";
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this;
	}
}
