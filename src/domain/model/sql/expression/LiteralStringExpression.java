package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;
/**
 * A LiteralStringExpression is an expression which contains a String.
 * 
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class LiteralStringExpression implements Expression {

	/**
	 * Variable storing the String value of the expression.
	 */
	private String value;

	/**
	 * Initialises a LiteralStringExpression with the given value.
	 * @param value the value of the literalStringExpression.
	 * @effect the value of the expression is set.
	 *        | this.setValue(value);
	 */
	public LiteralStringExpression(String value) {
		this.setValue(value);
	}

	/**
	 * Returns the (String) value of the expression.
	 * @return the String value of the expression.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the expression.
	 * @param value The value to which the variable will be set.
	 * @throws SqlException when the value equals null or when the value is empty.
	 *          | value == null || value.trim().isEmpty()
	 * @post the value is set.
	 *          | new.getValue() == value
	 */
	private void setValue(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new SqlException("Cannot set empty value for LiteralString");
		}
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 *  The toString method returns the value surrounded with a double quote.
	 */
	@Override
	public String toString() {
		return " \"" + this.getValue() + "\" ";
	}

	/**
	 * A LiteralStringExpression cannot be simplified.
	 * {@inheritDoc}
	 */
	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * A LiteralStringExpression is not editable.
	 */
	@Override
	public Object[] isEditable() {
		Object[] result = {new HashMap<CellId,Integer>() ,Boolean.FALSE};
		return result;
	}

	/**
	 * Resets and does nothing for the basic type LiteralStringExpression expression.
	 */
	@Override
	public void reset() {
		// Do nothing		
	}


}
