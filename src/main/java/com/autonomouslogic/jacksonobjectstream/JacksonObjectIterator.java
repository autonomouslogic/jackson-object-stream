package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;

import java.io.IOException;
import java.util.Iterator;

/**
 * An iterator reading objects from a {@link java.io.InputStream}.
 */
public class JacksonObjectIterator<T> implements Iterator<T> {
	private final ObjectCodec codec;
	private final Class<T> type;
	private final JsonParser parser;
	private boolean objectLoaded = false;
	private T next = null;

	protected JacksonObjectIterator(JsonParser parser, Class<T> type, ObjectCodec codec) {
		this.codec = codec;
		this.type = type;
		this.parser = parser;
	}

	private void loadNext() throws IOException {
		next = codec.readValue(parser, type);
		objectLoaded = true;
	}

	@Override
	public boolean hasNext() {
		try {
			if (!objectLoaded) {
				loadNext();
			}
			return next != null;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T next() {
		try {
			if (!objectLoaded) {
				loadNext();
			}
			T obj = next;
			objectLoaded = false;
			return obj;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
