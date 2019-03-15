package usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import controller.Controller;
import domain.model.Cell;
import domain.model.Column;
import domain.model.DomainFacade;
import domain.model.Row;
import domain.model.Table;
import domain.model.ValueType;
import ui.model.components.Component;
import ui.model.components.Container;
import ui.model.components.DesignTable;
import ui.model.components.RowsTable;
import ui.model.components.TableList;
import ui.model.view.UIFacade;
import ui.model.viewmodes.ViewModeType;

public abstract class UseCaseTest {
	private static UIFacade uiFacade;
	private static DomainFacade domainFacade;
	private static Controller controller;

	public static boolean setupIsDone = false;

	@BeforeAll
	public static void init() {
		setupSingleton();
	}

	public static void setupSingleton() {
		if (setupIsDone) {
			return;
		} else {
			setupIsDone = true;
			setUiFacade(UIFacade.getInstance());
			setDomainFacade(DomainFacade.getInstance());
			setController(new Controller(uiFacade, domainFacade, false));
		}
	}

	@BeforeEach
	public void resetDomainAndUiFacade() {
		this.getDomainFacade().resetTables();
		this.getUiFacade().resetViewModes();
	}

	protected void addDummyTable(String tableName) {
		getDomainFacade().addDummyTable(tableName);
		getUiFacade().updateTablesViewMode(getDomainFacade().getTableNames());
	}

	protected void emulateKeyPress(int keyCode) {
		getUiFacade().emulateKeyPress(keyCode);
	}

	protected void emulateKeyPresses(int keyCode, int amount) {
		for (int i = 0; i < amount; i++) {
			getUiFacade().emulateKeyPress(keyCode);
		}
	}

	protected void emulateKeyPress(char keyChar) {
		getUiFacade().emulateKeyPress(keyChar);
	}

	protected void emulateKeyPress(String string) {
		for (int i = 0; i < string.length(); i++) {
			getUiFacade().emulateKeyPress(string.charAt(i));
		}
	}

	protected void emulateSingleClick(int x, int y) {
		getUiFacade().emulateClickClicked(x, y, 1);
	}

	protected void emulateDoubleClick(int x, int y) {
		getUiFacade().emulateClickClicked(x, y, 2);
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

	protected Controller getController() {
		return controller;
	}

	private static void setController(Controller controller) {
		UseCaseTest.controller = controller;
	}

	protected TableList getTablesViewModeTableList() {
		for (Component c : getUiFacade().getView().getTablesViewMode().getComponents()) {
			if (c instanceof Container) {
				Container container = (Container) c;

				for (Component containerComponents : container.getComponentsList()) {
					if (containerComponents instanceof TableList) {
						return (TableList) containerComponents;
					}
				}
			}
		}
		return null;
	}

	protected DesignTable getTableViewModeDesignTable(UUID tableId) {
		for (Component c : getUiFacade().getView().getViewMode(tableId, ViewModeType.TABLEDESIGNVIEWMODE)
				.getComponents()) {
			if (c instanceof Container) {
				Container container = (Container) c;

				for (Component containerComponents : container.getComponentsList()) {
					if (containerComponents instanceof DesignTable) {
						return (DesignTable) containerComponents;
					}
				}
			}
		}
		return null;
	}

	protected RowsTable getTableViewModeRowsTable(UUID tableId) {
		for (Component c : getUiFacade().getView().getViewMode(tableId, ViewModeType.TABLEROWSVIEWMODE)
				.getComponents()) {
			if (c instanceof Container) {
				Container container = (Container) c;

				for (Component containerComponents : container.getComponentsList()) {
					if (containerComponents instanceof RowsTable) {
						return (RowsTable) containerComponents;
					}
				}
			}
		}
		return null;
	}

	protected void addDummyTableEmailColumnEmailCellValues() {
		Cell c1 = new Cell(ValueType.EMAIL, "A@");
		Cell c2 = new Cell(ValueType.EMAIL, "M@");
		Cell c3 = new Cell(ValueType.EMAIL, "D@");

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.STRING, null);
		Cell c2 = new Cell(ValueType.STRING, "M@");
		Cell c3 = new Cell(ValueType.STRING, "D@");

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
		Column col1 = new Column("Email", ValueType.STRING);
		col1.addCells(Arrays.asList(colCells1));

		Table email = new Table("DummyEmail");

		email.addColumn(col1);
		email.addRow(r1);
		email.addRow(r2);
		email.addRow(r3);

		getDomainFacade().addMockedTable(email);
	}

	protected void addDummyTableIntColumnStringCellValues() {
		Cell c1 = new Cell(ValueType.INTEGER, null);
		Cell c2 = new Cell(ValueType.INTEGER, 123);
		Cell c3 = new Cell(ValueType.INTEGER, 5);

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.STRING, null);
		Cell c2 = new Cell(ValueType.STRING, "true");
		Cell c3 = new Cell(ValueType.STRING, "false");

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.STRING, null);
		Cell c2 = new Cell(ValueType.STRING, null);
		Cell c3 = new Cell(ValueType.STRING, null);

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.EMAIL, null);
		Cell c2 = new Cell(ValueType.EMAIL, null);
		Cell c3 = new Cell(ValueType.EMAIL, null);

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.BOOLEAN, null);
		Cell c2 = new Cell(ValueType.BOOLEAN, null);
		Cell c3 = new Cell(ValueType.BOOLEAN, null);

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.INTEGER, null);
		Cell c2 = new Cell(ValueType.INTEGER, null);
		Cell c3 = new Cell(ValueType.INTEGER, null);

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.STRING, "@E");
		Cell c2 = new Cell(ValueType.STRING, "M@");
		Cell c3 = new Cell(ValueType.STRING, "D@");

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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
		Cell c1 = new Cell(ValueType.STRING, "@E");
		Cell c2 = new Cell(ValueType.STRING, "M@");
		Cell c3 = new Cell(ValueType.STRING, "D@");

		Cell row1cells[] = { c1 };
		Cell row2cells[] = { c2 };
		Cell row3cells[] = { c3 };

		Row r1 = new Row(new ArrayList<Cell>(Arrays.asList(row1cells)));
		Row r2 = new Row(new ArrayList<Cell>(Arrays.asList(row2cells)));
		Row r3 = new Row(new ArrayList<Cell>(Arrays.asList(row3cells)));

		Cell colCells1[] = { c1, c2, c3 };
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

}
