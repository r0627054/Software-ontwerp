package usecases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import ui.model.components.Component;
import ui.model.components.EditableTextField;
import ui.model.components.TableList;
import ui.model.components.UICell;
import ui.model.components.VerticalComponentList;
import ui.model.window.sub.TablesWindow;

public class UseCase3Test extends UseCaseTest implements TableListConstants {

	/**
	 * Test 1 : Edit table query
	 * click query cell in table rows and edit the query,
	 * table query should be updated
	 */
	@Test
	public void test1EditColumnInQuery() {
		try {
			if(!(getDomainFacade().getTableNames().isEmpty())) throw new Exception("mockup tables are still in domainfacade constr.");
			this.addDummyTable("A");
			addDummyTableNotEmptyEmailDefaultColumnValueNoBlanksAllowed(); // tableName = DummyEmail


			simulateSingleClick(SECOND_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPress(ADD_TABLE_QUERY_REF_SECOND_TABLE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			
			TablesWindow twBefore = (TablesWindow) getUiFacade().getView().getCurrentSubWindow();
			TableList tlBefore = (TableList) twBefore.getContainer().getComponentsList().get(0);	
			
			
			simulateSingleClick(SECOND_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 200);
			simulateKeyPress(VALID_EDIT_TABLE_QUERY_REF_SECOND_TABLE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			
			TablesWindow twAfter = (TablesWindow) getUiFacade().getView().getCurrentSubWindow();
			TableList tlAfter = (TableList) twAfter.getContainer().getComponentsList().get(0);
			VerticalComponentList vcAfter = (VerticalComponentList) tlAfter.getComponentsList().get(1);
			
			
			String tabelNameAfter = ((EditableTextField) ((UICell) vcAfter.getComponentsList().get(0)).getComponent()).getText();
			assertEquals(tabelNameAfter, VALID_EDIT_TABLE_QUERY_REF_SECOND_TABLE);
		} catch (Exception e) {
//			e.printStackTrace();
			assertTrue(false);
		}
	}	
	/**
	 * Test 1 : Changing to invalid query
	 * Changing the query to an invalid one should freez the query box
	 */
	@Test
	public void test2MakeQueryInvalid() {
		try {
			if(!(getDomainFacade().getTableNames().isEmpty())) throw new Exception("mockup tables are still in domainfacade constr.");
			this.addDummyTable("A");
			addDummyTableNotEmptyEmailDefaultColumnValueNoBlanksAllowed(); // tableName = DummyEmail


			simulateSingleClick(SECOND_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPress(ADD_TABLE_QUERY_REF_SECOND_TABLE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			
			TablesWindow twBefore = (TablesWindow) getUiFacade().getView().getCurrentSubWindow();
			TableList tlBefore = (TableList) twBefore.getContainer().getComponentsList().get(0);	
			VerticalComponentList vcBefore= (VerticalComponentList) tlBefore.getComponentsList().get(1);
			assertFalse(twBefore.isPaused());
			
			
			simulateSingleClick(SECOND_TABLE_X, FIRST_TABLE_Y);
			simulateKeyPresses(KeyEvent.VK_BACK_SPACE, 20);
			simulateKeyPress(VALID_EDIT_TABLE_QUERY_REF_SECOND_TABLE);
			simulateKeyPress(KeyEvent.VK_ENTER);
			
			TablesWindow twAfter = (TablesWindow) getUiFacade().getView().getCurrentSubWindow();
			TableList tlAfter = (TableList) twAfter.getContainer().getComponentsList().get(0);
			VerticalComponentList vcAfter = (VerticalComponentList) tlAfter.getComponentsList().get(1);
			System.out.println(twAfter.isPaused());
			
			String tableNameBefore = ((EditableTextField) ((UICell) vcBefore.getComponentsList().get(0)).getComponent()).getText();
			String tableNameAfter = ((EditableTextField) ((UICell) vcAfter.getComponentsList().get(0)).getComponent()).getText();
			assertTrue(twAfter.isPaused());
			assertEquals(tableNameAfter, tableNameBefore);
		} catch (Exception e) {
//			e.printStackTrace();
			assertTrue(false);
		}
	}		
	}

	


