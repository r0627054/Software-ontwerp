package domain.model.sql.statements;

import java.util.List;

import domain.model.sql.CellId;

/**
 * A statement is interface implemented by all the different SQL Statements.
 * 
 * @version 3.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public interface Statement {
	/**
	 * Returns all the CellIds the specific statement uses.
	 * @return a list of all the CellIds the specific statement uses.
	 */
	public List<CellId> getAllCellIds();
}
