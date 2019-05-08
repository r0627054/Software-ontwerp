package domain.model.sql;

import domain.model.DomainException;

public class InnerJoinCondition {
	private String tableName1;
	private String tableName2;

	private String colTable1;
	private String colTable2;

	public InnerJoinCondition(String tableName1, String tableName2, String colTable1, String colTable2) {
		this.setColTable1(colTable1);
		this.setColTable2(colTable2);
		this.setTableName1(tableName1);
		this.setTableName2(tableName2);
	}

	public String getTableName1() {
		return tableName1;
	}

	private void setTableName1(String tableName1) {
		if (tableName1 == null || tableName1.isEmpty()) {
			throw new DomainException("Cannot set a InnerJoinCondition with an empty tableName");
		}
		this.tableName1 = tableName1;
	}

	public String getTableName2() {
		return tableName2;
	}

	private void setTableName2(String tableName2) {
		if (tableName2 == null || tableName2.isEmpty()) {
			throw new DomainException("Cannot set a InnerJoinCondition with an empty tableName");
		}
		this.tableName2 = tableName2;
	}

	public String getColTable1() {
		return colTable1;
	}

	private void setColTable1(String colTable1) {
		if (colTable1 == null || colTable1.isEmpty()) {
			throw new DomainException("Cannot set a InnerJoinCondition with an empty columnName");
		}
		this.colTable1 = colTable1;
	}

	public String getColTable2() {
		return colTable2;
	}

	private void setColTable2(String colTable2) {
		if (colTable2 == null || colTable2.isEmpty()) {
			throw new DomainException("Cannot set a InnerJoinCondition with an empty columnName");
		}
		this.colTable2 = colTable2;
	}

	@Override
	public String toString() {
		return getTableName1() + "." + getColTable1() + " = " + getTableName2() + "." + getColTable2();
	}

}
