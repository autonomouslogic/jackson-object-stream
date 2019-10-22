package com.autonomouslogic.jacksonobjectstream;

import java.io.FileNotFoundException;
import java.io.InputStream;

class Util {
	static String[] testFiles = new String[] {
		"newline.json",
		"inline.json",
		"pretty.json",
		"mixed.json"
	};

	static InputStream openTestFile(String testFile) throws FileNotFoundException {
		InputStream in = JacksonObjectStreamFactory.class.getResourceAsStream("/" + testFile);
		if (in == null) {
			throw new FileNotFoundException(testFile);
		}
		return in;
	}
}
