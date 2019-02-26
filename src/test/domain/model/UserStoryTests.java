package test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ui.model.CanvasWindow;

public class UserStoryTests {

	private CanvasWindow window;
	private String windowTitle;

	@BeforeAll
	void setup() {
		windowTitle = "testTitle";
		window = new CanvasWindow(windowTitle);
	}

	/*@Test
	void useCase1CreateTableAndTestClick() throws AWTException {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		Robot bot = new Robot();

		bot.mouseMove(50, 50);

		//Click for 100ms
		bot.mousePress(InputEvent.BUTTON1_MASK);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
		
		assertEquals(outContent.toString(), "blabla mouse clicked");
	}*/
}
