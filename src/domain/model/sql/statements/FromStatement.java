package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.tablespecs.InnerJoinTableSpec;
import domain.model.sql.tablespecs.TableSpec;

/**
 * The FromStatement is a statement of an sql Query. It is composed of a list of tableSpecs.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class FromStatement implements Statement {

	/**
	 * Variable storing the list of tableSpecs.
	 */
	private List<TableSpec> tableSpecs;

	/**
	 * Creates a from statement without any tableSpecs.
	 */
	public FromStatement() {
		setTableSpecs(new ArrayList<>());
	}

	/**
	 * Adds a tableSpec to the list of tableSpecs.
	 * @param spec The tableSpec which will be added to the list of specs.
	 * @throws SqlException when the spec equals null.
	 *           | spec == null
	 * @effect The TableSpec is added to the list of TableSpecs.
	 *           | getTableSpecs().add(spec);
	 */
	public void addTableSpec(TableSpec spec) {
		if (spec == null) {
			throw new SqlException("Cannot add a null spec to the FromStatement specs.");
		}
		getTableSpecs().add(spec);
	}

	/**
	 * Returns the list of tableSpecs. (The instance variable)
	 * @return the list of tableSpecs.
	 */
	public List<TableSpec> getTableSpecs() {
		return tableSpecs;
	}

	/**
	 * Sets the list of tableSpecs to the given List of tableSpecs.
	 * @param tableSpecs The list of tableSpecs to which the variable will be set.
	 * @throws SqlException when the TableSpec equals null.
	 *          | tableSpecs == null
	 * @post The tableSpec variable equals the parameter.
	 *          | new.getTableSpecs() = tableSpecs
	 */
	private void setTableSpecs(List<TableSpec> tableSpecs) {
		if (tableSpecs == null) {
			throw new SqlException("TableSpecs cannot be null in FromStatement");
		}
		this.tableSpecs = tableSpecs;
	}

	/**
	 * {@inheritDoc}
	 * The result String is one long string composed using all toString methods of the tablesSpecs.
	 */
	@Override
	public String toString() {
		String result = "";

		for (TableSpec ts : getTableSpecs()) {
			result += ts.toString() + " ";
		}

		return result;
	}

	/**
	 * Returns all the (real) names of the tables the FromStatement uses.
	 * @return a list of all the names of the tables the FromStatement uses.
	 *       |for (TableSpec ts : getTableSpecs()) 
	 *      	result.add(ts.getRealTableName());
	 */
	public List<String> getAllTables() {
		List<String> result = new ArrayList<String>();

		for (TableSpec ts : getTableSpecs()) {
			result.add(ts.getRealTableName());
		}

		return result;
	}

	
	/**
	 * Returns all the cellIds the FromStatement contains.
	 * @return returns all the cellIds the FromStatement contains.
	 */
	public List<CellId> getAllCellIds() {
		List<CellId> result = new ArrayList<CellId>();

		for (TableSpec ts : getTableSpecs()) {
			if (ts instanceof InnerJoinTableSpec) {
				InnerJoinTableSpec casted = (InnerJoinTableSpec) ts;
				result.add(casted.getCellIdLeft());
				result.add(casted.getCellIdRight());
			}
		}
		return result;
	}

	/**
	 * Returns the mapping of display table names (these are the table 
	 *   names used locally in the query) to the real table names.
	 * @return the mapping of display table names (these are the table  names used locally in the query) to the real table names.
	 */
	public Map<String, String> getDisplayToRealNamesMap() {
		Map<String, String> result = new HashMap<>();
		for (TableSpec ts : getTableSpecs()) {
			result.put(ts.getDisplayTableName(), ts.getRealTableName());
		}
		return result;
	}

	/**
	 * Returns the mapping of real Table names to the display table names (these are the table names used locally in the query) 
	 * @return the mapping of real Table names to the display table names (these are the table names used locally in the query) 
	 */
	public Map<String, String> getRealToDisplayNamesMap() {
		Map<String, String> result = new HashMap<>();

		for (TableSpec ts : getTableSpecs()) {
			result.put(ts.getRealTableName(), ts.getDisplayTableName());
		}
		return result;
	}

	/**
	 * Returns all the display table names (these are the table names used locally in the query) 
	 * @return all the display table names (these are the table names used locally in the query) 
	 */
	public List<String> getAllDisplayTableNames() {
		List<String> result = new ArrayList<>();

		for (TableSpec ts : getTableSpecs()) {
			result.add(ts.getDisplayTableName());
		}
		return result;
	}

	/**
	 * Checks whether the FromStatement uses the table with the given name.
	 * @param name the name of the table.
	 * @return True when the FromStatement uses a table with the given table name; otherwise false.
	 */
	public boolean usesTable(String name) {
		for (TableSpec ts : getTableSpecs()) {
			if (ts.getRealTableName().equals(name)) {
				return true;
			}
		}
		return false;
	}
		
	

}
