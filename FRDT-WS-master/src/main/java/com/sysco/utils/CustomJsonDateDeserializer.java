package com.sysco.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by james.zhu on 2018/9/27.
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy");
        String date = jp.getText();
        if(StringUtils.isBlank(date)){
            return null;
        }
        try {
            return format.parse(date);
        } catch (ParseException e1) {
            throw new RuntimeException(e1);
        }
    }
}
