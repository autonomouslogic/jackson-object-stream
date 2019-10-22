package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class JacksonObjectIteratorTest {
	JacksonObjectStreamFactory factory;

	@Before
	public void before() throws Exception {
		factory = new JacksonObjectStreamFactory(new ObjectMapper());
	}

	@Test
	public void shouldReadObjects() throws Exception {
		for (String testFile : Util.testFiles) {
			String fmsg = "file:" + testFile;
			try (JacksonObjectIterator<TestObject> iterator = factory.createReader(Util.openTestFile(testFile), TestObject.class)) {
				assertNotNull(fmsg, iterator);
				for (int i = 0; i < 4; i++) {
					String imsg = fmsg + ",i:" + i;
					assertTrue(imsg, iterator.hasNext());
					TestObject obj = iterator.next();
					assertNotNull(imsg, obj);
					assertEquals(imsg, i, obj.a);
				}
				assertFalse(fmsg, iterator.hasNext());
				try {
					iterator.next();
					fail("No exception.");
				}
				catch (NoSuchElementException e) {
					assertNotNull(e);
				}
			}
		}
	}
}
