package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.columnSpec.ColumnSpec;

public class SelectStatement implements Statement {

	private List<ColumnSpec> columnSpecs;
	
	public SelectStatement() {
		this.columnSpecs = new ArrayList<>();
	}
	
	
	public void addColumnSpec(ColumnSpec columnSpec) {
		if(columnSpec == null) {
			throw new SqlException("The columnSpec cannot be null");
		}
		this.getColumnSpecs().add(columnSpec);
	}
	
	
	private List<ColumnSpec> getColumnSpecs() {
		return columnSpecs;
	}



	@Override
	public List<CellId> getAllCellIds() {
		return new ArrayList<>();
	}

}
