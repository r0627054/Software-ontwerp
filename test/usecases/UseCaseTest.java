package usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import controller.Controller;
import domain.model.DomainCell;
import domain.model.Column;
import domain.model.DomainFacade;
import domain.model.Email;
import domain.model.Row;
import domain.model.Table;
import domain.model.ValueType;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.RowsTable;
import ui.model.components.TableList;
import ui.model.view.UIFacade;
import ui.model.window.sub.SubWindow;

public abstract class UseCaseTest {
	private static UIFacade uiFacade;
	private static DomainFacade domainFacade;
	private static Controller controller;

	public static boolean setupIsDone = false;

	@BeforeAll
	public static void init() {
		setupApp();
	}

	public static void setupApp() {
		if (setupIsDone) {
			return;
		} else {
			setupIsDone = true;
			setUiFacade(new UIFacade());
			setDomainFacade(new DomainFacade());
			setController(new Controller(uiFacade, domainFacade, false));
		}
	}

	@BeforeEach
	public void resetDomainAndUiFacade() {
		setUiFacade(new UIFacade());
		setDomainFacade(new DomainFacade());
		setController(new Controller(uiFacade, domainFacade, false));

	}

	protected void addDummyTable(String tableName) {
		getDomainFacade().addTable(tableName);
		getUiFacade().createTablesSubWindow(getDomainFacade().getTableNames());
	}
	

	protected void simulateKeyPress(int keyCode) {
		getUiFacade().simulateKeyPress(keyCode);
	}

	protected void simulateKeyPresses(int keyCode, int amount) {
		for (int i = 0; i < amount; i++) {
			getUiFacade().simulateKeyPress(keyCode);
		}
	}

	protected void simulateKeyPress(char keyChar) {
		getUiFacade().simulateKeyPress(keyChar);
	}

	protected void simulateKeyPress(String string	) {
		for (int i = 0; i < string.length(); i++) {
			getUiFacade().simulateKeyPress(string.charAt(i));
		}
	}

	protected void simulateSingleClick(int x, int y) {
		getUiFacade().simulateClick(x, y, 1);
	}

	protected void simulateDoubleClick(int x, int y) {
		getUiFacade().simulateClick(x, y, 2);
	}
	
	protected void simulateClickRelease(int x, int y) {
		getUiFacade().simulateClickRelease(x, y);
	}
	
	protected void simulateClickDrag(int x, int y) {
		getUiFacade().simulateClickDrag(x, y);
	}

	protected UIFacade getUiFacade() {
		return uiFacade;
	}

	private static void setUiFacade(UIFacade uifacade) {
		uiFacade = uifacade;
	}

	protected DomainFacade getDomainFacade() {
		return domainFacade;
	}

	private static void setDomainFacade(DomainFacade domainfacade) {
		domainFacade = domainfacade;
	}

	private static void setController(Controller controller) {
		UseCaseTest.controller = controller;
	}

	protected TableList getTablesViewModeTableList() {
		Container container = getUiFacade().getView().getCurrentSubWindow().getContainer();

		for (Component containerComponents : container.getComponentsList()) {
			if (containerComponents instanceof TableList) {
				return (TableList) containerComponents;
			}
		}

		return null;
	}

	protected DesignTable getTableViewModeDesignTable(UUID tableId) {
		SubWindow designTableSubWindow = getUiFacade().getView().getSubWindows(tableId).get(0); // get any bcs all
																								// windows with same id
																								// should display the
																								// same info.
		if (designTableSubWindow != null) {
			for (Component c : designTableSubWindow.getContainer().getComponentsList()) {
				if (c instanceof DesignTable) {
					return (DesignTable) c;
				}
			}
		}
		return null;
	}

	protected RowsTable getTableViewModeRowsTable(UUID tableId) {
		SubWindow tableRowsTable = getUiFacade().getView().getSubWindows(tableId).get(0);
		if (tableRowsTable != null) {
			for (Component c : tableRowsTable.getContainer().getComponentsList()) {
				if (c instanceof RowsTable) {
					return (RowsTable) c;
				}
			}
		}
		return null;
	}

	protected void addDummyTableEmailColumnEmailCellValues() {
		DomainCell c1 = new DomainCell(ValueType.EMAIL, "A@");
		DomainCell c2 = new DomainCell(ValueType.EMAIL, "M@");
		DomainCell c3 = new DomainCell(ValueType.EMAIL, "D@");

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.EMAIL);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("DummyEmail");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableStringColumnEmailCellValues() {
		DomainCell c1 = new DomainCell(ValueType.STRING, null);
		DomainCell c2 = new DomainCell(ValueType.STRING, "M@");
		DomainCell c3 = new DomainCell(ValueType.STRING, "D@");

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.STRING);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("DummyEmail");

		email.addColumn(col1);
		email.addRowWithoutAddingToColumns(r1);
		email.addRowWithoutAddingToColumns(r2);
		email.addRowWithoutAddingToColumns(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableIntColumnStringCellValues() {
		DomainCell c1 = new DomainCell(ValueType.INTEGER, 0);
		DomainCell c2 = new DomainCell(ValueType.INTEGER, null);
		DomainCell c3 = new DomainCell(ValueType.INTEGER, 123);

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Int", ValueType.INTEGER);
		col1.addCells(Arrays.asList(colCells1));

		Table table = new Table("DummyInts");

		table.addColumn(col1);
		table.addRow(r1);
		table.addRow(r2);
		table.addRow(r3);

		getDomainFacade().addMockedTable(table);
	}

	protected void addDummyTableStringColumnStringBoolanValues() {
		DomainCell c1 = new DomainCell(ValueType.STRING, null);
		DomainCell c2 = new DomainCell(ValueType.STRING, "true");
		DomainCell c3 = new DomainCell(ValueType.STRING, "false");

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Bool", ValueType.STRING);
		col1.addCells(Arrays.asList(colCells1));

		Table table = new Table("DummyBooleans");

		table.addColumn(col1);
		table.addRow(r1);
		table.addRow(r2);
		table.addRow(r3);

		getDomainFacade().addMockedTable(table);
	}

	protected void addDummyTableStringColumnNullCellValues() {
		DomainCell c1 = new DomainCell(ValueType.STRING, null);
		DomainCell c2 = new DomainCell(ValueType.STRING, null);
		DomainCell c3 = new DomainCell(ValueType.STRING, null);

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("String", ValueType.STRING);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("Table");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableEmailColumnNullCellValues() {
		DomainCell c1 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c2 = new DomainCell(ValueType.EMAIL, null);
		DomainCell c3 = new DomainCell(ValueType.EMAIL, null);

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.EMAIL);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("Table");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableBooleanColumnNullCellValues() {
		DomainCell c1 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c2 = new DomainCell(ValueType.BOOLEAN, null);
		DomainCell c3 = new DomainCell(ValueType.BOOLEAN, null);

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Boolean", ValueType.BOOLEAN);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("Table");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}
	
	protected void addDummyTableBooleanColumnCellValues() {
		DomainCell c1 = new DomainCell(ValueType.BOOLEAN, true);
		DomainCell c2 = new DomainCell(ValueType.BOOLEAN, false);
		DomainCell c3 = new DomainCell(ValueType.BOOLEAN, true);

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3};
		Column col1 = new Column("Boolean", ValueType.BOOLEAN);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("Table");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableIntegerColumnNullCellValues() {
		DomainCell c1 = new DomainCell(ValueType.INTEGER, null);
		DomainCell c2 = new DomainCell(ValueType.INTEGER, null);
		DomainCell c3 = new DomainCell(ValueType.INTEGER, null);

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Integer", ValueType.INTEGER);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("Table");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableStringColumnEmailCellValuesNotEmptyDefaultColumnValue() {
		DomainCell c1 = new DomainCell(ValueType.STRING, "@EEE");
		DomainCell c2 = new DomainCell(ValueType.STRING, "M@AAA");
		DomainCell c3 = new DomainCell(ValueType.STRING, "D@FFF");

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.STRING);
		col1.setDefaultValue("default@email");
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("DummyEmail");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableNotEmptyDefaultColumnValueNoBlanksAllowed() {
		DomainCell c1 = new DomainCell(ValueType.STRING, "@E");
		DomainCell c2 = new DomainCell(ValueType.STRING, "M@");
		DomainCell c3 = new DomainCell(ValueType.STRING, "D@");

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.STRING);
		col1.setDefaultValue("default@email");
		col1.setAllowsBlanks(false);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("DummyEmail");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	public void addDummyEmptyTableBooleanColumnVariableAllowsBlank(boolean allowsBlank) {
		Column col1 = new Column("EmptyBoolean", ValueType.BOOLEAN);
		col1.setAllowsBlanks(allowsBlank);

		Table table = new Table("Table");
		table.addColumn(col1);

		getDomainFacade().addMockedTable(table);
	}

	public void addDummyEmptyTableEmailColumnVariableAllowsBlank(boolean allowsBlank) {
		Column col1 = new Column("EmptyEmail", ValueType.EMAIL);
		col1.setAllowsBlanks(allowsBlank);

		Table table = new Table("Table");
		table.addColumn(col1);

		getDomainFacade().addMockedTable(table);
	}

	public void addDummyEmptyIntegerColumnWithVariableAllowsBlank(boolean allowsBlank) {
		Column col1 = new Column("EmptyInt", ValueType.INTEGER);
		col1.setAllowsBlanks(allowsBlank);

		Table table = new Table("Table");
		table.addColumn(col1);

		getDomainFacade().addMockedTable(table);
	}

	protected void addDummyTableNotEmptyEmailDefaultColumnValueNoBlanksAllowed() {
		DomainCell c1 = new DomainCell(ValueType.EMAIL, "@E");
		DomainCell c2 = new DomainCell(ValueType.EMAIL, "M@");
		DomainCell c3 = new DomainCell(ValueType.EMAIL, "D@");

		DomainCell row1cells[] = { c1 };
		DomainCell row2cells[] = { c2 };
		DomainCell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<DomainCell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<DomainCell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<DomainCell>(Arrays.asList(row3cells)));

		DomainCell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.EMAIL);
		col1.setDefaultValue("default@email");
		col1.setAllowsBlanks(false);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("DummyEmail");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
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

		persons.addRowWithoutAddingToColumns(r1);
		persons.addRowWithoutAddingToColumns(r2);
		persons.addRowWithoutAddingToColumns(r3);
		persons.addRowWithoutAddingToColumns(r4);
		return persons;
	}

}
