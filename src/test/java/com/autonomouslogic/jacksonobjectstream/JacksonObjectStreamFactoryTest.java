package com.autonomouslogic.jacksonobjectstream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

public class JacksonObjectStreamFactoryTest {
	JacksonObjectStreamFactory factory;

	@Before
	public void before() {
		factory = new JacksonObjectStreamFactory(new ObjectMapper());
	}

	@Test
	public void shouldCreateIteratorsFromClassSpec() throws Exception {
		JacksonObjectIterator<TestObject> iterator =
				factory.createReader(Util.openTestFile(Util.testFiles[0]), TestObject.class);
		assertNotNull(iterator);
		TestObject obj = iterator.next();
		assertNotNull(obj);
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
