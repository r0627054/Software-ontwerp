package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.model.Column;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.columnSpec.ColumnSpec;

public class SelectStatement implements Statement {

	private List<ColumnSpec> columnSpecs;

	public SelectStatement() {
		this.columnSpecs = new ArrayList<>();
	}

	public void addColumnSpec(ColumnSpec columnSpec) {
		if (columnSpec == null) {
			throw new SqlException("The columnSpec cannot be null");
		}
		this.getColumnSpecs().add(columnSpec);
	}

	public List<ColumnSpec> getColumnSpecs() {
		return columnSpecs;
	}

	@Override
	public List<CellId> getAllCellIds() {
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return "SELECT ";
	}

	public List<Column> executeColumnSpecs(List<Column> columns, Map<CellId, Integer> cellIdMap) {
		List<Column> result = new ArrayList<Column>();
		for (ColumnSpec cs : getColumnSpecs()) {
			Column newCol = cs.isValidCol(columns, cellIdMap);

			if (newCol == null) {
				throw new SqlException("Columnspec of Select statement found no column");
			}
			result.add(newCol);
		}
		return result;
	}

}
