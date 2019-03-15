package domain.model;

import static org.junit.jupiter.api.Assertions.*;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.Test;

class DomainExceptionTest {
	private Exception de;
	private String exceptionString = "throws exception";

	/**
	 * Test 1 : Create Domain Exception without parameters
	 * | should create an instance from RuntimeException
	 */
	@Test
	void test1CreateBasicDomainException() {
		de = new DomainException();
		assertTrue(de instanceof RuntimeException);		
	}
	
	/**
	 * Test 2 : Create Domain Exception with basic init
	 * | should initialize an Domain Exception
	 */
	@Test
	void test2CreateBasicDomainExceptionWithParams() {
		de = new DomainException(exceptionString, new RuntimeException());
		assertEquals(exceptionString, de.getMessage());
	}
	
	/**
	 * Test 3 : Create Domain Exception with message string
	 */
	@Test
	void test3CreateDomainExceptionWithMessage() {
		de = new DomainException(exceptionString);
		assertEquals(exceptionString, de.getMessage());
	}
	
	/**
	 * Test 4 : Create Domain Exception with Throwable
	 * | should throw the given throwable class instance
	 */
	@Test
	void test4CreateDomainExceptionWithThrowable() {
		de = new DomainException(new RuntimeException());
		assertEquals(new RuntimeException().getClass(), de.getCause().getClass());
	}

}
