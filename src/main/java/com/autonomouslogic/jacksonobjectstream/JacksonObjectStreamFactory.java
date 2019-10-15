package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

/**
 * Main factory for creating stream readers and writers.
 */
public class JacksonObjectStreamFactory {
	private final ObjectMapper objectMapper;
	private final JsonFactory jsonFactory;

	public JacksonObjectStreamFactory(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		jsonFactory = objectMapper.getFactory();
	}

	public <T> JacksonObjectIterator<T> createReader(File file, Class<T> type) throws IOException, JsonParseException {
		return createReader(file, 8192, type);
	}

	public <T> JacksonObjectIterator<T> createReader(File file, int bufferSize, Class<T> type) throws IOException, JsonParseException {
		return createReader(new BufferedInputStream(new FileInputStream(file), bufferSize), type);
	}

	public <T> JacksonObjectIterator<T> createReader(InputStream in, Class<T> type) throws IOException, JsonParseException {
		JsonParser parser = createJsonParser(in);
		return createReader(parser, type);
	}

	public <T> JacksonObjectIterator<T> createReader(JsonParser parser, Class<T> type) throws IOException {
		MappingIterator<T> mappingIterator = objectMapper.readerFor(type).readValues(parser);
		return new JacksonObjectIterator<>(mappingIterator);
	}

	public JsonParser createJsonParser(InputStream in) throws IOException, JsonParseException {
		return jsonFactory.createParser(in);
	}

	public JacksonObjectStreamWriter createWriter(File file) throws FileNotFoundException, IOException {
		return createWriter(file, 8192);
	}

	public JacksonObjectStreamWriter createWriter(File file, int bufferSize) throws FileNotFoundException, IOException {
		return createWriter(new BufferedOutputStream(new FileOutputStream(file), bufferSize));
	}

	public JacksonObjectStreamWriter createWriter(OutputStream out) throws IOException {
		Writer writer = new OutputStreamWriter(out);
		JsonGenerator generator = createGenerator(writer);
		return createWriter(generator, writer);
	}

	public JacksonObjectStreamWriter createWriter(JsonGenerator generator, Writer writer) {
		return new JacksonObjectStreamWriter(objectMapper, generator, writer);
	}

	public JsonGenerator createGenerator(Writer writer) throws IOException {
		JsonGenerator generator = jsonFactory.createGenerator(new UnclosableWriter(writer));
		generator.setRootValueSeparator(null);
		return generator;
	}

}
