package com.j256.simplemagic.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class LoggerFactoryTest {

	@Test
	public void testGetLoggerClass() {
		assertNotNull(LoggerFactory.getLogger(getClass()));
	}

	@Test
	public void testGetLoggerString() {
		assertNotNull(LoggerFactory.getLogger(getClass().getName()));
	}

	@Test
	public void testConstructor() throws Exception {
		@SuppressWarnings("rawtypes")
		Constructor[] constructors = LoggerFactory.class.getDeclaredConstructors();
		assertEquals(1, constructors.length);
		constructors[0].setAccessible(true);
		constructors[0].newInstance();
	}

	@Test
	public void testLogTypeIsAvailable() {
		assertFalse(LoggerFactory.LogType.COMMONS_LOGGING.isAvailable());
		assertTrue(LoggerFactory.LogType.LOG4J2.isAvailable());
		assertTrue(LoggerFactory.LogType.LOCAL.isAvailable());
		assertTrue(LoggerFactory.LogType.LOCAL.isAvailableTestClass());
	}

	@Test
	public void testLogTypeUnknownLog() {
		Log log = LoggerFactory.LogType.COMMONS_LOGGING.createLog(getClass().getName());
		assertTrue(log instanceof LocalLog);
		log = LoggerFactory.LogType.COMMONS_LOGGING.createLog(getClass().getName());
		assertTrue(log instanceof LocalLog);
		log = LoggerFactory.LogType.LOG4J2.createLog(getClass().getName());
		assertTrue(log instanceof Log4j2Log);
		log = LoggerFactory.LogType.LOCAL.createLog(getClass().getName());
		assertTrue(log instanceof LocalLog);
	}

	@Test
	public void testLogTypeKnownLog() {
		Log log = LoggerFactory.LogType.LOCAL.createLog(getClass().getName());
		assertTrue(log instanceof LocalLog);
	}

	@Test
	public void testGetSimpleClassName() {
		String first = "foo";
		String name = LoggerFactory.getSimpleClassName(first);
		assertEquals(first, name);
		String second = "bar";
		String className = first + "." + second;
		name = LoggerFactory.getSimpleClassName(className);
		assertEquals(second, name);
	}
}
