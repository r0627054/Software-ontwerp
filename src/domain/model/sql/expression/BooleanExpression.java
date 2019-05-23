package domain.model.sql.expression;

import java.util.HashMap;
import java.util.Map;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

/**
 * The boolean expression is an expression which stores a boolean value.
 *  This can only be TRUE or FALSE.
 *  
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class BooleanExpression implements Expression {

	/**
	 * Variable storing the boolean value.
	 */
	private boolean value;

	/**
	 * Creates an instance using the string which represents a Boolean.
	 * @param stringValue The boolean value as string format.
	 * @effect the String is converted to a boolean type and the value is set.
	 *         | this.setValue(this.parseToBoolean(stringValue)); 
	 */
	public BooleanExpression(String stringValue) {
		this.setValue(this.parseToBoolean(stringValue));
	}

	/**
	 * Creates an instance of the boolean expression.
	 * @param value The boolean value of the expression.
	 * @effect the value parameter is set.
	 *        | this.setValue(value)
	 */
	public BooleanExpression(boolean value) {
		this.setValue(value);
	}

	/**
	 * Returns the value of the boolean expression.
	 * @return the value variable of the expression.
	 */
	public Boolean getValue() {
		return value;
	}

	/**
	 * Sets the given value.
	 * @param value the value which will be set.
	 * @effect the value is set to the given parameter.
	 *        | new.getValue() == value
	 */
	private void setValue(boolean value) {
		this.value = value;
	}

	/**
	 * Tries to parse a String to a boolean value.
	 *  If the String couldn't be parsed an expression is thrown.
	 * @param stringValue The boolean value represented as a string.
	 * @return The boolean value of in string format.
	 * @throws SqlException when the boolean couldn't converted to a boolean.
	 * @effect the String value is parsed to a Boolean. 
	 *         | Boolean.valueOf(stringValue);
	 */
	private boolean parseToBoolean(String stringValue) {
		try {
			return Boolean.valueOf(stringValue);
		} catch (Exception e) {
			throw new SqlException("Invalid boolean Expression");
		}
	}

	/**
	 * {@inheritDoc}
	 * @return the string is presentation is the upper case string representation of the boolean value.
	 */
	@Override
	public String toString() {
		return " " + this.getValue().toString().toUpperCase() + " ";
	}

	/**
	 * {@inheritDoc}
	 * The simplification of a boolean expression is a boolean expression itself.
	 */
	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * A Boolean expression is not editable.
	 */
	@Override
	public Object[] isEditable() {
		Object[] result = {new HashMap<CellId,Integer>() ,Boolean.FALSE};
		return result;
	}

	/**
	 * Resets and does nothing for the basic type boolean expression.
	 */
	@Override
	public void reset() {
		// Do nothing
	}

}
