package domain.model.sql;

import java.util.HashMap;
import java.util.Map;

import domain.model.sql.expression.BooleanExpression;
import domain.model.sql.expression.Expression;

public enum Operator {
	OR("OR") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			if ((e1 instanceof BooleanExpression && ((BooleanExpression) e1).getValue())
					|| (e2 instanceof BooleanExpression && ((BooleanExpression) e2).getValue())) {
				return new BooleanExpression(true);
			} else {
				return new BooleanExpression(false);
			}
		}
	},
	AND("AND") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			if ((e1 instanceof BooleanExpression && ((BooleanExpression) e1).getValue())
					&& (e2 instanceof BooleanExpression && ((BooleanExpression) e2).getValue())) {
				return new BooleanExpression(true);
			} else {
				return new BooleanExpression(false);
			}
		}
	},
	EQUAL("=") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	GREATER(">") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	SMALLER("<") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	PLUS("+") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	MINUS("-") {
		@Override
		public Expression getResult(Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}
	};

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

	public abstract Expression getResult(Expression e1, Expression e2);

}