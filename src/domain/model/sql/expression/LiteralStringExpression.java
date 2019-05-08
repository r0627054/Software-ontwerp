package domain.model.sql.expression;

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
}
