package com.bjxc.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


public class JsonDateSerializer extends JsonSerializer<Date> {
	private SimpleDateFormat dateFormat;

	public JsonDateSerializer(String format) {
		dateFormat = new SimpleDateFormat(format);
	}

	@Override
	public void serialize(Date date, JsonGenerator gen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		String value = dateFormat.format(date);
		gen.writeString(value);
	}
}
