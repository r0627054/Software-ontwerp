package domain.model.sql.expression;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import domain.model.Row;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;

/**
 * A LiteralNumberExpression is an expression which contains a literal Number.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class LiteralNumberExpression implements Expression {
	
	/**
	 * Variable storing the actual integer value.
	 */
	private Integer value;
	
	/**
	 * Variable storing how the literal number was build up. It stores the UUID of the columns of which it was calculated and how often.
	 */
	private Map<UUID, Integer> usedIds = new HashMap<>();
	
	/**
	 * Returns the subtotal of the literalNumber. This is what number there was added to the original number in the full expression.
	 */
	private Integer subTotal;

	/**
	 * Creates an instance of a literalNumber expression.
	 * @param value    the value of the literalNumber expression
	 * @param subtotal the value of the subTotal (the number added or subtracted to the value)
	 * @param usedIds  The UUIDs used for composing the literalNumberExpression.
	 * @effect the variables are set using the setters. 
	 *         | this.setValue(value);
	 *         | this.setSubTotal(subtotal);
	 *         | this.setUsedIds(usedIds);
	 */
	public LiteralNumberExpression(Integer value, Integer subtotal, Map<UUID, Integer> usedIds) {
		this.setValue(value);
		this.setSubTotal(subtotal);
		this.setUsedIds(usedIds);
	}

	/**
	 * Creates an instance of a literalNumberExpression.
	 * @param value the value of the literal number expression.
	 * @effect The value is set and a default subTotal of 0 is used and an empty hashmap.
	 *         | this(value, 0, new HashMap<>());
	 */
	public LiteralNumberExpression(Integer value) {
		this(value, 0, new HashMap<>());
	}

	/**
	 * Creates a literal number Expression is created out of a string value of an integer.
	 * @param intString The String value of an integer.
	 * @effect the String is parsed to an integer and this value is set.
	 *          | this.setValue(this.parsetoInteger(intString));
	 */
	public LiteralNumberExpression(String intString) {
		this.setValue(this.parsetoInteger(intString));
	}

	/**
	 * returns the ids Map which are used for composing the expression literal number.
	 * @return the ids Map which are used for composing the expression literal number.
	 */
	public Map<UUID, Integer> getUsedIds() {
		return usedIds;
	}

	/**
	 * Sets the map of Ids used of which the literal is composed.
	 * @param usedIds The map of ids used of which the literal is composed.
	 * @post The usedIds map is set to the given parameter.
	 *        | new.getUsedIds() == usedIds
	 */
	public void setUsedIds(Map<UUID, Integer> usedIds) {
		this.usedIds = usedIds;
	}

	/**
	 * Returns the if the literal is editable.
	 * It checks whether the literal is composed out of only one Id.
	 * @return True when the literal is editable and only contains one Id of which it is composed.
	 */
	public boolean isOneEditable() {
		return this.getUsedIds().keySet().size() == 1 && !this.getUsedIds().containsKey(null);
	}

	/**
	 * Returns the subTotal variable of the literalNumber expression.
	 * @return the subtTotal variable of the literalNumber expression.
	 */
	public Integer getSubTotal() {
		return subTotal;
	}

	/**
	 * Sets the subTotal to the given parameter.
	 * @param subTotal The subtotal parameter to which it will be set.
	 * @post The subTotal variable is set to the given parameter.
	 *       | new.getSubtotal() = subTotal.
	 */
	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * Returns the value of the literal number.
	 * @return The value of the literal number.
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Sets the value of the of the literal number expression.
	 * @param value The value to which it will be set.
	 * @post the value variable is set to the given parameter.
	 *        | new.getValue() = value
	 */
	private void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * Tries to parse the String (which contains an integer) to an integer value. 
	 * @param intString The String which contains an integer.
	 * @return The integer value which was in the String.
	 *          | Integer.valueOf(intString);
	 * @effect the parsed value is returned.
	 *          | Integer.valueOf(intString);
	 * @throws SqlException when the String couldn't be parsed to an integer.
	 */
	private Integer parsetoInteger(String intString) {
		try {
			return Integer.valueOf(intString);
		} catch (Exception e) {
			throw new SqlException("Invalid integer Expression.");
		}
	}
		
	/**
	 * Returns the first UUID in the map if it only contains one value.
	 * @return The first UUID in the map.
	 * @throws SqlException when there are no elements in the map or when there are more than 1 element in the map.
	 *         | this.getUsedIds().keySet().size() > 1 || this.getUsedIds().keySet().size() == 0
	 */
	public UUID getFirstUUIDOfMap() {
		if (this.getUsedIds().keySet().size() > 1 || this.getUsedIds().keySet().size() == 0) {
			throw new SqlException("Cannot get the first UUID of the usedIdMap");
		}
		return (UUID) this.getUsedIds().keySet().toArray()[0];
	}

	/**
	 * {@inheritDoc}
	 * The to String exists only of the value of the literal.
	 */
	@Override
	public String toString() {
		return " " + this.getValue() + " ";
	}

	/**
	 * {@inheritDoc}
	 * A LiteralNumberExpression cannot be simplified futher.
	 */
	@Override
	public Expression simplify(Row row, Map<CellId, Integer> cellIdMap) {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * A Literal Number by itself is not editable.
	 */
	@Override
	public Object[] isEditable() {
		Object[] result = { new HashMap<CellId, Integer>(), Boolean.FALSE };
		return result;
	}

	public void reset() {
		this.setUsedIds(new HashMap<>());
		this.setSubTotal(0);
	}

}
