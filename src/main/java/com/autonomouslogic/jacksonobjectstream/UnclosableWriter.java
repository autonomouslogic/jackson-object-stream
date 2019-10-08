package com.autonomouslogic.jacksonobjectstream;

import java.io.IOException;
import java.io.Writer;

/**
 * Wraps a delegate {@link Writer} instance, disabling the <code>close()</code> method.
 */
class UnclosableWriter extends Writer {
	private final Writer delegate;

	public UnclosableWriter(Writer delegate) {
		this.delegate = delegate;
	}

	@Override
	public void write(int c) throws IOException {
		delegate.write(c);
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		delegate.write(cbuf);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		delegate.write(cbuf, off, len);
	}

	@Override
	public void write(String str) throws IOException {
		delegate.write(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		delegate.write(str, off, len);
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		return delegate.append(csq);
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		return delegate.append(csq, start, end);
	}

	@Override
	public Writer append(char c) throws IOException {
		return delegate.append(c);
	}

	@Override
	public void flush() throws IOException {
		delegate.flush();
	}

	@Override
	public void close() throws IOException {
		// NOOP;
	}
}
