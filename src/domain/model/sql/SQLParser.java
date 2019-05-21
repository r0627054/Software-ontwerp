package domain.model.sql;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import domain.model.sql.columnSpec.ColumnSpec;
import domain.model.sql.expression.BooleanExpression;
import domain.model.sql.expression.BracketExpression;
import domain.model.sql.expression.CellIdExpression;
import domain.model.sql.expression.ConjunctionOperatorExpression;
import domain.model.sql.expression.DisjunctionOperatorExpression;
import domain.model.sql.expression.Expression;
import domain.model.sql.expression.LiteralNumberExpression;
import domain.model.sql.expression.LiteralStringExpression;
import domain.model.sql.expression.MathOperatorExpression;
import domain.model.sql.expression.OperatorExpression;
import domain.model.sql.expression.RelationalOperatorExpression;
import domain.model.sql.statements.FromStatement;
import domain.model.sql.statements.SelectStatement;
import domain.model.sql.statements.WhereStatement;
import domain.model.sql.tablespecs.InnerJoinTableSpec;
import domain.model.sql.tablespecs.SingleTableSpec;
import domain.model.sql.tablespecs.TableSpec;

/**
 * The SQLParser is responsible for parsing the query to a formatted String or into a Query Object instance.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class SQLParser extends StreamTokenizer {

	/**
	 * The variable storing the String mapping of the keywords ttype.
	 */
	private static HashMap<String, Integer> keywords = new HashMap<>();
	
	/**
	 * Variables storing the ttype integer value.
	 */
	public static final int TT_IDENT = -9, TT_SELECT = -10, TT_OR = -11, TT_AND = -12, TT_TRUE = -13, TT_FALSE = -14,
			TT_AS = -15, TT_FROM = -16, TT_INNER = -17, TT_JOIN = -18, TT_ON = -19, TT_WHERE = -20;

	/**
	 * Populates the keywords map with the correct words and ttype values.
	 */
	static {
		keywords.put("SELECT", TT_SELECT);
		keywords.put("OR", TT_OR);
		keywords.put("AND", TT_AND);
		keywords.put("TRUE", TT_TRUE);
		keywords.put("FALSE", TT_FALSE);
		keywords.put("AS", TT_AS);
		keywords.put("FROM", TT_FROM);
		keywords.put("INNER", TT_INNER);
		keywords.put("JOIN", TT_JOIN);
		keywords.put("ON", TT_ON);
		keywords.put("WHERE", TT_WHERE);
	}

	/**
	 * Creates a new instance of the SqlParser.
	 * @param text The sql query as a String.
	 * @effect all the variables are set.
	 *       | super(new StringReader(text));
	 *       |	ordinaryChar('.');
	 *       | 	wordChars('_', '_');
	 *       |	nextToken();
	 */
	public SQLParser(String text) {
		super(new StringReader(text));
		ordinaryChar('.');
		wordChars('_', '_');
		nextToken();
	}

	/**
	 * Parses the query with the given string.
	 * @param text The string which will be parsed.
	 * @return The parsed and formatted String of the query.
	 */
	public static String parseQuery(String text) {
		return new SQLParser(text).parseQuery();
	}

	/**
	 * Returns the ttype of the next token.
	 * @return the ttype of the next token.
	 * @throws RuntimeException when the ttpye isn't in the keywords.
	 */
	@Override
	public int nextToken() {
		try {
			super.nextToken();
			if (ttype == TT_WORD) {
				Integer kwd = keywords.get(sval);
				if (kwd != null)
					ttype = kwd;
				else
					ttype = TT_IDENT;
			}
			return ttype;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns an instance of the parseException.
	 * @return an instance of the parseException.
	 */
	public RuntimeException error() {
		return new ParseException();
	}

	/**
	 * Checks if the input has a valid ttype.
	 * @param ttype The correct input integere representation.
	 * @throws RuntimeException when the expected type is different from the actual ttype.
	 */
	public void expect(int ttype) {
		if (this.ttype != ttype)
			throw new RuntimeException("Expected " + ttype + ", found " + this.ttype);
		nextToken();
	}


	/**
	 * Parses the indent to a general formated String.
	 * @return the general formated String of the indent.
	 */
	public String expectIdent() {
		if (ttype != TT_IDENT)
			throw error();
		String result = sval;
		nextToken();
		return result;
	}

	/**
	 * Parses a cellId to a general formated String.
	 * @return the general formated String of the cellId.
	 */
	public String parseCellId() {
		String rowId = expectIdent();
		expect('.');
		String colName = expectIdent();
		return rowId + "." + colName;
	}

	/**
	 * Creates a cellId object out of the input data.
	 * @return a cellId object out of the input data.
	 */
	public Expression parseCellIdSqlExpression() {
		String rowId = expectIdent();
		expect('.');
		String colName = expectIdent();
		return new CellIdExpression(new CellId(rowId, colName));
	}

	/**
	 * Creates a the final Expression object out of the input data.
	 * @return a the final Expression object out of the input data.
	 */
	public Expression parsePrimarySqlExpression() {
		switch (ttype) {
		case TT_TRUE:
			nextToken();
			return new BooleanExpression(true);
		case TT_FALSE:
			nextToken();
			return new BooleanExpression(false);
		case TT_NUMBER: {
			int value = (int) nval;
			nextToken();
			return new LiteralNumberExpression(value);
		}
		case '"': {
			String value = sval;
			nextToken();
			return new LiteralStringExpression(value);
		}
		case TT_IDENT:
			return parseCellIdSqlExpression();
		case '(': {
			nextToken();
			Expression result = new BracketExpression(parseSqlExpression());
			expect(')');
			return result;
		}
		default:
			throw error();
		}
	}

	/**
	 * Parses a primary expression to a general formated String.
	 * @return the general formated String of the primary expression.
	 */
	public String parsePrimaryExpr() {
		switch (ttype) {
		case TT_TRUE:
			nextToken();
			return "TRUE";
		case TT_FALSE:
			nextToken();
			return "FALSE";
		case TT_NUMBER: {
			int value = (int) nval;
			nextToken();
			return String.valueOf(value);
		}
		case '"': {
			String value = sval;
			nextToken();
			return '"' + value + '"';
		}
		case TT_IDENT:
			return parseCellId();
		case '(': {
			nextToken();
			String result = parseExpr();
			expect(')');
			return "(" + result + ")";
		}
		default:
			throw error();
		}
	}

	/**
	 * Parses a sumExpression to a general formated String.
	 * @return the general formated String of the sumExpression.
	 */
	public String parseSum() {
		String e = parsePrimaryExpr();
		for (;;) {
			switch (ttype) {
			case '+':
				nextToken();
				e = e + " + " + parsePrimaryExpr();
				break;
			case '-':
				nextToken();
				e = e + " - " + parsePrimaryExpr();
				break;
			default:
				return e;
			}
		}
	}

	
	/**
	 * Creates a (MathOperatorExpression) Expression object out of the input data.
	 * @return a (MathOperatorExpression) Expression object out of the input data.
	 */
	public Expression parseSqlExpressionSum() {
		Expression e = this.parsePrimarySqlExpression();
		for (;;) {
			switch (ttype) {
			case '+':
				nextToken();
				e = new MathOperatorExpression(e, parsePrimarySqlExpression(), Operator.PLUS);
				break;
			case '-':
				nextToken();
				e = new MathOperatorExpression(e, parsePrimarySqlExpression(), Operator.MINUS);
				break;
			default:
				return e;
			}
		}
	}

	/**
	 * Parses an relationalExpression to a general formated String.
	 * @return the general formated String of the relationalExpression.
	 */
	public String parseRelationalExpr() {
		String e = parseSum();
		switch (ttype) {
		case '=':
		case '<':
		case '>':
			char operator = (char) ttype;
			nextToken();
			return e + " " + operator + " " + parseSum();
		default:
			return e;
		}
	}
	
	/**
	 * Creates a (RelationalOperatorExpression) Expression object out of the input data.
	 * @return a (RelationalOperatorExpression) Expression object out of the input data.
	 */
	public Expression parseSqlRelationalExpression() {
		Expression e = parseSqlExpressionSum();
		switch (ttype) {
		case '=':
			nextToken();
			return new RelationalOperatorExpression(e, parseSqlExpressionSum(), Operator.EQUAL);
		case '<':
			nextToken();
			return new RelationalOperatorExpression(e, parseSqlExpressionSum(), Operator.SMALLER);
		case '>':
			nextToken();
			return new RelationalOperatorExpression(e, parseSqlExpressionSum(), Operator.GREATER);
		default:
			return e;
		}
	}

	/**
	 * Parses an conjunction to a general formated String.
	 * @return the general formated String of the conjunction.
	 */
	public String parseConjunction() {
		String e = parseRelationalExpr();
		switch (ttype) {
		case TT_AND:
			nextToken();
			return e + " AND " + parseConjunction();
		default:
			return e;
		}
	}

	/**
	 * Creates a (conjunction) Expression object out of the input data.
	 * @return a (conjunction) Expression object out of the input data.
	 */
	public Expression parseSqlConjunctionExpression() {
		Expression e = parseSqlRelationalExpression();
		switch (ttype) {
		case TT_AND:
			nextToken();
			return new ConjunctionOperatorExpression(e, parseSqlConjunctionExpression(), Operator.AND);
		default:
			return e;
		}
	}

	/**
	 * Parses an disjunction to a general formated String.
	 * @return the general formated String of the disjunction.
	 */
	public String parseDisjunction() {
		String e = parseConjunction();
		switch (ttype) {
		case TT_OR:
			nextToken();
			return e + " OR " + parseDisjunction();
		default:
			return e;
		}
	}
	
	/**
	 * Creates a (disjunction) Expression object out of the input data.
	 * @return a (disjunction) Expression object out of the input data.
	 */
	public Expression parseSqlDisjunctionExpression() {
		Expression e = parseSqlConjunctionExpression();
		switch (ttype) {
		case TT_OR:
			nextToken();
			return new DisjunctionOperatorExpression(e, parseSqlDisjunctionExpression(), Operator.OR);
		default:
			return e;
		}
	}

	/**
	 * Parses an expression to a general formated String.
	 * @return the general formated String of the expression.
	 */
	public String parseExpr() {
		return parseDisjunction();
	}
	
	/**
	 * Creates an Expression object out of the input data.
	 * @return an Expression object out of the input data.
	 */
	public Expression parseSqlExpression() {
		return parseSqlDisjunctionExpression();
	}

	/**
	 * Parses a query to a general formated String.
	 * @return the general formated String of the query.
	 */
	public String parseQuery() {
		StringBuilder result = new StringBuilder();

		expect(TT_SELECT);
		result.append("SELECT ");
		for (;;) {
			String e = parseExpr();
			expect(TT_AS);
			String colName = expectIdent();
			result.append(e + " AS " + colName);
			if (ttype == ',') {
				nextToken();
				result.append(", ");
			} else
				break;
		}
		expect(TT_FROM);
		result.append(" FROM ");
		{
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			result.append(tableName + " AS " + rowId);
		}
		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			expect(TT_ON);
			String cell1 = parseCellId();
			expect('=');
			String cell2 = parseCellId();
			result.append(" INNER JOIN " + tableName + " AS " + rowId + " ON " + cell1 + " = " + cell2);
		}
		expect(TT_WHERE);
		String cond = parseExpr();
		result.append(" WHERE " + cond);
		return result.toString();
	}

	/**
	 * Returns the query created out of the String input
	 * @return the query created out of the String input
	 */
	public Query getQueryFromString() {
		SelectStatement selectStatement = this.createSelectStatement();
		FromStatement fromStatement = this.createFromStatement();
		WhereStatement whereStatement = this.createWhereStatement();
		return new Query(selectStatement, fromStatement, whereStatement);
	}

	/**
	 * Parses the input to a select statement object.
	 * @return The select statement created out of the String input.
	 */
	private SelectStatement createSelectStatement() {
		SelectStatement selectStatement = new SelectStatement();
		expect(TT_SELECT);
		for (;;) {
			Expression e = parseSqlExpression();
			expect(TT_AS);
			String colName = expectIdent();
			selectStatement.addColumnSpec(new ColumnSpec(e, colName));
			
			if (ttype == ',') {
				nextToken();
			} else
				break;
		}

		return selectStatement;
	}

	/**
	 * Parses the input to a from statement object.
	 * @return The from statement created out of the String input.
	 */
	private FromStatement createFromStatement() {
		FromStatement fromStatement = new FromStatement();
		TableSpec single = null;
		expect(TT_FROM);
		{
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			single = new SingleTableSpec(tableName, rowId);
			fromStatement.addTableSpec(single);
		}
		TableSpec temp = single;
		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			String tableName = expectIdent();
			expect(TT_AS);
			String rowId = expectIdent();
			expect(TT_ON);
			String cell1 = parseCellId();
			expect('=');
			String cell2 = parseCellId();

			CellId cellId1 = new CellId(cell1);
			CellId cellId2 = new CellId(cell2);
			TableSpec newSpec = new InnerJoinTableSpec(tableName, rowId, temp, cellId1, cellId2);
			fromStatement.addTableSpec(newSpec);
			temp = newSpec;
		}
		return fromStatement;
	}

	/**
	 * Parses the input to a where statement object.
	 * @return The where statement created out of the String input.
	 */
	private WhereStatement createWhereStatement() {
		expect(TT_WHERE);
		return new WhereStatement(this.parseSqlExpression());
	}

	/**
	 * 
	 * A custom RuntimeException class made for Exceptions in the SQLParser class.
	 * 
	 * @version 3.0
	 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
	 *
	 */
	public static class ParseException extends RuntimeException {
	}

}

