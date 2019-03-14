package usecases;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ UseCase1Test.class, UseCase2Test.class })
public final class AllUseCaseTests {
}
