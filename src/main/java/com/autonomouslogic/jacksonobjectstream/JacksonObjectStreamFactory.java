package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 *
 */
public class JacksonObjectStreamFactory {
	private final ObjectCodec objectCodec;
	private final JsonFactory jsonFactory;

	public JacksonObjectStreamFactory(ObjectCodec objectCodec) {
		this.objectCodec = objectCodec;
		jsonFactory = objectCodec.getFactory();
	}

	public <T> Iterator<T> createIterator(InputStream in, Class<T> type) throws JsonParseException, IOException {
		return createIterator(jsonFactory.createParser(in), type);
	}

	public <T> Iterator<T> createIterator(JsonParser parser, Class<T> type) {
		return new JacksonObjectIterator<>(parser, type, objectCodec);
	}
}
