//package app;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//import domain.model.Column;
//import domain.model.ComputedTable;
//import domain.model.DomainCell;
//import domain.model.Email;
//import domain.model.Row;
//import domain.model.Table;
//import domain.model.ValueType;
//import domain.model.sql.Query;
//import domain.model.sql.SQLParser;
//
//public class TestMain {
//
//	public static void main(String[] args) {
//		List<Table> tableList = new ArrayList<Table>();
//		tableList.add(dummyTable1());
//		tableList.add(dummyTable2());
//		tableList.add(dummyTable3());
//
//		String sql = "SELECT qfd.dd AS h              FROM TEST1 AS table1   "
////				+ "    INNER JOIN TEST3 AS table3 ON table3.Emai = table1.Email    "
////				+ "   INNER JOIN TEST3 AS table3 ON table1.Age = table1.Age       "
//				+ "         WHERE table1.Student > TRUE";
//		
//		System.out.println(SQLParser.parseQuery(sql) + "\n");
//
//		SQLParser parser = new SQLParser(sql);
//		Query query = parser.getQueryFromString();
//		
//		ComputedTable comp = new ComputedTable("TEST1", query, tableList);
//		System.out.println(comp);
//		System.out.println("TABLE1:\n");
//		System.out.println(tableList.get(0));
//		
//		System.out.println("TABLE3:\n");
//		System.out.println(tableList.get(2));
//		
//		System.out.println("\n\n\n");
//	
//		
//	}
//
//	public static Table dummyTable1() {
//		DomainCell c01 = new DomainCell(ValueType.STRING, "Steven");
//		DomainCell c02 = new DomainCell(ValueType.STRING, "Mauro");
//		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
//		DomainCell c04 = new DomainCell(ValueType.STRING, "Laurens");
//
//		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
//		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
//		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
//		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);
//
//		DomainCell c21 = new DomainCell(ValueType.INTEGER, 10);
//		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
//		DomainCell c23 = new DomainCell(ValueType.INTEGER, 30);
//		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);
//
//		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
//		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
//		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("D@"));
//		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("L@"));
//
//		DomainCell c1[] = { c01, c11, c21, c31 };
//		DomainCell c2[] = { c02, c12, c22, c32 };
//		DomainCell c3[] = { c03, c13, c23, c33 };
//		DomainCell c4[] = { c04, c14, c24, c34 };
//
//		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
//		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
//		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
//		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));
//
//		DomainCell colCells1[] = { c01, c02, c03, c04 };
//		DomainCell colCells2[] = { c11, c12, c13, c14 };
//		DomainCell colCells3[] = { c21, c22, c23, c24 };
//		DomainCell colCells4[] = { c31, c32, c33, c34 };
//
//		Column col1 = new Column("Name", ValueType.STRING);
//		Column col2 = new Column("Student", ValueType.BOOLEAN);
//		Column col3 = new Column("Age", ValueType.INTEGER);
//		Column col4 = new Column("Email", ValueType.EMAIL);
//
//		col1.addCells(Arrays.asList(colCells1));
//		col2.addCells(Arrays.asList(colCells2));
//		col3.addCells(Arrays.asList(colCells3));
//		col4.addCells(Arrays.asList(colCells4));
//
//		Table persons = new Table("TEST1");
//
//		persons.addColumn(col1);
//		persons.addColumn(col2);
//		persons.addColumn(col3);
//		persons.addColumn(col4);
//
//		persons.addRow(r1);
//		persons.addRow(r2);
//		persons.addRow(r3);
//		persons.addRow(r4);
//		return persons;
//	}
//
//	public static Table dummyTable2() {
//		DomainCell c01 = new DomainCell(ValueType.STRING, "Steven");
//		DomainCell c02 = new DomainCell(ValueType.STRING, "Mauro");
//		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
//		DomainCell c04 = new DomainCell(ValueType.STRING, "Laurens");
//
//		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
//		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
//		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
//		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);
//
//		DomainCell c21 = new DomainCell(ValueType.INTEGER, 10);
//		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
//		DomainCell c23 = new DomainCell(ValueType.INTEGER, 30);
//		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);
//
//		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
//		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("S@"));
//		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("D@"));
//		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("L@"));
//
//		DomainCell c1[] = { c01, c11, c21, c31 };
//		DomainCell c2[] = { c02, c12, c22, c32 };
//		DomainCell c3[] = { c03, c13, c23, c33 };
//		DomainCell c4[] = { c04, c14, c24, c34 };
//
//		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
//		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
//		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
//		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));
//
//		DomainCell colCells1[] = { c01, c02, c03, c04 };
//		DomainCell colCells2[] = { c11, c12, c13, c14 };
//		DomainCell colCells3[] = { c21, c22, c23, c24 };
//		DomainCell colCells4[] = { c31, c32, c33, c34 };
//
//		Column col1 = new Column("Na", ValueType.STRING);
//		Column col2 = new Column("Stude", ValueType.BOOLEAN);
//		Column col3 = new Column("A", ValueType.INTEGER);
//		Column col4 = new Column("Ema", ValueType.EMAIL);
//
//		col1.addCells(Arrays.asList(colCells1));
//		col2.addCells(Arrays.asList(colCells2));
//		col3.addCells(Arrays.asList(colCells3));
//		col4.addCells(Arrays.asList(colCells4));
//
//		Table persons = new Table("TEST2");
//
//		persons.addColumn(col1);
//		persons.addColumn(col2);
//		persons.addColumn(col3);
//		persons.addColumn(col4);
//
//		persons.addRow(r1);
//		persons.addRow(r2);
//		persons.addRow(r3);
//		persons.addRow(r4);
//		return persons;
//	}
//	
//	
//	public static Table dummyTable3() {
//		DomainCell c01 = new DomainCell(ValueType.STRING, "Dirk");
//		DomainCell c02 = new DomainCell(ValueType.STRING, "Rita");
//		DomainCell c03 = new DomainCell(ValueType.STRING, "Jos");
//		DomainCell c04 = new DomainCell(ValueType.STRING, "Filip");
//
//		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, false);
//		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
//		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
//		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);
//
//		DomainCell c21 = new DomainCell(ValueType.INTEGER, 20);
//		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
//		DomainCell c23 = new DomainCell(ValueType.INTEGER, 20);
//		DomainCell c24 = new DomainCell(ValueType.INTEGER, null);
//
//		DomainCell c31 = new DomainCell(ValueType.EMAIL, null);
//		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
//		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
//		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
//
//		DomainCell c1[] = { c01, c11, c21, c31 };
//		DomainCell c2[] = { c02, c12, c22, c32 };
//		DomainCell c3[] = { c03, c13, c23, c33 };
//		DomainCell c4[] = { c04, c14, c24, c34 };
//
//		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(c1)));
//		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(c2)));
//		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(c3)));
//		Row r4 = new Row(new ArrayList<DomainCell>(Arrays.asList(c4)));
//
//		DomainCell colCells1[] = { c01, c02, c03, c04 };
//		DomainCell colCells2[] = { c11, c12, c13, c14 };
//		DomainCell colCells3[] = { c21, c22, c23, c24 };
//		DomainCell colCells4[] = { c31, c32, c33, c34 };
//
//		Column col1 = new Column("Nam", ValueType.STRING);
//		Column col2 = new Column("Studen", ValueType.BOOLEAN);
//		Column col3 = new Column("Ag", ValueType.INTEGER);
//		Column col4 = new Column("Emai", ValueType.EMAIL);
//
//		col1.addCells(Arrays.asList(colCells1));
//		col2.addCells(Arrays.asList(colCells2));
//		col3.addCells(Arrays.asList(colCells3));
//		col4.addCells(Arrays.asList(colCells4));
//
//		Table persons = new Table("TEST3");
//
//		persons.addColumn(col1);
//		persons.addColumn(col2);
//		persons.addColumn(col3);
//		persons.addColumn(col4);
//
//		persons.addRow(r1);
//		persons.addRow(r2);
//		persons.addRow(r3);
//		persons.addRow(r4);
//		return persons;
//	}
//	
//}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import domain.model.Column;
import domain.model.ComputedTable;
import domain.model.DomainCell;
import domain.model.Email;
import domain.model.Row;
import domain.model.Table;
import domain.model.ValueType;
import domain.model.sql.Query;
import domain.model.sql.SQLParser;

public class TestMain {

	public static void main(String[] args) {
		List<Table> tableList = new ArrayList<Table>();
		tableList.add(dummyTable1());
		tableList.add(dummyTable2());
		tableList.add(dummyTable3());

		String sql = "SELECT qfd.dd AS h              FROM TEST1 AS table1   "
//				+ "    INNER JOIN TEST3 AS table3 ON table3.Emai = table1.Email    "
//				+ "   INNER JOIN TEST3 AS table3 ON table1.Age = table1.Age       "
				+ "         WHERE table1.Student > TRUE";
		
		System.out.println(SQLParser.parseQuery(sql) + "\n");

		SQLParser parser = new SQLParser(sql);
		Query query = parser.getQueryFromString();
		
		ComputedTable comp = new ComputedTable("TEST1", query, tableList);
		System.out.println(comp);
		System.out.println("TABLE1:\n");
		System.out.println(tableList.get(0));
		
		System.out.println("TABLE3:\n");
		System.out.println(tableList.get(2));
		
		System.out.println("\n\n\n");
	
		
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

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 10);
		DomainCell c22 = new DomainCell(ValueType.INTEGER, 20);
		DomainCell c23 = new DomainCell(ValueType.INTEGER, 30);
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
		Column col3 = new Column("Age", ValueType.INTEGER);
		Column col4 = new Column("Email", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("TEST1");

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
		DomainCell c01 = new DomainCell(ValueType.STRING, "Steven");
		DomainCell c02 = new DomainCell(ValueType.STRING, "Mauro");
		DomainCell c03 = new DomainCell(ValueType.STRING, "Dries");
		DomainCell c04 = new DomainCell(ValueType.STRING, "Laurens");

		DomainCell c11 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c12 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c13 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c14 = new DomainCell(ValueType.BOOLEAN, false);

		DomainCell c21 = new DomainCell(ValueType.INTEGER, 10);
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

		Column col1 = new Column("Na", ValueType.STRING);
		Column col2 = new Column("Stude", ValueType.BOOLEAN);
		Column col3 = new Column("A", ValueType.INTEGER);
		Column col4 = new Column("Ema", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("TEST2");

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
		DomainCell c32 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
		DomainCell c33 = new DomainCell(ValueType.EMAIL, new Email("Info@"));
		DomainCell c34 = new DomainCell(ValueType.EMAIL, new Email("Info@"));

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

		Column col1 = new Column("Nam", ValueType.STRING);
		Column col2 = new Column("Studen", ValueType.BOOLEAN);
		Column col3 = new Column("Ag", ValueType.INTEGER);
		Column col4 = new Column("Emai", ValueType.EMAIL);

		col1.addCells(Arrays.asList(colCells1));
		col2.addCells(Arrays.asList(colCells2));
		col3.addCells(Arrays.asList(colCells3));
		col4.addCells(Arrays.asList(colCells4));

		Table persons = new Table("TEST3");

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
	
}
