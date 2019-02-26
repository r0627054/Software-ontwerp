package domain.model;

import java.util.ArrayList;

/**
 * A class of rows; a row contains multiple cells.
 * 
 * @version 1.0
 * @author Dries Janse
 *
 */
public class Row {

	/**
	 * Variable storing all the cells.
	 */
	private ArrayList<Cell> cells = new ArrayList<>();
	
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
	 * @efffect The list of cells is set.
	 *          | this.setCells(cells)
	 */
	public Row(ArrayList<Cell> cells) {
		this.setCells(cells);
	}
	
	/**
	 * Returns a copy of the ArrayList of cells.
	 * 
	 * @return A copy of the ArrayList of cells.
	 *        | new ArrayList<>(this.cells)
	 */
	public ArrayList<Cell> getCells() {
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
	private void setCells(ArrayList<Cell> cells) {
		if(cells == null) {
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
	public Cell getCellAtIndex(int index) {
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
	public void addCell(Cell cell) {
		if(cell == null) {
			throw new DomainException("A cell cannot be null in a column.");
		}
		cells.add(cell);
	}
	
}
