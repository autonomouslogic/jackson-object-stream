package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class JacksonObjectStreamWriterTest {
	JacksonObjectStreamFactory factory;
	JacksonObjectStreamWriter writer;
	ByteArrayOutputStream out;

	@Before
	public void before() throws Exception {
		factory = new JacksonObjectStreamFactory(new ObjectMapper());
		out = new ByteArrayOutputStream();
		writer = factory.createWriter(out);
	}

	@Test
	public void shouldWriteObjects() throws Exception {
		writer.writeObject(new TestObject(0));
		writer.writeObject(new TestObject(1));
		writer.writeObject(new TestObject(2));
		writer.close();
		assertEquals("{\"a\":0}\n{\"a\":1}\n{\"a\":2}", new String(out.toByteArray()));
	}

	@Test
	public void shouldWriteFromIterable() throws Exception {
		writer.writeAll(Arrays.asList(
			new TestObject(0),
			new TestObject(1),
			new TestObject(2)
		));
		writer.close();
		assertEquals("{\"a\":0}\n{\"a\":1}\n{\"a\":2}", new String(out.toByteArray()));
	}

	@Test
	public void shouldWriteFromIterator() throws Exception {
		writer.writeAll(Arrays.asList(
			new TestObject(0),
			new TestObject(1),
			new TestObject(2)
		).iterator());
		writer.close();
		assertEquals("{\"a\":0}\n{\"a\":1}\n{\"a\":2}", new String(out.toByteArray()));
	}

	@Test
	public void shouldWriteFromStream() throws Exception {
		writer.writeAll(Stream.of(
			new TestObject(0),
			new TestObject(1),
			new TestObject(2)
		));
		writer.close();
		assertEquals("{\"a\":0}\n{\"a\":1}\n{\"a\":2}", new String(out.toByteArray()));
	}

}
