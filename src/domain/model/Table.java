package domain.model;

import java.util.ArrayList;
import java.util.List;

public class Table {

	String name;
	List<Column> columns = new ArrayList<>();
	List<Row>    rows    = new ArrayList<>();
	
	public Table(String name) {
		this.setName(name);
	}
	
	
	public void addColumn(Column column) {
		
	}
	
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public List<Column> getColumns() {
		return columns;
	}
	
	private void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public List<Row> getRows() {
		return rows;
	}
	
	private void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	
	
	
	
	
	
}
