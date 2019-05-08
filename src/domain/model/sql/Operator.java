package domain.model.sql;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
	OR("OR"), AND("AND"), EQUAL("="), GREATER(">"), SMALLER("<"), PLUS("+"), MINUS("-");

	private String symbol;
	private static final Map<String, Operator> lookup = new HashMap<String, Operator>();

	static {
		for (Operator op : Operator.values()) {
			lookup.put(op.getSymbol(), op);
		}
	}

	private Operator(String symbol) {
		this.setSymbol(symbol);
	}

	private void setSymbol(String symbol) {
		if (symbol == null || symbol.trim().isEmpty()) {
			throw new SqlException("Symbol of an operator cannot be null or empty.");
		}
		this.symbol = symbol;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public static Operator getOperatorOfSymbol(String symbol) {
		return lookup.get(symbol);
	}
	
	@Override
	public String toString() {
		return this.getSymbol();
	}

}