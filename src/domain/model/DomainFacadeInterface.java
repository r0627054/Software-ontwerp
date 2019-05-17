package domain.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * An interface of the domainFacade. This interface defines all the functionalities
 *  that should be handled by the domain.
 * 
 * @version 2.0
 * @author Dries Janse, Steven Ghekiere, Laurens Druwel
 *
 */
public interface DomainFacadeInterface {

	/**
	 * Updates the name of the table, of that given id, with the given name.
	 * 
	 * @param id
	 *        The id of the table.
	 * @param newName
	 *        The new name of the table.
	 */
	public void updateTableName(UUID id, String newName);

	/**
	 * Updates the name of the column.
	 * The name of the column (with columnId) in the table (with tableId), will be changed to the new name.
	 * 
	 * @param tableId
	 *        The tableId of the table where the column is situated.
	 * @param columnId
	 *        The columnId of the column which name will be changed.
	 * @param newName
	 *        The new name of the column.
	 * @throws DomainException when the tableId or columnId or newName equals null. Or when the newName is empty.
	 *        | tableId ==null || columnId == null || newName == null || newName.isEmpty()
	 */
	public void updateColumnName(UUID tableId, UUID columnId, String newName);

	/**
	 * Returns a map of all the table names and queries. 
	 * The key is the UUID of the table and the value is a list containing the name of table and maybe the query.
	 */
	public Map<UUID, List<String>> getTableNames();

	/**
	 * Returns a map with all the table information of the given tableId.
	 * The first inner map, the key: UUID of the column and value: column name.
	 * The second inner map, the key: UUID of cell and the value: the value of the cell in the column.
	 * 
	 * @param tableId
	 *        The tableId of which the information should be gathered.
	 * @return a map with all the information associated with the given tableId.
	 */
	public Map<List<Object>, List<Object[]>> getTableWithIds(UUID tableId);

	/**
	 * Creates a new table with the given name.
	 * 
	 * @param name
	 *        The name the new table will have.
	 */
	public void addTable(String name);

	/**
	 * Returns the name of the table with the given id.
	 * 
	 * @param id
	 *        The id of the table.
	 * @return The name of the table with the given id.
	 */
	public String getTableNameOfId(UUID id);

	/**
	 * Creates a new Table with a unique name.
	 * Its name is tableN, where N is a number, and it is different from the names of the existing tables.
	 * The new table initially has no columns and no rows.
	 */
	public void createNewTable();

	/**
	 * Deletes the table of the given id.
	 * @param id
	 *        The id of the table which will be deleted.
	 * @throws DomainException when the id equals null.
	 *         | id == null
	 */
	public void deleteTable(UUID id);

	/**
	 * Returns map where the key is the id of the column, and the value is a linkedHashList of characteristics.
	 * (the characteristic values are: column name, type, allow Blanks, Default Value)
	 * 
	 * @param id
	 *        The id of the table.
	 * @return a map where the key is the id of the column, and the value is a linkedHashList of characteristics. (column name, type, allow Blanks, Default Value)
	 * @throws DomainException when the id equals null
	 *         | id == null
	 * @throws DomainException when the there does not exist a table with the given id.
	 *         | getTable(id) == null
	 */
	public Map<UUID, LinkedHashMap<String, Object>> getColumnCharacteristics(UUID id);

	/**
	 * Creates a new column in the table with the given table id.
	 * The name of the column is ColumnN, where N is a number, and it is different from the names of the existing columns.
	 * All existing rows of the table get a blank value as the value for the new column.
	 * 
	 * @param id
	 *        The id of the table where the column will be created.
	 * @throws DomainException when the id equals null
	 *         | id == null
	 */
	public void addColumnToTable(UUID id);

	/**
	 * Returns the index of the characteristic in the given column with column Id.
	 * Returns -1 if the characteristic cannot be found in the column.
	 * 
	 * @param tableId 
	 *        The id of the table.
	 * @param columnId
	 *        The id of the column in the table.
	 * @param characteristic
	 *        The characteristic of the column.
	 * @return The index of the characteristic in the column.
	 *         When the characteristic cannot be found, -1 is returned.
	 */
	public int getIndexOfColumnCharacteristic(UUID tableId, UUID columnId, String characteristic);

	/**
	 * Updates the type of the column in the table with the given tableId. 
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param newType
	 *        | The new type of the column.
	 * @throws DomainException when the tableId or columnId or newType equals null
	 *         | tableId ==null || columnId == null || newType == null
	 */
	public void setColumnType(UUID tableId, UUID columnId, ValueType newType);

	/**
	 * Updates the allowBlanks variable of the column in the table with the given tableId. 
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param newBool
	 *        | The new allowBlanks value of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 */
	public void setAllowBlanks(UUID tableId, UUID columnId, boolean newBool);

	/**
	 * Updates the default value of the column in the table with the given tableId. 
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param newDefaultValue
	 *        | The new default value of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 */
	public void setColumnDefaultValue(UUID tableId, UUID columnId, Object newDefaultValue);

	/**
	 * Returns the valueType of the column in the table with the given tableId.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 * @return the valueType of the column in the table with the given tableId.
	 */
	public ValueType getValueTypeOfColumn(UUID tableId, UUID columnId);

	/**
	 * Returns whether or not the column in the table with the given tableId allows blank values.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @throws DomainException when the tableId or columnId equals null
	 *         | tableId ==null || columnId == null
	 * @return Whether or not the column in the table with the given tableId allows blank values.
	 */
	public boolean getColumnAllowBlanks(UUID tableId, UUID columnId);

	/**
	 * The column with the given columnId is removed from the table with the given tableId.
	 * And it removes the value for the deleted column from all of the table's rows.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @throws DomainException when the tableId or columnId  equals null
	 *        | tableId ==null || columnId == null
	 */
	public void deleteColumn(UUID tableId, UUID columnId);

	/**
	 * Creates a new row in the table with the given tableId.
	 * It's value for each column is the column's default value.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @throws DomainException when the tableId equals null
	 *        | tableId ==null
	 */
	public void createNewRow(UUID tableId);

	/**
	 * Edits the cell in the table.
	 *  
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @param newValue
	 *        | the new value of the cell.
	 * @throws DomainException when the tableId or columnId or cellId equals null
	 *        | tableId ==null || columnId == null || cellId == null
	 */
	public void editCellInTable(UUID tableId, UUID columnId, UUID cellId, Object newValue);

	/**
	 * Returns the columnId of the cell (with cellId) in the table (with tableId).
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param cellId
	 *        | The id of the cell.
	 * @return the columnId of the cell (with cellId) in the table (with tableId).
	 * @throws DomainException when the tableId or cellId equals null
	 *        | tableId ==null || cellId == null
	 */
	public UUID getColumnId(UUID tableId, UUID cellId);

	/**
	 * Returns the index of the cell in the column.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param columnId
	 *        | The id of the column.
	 * @param cellId
	 *        | The id of the cell.
	 * @return Returns the index of the cell in the column.
	 */
	public int getIndexOfCellInColumnId(UUID tableId, UUID columnId, UUID cellId);

	/**
	 * Removes the row (with rowId) in the table (with table id).
	 * It also removes the cells of that row in the columns.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param rowId
	 *        | The id of the row.
	 */
	public void deleteRow(UUID tableId, UUID rowId);

	/**
	 * Returns the id of the row which has a cell at the first position with the given cellId.
	 * 
	 * @param tableId
	 *        | The id of the table.
	 * @param cellIdOfFirstElement
	 *        | The id of the first cell in a row in the table.
	 * @return the id of the row which has a cell at the first position with the given cellId.
	 */
	public UUID getRowId(UUID tableId, UUID cellIdOfFirstElement);

	/**
	 * Checks if table with certain id is empty.
	 * 
	 * @param tableId
	 * 		| The id of the table to check.
	 * @return the truth value of the table with an id
	 */
	public boolean isTableWithIdEmpty(UUID tableId);

	public void createComputedTable(UUID tableId, String query);

	public boolean isComputedTable(UUID tableId);

	public List<UUID> getTableIdOfUsedTables(UUID tableId, UUID columnId, UUID cellId);

	public void setEmptyQuery(UUID tableId);
}
