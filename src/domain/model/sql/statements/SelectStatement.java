package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.List;

import domain.model.sql.CellId;

public class SelectStatement implements Statement {

	@Override
	public List<CellId> getAllCellIds() {
		return new ArrayList<>();
	}

}
