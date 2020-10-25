package app.devir.stockquotemanager.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.CommunicationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.devir.stockquotemanager.exception.StockQuoteManagerException;

public class StockQuoteManagerClient {

    private static final String STOCK_MANAGER_URL = "http://localhost:8080";

    public void verifyStock(String stockId)
            throws StockQuoteManagerException, CommunicationException, JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        LogManager.getLogger().info("URL: " + STOCK_MANAGER_URL);
        ResponseEntity<String> response = restTemplate.getForEntity(STOCK_MANAGER_URL + "/stock", String.class);

        if (HttpStatus.OK.equals(response.getStatusCode())) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  

            String json = response.getBody();
            List<StockQuoteManager> stocksListed = mapper.readValue(json, new TypeReference<List<StockQuoteManager>>(){});

            stocksListed.forEach(s -> LogManager.getLogger().info("ID: " + s.getId()));

            boolean stockInList = false;
            for (StockQuoteManager s : stocksListed) {
                if (stockId.equalsIgnoreCase(s.getId())) stockInList = true;
            }

            if (!stockInList) throw new StockQuoteManagerException("Stock ID not found");

        } else {
            throw new CommunicationException("Communication to StockManager has failed");
        }

    }

}
