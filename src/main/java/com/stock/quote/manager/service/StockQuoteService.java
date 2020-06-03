package com.stock.quote.manager.service;


import com.stock.quote.manager.model.Stock;
import net.minidev.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class StockQuoteService {

    StockQuoteService(){}

    public void registerNotification(){
        try {
            System.out.println("registerNotification");
            String resourceUrl
                    = "http://localhost:8080/notification";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject hostObject = new JSONObject();
            hostObject.put("host", "localhost");
            hostObject.put("port", 8081);

            HttpEntity<String> request = new HttpEntity<String>(hostObject.toJSONString(), headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    resourceUrl, request , String.class);
        }catch (HttpStatusCodeException ex) {
            System.out.println(ex.getStatusCode().toString());
        }
    }

    @Cacheable(cacheNames = "Stocks")
    public Stock[] getStocksByUrl(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl
                    = "http://localhost:8080/stock";
            ResponseEntity<Stock[]> response
                    = restTemplate.getForEntity(resourceUrl, Stock[].class);
            return response.getBody();
        }catch (HttpStatusCodeException ex){
            System.out.println(ex.getStatusCode().toString());
        }
        return null;
    }
}
