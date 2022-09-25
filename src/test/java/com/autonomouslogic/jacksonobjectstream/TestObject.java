package com.autonomouslogic.jacksonobjectstream;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Test object used for JSON parse tests.
 */
public class TestObject {
	@JsonProperty
	public int a;

	public TestObject() {}

	public TestObject(int a) {
		this.a = a;
	}
}
