package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.SqlException;
import domain.model.sql.tablespecs.TableSpec;

public class FromStatement {

	private List<TableSpec> tableSpecs;

	public FromStatement() {
		setTableSpecs(new ArrayList<>());
	}

	public void addTableSpec(TableSpec spec) {
		if (spec == null) {
			throw new SqlException("Cannot add a null spec to the FromStatement specs.");
		}
		getTableSpecs().add(spec);
	}

	/**
	 * @return the tableSpecs
	 */
	private List<TableSpec> getTableSpecs() {
		return tableSpecs;
	}

	/**
	 * @param tableSpecs the tableSpecs to set
	 */
	private void setTableSpecs(List<TableSpec> tableSpecs) {
		if (tableSpecs == null) {
			throw new SqlException("TableSpecs cannot be null in FromStatement");
		}
		this.tableSpecs = tableSpecs;
	}

	@Override
	public String toString() {
		String result = "";

		for (TableSpec ts : getTableSpecs()) {
			result += ts.toString() + " ";
		}

		return result;
	}

	public List<String> getAllTables() {
		List<String> result = new ArrayList<String>();

		for (TableSpec ts : getTableSpecs()) {
			result.add(ts.getRealTableName());
		}

		return result;
	}

}
