package com.parser.parser.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.parser.models.Quote;
import com.parser.parser.models.Response;
import com.parser.parser.models.ResponseApi;
import com.parser.parser.models.ResponseDB;
import com.parser.parser.models.Value;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ServiceImpl implements ApxService{

    // I try to create a scenario where the json result is coming from DB site.
    // However, I didnt include Repository part in order to save time because creating DB is not in the requirement, I could use H2 Db here.
    // Mocked data can be found as apx-data.json which can be seen as response from Repository.

    @Override
    public ResponseApi parseApx() {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDB apxResponse = new ResponseDB();
        ResponseApi responseApi = new ResponseApi();
        try {
           apxResponse = 
            objectMapper.readValue(new File("src/main/resources/apx-data.json"), ResponseDB.class);
            List<Quote> quotes = apxResponse.getQuotes();
            List<Response> responses = convertToResponseList(quotes);
            responseApi.setResponses(responses);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseApi;
    }

    private List<Response> convertToResponseList(List<Quote> quotes){
        List<Response> responseList = new ArrayList<Response>();
         for(Quote quote: quotes){
                Response result = new Response();

                String dateInMsStr = quote.getDateApplied();
                Long dateInMs = Long.parseLong(dateInMsStr);
                DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");   
                Date res = new Date(dateInMs);
                result.setDate(obj.format(res));
                
                convertValues(result, quote.getValues());

                responseList.add(result);
            }

        return responseList;
    }

    private void convertValues(Response result, List<Value> values){
        for(Value value: values){
            String tLabel = value.getTLabel();
            if(tLabel.equals("Hour")){
                result.setHour(value.getValue());
            } else if (tLabel.equals("Net Volume")){
                result.setNetVolume(Float.parseFloat(value.getValue()));
            } else if (tLabel.equals("Price")){
                result.setPrice(Float.parseFloat(value.getValue()));
            }

        }
        
    }
}
