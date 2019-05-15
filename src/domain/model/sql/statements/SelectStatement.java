package domain.model.sql.statements;

import java.util.ArrayList;
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

			UUID cellId = litNum.isOneEditable() ? litNum.getFirstUUIDOfMap() : null;

			return new DomainCell(cellId, litNum.getValue(), ValueType.INTEGER);

		} else if (exp instanceof BooleanExpression) {
			return new DomainCell(null, ((BooleanExpression) exp).getValue(), ValueType.BOOLEAN);
		} else if (exp instanceof LiteralStringExpression) {
			return new DomainCell(null, ((LiteralStringExpression) exp).getValue(), ValueType.STRING);
		} else {
			throw new SqlException("Not implemented computeCell Expression");
		}
	}

	private DomainCell getDomainCellOfOutOfCellId(Expression exp, Row row, Map<CellId, Integer> cellIdMap) {
		CellId cellId = ((CellIdExpression) exp).getValue();
		Integer index = cellIdMap.get(cellId);
		return row.getCellAtIndex(index);
	}

}
