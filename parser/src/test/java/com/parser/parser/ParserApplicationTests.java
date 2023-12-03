package com.parser.parser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.parser.models.ResponseApi;
import com.parser.parser.models.ResponseDB;
import com.parser.parser.service.ServiceImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
class ParserApplicationTests {

	@Test
	void parsingQuoteSizeTest() throws StreamReadException, DatabindException, IOException {
		ResponseDB mockedApxResponse = readMockedJson("src/main/resources/apx-data.json");
			assertTrue(mockedApxResponse.getQuotes().size() > 0);
	}

	private ResponseDB readMockedJson(String filePath) throws StreamReadException, DatabindException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseDB mockedApxResponse = 
            objectMapper.readValue(new File(filePath), ResponseDB.class);
		return mockedApxResponse;
	}

	@Test
	void parsingMarketTest() throws StreamReadException, DatabindException, IOException {
		ResponseDB mockedApxResponse = readMockedJson("src/main/resources/apx-data.json");
			assertEquals("APX Power NL Hourly", mockedApxResponse.getQuotes().get(0).getMarket());
	}

	@Test
	void parsingValuesSizeTest() throws StreamReadException, DatabindException, IOException {
		ResponseDB mockedApxResponse = readMockedJson("src/main/resources/apx-data.json");
		
		HashSet<String> valueTypes = new HashSet<String>();
		mockedApxResponse.getQuotes()
		.forEach(quote -> quote.getValues().forEach(value -> valueTypes.add(value.getTLabel())));

		assertEquals(4, valueTypes.size());
	}

	@Test
	void parsingValueTypeTest() throws StreamReadException, DatabindException, IOException {

		ResponseDB mockedApxResponse = readMockedJson("src/main/resources/apx-data.json");
		ArrayList<String> values = new ArrayList<String>(List.of("Hour", "Order", "Net Volume", "Price"));

		HashSet<String> valueTypes = new HashSet<String>();
		mockedApxResponse.getQuotes()
		.forEach(quote -> quote.getValues().forEach(value -> valueTypes.add(value.getTLabel())));
		
		values.forEach(value -> assertTrue(valueTypes.contains(value)));
	}


	@Test
	void parseApxSizeTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		ServiceImpl serviceImp = new ServiceImpl(); 
		ResponseApi resp = serviceImp.parseApx();
		
		assertEquals(24, resp.getResponses().size());
	}


	@Test
	void parseApxFirstHourTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		ServiceImpl serviceImp = new ServiceImpl(); 
		ResponseApi resp = serviceImp.parseApx();
		
		assertEquals("01", resp.getResponses().get(0).getHour());
	}

	@Test
	void parseApxFirstDateTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		ServiceImpl serviceImp = new ServiceImpl(); 
		ResponseApi resp = serviceImp.parseApx();
		
		
		//First date_applied in ms to date (1573599600000)
		Long ms = 1573599600000L;
		DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");   
        Date res = new Date(ms);
		String result = obj.format(res);

		assertEquals(result, resp.getResponses().get(0).getDate());
	}

}
