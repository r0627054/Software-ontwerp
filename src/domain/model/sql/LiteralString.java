package domain.model.sql;

public class LiteralString implements Expression {

	private String value;

	public LiteralString(String value) {
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
