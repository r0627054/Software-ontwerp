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
	private Integer value;
	private Map<UUID, Integer> usedIds = new HashMap<>();
	private Integer subTotal;

	public LiteralNumberExpression(Integer value, Integer subtotal, Map<UUID, Integer> usedIds) {
		this.setValue(value);
		this.setSubTotal(subtotal);
		this.setUsedIds(usedIds);
	}

	public LiteralNumberExpression(Integer value) {
		this(value, 0, new HashMap<>());
	}

	public LiteralNumberExpression(String intString) {
		this.setValue(this.parsetoInteger(intString));
	}

	public Map<UUID, Integer> getUsedIds() {
		return usedIds;
	}

	public UUID getFirstUUIDOfMap() {
		if (this.getUsedIds().keySet().size() > 1 || this.getUsedIds().keySet().size() == 0) {
			throw new SqlException("Cannot get the first UUID of the usedIdMap");
		}
		return (UUID) this.getUsedIds().keySet().toArray()[0];
	}

	public void setUsedIds(Map<UUID, Integer> usedIds) {
		this.usedIds = usedIds;
	}

	public boolean isOneEditable() {
		return this.getUsedIds().keySet().size() == 1 && !this.getUsedIds().containsKey(null);
	}

	public Integer getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}

	public Integer getValue() {
		return value;
	}

	private void setValue(Integer value) {
		this.value = value;
	}

	private Integer parsetoInteger(String intString) {
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

	@Override
	public Object[] isEditable() {
		Object[] result = { new HashMap<CellId, Integer>(), Boolean.FALSE };
		return result;
	}

	public void reset() {
		this.setUsedIds(new HashMap<>());
		this.setSubTotal(0);
	}

}
