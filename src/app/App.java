package app;

import ui.view.TablesWindow;

public class App {

	public static void main(String[] args) {
		 java.awt.EventQueue.invokeLater(() -> {
	         new TablesWindow("Table Mode Window").show();
	      });
	}

}
