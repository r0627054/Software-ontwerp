package domain.model.sql;

import java.util.HashMap;
import java.util.Map;

import domain.model.sql.expression.BooleanExpression;
import domain.model.sql.expression.Expression;

/**
 * An enumeration of all the operators used (and currently supported) by sql expressions.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public enum Operator {
	OR("OR") ,
	AND("AND") ,
	EQUAL("="),
	GREATER(">") ,
	SMALLER("<") ,
	PLUS("+") ,
	MINUS("-");

	/**
	 * Variable storing the Symbol String of the operator.
	 */
	private String symbol;
	
	/**
	 * A variable map which has all the symbols linked with their operator.
	 */
	private static final Map<String, Operator> lookup = new HashMap<String, Operator>();

	/**
	 * Puts all the symbols with their operator enumeration in the HashMap.
	 */
	static {
		for (Operator op : Operator.values()) {
			lookup.put(op.getSymbol(), op);
		}
	}

	/**
	 * Private constructor used to set the symbol value to each enumeration instance.
	 * @param symbol The symbol which is linked to the specific operator.
	 * @effect The symbol is set for the operator.
	 *         | this.setSymbol(symbol)
	 */
	private Operator(String symbol) {
		this.setSymbol(symbol);
	}

	/**
	 * Sets the symbol the specific operator.
	 * @param symbol The symbol linked to the operator.
	 * @throws SqlException when the symbol equals null or when the symbol is empty.
	 *         | symbol == null || symbol.trim().isEmpty()
	 * @effect the symbol equals the symbol variable.
	 *         | new.getSymbol() == symbol.
	 */
	private void setSymbol(String symbol) {
		if (symbol == null || symbol.trim().isEmpty()) {
			throw new SqlException("Symbol of an operator cannot be null or empty.");
		}
		this.symbol = symbol;
	}

	/**
	 * Returns the symbol of the operator.
	 * @return The symbol of the operator.
	 */
	public String getSymbol() {
		return this.symbol;
	}

	/**
	 * Returns the Operator of which the symbol is given.
	 * @param symbol The symbol of which the operator will be returned.
	 * @return The Operator of the symbol. 
	 *         | lookup.get(symbol)
	 */
	public static Operator getOperatorOfSymbol(String symbol) {
		return lookup.get(symbol);
	}

	/**
	 * Makes a String of the operator. 
	 * @return the String of the symbol.
	 */
	@Override
	public String toString() {
		return this.getSymbol();
	}


}