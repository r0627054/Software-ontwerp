package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class BooleanExpression implements Expression {

	private boolean value;

	public BooleanExpression(String stringValue) {
		this.setValue(this.parseToBoolean(stringValue));
	}

	public BooleanExpression(boolean value) {
		this.setValue(value);
	}

	public Boolean getValue() {
		return value;
	}

	private void setValue(boolean value) {
		this.value = value;
	}

	private boolean parseToBoolean(String stringValue) {
		try {
			return Boolean.valueOf(stringValue);
		} catch (Exception e) {
			throw new SqlException("Invalid boolean Expression");
		}
	}

	@Override
	public String toString() {
		return " " + this.getValue().toString().toUpperCase() + " ";
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

	@Override
	public void reset() {
		// Do nothing
	}

}
