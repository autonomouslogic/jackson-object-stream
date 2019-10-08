package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

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
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		factory = new JacksonObjectStreamFactory(mapper);
	}

	@Test
	public void shouldCreateIteratorsFromClassSpec() throws Exception {
		for (String testFile : testFiles) {
			String fmsg = "file:" + testFile;
			System.out.println(fmsg);
			Iterator<TestObject> iterator = factory.createIterator(openTestFile(testFile), TestObject.class);
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
		JacksonObjectStreamWriter writer = factory.createWriter(out);
		writer.writeObject(new TestObject(0));
		writer.writeObject(new TestObject(1));
		writer.writeObject(new TestObject(2));
		writer.close();
		assertEquals("{\"a\":0}\n{\"a\":1}\n{\"a\":2}\n", new String(out.toByteArray()));
	}
}
