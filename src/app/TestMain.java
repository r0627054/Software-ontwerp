package app;

import java.util.List;
import java.util.Map;

import domain.model.sql.SQLParser;

public class TestMain {

	public static void main(String[] args) {
		/*String sql = "SELECT movie.title AS title " + "FROM movies AS movie "
				+ "INNER JOIN appel AS peer ON movie.id = peer.id " + "INNER JOIN a AS b ON a.id = peer.id "
				+ "WHERE movie.imdb_score > 7\r\n";

		System.out.println(SQLParser.parseQuery(sql) + "\n");
		SQLParser parser = new SQLParser(sql);
		List<InnerJoinCondition> result = parser.getJoinConditions();

		for (InnerJoinCondition i: result) {
			System.out.println(i);
		}*/
	}

}
