package domain.model.sql.expression;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;

public class BracketExpression implements Expression {

	private Expression expression;
	
	public BracketExpression(Expression expression) {
		this.setExpression(expression);
	}
	
	/*@Override
	public boolean greaterThan(Expression e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean smallerThan(Expression e) {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public String toString() {
		return " ( " + this.getExpression().toString() + " ) ";  
	}

	private Expression getExpression() {
		return expression;
	}

	private void setExpression(Expression expression) {
		if(expression == null) {
			throw new SqlException("The expression cannot be null.");
		}
		this.expression = expression;
	}
	
	@Override
	public List<CellId> getAllCellIds() {
		return this.getExpression().getAllCellIds();
	}
	
	

}
