package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Path;

/**
 * Main factory for creating stream readers and writers.
 */
public class JacksonObjectStreamFactory {
	private static final SerializedString NEWLINE_SEPARATOR = new SerializedString("\n");

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

	public <T> JacksonObjectIterator<T> createReader(Path path, Class<T> type) throws IOException, JsonParseException {
		return createReader(path.toFile(), type);
	}

	public <T> JacksonObjectIterator<T> createReader(Path path, int bufferSize, Class<T> type) throws IOException, JsonParseException {
		return createReader(path.toFile(), bufferSize, type);
	}

	public <T> JacksonObjectIterator<T> createReader(InputStream in, Class<T> type) throws IOException, JsonParseException {
		return createReader(new InputStreamReader(in), type);
	}

	public <T> JacksonObjectIterator<T> createReader(Reader reader, Class<T> type) throws IOException, JsonParseException {
		JsonParser parser = createJsonParser(reader);
		return createReader(parser, type);
	}

	public <T> JacksonObjectIterator<T> createReader(JsonParser parser, Class<T> type) throws IOException {
		MappingIterator<T> mappingIterator = objectMapper.readerFor(type).readValues(parser);
		return new JacksonObjectIterator<>(mappingIterator);
	}

	public JsonParser createJsonParser(Reader reader) throws IOException, JsonParseException {
		return jsonFactory.createParser(reader);
	}

	public JacksonObjectStreamWriter createWriter(Path path) throws FileNotFoundException, IOException {
		return createWriter(path.toFile());
	}

	public JacksonObjectStreamWriter createWriter(Path path, int bufferSize) throws FileNotFoundException, IOException {
		return createWriter(path.toFile(), bufferSize);
	}

	public JacksonObjectStreamWriter createWriter(File file) throws FileNotFoundException, IOException {
		return createWriter(file, 8192);
	}

	public JacksonObjectStreamWriter createWriter(File file, int bufferSize) throws FileNotFoundException, IOException {
		return createWriter(new BufferedOutputStream(new FileOutputStream(file), bufferSize));
	}

	public JacksonObjectStreamWriter createWriter(OutputStream out) throws IOException {
		return createWriter(new OutputStreamWriter(out));
	}

	public JacksonObjectStreamWriter createWriter(Writer writer) throws IOException {
		JsonGenerator generator = createGenerator(writer);
		return createWriter(generator, writer);
	}

	public JacksonObjectStreamWriter createWriter(JsonGenerator generator, Writer writer) {
		return new JacksonObjectStreamWriter(objectMapper, generator, writer);
	}

	public JsonGenerator createGenerator(Writer writer) throws IOException {
		JsonGenerator generator = jsonFactory.createGenerator(writer);
		generator.setRootValueSeparator(NEWLINE_SEPARATOR);
		return generator;
	}

}
