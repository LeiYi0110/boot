package com.bjxc.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("hiding")
public class JsonDateYMDDeserializer extends JsonDeserializer<Date> {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat ymdBasFormat = new SimpleDateFormat("yyyy/MM/dd");
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
        String date = jp.getText();  
        System.out.println(date);
        try {
        	if(date.contains("-")){
        		return (Date) dateFormat.parse(date); 
        	}else if(date.contains("/")){
        		return (Date) ymdBasFormat.parse(date); 
        	}else{
        		return (Date) ymdFormat.parse(date); 
        	}
        } catch (ParseException e) {  
            throw new RuntimeException(e);  
        }  
	}



}
