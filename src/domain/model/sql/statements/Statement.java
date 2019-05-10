package domain.model.sql.statements;

import java.util.List;

import domain.model.sql.CellId;

public interface Statement {
	public List<CellId> getAllCellIds();
}
