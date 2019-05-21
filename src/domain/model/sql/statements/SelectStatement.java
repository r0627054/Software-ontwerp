package domain.model.sql.statements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import domain.model.Column;
import domain.model.DomainCell;
import domain.model.Row;
import domain.model.ValueType;
import domain.model.sql.CellId;
import domain.model.sql.SqlException;
import domain.model.sql.columnSpec.ColumnSpec;
import domain.model.sql.expression.BooleanExpression;
import domain.model.sql.expression.CellIdExpression;
import domain.model.sql.expression.Expression;
import domain.model.sql.expression.LiteralNumberExpression;
import domain.model.sql.expression.LiteralStringExpression;

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

	public ColumnSpec getColumnSpec(int index) {
		return this.getColumnSpecs().get(index);
	}

	public String getColumnNameOfColumnSpec(int index) {
		return this.getColumnSpec(index).getColumnName();
	}

	public Expression getExpressionOfColumnSpec(int index) {
		return this.getColumnSpec(index).getExpression();
	}

	public List<ColumnSpec> getColumnSpecs() {
		return columnSpecs;
	}

	@Override
	public List<CellId> getAllCellIds() {
		List<CellId> result = new ArrayList<>();
		for (ColumnSpec spec : this.getColumnSpecs()) {
			result.addAll(spec.getExpression().getAllCellIds());
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "SELECT";
		for (int i = 0; i < this.getColumnSpecs().size(); i++) {
			result += " " + this.getColumnSpecs().get(i).toString();
			if (i != (this.getColumnSpecs().size() - 1)) {
				result += ",";
			}
		}
		return result;
	}

	public DomainCell computeCell(Row row, Map<CellId, Integer> cellIdMap, int specIndex) {
		Expression exp = this.getExpressionOfColumnSpec(specIndex).simplify(row, cellIdMap);

		if (exp instanceof CellIdExpression) {
			return this.getDomainCellOfOutOfCellId(exp, row, cellIdMap);
		} else if (exp instanceof LiteralNumberExpression) {
			LiteralNumberExpression litNum = (LiteralNumberExpression) exp;

			DomainCell cell = null;
			if (litNum.isOneEditable()) {
				cell = new DomainCell(litNum.getFirstUUIDOfMap(), litNum.getValue(), ValueType.INTEGER);
			} else {
				cell = new DomainCell(ValueType.INTEGER, litNum.getValue());
			}
			this.getExpressionOfColumnSpec(specIndex).reset();
			return cell;
		} else if (exp instanceof BooleanExpression) {
			return new DomainCell(ValueType.BOOLEAN, ((BooleanExpression) exp).getValue());
		} else if (exp instanceof LiteralStringExpression) {
			return new DomainCell(ValueType.STRING, ((LiteralStringExpression) exp).getValue());
		} else {
			throw new SqlException("Not implemented computeCell Expression");
		}
	}

	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ((CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}

	public Object[] isEditableForColumnSpecIndex(int i) {
		return getColumnSpec(i).getExpression().isEditable();
	}

	public CellId getCellIdOfEditable(int specIndex) {
		return (CellId) ((Map<CellId, Integer>) this.isEditableForColumnSpecIndex(specIndex)[0]).keySet().toArray()[0];
	}

	public CellId getCellIdWithRealColumnName(CellId diffId) {
		for (ColumnSpec cs : getColumnSpecs()) {
			for (CellId id : cs.getExpression().getAllCellIds()) {
				if (id.equals(diffId)) {
					return new CellId(id.getTableId(), cs.getColumnName());
				}
			}
		}
		return null;
	}

	public List<String> getUsedColumnNamesOfDisplayTableName(String displayName) {
		if (displayName == null) {
			throw new SqlException("Cannot get used column names for null display table name");
		}

		List<String> result = new ArrayList<>();
		for (ColumnSpec cs : getColumnSpecs()) {
			for (CellId id : cs.getExpression().getAllCellIds()) {
				if (id.getTableId().equals(displayName)) {
					result.add(id.getColumnName());
				}
			}
		}
		return result;
	}

	public List<String> getAllSelectColumnNames() {
		List<String> result = new ArrayList<>();

		for (ColumnSpec cs : getColumnSpecs()) {
			result.add(cs.getColumnName());
		}

		return result;
	}

	public ColumnSpec getColumnSpecOfDisplayName(String displayName) {
		for (ColumnSpec columnSpec : this.getColumnSpecs()) {
			if (columnSpec.getColumnName().equals(displayName)) {
				return columnSpec;
			}
		}
		throw new SqlException("ColumnSpec requested which does not exist");
	}

	public List<CellId> getCellIdOfColumnName(String columnNameOfEditedCell) {
		for (ColumnSpec cs : getColumnSpecs()) {
			if (cs.getColumnName().equals(columnNameOfEditedCell)) {
				return cs.getExpression().getAllCellIds();
			}
		}
		return null;
	}

	public boolean hasDuplicateColumnNames() {
		int nmbrOfColumns = this.getAllSelectColumnNames().size();
		int nmbrOfcolumnsRemovedDuplicatedColumns = new HashSet<String>(getAllSelectColumnNames()).size();
		if (nmbrOfColumns == nmbrOfcolumnsRemovedDuplicatedColumns) {
			return false;
		}
		return true;
	}

}
