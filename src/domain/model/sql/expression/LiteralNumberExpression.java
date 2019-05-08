package domain.model.sql.expression;

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
	public boolean equals(Object obj) {
		return obj != null && obj instanceof LiteralNumberExpression
				&& ((LiteralNumberExpression) obj).getValue() == this.getValue();
	}

	@Override
	public boolean greaterThan(Expression e) {
		return e != null && e instanceof LiteralNumberExpression
				&& ((LiteralNumberExpression) e).getValue() > this.getValue();
	}

	@Override
	public boolean smallerThan(Expression e) {
		return e != null && e instanceof LiteralNumberExpression
				&& ((LiteralNumberExpression) e).getValue() < this.getValue();
	}
}