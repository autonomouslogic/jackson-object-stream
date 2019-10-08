package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class JacksonObjectStreamFactoryTest {
	private static String[] testFiles = new String[] {
		"newline.json",
		"inline.json",
		"pretty.json",
		"mixed.json"
	};
	JacksonObjectStreamFactory factory;

	@Before
	public void before() {
		factory = new JacksonObjectStreamFactory(new ObjectMapper());
	}

	@Test
	public void shouldCreateIteratorsFromClassSpec() throws Exception {
		for (String testFile : testFiles) {
			String fmsg = "file:" + testFile;
			System.out.println(fmsg);
			try (JacksonObjectIterator<TestObject> iterator = factory.createIterator(openTestFile(testFile), TestObject.class)) {
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
				assertNull(fmsg, iterator.next());
			}
		}
	}

	private static final InputStream openTestFile(String testFile) throws FileNotFoundException {
		InputStream in = JacksonObjectStreamFactory.class.getResourceAsStream("/" + testFile);
		if (in == null) {
			throw new FileNotFoundException(testFile);
		}
		return in;
	}

	@Test
	public void shouldCreateStreamWriter() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (JacksonObjectStreamWriter writer = factory.createWriter(out)) {
			writer.writeObject(new TestObject(0));
			writer.writeObject(new TestObject(1));
			writer.writeObject(new TestObject(2));
		}
		assertEquals("{\"a\":0}\n{\"a\":1}\n{\"a\":2}\n", new String(out.toByteArray()));
	}
}
