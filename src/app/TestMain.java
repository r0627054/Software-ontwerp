package app;

import domain.model.SQLParser;

public class TestMain {

	public static void main(String[] args) {
		String sql = "SELECT movie.title AS title FROM movies AS movie\r\n" + 
				"WHERE movie.imdb_score > 7\r\n";
		
		System.out.println(SQLParser.parseQuery(sql));

	}

}
