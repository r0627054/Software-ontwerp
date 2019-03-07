package ui.model.viewmodes;

public enum ViewModeType {

	TABLESVIEWMODE("tablesViewMode"),
	TABLEROWSVIEWMODE("TableRowsViewMode"),
	TABLEDESIGNVIEWMODE("TableDesignViewMode");
	
	private String name;
	
	private ViewModeType(String name) {
		this.name= name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
