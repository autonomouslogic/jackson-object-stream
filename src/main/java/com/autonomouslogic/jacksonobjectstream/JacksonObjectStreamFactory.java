package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.core.*;

import java.io.*;

/**
 * Main factory for creating stream readers and writers.
 */
public class JacksonObjectStreamFactory {
	private final ObjectCodec objectCodec;
	private final JsonFactory jsonFactory;

	public JacksonObjectStreamFactory(ObjectCodec objectCodec) {
		this.objectCodec = objectCodec;
		jsonFactory = objectCodec.getFactory();
	}

	public <T> JacksonObjectIterator<T> createIterator(File file, Class<T> type) throws IOException, JsonParseException {
		return createIterator(file, 8192, type);
	}

	public <T> JacksonObjectIterator<T> createIterator(File file, int bufferSize, Class<T> type) throws IOException, JsonParseException {
		return createIterator(new BufferedInputStream(new FileInputStream(file), bufferSize), type);
	}

	public <T> JacksonObjectIterator<T> createIterator(InputStream in, Class<T> type) throws IOException, JsonParseException {
		JsonParser parser = createJsonParser(in);
		return createIterator(parser, type);
	}

	public <T> JacksonObjectIterator<T> createIterator(JsonParser parser, Class<T> type) {
		return new JacksonObjectIterator<>(parser, type, objectCodec);
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
		return new JacksonObjectStreamWriter(objectCodec, generator, writer);
	}

	public JsonGenerator createGenerator(Writer writer) throws IOException {
		JsonGenerator generator = jsonFactory.createGenerator(new UnclosableWriter(writer));
		generator.setRootValueSeparator(null);
		return generator;
	}

}
