package domain.model.sql;

public interface Expression {

	public default Expression getResult() {
		return this;
	}
}
