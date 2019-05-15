package domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain.model.sql.Query;
import domain.model.sql.SQLParser;

class ComputedTableTest {

	private static List<Table> tableList;
	private static List<String> studentNames;
	private ComputedTable comp;
	private Query query;
	
	@BeforeAll
	public static void initialTables() {
		tableList = new ArrayList<Table>();
		tableList.add(dummyTable1());
		tableList.add(dummyTable2());
		tableList.add(dummyTable3());
	}
	
	//-------------------------
	//------FROM STATEMENT----- 
	//-------------------------
	
	@Test
	public void basicFromAndWhereTrueShouldDisplayThetable() {
		String sql = "SELECT stud.Name AS Name,  stud.Student AS s, stud.Grade AS g, stud.Email  AS e     FROM Students AS stud        WHERE  TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(4, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerJoinAndWhereTrueShouldInnerJoinOnNumber() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());;
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(3, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerJoinAndWhereTrueShouldInnerJoinOnString() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Name = w.Firstname          WHERE  TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Dries");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());;
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(1, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	 
	@Test
	public void innerJoinAndWhereTrueShouldInnerJoinOnEmail() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Email = w.Email          WHERE  TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(3, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerJoinAndWhereTrueShouldInnerJoinOnBoolean() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart          WHERE  TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(6, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void multipleInnerJoinAndWhereTrue() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em, g.Nickname AS ni, g.Paid AS pa, g.Money AS m, g.Email AS gma     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age INNER JOIN Group AS g ON g.Paid = w.Smart          WHERE  TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		studentNames.add("Laurens");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(12, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereOnCellidString() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  stud.Name = \"Mauro\"";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(2, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereOnFalse() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  FALSE";
		studentNames = new ArrayList<>();
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereOnlyLiteralString() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  \"hello\"";
		studentNames = new ArrayList<>();
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereOnlyLiteralNumberDifferentFromZero() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  5";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());;
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(3, comp.getRows().size());

		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereOnlyLiteralNumberEqualToZero() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  0";
		studentNames = new ArrayList<>();

		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereOnlyCellid() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  stud.Name";
		studentNames = new ArrayList<>();
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereStringEqual() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  stud.Name = \"Mauro\"";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(2, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereStringSmaller() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  stud.Name < \"Mauro\"";
		studentNames = new ArrayList<>();
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(1, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereStringGreater() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Grade = w.Age          WHERE  stud.Name > \"Mauro\"";
		studentNames = new ArrayList<>();
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	
	@Test
	public void innerjoinWithWhereIntegerGreater() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE stud.Grade > 5";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		studentNames.add("Dries");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(3, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereIntegerEqual() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE stud.Grade =15";
		studentNames = new ArrayList<>();
		studentNames.add("Dries");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(1, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	@Test
	public void innerjoinWithWhereIntegerSmaller() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE stud.Grade <16";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Dries");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(3, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereBooleanAndCellIdEqual() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE stud.Student = TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());;
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	
	
	@Test
	public void innerjoinWithWhereBooleanAndCellIdGreater() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE stud.Student > FALSE";
			studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	
	@Test
	public void innerjoinWithWhereBooleanAndBooleanEqual() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE TRUE = TRUE";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(6, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereBooleanAndBooleanSmaller() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE TRUE < TRUE";
		studentNames = new ArrayList<>();

		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void innerjoinWithWhereBooleanAndBooleanGreater() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em     FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart         WHERE TRUE > TRUE";
		studentNames = new ArrayList<>();
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(0, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void whereplusCellIdAndInteger() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
				+ " WHERE stud.Grade + 15 = w.Age";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void whereplusCellIdAndInteger2() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
				+ " WHERE  15 + stud.Grade  = w.Age";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void whereplustwoCellIds() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
			    +"    WHERE stud.Grade + w.Age  = 25";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(2, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	
	@Test
	public void whereMinuscellIdandInteger() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
			    +"    WHERE w.Age - 5  = 15";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void whereMinusIntegerandCellId() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
			    +"    WHERE 55- w.Age  = 25";
		studentNames = new ArrayList<>();
		studentNames.add("Dries");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(1, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void whereSubtractTwoCellIds() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
			    +"    WHERE  w.Age - stud.Grade  = 0";
		studentNames = new ArrayList<>();
		studentNames.add("Mauro");
		studentNames.add("Mauro");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(2, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void relationalAndOperatorExpression() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
				+ "         WHERE stud.Name = \"Steven\" AND w.Smart = TRUE AND w.Email =\"S@\"";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(1, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void relationalBracketsExpression() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
				+ "         WHERE (stud.Name = \"Steven\" OR stud.Student = FALSE) AND w.Smart = FALSE";
		studentNames = new ArrayList<>();
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());;
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(1, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}
	
	@Test
	public void relationalAndOROperatorExpression() {
		String sql = "SELECT stud.Name AS n, stud.Student AS s, stud.Grade AS g, stud.Email  AS e, w.Firstname AS f, w.Smart AS sm, w.Age AS ag, w.Email AS em  "
				+ "   FROM Students AS stud INNER JOIN Work AS w ON stud.Student = w.Smart "
				+ "         WHERE stud.Name = \"Steven\" OR stud.Student = FALSE AND w.Smart = FALSE";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Steven");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());
		assertEquals(8, comp.getNbrOfColumns());
		assertEquals(3, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), studentNames);
	}


	//-------------------------
	//----SELECT STATEMENT----- 
	//-------------------------
	
	@Test
	public void testSelectStatementWithCellIdSelects() {
		String sql = "SELECT  stud.Email AS em, stud.Name AS n   FROM Students AS stud           WHERE  TRUE ";
		studentNames = new ArrayList<>();
		studentNames.add("Steven");
		studentNames.add("Mauro");
		studentNames.add("Dries");
		studentNames.add("Laurens");
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getTables());;
		assertEquals(2, comp.getNbrOfColumns());
		assertEquals(4, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(1).getCells(), studentNames);
	}
	
	
	@Test
	public void testMultiplestatementWithCellIdSelects() {
		String sql = "SELECT w.Firstname AS first, g.Nickname AS nickn            FROM Work AS w  INNER JOIN Group AS g ON w.Age = g.Money          WHERE  TRUE ";
		ArrayList<String> firstNames = new ArrayList<>();
		firstNames.add("John");
		firstNames.add("John");
		firstNames.add("John");
		firstNames.add("Quinten");
		firstNames.add("Quinten");
		firstNames.add("Quinten");
		firstNames.add("Frederik");
		ArrayList<String> nickNames = new ArrayList<>();
		nickNames.add("Dirk");
		nickNames.add("Rita");
		nickNames.add("Jos");
		nickNames.add("Dirk");
		nickNames.add("Rita");
		nickNames.add("Jos");
		nickNames.add("Filip");
				
		SQLParser parser = new SQLParser(sql);
		query = parser.getQueryFromString();
		comp = new ComputedTable(UUID.randomUUID(), "compT", query , this.getWorkAndGroupTables());;
		assertEquals(2, comp.getNbrOfColumns());
		assertEquals(7, comp.getRows().size());
		this.checkAllNames(comp.getColumns().get(0).getCells(), firstNames);
		this.checkAllNames(comp.getColumns().get(1).getCells(), nickNames);
	}
	
	

	
	
	
	
	
	
	public static Table dummyTable1() {
		DomainCell c01 = new DomainCell(ValueType.STRING, "Steven");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Mauro");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Laurens");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 5);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 15);
		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);

		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("D@"));
		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("L@"));

		DomainCell c1[] = { c01, c11, c21, c31 };
		DomainCell c2[] = { c02, c12, c22, c32 };
		DomainCell c3[] = { c03, c13, c23, c33 };
		DomainCell c4[] = { c04, c14, c24, c34 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));

		DomainCell colCells1[] = { c01, c02, c03, c04 };
		DomainCell colCells2[] = { c11, c12, c13, c14 };
		DomainCell colCells3[] = { c21, c22, c23, c24 };
		DomainCell colCells4[] = { c31, c32, c33, c34 };

		Column col1 = new Column("Name", ValueType.STRING);
		Column col2 = new Column("Student", ValueType.BOOLEAN);
		Column col3 = new Column("Grade", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Students");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);
		return persons;
	}

	public static Table dummyTable2() {
		DomainCell c01 = new DomainCell(ValueType.STRING, "John");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Quinten");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Frederik");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 30);
		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);

		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("S@"));
		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("D@"));
		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("L@"));

		DomainCell c1[] = { c01, c11, c21, c31 };
		DomainCell c2[] = { c02, c12, c22, c32 };
		DomainCell c3[] = { c03, c13, c23, c33 };
		DomainCell c4[] = { c04, c14, c24, c34 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));

		DomainCell colCells1[] = { c01, c02, c03, c04 };
		DomainCell colCells2[] = { c11, c12, c13, c14 };
		DomainCell colCells3[] = { c21, c22, c23, c24 };
		DomainCell colCells4[] = { c31, c32, c33, c34 };

		Column col1 = new Column("Firstname", ValueType.STRING);
		Column col2 = new Column("Smart", ValueType.BOOLEAN);
		Column col3 = new Column("Age", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Work");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);
		return persons;
	}
	
	
	public static Table dummyTable3() {
		DomainCell c01 = new DomainCell(ValueType.STRING, "Dirk");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Rita");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Jos");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Filip");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, false);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);

		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@R.be"));
		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("Info@J.be"));
		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("Info@F.be"));

		DomainCell c1[] = { c01, c11, c21, c31 };
		DomainCell c2[] = { c02, c12, c22, c32 };
		DomainCell c3[] = { c03, c13, c23, c33 };
		DomainCell c4[] = { c04, c14, c24, c34 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));

		DomainCell colCells1[] = { c01, c02, c03, c04 };
		DomainCell colCells2[] = { c11, c12, c13, c14 };
		DomainCell colCells3[] = { c21, c22, c23, c24 };
		DomainCell colCells4[] = { c31, c32, c33, c34 };

		Column col1 = new Column("Nickname", ValueType.STRING);
		Column col2 = new Column("Paid", ValueType.BOOLEAN);
		Column col3 = new Column("Money", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("Group");

		persons.addColumn(col1);
		persons.addColumn(col2);
		persons.addColumn(col3);
		persons.addColumn(col4);

		persons.addRow(r1);
		persons.addRow(r2);
		persons.addRow(r3);
		persons.addRow(r4);
		return persons;
	}
	
	public void checkAllNames(List<DomainCell> cells,List<String> names) {
		assertTrue((cells.size() == names.size()));
		for (int i = 0; i < cells.size(); i++) {
			if(cells.get(i) == null) {
				assertNull(names.get(i));
			} else {
				assertTrue( (cells.get(i).getValue() instanceof String) &&  ((String) cells.get(i).getValue()).equals(names.get(i)));
			}
		}
	}
	
	public List<Table> getWorkAndGroupTables(){
		List<Table> tables = new ArrayList<>();
		tables.add(dummyTable2());
		tables.add(dummyTable3());
		return tables;
	}
	
	public List<Table> getTables(){
		return this.tableList;
	}

}
