package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class JacksonObjectStreamFactoryTest {
	JacksonObjectStreamFactory factory;

	@Before
	public void before() {
		factory = new JacksonObjectStreamFactory(new ObjectMapper());
	}

	@Test
	public void shouldCreateIteratorsFromClassSpec() throws Exception {
		for (String testFile : Util.testFiles) {
			String fmsg = "file:" + testFile;
			System.out.println(fmsg);
			try (JacksonObjectIterator<TestObject> iterator = factory.createReader(Util.openTestFile(testFile), TestObject.class)) {
				assertNotNull(fmsg, iterator);
				for (int i = 0; i < 4; i++) {
					String imsg = fmsg + ",i:" + i;
					System.out.println(imsg);
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

	@Test
	public void shouldCreateStreamWriter() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (JacksonObjectStreamWriter writer = factory.createWriter(out)) {
			writer.writeObject(new TestObject(0));
		}
		assertEquals("{\"a\":0}", new String(out.toByteArray()));
	}
}
