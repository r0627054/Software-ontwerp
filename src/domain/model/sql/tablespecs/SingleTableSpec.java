package domain.model.sql.tablespecs;

public class SingleTableSpec extends TableSpec {

	public SingleTableSpec(String realTableName, String displayTableName) {
		this.setRealTableName(realTableName);
		this.setDisplayTableName(displayTableName);
	}

	@Override
	public String toString() {
		return "FROM " + getRealTableName() + " AS " + getDisplayTableName();
	}
}
