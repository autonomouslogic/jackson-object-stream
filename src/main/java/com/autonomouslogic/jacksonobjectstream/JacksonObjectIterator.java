package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.databind.MappingIterator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/**
 * An iterator reading JSON objects from a {@link java.io.InputStream}.
 * This is basically a wrapper for {@link MappingIterator} which is {@link AutoCloseable}.
 * This used to be a more complex implementation, but Jackson already has this built-in.
 *
 * This is not thread-safe.
 */
public class JacksonObjectIterator<T> implements Iterator<T>, Closeable, AutoCloseable {

	private final MappingIterator<T> delegate;

	protected JacksonObjectIterator(MappingIterator<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public T next() {
		return delegate.next();
	}

	@Override
	public void close() throws IOException {
		delegate.close();
	}

	public MappingIterator<T> getDelegate() {
		return delegate;
	}
}
