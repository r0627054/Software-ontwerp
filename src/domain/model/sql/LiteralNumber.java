package domain.model.sql;

public class LiteralNumber implements Expression {
	private int value;

	public LiteralNumber(int value) {
		this.setValue(value);
	}
	
	public LiteralNumber(String intString) {
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
	
	
}
