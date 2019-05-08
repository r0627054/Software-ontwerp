package domain.model.sql.expression;

public interface Expression {

	public default Expression getResult() {
		return this;
	}

	boolean equals(Object obj);
	
	boolean greaterThan(Expression e);
	boolean smallerThan(Expression e);
}
