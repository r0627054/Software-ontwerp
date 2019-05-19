package domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A class of rows; a row contains multiple cells.
 * 
 * @version 1.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public class Row extends ObjectIdentifier {

	/**
	 * Variable storing all the cells.
	 */
	private List<DomainCell> cells = new ArrayList<>();

	/**
	 * Initialises a new row without cells. 
	 */
	public Row() {

	}

	/**
	 * Initialise a new row with the given list of cells.
	 *  
	 * @param cells
	 *        The list of cells.
	 * @effect The list of cells is set.
	 *          | this.setCells(cells)
	 */
	public Row(ArrayList<DomainCell> cells) {
		this.setCells(cells);
	}

	/**
	 * Returns a copy of the ArrayList of cells.
	 * 
	 * @return A copy of the ArrayList of cells.
	 *        | new ArrayList<>(this.cells)
	 */
	public ArrayList<DomainCell> getCells() {
		return new ArrayList<>(this.cells);
	}

	/**
	 * Sets an ArrayList of cells for the cells.
	 * 
	 * @param cells
	 *        an ArrayList which contains cells.
	 * @throws DomainException
	 *         The cells equals null.
	 *         | cells == null
	 * @post The ArrayList of cells equals the given cells
	 *         | new.getCells().equals(cells)      
	 */
	private void setCells(List<DomainCell> cells) {
		if (cells == null) {
			throw new DomainException("The List of cells cannot be null for a column.");
		}
		this.cells = cells;
	}

	/**
	 * Returns the cell at the requested index of the row.
	 * 
	 * @param index
	 *        | The index in of which the cell is positioned.
	 * @return The cell at the requested index of the row.
	 *          | this.cells.get(index)
	 */
	public DomainCell getCellAtIndex(int index) {
		if (index < 0) {
			throw new DomainException("Index of cell cannot be negative.");
		}
		return this.cells.get(index);
	}

	/**
	 * Adds at the end of the row.
	 * 
	 * @param cell
	 *        The cell that needs to be added to the row.
	 * @throws DomainException
	 *         The cell equals null.
	 *         | cells == null
	 * @effect The cell is added to the list of cells.
	 *         | cells.add(cell)
	 */
	public void addCell(DomainCell cell) {
		if (cell == null) {
			throw new DomainException("A cell cannot be null in a column.");
		}
		cells.add(cell);
	}

	/**
	 * Deletes the cell on the given index of the row.
	 * 
	 * @param columnIndex the index of the cell which will be deleted.
	 * @effect the cell on the given index is removed.
	 *         | this.cells.remove(rowIndex)
	 */
	public void deleteCell(int columnIndex) {
		if (columnIndex < 0) {
			throw new DomainException("Cannot delete a cell with negative index of rows.");
		}
		List<DomainCell> currentCells = this.getCells();
		currentCells.remove(columnIndex);
		this.setCells(currentCells);
	}

	/**
	 * Checks whether the cell in the row, stored at the given index has the given UUID.
	 * @param index the index of the cell in the row.
	 * @param cellId the UUID of the cell in the row.
	 * @return true if the row contains a cell with the given UUID at the given index, otherwise false.
	 */
	public boolean getCellAtIndexEqualsId(int index, UUID cellId) {
		return this.getCellAtIndex(index).getId().equals(cellId);
	}

	/**
	 * Checks whether the row contains a cell with the given UUID.
	 * 
	 * @param cellId an ID of a cell.
	 * @return true if the row has cell with the given id, otherwise false.
	 */
	public boolean containsCell(UUID cellId) {
		for (int i = 0; i < this.getCells().size(); i++) {
			DomainCell cell = this.getCellAtIndex(i);
			if (cell.getId().equals(cellId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a String of the row class.
	 * {@inheritDoc}
	 * @return All the values of a row separated by a vertical line.
	 */
	@Override
	public String toString() {
		String result ="";
		for (DomainCell domainCell : cells) {
			result += "|\t" +domainCell.toString() + "\t";
		}
		result+= "|";
		return result;
	}
}
