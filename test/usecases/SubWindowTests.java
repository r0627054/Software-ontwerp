package usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

import ui.model.window.sub.TableRowsWindow;
import ui.model.window.sub.TablesWindow;

public class SubWindowTests extends UseCaseTest implements TableListConstants, SubWindowConstants {

	/**
	 * Test 1 : Creating a new tablesSubWindow
	 * | A new tableSubWindow should be shown when pressed Ctrl+T.
	 * | It should be the active subwindow aswell.
	 */
	@Test
	public void test1ControlTCreatesANewTablesSubWindowAndMakesItTheActiveOne() {
		try {
			assertEquals(null, getUiFacade().getView().getCurrentSubWindow());

			simulateKeyPress(KeyEvent.VK_CONTROL);
			simulateKeyPress(KeyEvent.VK_T);

			assertTrue(getUiFacade().getView().getCurrentSubWindow() instanceof TablesWindow);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 2 : Closing a window
	 * | A subWindow should be closed when pressing the close button.
	 * | It should set the previous window to the active window.
	 */
	@Test
	public void test2CloseButtonClosesTheActiveWindowAndMakesTheLastUsedActive() {
		try {
			addDummyTable(NEW_TABLE_NAME);

			simulateKeyPress(KeyEvent.VK_CONTROL);
			simulateKeyPress(KeyEvent.VK_T);
			simulateDoubleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			assertTrue(getUiFacade().getView().getCurrentSubWindow() instanceof TableRowsWindow);

			simulateSingleClick(CLOSE_BUTTON_X, CLOSE_BUTTON_Y);
			simulateClickRelease(CLOSE_BUTTON_X, CLOSE_BUTTON_Y);
			assertTrue(getUiFacade().getView().getCurrentSubWindow() instanceof TablesWindow);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 3 : Moving a window
	 * | A subWindow should be moved when clicking the title bar and dragging it somewhere.
	 */
	@Test
	public void test3DraggingTitleBarWillMoveTheWindow() {
		try {
			addDummyTable(NEW_TABLE_NAME);

			int startX = getUiFacade().getView().getCurrentSubWindow().getX();
			int startY = getUiFacade().getView().getCurrentSubWindow().getY();

			simulateSingleClick(TITLEBAR_X, TITLEBAR_Y);
			simulateClickDrag(100, 100);

			assertTrue(startX < getUiFacade().getView().getCurrentSubWindow().getX());
			assertTrue(startY < getUiFacade().getView().getCurrentSubWindow().getY());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 4 : Resizing a window
	 * | A subWindow should be made smaller when clicking on the top left corner and dragging it down and to the right.
	 */
	@Test
	public void test4DraggingTheBorderWillResizeTheWindow() {
		try {
			addDummyTable(NEW_TABLE_NAME);

			int startW = getUiFacade().getView().getCurrentSubWindow().getWidth();
			int startH = getUiFacade().getView().getCurrentSubWindow().getHeight();

			simulateSingleClick(DRAG_LEFT_TOP_X, DRAG_LEFT_TOP_Y);
			simulateClickDrag(100, 100);

			assertTrue(startW > getUiFacade().getView().getCurrentSubWindow().getWidth());
			assertTrue(startH > getUiFacade().getView().getCurrentSubWindow().getHeight());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test 5 : Resizing a window
	 * | A subWindow should be made bigger when clicking on the bottom right corner and dragging it down and to the right.
	 */
	@Test
	public void test5DraggingTheBorderWillResizeTheWindow() {
		try {
			addDummyTable(NEW_TABLE_NAME);

			int startW = getUiFacade().getView().getCurrentSubWindow().getWidth();
			int startH = getUiFacade().getView().getCurrentSubWindow().getHeight();

			simulateSingleClick(DRAG_RIGHT_BOT_X, DRAG_RIGHT_BOT_Y);
			simulateClickDrag(DRAG_RIGHT_BOT_X + 50, DRAG_RIGHT_BOT_Y + 50);

			assertTrue(startW < getUiFacade().getView().getCurrentSubWindow().getWidth());
			assertTrue(startH < getUiFacade().getView().getCurrentSubWindow().getHeight());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**
	 * Test 6 : Moving a window and selecting another afterwards
	 * | A subWindow should be moved when clicking the title bar and dragging it somewhere.
	 * | When clicking on a non active sub window afterwards, this sub window should become the active one.
	 */
	@Test
	public void test6DraggingTitleBarWillMoveTheWindow() {
		try {
			addDummyTable(NEW_TABLE_NAME);

			simulateDoubleClick(FIRST_TABLE_X, FIRST_TABLE_Y);
			assertTrue(getUiFacade().getView().getCurrentSubWindow() instanceof TableRowsWindow);
			
			simulateSingleClick(TITLEBAR_X, TITLEBAR_Y);
			simulateClickDrag(100, 100);
			simulateSingleClick(DRAG_LEFT_TOP_X, DRAG_LEFT_TOP_Y);
			
			assertTrue(getUiFacade().getView().getCurrentSubWindow() instanceof TablesWindow);			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
