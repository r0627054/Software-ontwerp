package domain.model.sql.expression;

public interface Expression {

	public default Expression getResult() {
		return this;
	}
}
