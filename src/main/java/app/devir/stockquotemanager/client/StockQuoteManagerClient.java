package app.devir.stockquotemanager.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.CommunicationException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.devir.stockquotemanager.exception.StockQuoteManagerException;

public class StockQuoteManagerClient {

    private static final String STOCK_MANAGER_URL = "http://localhost:8080";
    private static final String HOST = "localhost";
    private static final String PORT = "8081";
    
    private RestTemplate restTemplate;
    private ResponseEntity<String> response;
    public boolean isStockInList;

    public StockQuoteManagerClient() {
        this.restTemplate = new RestTemplate();
    }

    public void verifyStock(String stockId)
            throws StockQuoteManagerException, CommunicationException, JsonProcessingException {

        response = requestStockVerify();

        if (HttpStatus.OK.equals(response.getStatusCode())) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  

            String json = response.getBody();
            List<StockQuoteManager> stocksListed = mapper.readValue(json, new TypeReference<List<StockQuoteManager>>(){});

            for (StockQuoteManager s : stocksListed) {
                if (stockId.equalsIgnoreCase(s.getId())) isStockInList = true;
            }

            if (!isStockInList) throw new StockQuoteManagerException("Stock ID not found");

        } else {
            throw new CommunicationException("Communication to StockManager has failed");
        }

    }

    public ResponseEntity<String> requestNotification() {
        Map<String, String> map = new HashMap<>();
        map.put("host", HOST);
        map.put("port", PORT);

        return restTemplate.postForEntity(STOCK_MANAGER_URL + "notification", map, String.class);
    }

    @Cacheable("stock")
    public ResponseEntity<String> requestStockVerify() {
        return restTemplate.getForEntity(STOCK_MANAGER_URL + "/stock", String.class);
    }

    public boolean isStockInList() {
        return isStockInList;
    }

}
