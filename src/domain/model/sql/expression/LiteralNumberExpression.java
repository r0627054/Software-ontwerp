package domain.model.sql.expression;

import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class LiteralNumberExpression implements Expression {
	private int value;

	public LiteralNumberExpression(int value) {
		this.setValue(value);
	}

	public LiteralNumberExpression(String intString) {
		this.setValue(this.parsetoInteger(intString));
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
