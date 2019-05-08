package domain.model.sql;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLParser extends StreamTokenizer {

	private static HashMap<String, Integer> keywords = new HashMap<>();
	public static final int TT_IDENT = -9, TT_SELECT = -10, TT_OR = -11, TT_AND = -12, TT_TRUE = -13, TT_FALSE = -14,
			TT_AS = -15, TT_FROM = -16, TT_INNER = -17, TT_JOIN = -18, TT_ON = -19, TT_WHERE = -20;

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

	public SQLParser(String text) {
		super(new StringReader(text));
		ordinaryChar('.');
		wordChars('_', '_');
		nextToken();
	}

	public static String parseQuery(String text) {
		return new SQLParser(text).parseQuery();
	}

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

	public RuntimeException error() {
		return new ParseException();
	}

	public void expect(int ttype) {
		if (this.ttype != ttype)
			throw new RuntimeException("Expected " + ttype + ", found " + this.ttype);
		nextToken();
	}

	public String expectIdent() {
		if (ttype != TT_IDENT)
			throw error();
		String result = sval;
		nextToken();
		return result;
	}

	public String parseCellId() {
		String rowId = expectIdent();
		expect('.');
		String colName = expectIdent();
		return rowId + "." + colName;
	}

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

	public String parseExpr() {
		return parseDisjunction();
	}

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

	
	public Query getQueryFromString(String stringQuery) {
		SelectStatement selectStatement = this.createSelectStatement();
		FromStatement fromStatement     = this.createFromStatement();
		WhereStatement whereStatement   = this.createWhereStatement();
		return new Query(selectStatement, fromStatement, whereStatement);
	}
	



	private WhereStatement createWhereStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	private FromStatement createFromStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	private SelectStatement createSelectStatement() {
		// TODO Auto-generated method stub
		return null;
	}




	public static class ParseException extends RuntimeException {
	}

/*	
 * 
 * 
 * 
 * 
 *
 
 private void readSelect() {
		expect(TT_SELECT);
		while (true) {
			parseExpr();
			expect(TT_AS);
			expectIdent();
			if (ttype == ',') {
				nextToken();
			} else
				break;
		}
	}
 
 	public Map<String, String> getTableNames() {
		Map<String, String> result = new LinkedHashMap<>();

		readSelect();

		expect(TT_FROM);

		String tableName = expectIdent();
		expect(TT_AS);
		String rowId = expectIdent();
		result.put(tableName, rowId);

		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			tableName = expectIdent();
			expect(TT_AS);
			rowId = expectIdent();
			result.put(tableName, rowId);
			expect(TT_ON);
			parseCellId();
			expect('=');
			parseCellId();
		}
		return result;
	}
 
 
 public List<InnerJoinCondition> getJoinConditions() {
		List<InnerJoinCondition> result = new ArrayList<>();
		readSelect();

		expect(TT_FROM);

		expectIdent();
		expect(TT_AS);
		expectIdent();

		while (ttype == TT_INNER) {
			nextToken();
			expect(TT_JOIN);
			expectIdent();
			expect(TT_AS);
			expectIdent();
			expect(TT_ON);
			String colTable1 = parseCellId();
			expect('=');
			String colTable2 = parseCellId();
			
			String table1 = colTable1.split("\\.")[0];
			String table2 = colTable2.split("\\.")[0];
			String col1 = colTable1.split("\\.")[1];
			String col2 = colTable2.split("\\.")[1];
			result.add(new InnerJoinCondition(table1, table2, col1, col2));
		}

		return result;
	}
*/
}

/*
 * EXAMPLES
 * 
 * String sql = "SELECT movie.title AS title " + "FROM movies AS movie " +
 * "INNER JOIN appel AS peer ON movie.id = peer.id " +
 * "INNER JOIN a AS b ON a.id = peer.id " + "WHERE movie.imdb_score > 7\r\n";
 */
