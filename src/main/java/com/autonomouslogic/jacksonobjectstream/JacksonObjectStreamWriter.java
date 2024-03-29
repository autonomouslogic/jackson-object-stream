package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.ObjectCodec;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Writes JSON objects to an output stream using Jackson.
 * This is not thread-safe.
 */
public class JacksonObjectStreamWriter implements Closeable, AutoCloseable {
	private final ObjectCodec codec;
	private final JsonGenerator generator;
	private final Writer writer;

	protected JacksonObjectStreamWriter(ObjectCodec codec, JsonGenerator generator, Writer writer) {
		this.codec = codec;
		this.generator = generator;
		this.writer = writer;
	}

	public void writeObject(Object obj) throws IOException {
		codec.writeValue(generator, obj);
	}

	public void writeAll(Iterable<?> iterable) throws IOException {
		for (Object o : iterable) {
			writeObject(o);
		}
	}

	public void writeAll(Iterator<?> iterator) throws IOException {
		while (iterator.hasNext()) {
			writeObject(iterator.next());
		}
	}

	public void writeAll(Stream<?> stream) throws IOException {
		writeAll(stream.iterator());
	}

	@Override
	public void close() throws IOException {
		generator.close();
		writer.close();
	}
}
