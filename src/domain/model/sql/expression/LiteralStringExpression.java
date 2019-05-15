package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class LiteralStringExpression implements Expression {

	private String value;

	public LiteralStringExpression(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new SqlException("Cannot set empty value for LiteralString");
		}
		this.value = value;
	}

	@Override
	public String toString() {
		return " \"" + this.getValue() + "\" ";
	}

	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this;
	}

	@Override
	public Object[] isEditable() {
		Object[] result = {new HashMap<CellId,Integer>() ,Boolean.FALSE};
		return result;
	}


}
