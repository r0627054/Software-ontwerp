package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.CellId;

public interface Expression {

	public default Expression getResult() {
		return this;
	}
	
	public default List<CellId> getAllCellIds(){
		return new ArrayList<>();
	}

	//boolean equals(Object obj);
	//boolean greaterThan(Expression e);
	//boolean smallerThan(Expression e);
	
	//Expression simplify();
}
