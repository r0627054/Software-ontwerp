package domain.model.sql.expression;

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
		return " " + this.getValue().toString() + " ";
	}

	/*@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof BooleanExpression && ((BooleanExpression) obj).getValue() == getValue();
	}

	@Override
	public boolean greaterThan(Expression e) {
		return e != null && e instanceof BooleanExpression && !((BooleanExpression)e).getValue() && this.getValue();
	}

	@Override
	public boolean smallerThan(Expression e) {
		return e != null && e instanceof BooleanExpression && (((BooleanExpression)e).getValue() && !this.getValue());
	}
*/
}
